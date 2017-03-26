package com.myself.newsapp.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.myself.newsapp.R;
import com.myself.newsapp.na_store.GoodsDetailActivity;
import com.myself.newsapp.na_store.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 商品列表
 * Created by Jusenr on 2017/03/25.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private Context mContext;
    private List<AVObject> mList;

    public StoreAdapter(List<AVObject> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_main, parent, false));
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, final int position) {
        Picasso.with(mContext)
                .load(mList.get(position).getAVFile("image") == null ? "www" : mList.get(position).getAVFile("image").getUrl())
                .transform(new RoundedTransformation(9, 0))
                .into(holder.mPicture);
        holder.mTitle.setText(mList.get(position).getAVUser("owner") == null ? "" : mList.get(position).getAVUser("owner").getUsername());
        holder.mSubtitle.setText((CharSequence) mList.get(position).get("title"));
        holder.mPrice.setText(mList.get(position).get("price") == null ? "￥" : "￥ " + mList.get(position).get("price"));
        holder.mIntegral.setText(mList.get(position).get("integral") == null ? "0" : String.valueOf(mList.get(position).get("integral")));

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("goodsObjectId", mList.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        private CardView mItem;
        private ImageView mPicture;
        private TextView mTitle;
        private TextView mSubtitle;
        private TextView mPrice;
        private TextView mIntegral;

        public StoreViewHolder(View itemView) {
            super(itemView);
            mItem = (CardView) itemView.findViewById(R.id.item_main);
            mPicture = (ImageView) itemView.findViewById(R.id.item_picture);
            mTitle = (TextView) itemView.findViewById(R.id.item_maintitle);
            mSubtitle = (TextView) itemView.findViewById(R.id.item_subtitle);
            mPrice = (TextView) itemView.findViewById(R.id.item_price);
            mIntegral = (TextView) itemView.findViewById(R.id.item_integral);
        }
    }
}
