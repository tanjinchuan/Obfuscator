package FYP;

import java.util.HashMap;
import java.util.ArrayList;

class Statistics {
    private ArrayList<HashMap<String, String>> allStats = new ArrayList<HashMap<String, String>>();
    private HashMap<String, String> methodStats = new HashMap<String, String>();
    private HashMap<String, String> variableStats = new HashMap<String, String>();
    private HashMap<String, String> parameterStats = new HashMap<String, String>();
    
    public void addToList(HashMap<String, String> stats) {
        allStats.add(stats);
    }
    public ArrayList<HashMap<String, String>> getStats() {
        return allStats;
    }
    public HashMap<String, String> getMethodStats() {
        return methodStats;
    }

    public HashMap<String, String> getVariableStats() {
        return variableStats;
    }

    public HashMap<String, String> getParameterStats() {
        return parameterStats;
    }

    public void setMethodStats(String oldName, String newName) {
        methodStats.put(oldName, newName);
    }

    public void setVariableStats(String oldName, String newName) {
        variableStats.put(oldName, newName);
    }
    
    public void setParameterStats(String oldName, String newName) {
        parameterStats.put(oldName, newName);
    }


    public void printStats(ArrayList<HashMap<String, String>> allStats) {
        System.out.println("\nStatistics\n==========================================================\n");
        for (HashMap<String, String> h: allStats) {
            for(HashMap.Entry<String, String> entry: h.entrySet()){
                System.out.println(entry.getKey() + "-------> " + entry.getValue());
            }
        }
        
    }
}