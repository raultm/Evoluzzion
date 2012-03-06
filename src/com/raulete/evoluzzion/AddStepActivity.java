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

import com.raulete.evoluzzion.models.Step;

public class AddStepActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	public static int CAMERA_RESULT = 200;
	private long jigsaw_id;
	private String image_uri;
	private Uri camera_uri;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_add);
        setJigsawId();
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
	
	
	public void addStep(View view){
		Step step = new Step(this);
		if(image_uri == null || image_uri.equals("")){ 
			Toast.makeText(this, "Please, take a photo!", Toast.LENGTH_LONG).show();
			return; 
		}
		step.fill(
			0,
			getEditTextText(R.id.step_add_name), 
			getEditTextText(R.id.step_add_comment), 
			image_uri,
			"",
			jigsaw_id
		);
		if(step.save()){
			Toast.makeText(this, "Step saved", Toast.LENGTH_LONG).show();
			finish();
		}else
			Toast.makeText(this, "Step could't be saved", Toast.LENGTH_LONG).show();
	}
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void setJigsawId(){
		Intent intent = getIntent();
		jigsaw_id = intent.getLongExtra(JIGSAW_ID, 0);
	}
	
	public boolean hasImageCaptureBug() {
		return true;
//		ArrayList<String> devices = new ArrayList<String>();
//	    	devices.add("android-devphone1/dream_devphone/dream");
//	    	devices.add("generic/sdk/generic");
//	    	devices.add("vodafone/vfpioneer/sapphire");
//	    	devices.add("tmobile/kila/dream");
//	    	devices.add("verizon/voles/sholes");
//	    	devices.add("google_ion/google_ion/sapphire");
//	    return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/" + android.os.Build.DEVICE);
	}

}
