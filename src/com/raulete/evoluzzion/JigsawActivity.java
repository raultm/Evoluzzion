package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.raulete.evoluzzion.models.Jigsaw;

public class JigsawActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	private long jigsaw_id = 0;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_layout);
        setJigsawId();
        populateJigsaw();
    }
	
	public void setJigsawId(){
		Intent intent = getIntent();
		jigsaw_id = intent.getLongExtra(JIGSAW_ID, 0);
	}
	
	public void populateJigsaw(){
		Jigsaw jigsaw = new Jigsaw(this);
		jigsaw.read(jigsaw_id);
		jigsaw.populateItem(this);
	}
}
