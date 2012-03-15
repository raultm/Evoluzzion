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

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.utils.camera.RCameraUtil;

public class AddJigsawActivity extends Activity {

	private String image_uri;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_add);
    }
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void launchCamera(View view){
		Uri camera_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/evoluzzion/"));
		RCameraUtil.launchCamera(this, camera_uri);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		image_uri = RCameraUtil.getUriFromCameraResult(this, request, result, intent);
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
}
