package com.raulete.evoluzzion;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
		if (hasImageCaptureBug()) {
			camera_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/evoluzzion/"));
		} else {
			camera_uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		}
		Log.i("Evoluzzion", camera_uri.toString());
		camera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, camera_uri);
		startActivityForResult(camera, CAMERA_RESULT);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		
		Uri u = null;
        if (hasImageCaptureBug()) {
        	Log.i("Evoluzzion", "hasBug");
            File fi = new File(Environment.getExternalStorageDirectory() + "/evoluzzion/");
            try {
                u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), fi.getAbsolutePath(), null, null));
                if (!fi.delete()) {
                    Log.i("logMarker", "Failed to delete " + fi);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
        	Log.i("Evoluzzion", "no Bug");
        	u = intent.getData();
        	Log.i("Evoluzzion", "");
        }

        if(u != null){
        	Log.i("Evoluzzion", "Not null " + u.toString());
        	image_uri = u.toString();
        }else
        	Log.i("Evoluzzion", "null");
		
        if (request == CAMERA_RESULT) {
            if (result == Activity.RESULT_OK) {
            		//ImageView pictureHolder = (ImageView) this.findViewById(R.id.jigsaw_image);
            		//pictureHolder.setImageURI(u);
            		//pictureHolder.invalidate();
            }
            else if (result == Activity.RESULT_CANCELED) {
            	Toast.makeText(this, "Camera has failed", Toast.LENGTH_LONG).show();
            }
		}
	}
	
	public void addStep(View view){
		Step step = new Step(this);
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
