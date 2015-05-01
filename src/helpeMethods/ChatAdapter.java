package helpeMethods;

import java.util.ArrayList;

import com.main.divvyapp.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<ChatItem> {
	
    private static class ViewHolder {
        private ListView itemView;
        TextView otherUserAndDate;
        TextView dealDescriptionAndStore;
        ImageView dealImage;

		@SuppressWarnings("unused")
		public ListView getItemView() {
			return itemView;
		}
    }

    public ChatAdapter(Context context, int textViewResourceId, ArrayList<ChatItem> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder viewHolder;
    	ChatItem item = getItem(position);
    	
    	if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
            .inflate(R.layout.chats_list, parent, false);

            viewHolder = new ViewHolder();
            
            viewHolder.otherUserAndDate = (TextView) convertView.findViewById(R.id.other_user_and_date);
            viewHolder.dealDescriptionAndStore = (TextView) convertView.findViewById(R.id.deal_description_chat_and_store);
            viewHolder.dealImage = (ImageView) convertView.findViewById(R.id.deal_image);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
    	viewHolder.otherUserAndDate.setText(item.getOtherUser() + " | \n" + item.getDate());
        viewHolder.dealDescriptionAndStore.setText(item.getDealdescription() + "\n" + item.getStoreid());
        
        String url = ListDealsAdapter.SERVER + item.getPicture();
		Picasso.with(getContext()).load(url).into(viewHolder.dealImage);
    	
        return convertView;
    }
}