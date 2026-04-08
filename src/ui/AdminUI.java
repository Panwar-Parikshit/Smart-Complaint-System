package ui;

import javax.swing.*;
import util.AppContext;
import model.Complaint;

public class AdminUI {

    public AdminUI() {

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(400, 380); // slightly increased height
        frame.setLayout(null);

        // 🔹 Process Button (Greedy + BFS + Explainable AI)
        JButton processBtn = new JButton("Process Complaint");
        processBtn.setBounds(100, 50, 200, 40);
        frame.add(processBtn);

        // 🔹 View Priority Order (Greedy Visualization)
        JButton viewSorted = new JButton("View Priority Order");
        viewSorted.setBounds(100, 110, 200, 40);
        frame.add(viewSorted);

        // 🔥 PROCESS BUTTON LOGIC (UPDATED WITH EXPLANATION)
        processBtn.addActionListener(e -> {

            Complaint c = AppContext.service.processNext();

            if (c == null) {
                JOptionPane.showMessageDialog(frame, "No complaints");
            } else {

                String team = AppContext.service.assignTeam(c);

                // 🔥 BFS RELATED COMPLAINTS
                java.util.List<Integer> related =
                        AppContext.service.getRelatedComplaints(c.getId());

                // 🔥 NEW: EXPLAIN PRIORITY
                String explanation =
                        "Priority Breakdown:\n" +
                        "Severity (" + c.getSeverity() + ") × 0.5\n" +
                        "Urgency (" + c.getUrgency() + ") × 0.3\n" +
                        "Impact (" + c.getImpact() + ") × 0.2\n\n" +
                        "Final Priority = " + c.getPriority();

                JOptionPane.showMessageDialog(frame,
                        "Complaint ID: " + c.getId() +
                        "\nAssigned to: " + team +
                        "\nRelated Complaints: " + related +
                        "\n\n" + explanation
                );
            }
        });

        // 🔥 GREEDY SORT DISPLAY
        viewSorted.addActionListener(e -> {

            java.util.List<Complaint> list =
                    AppContext.service.getSortedComplaints();

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints available");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Complaint c : list) {
                sb.append("ID: ").append(c.getId())
                  .append(" | Priority: ").append(c.getPriority())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        frame.setVisible(true);
    }
}
