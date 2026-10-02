package com.example.data.repository

import com.example.data.model.Chapter
import com.example.data.model.ComputerTrack
import com.example.data.model.DifficultyLevel

object CurriculumCatalog {

    val allChapters: List<Chapter> by lazy {
        generateAll120Chapters()
    }

    private fun generateAll120Chapters(): List<Chapter> {
        val list = mutableListOf<Chapter>()

        // 1. HARDWARE (Ch 1 - 12)
        val hwTitles = listOf(
            Pair("ស្ថាបត្យកម្ម CPU និងដំណើរការគណនា", "CPU Architecture & Instruction Pipeline"),
            Pair("Motherboard, Chipsets និង Bus Speed", "Motherboards, Chipsets & System Buses"),
            Pair("អង្គចងចាំ RAM, DDR4/DDR5 និង L1/L2/L3 Cache", "RAM Technologies, DDR5 & Cache Hierarchy"),
            Pair("ឧបករណ៍ផ្ទុកទិន្នន័យ HDD, SSD SATA និង NVMe PCIe", "Storage Systems: HDD, SATA SSD & NVMe Gen5"),
            Pair("បន្ទះក្រាហ្វិក GPU, VRAM និង Ray Tracing", "GPU Architecture, VRAM & Ray Tracing"),
            Pair("ប្រភពថាមពល PSU និងការគ្រប់គ្រងកម្តៅ Thermal", "Power Supply (PSU) & Thermal Cooling Solutions"),
            Pair("រន្ធតភ្ជាប់ PCIe, USB4, Thunderbolt និង DisplayPort", "High-Speed I/O: PCIe 5.0, USB4 & Thunderbolt"),
            Pair("ប្រព័ន្ធ BIOS/UEFI, Secure Boot និង Firmware", "BIOS/UEFI Firmware & Secure Boot Architecture"),
            Pair("ឧបករណ៍បញ្ចូលនិងបញ្ចេញ Peripherals & Sensors", "Input/Output Peripherals & Hardware Interfaces"),
            Pair("ការតម្លើងកុំព្យូទ័រ PC Assembly មួយជំហានម្តងៗ", "Complete PC Assembly & Cable Management Guide"),
            Pair("ការធ្វើរោគវិនិច្ឆ័យផ្នែករឹង Hardware Diagnostics", "Hardware Diagnostics, Multimeter & Stress Testing"),
            Pair("អនាគតផ្នែករឹង Quantum Computing & Neuromorphic", "Next-Gen Computing: Quantum & Neuromorphic Chips")
        )
        hwTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 1
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.HARDWARE,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 4) DifficultyLevel.BEGINNER else if (num <= 9) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 35 + (num % 3) * 10
                )
            )
        }

        // 2. OPERATING SYSTEMS (Ch 13 - 24)
        val osTitles = listOf(
            Pair("ស្ថាបត្យកម្ម OS Kernel (Monolithic vs Microkernel)", "OS Kernel Architecture & System Calls"),
            Pair("ការគ្រប់គ្រង Memory និង Virtual Memory Paging", "Memory Management, Paging & Segmentation"),
            Pair("ប្រព័ន្ធឯកសារ File Systems (NTFS, EXT4, APFS, ZFS)", "File Systems Architecture: NTFS, EXT4 & APFS"),
            Pair("ដំណើរការ Process, Threading និង CPU Scheduling", "Processes, Multithreading & Concurrency"),
            Pair("ការគ្រប់គ្រង Windows 11 Administration កម្រិតខ្ពស់", "Windows 11 System Administration & Group Policy"),
            Pair("Windows Registry និងស្វ័យប្រវត្តិកម្ម PowerShell", "Windows Registry & PowerShell Automation"),
            Pair("មូលដ្ឋានគ្រឹះ Linux Kernel និងពាក្យបញ្ជា Bash CLI", "Linux Fundamentals, File Hierarchy & Bash CLI"),
            Pair("ការគ្រប់គ្រងប្រព័ន្ធ Linux (Users, Systemd, Services)", "Linux System Administration & Services Control"),
            Pair("ស្ថាបត្យកម្ម macOS, Darwin Kernel និង Unix Foundation", "macOS Architecture & Unix Command Environments"),
            Pair("និម្មិតកម្ម Virtualization (KVM, Hyper-V, VirtualBox)", "Hardware Virtualization & Type-1/2 Hypervisors"),
            Pair("ការគ្រប់គ្រង Device Drivers និង Hardware Interrupts (IRQ)", "Device Drivers, Interrupts & DMA Controllers"),
            Pair("ការការពារនិងពង្រឹងសុវត្ថិភាព OS Hardening", "Operating System Hardening & Kernel Security")
        )
        osTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 13
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.OPERATING_SYSTEMS,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 16) DifficultyLevel.BEGINNER else if (num <= 20) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 40
                )
            )
        }

        // 3. NETWORKING (Ch 25 - 36)
        val netTitles = listOf(
            Pair("គំរូយោង 7-Layer OSI Model និង TCP/IP Stack", "OSI 7-Layer Reference Model & TCP/IP Protocol Suite"),
            Pair("អាសយដ្ឋាន IP (IPv4 vs IPv6) និង CIDR Subnetting", "IP Addressing, VLSM & Subnetting Calculations"),
            Pair("ឧបករណ៍បណ្តាញ (Routers, Managed Switches, Firewalls)", "Network Hardware: Routers, Switches & VLANs"),
            Pair("ពិធីការតម្រង់ផ្លូវ Routing Protocols (OSPF, BGP, RIP)", "Routing Protocols: Static, OSPF & Enterprise BGP"),
            Pair("សេវាកម្មស្នូលបណ្តាញ DNS, DHCP និង NTP", "Core Network Services: DNS Resolution & DHCP Scope"),
            Pair("ពិធីការដឹកជញ្ជូន TCP Handshake, UDP និង QUIC", "Transport Protocols: TCP 3-Way Handshake & UDP"),
            Pair("ពិធីការវេប HTTP/1.1, HTTP/2, HTTP/3 និង SSL/TLS", "Web Protocols: HTTP/3 Evolution & SSL/TLS Encryption"),
            Pair("បណ្តាញមូលដ្ឋាន LAN, VLANs, Trunks និង 802.1Q", "Local Area Networks, VLAN Segmentation & Trunking"),
            Pair("បណ្តាញឥតខ្សែ Wi-Fi Standards (Wi-Fi 6E/7) និង WPA3", "Wireless Networking (Wi-Fi 7) & WPA3 Enterprise"),
            Pair("បណ្តាញទូលំទូលាយ WAN, MPLS និង SD-WAN Architecture", "Wide Area Networks (WAN), MPLS & SD-WAN"),
            Pair("ការវិភាគកញ្ចប់ទិន្នន័យ Packet Analysis ជាមួយ Wireshark", "Packet Sniffing & Network Forensics with Wireshark"),
            Pair("ការដោះស្រាយបញ្ហាបណ្តាញ Network Troubleshooting", "Comprehensive Network Diagnostics & Connectivity Fixes")
        )
        netTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 25
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.NETWORKING,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 27) DifficultyLevel.BEGINNER else if (num <= 32) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 4. CYBERSECURITY (Ch 37 - 48)
        val secTitles = listOf(
            Pair("ទិដ្ឋភាពទូទៅនៃមេរោគ Malware, Ransomware & Rootkits", "Malware Types: Viruses, Trojans, Ransomware & Spyware"),
            Pair("វិទ្យាសាស្ត្រអ៊ិនគ្រីប Cryptography (AES, RSA, Hashing)", "Modern Cryptography: Symmetric, Asymmetric & SHA-256"),
            Pair("ហេដ្ឋារចនាសម្ព័ន្ធ Public Key (PKI) និង Digital Certificates", "Public Key Infrastructure (PKI) & X.509 Certificates"),
            Pair("ប្រព័ន្ធការពារ Firewalls, IDS/IPS និង WAF", "Network Firewalls, Stateful Inspection & Next-Gen IDS/IPS"),
            Pair("ការផ្ទៀងផ្ទាត់អត្តសញ្ញាណ MFA, Passkeys, OAuth 2.0", "Identity Management: MFA, FIDO2 Passkeys & OAuth 2.0"),
            Pair("មូលដ្ឋានគ្រឹះ Ethical Hacking និងការវាយតម្លៃភាពងាយរងគ្រោះ", "Ethical Hacking Fundamentals & Vulnerability Scans"),
            Pair("ភាពងាយរងគ្រោះលើ Web Applications (OWASP Top 10)", "Web Security: SQL Injection, XSS, CSRF & OWASP Top 10"),
            Pair("វិស្វកម្មសង្គម Social Engineering & Phishing Prevention", "Social Engineering Vectors & Advanced Phishing Defense"),
            Pair("ប្រព័ន្ធគ្រប់គ្រងសុវត្ថិភាព SIEM និង Log Auditing", "SIEM Platforms, Threat Intelligence & Event Auditing"),
            Pair("ការឆ្លើយតបឧប្បត្តិហេតុ Incident Response & Digital Forensics", "Incident Response Life Cycle & Computer Forensics"),
            Pair("ស្ថាបត្យកម្ម Zero Trust Architecture (ZTA)", "Zero Trust Architecture: Never Trust, Always Verify"),
            Pair("ការអនុវត្តច្បាប់សន្តិសុខនិងស្តង់ដារ ISO 27001 / NIST", "Cybersecurity Governance, Compliance & NIST Framework")
        )
        secTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 37
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.CYBERSECURITY,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 40) DifficultyLevel.BEGINNER else if (num <= 44) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 5. PROGRAMMING (Ch 49 - 60)
        val progTitles = listOf(
            Pair("តក្កវិទ្យាកូដ Flowcharts និង Pseudocode", "Computational Thinking, Logic & Flowcharts"),
            Pair("ប្រភេទអថេរ Data Types និងការបែងចែក Memory (Stack/Heap)", "Variables, Primitive Types & Memory Stack vs Heap"),
            Pair("លក្ខខណ្ឌ Control Structures (if/else, switch) និង Loops", "Control Flow, Conditional Logic & Iteration Loops"),
            Pair("អនុគមន៍ Functions, Scope, Closures និង Recursion", "Modular Functions, Scope, Call Stack & Recursion"),
            Pair("គោលការណ៍ Object-Oriented Programming (OOP) ស៊ីជម្រៅ", "OOP Mastery: Encapsulation, Inheritance, Polymorphism"),
            Pair("ទិន្នន័យរចនាសម្ព័ន្ធមូលដ្ឋាន Arrays, Stacks, Queues", "Core Data Structures: Arrays, Linked Lists, Stacks, Queues"),
            Pair("ទិន្នន័យរចនាសម្ព័ន្ធកម្រិតខ្ពស់ Binary Trees, Graphs, Hash Maps", "Advanced Data Structures: Binary Trees, Hash Tables & Graphs"),
            Pair("ក្បួនដោះស្រាយតម្រៀប Sorting & Searching Algorithms", "Classic Algorithms: Quicksort, Mergesort, Binary Search"),
            Pair("ការគណនាប្រសិទ្ធភាពកូដ Big-O Complexity Analysis", "Time & Space Complexity with Big-O Notation"),
            Pair("គោលការណ៍កូដស្អាត Clean Code & Software Design Patterns", "Clean Code Best Practices & SOLID Design Patterns"),
            Pair("ការគ្រប់គ្រងកូដជាមួយ Git, Branches និង GitHub Workflows", "Version Control: Git Mastery, Pull Requests & GitHub"),
            Pair("បច្ចេកទេស Debugging, Logging និង Unit Testing (TDD)", "Code Debugging Strategies, Profiling & Unit Testing")
        )
        progTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 49
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.PROGRAMMING,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 52) DifficultyLevel.BEGINNER else if (num <= 56) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 40
                )
            )
        }

        // 6. WEB & APP DEV (Ch 61 - 72)
        val webTitles = listOf(
            Pair("រចនាសម្ព័ន្ធ Semantic HTML5 និង Accessibility (a11y)", "Modern HTML5 Semantic Tags & Web Accessibility"),
            Pair("រចនាស្ទីលទំនើប Modern CSS3, Flexbox និង CSS Grid", "CSS3 Layout Mastery: Flexbox, CSS Grid & Animations"),
            Pair("មូលដ្ឋានគ្រឹះ JavaScript (ES6+), Async/Await & Promises", "Modern JavaScript (ES6+), Event Loop & Promises"),
            Pair("ការបង្កើត UI ជាមួយ Frontend Frameworks (React & Next.js)", "Frontend Development with React & Next.js"),
            Pair("ការអភិវឌ្ឍកម្មវិធី Android ទំនើប (Kotlin + Jetpack Compose)", "Modern Android Mobile App Dev (Kotlin & Compose)"),
            Pair("ការកសាង Backend API ជាមួយ Node.js, Express & Python FastAPI", "Backend Services with Node.js & Python FastAPI"),
            Pair("ការរចនាប្រព័ន្ធ RESTful API និង Best Practices", "RESTful API Architectural Principles & Endpoints"),
            Pair("ការប្រាស្រ័យទាក់ទងទិន្នន័យ Real-time WebSockets & GraphQL", "Real-Time WebSockets & GraphQL Query Language"),
            Pair("គោលការណ៍រចនា Responsive Web Design & UI/UX Principles", "Responsive UI/UX Design System & Mobile Optimization"),
            Pair("ស្ថាបត្យកម្ម Microservices Architecture និង API Gateways", "Microservices Architecture & Event-Driven Systems"),
            Pair("ការវេចខ្ចប់កម្មវិធីជាមួយ Containers (Docker & Docker Compose)", "Containerization Mastery with Docker & Containers"),
            Pair("ប្រព័ន្ធស្វ័យប្រវត្តិកូដ CI/CD Pipelines & Cloud Deployment", "Automated CI/CD Workflows with GitHub Actions")
        )
        webTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 61
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.WEB_APP_DEV,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 63) DifficultyLevel.BEGINNER else if (num <= 68) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 7. DATABASES (Ch 73 - 84)
        val dbTitles = listOf(
            Pair("គំនិតមូលដ្ឋាន Relational Databases និង Schema Normalization", "Relational Database Concepts & 1NF/2NF/3NF Normalization"),
            Pair("ការសរសេរ SQL Queries កម្រិតខ្ពស់ (JOINs, GROUP BY, Subqueries)", "Advanced SQL: Inner/Outer JOINs & Complex Subqueries"),
            Pair("ការបង្កើនល្បឿន Database Indexing (B-Tree, Hash) & Query Plans", "Database Indexing Strategies (B-Trees) & EXPLAIN Plans"),
            Pair("ប្រតិបត្តិការទិន្នន័យ ACID Transactions និង Concurrency Locks", "ACID Guarantees, Transaction Isolation & Deadlocks"),
            Pair("ប្រព័ន្ធទិន្នន័យ NoSQL (Document MongoDB & Key-Value)", "NoSQL Architecture: MongoDB Document Databases"),
            Pair("ប្រព័ន្ធ Caching ល្បឿនខ្ពស់ជាមួយ Redis និង Memcached", "High-Performance In-Memory Caching with Redis"),
            Pair("ឃ្លាំងទិន្នន័យ Data Warehousing (OLAP vs OLTP) & Star Schema", "Data Warehousing: OLTP vs OLAP & Star Schema"),
            Pair("ការរៀបចំទិន្នន័យ ETL Pipelines និង Event Streams (Kafka)", "ETL Data Pipelines & Real-time Event Streaming with Kafka"),
            Pair("បច្ចេកវិទ្យាដំណើរការ Big Data (Apache Spark & Distributed Compute)", "Big Data Processing with Apache Spark & Distributed Engines"),
            Pair("បច្ចេកទេសចែកទិន្នន័យ Database Sharding, Replication & HA", "Database Replication, Read Replicas & Horizontal Sharding"),
            Pair("ប្រព័ន្ធទិន្នន័យ Vector Databases សម្រាប់ដំណើរការ AI", "Vector Databases (Pinecone, Chroma) for AI & Embeddings"),
            Pair("ការបម្រុងទុកទិន្នន័យ Disaster Recovery, Backup & Restore", "Database Backup Strategies, WAL Archives & Disaster Recovery")
        )
        dbTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 73
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.DATABASES,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 75) DifficultyLevel.BEGINNER else if (num <= 80) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 8. AI & DATA SCIENCE (Ch 85 - 96)
        val aiTitles = listOf(
            Pair("សេចក្តីផ្តើមអំពីបញ្ញាសិប្បនិម្មិត (AI) និង Machine Learning", "Introduction to Artificial Intelligence & Machine Learning"),
            Pair("ក្បួនដោះស្រាយ Supervised Learning (Regression & Classification)", "Supervised Learning: Linear Regression, Decision Trees"),
            Pair("ការរៀនដោយគ្មានការត្រួតពិនិត្យ Unsupervised Learning & Clustering", "Unsupervised Learning: K-Means Clustering & PCA"),
            Pair("បណ្តាញប្រសាទសិប្បនិម្មិត Deep Learning & Neural Networks", "Neural Networks Fundamentals & Backpropagation"),
            Pair("ដំណើរការភាសាធម្មជាតិ Natural Language Processing (NLP)", "Natural Language Processing (NLP), Tokenization & TF-IDF"),
            Pair("ការមើលឃើញតាមកុំព្យូទ័រ Computer Vision & CNNs", "Computer Vision, Image Processing & Convolutional Networks"),
            Pair("ស្ថាបត្យកម្ម Transformer និង Large Language Models (LLMs)", "Transformer Architecture & Large Language Models (LLMs)"),
            Pair("សិល្បៈនៃការបង្កើតសំណួរ Prompt Engineering សម្រាប់ AI", "Mastering Prompt Engineering for Developers"),
            Pair("ប្រព័ន្ធទាញយកព័ត៌មាន Retrieval-Augmented Generation (RAG)", "RAG Systems: Connecting LLMs with Custom Knowledge"),
            Pair("ក្រមសីលធម៌ AI, Bias, សុវត្ថិភាព និង Responsible AI", "AI Ethics, Bias Mitigation & Responsible AI Frameworks"),
            Pair("ការបង្វឹកនិង Fine-Tuning គំរូ AI ជាក់លាក់", "Model Fine-Tuning Techniques (LoRA, QLoRA) & Datasets"),
            Pair("ការដាក់ឱ្យប្រើប្រាស់គំរូ AI Model Deployment & MLOps", "MLOps: Deploying AI Models to Production APIs")
        )
        aiTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 85
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.AI_DATA_SCIENCE,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 88) DifficultyLevel.BEGINNER else if (num <= 92) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 9. CLOUD & DEVOPS (Ch 97 - 108)
        val cloudTitles = listOf(
            Pair("មូលដ្ឋានគ្រឹះ Cloud Computing (IaaS, PaaS, SaaS, Hybrid)", "Cloud Computing Fundamentals: IaaS, PaaS & SaaS"),
            Pair("សេវាកម្មស្នូល Amazon Web Services (AWS EC2, S3, VPC)", "Amazon Web Services (AWS) Core Architecture"),
            Pair("ហេដ្ឋារចនាសម្ព័ន្ធ Google Cloud Platform (Compute Engine, GCS)", "Google Cloud Platform (GCP) Fundamentals"),
            Pair("ប្រព័ន្ធសហគ្រាស Microsoft Azure Architecture & Entra ID", "Microsoft Azure Cloud Services & Enterprise Identity"),
            Pair("ការគណនាគ្មានម៉ាស៊ីនបម្រើ Serverless (AWS Lambda, Cloud Functions)", "Serverless Computing & Event-Driven Architecture"),
            Pair("ការគ្រប់គ្រង Container ខ្នាតធំជាមួយ Kubernetes (K8s)", "Kubernetes Orchestration: Pods, Services & Ingress"),
            Pair("ការបង្កើតហេដ្ឋារចនាសម្ព័ន្ធតាមកូដ Infrastructure as Code (Terraform)", "Infrastructure as Code (IaC) with Terraform"),
            Pair("វប្បធម៌ DevOps និង Site Reliability Engineering (SRE)", "DevOps Principles & Site Reliability Engineering (SRE)"),
            Pair("ប្រព័ន្ធតាមដាននិងត្រួតពិនិត្យ Monitoring (Prometheus & Grafana)", "Observability: Metrics, Traces & Logs with Prometheus/Grafana"),
            Pair("យុទ្ធសាស្ត្រ Cloud Storage និង Content Delivery Networks (CDNs)", "Global Cloud Storage, Caching & Edge CDNs"),
            Pair("ផែនការសង្គ្រោះគ្រោះមហន្តរាយ Cloud Disaster Recovery & High Availability", "High Availability, Multi-Region Failover & Cloud DR"),
            Pair("ការគ្រប់គ្រង Multi-Cloud និងការសន្សំថវិកា Cloud FinOps", "Multi-Cloud Strategy & Cloud Cost Optimization (FinOps)")
        )
        cloudTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 97
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.CLOUD_DEVOPS,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 100) DifficultyLevel.BEGINNER else if (num <= 104) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 45
                )
            )
        }

        // 10. IT SUPPORT & OFFICE (Ch 109 - 120)
        val supportTitles = listOf(
            Pair("ការរៀបចំកន្លែងធ្វើការ Workspace Ergonomics & Cable Setup", "Computer Workspace Setup & Ergonomics Standards"),
            Pair("ជំនាញ Microsoft Word & Docs កម្រិតខ្ពស់សម្រាប់របាយការណ៍", "Advanced Document Formatting & Collaborative Docs"),
            Pair("Microsoft Excel & Sheets (Formulas, VLOOKUP, XLOOKUP)", "Excel & Sheets: Advanced Formulas, Logic & Pivot Tables"),
            Pair("ការរចនាបទបង្ហាញប្រកបដោយវិជ្ជាជីវៈ (PowerPoint / Slides)", "Professional Technical Presentations & Visual Storytelling"),
            Pair("សារអេឡិចត្រូនិច Email Etiquette, Calendar & IT Workspace", "Enterprise Email Protocols & Workspace Collaboration"),
            Pair("វិធីសាស្ត្រ IT Help Desk, Ticketing & Remote Support Tools", "IT Help Desk Methodologies & Remote Diagnostics"),
            Pair("ការដំឡើងនិង Upgrade គ្រឿងបន្លាស់កុំព្យូទ័រយួរដៃ Laptop/Desktop", "PC & Laptop Component Upgrades: RAM, SSD & Battery"),
            Pair("ការដោះស្រាយបញ្ហាគាំងម៉ាស៊ីននិង Blue Screen (BSOD)", "Blue Screen (BSOD) Root Cause Diagnosis & Repair"),
            Pair("បច្ចេកទេសសង្គ្រោះឯកសារទិន្នន័យដែលបាត់ Data Recovery", "File Rescue, Partition Recovery & Storage Repair"),
            Pair("ការដោះស្រាយបញ្ហាដាច់អ៊ីនធឺណិត និង WiFi Troubleshooting", "Network Failure Troubleshooting & Wi-Fi Deadzone Fixes"),
            Pair("ការថែទាំនិងជួសជុល Printer, Scanner និង Peripherals", "Printers, Plotters & Peripheral Diagnostics & Repair"),
            Pair("ការគ្រប់គ្រងទ្រព្យសម្បត្តិ IT Asset Management & Security Policies", "Enterprise IT Asset Inventory, Licensing & Security Hygiene")
        )
        supportTitles.forEachIndexed { i, (kh, en) ->
            val num = i + 109
            list.add(
                createChapter(
                    id = num,
                    track = ComputerTrack.IT_SUPPORT,
                    num = num,
                    kh = kh,
                    en = en,
                    diff = if (num <= 112) DifficultyLevel.BEGINNER else if (num <= 116) DifficultyLevel.INTERMEDIATE else DifficultyLevel.ADVANCED,
                    mins = 35
                )
            )
        }

        return list
    }

    private fun createChapter(
        id: Int,
        track: ComputerTrack,
        num: Int,
        kh: String,
        en: String,
        diff: DifficultyLevel,
        mins: Int
    ): Chapter {
        return Chapter(
            id = id,
            track = track,
            chapterNumber = num,
            titleKhmer = kh,
            titleEnglish = en,
            summaryKhmer = "ស្វែងយល់លម្អិតអំពី $kh ជាមួយទ្រឹស្តី គំរូអនុវត្តជាក់ស្តែង និងដំណោះស្រាយបញ្ហា។",
            summaryEnglish = "Comprehensive guide and hands-on breakdown for $en with real-world computer applications.",
            difficulty = diff,
            estimatedMinutes = mins,
            keyConcepts = listOf("ស្ថាបត្យកម្មប្រព័ន្ធ (Architecture)", "ទ្រឹស្តីស្នូល (Core Theory)", "ការអនុវត្ត (Hands-on)", "ការដោះស្រាយបញ្ហា (Troubleshooting)"),
            lessonsCount = 3
        )
    }

    fun getChapterById(id: Int): Chapter? {
        return allChapters.firstOrNull { it.id == id }
    }

    fun getChaptersForTrack(track: ComputerTrack): List<Chapter> {
        return allChapters.filter { it.track == track }
    }
}
