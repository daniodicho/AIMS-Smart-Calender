package csun.aims.aimssmartcalendar;

public class Class {

    String name ;
    int difficulty;
    String startTime;
    double timeLength;
    int units;
    int currentGrade ;
    Assignment assignments ;
    boolean requiresReading ;

    public Class() {
        name = null;
        difficulty = 0;
        startTime = null;
        timeLength = 0;
        units = 0;
        currentGrade = 0;
        assignments = null;
        requiresReading = false;
    }
    
    public Class(String n, int d, String sT, double tL, int u, int cG, Assignment aS, boolean rR) {
        name = n;
        difficulty = d;
        startTime = sT;
        timeLength = tL;
        units = u;
        currentGrade = cG;
        assignments = aS;
        requiresReading = rR;
    }
    
        

    public String getName() {
        return this.name;  
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public int getDifficulty() {
        return this.difficulty;  
    }
    
    public void setDifficulty(int d) {
        this.difficulty = d;
    }
    
    public String getStartTime() {
        return this.startTime;  
    }
    
    public void setStartTIme(String sT) {
        this.startTime = sT;
    }

    public double getTimeLength() {
        return this.timeLength;  
    }
    
    public void setTimeLength(double tL) {
        this.timeLength = tL;
    }

    public int getUnits() {
        return this.units;
    }
    
    public void setUnits(int u) {
        this.units = u;
    }
    
    public Assignment getAssignments() {
        return this.assignments;
    }
    
    public void setUnits(Assignment aS) {
        this.assignments = aS;
    }
    
    public int getCurrentGrade() {
        return this.currentGrade;
    }
    
    public void setCurrentGrade(int cG) {
        this.currentGrade = cG;
    }

    public boolean isRequiresReading() {
        return this.requiresReading;
    }
    
    public void setRequiresReading(boolean rR) {
        this.requiresReading = rR;
    }

}