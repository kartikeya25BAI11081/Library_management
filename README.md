Library Management System

Overview of the Project

The Library Management System is a lightweight, dependency-free desktop application built purely in Java. It allows library administrators to manage book inventories and member registries through a clean Graphical User Interface (GUI).

Instead of relying on heavy relational databases like MySQL or Oracle, this system uses flat-file architecture (.txt files) for persistent data storage. This makes the application highly portable, incredibly easy to set up, and perfect for small-scale use cases or academic projects.

Screenshot Placeholder: (Once you upload to GitHub, replace this line with a screenshot of your app by dragging an image here!)

 Features

Book Management (CRUD):

Create: Add new books with a unique ID, Title, and Author.

Read: View all books in a dynamically updated, formatted table.

Update: Select any book from the table and modify its title or author inline.

Delete: Remove lost or damaged books permanently from the system.

 Persistent Storage: Data is instantly saved to books.txt and members.txt on the local disk. No data is lost when the application is closed.

 Native Look and Feel: Utilizes the host operating system's native UI theme for a sleek, professional appearance.

 Auto-Initialization: Automatically generates the required database text files on the first run to prevent runtime crash errors.

 Technologies & Tools Used

Language: Core Java (JDK 8 or higher)

User Interface: Java Swing (javax.swing.*, java.awt.*)

Storage/Database: Core Java File I/O (java.io.* - FileReader, FileWriter, BufferedReader, PrintWriter)

Architecture: Object-Oriented Programming (OOP), Event-Driven UI

 Steps to Install & Run the Project

Prerequisites: Ensure you have the Java Development Kit (JDK) installed on your system. You can verify this by opening a terminal/command prompt and typing java -version.

Clone the Repository:
https://github.com/kartikeya25BAI11081/Library_management/edit/main/README.md
Compile the Application:
Compile the source code using the Java Compiler (javac):

javac LibrarySystemGUI.java

Run the Application:
Execute the compiled class:

java LibrarySystemGUI


Note: The application window will open, and books.txt and members.txt will automatically be created in your root directory if they don't already exist.
 
Instructions for Testing

To ensure the system works exactly as intended, perform the following manual tests in the GUI:

Testing Storage Initialization:

Action: Run the application for the very first time.

Expected Result: The app opens without errors. Check your project folder; books.txt and members.txt should be generated automatically.

Testing Book Creation (Write):

Action: In the "Manage Books" tab, enter 101 for ID, Clean Code for Title, and Robert C. Martin for Author. Click "Add Book".

Expected Result: The text fields clear out, and the new book immediately appears in the table below.

Testing Updates (Modify):

Action: Click on the row containing "Clean Code" in the table. Click the "Update Selected" button. Enter a new title and author when prompted.

Expected Result: The table refreshes instantly with the newly modified data.

Testing Deletion (Remove):

Action: Select a book or member from their respective tables and click "Delete Selected".

Expected Result: The row disappears from the GUI table and is removed from the underlying .txt file.

Testing Persistence:

Action: Close the application completely. Re-run the application using java LibrarySystemGUI.

Expected Result: The tables automatically populate with all the data you added during the previous session, proving File I/O persistence is fully functional.

Author
Kartikeya Mishra
25BAI11081
