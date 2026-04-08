package service;

import model.Complaint;
import java.util.*;

public class ComplaintService {

    private PriorityQueue<Complaint> queue;
    private HashMap<Integer, Complaint> map;
    private HashMap<String, String> teamMap;

    public ComplaintService() {
        queue = new PriorityQueue<>(
            (c1, c2) -> Double.compare(c2.getPriority(), c1.getPriority())
        );

        map = new HashMap<>();
        teamMap = new HashMap<>();

        teamMap.put("IT", "IT Team");
        teamMap.put("Maintenance", "Maintenance Team");
        teamMap.put("Service", "Service Team");
    }

    public void addComplaint(Complaint c) {
        queue.add(c);
        map.put(c.getId(), c);
    }

    public Complaint processNext() {
        Complaint c = queue.poll();
        if (c != null) {
            c.setStatus("ASSIGNED");
        }
        return c;
    }

    public String assignTeam(Complaint c) {
        return teamMap.get(c.getCategory());
    }

    public Complaint searchById(int id) {
        return map.get(id);
    }
    public Collection<Complaint> getAllComplaints() {
        return map.values();
    }
}