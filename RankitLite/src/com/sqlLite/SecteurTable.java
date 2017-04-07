package com.sqlLite; 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.reponse.Reponse;
import com.volley.Const;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SecteurTable {


	
	  public static final String TABLE_SECTEUR = "secteur";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_ID_SECTEUR = "secteur_id";
	 public static final String COLUMN_TYPE_SECTEUR = "secteur_type";
	  public static final String COLUMN_SECTEUR_NAME = "secteur_name";
	  public static final String COLUMN_ID_LANGUE = "id_langue";
	  public static final String COLUMN_STATUT = "secteur_statut";

	
	private SQLiteDatabase bdd;
 
	private MySQLiteCreate maBaseSQLite;
	
 
	public SecteurTable(Context context){
		//On cr�er la BDD et sa table
		maBaseSQLite = new MySQLiteCreate (context, MySQLAttributs.DATABASE_NAME, null, MySQLAttributs.DATABASE_VERSION);

	}
 
	public void open(){
		//on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public JSONArray getListSecteur(String  langue_ab) throws JSONException{
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
 
		Cursor c2=	bdd.rawQuery("SELECT "+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" ,"
				  						  +TABLE_SECTEUR+"."+COLUMN_SECTEUR_NAME+","
				  						  +TABLE_SECTEUR+"."+COLUMN_ID_LANGUE+","
										  +TABLE_SECTEUR+"."+COLUMN_STATUT+","
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+"," 
										 
										 
										  +LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB
										  +" from "+TABLE_SECTEUR+","+LangueTable.TABLE_LANGUE
				+" where(("+TABLE_SECTEUR+"."+COLUMN_ID_LANGUE+" ="+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+")"
				
				+"and("+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB+"='"+langue_ab+"'))  ORDER BY "
				+TABLE_SECTEUR+"."+COLUMN_SECTEUR_NAME +"  COLLATE NOCASE",null);
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
 
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	 
		
		{		
		//On cr�� un livre
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
			//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor		
			jsonObjet = new JSONObject();
 
			jsonObjet.put(COLUMN_ID_SECTEUR,c2.getInt(0));
			jsonObjet.put(COLUMN_SECTEUR_NAME,c2.getString(1).replace("#", "'"));
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


	public JSONArray getListeSecteur(String  langue_ab,String type_secteur) throws JSONException{
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)

		Cursor c2=	bdd.rawQuery("SELECT "+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" ,"
				+TABLE_SECTEUR+"."+COLUMN_SECTEUR_NAME+","
				+TABLE_SECTEUR+"."+COLUMN_ID_LANGUE+","
				+TABLE_SECTEUR+"."+COLUMN_STATUT+","

				+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+","


				+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB
				+" from "+TABLE_SECTEUR+","+LangueTable.TABLE_LANGUE
				+" where(("+TABLE_SECTEUR+"."+COLUMN_ID_LANGUE+" ="+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_ID+")"

				+"and("+LangueTable.TABLE_LANGUE+"."+LangueTable.COLUMN_LANGUE_AB+"='"+langue_ab+"')and("+TABLE_SECTEUR+"."+COLUMN_TYPE_SECTEUR+"='"+type_secteur+"'))  ORDER BY "
				+TABLE_SECTEUR+"."+COLUMN_SECTEUR_NAME +"  COLLATE NOCASE",null);
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null

		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();


		{
			//On cr�� un livre
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
				//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
				jsonObjet = new JSONObject();

				jsonObjet.put(COLUMN_ID_SECTEUR,c2.getInt(0));
				jsonObjet.put(COLUMN_SECTEUR_NAME,c2.getString(1).replace("#", "'"));
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

	public int getLastIdSecteur(int type){
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
int id_last=0;
		Cursor c2=null ;
		if(type==0)

		{
			c2=	bdd.rawQuery("SELECT "+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" "

					+" from "+TABLE_SECTEUR
					+" ORDER BY "
					+TABLE_SECTEUR+"."+COLUMN_ID +" ASC LIMIT 1 ",null);
		}
		else if(type==1)


		{
			c2=	bdd.rawQuery("SELECT "+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" "

					+" from "+TABLE_SECTEUR
					+" ORDER BY "
					+TABLE_SECTEUR+"."+COLUMN_ID +" DESC LIMIT 1 ",null);
		}

		else


		{
			c2=	bdd.rawQuery("SELECT "+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" "

					+" from "+TABLE_SECTEUR
					+" where("+TABLE_SECTEUR+"."+COLUMN_ID_SECTEUR+" <"+type+")"
					+" ORDER BY "
					+TABLE_SECTEUR+"."+COLUMN_ID +" DESC LIMIT 1 ",null);
		}




			//On cr�� un livre
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
				//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor

				id_last=c2.getInt(0);








		}

		return id_last;

	}





	public void   insertListeSecteur(JSONArray feedArray) throws  JSONException
	{

		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{
				 
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				bdd.execSQL("INSERT INTO "+TABLE_SECTEUR+" ( "
						+COLUMN_ID_SECTEUR+","
						+COLUMN_TYPE_SECTEUR+","
						+COLUMN_SECTEUR_NAME+","
						+COLUMN_ID_LANGUE+","
						+COLUMN_STATUT+")"
						+" VALUES("+feedObj.getInt(COLUMN_ID_SECTEUR)
						+",'"+feedObj.getString(COLUMN_TYPE_SECTEUR).replace("'", "#")+"'"
						+",'"+feedObj.getString(COLUMN_SECTEUR_NAME).replace("'", "#")+"'"
						+","+feedObj.getInt(COLUMN_ID_LANGUE)+","
						
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
