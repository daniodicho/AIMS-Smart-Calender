package csun.aims.aimssmartcalendar;

import java.lang.*;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Date;

public class Cal {

	LinkedList <Routine>routines = new LinkedList<Routine>();
	LinkedList <Class>classes = new LinkedList<Class>();

	LinkedList <Assignment> current = new LinkedList<Assignment>();
	DaySlots[] slots;
	Date date = new Date();
	GregorianCalendar calendar ;
	Date today ;
	int day = 0;
	int sleepTime;
	
	
	Cal(){
		sleepTime = 7;
		slots = new DaySlots[14];
		
		resetToday();
		for(int i=0;i<14;i++){
			slots[i] = new DaySlots(today);
			goToNextDay();
		}
		resetToday();
	}
	
	public void resetToday(){
		calendar = new GregorianCalendar();
		today = calendar.getTime();
	}
	
	public void goToNextDay(){
		calendar.add(calendar.DAY_OF_MONTH, 1);
		today = calendar.getTime();
	}
	
	public void addToDay(String name, Date date, String start, String finish){
		if(start.compareTo(finish)<0)
		{
			for(int i=0;i<14;i++){
				if((slots[i].getDate().getMonth() == date.getMonth())&&(slots[i].getDate().getDate() == date.getDate())){
					slots[i].newTime(start, finish);
					slots[i].addEventInPlace(new Event(name,start,finish));

				}
			}
		}
		else if(start.compareTo(finish)>0)
		{
			for(int i=0;i<14;i++){
				if((slots[i].getDate().getMonth() == date.getMonth())&&(slots[i].getDate().getDate() == date.getDate())){
					slots[i].newTime(start, "24:00");
					slots[i].addEventInPlace(new Event(name,"24:00",finish));
					slots[i+1].newTime("00:00", finish);
					slots[i+1].addEventInPlace(new Event(name,"00:00",finish));

				}
			}
		}
		
	}
	
	public void addToDay(int index, String start, String finish){
		if(start.compareTo(finish)<0)
		{
			slots[index].newTime(start, finish);
		}
		else if(start.compareTo(finish)>0)
		{
			slots[index].newTime(start, "24:00");
			slots[index+1].newTime("00:00", finish);
		}
	}
	
	
	public void addClass(Class c){
		addRoutine(c);
		classes.add(c);
	}
	
	public void addRoutine(Routine r){
		routines.add(r);
	}
	
	public void deleteClass(Class c){
		deleteRoutine(c);
		for(int i=0;i<classes.size();i++){
			if ( classes.get(i).name == c.name){
				classes.remove(i);
			}
		}
	}
	
	void deleteRoutine(Routine r){
		for(int i=0;i<routines.size();i++){
			if ( routines.get(i).name == r.name){
				routines.remove(i);
			}
		}
	}
	
	public void update(){
		
		//Sets routine times to busy times
		for(int i=0;i<7;i++)
		{
			int day = slots[i].getDate().getDay();
			String daysChars[] = {"N","M","T","W","R","F","S"};
			for(int j=0;j<routines.size();j++){
				if(routines.get(j).getDays().contains(daysChars[day])){
					addToDay(routines.get(j).getName(),slots[i].getDate(),routines.get(j).startTime,routines.get(j).endTime);
		//			slots[i].addEventInPlace(new Event(routines.get(j).getName(),routines.get(j).startTime,routines.get(j).endTime));
					addToDay(routines.get(j).getName(),slots[i+7].getDate(),routines.get(j).startTime,routines.get(j).endTime);
			//		slots[i+7].addEventInPlace(new Event(routines.get(j).getName(),routines.get(j).startTime,routines.get(j).endTime));

				}
			}
		}
		
		for(int i=0;i<7;i++)
		{
			String time = slots[i].getFirstBusyTime();
			if(time.compareTo("12:00")<0){
				StringInt si = timeShift(-10,time);

				if(si.i==0){
				addToDay("Sleep",slots[i].getDate(),si.s,time);
				//slots[i].addEventInPlace(new Event("Sleep",si.s,time));
				addToDay("Sleep",slots[i+7].getDate(),si.s,time);
				//slots[i+7].addEventInPlace(new Event("Sleep",si.s,time));

				}
				else if(i!=0){
					addToDay("Sleep",slots[i].getDate(),"00:00",time);

					addToDay("Sleep",slots[i+si.i].getDate(),si.s,"24:00");
					//slots[i+si.i].addEventInPlace(new Event("Sleep",si.s,"24:00"));
					//slots[i].addEventInPlace(new Event("Sleep","00:00",time));

					addToDay("Sleep",slots[i+7+si.i].getDate(),si.s,"24:00");
					//slots[i+7+si.i].addEventInPlace(new Event("Sleep",si.s,"24:00"));
					addToDay("Sleep",slots[i+7].getDate(),"00:00",time);
					//slots[i+7].addEventInPlace(new Event("Sleep","00:00",time));

				}
			}
			else{
				addToDay("Sleep",slots[i].getDate(),"02:00","10:00");
				//slots[i].addEventInPlace(new Event("Sleep","01:00","11:00"));

				addToDay("Sleep",slots[i+7].getDate(),"02:00","10:00");
				//slots[i+7].addEventInPlace(new Event("Sleep","00:00","11:00"));

			}
		}
		assignBlocks();
	//	assignReading();
	}
	
	
	
	public void addInPlace(Assignment e){
		if(current.isEmpty()){
			current.add(e);			
		}
		else if(current.size()==1){
			if(e.Priority > current.get(0).Priority){
				current.add(0,e);
			}
			else{
				current.add(e);
			}
		}
		else{
		for(int i=0;i<current.size()-1;i++){
			if(e.Priority>current.get(i).Priority){
				current.add(i, e);
				return;
			}
			else if((e.Priority<current.get(i).Priority)&&(e.Priority<current.get(i+1).Priority)){
				current.add(i, e);
				return;
			}
		}
		current.add(e);
		}
	}
	
	public void assignBlocks(){
		for(int i=0;i<current.size();i++){
			if(current.get(i).getAllocatedTime()<7){
				if(!findBigBlock(current.get(i))){
					breakToPieces(current.get(i));
					System.out.println("not found");
				}
			}
			else{
				breakToPieces(current.get(i));
				System.out.println("Too Big");
			}
		}
	}
	
	public boolean findBigBlock(Assignment a){
		long l = a.getDueDate();
		int bestMax=0;
		int bestIndex=0;
		StringInt si = null;

		for(int i=0;(i<14)&&(l-dateToInt(slots[i].getDate()))>1000000;i++){
			si = slots[i].getMaxConsecutive(); 
		//	System.out.println(si.s+"	"+timeShiftForBlocks(a.AllocatedTime,si.s).s);
			if(si.i>a.AllocatedTime){
				if(si.i>bestMax){
					bestMax=si.i;
					bestIndex=i;
				}
			}
		}
		if(bestMax!=49){
			si = slots[bestIndex].getMaxConsecutive(); 
			addToDay(a.getName(), slots[bestIndex].getDate(), si.s, timeShiftForBlocks(a.AllocatedTime,si.s).s);
			return true;
		}
		else{
			return false;
		}
		
	}
	
	boolean breakToPieces(Assignment a){
		
		if(a.getAllocatedTime()%2==0){
			a.AllocatedTime = a.AllocatedTime/2;
			if(!findBigBlock(a)){
				breakToPieces(a);
				System.out.println("Looking for"+a.AllocatedTime+"block");
			}
		//	System.out.print("breaking block"+a.AllocatedTime);
		//	slots[4].printAvailableTimes();
			if(!findBigBlock(a)){
				breakToPieces(a);
				System.out.println("Looking for"+a.AllocatedTime+"block");
			}
			return true;
		}
		else{
			a.AllocatedTime = (a.AllocatedTime/2)+1;
			if(!findBigBlock(a)){
				breakToPieces(a);
				System.out.println("Looking for"+a.AllocatedTime+"block");
			}
			a.AllocatedTime--;
			if(!findBigBlock(a)){
				breakToPieces(a);
				System.out.println("Looking for"+a.AllocatedTime+"block");

			}
			return true;
		}
	}
	public long dateToInt(Date d){
		long temp;
		temp = d.getMinutes() + 100*d.getHours() + 10000*d.getDate() + 1000000*d.getMonth()+ (d.getYear()%100)*100000000;
		return temp;
	}
	
	
	StringInt timeShift(int i, String s){ 
		int extra = 0;
		String first2 = s.substring(0, 2);
		int n = Integer.parseInt(first2);
		n+=i;
		if((i>=0)&&(n>24)){
			n=n-24;
			extra++;
		}
		else if((i<0)&&(n<0)){
			n=n+24;
			extra--;
		}
		String newfirst2;
		if(n<10){
			newfirst2 = "0"+Integer.toString(n);
		}
		else{
			newfirst2 = Integer.toString(n);								
		}
		String newStart = s.replace(first2, newfirst2);
		return new StringInt(newStart,extra);
	}
	
	
	StringInt timeShiftForBlocks(int i, String s){
		StringInt si1 = timeShift(i/2,s);
		if(i%2==1){
			String first2 = si1.s.substring(0, 2);
			String last2 = si1.s.substring(3, 5);
			int f = Integer.parseInt(first2);
			int l = Integer.parseInt(last2);
			if(l<30){
				l+=30;
			}
			else{
				l-=30;
				f+=1;
			}
			if(f>=24&&l>0){
				si1.i++;
			}
			String newfirst2;
			if(f<10){
				newfirst2 = "0"+Integer.toString(f);
			}
			else{
				newfirst2 = Integer.toString(f);								
			}
			String newlast2;
			if(l<10){
				newlast2 = "0"+Integer.toString(l);
			}
			else{
				newlast2 = Integer.toString(l);								
			}
			String newStart = si1.s.replace(first2, newfirst2);
			newStart = newStart.replace(last2, newlast2);

			return new StringInt(newStart,si1.i);
		}
		return si1;
	}
	
	/*public void assignReading(){
		for(int i=0;i<classes.size();i++){
			if(classes.get(i).requiresReading){
				findBigBlock(new Assignment("Reading for "+classes.get(i).getName(),classes.get(i),
						))
			}
		}
	}*/
}


