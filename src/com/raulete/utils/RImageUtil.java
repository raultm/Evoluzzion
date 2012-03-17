package com.raulete.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class RImageUtil {

	public static Bitmap getScaledImageFromUri(Activity activity, Uri uri, int size){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = size;
		Bitmap bitmapImage = BitmapFactory.decodeFile(getRealPathFromURI(activity, uri), options);
		return bitmapImage;
	}
	
	public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
