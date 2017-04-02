package com.myself.leancloudlibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Description: ErrorCode操作提示
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/7 13:17.
 */

public class TipsUtils {
    public static final String TAG = TipsUtils.class.getSimpleName();

    public static void getPrompt(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void getPrompt(Context context, int code) {
        String description = null;
        if (code != -1) {
            switch (code) {
                case 1:
                    description = context.getString(R.string.errcode_1);
                    break;
                case 100:
                    description = context.getString(R.string.errcode_100);
                    break;
                case 101:
                    description = context.getString(R.string.errcode_101);
                    break;
                case 103:
                    description = context.getString(R.string.errcode_103);
                    break;
                case 104:
                    description = context.getString(R.string.errcode_104);
                    break;
                case 105:
                    description = context.getString(R.string.errcode_105);
                    break;
                case 106:
                    description = context.getString(R.string.errcode_106);
                    break;
                case 107:
                    description = context.getString(R.string.errcode_107);
                    break;
                case 108:
                    description = context.getString(R.string.errcode_108);
                    break;
                case 109:
                    description = context.getString(R.string.errcode_109);
                    break;
                case 111:
                    description = context.getString(R.string.errcode_111);
                    break;
                case 112:
                    description = context.getString(R.string.errcode_112);
                    break;
                case 113:
                    description = context.getString(R.string.errcode_113);
                    break;
                case 114:
                    description = context.getString(R.string.errcode_114);
                    break;
                case 116:
                    description = context.getString(R.string.errcode_116);
                    break;
                case 117:
                    description = context.getString(R.string.errcode_117);
                    break;
                case 119:
                    description = context.getString(R.string.errcode_119);
                    break;
                case 120:
                    description = context.getString(R.string.errcode_120);
                    break;
                case 121:
                    description = context.getString(R.string.errcode_121);
                    break;
                case 122:
                    description = context.getString(R.string.errcode_122);
                    break;
                case 123:
                    description = context.getString(R.string.errcode_123);
                    break;
                case 124:
                    description = context.getString(R.string.errcode_124);
                    break;
                case 125:
                    description = context.getString(R.string.errcode_125);
                    break;
                case 126:
                    description = context.getString(R.string.errcode_126);
                    break;
                case 127:
                    description = context.getString(R.string.errcode_127);
                    break;
                case 128:
                    description = context.getString(R.string.errcode_128);
                    break;
                case 137:
                    description = context.getString(R.string.errcode_137);
                    break;
                case 139:
                    description = context.getString(R.string.errcode_139);
                    break;
                case 140:
                    description = context.getString(R.string.errcode_140);
                    break;
                case 141:
                    description = context.getString(R.string.errcode_141);
                    break;
                case 142:
                    description = context.getString(R.string.errcode_142);
                    break;
                case 145:
                    description = context.getString(R.string.errcode_145);
                    break;
                case 150:
                    description = context.getString(R.string.errcode_150);
                    break;
                case 154:
                    description = context.getString(R.string.errcode_154);
                    break;
                case 160:
                    description = context.getString(R.string.errcode_160);
                    break;
                case 200:
                    description = context.getString(R.string.errcode_200);
                    break;
                case 201:
                    description = context.getString(R.string.errcode_201);
                    break;
                case 202:
                    description = context.getString(R.string.errcode_202);
                    break;
                case 203:
                    description = context.getString(R.string.errcode_203);
                    break;
                case 204:
                    description = context.getString(R.string.errcode_204);
                    break;
                case 205:
                    description = context.getString(R.string.errcode_205);
                    break;
                case 206:
                    description = context.getString(R.string.errcode_206);
                    break;
                case 207:
                    description = context.getString(R.string.errcode_207);
                    break;
                case 208:
                    description = context.getString(R.string.errcode_208);
                    break;
                case 210:
                    description = context.getString(R.string.errcode_210);
                    break;
                case 211:
                    description = context.getString(R.string.errcode_211);
                    break;
                case 212:
                    description = context.getString(R.string.errcode_212);
                    break;
                case 213:
                    description = context.getString(R.string.errcode_213);
                    break;
                case 214:
                    description = context.getString(R.string.errcode_214);
                    break;
                case 215:
                    description = context.getString(R.string.errcode_213);
                    break;
                case 216:
                    description = context.getString(R.string.errcode_216);
                    break;
                case 217:
                    description = context.getString(R.string.errcode_217);
                    break;
                case 218:
                    description = context.getString(R.string.errcode_218);
                    break;
                case 219:
                    description = context.getString(R.string.errcode_219);
                    break;
                case 250:
                    description = context.getString(R.string.errcode_250);
                    break;
                case 251:
                    description = context.getString(R.string.errcode_251);
                    break;
                case 252:
                    description = context.getString(R.string.errcode_252);
                    break;
                case 300:
                    description = context.getString(R.string.errcode_300);
                    break;
                case 301:
                    description = context.getString(R.string.errcode_301);
                    break;
                case 302:
                    description = context.getString(R.string.errcode_302);
                    break;
                case 303:
                    description = context.getString(R.string.errcode_303);
                    break;
                case 304:
                    description = context.getString(R.string.errcode_304);
                    break;
                case 305:
                    description = context.getString(R.string.errcode_305);
                    break;
                case 401:
                    description = context.getString(R.string.errcode_401);
                    break;
                case 403:
                    description = context.getString(R.string.errcode_403);
                    break;
                case 429:
                    description = context.getString(R.string.errcode_429);
                    break;
                case 430:
                    description = context.getString(R.string.errcode_430);
                    break;
                case 431:
                    description = context.getString(R.string.errcode_431);
                    break;
                case 502:
                    description = context.getString(R.string.errcode_502);
                    break;
                case 503:
                    description = context.getString(R.string.errcode_503);
                    break;
                case 511:
                    description = context.getString(R.string.errcode_511);
                    break;
                case 524:
                    description = context.getString(R.string.errcode_524);
                    break;
                case 529:
                    description = context.getString(R.string.errcode_529);
                    break;
                case 600:
                    description = context.getString(R.string.errcode_600);
                    break;
                case 601:
                    description = context.getString(R.string.errcode_601);
                    break;
                case 602:
                    description = context.getString(R.string.errcode_602);
                    break;
                case 603:
                    description = context.getString(R.string.errcode_603);
                    break;
                case 604:
                    description = context.getString(R.string.errcode_604);
                    break;
                case 605:
                    description = context.getString(R.string.errcode_605);
                    break;
                case 606:
                    description = context.getString(R.string.errcode_606);
                    break;
                case 700:
                    description = context.getString(R.string.errcode_700);
                    break;
                case 1006:
                    description = context.getString(R.string.errcode_1006);
                    break;
                case 4100:
                    description = context.getString(R.string.errcode_4100);
                    break;

//                case 600:
//                    description = context.getString(R.string.errcode_600);
//                    break;
//                case 600:
//                    description = context.getString(R.string.errcode_600);
//                    break;

                default:
                    description = context.getString(R.string.errcode_70000);
            }
            Log.d(TAG, "####-getPrompt: code-->" + code + "--description-->" + description);
            Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
        }
    }
}
