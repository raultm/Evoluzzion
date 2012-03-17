package com.raulete.evoluzzion;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
    }
	
	public void onResume(){
		super.onResume();
		populateJigsaw();
        populateSteps();
	}
	
	public void editJigsaw(View view){
		openEditJigsawActivity();
	}
	
	public void deleteJigsaw(View view){
		new AlertDialog.Builder(this)
			.setTitle(R.string.jigsaw_delete)
			.setMessage(R.string.sure)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int which) { JigsawActivity.this.deleteJigsawAndFinish(); }
			})
	        .setNegativeButton(R.string.no, null)
	        .show();
	}
	
	public void addStep(View view){
		Intent intent = new Intent(this, AddStepActivity.class);
		intent.putExtra(AddStepActivity.JIGSAW_ID, jigsaw_id);
		startActivity(intent);
	}
	
	public void populateSteps(){
		ListView steps =  (ListView)findViewById(R.id.jigsaw_steps_list); 
		steps.setAdapter(new LayoutArrayAdapter(
				this, 
				R.layout.jigsaw_layout_step_list_item, 
				getSteps()));
		steps.setOnItemClickListener(new StepItemListener(this));
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
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jigsaw_menu, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_jigsaw_edit:
        	openEditJigsawActivity();
        	return true;
        case R.id.menu_jigsaw_delete:
        	deleteJigsawAndFinish();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	public void openEditJigsawActivity(){
		Intent intent = new Intent(this, EditJigsawActivity.class);
		intent.putExtra(EditJigsawActivity.JIGSAW_ID, jigsaw_id);
		startActivity(intent);
	}
	
	public void deleteJigsawAndFinish(){
		new Jigsaw(this).delete(jigsaw_id);
    	finish();
	}
	
	class StepItemListener implements AdapterView.OnItemClickListener{
		
		private Context context;
		
		public StepItemListener(Context context){
			super();
			this.context = context; 
		}
		
  		public void onItemClick(
  				AdapterView<?> paramAdapterView, 
  				View paramView, 
  				int position, 
  				long id){
  			Intent intent = new Intent(context, StepActivity.class);
  			intent.putExtra(StepActivity.STEP_ID, id);
  			startActivity(intent);
  		}
  	}
}
