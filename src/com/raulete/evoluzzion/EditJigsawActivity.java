package com.raulete.evoluzzion;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.utils.RCameraUtil;
import com.raulete.utils.RImageUtil;

public class EditJigsawActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	private long jigsaw_id = 0;
	private Jigsaw jigsaw;
	private String image_uri;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_add);
        setJigsaw();
        populateJigsawInForm();
    }
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void setEditTextText(int resId, String value){
		EditText et = (EditText)findViewById(resId);
		et.setText(value);
	}
	
	public void launchCamera(View view){
		Uri camera_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/evoluzzion/"));
		RCameraUtil.launchCamera(this, camera_uri);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		image_uri = RCameraUtil.getUriFromCameraResult(this, request, result, intent);
		setPreviewImage(image_uri);
	}

	public void addJigsaw(View view){
		if(image_uri == null || image_uri.equals("")){ 
			image_uri = ""; 
		}
		Jigsaw jigsaw = new Jigsaw(this);
		jigsaw.fill(
			jigsaw_id,
			getEditTextText(R.id.jigsaw_add_name), 
			getEditTextText(R.id.jigsaw_add_pieces), 
			getEditTextText(R.id.jigsaw_add_barcode),
			image_uri
		);
		if(jigsaw.save()){
			Toast.makeText(this, R.string.jigsaw_saved, Toast.LENGTH_LONG).show();
			finish();
		}else
			Toast.makeText(this, R.string.jigsaw_not_saved, Toast.LENGTH_LONG).show();
	}
	
	public void setJigsaw(){
		Intent intent = getIntent();
		jigsaw_id = intent.getLongExtra(JIGSAW_ID, 0);
		jigsaw = new Jigsaw(this);
		jigsaw.read(jigsaw_id);
	}
	
	public void populateJigsawInForm(){
		setEditTextText(R.id.jigsaw_add_name, jigsaw.name);
		setEditTextText(R.id.jigsaw_add_pieces, jigsaw.pieces);
		setEditTextText(R.id.jigsaw_add_barcode, jigsaw.barcode);
		image_uri = jigsaw.image_uri;
		setPreviewImage(image_uri);
	}
	
	public void setPreviewImage(String uri){
		if(image_uri != null && !image_uri.equals("")){
			ImageView image = (ImageView)findViewById(R.id.camera_image);
			image.setImageBitmap(RImageUtil.getScaledImageFromUri(this, Uri.parse(this.image_uri), 5));
		}
	}
}
