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

public class ProducTable {


	
	  public static final String TABLE_PRODUCT = "product";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_ID_CRITERE_NOTATION = "critere_id";
	  public static final String COLUMN_ID_PRODUCT = "product_id";
	  public static final String COLUMN_ID_SOUS_CAT = "sousCat_id";
	  public static final String COLUMN_CRITERE_NAME = "critere_name";
	  public static final String COLUMN_ID_LANGUE = "id_langue";
	  public static final String COLUMN_STATUT = "critere_statut";  
	
	private SQLiteDatabase bdd;
 
	private MySQLiteCreate maBaseSQLite;
	
 
	public ProducTable(Context context){
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
	
	public JSONArray getListCritere(String  langue_ab,int idSousCat,int idProduct) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
 
		Cursor c2=	bdd.rawQuery("SELECT "+TABLE_PRODUCT+"."+COLUMN_ID_CRITERE_NOTATION+" ,"
		                                 
				  						  +TABLE_PRODUCT+"."+COLUMN_CRITERE_NAME+","
				  						  +TABLE_PRODUCT+"."+COLUMN_ID_LANGUE+","
										  +TABLE_PRODUCT+"."+COLUMN_STATUT+","
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+"," 
										 
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB
										  +" from "+TABLE_PRODUCT+","+LangueTable.TABLE_LANGUE
				+" where((("+TABLE_PRODUCT+"."+COLUMN_ID_LANGUE+" ="+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+")"
				
				+"and("+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB+"='"+langue_ab+"')and(("+TABLE_PRODUCT+"."+COLUMN_ID_SOUS_CAT+"="+idSousCat+")or("+TABLE_PRODUCT+"."+COLUMN_ID_PRODUCT+"="+idProduct+"))))  ORDER BY "
				+TABLE_PRODUCT+"."+COLUMN_ID_CRITERE_NOTATION +"  DESC",null);
		//si aucun élément n'a été retourné dans la requête, on renvoie null
 
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	 
		
		{		
	
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
			//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor		
			jsonObjet = new JSONObject();
 
			jsonObjet.put(COLUMN_ID_CRITERE_NOTATION,c2.getInt(0));
			jsonObjet.put(COLUMN_CRITERE_NAME,c2.getString(1).replace("#", "'"));
			jsonObjet.put(COLUMN_ID_LANGUE,c2.getInt(2));
			jsonObjet.put(COLUMN_STATUT,c2.getInt(3));
			jsonObjet.put(LangueTable.COLUMN_LANGUE_ID, c2.getInt(4));
			jsonObjet.put(LangueTable.COLUMN_LANGUE_AB, c2.getString(5)); 
			
			
			json.put(jsonObjet);		
			}  
		
			jsonObjetSend.put("resultat", json);
			jsonObjetSend.put("statut", true);
		
		}
		 
		return json; 
	
	}
 

	public void   insertListeCritereNotation(JSONArray feedArray) throws  JSONException
	{

		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{
				 
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				bdd.execSQL("INSERT INTO "+TABLE_PRODUCT+" ( "
						+COLUMN_ID_CRITERE_NOTATION+","
						+COLUMN_ID_SOUS_CAT+","
						+COLUMN_ID_PRODUCT+","
						+COLUMN_CRITERE_NAME+","
						+COLUMN_ID_LANGUE+","
						+COLUMN_STATUT+")"
						+" VALUES("+feedObj.getInt(COLUMN_ID_CRITERE_NOTATION)
						+","+feedObj.getInt(COLUMN_ID_SOUS_CAT)
						+","+feedObj.getInt(COLUMN_ID_PRODUCT)
						+",'"+feedObj.getString(COLUMN_CRITERE_NAME).replace("'", "#")+"'"
						+","+feedObj.getInt(COLUMN_ID_LANGUE)+","
						
						+feedObj.getInt(COLUMN_STATUT)+")");
						
			}
			
		}
	}

	
public void dropTable(String table){
		
		bdd.execSQL("DROP TABLE IF EXISTS "+table);
	}

public void CreateTableCritere()
{		
	bdd.execSQL(MySQLiteCreate.TABLE_CRITERE_NOTATION);
}



	
	


	
	
	
	

	



}
