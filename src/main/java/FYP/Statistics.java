package FYP;

import java.util.HashMap;


class Statistics {

    private String statType;
    private HashMap<String, String> stats = new HashMap<String, String>();
    private HashMap<String, Integer> counts = new HashMap<String, Integer>();
    
    public void setType(String type) {
        this.statType = type;
    }
    public void setStats(String name, String newName) {
        stats.put(name, newName);
    }

    public void increaseCount(String s) {
        if (counts.get(s) == null) {
            counts.put(s, 1);
        }
        else {
            int count = counts.get(s); 
            counts.put(s, count + 1);
        }
    }

    public String printStats() {
        String text = "";
        if (this.statType.equals("Dummy Code")) {

            for(String s: stats.keySet()) {
                text = text + String.format("%-20s %-20s Inserted\n\n", this.statType + ": ", s);
    
            }
        }
        else if (this.statType.equals("Removed Comments")) {
            for (String s: stats.keySet()) {
                text = text + String.format("%-20s %-20s\n\n", this.statType + ": ", s);
            }
        }
        else {
                
            for(String s: stats.keySet()) {
                text = text + String.format("%-20s %-20s -----------> %-30s Replaced %2d times\n\n", this.statType + ": ", s, stats.get(s), counts.get(s));

            }
        }
        return text;
    }
}