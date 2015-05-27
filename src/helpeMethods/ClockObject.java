package helpeMethods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClockObject extends LinearLayout {

	public ClockObject(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.getContext();
		
	}
	
	public void timeSetter(String time){
		setMinutesLeft(time);
		setMinutesRight(time);
		setSecondsLeft(time);
		setSecondsRight(time);
	}
	
	public void setMinutesLeft(String time){	
		TextView minutesLeft = (TextView) this.getChildAt(0);
		minutesLeft.setText(time.charAt(0)+"");
	}
	
	public void setMinutesRight(String time){
		TextView minutesRight = (TextView) this.getChildAt(1);
		minutesRight.setText(time.charAt(1)+"");
	}
	
	public void setSecondsLeft(String time){
		TextView secondsLeft = (TextView) this.getChildAt(2);
		secondsLeft.setText(time.charAt(3)+"");
	}
	public void setSecondsRight(String time){
		TextView secondsRight = (TextView) this.getChildAt(3);
		secondsRight.setText(time.charAt(4)+"");
	}
}
