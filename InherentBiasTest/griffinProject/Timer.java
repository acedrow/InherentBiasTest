package griffinProject;

import java.util.Vector;

/*
 * Takes two wall-clock time snapshots, once the 2nd snapshot is taken, 
 * computes the difference between the two and adds the difference in ms to a Vector.
 */


public class Timer {
	long time1;
	long time2;
	Vector<Long> timeLog;
	
	public Timer(){
		time1 = 0L;
		time2 = 0L;
		timeLog = new Vector<Long>();
	}
	
	public void startSnapShot(){
		time1 = System.currentTimeMillis();
	}
	
	public void endSnapShot(){
		time2 = System.currentTimeMillis();
		long difference = time2 - time1;
		timeLog.add(difference);
		reset();
		
	}
	
	public void reset (){
		time1 = 0L;
		time2 = 0L;
	}
	
	public long getTime(int index){
		return timeLog.get(index);
	}
}

 
