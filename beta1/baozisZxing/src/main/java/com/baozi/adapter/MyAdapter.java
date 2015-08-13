package com.baozi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baoziszxing.R;

import java.util.List;
import java.util.Map;

//import com.shop.xtyk.myapplication.R;

/**
 * Created by User on 2015/8/3 003.
 */
//MyAdapter
public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;
    private OnClickListener onClickListener;

    public MyAdapter(Context context, List mData, OnClickListener onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.onClickListener=onClickListener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.item_npc_product, null);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.info = (TextView) convertView.findViewById(R.id.info);
            holder.deleteBtn = (ImageView) convertView.findViewById(R.id.delete_product);
            holder.editProduct= (LinearLayout) convertView.findViewById(R.id.edit_product);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.title.setText((String) mData.get(position).get("title"));
        holder.info.setText((String) mData.get(position).get("info"));

        holder.deleteBtn.setOnClickListener(onClickListener);
        holder.deleteBtn.setTag(position);
        
        holder.editProduct.setOnClickListener(onClickListener);
        holder.editProduct.setTag(position);
        

        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
        public TextView info;
        public ImageView deleteBtn;
        public LinearLayout editProduct;
    }
}
