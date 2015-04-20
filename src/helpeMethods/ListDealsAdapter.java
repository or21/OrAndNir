package helpeMethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private final String SERVER = "http://nir.milab.idc.ac.il/pictures/";
	//Store Map
	private static final Map<String, Integer> constantStores;
	static
	{
		constantStores = new HashMap<String, Integer>();
		constantStores.put("newLogo", R.drawable.newlogo);
		constantStores.put("castro", R.drawable.castro);
	}


	private DealObj deal;
	private ViewHolder holder;
	Context context;
	protected List<DealObj> listDeals;
	private LayoutInflater inflater;

	// Constructor
	public ListDealsAdapter(Context context, List<DealObj> listDeals) {
		this.listDeals = listDeals;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
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
		//		return listDeals.get(position).getPicture();
		return 100;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			holder = new ViewHolder();
			convertView = this.inflater.inflate(R.layout.layout_list_item,
					parent, false);

			holder.dealName = (TextView) convertView.findViewById(R.id.txt_id);
			holder.storId = (TextView) convertView.findViewById(R.id.txt_storeid);
			holder.category = (TextView) convertView.findViewById(R.id.txt_category);

			holder.claimedBy = (TextView) convertView.findViewById(R.id.txt_claimedBy);
			holder.picture = (ImageView) convertView.findViewById(R.id.img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		deal = listDeals.get(position);
		holder.dealName.setText(deal.getDealName());
		holder.storId.setText(deal.getStoreId());
		holder.category.setText(deal.getCategory());

		if (deal.getClaimedBy().length() > 15) {
			holder.claimedBy.setText("Pending");
		}

		// Image setup
		String url = SERVER + deal.getPicture();
		Picasso.with(context).load(url).into(holder.picture);

		return convertView;
	}


	private class ViewHolder {
		//		 TextView id;
		TextView dealName;
		TextView storId;
		TextView category;
		TextView claimedBy;
		ImageView picture;
	}

}
