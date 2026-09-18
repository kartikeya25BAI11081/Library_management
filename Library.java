import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class LibrarySystemGUI extends JFrame {
    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";

    private DefaultTableModel bookTableModel;
    private DefaultTableModel memberTableModel;
    private JTable bookTable;
    private JTable memberTable;

    public LibrarySystemGUI() {
        initFiles();
        
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manage Books", createBookPanel());
        tabbedPane.addTab("Manage Members", createMemberPanel());

        add(tabbedPane);
        
        // Load initial data
        loadData(BOOKS_FILE, bookTableModel, 3);
        loadData(MEMBERS_FILE, memberTableModel, 2);
    }

    private void initFiles() {
        try {
            new File(BOOKS_FILE).createNewFile();
            new File(MEMBERS_FILE).createNewFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error initializing database files.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        
        formPanel.add(new JLabel("Book ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        String[] columns = {"Book ID", "Title", "Author"};
        bookTableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(bookTableModel);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> {
            if (idField.getText().isEmpty() || titleField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ID and Title are required.");
                return;
            }
            String record = idField.getText() + "," + titleField.getText() + "," + authorField.getText();
            appendToFile(BOOKS_FILE, record);
            loadData(BOOKS_FILE, bookTableModel, 3);
            idField.setText(""); titleField.setText(""); authorField.setText("");
        });

        deleteButton.addActionListener(e -> {
            int row = bookTable.getSelectedRow();
            if (row >= 0) {
                String id = bookTableModel.getValueAt(row, 0).toString();
                deleteRecord(BOOKS_FILE, id);
                loadData(BOOKS_FILE, bookTableModel, 3);
            }
        });

        updateButton.addActionListener(e -> {
            int row = bookTable.getSelectedRow();
            if (row >= 0) {
                String id = bookTableModel.getValueAt(row, 0).toString();
                String newTitle = JOptionPane.showInputDialog(panel, "Enter new Title:", bookTableModel.getValueAt(row, 1));
                String newAuthor = JOptionPane.showInputDialog(panel, "Enter new Author:", bookTableModel.getValueAt(row, 2));
                
                if (newTitle != null && newAuthor != null) {
                    updateRecord(BOOKS_FILE, id, id + "," + newTitle + "," + newAuthor);
                    loadData(BOOKS_FILE, bookTableModel, 3);
                }
            }
        });

        return panel;
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        
        formPanel.add(new JLabel("Member ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Member");
        JButton deleteButton = new JButton("Delete Selected");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"Member ID", "Name"};
        memberTableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(memberTableModel);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty()) return;
            String record = idField.getText() + "," + nameField.getText();
            appendToFile(MEMBERS_FILE, record);
            loadData(MEMBERS_FILE, memberTableModel, 2);
            idField.setText(""); nameField.setText("");
        });

        deleteButton.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row >= 0) {
                String id = memberTableModel.getValueAt(row, 0).toString();
                deleteRecord(MEMBERS_FILE, id);
                loadData(MEMBERS_FILE, memberTableModel, 2);
            }
        });

        return panel;
    }

    private void loadData(String filename, DefaultTableModel model, int columnCount) {
        model.setRowCount(0); // Clear existing table data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Vector<String> row = new Vector<>();
                for (int i = 0; i < columnCount; i++) {
                    row.add(i < parts.length ? parts[i] : "");
                }
                model.addRow(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    private void appendToFile(String filename, String data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(data);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Write error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord(String filename, String idToDelete) {
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.split(",")[0].equals(idToDelete)) {
                    pw.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    private void updateRecord(String filename, String idToUpdate, String newRecord) {
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[0].equals(idToUpdate)) {
                    pw.println(newRecord);
                } else {
                    pw.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }

        SwingUtilities.invokeLater(() -> {
            new LibrarySystemGUI().setVisible(true);
        });
    }
}
