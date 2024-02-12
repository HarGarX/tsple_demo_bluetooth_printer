package com.sdk.tspl.demo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NO on 2017/9/14.
 */

public class Utility {
    public  static void checkBlueboothPermission(Activity context, String permission, String[] requestPermissions, Callback callback){
        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, requestPermissions, 100);
            } else {
                //具有权限
                callback.permit();
                return;
            }
        } else {
            //系统不高于6.0直接执行
            callback.pass();
        }
    }
    public interface Callback {
        /**
         * API>=23 允许权限
         */
        void permit();


        /**
         * API<23 无需授予权限
         */
        void pass();
    }

    public static void show(final Context context, final String message){
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        });

    }

    //file:PDF文件，
    //pageNumber:需要生成的页数，例如："1,2-3",
    //pageWidth:需要生成的宽度
    public static List<Bitmap> pdfToBitmap(Context context, File file, String pageNumber, int pageWidth) {
        ArrayList<Bitmap> ListBitmap = new ArrayList<Bitmap>();
        try {
            ArrayList<String> list = new ArrayList<String>();
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfiumCore pdfiumCore = new PdfiumCore(context);
            PdfDocument pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor);
            int pages = pdfiumCore.getPageCount(pdfDocument);
            String[] split = pageNumber.split(",");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("-");
                if (split2.length == 2) {
                    Integer bigPage = Integer.valueOf(split2[1]);
                    Integer smallPage = Integer.valueOf(split2[0]);
                    int page = bigPage - smallPage;
                    for (int j = 0; j < page + 1; j++) {
                        if (!list.contains("" + (smallPage + j))) {
                            list.add("" + (smallPage + j));
                        }
                    }
                } else {
                    if (!list.contains(split2[0])) {
                        list.add(split2[0]);
                    }
                }
            }
            int width = pageWidth;
            int height = 864;
            int dpi = 200;
            if (list.size() == 0)
                return ListBitmap;
            for (int pageIndex = 0; pageIndex < list.size(); pageIndex++) {
                int page = Integer.valueOf(list.get(pageIndex));
                if (0 < page && page <= pages) {
                    pdfiumCore.openPage(pdfDocument, page - 1);
                    int pageWidthPoint = pdfiumCore.getPageWidthPoint(pdfDocument, page - 1);
                    int pageHeightPoint = pdfiumCore.getPageHeightPoint(pdfDocument, page - 1);
                    double bili = width / (double) pageWidthPoint;
                    height = (int) (pageHeightPoint * bili);
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, page - 1, 0, 0, width, height);
                    ListBitmap.add(bitmap);
                }
            }
            pdfiumCore.closeDocument(pdfDocument);
        } catch (Exception e) {
            return ListBitmap;
        }
        return ListBitmap;
    }


    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if(uri == null) return file;
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.P){
            //android10以上转换
            if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
                file = new File(uri.getPath());
            } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                //把文件复制到沙盒目录
                ContentResolver contentResolver = context.getContentResolver();
                String displayName = "123"
                        +"."+ MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            file = new File(getPath(context, uri));
        }
        return file;
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
