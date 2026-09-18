# System Architecture & Project Statement

---

## 1. Problem Statement

Small-to-medium educational institutions, departmental reading rooms, academic laboratories, and independent community libraries face persistent operational challenges regarding catalog and membership recordkeeping. Traditionally, these entities rely on one of two divergent operational methods, both of which introduce distinct structural inefficiencies:

1. **Manual Paper-Based Ledger Systems**:
   - High vulnerability to physical wear, tear, accidental spills, loss, and catastrophic environmental damage.
   - Severe latency in record retrieval, catalog lookup, and patron verification.
   - High susceptibility to manual recording discrepancies, duplicate record errors, and illegible handwriting.
   - Total lack of concurrent record visibility or programmatic data validation.

2. **Enterprise Integrated Library Systems (ILS)**:
   - High infrastructure and computational overhead requiring dedicated database servers (e.g., MySQL, PostgreSQL, Oracle).
   - Complex deployment lifecycles requiring Java Database Connectivity (JDBC) configuration, driver dependency management, and active schema migrations.
   - Prohibitive resource and cost requirements for low-complexity local book repositories that lack dedicated IT infrastructure.

To resolve this operational gap, there is a clear requirement for a lightweight, self-contained desktop system. The application must run out of the box with zero runtime configuration, provide an intuitive graphical user interface for non-technical library staff, support essential CRUD operations for books and members, and ensure persistent, corruption-resistant data storage on the local file system.

---

## 2. Scope of the Project

### 2.1 In-Scope Capabilities

The system covers core administrative management functions across graphical interaction, data handling, and file persistence:

* **Graphical User Interface (GUI)**:
  - Centralized single-window architecture utilizing a tabbed layout (`JTabbedPane`) to separate catalog management from patron administration.
  - Interactive tabular views (`JTable`) backed by dynamic vector models (`DefaultTableModel`) for synchronized display of persisted records.
  - Native operating system look-and-feel integration via Java Swing's `UIManager` platform introspection.
  - Intuitive modal dialogs (`JOptionPane`) for non-blocking error notifications and user prompts.

* **Book Inventory Operations (Catalog CRUD)**:
  - **Create**: Registration of new catalog entries with mandatory validation for unique `Book ID` and `Title`, alongside optional `Author` attribution.
  - **Read**: Dynamic loading and tabular rendering of all persisted book records during system launch and post-operation refreshes.
  - **Update**: Row-selection-driven updating of book metadata (Title and Author) via sequential input prompts.
  - **Delete**: Row-selection-driven deletion that purges targeted book records from both memory and disk.

* **Member Directory Operations**:
  - **Create**: Enrollment of library members through mandatory validation of `Member ID` and `Name`.
  - **Read**: Continuous tabular display of active patron records.
  - **Delete**: Removal of member records from the directory via table row selection.

* **Persistence & File I/O Engine**:
  - Direct local flat-file storage using comma-separated value formatting (`books.txt` and `members.txt`).
  - Automatic directory initialization of target database files upon initial application execution.
  - Fault-tolerant record updating and deletion using a temporary buffer file (`temp.txt`) and atomic file renaming to prevent partial writes or record corruption.

### 2.2 Out-of-Scope (Boundaries & Future Roadmaps)

To maintain a zero-dependency architecture, the following capabilities are explicitly outside the current implementation boundary:
- Automated circulation management (book issue/checkout, return timestamps, renewal tracking, and loan duration logging).
- Automated fine calculation, late return penalty tracking, and payment processing.
- Multi-tier role-based access control (RBAC), multi-user concurrency control, and password authentication.
- Complex RFC 4180 CSV escaping (handling literal commas or embedded line breaks within titles or author names).
- Relational DBMS integration (SQL/JDBC), network communication, or cloud database synchronization.

---

## 3. Target Users

The system is designed for organizations and individuals seeking an efficient, offline-first recordkeeping solution:

1. **Departmental & Academic Lab Librarians**:
   - Instructors, teaching assistants, or lab coordinators managing subject-specific reference collections, research handbooks, and student project archives without central IT support.
2. **School & Community Reading Room Administrators**:
   - Operators of rural educational centers, community learning centers, and non-profit reading spaces that need software that runs on entry-level, offline hardware.
3. **Private Collectors & Archival Curators**:
   - Individuals, researchers, or hobbyists maintaining personal libraries, rare document repositories, or localized inventories.
4. **Computer Science Learners & Educators**:
   - Students and instructors evaluating fundamental desktop software engineering patterns, Java Swing event-driven architecture, and file stream handling.

---

## 4. High-Level Features

```text
+---------------------------------------------------------------------------------------+
|                               Library Management System                               |
+-------------------------------------------+-------------------------------------------+
|             Manage Books Module           |           Manage Members Module           |
+-------------------------------------------+-------------------------------------------+
| - Book Registration Form                  | - Member Enrollment Form                  |
| - Non-Empty Validation (ID & Title)       | - Input Validation (ID & Name)            |
| - Dynamic Table View (JTable)             | - Directory Table View (JTable)           |
| - In-Place Update via Selection & Prompts | - Selected Patron Record Removal          |
| - Selected Book Record Deletion           |                                           |
+-------------------------------------------+-------------------------------------------+
                                      |
                                      v
+---------------------------------------------------------------------------------------+
|                             Data Persistence & Storage Engine                         |
+---------------------------------------------------------------------------------------+
| - Automatic initialization of local flat files (books.txt, members.txt)               |
| - Append-mode stream persistence via PrintWriter & FileWriter                         |
| - Line-by-line buffered stream parsing and tokenization via BufferedReader             |
| - Atomic file rewrite routine (temp.txt swap) for safe record updates and deletions   |
+---------------------------------------------------------------------------------------+
