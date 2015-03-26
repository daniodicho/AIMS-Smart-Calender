
public class Routine extends Event{
    String days;

Routine(){
	name = null;
	days = "";
	startTime = null;
    endTime = null;
}

Routine(String n,String d, String s, String e){
	setName(n);
	setDays(d);
	setStartTime(s);
	setEndTime(e);
}

public String getDays(){
	return days;
}

public void setDays(String d){
	days = d;
}

}