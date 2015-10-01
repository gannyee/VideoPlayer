package com.views;

/**
 * Calculate current time and total time
 * @author ganyee
 *
 */
public class VideoTime {
	
	private int secondTotal;
	private int minitueTotal;
	private int secondCurrent;
	private int minitueCurrent;
	
	public void timeCalculate(long totalTime,long currentTime){
		totalTime = totalTime / 1000;
		minitueTotal = (int) (totalTime / 60);
		secondTotal = (int) (totalTime % 60);
		
		currentTime = currentTime / 1000;
		minitueCurrent = (int) (currentTime / 60);
		secondCurrent = (int) (currentTime % 60);
		
	}
	
	public int getSecondTotal() {
		return secondTotal;
	}
	public int getMinitueTotal() {
		return minitueTotal;
	}

	public int getSecondCurrent() {
		return secondCurrent;
	}

	public int getMinitueCurrent() {
		return minitueCurrent;
	}
	
	
}
