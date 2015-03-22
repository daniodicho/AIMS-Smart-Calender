public class TimeSlot {
	String start;
	String finish;
	boolean free;
	
	TimeSlot(String s, String f,boolean b){
		start = s;
		finish = f;
		free = b;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
}
