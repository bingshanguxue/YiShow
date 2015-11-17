package com.bingshanguxue.tenyears;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/11/15.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements MyItemTouchHelperCallback.ItemTouchHelperAdapter{

    public interface OnAdapterListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onDataSetChanged();
    }

    private List<Contact> entityList;

    private OnAdapterListener adapterListener;
    public void setOnAdapterListener(OnAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    ContactAdapter(ArrayList<Contact> entityList){
        this.entityList = entityList;
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(entityList, i, i + 1);
            }
        }
        else{
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(entityList, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemoved(final int position) {
        //Remove this line if not using Google Analytics
        removeEntity(position);
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.ViewHolder holder, final int position) {
        Contact item = entityList.get(position);

        //Background color for each to-do item. Necessary for night/day mode
        int bgColor = Color.DKGRAY;
        //color of title text in our to-do item. White for night mode, dark gray for day mode
        int todoTextColor = Color.WHITE;

        holder.linearLayout.setBackgroundColor(bgColor);

        holder.tvName.setText(item.getName());
        holder.tvTelphone.setText(item.getTelphone());
//        holder.tvName.setTextColor(todoTextColor);
        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(item.getName().substring(0, 1),Color.BLACK);

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
        holder.ivHeader.setImageDrawable(myDrawable);
    }

    @Override
    public int getItemCount() {
        return entityList != null ? entityList.size() : 0;
    }

    @SuppressWarnings("deprecation")
    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        LinearLayout linearLayout;
        ImageView ivHeader;
        TextView tvName;
        TextView tvTelphone;

        public ViewHolder(View v){
            super(v);
            mView = v;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position < 0 || position >= entityList.size()){
//                        MLog.d(String.format("do nothing because posiion is %d when dataset changed.", position));
                        return;
                    }

                    notifyDataSetChanged();

                    if (adapterListener != null){
                        adapterListener.onItemClick(itemView, position);
                    }
                }
            });
            tvName = (TextView)v.findViewById(R.id.textName);
            tvTelphone = (TextView)v.findViewById(R.id.textTelphone);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
            ivHeader = (ImageView)v.findViewById(R.id.ivHeader);
            linearLayout = (LinearLayout)v.findViewById(R.id.itemview);
        }
    }

    public void setEntityList(List<Contact> entityList){
        this.entityList = entityList;
        notifyDataSetChanged();
    }

    /**
     * 删除列表项
     * */
    private void removeEntity(int position){
        try {
            if (entityList == null){
                return;
            }

            if (position < 0 || position >= entityList.size()){
//                        MLog.d(String.format("do nothing because posiion is %d when dataset changed.", position));
                return;
            }

            //删除数据库
            Contact entity = entityList.get(position);
            if (entity != null) {
                DataSupport.delete(Contact.class, entity.getId());
            }

            //刷新列表
            entityList.remove(position);
            notifyItemRemoved(position);

            if (adapterListener != null) {
                adapterListener.onDataSetChanged();
            }
        } catch (Exception e) {
            Log.e("ContactAdapter", e.toString());
        }
    }
}
