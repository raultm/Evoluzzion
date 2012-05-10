package com.raulete.evoluzzion.models.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.raulete.evoluzzion.models.interfaces.Model;

public class LayoutArrayAdapter extends ArrayAdapter<Model>{

	private Context context;
	private List<Model> items;
	private int layoutToInflate;

	public LayoutArrayAdapter(Context context, int layoutToInflate, List<Model> list){
		super(context, layoutToInflate, list);
		this.context = context;
		this.layoutToInflate = layoutToInflate;
		this.items = list;
	}

	public Model getItem(int index){
		return (Model)this.items.get(index);
	}

	public long getItemId(int index){
		return ((Model)getItem(index)).getId();
	}

	public View getView(int index, View view, ViewGroup viewGroup){
		if (view == null)
			view = ((LayoutInflater)this.context
					.getSystemService("layout_inflater"))
					.inflate(this.layoutToInflate, null);
		Model model = (Model)this.items.get(index);
  	  	if (model != null){
  	  		model.populateListItem(view);
  		}
  	  	return view;
	}
}