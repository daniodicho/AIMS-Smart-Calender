import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Date;

public class Calendar {

	LinkedList <Routine>routines = new LinkedList<Routine>();
	LinkedList <Class>classes = new LinkedList<Class>();

	LinkedList <Assignment> current = new LinkedList<Assignment>();
	DaySlots[] slots;
	Date date = new Date();
	GregorianCalendar calendar ;
	Date today ;
	int day = 0;
	int sleepTime;
	
	
	Calendar(){
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
	//		if(date.)
	//		addToDay(new Date(date.getYear(),date.getMonth(),date.getDate()+1,date.getHours(),date.getMinutes()),"00:00",finish);
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
	/*	for(int i=0;i<routines.size();i++){
			String s = routines.get(i).getDays();
			for(int j=0;j<s.length();j++){
				switch (s.charAt(j)){
				case 'S':
					addToDay(slots[7-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					addToDay(slots[14-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					if(routines.get(i).startTime.compareTo("12:00")<0){
							StringInt si = timeShift(-10,routines.get(i).startTime);

							if(si.i==0){
							addToDay(slots[7-firstDay].getDate(),si.s,routines.get(i).startTime);
							addToDay(slots[14-firstDay].getDate(),si.s,routines.get(i).startTime);
							}
							else if(si.i==-1){
								addToDay(slots[7-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[7-firstDay].getDate(),"00:00",routines.get(i).startTime);

								addToDay(slots[14-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[14-firstDay].getDate(),"00:00",routines.get(i).startTime);	
							}
					}
					break;
				case 'M':
					if(firstDay>1){
						addToDay(slots[8-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[15-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						if(routines.get(i).startTime.compareTo("12:00")<0){
							StringInt si = timeShift(-10,routines.get(i).startTime);

							if(si.i==0){
							addToDay(slots[8-firstDay].getDate(),si.s,routines.get(i).startTime);
							addToDay(slots[15-firstDay].getDate(),si.s,routines.get(i).startTime);
							}
							else if(si.i==-1){
								addToDay(slots[8-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[8-firstDay].getDate(),"00:00",routines.get(i).startTime);

								addToDay(slots[15-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[15-firstDay].getDate(),"00:00",routines.get(i).startTime);	
							}
						}
					}
					else
					{
						addToDay(slots[1-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[8-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						if(routines.get(i).startTime.compareTo("12:00")<0){
							StringInt si = timeShift(10,routines.get(i).startTime);

							if(si.i==0){
							addToDay(slots[8-firstDay].getDate(),si.s,routines.get(i).startTime);
							addToDay(slots[1-firstDay].getDate(),si.s,routines.get(i).startTime);
							}
							else if(si.i==-1){
								addToDay(slots[8-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[8-firstDay].getDate(),"00:00",routines.get(i).startTime);

								addToDay(slots[1-firstDay+si.i].getDate(),si.s,"24:00");
								addToDay(slots[1-firstDay].getDate(),"00:00",routines.get(i).startTime);	
							}
					}
					}
					
					break;
				case 'T':
					if(firstDay>2){
						addToDay(slots[9-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[16-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					else
					{
						addToDay(slots[2-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[9-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					break;
				case 'W':
					if(firstDay>3){
						addToDay(slots[10-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[17-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					else
					{
						addToDay(slots[3-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[10-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					break;
				case 'H':
					if(firstDay>4){
						addToDay(slots[11-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[18-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					else
					{
						addToDay(slots[4-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[11-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					break;
				case 'F':
					if(firstDay>5){
						addToDay(slots[12-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[19-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					else
					{
						addToDay(slots[5-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
						addToDay(slots[12-firstDay].getDate(),routines.get(i).startTime,routines.get(i).endTime);
					}
					break;
				}
			}
		}*/
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
	
}


