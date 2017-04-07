package com.sqlLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SousCatTable {



	  public static final String TABLE_SOUSCAT = "sousCat";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_ID_SOUSCAT = "sousCat_id";
	   public static final String COLUMN_ID_SECTEUR= "secteur_id";
	  public static final String COLUMN_SOUSCAT_NAME = "sousCat_name";
	  public static final String COLUMN_ID_LANGUE = "id_langue";
	  public static final String COLUMN_STATUT = "sousCat_statut";

	private SQLiteDatabase bdd;

	private MySQLiteCreate maBaseSQLite;


	public SousCatTable(Context context){
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
	
	public JSONArray getListSousCat(String  langue_ab,int id_secteur) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
 
		Cursor c2=	bdd.rawQuery("SELECT "+TABLE_SOUSCAT+"."+COLUMN_ID_SOUSCAT+" ,"
				  						  +TABLE_SOUSCAT+"."+COLUMN_SOUSCAT_NAME+","
				  						  +TABLE_SOUSCAT+"."+COLUMN_ID_SECTEUR+","
				                          +TABLE_SOUSCAT+"."+COLUMN_ID_LANGUE+","
										  +TABLE_SOUSCAT+"."+COLUMN_STATUT+","
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+"," 
										 
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB
										  +" from "+TABLE_SOUSCAT+","+LangueTable.TABLE_LANGUE
				+" where(("+TABLE_SOUSCAT+"."+COLUMN_ID_LANGUE+" ="+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+")"
				
				+"and("+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB+"='"+langue_ab+"')and("+TABLE_SOUSCAT+"."+COLUMN_ID_SECTEUR+"="+id_secteur+"))  ORDER BY "
				+TABLE_SOUSCAT+"."+COLUMN_SOUSCAT_NAME +"  COLLATE NOCASE",null);
		//si aucun élément n'a été retourné dans la requête, on renvoie null
 
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	 
		
		{		
		//On créé un livre
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
			//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor		
			jsonObjet = new JSONObject();
 
			jsonObjet.put(COLUMN_ID_SOUSCAT,c2.getInt(0));
			jsonObjet.put(COLUMN_SOUSCAT_NAME,c2.getString(1).replace("#", "'"));
			jsonObjet.put(COLUMN_ID_SECTEUR,c2.getInt(2));
			jsonObjet.put(COLUMN_ID_LANGUE,c2.getInt(3));
			jsonObjet.put(COLUMN_STATUT,c2.getInt(4));
			jsonObjet.put(LangueTable.COLUMN_LANGUE_ID, c2.getInt(5));
			jsonObjet.put(LangueTable.COLUMN_LANGUE_AB, c2.getString(6));
			
			
			json.put(jsonObjet);		
			}  
		
			jsonObjetSend.put("resultat", json);
			jsonObjetSend.put("statut", true);
		
		}
		 
		return json; 
	
	}
 

	public void   insertListeSousCat(JSONArray feedArray) throws  JSONException
	{

		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{
				 
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				bdd.execSQL("INSERT INTO "+TABLE_SOUSCAT+" ( "
						+COLUMN_ID_SOUSCAT+","
						+COLUMN_ID_SECTEUR+","
						+COLUMN_SOUSCAT_NAME+","
						+COLUMN_ID_LANGUE+","
						+COLUMN_STATUT+")"
						+" VALUES("+feedObj.getInt(COLUMN_ID_SOUSCAT)
						+","+feedObj.getInt(COLUMN_ID_SECTEUR)
						+",'"+feedObj.getString(COLUMN_SOUSCAT_NAME).replace("'", "#")+"'"
						+","+feedObj.getString(COLUMN_ID_LANGUE)+","
						
						+feedObj.getInt(COLUMN_STATUT)+")");
						
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
