package com.raulete.evoluzzion;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.evoluzzion.models.adapters.LayoutArrayAdapter;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class JigsawsListActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jigsaws_list);
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
		Jigsaw jigsaw = new Jigsaw(this);
		jigsaw.fill("Pasacalles", "3000", "1234567890");
		if(jigsaw.save())
			Toast.makeText(this, "Jigsaw added", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Jigsaw not added", Toast.LENGTH_SHORT).show();
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
