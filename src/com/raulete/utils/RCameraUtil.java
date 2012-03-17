package com.raulete.utils;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class RCameraUtil {

	public static int CAMERA_RESULT = 200;
	
	public static File getFileToStoreCameraResult(){
		String directoryPath = Environment.getExternalStorageDirectory() + "/data/com.raulete.evoluzzion/tmp/";
		File directory = new File(directoryPath);
		if(!directory.exists())
			directory.mkdirs();
		String filePath = directoryPath + "camera_capture";
		File file = new File(filePath);
		return file;
	}
	
	public static void launchCamera(Activity activity){
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFileToStoreCameraResult()));
		activity.startActivityForResult(camera, CAMERA_RESULT);
	}
	
	public static String getUriFromCameraResult(Activity activity, int request, int result, Intent intent){
		String image_string = null;
		if (request == CAMERA_RESULT) {
            if (result == Activity.RESULT_OK) {
            	image_string = getStringUriFromCameraResult(activity);
            } else if (result == Activity.RESULT_CANCELED) {
            	Toast.makeText(activity, "Camera has failed", Toast.LENGTH_LONG).show();
            }
		}
		return image_string;
	}
	
	private static String getStringUriFromCameraResult(Activity activity){
		Uri uri = null;
    	File file = getFileToStoreCameraResult();
    	try {
    		uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), null, null));
    	} catch (FileNotFoundException e) { 
        	e.printStackTrace(); 
        }
        if(uri != null){
        	return  uri.toString();
        }
        return null;
    }
}
