package com.myself.newsapp.guidance.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myself.library.view.viewpager.banner.holder.Holder;
import com.myself.newsapp.R;
import com.myself.newsapp.guidance.model.bean.GuidancePicture;

/**
 * Created by Administrator on 2016/8/8.
 */
public class GuidancePictureHolderView implements Holder<GuidancePicture> {
    private ImageView picture;
    private TextView title;
    private TextView lable;

    @Override
    public View createView(Context context) {
        View view = View.inflate(context, R.layout.guidance_item, null);
        picture = (ImageView) view.findViewById(R.id.iv_guidance_pic);
        title = (TextView) view.findViewById(R.id.tv_guidance_1);
        lable = (TextView) view.findViewById(R.id.tv_guidance_2);

        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, GuidancePicture data) {
        picture.setImageResource(data.getPicture());
        title.setText(data.getTitle());
        lable.setText(data.getLable());
    }
}
