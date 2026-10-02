package com.example.data.repository

import com.example.data.model.ComputerTrack
import com.example.data.model.QuizQuestion

object QuizDataProvider {

    fun getQuizForChapter(chapterId: Int): List<QuizQuestion> {
        val chapter = CurriculumCatalog.getChapterById(chapterId)
            ?: return emptyList()

        return when (chapter.track) {
            ComputerTrack.HARDWARE -> listOf(
                QuizQuestion(
                    id = "q_${chapterId}_1",
                    questionKhmer = "តើសមាសភាគមួយណាជា 'ខួរក្បាល' នៃកុំព្យូទ័រ ដែលដំណើរការការគណនានិងបញ្ជាកូដ?",
                    questionEnglish = "Which component serves as the computer's 'brain' responsible for executing instructions and math calculations?",
                    optionsKhmer = listOf("RAM (អង្គចងចាំ)", "CPU (អង្គគណនាកណ្តាល)", "Power Supply (PSU)", "Hard Disk (HDD)"),
                    optionsEnglish = listOf("RAM", "CPU (Central Processing Unit)", "Power Supply Unit", "Hard Disk Drive"),
                    correctIndex = 1,
                    explanationKhmer = "CPU (Central Processing Unit) ទទួលបន្ទុកលើការទាញយក បកស្រាយ និងគណនាបញ្ជាកូដទាំងអស់។",
                    explanationEnglish = "The CPU is the central processing engine that performs the machine cycle."
                ),
                QuizQuestion(
                    id = "q_${chapterId}_2",
                    questionKhmer = "តើអង្គចងចាំមួយណាដែលមានល្បឿនលឿនបំផុត ប៉ុន្តែបាត់បង់ទិន្នន័យពេលដាច់ភ្លើង (Volatile)?",
                    questionEnglish = "Which memory type delivers ultra-fast speed but loses all contents when power is turned off (Volatile)?",
                    optionsKhmer = listOf("NVMe SSD", "RAM (Random Access Memory)", "ROM BIOS", "Flash Drive USB"),
                    optionsEnglish = listOf("NVMe SSD", "RAM (Random Access Memory)", "ROM BIOS", "USB Flash Drive"),
                    correctIndex = 1,
                    explanationKhmer = "RAM ជា Volatile Memory ដែលផ្ទុកទិន្នន័យកម្មវិធីកំពុងដំណើរការភ្លាមៗ និងរលុបពេលបិទភ្លើង។",
                    explanationEnglish = "RAM requires continuous electrical power to maintain state, making it volatile."
                ),
                QuizQuestion(
                    id = "q_${chapterId}_3",
                    questionKhmer = "តើបច្ចេកវិទ្យា Storage មួយណាដែលភ្ជាប់ដោយផ្ទាល់តាមរយៈរន្ធ PCIe និងផ្តល់ល្បឿនលឿនជាងគេ?",
                    questionEnglish = "Which storage technology connects directly over PCIe buses to deliver maximum read/write bandwidth?",
                    optionsKhmer = listOf("HDD 5400 RPM", "SATA SSD 2.5\"", "M.2 NVMe SSD", "CD-ROM"),
                    optionsEnglish = listOf("HDD 5400 RPM", "SATA SSD 2.5\"", "M.2 NVMe SSD", "CD-ROM"),
                    correctIndex = 2,
                    explanationKhmer = "M.2 NVMe ដំណើរការលើផ្លូវ PCIe Bus ផ្ទាល់ អាចរត់បានល្បឿនរហូតដល់ 7000+ MB/s (Gen4) និង 14000+ MB/s (Gen5)។",
                    explanationEnglish = "NVMe communicates directly with the CPU via PCIe lanes bypassing slow legacy SATA controllers."
                )
            )
            ComputerTrack.OPERATING_SYSTEMS -> listOf(
                QuizQuestion(
                    id = "q_${chapterId}_1",
                    questionKhmer = "តើអ្វីជាតួនាទីចម្បងនៃ OS Kernel?",
                    questionEnglish = "What is the primary role of the Operating System Kernel?",
                    optionsKhmer = listOf("រចនាវេបសាយ", "គ្រប់គ្រងធនធាន Hardware និងសុវត្ថិភាពរវាងកម្មវិធី", "ចាក់តន្ត្រី", "បង្កើតកូដ AI"),
                    optionsEnglish = listOf("Web design", "Managing hardware resources & memory security", "Playing media", "Generating AI models"),
                    correctIndex = 1,
                    explanationKhmer = "Kernel ជាស្នូលនៃ OS ដែលគ្រប់គ្រង CPU, Memory, File Systems និង I/O Devices។",
                    explanationEnglish = "The kernel mediates between applications and underlying hardware via system calls."
                ),
                QuizQuestion(
                    id = "q_${chapterId}_2",
                    questionKhmer = "លើប្រព័ន្ធ Linux តើពាក្យបញ្ជាណាដែលប្រើសម្រាប់ពិនិត្យ Processes កំពុងដំណើរការក្នុងពេលជាក់ស្តែង?",
                    questionEnglish = "On Linux, which command displays running processes in real-time?",
                    optionsKhmer = listOf("ls", "top ឬ htop", "cd", "mkdir"),
                    optionsEnglish = listOf("ls", "top or htop", "cd", "mkdir"),
                    correctIndex = 1,
                    explanationKhmer = "ពាក្យបញ្ជា `top` ឬ `htop` បង្ហាញ CPU usage, Memory, PID និង Processes យ៉ាងលម្អិត។",
                    explanationEnglish = "`top` and `htop` provide real-time interactive process monitoring."
                )
            )
            ComputerTrack.NETWORKING -> listOf(
                QuizQuestion(
                    id = "q_${chapterId}_1",
                    questionKhmer = "តើគំរូយោង OSI Model មានប៉ុន្មានជាន់ (Layers)?",
                    questionEnglish = "How many distinct layers make up the OSI Reference Model?",
                    optionsKhmer = listOf("៤ ជាន់", "៥ ជាន់", "៧ ជាន់", "១០ ជាន់"),
                    optionsEnglish = listOf("4 Layers", "5 Layers", "7 Layers", "10 Layers"),
                    correctIndex = 2,
                    explanationKhmer = "OSI Model មាន ៧ ជាន់: Physical, Data Link, Network, Transport, Session, Presentation, Application។",
                    explanationEnglish = "The Open Systems Interconnection model standardizes communication into 7 distinct layers."
                ),
                QuizQuestion(
                    id = "q_${chapterId}_2",
                    questionKhmer = "តើពិធីការណាដែលទទួលខុសត្រូវលើការបកប្រែឈ្មោះ Domain (ឧ. google.com) ទៅជា IP Address?",
                    questionEnglish = "Which protocol translates human-readable domain names into numerical IP addresses?",
                    optionsKhmer = listOf("DHCP", "DNS (Domain Name System)", "FTP", "ARP"),
                    optionsEnglish = listOf("DHCP", "DNS (Domain Name System)", "FTP", "ARP"),
                    correctIndex = 1,
                    explanationKhmer = "DNS (Domain Name System) ដើរតួជាសៀវភៅទូរស័ព្ទនៃអ៊ីនធឺណិត បកប្រែឈ្មោះគេហទំព័រទៅជា IP Address។",
                    explanationEnglish = "DNS resolves human-friendly hostnames into machine-routable IP addresses."
                )
            )
            else -> listOf(
                QuizQuestion(
                    id = "q_${chapterId}_1",
                    questionKhmer = "តើគោលការណ៍សំខាន់បំផុតក្នុងការដោះស្រាយបញ្ហាកុំព្យូទ័រគឺអ្វី?",
                    questionEnglish = "What is the golden rule when troubleshooting computer systems?",
                    optionsKhmer = listOf("ទាយដោយចៃដន្យ", "ពិនិត្យ Logs និងបំបែកបញ្ហាម្តងមួយជំហាន", "ទិញគ្រឿងថ្មីភ្លាមៗ", "លុបឯកសារចោលទាំងអស់"),
                    optionsEnglish = listOf("Guess randomly", "Check logs and isolate variables systematically", "Buy new hardware immediately", "Delete all system files"),
                    correctIndex = 1,
                    explanationKhmer = "ការអាន Log files និងការវិភាគជាប្រព័ន្ធជួយឱ្យរកឃើញឬសគល់នៃបញ្ហាយ៉ាងត្រឹមត្រូវ។",
                    explanationEnglish = "Systematic isolation and inspecting diagnostic telemetry leads to accurate root cause discovery."
                ),
                QuizQuestion(
                    id = "q_${chapterId}_2",
                    questionKhmer = "តើការអនុវត្តមួយណាដែលធានាសុវត្ថិភាពទិន្នន័យខ្ពស់បំផុត?",
                    questionEnglish = "Which practice ensures the highest level of data recovery safety?",
                    optionsKhmer = listOf("មិនបាច់ Backup", "Backup ទិន្នន័យជាប្រចាំតាមច្បាប់ 3-2-1", "រក្សាទុកលើ Flash Drive តែមួយគត់", "បិទកុំព្យូទ័រដោយដកខ្សែភ្លើង"),
                    optionsEnglish = listOf("No backups", "Regular automated 3-2-1 backup strategy", "Single USB drive backup", "Unplugging power cord"),
                    correctIndex = 1,
                    explanationKhmer = "ច្បាប់ 3-2-1: មានច្បាប់ចម្លង 3, លើឧបករណ៍ 2 ប្រភេទខុសគ្នា, និង 1 ច្បាប់ទុកនៅ Off-site ឬ Cloud។",
                    explanationEnglish = "The 3-2-1 rule keeps 3 copies on 2 different media with 1 copy off-site."
                )
            )
        }
    }
}
