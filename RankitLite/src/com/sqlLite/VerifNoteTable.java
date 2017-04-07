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

public class VerifNoteTable {


	
	  public static final String TABLE_VERIFICATION_NOTE = "verifNote";
	  public static final String COLUMN_VERIFICATION_ID = "_id";
	  public static final String COLUMN_VERIFICATION_IDNote = "verification_Idnote";
	  public static final String COLUMN_VERIFICATION_Date = "verification_date";

	
	private SQLiteDatabase bdd;
 
	private MySQLiteCreate maBaseSQLite;
	
 
	public VerifNoteTable(Context context){
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
	
	
	public void   InsertLigne(int verification_IdNote ,String date_jour) throws  JSONException
	{
	 bdd.execSQL("INSERT INTO "+TABLE_VERIFICATION_NOTE+" ( "
					+COLUMN_VERIFICATION_IDNote+","+COLUMN_VERIFICATION_Date+")"
					+" VALUES("+verification_IdNote+",'"+date_jour+"')");
					
	}
	
	
	public boolean getVerification(int idVerification, String date_jour){
		 boolean statut = false ;
		Cursor c = bdd.query(TABLE_VERIFICATION_NOTE, new String[] {
				COLUMN_VERIFICATION_IDNote,COLUMN_VERIFICATION_Date},
				"(("+COLUMN_VERIFICATION_IDNote+ "="+idVerification+")and("+COLUMN_VERIFICATION_Date+ "='"+date_jour+"'))", null, null, null, null);
		
			if (c.getCount() == 0)
			{
				   statut = false; 		
			}	 
			else
			{ 
			
			for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
			{ 
				 statut = true;
			} 
			
			c.close();
		
		 	  
			}
		 
			return statut;
 }



	
public void dropTable(String table){
		
		bdd.execSQL("DROP TABLE IF EXISTS "+table);
	}

public void dropTableContact(String table){
	
	bdd.execSQL("DROP TABLE IF EXISTS "+table);
}




	
	


	
	
	
	

	



}
