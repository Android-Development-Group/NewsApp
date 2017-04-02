package com.myself.library.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.myself.library.R;
import com.myself.library.controller.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 图片处理工具
 * Created by guchenkai on 2015/12/8.
 */
public final class ImageUtils {
    public static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * 将小图片x轴循环填充进imageView中
     *
     * @param imageView imageView
     * @param bitmap    目标图片
     */
    public static void fillXInImageView(Context context, ImageView imageView, Bitmap bitmap) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
        imageView.setImageBitmap(createRepeater(screenWidth, bitmap));
    }


    /**
     * 将图片在imageView中x轴循环填充需要的bitmap
     *
     * @param width 填充宽度
     * @param src   图片源
     * @return 新图片源
     */
    private static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth(); //计算出平铺填满所给width（宽度）最少需要的重复次数
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth() * count, src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath 图片路径
     * @param width    压缩后的宽
     * @param height   压缩后的高
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将bitmap输出到sdCard中
     *
     * @param outPath 输出路径
     */
    public static void bitmapOutSdCard(Bitmap bitmap, String outPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath, OnImageSaveCallback callback) {
        int mSrollViewHeight = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                child.setBackgroundResource(R.color.white);
                mSrollViewHeight += child.getHeight();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), mSrollViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath) {
        cutOutScrollViewToImage(view, savePath, null);
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath, OnImageSaveCallback callback) {
//        int mSrollViewHeight = 0;
//        for (int i = 0; i < view.getChildCount(); i++) {
//            View child = view.getChildAt(i);
//            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
//                child.setBackgroundResource(R.color.white);
//                mSrollViewHeight += child.getHeight();
//            }
//        }
        view.setBackgroundResource(R.color.white);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath) {
        cutOutViewToImage(view, savePath, null);
    }

    /**
     * 截取view画面保存至bitmap
     *
     * @param view   view源
     * @param width  view宽
     * @param height view高
     */
    public static Bitmap cutOutViewToSmallBitmap(int width, int height, View view) {
        Log.i(TAG, "view-height=" + height + "\r\nview-width=" + width);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
        /*Matrix matrix = new Matrix();
        matrix.postScale(0.1f, 0.1f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;*/
    }

    /**
     * 截取view的屏幕返回Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap cutOutViewToBitmap(View view) {
        /**
         * view.measure（）方法可能会报空指针异常
         *   view在调用measure的时候必须重写他的onMeasure()方法。但是没有重写也有成功的情况。查看布局发现有的布局跟标签是 有的是
         * 将RelativeLayout 把他换为LinearLayout 就好了。
         *   究其原因，原来是 Linearlayout重写了onmeasure方法，其他的布局文件没有重写onmeasure,所以在调用listItem.measure(0, 0);
         * 会报空指针异常，如果想用这个东东，就必须用linearlayout布局或者重写onMeasure（）方法了。
         */
        //打开图像缓存
        view.setDrawingCacheEnabled(true);
        //必须调用measure和layout方法才能成功保存可视组件的截图到png图像文件
        //测量View大小
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //获取宽高
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Log.i(TAG, "cutOutViewToBitmap: Width=" + measuredWidth);
        Log.i(TAG, "cutOutViewToBitmap: Height=" + measuredHeight);
        //发送位置和尺寸到View及其所有的子View
        view.layout(0, 0, measuredWidth, measuredHeight);
        //获得可视组件的截图
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 截取Webview保存为Bitmap
     *
     * @param webView
     */
    private Bitmap saveWebView2Bitmap(WebView webView) {
        Picture pic = webView.capturePicture();
        int width = pic.getWidth();
        int height = pic.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        pic.draw(canvas);

        return bmp;
    }

    /**
     * 获取Bitmap格式的屏幕截图
     *
     * @param activity
     * @return
     */
    public Bitmap cutShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
        Log.e("#####", "myShot: width=" + widths);//1080
        Log.e("#####", "myShot: height=" + heights);//1920
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    /**
     * 保存Bitmap至相册
     *
     * @param context
     * @param bmp         Bitmap图片对象
     * @param pictureName 图片名称
     * @return 保存结果(剩余储存空间>1MB为true)
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp, String pictureName) {
        // 首先保存图片
        String filePath = BaseApplication.sdCardPath + File.separator + "pbqrcode";
        Log.v(TAG, "saveImageToGallery: " + filePath);
        long freeBytes = SDCardUtils.getFreeBytes(filePath);
        if (Double.valueOf(String.valueOf(freeBytes / (1024 * 1024))) > 1) {
            File appDir = new File(filePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = pictureName + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));

            return true;
        }
        return false;
    }

    /**
     * 保存Bitmap至相册
     *
     * @param context
     * @param picBitmap photos
     * @param tips      提示信息
     * @param des       描述
     */
    public static void saveBitmap2Photoes(Context context, Bitmap picBitmap, String tips, String des) {
        String uri = MediaStore.Images.Media.insertImage(context.getContentResolver(), picBitmap, UUID.randomUUID().toString(), des);
        String savePath = Environment.getExternalStorageDirectory() + uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Uri contentUri = Uri.fromFile(new File(savePath));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            Uri contentUri = Uri.parse("file://" + savePath);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, contentUri));
        }
        if (TextUtils.isEmpty(tips))
            Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param filepath
     * @return
     */
    public static File createScaledBitmapFile(Context context, String filepath, int width, int height) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, bmOptions);
        int scaleFactor = calculateInSampleSize(bmOptions, width, height);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bmOptions.inPurgeable = true;
        bmOptions.inInputShareable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filepath, bmOptions);

        File file = new File(FileUtils.getPicDirectory(context), FileUtils.getFileName(filepath));
        FileOutputStream out = null;
        try {
            if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0)
                return null;
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * create a new image file
     *
     * @return
     * @throws IOException
     */
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    /**
     *
     */
    public interface OnImageSaveCallback {

        void onImageSave(boolean isSuccess);
    }


    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @date 2014-10-12
     */
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isImage(String url) {
        if (StringUtils.isEmpty(url)) return false;
//        String upperUrl = new String();
        url = url.toUpperCase();
        if (url.contains(".JPG") || url.contains(".PNG") || url.contains(".GIF") || url.contains(".JPEG") || url.contains(".BMP"))
            return true;
        else return false;
    }

    public static String getImageSizeUrl(String url, ImageSizeURL type) {
        if (!isImage(url)) return "";
        else {
            int i = url.lastIndexOf(".");
            String font = url.substring(0, i);
            switch (type) {
                case SIZE_1200X400:
                    font += "_1200x400";
                    break;
                case SIZE_1200x750:
                    font += "_1200x750";
                    break;
                case SIZE_1200x600:
                    font += "_1200x600";
                    break;
                case SIZE_240x240:
                    font += "_240x240";
                    break;
                case SIZE_360x360:
                    font += "_360x360";
                    break;
                case SIZE_180x180:
                    font += "_180x180";
                    break;
                case SIZE_1200x1200:
                    font += "_1200x1200";
                    break;
                case SIZE_120x120:
                    font += "_120x120";
                    break;
                case SIZE_150x150:
                    font += "_150x150";
                    break;
                case SIZE_192x192:
                    font += "_192x192";
                    break;
                case SIZE_96x96:
                    font += "_96x96";
                    break;
                case SIZE_250x0:
                    font += "_250x0";
                    break;
            }
            return font + url.substring(i, url.length());
        }
    }

    public enum ImageSizeURL {
        SIZE_1200X400, SIZE_1200x750, SIZE_1200x600, SIZE_240x240, SIZE_360x360, SIZE_180x180, SIZE_1200x1200, SIZE_120x120, SIZE_150x150, SIZE_192x192, SIZE_96x96, SIZE_250x0
    }

    /**
     * 处理图片
     *
     * @param bitmap    所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 根据资源,新图片宽,高 返回bitmap
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置options.inJustDecodeBounds为true
        options.inJustDecodeBounds = true;
        //传递options的值
        BitmapFactory.decodeResource(res, resId, options);
        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //设置 options.inJustDecodeBounds为false
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 绘制圆形
     *
     * @param source
     * @return
     */
    public static Bitmap drawCircleBitmap(Bitmap source) {
        // 以最小的边为圆的半径
        int min = 0;
        if (source.getWidth() < source.getHeight()) {
            min = source.getWidth();
        } else {
            min = source.getHeight();
        }
        // 获取图片的中心点，使之与圆形的中心点重合，才不会让图片出现偏移
        int x = 0;
        int y = 0;
        if (source.getWidth() > min) {
            x = -(source.getWidth() - min);
        } else {
            x = min - source.getWidth();
        }
        if (source.getHeight() > min) {
            y = -(source.getHeight() - min);
        } else {
            y = min - source.getHeight();
        }
        // 创建画笔
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 以新建的bitmap创建画布
        Bitmap bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 以图片最小边为直径绘制圆形区域
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        // 设置圆形区域与要显示的图片相交，并取相交区域的图片为结果图片
        // Mode.SRC_IN:取两层绘制交集。显示上层。
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, x / 2, y / 2, paint);
        // 返回生成的bitmap，bitmap就是圆形图片
        return bitmap;
    }

    /**
     * 绘制圆角矩形
     *
     * @param source
     * @param radius
     * @return
     */
    public static Bitmap drawRect(Bitmap source, int radius) {
        // 得到要绘制的图片的长和宽，用来绘制圆角矩形的长和宽
        int width = source.getWidth();
        int height = source.getHeight();
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF rect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return bitmap;
    }

    /**
     * 绘制圆角正方形
     *
     * @param source
     * @param radius
     * @return
     */
    public static Bitmap drawSqureRect(Bitmap source, int radius) {
        int min = 0;//获取最小的边长
        if (source.getWidth() < source.getHeight()) {
            min = source.getWidth();
        } else {
            min = source.getHeight();
        }
        int x = 0;//绘制中心的x坐标
        int y = 0;//绘制中心的y坐标
        if (source.getWidth() > min) {
            x = -(source.getWidth() - min);
        } else {
            x = min - source.getWidth();
        }
        if (source.getHeight() > min) {
            y = -(source.getHeight() - min);
        } else {
            y = min - source.getHeight();
        }
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF rect = new RectF(0, 0, min, min);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, x / 2, y / 2, paint);//将要绘制的图片中心移动到画布中心
        return bitmap;
    }

    /**
     * Base64加密bitmap
     *
     * @param bmp
     * @return
     */
    public static String bitmap2Base64(Bitmap bmp) {
        String result = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] bitmapBytes = bao.toByteArray();
            bao.flush();
            bao.close();
            result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
        } catch (Exception ex) {

        } finally {
            return result;
        }
    }

    /**
     * Write text to Bitmap
     *
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToBitmap(Bitmap bitmap, String text, int textColor, int textSize) {
        int mTextColor = textColor == 0 ? Color.BLACK : textColor;
        int mTextSize = textSize == 0 ? 12 : textSize;
        try {
            int scale = bitmap.getDensity();
            Log.i("drawTextToBitmap: ", "scale=" + scale);
            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(mTextColor);
            paint.setTextSize(mTextSize);
//            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = bitmap.getWidth() / 4;
            int y = bitmap.getHeight() - 10;
//            canvas.drawText(text, x * scale, y * scale, paint);
            canvas.drawText(text, x, y, paint);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Write text to Bitmap
     *
     * @param mContext
     * @param resourceId
     * @param text
     * @return
     */
    public static Bitmap drawTextToBitmap(Context mContext, int resourceId, String text, int textColor, int textSize) {
        int mTextColor = textColor == 0 ? Color.BLACK : textColor;
        int mTextSize = textSize == 0 ? 12 : textSize;
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Log.i("drawTextToBitmap: ", "scale=" + scale);
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(mTextColor);
            paint.setTextSize(mTextSize);
//            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = 2;
            int y = bitmap.getHeight() - 10;
//            canvas.drawText(text, x * scale, y * scale, paint);
            canvas.drawText(text, x, y, paint);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
