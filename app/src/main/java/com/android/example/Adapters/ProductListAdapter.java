package com.android.example.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.example.Models.ProductListItem;
import com.android.example.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lokesh on 11/11/2016.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private ArrayList<ProductListItem> mProductDataList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnButtonClickListener mOnButtonClickListener;
    private final int BTN_BOOKMARK = 1, BTN_SIZE = 2, BTN_TAG = 3, ITEM_CLICK = 4;

    public ProductListAdapter(Context mContext, ArrayList<ProductListItem> mProductDataList) {
        this.mProductDataList = mProductDataList;
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.row_product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!mProductDataList.get(position).getLogoUrl().isEmpty())
            Picasso.with(mContext).load(mProductDataList.get(position).getLogoUrl())
                    .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                    .resize(mProductDataList.get(position).getLogoWidth(), mProductDataList.get(position).getLogoHeight())
                    .into(holder.ivProductImage);
        holder.tvProductName.setText(mProductDataList.get(position).getTitle());
        holder.tvProductDesc.setText(mProductDataList.get(position).getDescription());
        holder.btnBookmark.setText(mProductDataList.get(position).isBookmarked() ? R.string.btn_bookmarked : R.string.btn_bookmark);
        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonClickListenerValues(BTN_BOOKMARK, position);
            }
        });
        holder.btnCustom.setText((mProductDataList.get(position).getItemType().equals("F")) ? R.string.btn_tag : R.string.btn_size);
        holder.btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonClickListenerValues((mProductDataList.get(position).getItemType().equals("F")) ? BTN_TAG : BTN_SIZE, position);
            }
        });
        holder.llMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonClickListenerValues(ITEM_CLICK, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductDataList.size();
    }

    public void onDataSetChanged(ArrayList<ProductListItem> mProductDataList) {
        this.mProductDataList = mProductDataList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProductImage;
        public TextView tvProductName;
        public TextView tvProductDesc;
        public Button btnBookmark;
        public Button btnCustom;
        public LinearLayout llMainLayout;

        public ViewHolder(View v) {
            super(v);
            ivProductImage = (ImageView) v.findViewById(R.id.ivProductImage);
            tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            tvProductDesc = (TextView) v.findViewById(R.id.tvProductDesc);
            btnBookmark = (Button) v.findViewById(R.id.btnBookmark);
            btnCustom = (Button) v.findViewById(R.id.btnCustom);
            llMainLayout = (LinearLayout) v.findViewById(R.id.llMainLayout);
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(int btnType, int position);
    }

    public void setButtonClickListener(OnButtonClickListener mOnButtonClickListener) {
        this.mOnButtonClickListener = mOnButtonClickListener;
    }

    private void setButtonClickListenerValues(int btnType, int position) {
        this.mOnButtonClickListener.onButtonClick(btnType, position);
    }

}
