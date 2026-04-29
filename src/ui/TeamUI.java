package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

import dao.ComplaintDAO;
import model.User;

public class TeamUI {

    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JComboBox<String> statusBox;
    JComboBox<String> searchType;

    public TeamUI(User user) {

        JFrame frame = new JFrame("Team Panel - " + user.getRole());
        frame.setSize(1200, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(235,240,245));

        JLabel header = new JLabel("Team Dashboard");
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBounds(20, 10, 300, 30);
        frame.add(header);

        // 🔥 FIX: Map role → category
        String category = mapRoleToCategory(user.getRole());

        JPanel left = new JPanel(null);
        left.setBounds(20, 60, 320, 460);
        left.setBackground(Color.WHITE);
        left.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        frame.add(left);

        statusBox = new JComboBox<>(new String[]{"IN_PROGRESS", "RESOLVED", "ESCALATED"});
        statusBox.setBounds(30, 40, 260, 35);
        left.add(statusBox);

        JButton updateBtn = createButton("Update Status", new Color(46,204,113));
        updateBtn.setBounds(30, 90, 260, 45);
        left.add(updateBtn);

        searchType = new JComboBox<>(new String[]{"ID", "Title"});
        searchType.setBounds(30, 160, 120, 35);
        left.add(searchType);

        searchField = new JTextField();
        searchField.setBounds(160, 160, 130, 35);
        left.add(searchField);

        JButton searchBtn = createButton("Search", new Color(33,150,243));
        searchBtn.setBounds(30, 210, 260, 45);
        left.add(searchBtn);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBounds(360, 60, 800, 460);
        tablePanel.setBackground(Color.WHITE);
        frame.add(tablePanel);

        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);

        tablePanel.add(new JScrollPane(table));

        ComplaintDAO dao = new ComplaintDAO();

       
        Runnable load = () -> {
            try {
                model.setRowCount(0);

                ResultSet rs = dao.getComplaintsByTeam(category);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getDouble("priority"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        load.run();

        // UPDATE STATUS
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) model.getValueAt(row, 0);
            dao.updateStatus(id, statusBox.getSelectedItem().toString());

            load.run();
        });

        // SEARCH FIXED
        searchBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);

                ResultSet rs;

                if (searchType.getSelectedItem().equals("ID")) {
                    int id = Integer.parseInt(searchField.getText());
                    rs = dao.searchById(id, category);
                } else {
                    rs = dao.searchByTitle(searchField.getText(), category);
                }

                while (rs.next()) {
                    model.addRow(new Object[]{
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
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        frame.setVisible(true);
    }

  
    private String mapRoleToCategory(String role) {

        if (role.equalsIgnoreCase("IT") || role.contains("IT"))
            return "IT";

        if (role.equalsIgnoreCase("Maintenance") || role.contains("Maintenance"))
            return "Maintenance";

        if (role.equalsIgnoreCase("Service") || role.contains("Service"))
            return "Service";

        return role; 
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