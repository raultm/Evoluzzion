package com.raulete.evoluzzion.models.interfaces;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

public abstract interface DatabaseModel
{
  public abstract List<DatabaseModel> cursor2ListOptions(Cursor paramCursor);

  public abstract List<DatabaseModel> find(String paramString);

  public abstract String getIdField();

  public abstract String getSelectSQL(long paramLong);

  public abstract ContentValues parse2ContentValues();

  public abstract void parseCursor(Cursor paramCursor);

  public abstract DatabaseModel read(long paramLong);

  public abstract boolean save();
  
  public abstract long getId();
  
  public abstract View populateItem(View view);
}