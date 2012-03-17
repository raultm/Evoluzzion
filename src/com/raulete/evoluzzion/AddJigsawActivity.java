package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.utils.RCameraUtil;
import com.raulete.utils.RImageUtil;

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
		RCameraUtil.launchCamera(this);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent){
		image_uri = RCameraUtil.getUriFromCameraResult(this, request, result, intent);
		if(image_uri != null && !image_uri.equals("")){
			ImageView image = (ImageView)findViewById(R.id.camera_image);
			image.setImageBitmap(RImageUtil.getScaledImageFromUri(this, Uri.parse(this.image_uri), 5));
		}
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
			Toast.makeText(this, R.string.jigsaw_saved, Toast.LENGTH_LONG).show();
			finish();
		}else
			Toast.makeText(this, R.string.jigsaw_not_saved, Toast.LENGTH_LONG).show();
	}
}
