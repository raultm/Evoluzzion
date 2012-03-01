package com.raulete.evoluzzion.models.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class LayoutArrayAdapter extends ArrayAdapter<DatabaseModel>{

	private Context context;
	private List<DatabaseModel> items;
	private int layoutToInflate;

	public LayoutArrayAdapter(Context context, int layoutToInflate, List<DatabaseModel> list){
		super(context, layoutToInflate, list);
		this.context = context;
		this.layoutToInflate = layoutToInflate;
		this.items = list;
	}

	public DatabaseModel getItem(int index){
		return (DatabaseModel)this.items.get(index);
	}

	public long getItemId(int index){
		return ((DatabaseModel)getItem(index)).getId();
	}

	public View getView(int index, View view, ViewGroup viewGroup){
		if (view == null)
			view = ((LayoutInflater)this.context
					.getSystemService("layout_inflater"))
					.inflate(this.layoutToInflate, null);
		DatabaseModel model = (DatabaseModel)this.items.get(index);
  	  	if (model != null){
  	  		model.populateItem(view);
  		}
  	  	return view;
	}
}