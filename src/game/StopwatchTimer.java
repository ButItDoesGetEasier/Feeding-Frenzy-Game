package game;

public class StopwatchTimer extends Thread { // used for status bar and results (time alive)
	int hr, min, sec;

	StopwatchTimer() {} // empty as there are no needed attributes

	public void run() {
		for(hr = 0; hr < 24; hr++){ // loop from 0 to 23 (hours)
			for(min = 0; min < 60; min++){ // loop from 0 to 59 (minutes)
				for(sec = 0; sec < 60; sec++){ // loop from 0 to 59 (seconds)
					try {
						// wait 1 sec before counting up
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	String getTime() { // return string based on hour, minute, and time
		String hour, minute, second; // string versions

		if (this.hr < 10) {hour = "0"+this.hr;} // if hour is less than 10, append a 0 in front for formatting
		else {hour = Integer.toString(this.hr);} // else, just convert to string

		if (this.min < 10) {minute = "0"+this.min;} // if minute is less than 10, append a 0 in front for formatting
		else {minute = Integer.toString(this.min);} // else, just convert to string

		if (this.sec < 10) {second = "0"+this.sec;} // if second is less than 10, append a 0 in front for formatting
		else {second = Integer.toString(this.sec);} // else, just convert to string

		return hour+":"+minute+":"+second; // return concatenated strings
	}
}
