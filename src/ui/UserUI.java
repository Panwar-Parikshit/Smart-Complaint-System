package ui;

import dao.ComplaintDAO;
import model.Complaint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class UserUI extends JFrame {

    JTextField titleField;
    JTextArea descArea;
    JComboBox<String> categoryBox;
    JComboBox<String> severityBox, urgencyBox, impactBox;

    JTable table;
    DefaultTableModel tableModel;

    public UserUI() {

        setTitle("User Panel");
        setSize(1000, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Title
        JLabel t1 = new JLabel("Title");
        t1.setBounds(20, 20, 100, 25);
        add(t1);

        titleField = new JTextField();
        titleField.setBounds(120, 20, 200, 25);
        add(titleField);

        // Description
        JLabel t2 = new JLabel("Description");
        t2.setBounds(20, 60, 100, 25);
        add(t2);

        descArea = new JTextArea();
        descArea.setBounds(120, 60, 200, 80);
        add(descArea);

        // Category
        JLabel t3 = new JLabel("Category");
        t3.setBounds(20, 150, 100, 25);
        add(t3);

        categoryBox = new JComboBox<>(new String[]{"IT", "Maintenance", "Service"});
        categoryBox.setBounds(120, 150, 200, 25);
        add(categoryBox);

        // Dropdown values
        String[] values = {"1","2","3","4","5","6","7","8","9","10"};

        JLabel s1 = new JLabel("Severity");
        s1.setBounds(20, 190, 80, 25);
        add(s1);

        severityBox = new JComboBox<>(values);
        severityBox.setBounds(100, 190, 80, 25);
        add(severityBox);

        JLabel s2 = new JLabel("Urgency");
        s2.setBounds(190, 190, 80, 25);
        add(s2);

        urgencyBox = new JComboBox<>(values);
        urgencyBox.setBounds(260, 190, 80, 25);
        add(urgencyBox);

        JLabel s3 = new JLabel("Impact");
        s3.setBounds(350, 190, 80, 25);
        add(s3);

        impactBox = new JComboBox<>(values);
        impactBox.setBounds(410, 190, 80, 25);
        add(impactBox);

        // Buttons
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(120, 230, 100, 30);
        add(submitBtn);

        JButton viewBtn = new JButton("View Complaints");
        viewBtn.setBounds(230, 230, 160, 30);
        add(viewBtn);

        // TABLE
        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(500, 20, 450, 400);
        add(scroll);

        // SUBMIT
        submitBtn.addActionListener(e -> {

            Complaint c = new Complaint();

            c.setTitle(titleField.getText());
            c.setDescription(descArea.getText());

            // 🔥 AUTO CATEGORY DETECTION (DAA - STRING MATCHING)
            String detectedCategory = detectCategory(descArea.getText());
            c.setCategory(detectedCategory);

            int severity = Integer.parseInt(severityBox.getSelectedItem().toString());
            int urgency = Integer.parseInt(urgencyBox.getSelectedItem().toString());
            int impact = Integer.parseInt(impactBox.getSelectedItem().toString());

            c.setSeverity(severity);
            c.setUrgency(urgency);
            c.setImpact(impact);

            double priority = (severity * 0.5) + (urgency * 0.3) + (impact * 0.2);
            c.setPriority(priority);

            new ComplaintDAO().insertComplaint(c);

            JOptionPane.showMessageDialog(null, 
                "Complaint Submitted!\nDetected Category: " + detectedCategory);
        });

        // VIEW
        viewBtn.addActionListener(e -> {
            try {
                ResultSet rs = new ComplaintDAO().getComplaintsByUser();

                tableModel.setRowCount(0);

                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getDouble("priority"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    // 🔥 STRING MATCHING FUNCTION (DAA)
    private String detectCategory(String desc) {
        desc = desc.toLowerCase();

        if (desc.contains("network") || desc.contains("server") || desc.contains("software"))
            return "IT";

        if (desc.contains("electric") || desc.contains("repair") || desc.contains("clean"))
            return "Maintenance";

        if (desc.contains("service") || desc.contains("support"))
            return "Service";

        return "IT"; // default
    }
}
