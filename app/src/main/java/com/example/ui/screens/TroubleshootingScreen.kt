package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PowerOff
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.InteractiveCodeBlock
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent

data class TroubleshootingGuide(
    val id: String,
    val icon: ImageVector,
    val titleKhmer: String,
    val titleEnglish: String,
    val symptomsKhmer: String,
    val commonCausesKhmer: String,
    val stepsKhmer: List<String>,
    val diagnosticCommand: String? = null
)

@Composable
fun TroubleshootingScreen(
    isKhmer: Boolean,
    onAskAiTutor: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val guides = remember {
        listOf(
            TroubleshootingGuide(
                id = "bsod",
                icon = Icons.Default.Computer,
                titleKhmer = "អេក្រង់ខៀវ Blue Screen (BSOD)",
                titleEnglish = "Blue Screen of Death (BSOD) Diagnosis",
                symptomsKhmer = "កុំព្យូទ័រស្រាប់តែរលត់ចេញផ្ទាំងខៀវ មានកូដកំហុស CRITICAL_PROCESS_DIED ឬ MEMORY_MANAGEMENT",
                commonCausesKhmer = "Driver ខូច មិនត្រូវគ្នា, RAM មាន error, ឬ Windows System Files ខូច",
                stepsKhmer = listOf(
                    "កត់ត្រា Stop Code លើអេក្រង់",
                    "បើក Command Prompt (CMD) ជា Administrator",
                    "ដំណើរការ `sfc /scannow` ដើម្បីជួសជុល System Files",
                    "ដំណើរការ `chkdsk C: /f /r` ដើម្បីត្រួតពិនិត្យ Bad Sectors លើ Drive",
                    "ធ្វើបច្ចុប្បន្នភាព (Update) ឬ Rollback Driver ក្រាហ្វិក GPU"
                ),
                diagnosticCommand = "sfc /scannow && DISM /Online /Cleanup-Image /RestoreHealth"
            ),
            TroubleshootingGuide(
                id = "no_display",
                icon = Icons.Default.PowerOff,
                titleKhmer = "បើកកុំព្យូទ័រមិនចេញរូប (No Display / Beep Code)",
                titleEnglish = "PC Powers On But No Display / Beep Codes",
                symptomsKhmer = "កង្ហារវិល ភ្លើង Case ភ្លឺ តែអេក្រង់ងងឹត ឬឮសំឡេងប៊ីប (Beeps)",
                commonCausesKhmer = "ធូលីជាប់ RAM Slots, ខ្សែ HDMI/DisplayPort មិនណែន, ឬបន្ទះ GPU ដោតមិនត្រូវ",
                stepsKhmer = listOf(
                    "ដកខ្សែភ្លើង Power និងចុចប៊ូតុង Power ចោល 30 វិនាទី (Clear Residual Power)",
                    "ដោះបន្ទះ RAM ចេញ យកជ័រលុបមកជូតសម្អាតជើងស្ពាន់ឱ្យភ្លឺរលោង",
                    "ដោត RAM ចូល Slot ផ្សេង ឬដោតតែ ១ បន្ទះដើម្បីតេស្ត",
                    "ដោះថ្ម CMOS (CR2032) ចេញ ៥ នាទីដើម្បី Reset BIOS Default"
                ),
                diagnosticCommand = "# Check POST Beep Codes Guide for Motherboard"
            ),
            TroubleshootingGuide(
                id = "overheat",
                icon = Icons.Default.Speed,
                titleKhmer = "កុំព្យូទ័រឡើងកម្តៅខ្លាំង និងកង្ហារស្រែកខ្លាំង",
                titleEnglish = "CPU Thermal Throttling & High Fan Noise",
                symptomsKhmer = "កុំព្យូទ័រដំណើរការយឺតខុសធម្មតា (Throttling) ហើយរលត់ដោយស្វ័យប្រវត្តិពេលលេងហ្គេម",
                commonCausesKhmer = "ស្ងួតកាវកម្តៅ (Thermal Paste), ធូលីកកស្ទះលើ Heatsink, ឬកង្ហារខូច",
                stepsKhmer = listOf(
                    "ពិនិត្យសីតុណ្ហភាព CPU ដោយប្រើកម្មវិធី HWMonitor (ធម្មតា: 35-50°C, ខ្ពស់: 85°C+)",
                    "បាញ់សម្អាតធូលីចេញពី Heatsink និងកង្ហារ Fan",
                    "ជូតកាវចាស់ចេញដោយជាតិអាល់កុល Isopropyl 99% និងលាប Thermal Paste ថ្មី"
                ),
                diagnosticCommand = "# Linux monitor CPU temp:\nsensors"
            ),
            TroubleshootingGuide(
                id = "wifi",
                icon = Icons.Default.WifiOff,
                titleKhmer = "ដាច់អ៊ីនធឺណិត ឬ WiFi មានសញ្ញាឧទានលឿង",
                titleEnglish = "Internet Disconnected / DNS Probe Finished",
                symptomsKhmer = "ភ្ជាប់ WiFi ជាប់តែសរសេរថា 'No Internet, Secured' ឬមិនអាចបើកវេបសាយបាន",
                commonCausesKhmer = "IP Conflict, កកស្ទះ DNS Cache, ឬ Router គាំង",
                stepsKhmer = listOf(
                    "Flush DNS Cache ដោយប្រើពាក្យបញ្ជា `ipconfig /flushdns`",
                    "ដោះលែង និងស្នើសុំ IP ថ្មី: `ipconfig /release` និង `ipconfig /renew`",
                    "ប្តូរ DNS ទៅ Google DNS (8.8.8.8 & 8.8.4.4) ឬ Cloudflare (1.1.1.1)",
                    "Restart Router / Modem ទុកចោល ៣០ វិនាទីរួចបើកវិញ"
                ),
                diagnosticCommand = "ipconfig /flushdns && netsh int ip reset"
            ),
            TroubleshootingGuide(
                id = "malware",
                icon = Icons.Default.Security,
                titleKhmer = "កុំព្យូទ័រឆ្លងមេរោគ ឬ Pop-up លោតជាប់",
                titleEnglish = "Malware Infection & Ransomware Isolation",
                symptomsKhmer = "Browser លោតផ្ទាំងផ្សាយពាណិជ្ជកម្មចម្លែក, ឯកសារប្តូរ Extension មិនស្គាល់",
                commonCausesKhmer = "ទាញយកឯកសារ Crack, បើក Email Phishing ឬដោត USB ដែលឆ្លងមេរោគ",
                stepsKhmer = listOf(
                    "ផ្តាច់ខ្សែបណ្តាញ LAN និងបិទ Wi-Fi ភ្លាមៗ (កុំឱ្យមេរោគឆ្លងទៅម៉ាស៊ីនផ្សេង)",
                    "ចូលទៅ Safe Mode with Networking",
                    "ដំណើរការ Full Scan ដោយប្រើ Microsoft Defender Offline Scan",
                    "ពិនិត្យ Task Manager Startup Apps និងលុប Tasks ចម្លែក"
                ),
                diagnosticCommand = "# Check active network listening sockets\nnetstat -ano | findstr LISTENING"
            )
        )
    }

    var expandedGuideId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("troubleshooting_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF132338)),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = CyanPrimary.copy(alpha = 0.2f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Build, contentDescription = null, tint = CyanPrimary)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isKhmer) "មជ្ឈមណ្ឌលដោះស្រាយបញ្ហាកុំព្យូទ័រ" else "PC Diagnostic & Repair Center",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CyanPrimary
                            )
                            Text(
                                text = if (isKhmer) "ដំណោះស្រាយកំហុស Hardware & Software ទូទៅបំផុត" else "Step-by-step diagnostic workflows",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }
        }

        items(guides) { guide ->
            val isExpanded = expandedGuideId == guide.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedGuideId = if (isExpanded) null else guide.id }
                    .testTag("trouble_item_${guide.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = CyanPrimary.copy(alpha = 0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = guide.icon,
                                        contentDescription = null,
                                        tint = CyanPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isKhmer) guide.titleKhmer else guide.titleEnglish,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isKhmer) guide.titleEnglish else guide.titleKhmer,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Text(
                            text = if (isExpanded) "បង្រួម" else "មើលដំណោះស្រាយ",
                            color = CyanPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(14.dp))

                        // Symptoms
                        Text(
                            text = "🔍 រោគសញ្ញា (Symptoms):",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AmberAccent
                        )
                        Text(
                            text = guide.symptomsKhmer,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Common causes
                        Text(
                            text = "⚠️ មូលហេតុបង្ក (Root Causes):",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEF4444)
                        )
                        Text(
                            text = guide.commonCausesKhmer,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Steps
                        Text(
                            text = "🛠️ ជំហានដោះស្រាយ (Actionable Steps):",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldAccent
                        )
                        guide.stepsKhmer.forEachIndexed { i, step ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("${i + 1}. ", fontWeight = FontWeight.Bold, color = CyanPrimary, fontSize = 12.sp)
                                Text(step, style = MaterialTheme.typography.bodySmall, lineHeight = 18.sp)
                            }
                        }

                        if (guide.diagnosticCommand != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            InteractiveCodeBlock(
                                code = guide.diagnosticCommand,
                                language = "Diagnostic Terminal"
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                onAskAiTutor("លោកគ្រូ សូមជួយពន្យល់លម្អិតពីរបៀបដោះស្រាយបញ្ហា: ${guide.titleKhmer}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("សួរគ្រូជំនួយ AI បន្ថែមលើបញ្ហានេះ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
