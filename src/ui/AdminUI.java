package ui;

import dao.ComplaintDAO;
import dao.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class AdminUI {

    JTable table;
    DefaultTableModel model;

    public AdminUI() {

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(1200, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(235,240,245));

        JLabel header = new JLabel("Admin Dashboard");
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBounds(20, 10, 300, 30);
        frame.add(header);

        JPanel left = new JPanel(null);
        left.setBounds(20, 60, 320, 460);
        left.setBackground(Color.WHITE);
        left.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        frame.add(left);

        JButton loadBtn = createButton("Load Complaints", new Color(33,150,243));
        loadBtn.setBounds(30, 40, 260, 45);
        left.add(loadBtn);

        JButton deleteBtn = createButton("Delete Complaint", new Color(231,76,60));
        deleteBtn.setBounds(30, 100, 260, 45);
        left.add(deleteBtn);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBounds(360, 60, 800, 460);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        frame.add(tablePanel);

        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);

        tablePanel.add(new JScrollPane(table));

        ComplaintDAO dao = new ComplaintDAO();

        loadBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);
                ResultSet rs = DBConnection.getConnection()
                        .prepareStatement("SELECT * FROM complaints")
                        .executeQuery();

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
                ex.printStackTrace();
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            dao.deleteComplaint(id);
            model.removeRow(row);
        });

        frame.setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }
}