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
        methodStats.put("Method Name: " + oldName, newName);
        changeCount.put(oldName, 0); // initialize number of times changed to 0
    }

    public void setVariableStats(String oldName, String newName) {
        variableStats.put("Variable Name: " + oldName, newName);
        changeCount.put(oldName, 0); // initialize number of times changed to 0

    }
    
    public void setParameterStats(String oldName, String newName) {
        parameterStats.put("Parameter Name: " + oldName, newName);
        changeCount.put(oldName, 0); // initialize number of times changed to 0

    }

    public void increaseCount(String methodName) {
        int count = changeCount.get(methodName);
        changeCount.put(methodName, count+ 1);
    }
    public HashMap<String, Integer> getCount() {
        return changeCount;
    }



    public void printStats(ArrayList<HashMap<String, String>> allStats, HashMap<String, Integer> allCount) {
        System.out.println("\nStatistics\n==========================================================\n");
        for (HashMap<String, String> h: allStats) {
            for(HashMap.Entry<String, String> entry: h.entrySet()){
                System.out.println(String.format("%-50s %5s %20s %20d",entry.getKey(), "------->", entry.getValue(), allCount.get(entry.getKey())));
            }
        }
        
    }

    
}