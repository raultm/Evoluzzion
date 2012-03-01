package com.raulete.evoluzzion;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.raulete.evoluzzion.JigsawsListActivity.JigsawItemListener;
import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.evoluzzion.models.Step;
import com.raulete.evoluzzion.models.adapters.LayoutArrayAdapter;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class JigsawActivity extends Activity {

	public static String JIGSAW_ID = "JIGSAW_ID";
	private long jigsaw_id = 0;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaw_layout);
        setJigsawId();
        populateJigsaw();
        populateSteps();
    }
	
	public void addStep(View view){
		Step step = new Step(this);
		step.fill(0, "Step", "d’a completo", "2012-01-03 21:55:00", jigsaw_id);
		if(step.save())
			Toast.makeText(this, "Step added", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Step not added", Toast.LENGTH_SHORT).show();
	}
	
	public void populateSteps(){
		ListView steps =  (ListView)findViewById(R.id.jigsaw_steps_list); 
		steps.setAdapter(new LayoutArrayAdapter(
				this, 
				R.layout.jigsaw_layout_step_list_item, 
				getSteps()));
		//jigsaws.setOnItemClickListener(new JigsawItemListener(this));
	}
	
	private List<DatabaseModel> getSteps(){
  		return new Step(this).find(
  				  " SELECT * FROM steps "
  				+ " WHERE steps.jigsaw_id=" + jigsaw_id 
  				+ " ORDER BY steps._id DESC");
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
