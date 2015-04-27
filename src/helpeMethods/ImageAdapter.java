package helpeMethods;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	
	 // The activity
	 private Context activity;
	 
	 //stores from database
	 private List<String> recievedStoreList;
	 
	  // references to our images
	  private List<Integer> imageList;


	    
	 public ImageAdapter(Context c, List<String> recievedStoreList) {
		 this.setRecievedStoreList(recievedStoreList);
		 
		 activity = c;
	     
	     imageList = new ArrayList<Integer>();
	     
	    }
	 
	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(activity);
	            imageView.setLayoutParams(new GridView.LayoutParams(255, 255));
	            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        imageView.setImageResource(imageList.get(position));
	        return imageView;
	    }


	    
	    public int getCount() {
	        return imageList.size();
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

		public List<String> getRecievedStoreList() {
			return recievedStoreList;
		}

		public void setRecievedStoreList(List<String> recievedStoreList) {
			this.recievedStoreList = recievedStoreList;
		}


}
