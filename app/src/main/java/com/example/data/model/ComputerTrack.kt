package com.example.data.model

enum class ComputerTrack(
    val id: String,
    val titleKhmer: String,
    val titleEnglish: String,
    val descriptionKhmer: String,
    val descriptionEnglish: String,
    val startChapter: Int,
    val endChapter: Int,
    val totalChapters: Int = 12
) {
    HARDWARE(
        id = "hardware",
        titleKhmer = "ផ្នែករឹងនិងស្ថាបត្យកម្មកុំព្យូទ័រ",
        titleEnglish = "Hardware & Computer Architecture",
        descriptionKhmer = "ស្វែងយល់ស៊ីជម្រៅពី CPU, RAM, GPU, Motherboard, Storage និងការតម្លើងកុំព្យូទ័រ",
        descriptionEnglish = "Deep dive into CPU, RAM, GPU, Motherboards, Storage, and PC Assembly",
        startChapter = 1,
        endChapter = 12
    ),
    OPERATING_SYSTEMS(
        id = "os",
        titleKhmer = "ប្រព័ន្ធប្រតិបត្តិការ (OS)",
        titleEnglish = "Operating Systems",
        descriptionKhmer = "រៀនពី Windows 11, Linux (Ubuntu/Debian), macOS, Terminal និង File Systems",
        descriptionEnglish = "Learn Windows 11, Linux kernel, Bash CLI, macOS, and Virtualization",
        startChapter = 13,
        endChapter = 24
    ),
    NETWORKING(
        id = "networking",
        titleKhmer = "បណ្តាញនិងអ៊ីនធឺណិត",
        titleEnglish = "Networking & Internet Protocols",
        descriptionKhmer = "គំរូ OSI, TCP/IP, IP Addressing, Routers/Switches, DNS, WiFi និង Wireshark",
        descriptionEnglish = "OSI Model, TCP/IP, Subnetting, Routing, DNS, DHCP, and Wireshark",
        startChapter = 25,
        endChapter = 36
    ),
    CYBERSECURITY(
        id = "cybersecurity",
        titleKhmer = "សន្តិសុខសាយប័រនិងព័ត៌មាន",
        titleEnglish = "Cybersecurity & InfoSec",
        descriptionKhmer = "Cryptography, Firewalls, Ethical Hacking, Malware Analysis, OWASP និង Zero Trust",
        descriptionEnglish = "Cryptography, Network Defense, Ethical Hacking, OWASP Top 10, Zero Trust",
        startChapter = 37,
        endChapter = 48
    ),
    PROGRAMMING(
        id = "programming",
        titleKhmer = "មូលដ្ឋានសរសេរកូដនិងក្បួនដោះស្រាយ",
        titleEnglish = "Programming & Algorithms",
        descriptionKhmer = "តក្កវិទ្យាកូដ, OOP, Data Structures, Algorithms (Big-O), Git និង Clean Code",
        descriptionEnglish = "Logic, OOP, Data Structures, Sorting/Search Algorithms, Git, and Testing",
        startChapter = 49,
        endChapter = 60
    ),
    WEB_APP_DEV(
        id = "web_app",
        titleKhmer = "ការអភិវឌ្ឍវេបសាយនិងកម្មវិធី",
        titleEnglish = "Web & Mobile App Development",
        descriptionKhmer = "HTML5, CSS3, JavaScript, React, Android Mobile (Kotlin), APIs និង Docker",
        descriptionEnglish = "HTML5, CSS3, JS, React, Mobile Kotlin Compose, REST APIs, Docker",
        startChapter = 61,
        endChapter = 72
    ),
    DATABASES(
        id = "databases",
        titleKhmer = "ប្រព័ន្ធទិន្នន័យនិង Big Data",
        titleEnglish = "Databases & Data Engineering",
        descriptionKhmer = "Relational SQL, NoSQL (MongoDB, Redis), Indexing, Transactions និង Caching",
        descriptionEnglish = "SQL, MongoDB, Redis, Schema Design, Indexing, and Data Pipelines",
        startChapter = 73,
        endChapter = 84
    ),
    AI_DATA_SCIENCE(
        id = "ai_data",
        titleKhmer = "បញ្ញាសិប្បនិម្មិតនិងទិន្នន័យ (AI)",
        titleEnglish = "AI, Machine Learning & LLMs",
        descriptionKhmer = "Machine Learning, Deep Learning, Neural Networks, Large Language Models (LLMs) & Prompts",
        descriptionEnglish = "Machine Learning, Neural Networks, Computer Vision, LLMs, and Prompt Engineering",
        startChapter = 85,
        endChapter = 96
    ),
    CLOUD_DEVOPS(
        id = "cloud_devops",
        titleKhmer = "កុំព្យូទ័រពពកនិង DevOps",
        titleEnglish = "Cloud Computing & DevOps",
        descriptionKhmer = "AWS, Google Cloud, Azure, Kubernetes, CI/CD Pipelines, Serverless និង Terraform",
        descriptionEnglish = "AWS, GCP, Azure, Kubernetes, CI/CD, Serverless, Infrastructure as Code",
        startChapter = 97,
        endChapter = 108
    ),
    IT_SUPPORT(
        id = "it_support",
        titleKhmer = "កម្មវិធីការិយាល័យនិងជួសជុលកុំព្យូទ័រ",
        titleEnglish = "IT Support, Office & Diagnostics",
        descriptionKhmer = "Word, Excel Formula, Troubleshooting Blue Screen, Data Recovery និងជួសជុលកំហុស",
        descriptionEnglish = "Office Suite, BSOD Diagnosis, Hardware Repair, Data Recovery, IT Helpdesk",
        startChapter = 109,
        endChapter = 120
    );

    companion object {
        fun fromChapter(chapterNumber: Int): ComputerTrack {
            return entries.firstOrNull { chapterNumber in it.startChapter..it.endChapter }
                ?: HARDWARE
        }
    }
}
