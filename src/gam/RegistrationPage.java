package gam ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
public class RegistrationPage extends JFrame {
 
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField dateField;
    private JButton registerButton;
    private JTable dataTable;

    private Connection connection;

    public RegistrationPage() {
        super("Registration Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize GUI components
        nameField = new JTextField();
        phoneNumberField = new JTextField();
        dateField = new JTextField();
        registerButton = new JButton("Register");
        dataTable = new JTable();

        // Set layout
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Add GUI components to the input panel
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Date of Registration:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel());
        inputPanel.add(registerButton);

        // Add the input panel and table to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        // Connect to the database
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Gam.db");
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }

        // Fetch and display the data in the table
        displayData();

        setVisible(true);
    }

    private void register() {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String date = dateField.getText();

        try (
//                PreparedStatement statement = connection.prepareStatement(
  //              "INSERT INTO registrations (name, phone_number,  registration_date) VALUES (?, ?, ?)")) {
                PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO gam_clints (gam_name , gam_phone , gam_sub , gam_date) VALUES (?,?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, date);
            statement.executeUpdate();
            System.out.println("Registration successful!");
            displayData(); // Refresh the table
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private void displayData() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM gam_clints");
             ResultSet resultSet = statement.executeQuery()) {

            // Create the table model
            DefaultTableModel tableModel = new DefaultTableModel();

            // Get the column names from the result set metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            // Set the table model to the JTable
            dataTable.setModel(tableModel);
        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationPage());
    }
}

 

 
 



 




    
 
