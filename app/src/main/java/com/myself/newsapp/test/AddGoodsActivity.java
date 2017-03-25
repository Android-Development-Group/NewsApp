package com.myself.newsapp.test;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加商品
 * Created by Jusenr on 2017/03/25.
 */
public class AddGoodsActivity extends TitleActivity {

    @BindView(R.id.progess)
    ProgressBar mProgess;
    @BindView(R.id.edittext_title_publish)
    EditText mEdittextTitlePublish;
    @BindView(R.id.edittext_discription_publish)
    EditText mEdittextDiscriptionPublish;
    @BindView(R.id.edittext_price_publish)
    EditText mEdittextPricePublish;
    @BindView(R.id.edittext_integral_publish)
    EditText mEdittextIntegralPublish;
    @BindView(R.id.imageview_select_publish)
    ImageView mImageviewSelectPublish;

    private byte[] mImageBytes = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_goods;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

    }

    @OnClick({R.id.button_select_publish, R.id.button_submit_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select_publish:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
                break;
            case R.id.button_submit_publish:
                submitPublish();
                break;
        }
    }

    /**
     * 提交
     */
    public void submitPublish() {
        if ("".equals(mEdittextTitlePublish.getText().toString())) {
            Toast.makeText(AddGoodsActivity.this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(mEdittextDiscriptionPublish.getText().toString())) {
            Toast.makeText(AddGoodsActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(mEdittextPricePublish.getText().toString())) {
            Toast.makeText(AddGoodsActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        if ("".equals(mEdittextIntegralPublish.getText().toString())) {
            Toast.makeText(AddGoodsActivity.this, "请输入积分", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mImageBytes == null) {
            Toast.makeText(AddGoodsActivity.this, "请选择一张照片", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgess.setVisibility(View.VISIBLE);
        AVObject product = new AVObject("Product");
        product.put("title", mEdittextTitlePublish.getText().toString());
        product.put("description", mEdittextDiscriptionPublish.getText().toString());
        product.put("price", Integer.parseInt(mEdittextPricePublish.getText().toString()));
        product.put("integral", Integer.parseInt(mEdittextIntegralPublish.getText().toString()));
        product.put("owner", AVUser.getCurrentUser());
        product.put("image", new AVFile("productPic", mImageBytes));
        product.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mProgess.setVisibility(View.GONE);
                    AddGoodsActivity.this.finish();
                } else {
                    mProgess.setVisibility(View.GONE);
                    Toast.makeText(AddGoodsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            try {
                mImageviewSelectPublish.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                mImageBytes = getBytes(getContentResolver().openInputStream(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输入流
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
