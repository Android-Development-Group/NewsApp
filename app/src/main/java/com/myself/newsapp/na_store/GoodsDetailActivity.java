//package com.myself.newsapp.na_store;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.avos.avoscloud.AVException;
//import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.GetCallback;
//import com.myself.newsapp.R;
//import com.myself.newsapp.base.TitleActivity;
//import com.squareup.picasso.Picasso;
//
//import butterknife.BindView;
//
///**
// * 商品详情页
// * Created by Jusenr on 2017/03/25.
// */
//public class GoodsDetailActivity extends TitleActivity {
//
//    @BindView(R.id.name_detail)
//    TextView mNameDetail;
//    @BindView(R.id.description_detail)
//    TextView mDescriptionDetail;
//    @BindView(R.id.image_detail)
//    ImageView mImageDetail;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_goods_detail;
//    }
//
//    @Override
//    protected void onViewCreatedFinish(Bundle saveInstanceState) {
//        addNavigation();
//
//        String goodsObjectId = getIntent().getStringExtra("goodsObjectId");
//        AVObject avObject = AVObject.createWithoutData("Product", goodsObjectId);
//        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
//            @Override
//            public void done(AVObject avObject, AVException e) {
//                mNameDetail.setText(avObject.getAVUser("owner") == null ? "" : avObject.getAVUser("owner").getUsername());
//                mDescriptionDetail.setText(avObject.getString("description"));
//                Picasso.with(GoodsDetailActivity.this)
//                        .load(avObject.getAVFile("image") == null ? "www" : avObject.getAVFile("image").getUrl())
//                        .into(mImageDetail);
//            }
//        });
//    }
//}
