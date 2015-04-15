package helpeMethods;

import java.util.ArrayList;

import com.main.divvyapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FilterAdatpter extends ArrayAdapter<FilterObj>{
	
	 private static class ViewHolder {
	        private ListView itemView;
	        TextView categoryName;
	        ImageView img;

			@SuppressWarnings("unused")
			public ListView getItemView() {
				return itemView;
			}
	    }

	    public FilterAdatpter(Context context, int textViewResourceId, ArrayList<FilterObj> items) {
	        super(context, textViewResourceId, items);
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	    	ViewHolder viewHolder;
	    	FilterObj item = getItem(position);
	    	
	    	if (convertView == null) {
	            convertView = LayoutInflater.from(this.getContext())
	            .inflate(R.layout.filter_list_item, parent, false);

	            viewHolder = new ViewHolder();
	            
	            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
	            viewHolder.img = (ImageView) convertView.findViewById(R.id.cat_img);
	            
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        viewHolder.categoryName.setText(item.getCategoryName());
	        viewHolder.img.setImageResource(item.getImage());
	        
	        return convertView;
	    }

}
