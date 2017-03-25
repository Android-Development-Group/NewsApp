package com.myself.newsapp;

/**
 * Created by Jusenr on 2017/03/25.
 */

public class UpgradeHelper {

   /* *//**
     * 检查更新，这里目前只对强制更新做处理
     *//*
    public static Subscription checkUpgradeInfo(final Context context) {
        String versionName = AppUtils.getNoPrefixVersionName(context);
        Observable<RetrofitBean<UpgradeInfo>> observable = RetrofitManager.getWeiDuApi()
                .getUpgradeInfo(versionName, BasicApplication.app_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable
                .subscribe(new Subscriber<RetrofitBean<UpgradeInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(RetrofitBean<UpgradeInfo> response) {
                        UpgradeInfo upgradeInfo = response.getData();
                        if (response.getHttp_code() == 200 && upgradeInfo != null) {
                            boolean forceUpgrade = upgradeInfo.isConstraint_update();
                            if (forceUpgrade) {
                                showUpdateDialog(context, true, upgradeInfo);
                            } else if (upgradeInfo.is_update()) {
                                showUpdateDialog(context, false, upgradeInfo);
                            }
                        }
                    }
                });
    }

    private static void showUpdateDialog(final Context context, boolean force, final UpgradeInfo response) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_force_upgrade, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(!force)
                .create();
        TextView tvContent = (TextView) view.findViewById(R.id.tv_upgrade_content);
        TextView tvGotoUpgrade = (TextView) view.findViewById(R.id.tv_goto_upgrade);
        TextView tvExit = (TextView) view.findViewById(R.id.tv_exit);

        final List<String> contents = response.getVersion_content();
        if (contents != null && !contents.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < contents.size(); i++) {
                String str = contents.get(i) + (i == contents.size() - 1 ? "" : "\n");
                builder.append(str);
            }
            tvContent.setText(builder.toString());
        }

        if (force) {
            tvExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.getInstance().finishAllActivity();
                }
            });
        } else {
            tvExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        tvGotoUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                String downloadUrl = response.getAndroid_download_url();
                if (downloadUrl != null) {
                    intent.setData(Uri.parse(downloadUrl));
                }
                context.startActivity(intent);
            }
        });

        dialog.show();
    }*/
}
