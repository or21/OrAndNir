package helpeMethods;

import java.util.List;

import com.main.divvyapp.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListDealsAdapter extends BaseAdapter {

	final static String SERVER = "http://nir.milab.idc.ac.il/pictures/";

	private Context context;
	protected List<DealObj> listDeals;
	private LayoutInflater inflater;
	int resource;

	// Constructor
	public ListDealsAdapter(Context context, List<DealObj> listDeals, int resource) {
		this.listDeals = listDeals;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.resource = resource;
	}

	@Override
	public int getCount() {
		return listDeals.size();
	}

	@Override
	public Object getItem(int position) {
		return listDeals.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = this.inflater.inflate(resource, parent, false);

			holder.dealName = (TextView) convertView.findViewById(R.id.txt_dealName);
			holder.storId = (TextView) convertView.findViewById(R.id.txt_storeid);
			holder.category = (TextView) convertView.findViewById(R.id.txt_category);

			holder.claimedBy = (TextView) convertView.findViewById(R.id.txt_claimedBy);
			holder.picture = (ImageView) convertView.findViewById(R.id.img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DealObj deal = listDeals.get(position);
		holder.dealName.setText(deal.getDealName());
		holder.storId.setText(deal.getStoreId());
		holder.category.setText(deal.getCategory());

		if (deal.getClaimedBy().length() > 15) {
			holder.claimedBy.setText("Pending | " + deal.getUserNameClaimed());
		} 
		else if (!deal.getClaimedBy().isEmpty()) {
			holder.claimedBy.setText(deal.getUserNameClaimed().toString());
		} else {
			holder.claimedBy.setText("");
		}

		// Image setup
		String url = SERVER + deal.getPicture();
		Picasso.with(context).load(url).into(holder.picture);

		return convertView;
	}

	private class ViewHolder {
		TextView dealName;
		TextView storId;
		TextView category;
		TextView claimedBy;
		ImageView picture;
	}
}
