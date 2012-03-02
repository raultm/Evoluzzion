package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.raulete.evoluzzion.models.Step;

public class StepActivity extends Activity {

	public static String STEP_ID = "step_id";
	private long step_id;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_layout);
        setStepId();
    }
	
	public void onResume(){
		super.onResume();
		populateStep();
	}
	
	public void onPause(){
		super.onPause();
		finish();
	}
	
	public void populateStep(){
		Step step = new Step(this);
		step.read(step_id);
		step.populateItem(this);
	}
	
	public void setStepId(){
		Intent intent = getIntent();
		step_id = intent.getLongExtra(STEP_ID, 0);
	}
}
