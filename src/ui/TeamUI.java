package ui;

import javax.swing.*; // used for UI
import javax.swing.table.DefaultTableModel;// for table handling
import java.awt.*; // for layout
import java.sql.ResultSet;// for database result

import dao.ComplaintDAO; // for database operations
import model.User; // for logged in user info

public class TeamUI {

    JTable table; // used for displaying contents
    DefaultTableModel model; // used to store table data
    JTextField searchField; // input for search
    JComboBox<String> statusBox; // dropdown menu to update status

    public TeamUI(User user) { // the constructor takes logged in user to know which which team has logged in

    	// creates a window with title
        JFrame frame = new JFrame("Team Panel - " + user.getRole());
        frame.setSize(950, 500);
        frame.setLayout(new BorderLayout());

        // TABLE
        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        model = new DefaultTableModel(cols, 0); // stores data
        table = new JTable(model); // displays data

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // prevents column from shrinking

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        // adds scrollable table
        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll, BorderLayout.CENTER);

       
        JPanel panel = new JPanel(); // used for holding buttons and input

        
        // selecting new status
        String[] statuses = {"IN_PROGRESS", "RESOLVED", "ESCALATED"};
        statusBox = new JComboBox<>(statuses);
        panel.add(statusBox);

        //button for updating status
        JButton updateBtn = new JButton("Update Status");
        panel.add(updateBtn);

        // input for searching
        searchField = new JTextField(15);
        panel.add(searchField);

        // button for searching
        JButton searchBtn = new JButton("Search by Title");
        panel.add(searchBtn);

        frame.add(panel, BorderLayout.SOUTH); // adds pannels to the bottom

        ComplaintDAO dao = new ComplaintDAO();

        // LOAD FUNCTION
        Runnable loadData = () -> { // function to load complaints
            try {
                model.setRowCount(0);

                ResultSet rs = dao.getComplaintsByTeam(user.getRole()); // fetch complaint of that team

                while (rs.next()) { // loops through database rows 
                    model.addRow(new Object[]{ // add each row to the table
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

        loadData.run();

        // UPDATE STATUS
        updateBtn.addActionListener(e -> { //logic for updating status -> runs when update button is clicked

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a complaint first!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            String status = statusBox.getSelectedItem().toString();

            dao.updateStatus(id, status);

            JOptionPane.showMessageDialog(frame, "Status Updated!");

            loadData.run();
        });

        // SEARCH
        searchBtn.addActionListener(e -> { // logic for searching -> runs when search button is clicked
            try {
                model.setRowCount(0);

                
                String title = searchField.getText();

                ResultSet rs = dao.searchByTitle(title, user.getRole());

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

        frame.setVisible(true);
    }
}