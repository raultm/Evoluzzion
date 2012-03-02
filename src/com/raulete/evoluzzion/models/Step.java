package com.raulete.evoluzzion.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.raulete.evoluzzion.R;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class Step extends Model {

	public static String TABLE_NAME = "steps";
	
	public static String COL_ID = "_id";
	public static String COL_NAME = "name";
	public static String COL_COMMENT = "comment";
	public static String COL_DATE = "date";
	public static String COL_IMAGE_URI = "image_uri";
	public static String COL_JIGSAW_ID = "jigsaw_id";
	
	public static String getCreateSql(){
		return "CREATE TABLE " + TABLE_NAME
				+ " ("
				+ COL_ID 		+ " integer primary key autoincrement, "
				+ COL_NAME 		+ " text, "
				+ COL_COMMENT 	+ " text, "
				+ COL_IMAGE_URI + " text, "
				+ COL_DATE 		+ " date, "
				+ COL_JIGSAW_ID + " integer "
				+");";
	}
	
	public String name;
	public String comment;
	public String image_uri;
	public String date;
	public long jigsaw_id;
	
	public Step(Context context){
		super(context);
	}
	
	public String getTableName(){
		return TABLE_NAME;
	}
	
	public ContentValues parse2ContentValues(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();

		ContentValues cv = new ContentValues();
		if(this._id != 0)
			cv.put(COL_ID, this._id);
		cv.put(COL_NAME, this.name);
		cv.put(COL_COMMENT, this.comment);
		cv.put(COL_IMAGE_URI, this.image_uri);
		cv.put(COL_DATE, dateFormat.format(date));
		cv.put(COL_JIGSAW_ID, this.jigsaw_id);
		return cv;
	}
	
	public List<DatabaseModel> cursor2ListOptions(Cursor cursor){
		ArrayList<DatabaseModel> list = new ArrayList<DatabaseModel>();
		if (cursor.getCount() > 0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()){
				DatabaseModel jigsaw = new Step(this.context);
				jigsaw.parseCursor(cursor);
				list.add(jigsaw);
				cursor.moveToNext();
			}
		}
		return list;
	}
	
	public void parseCursor(Cursor cursor){
		fill(
			cursor.getLong(cursor.getColumnIndex(Step.COL_ID)), 
			cursor.getString(cursor.getColumnIndex(Step.COL_NAME)), 
			cursor.getString(cursor.getColumnIndex(Step.COL_COMMENT)), 
			cursor.getString(cursor.getColumnIndex(Step.COL_IMAGE_URI)), 
			cursor.getString(cursor.getColumnIndex(Step.COL_DATE)),
			cursor.getLong(cursor.getColumnIndex(Step.COL_JIGSAW_ID))
		);
	}
	
	public void fill(long _id, String name, String comment, String image_uri, String date, long jigsaw_id){
		this._id = _id;
		this.name = name;
		this.comment = comment;
		this.image_uri = image_uri;
		this.date = date;
		this.jigsaw_id = jigsaw_id;
	}
	
	public void populateItemLayout(View v){
		TextView text = (TextView)v.findViewById(R.id.jigsaw_id);
		text.setText(this._id + "");
		
		text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.comment);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.date);
	}
	
	public View populateListItem(View v){
		TextView text = (TextView)v.findViewById(R.id.jigsaw_id);
		text.setText(this._id + "");
		
		text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.comment);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.date);
		
		text = (TextView)v.findViewById(R.id.jigsaw_image_uri);
		text.setText(this.image_uri);
		return v;
	}
	
	
	
}
