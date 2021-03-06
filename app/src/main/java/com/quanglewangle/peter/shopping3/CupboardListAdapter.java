package com.quanglewangle.peter.shopping3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.quanglewangle.peter.shopping3.R.id.quantity;


public class CupboardListAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, String>> products;
    private final Context context;

	CupboardListAdapter(Context context, ArrayList<HashMap<String, String>>products) {
        Log.d("In CupboardListAdapter", "");
        this.context = context;
        this.products = products;
	}
	
	@Override
	public int getCount() {
		return  products.size();
	}
 
	@Override
	public Object getItem(int position) {
		return products.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void update(ArrayList<HashMap<String, String>> products_){
		Log.d("updating", "products_ size: " + products_.size() + " products size: "+ products.size() );
	//	this.products.clear();
		Log.d("updating", "products size: " + products_.size());
        this.products = new ArrayList<HashMap<String, String>>(products_);
		this.products.addAll(products_);
        notifyDataSetChanged();
    }
	public void deleteItem (int position) {
		products.remove(position);
	}

	static class ViewHolderItem {
		TextView description;
		TextView basket;
		TextView quantity;
	}

	@Override

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolderItem holder = new ViewHolderItem();
		if (convertView == null) {
		 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.cupboard_list_item, null);

	        holder.quantity = (TextView) convertView.findViewById(quantity);
			holder.description = (TextView) convertView.findViewById(R.id.description);

			convertView.setTag(holder);
		} else {
			 holder = (ViewHolderItem) convertView.getTag();
		}
        holder.quantity.setText(products.get(position).get("quantity"));
		holder.description.setText(products.get(position).get("description"));

		return convertView;
	}
}
