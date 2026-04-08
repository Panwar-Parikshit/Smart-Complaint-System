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
        frame.setSize(1000, 500);
        frame.setLayout(new BorderLayout());

        // TABLE
        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll, BorderLayout.CENTER);

        // PANEL (Buttons)
        JPanel panel = new JPanel();

        JButton loadBtn = new JButton("Load Complaints");
        panel.add(loadBtn);

        JButton deleteBtn = new JButton("Delete Complaint");
        panel.add(deleteBtn);

        frame.add(panel, BorderLayout.SOUTH);

        ComplaintDAO dao = new ComplaintDAO();

        // LOAD ALL COMPLAINTS
        loadBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);

                // grouped by category (team)
                String sql = "SELECT * FROM complaints ORDER BY category, priority DESC";
                ResultSet rs = DBConnection.getConnection()
                        .prepareStatement(sql)
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

        // DELETE COMPLAINT
        deleteBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a complaint first!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete this complaint?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteComplaint(id);

                JOptionPane.showMessageDialog(frame, "Complaint Deleted!");

                model.removeRow(row); // remove from UI
            }
        });

        frame.setVisible(true);
    }
}