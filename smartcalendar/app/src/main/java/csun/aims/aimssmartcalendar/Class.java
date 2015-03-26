import java.util.LinkedList;

public class Class extends Routine{

    
    int difficulty;
    
    int units;
    double currentGrade ;
    LinkedList <Assignment> assignments;
    boolean requiresReading ;

    public Class() {
        name = null;
        difficulty = 0;
        units = 0;
        currentGrade = 0;
        requiresReading = false;
        assignments = new LinkedList<Assignment>();
    }
    
    public Class(String n, int d, String day, String sT, String tL, int u, double cG, boolean rR) {
        name = n;
        days = day;
        difficulty = d;
        startTime = sT;
        endTime = tL;
        units = u;
        currentGrade = cG;
        requiresReading = rR;
    }

   
    
    public int getDifficulty() {
        return this.difficulty;  
    }
    
    public void setDifficulty(int d) {
        this.difficulty = d;
    }
    
    

    public int getUnits() {
        return this.units;
    }
    
    public void setUnits(int u) {
        this.units = u;
    }
    
    public void addAssignment(Assignment a){
    	assignments.add(a);
    }
    
    public void deleteAssignment(int id){
    	for(int i=0;i<assignments.size();i++){
    		if(assignments.get(i).getId()==id){
    			assignments.remove(i);
    		}
    	}
    }
    
    public LinkedList<Assignment> getAssignments() {
        return this.assignments;
    }
    
    public Assignment getAssignment(int index){
    	return assignments.get(index);
    }
    
    public double getCurrentGrade() {
        return this.currentGrade;
    }
    
    public void setCurrentGrade(double cG) {
        this.currentGrade = cG;
    }

    public boolean isRequiresReading() {
        return this.requiresReading;
    }
    
    public void setRequiresReading(boolean rR) {
        this.requiresReading = rR;
    }
}