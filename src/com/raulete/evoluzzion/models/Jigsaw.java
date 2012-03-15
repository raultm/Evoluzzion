package com.raulete.evoluzzion.models;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.raulete.evoluzzion.R;
import com.raulete.evoluzzion.models.interfaces.DatabaseModel;

public class Jigsaw extends Model {

	public static String TABLE_NAME = "jigsaws";
	
	public static String COL_ID = "_id";
	public static String COL_NAME = "name";
	public static String COL_PIECES = "pieces";
	public static String COL_BARCODE = "barcode";
	public static String COL_IMAGE_URI = "image_uri";
	
	public static String getCreateSql(){
		return "CREATE TABLE " + TABLE_NAME
				+ " ("
				+ COL_ID 		+ " integer primary key autoincrement, "
				+ COL_NAME 		+ " text, "
				+ COL_PIECES 	+ " text, "
				+ COL_BARCODE 	+ " text, "
				+ COL_IMAGE_URI	+ " text "
				+");";
	}
	
	public String name;
	public String pieces;
	public String barcode;
	public String image_uri;
	
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
		cv.put(COL_IMAGE_URI, this.image_uri);
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
			cursor.getString(cursor.getColumnIndex(Jigsaw.COL_BARCODE)),
			cursor.getString(cursor.getColumnIndex(Jigsaw.COL_IMAGE_URI))
		);
	}
	
	public void fill(long _id, String name, String pieces, String barcode, String image_uri){
		this._id = _id;
		this.name = name;
		this.pieces = pieces;
		this.barcode = barcode;
		this.image_uri = image_uri;
	}
	
	public void fill(String name, String pieces, String barcode, String image_uri){
		this.name = name;
		this.pieces = pieces;
		this.barcode = barcode;
		this.image_uri = image_uri;
	}
	
	public View populateItem(View v){
		TextView text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.pieces);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.barcode);
		
		return v;
	}
	
	public void populateItem(Activity activity){
		//TextView text = (TextView)activity.findViewById(R.id.jigsaw_name);
		//text.setText(this.name);
	}
	
	public View populateListItem(View v){
		TextView text = (TextView)v.findViewById(R.id.jigsaw_name);
		text.setText(this.name);
		
		text = (TextView)v.findViewById(R.id.jigsaw_pieces);
		text.setText(this.pieces);
		
		text = (TextView)v.findViewById(R.id.jigsaw_barcode);
		text.setText(this.barcode);
		
		ImageView iv = (ImageView)v.findViewById(R.id.jigsaw_image);
		if(this.image_uri.equals(""))
			iv.setBackgroundResource(R.drawable.main_background_image);
		else
			iv.setImageBitmap(getScaledImageFromUri(Uri.parse(this.image_uri)));
		return v;
	}
	
	private Bitmap getScaledImageFromUri(Uri uri){
		BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inTempStorage = new byte[16*512];
		options.inSampleSize = 8;
		Bitmap bitmapImage = BitmapFactory.decodeFile(getRealPathFromURI(uri), options);
		return bitmapImage;
	}
	
	public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = ((Activity)context).managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
