//Over engineered ~~garbage~~ Class to make timing lots of thing easier (hopefully)
public class Metrics {
	private long[] timerArray;
	private int nextAvail = 0;
	boolean autoIncrease = false;
	int increaseByX;
	//int totalTimers = timerArray.length;
	
	//Use this constructor if you don't want it to increase the array size
	public Metrics(int initialTimers) {
		timerArray = new long[initialTimers];
	}
	
	//Use this constructor if you want it to auto increase the array size
	public Metrics(int initialTimers, int increaseByX) {
		timerArray = new long[initialTimers];
		this.increaseByX = increaseByX;
	}
	
	/* This method will pick the first available timer and returns it's id.
	 * Calls findNextAvail() before returning id
	 */
	public int startTime() {
		timerArray[nextAvail] = System.nanoTime();
		int idUsed = nextAvail;
		findNextAvail();
		return idUsed;
	}
	
	/* This method allows you to specify which timer it should use by id.
	 * Calls findNextAvail if the id specified is equal to nextAvail
	 */
	public void startTime(int id) {
		if(id == nextAvail) {
			findNextAvail();
		}
		timerArray[id] = System.nanoTime();
	}
	
	/* This method will return the time in nanoseconds from when the timer was started to when this method was called.
	 * Sets nextAvail to the id if it's lower than nextAvail
	 */
	public long endTime(int id) {
		long time = System.nanoTime();
		time -= timerArray[id];
		timerArray[id] = 0;
		if(nextAvail == -1) {
			nextAvail = id;
		} else {
			nextAvail = id < nextAvail ? id : nextAvail;
		}
		return time;
	}
	
	/* Method to force the timerArray to increase in size even if the first constructor was used.
	 * Can also be used with auto increase enabled.
	 */
	public void forceIncrease(int increaseByX) {
		int temp = timerArray.length;
		increaseArraySize(true, increaseByX);
		if(nextAvail == -1) {
			nextAvail = temp;
		}
	}
	
	/* Private method to find the next available timer.
	 * If there are no timers left it will call increaseArraySize() with autoIncrease and increaseByX
	 * otherwise it will set nextAvail to -1
	 */
	private void findNextAvail() {
		while(nextAvail++ + 1 != timerArray.length) {
			if(timerArray[nextAvail] == 0) {
				return;
			}
		}
		increaseArraySize(autoIncrease, increaseByX);
	}
	
	/* Private method to increase the array size by copying it to a temp array,
	 * overwriting the old one with a larger array and then copying the contents of the temp array back into it
	 */
	private void increaseArraySize(boolean increase, int increaseByX) {
		if(!increase) {
			nextAvail = -1;
			return;
		}
		long[] temp = new long[timerArray.length];
		System.arraycopy(timerArray, 0, temp, 0, timerArray.length);
		timerArray = new long[temp.length + increaseByX];
		System.arraycopy(temp, 0, timerArray, 0, temp.length);
	}
}