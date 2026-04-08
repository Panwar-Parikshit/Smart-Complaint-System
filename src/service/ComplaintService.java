package service;

import model.Complaint;
import java.util.*;

public class ComplaintService {

    private PriorityQueue<Complaint> queue;
    private HashMap<Integer, Complaint> map;
    private HashMap<String, String> teamMap;

    // GRAPH for related complaints
    private Map<Integer, List<Integer>> graph;

    public ComplaintService() {
        queue = new PriorityQueue<>(
                (c1, c2) -> Double.compare(c2.getPriority(), c1.getPriority())
        );

        map = new HashMap<>();
        teamMap = new HashMap<>();
        graph = new HashMap<>();

        teamMap.put("IT", "IT Team");
        teamMap.put("Maintenance", "Maintenance Team");
        teamMap.put("Service", "Service Team");
    }

    // ADD COMPLAINT + GRAPH BUILD
    public void addComplaint(Complaint c) {
        queue.add(c);
        map.put(c.getId(), c);

        graph.putIfAbsent(c.getId(), new ArrayList<>());

        for (Complaint other : map.values()) {
            if (other.getId() != c.getId() &&
                    other.getCategory().equals(c.getCategory())) {

                graph.get(c.getId()).add(other.getId());
                graph.get(other.getId()).add(c.getId());
            }
        }
    }

    // GREEDY
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

    // GREEDY SORT (VISIBLE FEATURE)
    public List<Complaint> getSortedComplaints() {
        List<Complaint> list = new ArrayList<>(map.values());
        list.sort((a, b) -> Double.compare(b.getPriority(), a.getPriority()));
        return list;
    }

    // BFS (GRAPH)
    public List<Integer> getRelatedComplaints(int id) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        q.add(id);
        visited.add(id);

        while (!q.isEmpty()) {
            int curr = q.poll();
            result.add(curr);

            for (int nei : graph.getOrDefault(curr, new ArrayList<>())) {
                if (!visited.contains(nei)) {
                    visited.add(nei);
                    q.add(nei);
                }
            }
        }

        return result;
    }

    // BINARY SEARCH
    public Complaint binarySearchByPriority(List<Complaint> list, double target) {

        int low = 0, high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (list.get(mid).getPriority() == target)
                return list.get(mid);

            else if (list.get(mid).getPriority() < target)
                high = mid - 1;

            else
                low = mid + 1;
        }

        return null;
    }
}
