package com.example.sameer.justeatsearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdaptor extends BaseAdapter {
    private final Context context;
    private List<SearchedContent.RestaurantItem> itemList = null;

    static class ViewHolder {
        ImageView logo;
        TextView name;
        TextView cuisines;
        TextView rating;
    }

    public SearchListAdaptor(Context context) {
        this.context = context;
        itemList = SearchedContent.restaurantItemsList;
    }

    public int getCount() {
        int count = 0;
        if (itemList!=null)
            count = itemList.size();
        Log.d("SearchListAdaptor", ".......getCount:" + count);
        return count;
    }

    public Object getItem(int position) {
        Object item = null;
        if (itemList!=null)
            item = itemList.get(position);
        return item;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        Log.d("SearchListAdaptor", ".......getView @ " + position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.search_list_item, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.imageViewLogo);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.cuisines = (TextView) convertView.findViewById(R.id.textViewCuisineTypes);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.textViewRatings);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Now assign values
        SearchedContent.RestaurantItem item = itemList.get(position);
        viewHolder.name.setText( item.getName() );

        ArrayList<String> arrayList = item.getCuisineTypes();
        String cuisines = "";
        for ( String name:arrayList){
            cuisines += name;
            cuisines += ", ";
        }
        cuisines = cuisines.substring( 0, cuisines.length()-2 );//remove last 2 chars
        viewHolder.cuisines.setText( cuisines );

        viewHolder.rating.setText( String.format("%.2f", item.getRating()) );
        //Image loading 0
        Ion.with(viewHolder.logo).placeholder(R.mipmap.ic_launcher).load(item.getLogo());

        return convertView;
    }
}