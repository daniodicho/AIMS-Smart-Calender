public class Event {

	String name ;
    String startTime;
    String endTime;
    int dayAssigned;
   
    Event(){
    	name="";
    	startTime = null;
    	endTime = null;
    }
    
    Event(String n, String s, String e){
    	setName(n);
    	setStartTime(s);
    	setEndTime(e);
    }
    
    public String getName() {
        return this.name;  
    }

    public void setName(String n) {
        this.name = n;
    }

    public int getIndex(){
    	return dayAssigned;
    }
    
    public void setIndex(int num){
    	dayAssigned = num;
    }
    
    public String getStartTime() {
        return this.startTime;  
    }

    public void setStartTime(String sT) {
        this.startTime = sT;
    }

    public String getEndTime() {
        return this.endTime;  
    }

    public void setEndTime(String tL) {
        this.endTime = tL;
    }
}
