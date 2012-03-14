package com.raulete.evoluzzion;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Jigsaw;

public class AddJigsawActivity extends Activity {

	public static int CAMERA_RESULT = 200;
	private String image_uri;
	private Uri camera_uri;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_add);
    }
	
	public void addJigsaw(View view){
		if(image_uri == null || image_uri.equals("")){ 
			image_uri = ""; 
		}
		Jigsaw jigsaw = new Jigsaw(this);
		jigsaw.fill(
			getEditTextText(R.id.jigsaw_add_name), 
			getEditTextText(R.id.jigsaw_add_pieces), 
			getEditTextText(R.id.jigsaw_add_barcode),
			image_uri
		);
		if(jigsaw.save()){
			Toast.makeText(this, "Jigsaw saved", Toast.LENGTH_LONG).show();
			finish();
		}else
			Toast.makeText(this, "Jigsaw could't be saved", Toast.LENGTH_LONG).show();
	}
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void launchCamera(View view){
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/evoluzzion/"));
		camera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, camera_uri);
		startActivityForResult(camera, CAMERA_RESULT);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		if (request == CAMERA_RESULT) {
            if (result == Activity.RESULT_OK) {
            	image_uri = getStringUriFromCameraResult();
            } else if (result == Activity.RESULT_CANCELED) {
            	Toast.makeText(this, "Camera has failed", Toast.LENGTH_LONG).show();
            }
		}
	}

	private String getStringUriFromCameraResult(){
		Uri uri = null;
    	File file = new File(Environment.getExternalStorageDirectory() + "/evoluzzion/");
    	try {
    		uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), null, null));
            if (!file.delete()) {	Log.i("logMarker", "Failed to delete " + file); }
        } catch (FileNotFoundException e) { 
        	e.printStackTrace(); 
        }
        if(uri != null){
        	return  uri.toString();
        }
        return null;
    }
}
