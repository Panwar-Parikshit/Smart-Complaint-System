package ui;

import dao.ComplaintDAO;
import model.Complaint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
        setSize(1200, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235,240,245));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // HEADER
        JLabel header = new JLabel("User Dashboard");
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBounds(20, 10, 300, 30);
        add(header);

        // LEFT PANEL
        JPanel left = new JPanel(null);
        left.setBounds(20, 60, 350, 460);
        left.setBackground(Color.WHITE);
        left.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        add(left);

        JLabel l1 = new JLabel("Title");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l1.setBounds(20, 10, 100, 25);
        left.add(l1);

        titleField = new JTextField();
        titleField.setBounds(20, 35, 300, 30);
        left.add(titleField);

        JLabel l2 = new JLabel("Description");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l2.setBounds(20, 75, 100, 25);
        left.add(l2);

        descArea = new JTextArea();
        descArea.setBounds(20, 100, 300, 80);
        descArea.setBorder(BorderFactory.createLineBorder(new Color(180,180,180)));
        left.add(descArea);

        JLabel l3 = new JLabel("Category");
        l3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l3.setBounds(20, 190, 100, 25);
        left.add(l3);

        categoryBox = new JComboBox<>(new String[]{"IT", "Maintenance", "Service"});
        categoryBox.setBounds(20, 215, 300, 30);
        left.add(categoryBox);

        String[] values = {"1","2","3","4","5","6","7","8","9","10"};

        JLabel l4 = new JLabel("Severity");
        l4.setBounds(20, 255, 80, 25);
        left.add(l4);

        severityBox = new JComboBox<>(values);
        severityBox.setBounds(20, 280, 80, 30);
        left.add(severityBox);

        JLabel l5 = new JLabel("Urgency");
        l5.setBounds(120, 255, 80, 25);
        left.add(l5);

        urgencyBox = new JComboBox<>(values);
        urgencyBox.setBounds(120, 280, 80, 30);
        left.add(urgencyBox);

        JLabel l6 = new JLabel("Impact");
        l6.setBounds(220, 255, 80, 25);
        left.add(l6);

        impactBox = new JComboBox<>(values);
        impactBox.setBounds(220, 280, 80, 30);
        left.add(impactBox);

        // BUTTONS (FIXED)
        JButton submitBtn = createButton("Submit Complaint", new Color(46,204,113));
        submitBtn.setBounds(20, 340, 150, 45);
        left.add(submitBtn);

        JButton viewBtn = createButton("View Complaints", new Color(33,150,243));
        viewBtn.setBounds(180, 340, 150, 45);
        left.add(viewBtn);

        // RIGHT PANEL
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBounds(400, 60, 760, 460);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        add(tablePanel);

        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        int[] widths = {50,150,100,80,100,140,140};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        tablePanel.add(new JScrollPane(table));

        // LOGIC
        submitBtn.addActionListener(e -> {

            Complaint c = new Complaint();

            c.setTitle(titleField.getText());
            c.setDescription(descArea.getText());
            c.setCategory(categoryBox.getSelectedItem().toString());

            int severity = Integer.parseInt(severityBox.getSelectedItem().toString());
            int urgency = Integer.parseInt(urgencyBox.getSelectedItem().toString());
            int impact = Integer.parseInt(impactBox.getSelectedItem().toString());

            c.setSeverity(severity);
            c.setUrgency(urgency);
            c.setImpact(impact);

            double priority = (severity * 0.5) + (urgency * 0.3) + (impact * 0.2);
            c.setPriority(priority);

            new ComplaintDAO().insertComplaint(c);

            JOptionPane.showMessageDialog(null, "Complaint Submitted!");
        });

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

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);

        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);

        return btn;
    }
}