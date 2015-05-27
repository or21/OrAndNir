package helpeMethods;

import com.main.divvyapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClockObject extends LinearLayout {

//	TextView minutesLeft;
//	TextView minutesRight;
//	TextView secondsLeft;
//	TextView secondsRight;
//	String time;

	public ClockObject(Context context, AttributeSet attrs) {
		super(context,attrs);
//		this.time = time;
		

//		
//		minutesLeft = (TextView) this.getChildAt(0);
//		minutesRight = (TextView) this.getChildAt(1);
//		secondsLeft = (TextView) this.getChildAt(2);
//		secondsRight = (TextView) this.getChildAt(3);
//		
//		setTextViewStyle(minutesLeft);
//		setTextViewStyle(minutesRight);
//		setTextViewStyle(secondsLeft);
//		setTextViewStyle(secondsRight);
		

		this.getContext();
		
	}
	
	public void timeSetter(String time){
		setMinutesLeft(time);
		setMinutesRight(time);
		setSecondsLeft(time);
		setSecondsRight(time);
	}
	
	public void setTextViewStyle(TextView textView){
		
//		textView.setWidth(20);
//		textView.setHeight(20);
//		textView.setBackgroundColor(getResources().getColor(R.color.appColor));
//		textView.setTextColor(0xffffffff);
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
