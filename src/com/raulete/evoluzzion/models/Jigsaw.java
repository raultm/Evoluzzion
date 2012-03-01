package com.raulete.evoluzzion.models;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.raulete.evoluzzion.R;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class Jigsaw extends Model {

	public static String TABLE_NAME = "jigsaws";
	
	public static String COL_ID = "_id";
	public static String COL_NAME = "name";
	public static String COL_PIECES = "pieces";
	public static String COL_BARCODE = "barcode";
	
	public static String getCreateSql(){
		return "CREATE TABLE " + TABLE_NAME
				+ " ("
				+ COL_ID 		+ " integer primary key autoincrement, "
				+ COL_NAME 		+ " text, "
				+ COL_PIECES 	+ " text, "
				+ COL_BARCODE 	+ " text "
				+");";
	}
	
	public String name;
	public String pieces;
	public String barcode;
	
	public Jigsaw(Context context){
		super(context);
	}
	
	public String getTableName(){
		return TABLE_NAME;
	}
	
	public ContentValues parse2ContentValues(){
		ContentValues cv = new ContentValues();
		if(this._id != 0)
			cv.put(COL_ID, this._id);
		cv.put(COL_NAME, this.name);
		cv.put(COL_PIECES, this.pieces);
		cv.put(COL_BARCODE, this.barcode);
		return cv;
	}
	
	public List<DatabaseModel> cursor2ListOptions(Cursor cursor){
		ArrayList<DatabaseModel> list = new ArrayList<DatabaseModel>();
		if (cursor.getCount() > 0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()){
				DatabaseModel jigsaw = new Jigsaw(this.context);
				jigsaw.parseCursor(cursor);
				list.add(jigsaw);
				cursor.moveToNext();
			}
		}
		return list;
	}
	
	public void parseCursor(Cursor cursor){
		fill(
			cursor.getLong(cursor.getColumnIndex(Jigsaw.COL_ID)), 
			cursor.getString(cursor.getColumnIndex(Jigsaw.COL_NAME)), 
			cursor.getString(cursor.getColumnIndex(Jigsaw.COL_PIECES)), 
			cursor.getString(cursor.getColumnIndex(Jigsaw.COL_BARCODE))
		);
	}
	
	public void fill(long _id, String name, String pieces, String barcode){
		this._id = _id;
		this.name = name;
		this.pieces = pieces;
		this.barcode = barcode;
	}
	
	public void fill(String name, String pieces, String barcode){
		this.name = name;
		this.pieces = pieces;
		this.barcode = barcode;
	}
	
	public View populateItem(View v){
		TextView text = (TextView)v.findViewById(R.id.jigsaw_id);
		text.setText(this._id + "");
		
		text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.pieces);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.barcode);
		
		return v;
	}
	
	public void populateItem(Activity activity){
		TextView text = (TextView)activity.findViewById(R.id.jigsaw_name);
		text.setText(this._id + " - " + this.name);
	}
	
	public View populateListItem(View v){
		Log.i("Evoluzzion", "populateListItem");
		TextView text = (TextView)v.findViewById(R.id.jigsaw_id);
		text.setText(this._id + "");
		
		text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.pieces);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.barcode);
		return v;
	}
	
	
	
}
