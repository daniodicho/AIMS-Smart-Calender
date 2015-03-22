
public class Routine {
	String name ;
    String days;
    String startTime;
    String endTime;

Routine(){
	name = null;
	days = "";
	startTime = null;
    endTime = null;
}

Routine(String n,String d, String s, String e){
	name = n;
	days = d;
	startTime = s;
    endTime = e;
}

public String getDays(){
	return days;
}

public void setDays(String d){
	days = d;
}
public String getName() {
    return this.name;  
}

public void setName(String n) {
    this.name = n;
}

public String getStartTime() {
    return this.startTime;  
}

public void setStartTIme(String sT) {
    this.startTime = sT;
}

public String getEndtime() {
    return this.endTime;  
}

public void setEndtime(String tL) {
    this.endTime = tL;
}
}