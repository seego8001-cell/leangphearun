package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiTutorService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val systemPrompt = """
        You are 'លោកគ្រូជំនួយ Computer Tutor', an inspiring, patient, and world-class Computer Science and IT professor helping students learn all 120 chapters of computing.
        You speak both fluent Khmer and English. Always explain complex concepts with simple real-world analogies, clean diagrams/bullet points, and clear code/terminal examples where relevant.
        When answering:
        1. Always be encouraging, structured, and pedagogical.
        2. If a student asks in Khmer, answer primarily in Khmer with standard English technical terms in parentheses (e.g. អង្គគណនា (CPU), អង្គចងចាំបណ្តោះអាសន្ន (RAM), បណ្តាញ (Network)).
        3. Break your explanation into:
           - សេចក្តីសង្ខេប / Summary
           - របៀបដំណើរការ / How it Works
           - ឧទាហរណ៍ជាក់ស្តែង / Practical Example / Code
           - គន្លឹះសំខាន់ / Pro Tip for Students
        4. If diagnosing a computer problem, provide a step-by-step diagnostic checklist.
    """.trimIndent()

    suspend fun askTutor(
        userMessage: String,
        lessonContext: String? = null,
        mode: String = "general"
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Provide high-quality localized educational guidance if API key is not yet configured
            return@withContext Result.success(generateOfflineTutorResponse(userMessage, lessonContext, mode))
        }

        try {
            val fullPrompt = buildString {
                if (!lessonContext.isNullOrBlank()) {
                    append("[បរិបទមេរៀន / Current Lesson Context: $lessonContext]\n\n")
                }
                append(userMessage)
            }

            val requestJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", fullPrompt)
                            })
                        })
                    })
                }
                put("contents", contentsArray)

                val systemInstructionObj = JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemPrompt)
                        })
                    })
                }
                put("systemInstruction", systemInstructionObj)

                val generationConfig = JSONObject().apply {
                    put("temperature", 0.7)
                    put("topP", 0.95)
                    put("topK", 40)
                }
                put("generationConfig", generationConfig)
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = requestJson.toString().toRequestBody(mediaType)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (!response.isSuccessful || responseBody.isNullOrBlank()) {
                Log.w("GeminiTutorService", "API call failed code ${response.code}: $responseBody")
                return@withContext Result.success(
                    generateOfflineTutorResponse(userMessage, lessonContext, mode)
                )
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text")
                    if (text.isNotBlank()) {
                        return@withContext Result.success(text)
                    }
                }
            }

            Result.success(generateOfflineTutorResponse(userMessage, lessonContext, mode))
        } catch (e: Exception) {
            Log.e("GeminiTutorService", "Error calling Gemini API", e)
            Result.success(generateOfflineTutorResponse(userMessage, lessonContext, mode))
        }
    }

    private fun generateOfflineTutorResponse(
        query: String,
        lessonContext: String?,
        mode: String
    ): String {
        val qLower = query.lowercase()
        return when {
            qLower.contains("cpu") || qLower.contains("processor") || qLower.contains("អង្គគណនា") -> {
                """
                🧠 **ការពន្យល់ពី CPU (Central Processing Unit / អង្គគណនាកណ្តាល):**
                
                CPU គឺជាខួរក្បាលនៃប្រព័ន្ធកុំព្យូទ័រ ដែលទទួលខុសត្រូវលើការទាញយក (Fetch), បកស្រាយ (Decode), និងប្រតិបត្តិ (Execute) រាល់បញ្ជាកូដទាំងអស់។
                
                📌 **សមាសភាគសំខាន់ៗទាំង ៣ នៃ CPU:**
                1. **ALU (Arithmetic Logic Unit):** គណនាលេខបូកដកគុណចែក និងប្រៀបធៀបតក្កវិទ្យា (AND, OR, NOT)។
                2. **Control Unit (CU):** គ្រប់គ្រងចរន្តទិន្នន័យរវាង CPU, RAM និងគ្រឿងផ្សេងៗ។
                3. **Registers & Cache (L1, L2, L3):** អង្គចងចាំដែលមានល្បឿនលឿនបំផុតនៅក្នុងបន្ទះឈីប ដើម្បីរក្សាទុកទិន្នន័យកំពុងគណនា។
                
                💡 **គន្លឹះជ្រើសរើស CPU សម័យថ្មី:**
                - រកមើលចំនួន Core និង Threads (ឧ. 8 Cores / 16 Threads សម្រាប់ Multitasking)
                - Clock Speed (GHz) និងទំហំ Cache L3 (ឧ. 32MB+ សម្រាប់ Gaming/Rendering)
                - IPC (Instructions Per Clock) ដែលកំណត់ប្រសិទ្ធភាពពិតប្រាកដក្នុងមួយវដ្តនាឡិកា។
                """.trimIndent()
            }
            qLower.contains("ram") || qLower.contains("memory") || qLower.contains("អង្គចងចាំ") -> {
                """
                ⚡ **ការពន្យល់ពី RAM (Random Access Memory / អង្គចងចាំបណ្តោះអាសន្ន):**
                
                RAM គឺជាអង្គចងចាំដែលមានល្បឿនខ្ពស់ខ្លាំង ប៉ុន្តែបាត់បង់ទិន្នន័យពេលដាច់ភ្លើង (Volatile Memory)។ វាផ្ទុកកម្មវិធី និងទិន្នន័យដែល CPU កំពុងដំណើរការភ្លាមៗ។
                
                📌 **ភាពខុសគ្នារវាង DDR4 និង DDR5:**
                - **DDR4:** ល្បឿនចាប់ពី 2400 MHz ដល់ 3600 MHz, តង់ស្យុង 1.2V
                - **DDR5:** ល្បឿនចាប់ពី 4800 MHz ដល់ 7200+ MHz, តង់ស្យុង 1.1V និងមាន On-die ECC សម្រាប់ស្ថេរភាពខ្ពស់
                
                💡 **គន្លឹះដោះស្រាយបញ្ហា RAM:**
                បើកុំព្យូទ័រឮសំឡេងប៊ីប (Beep codes) ឬមិនចេញរូប (No Display):
                1. ដោះបន្ទះ RAM ចេញ
                2. យកជ័រលុបមកជូតថ្នមៗលើជើងស្ពាន់ (Golden Pins)
                3. ផ្លាស់ប្តូរ Slot ឬដោតម្តងមួយបន្ទះដើម្បីធ្វើតេស្ត។
                """.trimIndent()
            }
            qLower.contains("blue screen") || qLower.contains("bsod") || qLower.contains("គាំង") || qLower.contains("អេក្រង់ខៀវ") -> {
                """
                🛠️ **ជំហានដោះស្រាយបញ្ហាអេក្រង់ខៀវ BSOD (Blue Screen of Death):**
                
                BSOD កើតឡើងនៅពេលប្រព័ន្ធប្រតិបត្តិការ Windows ជួបប្រទះបញ្ហាកម្រិត Kernel ដែលមិនអាចបន្តដំណើរការបានដោយសុវត្ថិភាព។
                
                🔍 **កូដកំហុសទូទៅបំផុត:**
                - `CRITICAL_PROCESS_DIED`: បញ្ហា System Files ឬ Drivers ខូច
                - `MEMORY_MANAGEMENT`: បន្ទះ RAM មាន Error ឬ Memory Leak
                - `INACCESSIBLE_BOOT_DEVICE`: បញ្ហា SSD/HDD ឬ BIOS AHCI/RAID Configuration
                
                📋 **ដំណោះស្រាយតាមជំហាន:**
                1. កត់ត្រា Stop Code ដែលបង្ហាញលើអេក្រង់
                2. ចូលទៅ **Safe Mode**
                3. បើក Command Prompt (CMD) ជា Administrator ហើយដំណើរការ:
                   `sfc /scannow`
                   `DISM /Online /Cleanup-Image /RestoreHealth`
                4. ពិនិត្យ Update ឬ Rollback Driver ក្រាហ្វិក (GPU) និង Chipset។
                """.trimIndent()
            }
            qLower.contains("ip") || qLower.contains("network") || qLower.contains("បណ្តាញ") || qLower.contains("tcp") -> {
                """
                🌐 **ស្វែងយល់ពីបណ្តាញកុំព្យូទ័រ (Networking Fundamentals):**
                
                បណ្តាញកុំព្យូទ័រដំណើរការលើគំរូ ៧ ជាន់ OSI Model:
                1. Physical Layer (ខ្សែកាប, រលកវិទ្យុ)
                2. Data Link Layer (MAC Address, Switches)
                3. Network Layer (IP Address, Routers)
                4. Transport Layer (TCP ធានាការបញ្ជូន, UDP ល្បឿនលឿន)
                5. Session Layer
                6. Presentation Layer (SSL/TLS Encryption)
                7. Application Layer (HTTP, DNS, FTP)
                
                💡 **ពាក្យបញ្ជា Command Line សំខាន់ៗសម្រាប់តេស្តបណ្តាញ:**
                - `ping 8.8.8.8` (ពិនិត្យការតភ្ជាប់អ៊ីនធឺណិត)
                - `ipconfig /all` ឬ `ifconfig` (មើល IP Address, Gateway, DNS)
                - `tracert google.com` (តាមដានផ្លូវ Hop នៃកញ្ចប់ Packet)
                - `nslookup domain.com` (ត្រួតពិនិត្យប្រព័ន្ធ DNS)
                """.trimIndent()
            }
            else -> {
                """
                🎓 **ការណែនាំពីលោកគ្រូជំនួយ Computer Tutor:**
                
                សួស្តីប្អូនសិស្សានុសិស្ស! ខ្ញុំជាគ្រូជំនួយឆ្លាតវៃសម្រាប់ជួយបង្រៀននិងពន្យល់រាល់មេរៀនកុំព្យូទ័រទាំង ១២០ ជំពូក។
                
                📚 **សំណួររបស់ប្អូន:** "$query"
                ${if (!lessonContext.isNullOrBlank()) "📖 **បរិបទមេរៀនបច្ចុប្បន្ន:** $lessonContext\n" else ""}
                
                💡 **ចំណុចគន្លឹះដែលត្រូវចងចាំ:**
                1. ស្វែងយល់ពីមូលដ្ឋានគ្រឹះស្ថាបត្យកម្មកុំព្យូទ័រ (Hardware + OS + Software)
                2. អនុវត្តជាក់ស្តែងតាមរយៈលំហាត់ Command Line ឬ Code Snippet ក្នុងមេរៀន
                3. ធ្វើតេស្ត Quiz នៅចុងបញ្ចប់ជំពូកនីមួយៗដើម្បីវាស់ស្ទង់ចំណេះដឹង។
                
                តើប្អូនចង់ឱ្យលោកគ្រូពន្យល់លម្អិតបន្ថែម ឬបង្កើតសំណួរអនុវត្ត (Practice Quiz) លើប្រធានបទនេះដែរឬទេ?
                """.trimIndent()
            }
        }
    }
}
