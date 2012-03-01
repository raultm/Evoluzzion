package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Step;

public class AddStepActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	private long jigsaw_id;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_add);
        setJigsawId();
    }
	
	public void addStep(View view){
		Step step = new Step(this);
		step.fill(
			0,
			getEditTextText(R.id.step_add_name), 
			getEditTextText(R.id.step_add_comment), 
			"",
			jigsaw_id
		);
		if(step.save()){
			Toast.makeText(this, "Step saved", Toast.LENGTH_LONG).show();
			openJigsawActivity();
		}else
			Toast.makeText(this, "Step could't be saved", Toast.LENGTH_LONG).show();
	}
	
	public String getEditTextText(int resId){
		EditText et = (EditText)findViewById(resId);
		return et.getText().toString();
	}
	
	public void openJigsawActivity(){
		Intent intent = new Intent(this, JigsawActivity.class);
		intent.putExtra(JigsawActivity.JIGSAW_ID, jigsaw_id);
		startActivity(intent);
	}
	
	public void setJigsawId(){
		Intent intent = getIntent();
		jigsaw_id = intent.getLongExtra(JIGSAW_ID, 0);
	}
}
