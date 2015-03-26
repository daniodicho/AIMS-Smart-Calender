//package csun.aims.aimssmartcalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

public class Assignment {

	int Id;
	String Name; 
	Class Course;
	long DueDateandTime;
	//String DueTime;
	int Type ;
	double Priority;
	String SuggestedTime;
	int AllocatedTime;
	String ActualCompletedTime;
	Boolean Finished;
	double Grade ;
	
	Assignment(String newName, Class newClass, long newDueDate,  int newType ){
		Name = newName;
		DueDateandTime = newDueDate;
		//DueTime = newDueTime;
		Type = newType;
		Course = newClass;
		Finished = false;
		Priority = calculatePriority();
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	public Class getCourse() {
		return Course;
	}
	public void setCourse(Class theClass) {
		Course = theClass;
	}
	public long getDueDate() {
		return DueDateandTime;
	}
	public void setDueDate(long dueDate) {
		DueDateandTime = dueDate;
	}
	
	public String getType() {
		if(Type==0){
			return "homework";
		}
		else if (Type ==1){
			return "project";
		}
		else
		{
			return "test";
		}
	}
	public void setType(int type) {
		Type = type;
	}
	public double getPriority() {
		return Priority;
	}
	public void setPriority(double priority) {
		Priority = priority;
	}
	public String getSuggestedTime() {
		return SuggestedTime;
	}
	public void setSuggestedTime(String suggestedTime) {
		SuggestedTime = suggestedTime;
	}
	public int getAllocatedTime() {
		return AllocatedTime;
	}
	public void setAllocatedTime(int type, int units, int diff) {
		AllocatedTime = (int) (((3 * (type + 1)/(units)) * (diff/2.5)) * 2);
	}
	public String getActualCompletedTime() {
		return ActualCompletedTime;
	}
	public void setActualCompletedTime(String actualCompletedTime) {
		ActualCompletedTime = actualCompletedTime;
	}
	public Boolean getFinished() {
		return Finished;
	}
	public void setFinished(Boolean finished) {
		Finished = finished;
	}
	public double getGrade() {
		return Grade;
	}
	public void setGrade(double grade) {
		Grade = grade;
	}
	public int getId(){
		return this.Id;
	}
	public void setId(int newId){
		this.Id = newId;
	}
	public double calculatePriority(){
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:MM:SS");
       
        DateFormat df = new SimpleDateFormat("YYMMddHHmm");
        long d = getDueDate();
        

        
        String now = df.format(new Date());
        int nowInt = Integer.parseInt(now);
        
        long diff = (d - nowInt)+((d%10000) -(nowInt%10000))*3;

		return 10000*(7.0/(diff) )* (1+Type)*(1/getCourse().getCurrentGrade())*getCourse().getDifficulty();
		
	}
	
}
