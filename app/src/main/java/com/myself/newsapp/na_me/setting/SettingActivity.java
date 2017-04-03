package com.myself.newsapp.na_me.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;
import com.myself.library.controller.eventbus.EventBusHelper;
import com.myself.library.utils.ImageUtils;
import com.myself.library.utils.Logger;
import com.myself.library.utils.PreferenceUtils;
import com.myself.library.utils.StringUtils;
import com.myself.library.utils.ToastUtils;
import com.myself.library.view.image.RoundImageView;
import com.myself.library.view.scroll.SupportScrollView;
import com.myself.newsapp.R;
import com.myself.newsapp.TestActivity;
import com.myself.newsapp.TotalApplication;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.base.SelectPopupWindow;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.guidance.GuidanceActivity;
import com.myself.newsapp.na_store.RoundedTransformation;
import com.myself.newsapp.user.PerfectActivity;
import com.myself.newsapp.util.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.myself.newsapp.R.id.iv_header_icon;
import static com.myself.newsapp.R.id.tv_nick_name;

/**
 * 设置
 * Created by Jusenr on 2017/03/26.
 */
public class SettingActivity extends TitleActivity {
    public static final String TYPE = "type";
    public static final String EVENT_PERFECT = "perfect";
    public static final String NICK_NAME = "nick_name";
    public static final String USER_INFO = "user_info";

    @BindView(iv_header_icon)
    RoundImageView mIvHeaderIcon;
    @BindView(tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_region)
    TextView mTvRegion;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    @BindView(R.id.sv_setting)
    SupportScrollView mSvSetting;

    final AVUser currentUser = AVUser.getCurrentUser();
    private SelectPopupWindow mSelectPopupWindow;
    private String mFilePath;//头像文件路径

    private final int CAMERA_REQCODE = 1;
    private final int ALBUM_REQCODE = 2;
    private final int CHANGE_NICK = 3;
    private final int CHANGE_INFO = 4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        mFilePath = TotalApplication.sdCardPath + File.separator + "head_icon.jpg";
        showPopu();

        String username = PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_USERNAME, null);
        mTvEmail.setText(StringUtils.isEmpty(username) ? "" : username);

        String nickname = AVUser.getCurrentUser().getString("nickname");
        mTvNickName.setText(StringUtils.isEmpty(nickname) ? getString(R.string.me_default_name) : nickname);

        String region = AVUser.getCurrentUser().getString("region");
        mTvRegion.setText(StringUtils.isEmpty(region) ? getString(R.string.me_default_region) : region);

        AVFile userpic = AVUser.getCurrentUser().getAVFile("userpic");
        Picasso.with(mContext)
                .load(userpic == null ? "www" : userpic.getUrl())
                .transform(new RoundedTransformation(9, 0))
                .into(mIvHeaderIcon);
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(TestActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CAMERA_REQCODE://相机选择
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            bitmap = (Bitmap) bundle.get("data");
                            mIvHeaderIcon.setImageBitmap(bitmap);
                            ImageUtils.bitmapOutSdCard(bitmap, mFilePath);
                            checkSha1(mFilePath);
                            return;
                        }
                    }
                    ToastUtils.showToastShort(this, "照片获取失败！");
                    break;
                case ALBUM_REQCODE://相册选择
//                    ToastUtils.showToastShort(this, "系统图库返回");
                    Uri selectedImage = data.getData();
                    String picturePath = ImageUtils.getImageAbsolutePath(SettingActivity.this, selectedImage);

                    Logger.d(picturePath);
                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    mIvHeaderIcon.setImageBitmap(bitmap);
                    ImageUtils.bitmapOutSdCard(bitmap, mFilePath);
                    checkSha1(mFilePath);
                    break;
                case CHANGE_NICK:
                    if (data != null) {
                        String nickname = data.getStringExtra(NICK_NAME);
                        mTvNickName.setText(nickname);
                        initInfo();
                    }
                    break;
                case CHANGE_INFO:
//                    initInfo();
                    break;
            }
        }
    }

    @OnClick({R.id.rl_header_icon, R.id.rl_nick_name, R.id.rl_region, R.id.tv_send_email, R.id.tv_update_password, R.id.btn_loginout})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_header_icon://选择用户头像
                mSelectPopupWindow.show(mSvSetting);
                break;
            case R.id.rl_nick_name://修改用户昵称
                // TODO: 2017/4/3
                bundle.putString(TYPE, NICK_NAME);
                bundle.putString(NICK_NAME, mTvNickName.getText().toString());
                Intent nickIntent = new Intent(this, PerfectActivity.class);
                nickIntent.putExtra(TYPE, bundle);
                startActivityForResult(nickIntent, CHANGE_NICK);
                break;
            case R.id.rl_region:
                break;
            case R.id.tv_send_email:
                break;
            case R.id.tv_update_password:
                break;
            case R.id.btn_loginout:
                AccountHelper.logout();
                startActivity(GuidanceActivity.class);
                finish();
                break;
        }
    }


    /**
     * @param uploadFilePath 上传文件路径
     */
    private void checkSha1(String uploadFilePath) {
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath(currentUser.getUsername().substring(0, 5) + "head_icon.png", uploadFilePath);
            file.deleteInBackground(new DeleteCallback() {
                @Override
                public void done(AVException e) {

                }
            });
            currentUser.put("userpic", file);
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    AVFile userpic = AVUser.getCurrentUser().getAVFile("userpic");
                    Picasso.with(mContext)
                            .load(userpic == null ? "www" : userpic.getUrl())
                            .transform(new RoundedTransformation(9, 0))
                            .into(mIvHeaderIcon);
                    EventBusHelper.post(EVENT_PERFECT, EVENT_PERFECT);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initInfo() {
        try {
            currentUser.put("nickname", mTvNickName.getText());
            currentUser.put("region", "上海");
            currentUser.put("age", 24);
            currentUser.put("sex", 0);//0男1女
            AVFile file = AVFile.withAbsoluteLocalPath(currentUser.getUsername().substring(0, 5) + "head_icon.png", mFilePath);
            currentUser.put("userpic", file);
            currentUser.saveInBackground();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EventBusHelper.post(EVENT_PERFECT, EVENT_PERFECT);
    }

    private void showPopu() {
        if (mSelectPopupWindow == null) {
            mSelectPopupWindow = new SelectPopupWindow(mContext) {
                @Override
                public void onFirstClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQCODE);
                }

                @Override
                public void onSecondClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ALBUM_REQCODE);
                }
            };
        }
    }
}
