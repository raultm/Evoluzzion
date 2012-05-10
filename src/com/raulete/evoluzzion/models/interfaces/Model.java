package com.raulete.evoluzzion.models.interfaces;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

public abstract interface Model
{
  public abstract List<Model> cursor2ListOptions(Cursor paramCursor);

  public abstract List<Model> find(String paramString);

  public abstract String getIdField();

  public abstract String getSelectSQL(long paramLong);

  public abstract ContentValues parse2ContentValues();

  public abstract void parseCursor(Cursor paramCursor);

  public abstract Model read(long paramLong);

  public abstract boolean save();
  
  public abstract boolean delete(long id);
  
  public abstract long getId();
  
  public abstract View populateItem(View view);
  
  public abstract View populateListItem(View view);
}