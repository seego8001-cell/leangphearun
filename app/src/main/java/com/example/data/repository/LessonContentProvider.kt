package com.example.data.repository

import com.example.data.model.CalloutType
import com.example.data.model.Lesson
import com.example.data.model.LessonSection

object LessonContentProvider {

    fun getLessonsForChapter(chapterId: Int): List<Lesson> {
        val chapter = CurriculumCatalog.getChapterById(chapterId)
            ?: return emptyList()

        return when (chapterId) {
            1 -> listOf(
                Lesson(
                    id = "ch1_les1",
                    chapterId = 1,
                    lessonNumber = 1,
                    titleKhmer = "មូលដ្ឋានគ្រឹះស្ថាបត្យកម្ម CPU និងវដ្តដំណើរការ (Fetch-Decode-Execute)",
                    titleEnglish = "CPU Architecture Fundamentals & Machine Cycle",
                    durationMinutes = 15,
                    overviewKhmer = "ស្វែងយល់ពីរបៀបដែលបន្ទះឈីប CPU ដំណើរការបញ្ជាកូដ និងគណនាលេខក្នុងកម្រិត hardware។",
                    overviewEnglish = "Understand how the CPU fetches, decodes, and executes instructions at the silicon level.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "១. វដ្តនៃការគណនា Machine Cycle",
                            titleEnglish = "1. The Machine Cycle",
                            contentKhmer = "CPU ដំណើរការតាមដំណាក់កាលស្នូល ៤ យ៉ាងរាប់ពាន់លានដងក្នុងមួយវិនាទី (Clock Speed):\n• Fetch: ទាញយកកូដបញ្ជាពី RAM ចូលទៅកាន់ Instruction Register\n• Decode: បកប្រែ binary code ទៅជាសញ្ញាបញ្ជាអគ្គិសនី\n• Execute: អង្គគណនា ALU ធ្វើប្រតិបត្តិការគណនាលេខ ឬប្រៀបធៀប\n• Store (Writeback): រក្សាទុកលទ្ធផលត្រឡប់ទៅក្នុង Register ឬ Cache។",
                            contentEnglish = "The machine cycle consists of 4 distinct steps:\n• Fetch: Retrieves instruction from RAM into the Instruction Register.\n• Decode: Control Unit decodes binary into control signals.\n• Execute: ALU performs calculation or logic test.\n• Store: Writes result back to registers or cache memory.",
                            calloutType = CalloutType.INFO,
                            calloutTextKhmer = "ល្បឿន 4.0 GHz មានន័យថា CPU អាចដំណើរការ 4,000,000,000 វដ្តក្នុងមួយវិនាទី!",
                            calloutTextEnglish = "A 4.0 GHz clock speed means 4 billion clock cycles executed per second!"
                        ),
                        LessonSection(
                            titleKhmer = "២. សមាសភាគខាងក្នុង CPU (ALU, CU, Registers)",
                            titleEnglish = "2. CPU Internal Components",
                            contentKhmer = "ខាងក្នុងបន្ទះឈីបមាន:\n• ALU (Arithmetic Logic Unit): អង្គគណនាលេខនព្វន្ត\n• Control Unit (CU): នាយកចាត់ចែងចរន្តទិន្នន័យ\n• Program Counter (PC): ចង្អុលទៅអាសយដ្ឋានកូដបន្ទាប់\n• Instruction Register (IR): កាន់កាប់កូដកំពុងដំណើរការ។",
                            contentEnglish = "Internal blocks include:\n• ALU: Performs math and boolean logic.\n• Control Unit: Controls data pathways.\n• Program Counter: Points to memory address of next instruction.\n• Instruction Register: Holds current executing instruction.",
                            calloutType = CalloutType.HARDWARE_SPEC,
                            calloutTextKhmer = "Registers គឺជាអង្គចងចាំដែលមានល្បឿនលឿនបំផុតក្នុងលោក (< 1 Nanosecond)។",
                            calloutTextEnglish = "Registers are the fastest memory in computing, operating under 1 nanosecond."
                        )
                    ),
                    practicalCode = """
                        // Assembly x86_64 concept
                        mov eax, 5       ; Load number 5 into register EAX
                        mov ebx, 10      ; Load number 10 into register EBX
                        add eax, ebx     ; ALU adds EBX to EAX (Result = 15)
                    """.trimIndent(),
                    codeLanguage = "x86_64 Assembly",
                    keyTakeawaysKhmer = listOf(
                        "CPU ដំណើរការតាមវដ្ត Fetch-Decode-Execute",
                        "ALU គណនាលេខ និងប្រៀបធៀបតក្កវិទ្យា",
                        "Registers លឿនជាង RAM រាប់ពាន់ដង"
                    ),
                    keyTakeawaysEnglish = listOf(
                        "CPU follows the Fetch-Decode-Execute machine cycle",
                        "ALU computes arithmetic and logic operations",
                        "Registers are orders of magnitude faster than system RAM"
                    )
                ),
                Lesson(
                    id = "ch1_les2",
                    chapterId = 1,
                    lessonNumber = 2,
                    titleKhmer = "ឋានានុក្រម Cache Memory (L1, L2, L3) និង Cache Hit/Miss",
                    titleEnglish = "Cache Hierarchy (L1, L2, L3) and Latency",
                    durationMinutes = 20,
                    overviewKhmer = "មូលហេតុដែល CPU ត្រូវការ Cache និងផលប៉ះពាល់នៃ Latency លើល្បឿនដំណើរការ។",
                    overviewEnglish = "Why processors require multi-level on-die cache to bridge the CPU-RAM speed gap.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "ឋានានុក្រម Cache L1, L2, L3",
                            titleEnglish = "L1, L2, L3 Cache Levels",
                            contentKhmer = "ដោយសារ RAM យឺតជាង CPU ប្រមាណ ២០០ ដង បន្ទះឈីបបានបំពាក់ Cache:\n• L1 Cache: ល្បឿនលឿនបំផុត (ប្រហែល 0.5 - 1 ns) ចែកជា L1i (Instruction) និង L1d (Data)\n• L2 Cache: លឿនបង្គួរ (ប្រហែល 3 - 5 ns) ផ្ទុកប្រហែល 512KB - 2MB ក្នុងមួយ Core\n• L3 Cache: ទំហំធំ (16MB - 96MB+) ចែករំលែករវាងគ្រប់ Cores ទាំងអស់ (Latency ~10-15 ns)។",
                            contentEnglish = "Because RAM is roughly 200x slower than CPU execution, on-die SRAM cache bridges the bottleneck:\n• L1: Fastest (~0.5-1ns), split into L1i and L1d per core\n• L2: Fast (~3-5ns), typically dedicated per core\n• L3: Large pool (16-96MB+), shared across all CPU cores.",
                            calloutType = CalloutType.PRO_TIP,
                            calloutTextKhmer = "Cache Hit មានន័យថាទិន្នន័យមានស្រាប់ក្នុង Cache ធ្វើឱ្យកុំព្យូទ័រដំណើរការលឿនអតិបរមា។",
                            calloutTextEnglish = "A Cache Hit prevents CPU stalling by avoiding slow trips to system DRAM."
                        )
                    ),
                    practicalCode = """
                        // C++ Cache-friendly iteration (Row-Major)
                        for (int i = 0; i < ROWS; i++) {
                            for (int j = 0; j < COLS; j++) {
                                matrix[i][j] *= 2; // High Cache Hit Rate!
                            }
                        }
                    """.trimIndent(),
                    codeLanguage = "C++",
                    keyTakeawaysKhmer = listOf("L1 លឿនបំផុត L3 ធំបំផុត", "ជៀសវាង Cache Miss ជួយឱ្យកម្មវិធីលឿន"),
                    keyTakeawaysEnglish = listOf("L1 is fastest, L3 is largest shared pool", "Cache locality boosts real software performance")
                ),
                Lesson(
                    id = "ch1_les3",
                    chapterId = 1,
                    lessonNumber = 3,
                    titleKhmer = "ស្ថាបត្យកម្មទំនើប Multi-Core, Hyper-Threading និង Instruction Sets (x86 vs ARM)",
                    titleEnglish = "Multi-Core, Hyper-Threading & x86 vs ARM",
                    durationMinutes = 20,
                    overviewKhmer = "ការប្រៀបធៀបស្ថាបត្យកម្ម CISC (x86-64) និង RISC (ARM) និងបច្ចេកវិទ្យា Cores សម័យថ្មី។",
                    overviewEnglish = "Compare CISC vs RISC, simultaneous multithreading, and power efficiency.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "x86-64 ទល់នឹង ARM64",
                            titleEnglish = "x86-64 vs ARM64 Architecture",
                            contentKhmer = "• x86-64 (Intel, AMD): ប្រើប្រព័ន្ធ CISC សម្បូរទៅដោយ instruction ស្មុគស្មាញ ស័ក្តិសមសម្រាប់ Desktop & Servers\n• ARM64 (Apple Silicon, Qualcomm): ប្រើប្រព័ន្ធ RISC ស៊ីភ្លើងតិច ប្រសិទ្ធភាពខ្ពស់ ស័ក្តិសមសម្រាប់ទូរស័ព្ទ និង Laptops សម័យថ្មី\n• P-Cores vs E-Cores: បែងចែកបន្ទះឈីបជា Performance Cores និង Efficiency Cores ដើម្បីសន្សំសំចៃថាមពល។",
                            contentEnglish = "• x86-64: CISC design, complex instructions, dominant in desktops/servers.\n• ARM64: RISC design, reduced instruction set, extreme power efficiency.\n• Hybrid Cores: P-cores handle heavy bursts, E-cores handle background tasks.",
                            calloutType = CalloutType.INFO,
                            calloutTextKhmer = "Apple M-Series និង Intel Core Ultra សុទ្ធតែប្រើ Hybrid Architecture!",
                            calloutTextEnglish = "Modern chips from Intel, Apple, and AMD utilize heterogeneous core topologies."
                        )
                    ),
                    practicalCode = """
                        # Linux command to inspect CPU topology
                        lscpu
                        # Or read CPU details directly:
                        cat /proc/cpuinfo | grep 'model name'
                    """.trimIndent(),
                    codeLanguage = "Bash CLI",
                    keyTakeawaysKhmer = listOf("ARM សន្សំភ្លើងខ្ពស់", "Hyper-Threading អនុញ្ញាតឱ្យ Core មួយដំណើរការ 2 Threads ដំណាលគ្នា"),
                    keyTakeawaysEnglish = listOf("ARM prioritizes efficiency per watt", "SMT/Hyper-Threading runs two threads per physical core")
                )
            )
            13 -> listOf(
                Lesson(
                    id = "ch13_les1",
                    chapterId = 13,
                    lessonNumber = 1,
                    titleKhmer = "ស្ថាបត្យកម្ម Kernel និង System Calls",
                    titleEnglish = "Operating System Kernel & System Calls",
                    durationMinutes = 15,
                    overviewKhmer = "ស្វែងយល់ពីបេះដូងនៃប្រព័ន្ធប្រតិបត្តិការ និងការទំនាក់ទំនងរវាង User Space និង Kernel Space។",
                    overviewEnglish = "Dive into OS kernel design, privilege rings, and how programs request hardware resources.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "User Mode ទល់នឹង Kernel Mode",
                            titleEnglish = "User Mode vs Kernel Mode (Ring 3 vs Ring 0)",
                            contentKhmer = "CPU មាន Hardware Protection Rings:\n• Ring 3 (User Space): កម្មវិធីទូទៅ (Chrome, Word, Games) ដំណើរការនៅទីនេះ គ្មានសិទ្ធិប៉ះពាល់ hardware ដោយផ្ទាល់ឡើយ\n• Ring 0 (Kernel Space): Kernel មានសិទ្ធិពេញលេញលើ memory, disk, និង CPU\n• System Call (Syscall): ជាច្រកផ្លូវផ្លូវការដែលកម្មវិធីសុំជំនួយពី Kernel (ឧ. `open()`, `read()`, `write()`, `fork()`)។",
                            contentEnglish = "Hardware privilege isolation protects the computer:\n• Ring 3: User apps run with restricted privileges.\n• Ring 0: Kernel mode with raw hardware execution rights.\n• System Calls: Controlled gatekeepers enabling safe resource allocation.",
                            calloutType = CalloutType.WARNING,
                            calloutTextKhmer = "បើសិនកម្មវិធីព្យាយាមកែ Memory ក្រៅសិទ្ធិ ប្រព័ន្ធនឹងបង្កើត Segmentation Fault (Crash) ភ្លាមៗ!",
                            calloutTextEnglish = "Illegal memory writes in user space trigger immediate SIGSEGV crashes."
                        )
                    ),
                    practicalCode = """
                        // C System Call example
                        #include <unistd.h>
                        int main() {
                            write(1, "Hello from Kernel Syscall!\n", 27);
                            return 0;
                        }
                    """.trimIndent(),
                    codeLanguage = "C",
                    keyTakeawaysKhmer = listOf("Kernel គ្រប់គ្រង Hardware ទាំងអស់", "Syscalls ធានាសុវត្ថិភាពរវាងកម្មវិធីនិងគ្រឿងម៉ាស៊ីន"),
                    keyTakeawaysEnglish = listOf("The kernel mediates all hardware interactions", "Syscalls provide a secure API layer")
                ),
                Lesson(
                    id = "ch13_les2",
                    chapterId = 13,
                    lessonNumber = 2,
                    titleKhmer = "Monolithic Kernels ទល់នឹង Microkernels",
                    titleEnglish = "Monolithic vs Microkernel Design",
                    durationMinutes = 15,
                    overviewKhmer = "ការប្រៀបធៀបស្ថាបត្យកម្ម Linux (Monolithic) និង QNX/Fuchsia (Microkernel)។",
                    overviewEnglish = "Comparing monolithic simplicity and raw speed with microkernel reliability and modularity.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "ទស្សនវិជ្ជាស្ថាបត្យកម្ម",
                            titleEnglish = "Architectural Philosophy",
                            contentKhmer = "• Monolithic Kernel (Linux): រាល់ Drivers, File Systems, Memory Manager ដំណើរការក្នុង Ring 0 ទាំងអស់ ធ្វើឱ្យល្បឿនលឿនបំផុត\n• Microkernel (MINIX, seL4): ដាក់តែ Core Scheduler ក្នុង Ring 0 ចំណែក Drivers និង File Systems ដំណើរការក្នុង User Space ធ្វើឱ្យប្រព័ន្ធមិនងាយ Crash\n• Hybrid Kernel (Windows NT, macOS XNU): បន្សំរវាងល្បឿន និងសុវត្ថិភាព។",
                            contentEnglish = "• Monolithic: Everything runs in privileged ring 0 for maximum throughput.\n• Microkernel: Minimal core in ring 0, drivers run as isolated user processes.\n• Hybrid: Balanced approach combining monolithic speed with modular services.",
                            calloutType = CalloutType.INFO,
                            calloutTextKhmer = "Windows NT ប្រើ Hybrid Kernel ចំណែក Linux ប្រើ Monolithic Kernel ជាមួយ Dynamic Modules។",
                            calloutTextEnglish = "Windows NT uses a hybrid model; Linux is monolithic with loadable modules."
                        )
                    ),
                    practicalCode = """
                        # Inspect loaded Linux kernel modules
                        lsmod
                        # Load a new driver dynamically:
                        sudo modprobe usb-storage
                    """.trimIndent(),
                    codeLanguage = "Linux Shell",
                    keyTakeawaysKhmer = listOf("Linux ប្រើ Monolithic Kernel", "Microkernel មានស្ថេរភាពខ្ពស់ជាង"),
                    keyTakeawaysEnglish = listOf("Linux is monolithic with loadable kernel modules", "Microkernels isolate driver crashes")
                ),
                Lesson(
                    id = "ch13_les3",
                    chapterId = 13,
                    lessonNumber = 3,
                    titleKhmer = "ការដោះស្រាយបញ្ហា Kernel Panics និង System Crashes",
                    titleEnglish = "Kernel Diagnostics & Crash Dumps",
                    durationMinutes = 20,
                    overviewKhmer = "របៀបពិនិត្យ Logs និង Memory Dumps ពេលប្រព័ន្ធប្រតិបត្តិការជួបបញ្ហាធ្ងន់ធ្ងរ។",
                    overviewEnglish = "Methods to inspect dmesg, Windows Event Viewer, and analyze kernel crash logs.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "ការពិនិត្យ Kernel Logs",
                            titleEnglish = "Inspecting Kernel Message Buffers",
                            contentKhmer = "នៅពេលកុំព្យូទ័រគាំង ឬ Driver មានបញ្ហា:\n• លើ Linux: ប្រើពាក្យបញ្ជា `dmesg -T` ឬ `journalctl -k` ដើម្បីមើលសារពី Kernel ដោយផ្ទាល់\n• លើ Windows: ប្រើ Memory Dump (`%SystemRoot%\\MEMORY.DMP`) និង WinDbg ដើម្បីមើល stack trace។",
                            contentEnglish = "When an OS hangs or panic occurs:\n• Linux: `dmesg -T` outputs kernel messages with human readable timestamps.\n• Windows: Analyze minidumps via WinDbg or BlueScreenView.",
                            calloutType = CalloutType.PRO_TIP,
                            calloutTextKhmer = "ភាគច្រើន ៨០% នៃ Kernel Panics បណ្តាលមកពី Driver មិនត្រូវគ្នា ឬ Hardware RAM ខូច!",
                            calloutTextEnglish = "Over 80% of kernel crashes originate from third-party faulty drivers or failing DRAM."
                        )
                    ),
                    practicalCode = """
                        # View live kernel ring buffer events
                        sudo dmesg -wH
                    """.trimIndent(),
                    codeLanguage = "Bash",
                    keyTakeawaysKhmer = listOf("dmesg គឺជាឧបករណ៍ចម្បងក្នុងការរកកំហុស Kernel", "Memory Dumps ជួយបង្ហាញ Driver ណាដែលបង្កបញ្ហា"),
                    keyTakeawaysEnglish = listOf("dmesg reveals real-time kernel warnings", "Memory dumps isolate crash culprits")
                )
            )
            25 -> listOf(
                Lesson(
                    id = "ch25_les1",
                    chapterId = 25,
                    lessonNumber = 1,
                    titleKhmer = "ស្វែងយល់ស៊ីជម្រៅពីគំរូ 7-Layer OSI Model",
                    titleEnglish = "Deep Dive into the 7-Layer OSI Model",
                    durationMinutes = 20,
                    overviewKhmer = "ដំណើរការនៃការវេចខ្ចប់ទិន្នន័យ (Encapsulation) ពី Layer 7 ដល់ Layer 1 នៃបណ្តាញ។",
                    overviewEnglish = "Understand data encapsulation, protocols, and headers from Layer 7 Application to Layer 1 Physical.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "ជាន់ទាំង ៧ នៃ OSI Model",
                            titleEnglish = "The Seven Architectural Layers",
                            contentKhmer = "1. Physical (Bits, Cables, RJ45, Fiber Optic)\n2. Data Link (Frames, MAC Addresses, Switch)\n3. Network (Packets, IP Addresses, Router)\n4. Transport (Segments, TCP Ports, UDP Ports)\n5. Session (Sessions, RPC)\n6. Presentation (Encryption, Compression, JPEG, SSL)\n7. Application (HTTP, DNS, SSH, SMTP)។",
                            contentEnglish = "1. Physical: Raw bit transmission over physical media\n2. Data Link: Node-to-node frame delivery via MAC addresses\n3. Network: Logical packet routing across subnets via IP\n4. Transport: End-to-end reliability (TCP) or speed (UDP)\n5. Session: Connection persistence and synchronization\n6. Presentation: Translation, SSL/TLS encryption, compression\n7. Application: User-facing network protocols like HTTP and DNS.",
                            calloutType = CalloutType.INFO,
                            calloutTextKhmer = "ពាក្យកាត់សម្រាប់ទន្ទេញ: 'Please Do Not Throw Sausage Pizza Away'",
                            calloutTextEnglish = "Mnemonic: 'Please Do Not Throw Sausage Pizza Away' (Layer 1 to 7)."
                        )
                    ),
                    practicalCode = """
                        # Trace network path hop by hop
                        traceroute 8.8.8.8
                        # On Windows:
                        tracert 1.1.1.1
                    """.trimIndent(),
                    codeLanguage = "CLI Commands",
                    keyTakeawaysKhmer = listOf("OSI Model បែងចែកការងារបណ្តាញជា ៧ ជាន់", "Encapsulation បន្ថែម Headers តាមជាន់នីមួយៗ"),
                    keyTakeawaysEnglish = listOf("OSI standardizes network modularity into 7 layers", "Encapsulation wraps payloads with layer-specific headers")
                ),
                Lesson(
                    id = "ch25_les2",
                    chapterId = 25,
                    lessonNumber = 2,
                    titleKhmer = "គំរូ TCP/IP និងដំណើរការ 3-Way Handshake",
                    titleEnglish = "TCP/IP Protocol Suite & 3-Way Handshake",
                    durationMinutes = 15,
                    overviewKhmer = "របៀបដែលកុំព្យូទ័របង្កើតការតភ្ជាប់ដែលមានទំនុកចិត្តខ្ពស់មុនពេលផ្ញើទិន្នន័យ។",
                    overviewEnglish = "How TCP SYN, SYN-ACK, and ACK packets establish stateful network connections.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "ជំហានទាំង ៣ នៃ TCP Handshake",
                            titleEnglish = "TCP 3-Way Handshake Workflow",
                            contentKhmer = "1. Client ផ្ញើកញ្ចប់ **SYN** (Synchronize) ជាមួយ Sequence Number ដំបូង\n2. Server ឆ្លើយតបវិញដោយ **SYN-ACK** បញ្ជាក់ការទទួលនិងផ្ញើលេខ sequence របស់ខ្លួន\n3. Client ផ្ញើ **ACK** (Acknowledge) ត្រឡប់ទៅវិញ បន្ទាប់មកការផ្ទេរទិន្នន័យចាប់ផ្តើម!",
                            contentEnglish = "1. Client sends SYN (Sequence X)\n2. Server replies with SYN-ACK (Seq Y, Ack X+1)\n3. Client confirms with ACK (Ack Y+1). Connection is established!",
                            calloutType = CalloutType.PRO_TIP,
                            calloutTextKhmer = "UDP មិនមាន 3-Way Handshake ឡើយ ដូច្នេះវាលឿនជាង តែអាចបាត់បង់ទិន្នន័យ (Packet Loss)។",
                            calloutTextEnglish = "UDP skips handshake connection overhead, making it ideal for streaming and gaming."
                        )
                    ),
                    practicalCode = """
                        # View active TCP connections and listening ports
                        netstat -tuln
                        # Or modern Linux tool:
                        ss -tulpn
                    """.trimIndent(),
                    codeLanguage = "Terminal",
                    keyTakeawaysKhmer = listOf("TCP ធានាការទទួលទិន្នន័យ ១០០%", "SYN -> SYN-ACK -> ACK បង្កើតការតភ្ជាប់"),
                    keyTakeawaysEnglish = listOf("TCP guarantees ordered and verified delivery", "3-way handshake initializes state synchronization")
                ),
                Lesson(
                    id = "ch25_les3",
                    chapterId = 25,
                    lessonNumber = 3,
                    titleKhmer = "ការអនុវត្តត្រួតពិនិត្យ Packets ជាមួយ Wireshark",
                    titleEnglish = "Capturing and Inspecting Traffic with Wireshark",
                    durationMinutes = 20,
                    overviewKhmer = "រៀនមើលកញ្ចប់ទិន្នន័យពិតប្រាកដដែលហោះហើរលើខ្សែបណ្តាញ។",
                    overviewEnglish = "Capture live frames, inspect protocol flags, and isolate network anomalies.",
                    sections = listOf(
                        LessonSection(
                            titleKhmer = "តម្រង Wireshark Display Filters សំខាន់ៗ",
                            titleEnglish = "Crucial Display Filters",
                            contentKhmer = "• `ip.addr == 192.168.1.1` (ច្រោះមើលតែ IP ជាក់លាក់)\n• `tcp.port == 443` (មើលតែចរាចរណ៍ HTTPS)\n• `dns` (មើលសំណួរ DNS query និងចម្លើយ response)\n• `http.request.method == \"POST\"` (ស្វែងរកទិន្នន័យដែលផ្ញើទៅកាន់ Web Server)។",
                            contentEnglish = "Filter expressions allow targeting exact traffic streams:\n• `ip.addr == 10.0.0.1`\n• `tcp.flags.syn == 1 && tcp.flags.ack == 0` (Find connection attempts)\n• `dns.flags.response == 1`",
                            calloutType = CalloutType.WARNING,
                            calloutTextKhmer = "កុំ Sniff បណ្តាញសាធារណៈដោយគ្មានការអនុញ្ញាត ព្រោះវាជាទង្វើខុសច្បាប់!",
                            calloutTextEnglish = "Packet sniffing on unauthorized third-party networks violates cybersecurity laws."
                        )
                    ),
                    practicalCode = """
                        # Capture HTTP/HTTPS traffic on interface eth0 using tcpdump
                        sudo tcpdump -i eth0 -n "port 80 or port 443" -c 10
                    """.trimIndent(),
                    codeLanguage = "Bash",
                    keyTakeawaysKhmer = listOf("Wireshark បង្ហាញកញ្ចប់ទិន្នន័យលម្អិតដល់កម្រិត Bit", "Display Filters ជួយស្វែងរកកំហុសបណ្តាញយ៉ាងរហ័ស"),
                    keyTakeawaysEnglish = listOf("Packet analyzers reveal full packet headers", "Display filters accelerate network troubleshooting")
                )
            )
            else -> generateStandardLessonsForChapter(chapter)
        }
    }

    private fun generateStandardLessonsForChapter(chapter: com.example.data.model.Chapter): List<Lesson> {
        return listOf(
            Lesson(
                id = "ch${chapter.id}_les1",
                chapterId = chapter.id,
                lessonNumber = 1,
                titleKhmer = "១. ទ្រឹស្តីស្នូលនិងគោលការណ៍គ្រឹះនៃ ${chapter.titleKhmer}",
                titleEnglish = "1. Core Principles & Architecture of ${chapter.titleEnglish}",
                durationMinutes = 15,
                overviewKhmer = "សិក្សាស៊ីជម្រៅលើគោលការណ៍គ្រឹះ និងស្ថាបត្យកម្មប្រព័ន្ធនៃ ${chapter.titleKhmer}។",
                overviewEnglish = "Comprehensive theoretical fundamentals and system design for ${chapter.titleEnglish}.",
                sections = listOf(
                    LessonSection(
                        titleKhmer = "សេចក្តីផ្តើមនិងសារៈសំខាន់ក្នុងវិស័យ IT",
                        titleEnglish = "Introduction & IT Significance",
                        contentKhmer = "${chapter.titleKhmer} គឺជាជំនាញនិងបច្ចេកវិទ្យាស្នូលក្នុងវិស័យ IT ទំនើប។ វាដើរតួយ៉ាងសំខាន់ក្នុងការបង្កើតនូវប្រព័ន្ធដែលមានស្ថេរភាព ប្រសិទ្ធភាព និងសុវត្ថិភាពខ្ពស់។\n\nចំណុចសំខាន់រួមមាន:\n• ការយល់ដឹងពីស្ថាបត្យកម្ម និងលំហូរដំណើរការ (Workflow)\n• ស្តង់ដារបច្ចេកទេស និងពិធីការដែលទទួលស្គាល់ទូទាំងសកលលោក\n• វិធីសាស្ត្រអនុវត្តល្អបំផុត (Best Practices) សម្រាប់វិស្វករកុំព្យូទ័រ។",
                        contentEnglish = "${chapter.titleEnglish} represents a foundational pillar of modern computing systems. Mastering these concepts provides crucial skills for systems architecture, troubleshooting, and enterprise-grade performance.",
                        calloutType = CalloutType.INFO,
                        calloutTextKhmer = "ការយល់ដឹងពីមូលដ្ឋានគ្រឹះនេះ នឹងជួយឱ្យប្អូនងាយស្រួលយល់មេរៀនបន្តបន្ទាប់!",
                        calloutTextEnglish = "Solid foundational knowledge enables rapid mastery of advanced computing topics."
                    )
                ),
                practicalCode = """
                    # System verification command for ${chapter.track.titleEnglish}
                    systeminfo | findstr /C:"System"
                    # Or Linux diagnostic check:
                    uname -a && uptime
                """.trimIndent(),
                codeLanguage = "CLI Commands",
                keyTakeawaysKhmer = listOf("យល់ច្បាស់ពីស្ថាបត្យកម្មស្នូល", "អនុវត្តតាមស្តង់ដារបច្ចេកវិទ្យា"),
                keyTakeawaysEnglish = listOf("Master core system design principles", "Comply with industry engineering standards")
            ),
            Lesson(
                id = "ch${chapter.id}_les2",
                chapterId = chapter.id,
                lessonNumber = 2,
                titleKhmer = "២. ការអនុវត្តជាក់ស្តែង និងឧទាហរណ៍ Real-World Use Cases",
                titleEnglish = "2. Practical Implementation & Real-World Case Studies",
                durationMinutes = 20,
                overviewKhmer = "អនុវត្តផ្ទាល់ជាមួយឧទាហរណ៍កូដ ការកំណត់ប្រព័ន្ធ (Configuration) និងដំណើរការជាក់ស្តែង។",
                overviewEnglish = "Hands-on execution with configurations, code examples, and workflow steps.",
                sections = listOf(
                    LessonSection(
                        titleKhmer = "ការកំណត់និងដំណើរការ (Setup & Execution)",
                        titleEnglish = "Configuration & Implementation Steps",
                        contentKhmer = "ដើម្បីទទួលបានប្រសិទ្ធភាពខ្ពស់ក្នុងការគ្រប់គ្រង ${chapter.titleKhmer} វិស្វករតែងតែប្រើប្រាស់ឧបករណ៍ជំនាញ និង Command Line ដើម្បីស្វ័យប្រវត្តិកម្ម។\n\nជំហានអនុវត្ត:\n1. ត្រួតពិនិត្យលក្ខខណ្ឌបរិស្ថាន (Prerequisites Check)\n2. ដំឡើងសេវាកម្ម ឬបណ្ណាល័យដែលត្រូវការ (Dependency Installation)\n3. កំណត់រចនាសម្ព័ន្ធឯកសារ Config និងសិទ្ធិប្រើប្រាស់ (Permissions & Security)\n4. តេស្តដំណើរការ និងត្រួតពិនិត្យ Logs។",
                        contentEnglish = "System engineers rely on structured execution patterns to maintain deterministic, highly available environments.",
                        calloutType = CalloutType.PRO_TIP,
                        calloutTextKhmer = "តែងតែធ្វើការ Backup ទិន្នន័យ និង Config មុនពេលកែប្រែប្រព័ន្ធផលិតកម្ម!",
                        calloutTextEnglish = "Always create rollback snapshots before executing production changes."
                    )
                ),
                practicalCode = """
                    // Standard implementation template
                    function verifySystemStatus() {
                        const status = { health: "OK", timestamp: Date.now() };
                        console.log("System verified:", status);
                        return status;
                    }
                    verifySystemStatus();
                """.trimIndent(),
                codeLanguage = "JavaScript / TypeScript",
                keyTakeawaysKhmer = listOf("អនុវត្តតាមជំហានដែលមានការត្រួតពិនិត្យត្រឹមត្រូវ", "ប្រើប្រាស់ Automation ដើម្បីកាត់បន្ថយកំហុស"),
                keyTakeawaysEnglish = listOf("Follow systematic deployment workflows", "Leverage automation to minimize human error")
            ),
            Lesson(
                id = "ch${chapter.id}_les3",
                chapterId = chapter.id,
                lessonNumber = 3,
                titleKhmer = "៣. វិធីសាស្ត្រដោះស្រាយបញ្ហា (Troubleshooting & Pro Tips)",
                titleEnglish = "3. Diagnostic Troubleshooting & Optimization",
                durationMinutes = 20,
                overviewKhmer = "វិធីសាស្ត្ររកឬសគល់នៃបញ្ហា (Root Cause Analysis) និងការបង្កើនល្បឿនដំណើរការ។",
                overviewEnglish = "Techniques for isolating errors, reading telemetry, and optimizing system bottlenecks.",
                sections = listOf(
                    LessonSection(
                        titleKhmer = "តារាងវិភាគកំហុសទូទៅ (Common Error Patterns)",
                        titleEnglish = "Diagnostic Patterns & Resolution",
                        contentKhmer = "កំហុសភាគច្រើនកើតឡើងដោយសារ:\n• ការកំណត់ Config ខុស (Misconfiguration) ឬច្រឡំ Port/Address\n• កង្វះខាតធនធាន (Memory Exhaustion / CPU Throttling)\n• បញ្ហាសិទ្ធិប្រើប្រាស់ (Permission Denied)\n• បណ្តាញរអាក់រអួល (Network Latency / Packet Timeout)។",
                        contentEnglish = "Systematic root cause analysis relies on reviewing event logs, checking resource ceilings, and verifying firewall rules.",
                        calloutType = CalloutType.WARNING,
                        calloutTextKhmer = "កុំទាយឬសាកល្បងដោយគ្មានការមើល Log! Logs គឺជាភស្តុតាងដ៏មានតម្លៃបំផុត។",
                        calloutTextEnglish = "Inspect system telemetry and log files before attempting ad-hoc remediations."
                    )
                ),
                practicalCode = """
                    # Check system errors and service status
                    journalctl -p err..emerg -n 20 --no-pager
                """.trimIndent(),
                codeLanguage = "Bash Diagnostics",
                keyTakeawaysKhmer = listOf("ពិនិត្យ Logs ជាជំហានទីមួយជានិច្ច", "ដោះស្រាយបញ្ហាម្តងមួយជំហានដើម្បីដឹងមូលហេតុពិត"),
                keyTakeawaysEnglish = listOf("Logs are always the primary diagnostic compass", "Isolate variables sequentially during troubleshooting")
            )
        )
    }
}
