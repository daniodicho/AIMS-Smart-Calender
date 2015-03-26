import java.util.Date;
import java.util.LinkedList;

public class DaySlots {
	Date Date;
	LinkedList <Event> events = new LinkedList<Event>();
	TimeSlot[] slot;
	DaySlots(Date d){
		slot = new TimeSlot[48];
	    slot[0] = new TimeSlot("00:00","00:30",true);
	    slot[1] = new TimeSlot("00:30","01:00",true);
	    slot[2] = new TimeSlot("01:00","01:30",true);
	    slot[3] = new TimeSlot("01:30","02:00",true);
	    slot[4] = new TimeSlot("02:00","02:30",true);
	    slot[5] = new TimeSlot("02:30","03:00",true);
	    slot[6] = new TimeSlot("03:00","03:30",true);
	    slot[7] = new TimeSlot("03:30","04:00",true);
	    slot[8] = new TimeSlot("04:00","04:30",true);
	    slot[9] = new TimeSlot("04:30","05:00",true);
	    slot[10] = new TimeSlot("05:00","05:30",true);
	    slot[11] = new TimeSlot("05:30","06:00",true);
	    slot[12] = new TimeSlot("06:00","06:30",true);
	    slot[13] = new TimeSlot("06:30","07:00",true);
	    slot[14] = new TimeSlot("07:00","07:30",true);
	    slot[15] = new TimeSlot("07:30","08:00",true);
	    slot[16] = new TimeSlot("08:00","08:30",true);
	    slot[17] = new TimeSlot("08:30","09:00",true);
	    slot[18] = new TimeSlot("09:00","09:30",true);
	    slot[19] = new TimeSlot("09:30","10:00",true);
	    slot[20] = new TimeSlot("10:00","10:30",true);
	    slot[21] = new TimeSlot("10:30","11:00",true);
	    slot[22] = new TimeSlot("11:00","11:30",true);
	    slot[23] = new TimeSlot("11:30","12:00",true);
	    slot[24] = new TimeSlot("12:00","12:30",true);
	    slot[25] = new TimeSlot("12:30","13:00",true);
	    slot[26] = new TimeSlot("13:00","13:30",true);
	    slot[27] = new TimeSlot("13:30","14:00",true);
	    slot[28] = new TimeSlot("14:00","14:30",true);
	    slot[29] = new TimeSlot("14:30","15:00",true);
	    slot[30] = new TimeSlot("15:00","15:30",true);
	    slot[31] = new TimeSlot("15:30","16:00",true);
	    slot[32] = new TimeSlot("16:00","16:30",true);
	    slot[33] = new TimeSlot("16:30","17:00",true);
	    slot[34] = new TimeSlot("17:00","17:30",true);
	    slot[35] = new TimeSlot("17:30","18:00",true);
	    slot[36] = new TimeSlot("18:00","18:30",true);
	    slot[37] = new TimeSlot("18:30","19:00",true);
	    slot[38] = new TimeSlot("19:00","19:30",true);
	    slot[39] = new TimeSlot("19:30","20:00",true);
	    slot[40] = new TimeSlot("20:00","20:30",true);
	    slot[41] = new TimeSlot("20:30","21:00",true);
	    slot[42] = new TimeSlot("21:00","21:30",true);
	    slot[43] = new TimeSlot("21:30","22:00",true);
	    slot[44] = new TimeSlot("22:00","22:30",true);
	    slot[45] = new TimeSlot("22:30","23:00",true);
	    slot[46] = new TimeSlot("23:00","23:30",true);
	    slot[47] = new TimeSlot("23:30","24:00",true);
	    setDate(d);
	}
	
	public void setDate(Date date){
		Date = date;
	}
	
	public Date getDate(){
		return Date;
	}
	
	public void newTime(String startTime, String endTime){
		for(int i=0;i<48;i++){
			if(!((slot[i].getFinish().compareTo(startTime)<=0)||(slot[i].getStart().compareTo(endTime)>=0))){
				slot[i].setFree(false);
			}
		}
	}
	
	public void printAvailableTimes(){
		System.out.print(getDate()+ ":   ");
		boolean foundStart = false;
		for(int i=0;i<48;i++){
			if(foundStart&&(!slot[i].isFree())){
				System.out.print(slot[i-1].finish +"\t");
				foundStart = false;
			}
			if(slot[i].isFree()&!foundStart){
				System.out.print(slot[i].start + "-");
				foundStart = true;
			}
		}
		if(slot[47].isFree()){
			System.out.print(slot[47].finish);
		}
		System.out.println("");
	}
	
	public String getFirstBusyTime(){
		int i;
		for(i=10;i<47;i++){
			if(!slot[i].isFree())
				break;
		}
		return slot[i].getStart();
	}
	
	public void addEventInPlace(Event e){
		if(events.isEmpty()){
			events.add(e);			
		}
		else if(events.size()==1){
			if(e.startTime.compareTo(events.get(0).startTime)<0){
				events.add(0,e);
			}
			else{
				events.add(e);
			}
		}
		else{
		for(int i=0;i<events.size()-1;i++){
			if(e.startTime.compareTo(events.get(i).startTime)<0){
				events.add(i, e);
				return;
			}
			else if((e.startTime.compareTo(events.get(i).startTime)>0)&&(e.startTime.compareTo(events.get(i+1).startTime)<0)){
				events.add(i, e);
				return;
			}
		}
		events.add(e);
		}
	}
	
	public void printBusyTimes(){
		System.out.print(getDate()+ ":   ");
		for(int i=0;i<events.size();i++){
			System.out.println(events.get(i).getName()+" From "+events.get(i).getStartTime()+" To "+events.get(i).getEndTime()+"	");
		}
		System.out.println("");
	}
}
