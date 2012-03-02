package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Jigsaw;

public class AddJigsawActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_add);
    }
	
	public void addJigsaw(View view){
		Jigsaw jigsaw = new Jigsaw(this);
		jigsaw.fill(
			getEditTextText(R.id.jigsaw_add_name), 
			getEditTextText(R.id.jigsaw_add_pieces), 
			getEditTextText(R.id.jigsaw_add_barcode)
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
	
}
