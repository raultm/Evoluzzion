package com.raulete.evoluzzion;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Step;
import com.raulete.utils.camera.RCameraUtil;

public class AddStepActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	private long jigsaw_id;
	private String image_uri;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_add);
        setJigsawId();
    }
	
	public void launchCamera(View view){
		Uri camera_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/evoluzzion/"));
		RCameraUtil.launchCamera(this, camera_uri);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		image_uri = RCameraUtil.getUriFromCameraResult(this, request, result, intent);
	}
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void setJigsawId(){
		Intent intent = getIntent();
		jigsaw_id = intent.getLongExtra(JIGSAW_ID, 0);
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
}
