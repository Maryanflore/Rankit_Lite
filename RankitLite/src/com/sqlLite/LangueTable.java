package com.sqlLite; 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.reponse.Reponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LangueTable {


	
	  public static final String TABLE_LANGUE = "langue";
	  public static final String COLUMN_LANGUE_ID = "langue_id";
	  public static final String COLUMN_LANGUE_AB = "langue_abreviation";

	
	private SQLiteDatabase bdd;
 
	private MySQLiteCreate maBaseSQLite;
	
 
	public LangueTable(Context context){
		//On créer la BDD et sa table
		maBaseSQLite = new MySQLiteCreate (context, MySQLAttributs.DATABASE_NAME, null, MySQLAttributs.DATABASE_VERSION);
		
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	
	public int getReturnIdlangue(String langue_ab) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
	int id_langue=0;
		
		Cursor c = bdd.query(TABLE_LANGUE, new String[] {
			COLUMN_LANGUE_ID,COLUMN_LANGUE_AB}, "("+COLUMN_LANGUE_AB+ "='"+langue_ab+"') order by "+COLUMN_LANGUE_ID+" DESC", null, null, null, null);
	for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
	{
		id_langue=c.getInt(0);
	}
	return id_langue; 	
	}

	public void   insertLangue(JSONArray feedArray) throws  JSONException
	{

		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{
				 
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				bdd.execSQL("INSERT INTO "+TABLE_LANGUE+" ( "
						+COLUMN_LANGUE_ID+","
						+COLUMN_LANGUE_AB+")"
						+" VALUES("+feedObj.getInt(COLUMN_LANGUE_ID)
						+",'"+feedObj.getString(COLUMN_LANGUE_AB)+"')");
						
			}
			
		}
	}


	
public void dropTable(String table){
		
		bdd.execSQL("DROP TABLE IF EXISTS "+table);
	}

public void dropTableContact(String table){
	
	bdd.execSQL("DROP TABLE IF EXISTS "+table);
}




	
	


	
	
	
	

	



}
