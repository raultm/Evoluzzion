package com.raulete.evoluzzion;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.evoluzzion.models.adapters.LayoutArrayAdapter;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class JigsawsListActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaws_list);
    }
	
	public void onResume(){
		super.onResume();
		populateJigsaws();
	}
	
	public void populateJigsaws(){
		ListView jigsaws =  (ListView)findViewById(R.id.jigsaws_list); 
		jigsaws.setAdapter(new LayoutArrayAdapter(
				this, 
				R.layout.jigsaws_list_item, 
				getJigsaws()));
		jigsaws.setOnItemClickListener(new JigsawItemListener(this));
	}
	
	private List<DatabaseModel> getJigsaws(){
  		return new Jigsaw(this).find("SELECT * FROM jigsaws ORDER BY jigsaws._id DESC");
  	}
	
	public void addJigsaw(View view){
		startActivity(new Intent(this, AddJigsawActivity.class));
	}
	
	class JigsawItemListener implements AdapterView.OnItemClickListener{
		
		private Context context;
		
		public JigsawItemListener(Context context){
			super();
			this.context = context; 
		}
		
  		public void onItemClick(
  				AdapterView<?> paramAdapterView, 
  				View paramView, 
  				int position, 
  				long id){
  			Intent intent = new Intent(context, JigsawActivity.class);
  			intent.putExtra(JigsawActivity.JIGSAW_ID, id);
  			startActivity(intent);
  		}
  	}
}
