package FYP;

import java.util.HashMap;
import java.util.ArrayList;

class Statistics {
    private ArrayList<HashMap<String, String>> allStats = new ArrayList<HashMap<String, String>>();

    private HashMap<String, String> methodStats = new HashMap<String, String>();
    private HashMap<String, String> variableStats = new HashMap<String, String>();
    private HashMap<String, String> parameterStats = new HashMap<String, String>();
    
    private HashMap<String, Integer> changeCount = new HashMap<String, Integer>();
    
    
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

    public void increaseCount(String methodName) {
        if (changeCount.get(methodName) == null) {
            changeCount.put(methodName, 0);
        }
        else {
            int count = changeCount.get(methodName);
            changeCount.put(methodName, count + 1);
        }
    }
    public HashMap<String, Integer> getCount() {
        return changeCount;
    }



    public String printStats(ArrayList<HashMap<String, String>> allStats, HashMap<String, Integer> allCount) {
        String text = "";
        for (HashMap<String, String> h: allStats) {
            for(HashMap.Entry<String, String> entry: h.entrySet()){
                text = text + String.format("%-40s %5s %-20s Replaced %2d times\n",entry.getKey(), "------->", entry.getValue(), allCount.get(entry.getKey()));
            }
        }
        return text;
        
    }

    
}