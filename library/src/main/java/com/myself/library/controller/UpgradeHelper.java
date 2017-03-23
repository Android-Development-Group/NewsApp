package com.myself.library.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.myself.library.R;

/**
 * 自动更新
 * Created by Jusenr on 16/9/27.
 */

public class UpgradeHelper {

    public static void showUpdateDialog(final Context context, boolean force, final FirInfoBean response) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_force_upgrade, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(!force)
                .create();
        TextView tvContent = (TextView) view.findViewById(R.id.tv_upgrade_content);
        TextView tvGotoUpgrade = (TextView) view.findViewById(R.id.tv_goto_upgrade);
        TextView tvExit = (TextView) view.findViewById(R.id.tv_exit);

        if (response != null) {
            tvContent.setText(response.getChangelog());
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
                String downloadUrl = response.getInstall_url();
                if (downloadUrl != null) {
                    intent.setData(Uri.parse(downloadUrl));
                }
                context.startActivity(intent);
            }
        });

        dialog.show();
    }
}
