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

public class MySQLiteGestion {

	private static final int VERSION_BDD = 1; 


	private static final int NUM_COL_COLUMN_ID = 0;
	private static final int NUM_COL_COLUMN_MESSAGE = 1;
	private static final int NUM_COL_COLUMN_DATE_SEND = 2;
	private static final int NUM_COL_COLUMN_DATE_RECEIPT =3;
	private static final int NUM_COL_COLUMN_IMAGE_SEND= 4; 
	private static final int NUM_COLUMN_STATUT_MESSAGE = 5;
	private static final int NUM_COL_COLUMN_ID_RECEIPT= 6; 
	private static final int NUM_COL_COLUMN_ID_SEND = 7;
	private static final int NUM_COL_COLUMN_ID_WEB = 8; 
	

	private static final int NUM_COL_COLUMN_TELEPHONE =1;
	private static final int NUM_COL_COLUMN_ID_CONTACT =2;
	private static final int NUM_COL_COLUMN_NOM =3; 
	private static final int NUM_COL_COLUMN_PHOTOS =4; 
	private static final int NUM_COL_COLUMN_BACKGROUND =5; 
	private static final int NUM_COL_COLUMN_MSG=6; 
	

	private static final int NUM_COL_COLUMN_IDPRODUIT =1;
	private static final int NUM_COL_COLUMN_NOMPRODUIT =2;
	private static final int NUM_COL_COLUMN_PRIXPRODUIT =3;
	private static final int NUM_COL_COLUMN_UNITE =4;
	private static final int NUM_COL_COLUMN_IMAGEPRODUIT =5;
	private static final int NUM_COL_COLUMN_QTE =6;
	
	 
	
	private SQLiteDatabase bdd;
 
	private MySQLiteCreate maBaseSQLite;
	
 
	public MySQLiteGestion(Context context){
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
	
	public void createTable(String table){
		
		bdd.execSQL("create table  " + table + "(" 
				  + MySQLAttributs.COLUMN_ID+ " integer primary key autoincrement, " 
				  + MySQLAttributs.COLUMN_IDWeb+ " integer not null, " 
				   + MySQLAttributs.COLUMN_ID_SEND+ " text not null," 
					      + MySQLAttributs.COLUMN_ID_RECEIPT+ " text not null," 
					    	      + MySQLAttributs.COLUMN_MESSAGE+ " text not null," 
					    	    	      + MySQLAttributs.COLUMN_IMAGE_SEND+ " text not null," 
					    	    	    	      + MySQLAttributs.COLUMN_DATE_SEND+ " text not null,"  
					    	    	    	    	    	      + MySQLAttributs.COLUMN_DATE_RECEIPT+ " text not null,"
							    			    	    	    	    	      + MySQLAttributs.COLUMN_STATUT_MESSAGE+ " text not null);");
	}
 
	
	/*
	 * 
	 * debut methodde permettant d'inserer plusieurs ligne dans le bd
	 * 
	 * 
	 * 
	 */
	public void   InsertMessage(JSONArray feedArray) throws  JSONException
	{
		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{

				JSONObject feedObj = (JSONObject) feedArray.get(i); 
				if(!getReturnMessageBool(feedObj.getInt("idWeb")))
				{
					String msg=feedObj.getString("message"); 
					//livreBdd.insertMessage(""+feedObj.getInt("idEmetteur"),""+feedObj.getInt("idRecepteur"),feedObj.getString("message"),encodedecode.DecodeString(feedObj.getString("dateReceiver")),encodedecode.DecodeString(feedObj.getString("dateMessage"))," ",feedObj.getString("statutMessage"));
					 
			 bdd.execSQL("INSERT INTO "+MySQLAttributs.TABLE_MESSAGES+" ( "
					+MySQLAttributs.COLUMN_IDWeb+","+MySQLAttributs.COLUMN_DATE_RECEIPT+","+MySQLAttributs.COLUMN_DATE_SEND+","+MySQLAttributs.COLUMN_ID_RECEIPT+","+MySQLAttributs.COLUMN_ID_SEND+","+MySQLAttributs.COLUMN_IMAGE_SEND+","+MySQLAttributs.COLUMN_MESSAGE+","+MySQLAttributs.COLUMN_STATUT_MESSAGE+")"
					+" VALUES("+feedObj.getInt("idWeb")+",'"+feedObj.getString("dateReceiver")+"','"+feedObj.getString("dateMessage")+"','"+feedObj.getInt("idRecepteur")+"','"+feedObj.getInt("idEmetteur")+"',' ','"+msg.replace("'","#")+"','"+feedObj.getString("statutmessage")+"')");
					
				}
				  
		//on insère l'objet dans la BDD via le ContentValues
			
			}
		}
	}
	
	/*
	 * 
	 * fin methodde permettant d'inserer plusieurs ligne dans le bd
	 * 
	 * 
	 * 
	 */
	
	public void   updateStatutMessage(JSONArray feedArray,int statut) throws  JSONException
	{
		int statutAvant=-1;
		if(statut==1)
		{
			statutAvant=0;
		}
		else
		{
			statutAvant=1;	
		}
		
		if(feedArray.length()>=0)
		{
			for (int i = 0; i < feedArray.length(); i++) 
			{
				
				JSONObject feedObj = (JSONObject) feedArray.get(i); 
				
				if(feedObj.getBoolean("statut"))
				 {
					if(statut==1)
					{
						bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET "
								+MySQLAttributs.COLUMN_IDWeb+"="+feedObj.getInt("idWeb")+"," 
								+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+feedObj.getString("statutmessage")+"' WHERE(("
								+MySQLAttributs.COLUMN_ID+"="+feedObj.getInt("idMobile")+")AND("+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+statutAvant+"'))");
														
					}
					else
					{
						bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET " 
								+MySQLAttributs.COLUMN_DATE_RECEIPT+"='"+feedObj.getString("dateReceiver")+"',"
								+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+feedObj.getString("statutmessage")+"' WHERE(("
								+MySQLAttributs.COLUMN_ID+"="+feedObj.getInt("idMobile")+")AND("+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+statutAvant+"'))");														
					}
	 
				 }
		/*
		 		bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET "
				+MySQLAttributs.COLUMN_IDWeb+"="+feedObj.getInt("idWeb")+","
				+MySQLAttributs.COLUMN_DATE_RECEIPT+"='"+feedObj.getString("dateReceiver")+"',"
				+MySQLAttributs.COLUMN_DATE_SEND+"='"+feedObj.getString("dateSender")
				+"',"+MySQLAttributs.COLUMN_ID_RECEIPT+"='"+feedObj.getString("receiver")
				+"',"+MySQLAttributs.COLUMN_ID_SEND+"='"+feedObj.getString("sender")
				+"',"+MySQLAttributs.COLUMN_IMAGE_SEND+"=' ',"
				+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+feedObj.getString("statutmessage")+"' WHERE(("
				+MySQLAttributs.COLUMN_ID+"="+feedObj.getInt("idMobile")+")AND("+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+statutAvant+"'))");
				 
		 */
				
		//on insère l'objet dans la BDD via le ContentValues
			
		
			}
		}
	}

/*
 * Methode qui update le statut dun utlisateur  
 * 	
 */
	public void updateStatutUsers(JSONArray feedArrayNumberPhone) throws JSONException
	{   
		if(feedArrayNumberPhone.length()>=0)
		{
			for (int i = 0; i < feedArrayNumberPhone.length(); i++) 
			{
				 
				JSONObject feedObj = (JSONObject) feedArrayNumberPhone.get(i);
				if(feedObj.getBoolean("statut"))
				{
					bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_NAME+" SET " 
							+MySQLAttributs.COLUMN_ID_CONTACT+"='"+feedObj.getString("id")+"'"+
							" WHERE ("+MySQLAttributs.COLUMN_TELEPHONE+"="+feedObj.getString("phoneNumber")+")");	
				}
			}
		}
	}	
/*
* fin du traitement
*/

	
	
	
/*
 * Methode qui update le background dun utlisateur lors du tchat
 * 	
 */
	public void updateBackgroundMessage(String photos,String telephone,String nom,String numBackground,String idContact,String idligne)
	{  
		/*
		bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_NAME+" SET "
				+MySQLAttributs.COLUMN_ID+"="+idligne+","
				+MySQLAttributs.COLUMN_PHOTOS+"='"+photos+"',"
				+MySQLAttributs.COLUMN_TELEPHONE+"='"+telephone+"',"				
				+MySQLAttributs.COLUMN_NOM+"='"+nom+"',"	 
				+MySQLAttributs.COLUMN_BACKGROUND+"='"+numBackground+"' " +
				" WHERE ("+MySQLAttributs.COLUMN_ID_CONTACT+"="+idContact+")");		
		*/

		bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_NAME+" SET " 
				+MySQLAttributs.COLUMN_PHOTOS+"='"+photos+"',"
				+MySQLAttributs.COLUMN_TELEPHONE+"='"+telephone+"',"				
				+MySQLAttributs.COLUMN_NOM+"='"+nom+"',"	 
				+MySQLAttributs.COLUMN_BACKGROUND+"='"+numBackground+"' " +
				" WHERE ("+MySQLAttributs.COLUMN_ID_CONTACT+"="+idContact+")");
	}
	
/*
* fin du traitement
*/

	
	public int getLigne(String idContact)
	{ 
		Cursor c = bdd.query(MySQLAttributs.TABLE_NAME, new String[] {
				MySQLAttributs.COLUMN_ID},"("+MySQLAttributs.COLUMN_ID_CONTACT + "="+idContact+")", null, null, null, null);
		
		return cursorToLigne(c);	 
		
		/*Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb},
				"(("+MySQLAttributs.COLUMN_ID_SEND + " in("+idUser+","+idContact+"))AND("+MySQLAttributs.COLUMN_ID_RECEIPT + " in("+idUser+","+idContact+"))) order by "+MySQLAttributs.COLUMN_ID+" ASC", null, null, null, null);
		return cursorToMsg(c); */
	}

	private int cursorToLigne(Cursor c) 
	{ 
		int retour = 0;
				
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
				{
					retour=c.getInt(0);
					break;
				}
				return retour; 
	}
	
	/*
	 * methode qui me renvoie le background de conversation entre deux personnes
	 */
	public String getBackgroundMessage(String idContact)
	{ 
		Cursor c = bdd.query(MySQLAttributs.TABLE_NAME, new String[] {
				MySQLAttributs.COLUMN_BACKGROUND}, 
				"("+MySQLAttributs.COLUMN_ID_CONTACT + "="+idContact+")", null, null, null, null);
		
		return cursorToBackground(c);	 
	}

		private String cursorToBackground(Cursor c) 
		{ 
			String retour="";
			if (c.getCount() == 0)
			{ 
				retour="init";
			}
			 
			else
			{	  
					for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
					{
						retour=c.getString(0);
						break;
					}
			}
		return retour; 
		}
	/*
	 * 
	 */
	
		
		
		
public void dropTable(String table){
		
		bdd.execSQL("DROP TABLE IF EXISTS "+table);
	}

public void dropTableContact(String table){
	
	bdd.execSQL("DROP TABLE IF EXISTS "+table);
}


public Boolean getReturnContact(String Contact) throws JSONException{
	//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
Cursor c = bdd.query(MySQLAttributs.TABLE_NAME, new String[] {
					MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_TELEPHONE}, "("+MySQLAttributs.COLUMN_TELEPHONE + "='"+Contact.replace(" ", "")+"') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
			return cursorToContact(c); 	
}

//Cette méthode permet de convertir un cursor en un livre
private Boolean cursorToContact(Cursor c)
{
	boolean verif=false;
	
	c.moveToFirst();
	
	if (c.getCount() == 0)
	{
		verif=false;		
	}
		
	else
	{
		c.getInt(NUM_COL_COLUMN_ID);
		c.getInt(NUM_COL_COLUMN_TELEPHONE);
		
		verif=true;
	}
	
	c.close();

	return verif;
}

/*insertion des contact dans la table des contact*/

/*
 * 
 * debut methodde permettant d'inserer plusieurs ligne dans le bd
 * 
 * 
 * 
 */
public void   insertcontact(JSONArray feedArray,String telMine) throws  JSONException
{
	String background="init";    
	boolean telverif=false; 


	String telephone=""; 
	
	if(feedArray.length()>=0)
	{
		for (int i = 0; i < feedArray.length(); i++) 
		{
			 
			JSONObject feedObj = (JSONObject) feedArray.get(i);
			 if(feedObj.getBoolean("statut"))
				{
		String tel=feedObj.getString("phoneNumber").replace(" ", "");
		
		if(!tel.equals(telMine))
			{		
				telverif=tel.contains("+");
				
				if(telverif)
				{
					telephone=tel.substring(1);
				}
				else
				{
					telephone=tel;
				}
			bdd.execSQL("INSERT INTO "+MySQLAttributs.TABLE_NAME+" ( "
					+MySQLAttributs.COLUMN_PHOTOS+","+MySQLAttributs.COLUMN_NOM+","+MySQLAttributs.COLUMN_TELEPHONE+","
					+MySQLAttributs.COLUMN_ID_CONTACT+","+MySQLAttributs.COLUMN_BACKGROUND+")"
					+" VALUES('"+feedObj.getString("photos")+"','"+feedObj.getString("name")+"','"
					+telephone+"',"+Integer.parseInt(feedObj.getString("id"))+",'"+background+"')");
					
			//on insère l'objet dans la BDD via le ContentValues
			}
				}
		}	
	}
}
/*
 * 
 * fin methodde permettant d'inserer plusieurs ligne dans le bd
 * 
 * 
 * 
 */

	public long insertMessage(String idSend,String idReceipt,String message,String dateReceipt,String dateSend,String intImage,String statut){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(MySQLAttributs.COLUMN_IDWeb,0); 
		values.put(MySQLAttributs.COLUMN_DATE_RECEIPT,dateReceipt); 
		values.put(MySQLAttributs.COLUMN_DATE_SEND, dateSend); 
		values.put(MySQLAttributs.COLUMN_ID_RECEIPT,idReceipt); 
		values.put(MySQLAttributs.COLUMN_ID_SEND,idSend); 
		values.put(MySQLAttributs.COLUMN_IMAGE_SEND, intImage); 
		values.put(MySQLAttributs.COLUMN_MESSAGE,message); 
		values.put(MySQLAttributs.COLUMN_STATUT_MESSAGE,statut); 
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(MySQLAttributs.TABLE_MESSAGES, null, values);
		
	}

	public int removeMessageWithID(int id){
		//Suppression d'un livre de la BDD grâce à l'ID
		return bdd.delete(MySQLAttributs.TABLE_MESSAGES, MySQLAttributs.COLUMN_ID + " = " +id, null);
	}
 
	
	/*
	 * cette méthode me retourne le statut d'un message
	 * 
	 * 
	 */
	
	public boolean getReturnStatutMessage(String idMobileMsg,String statut) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_IDWeb},
				"("+MySQLAttributs.COLUMN_IDWeb + "="+idMobileMsg+" and "+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+statut+"') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursoResultStatutmsg(c); 
 }

	//Cette méthode permet de convertir un cursor en un livre
	private boolean cursoResultStatutmsg(Cursor c) throws JSONException{
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		 boolean statut = false ;
		if (c.getCount() == 0)
		{
			   statut = false; 		
		}	 
		else
		{ 
		//On créé un livre
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
		{ 
			 statut = true;
		} 
		//On ferme le cursor
		c.close();
		//On retourne le message
	 	  
		}
	 
		return statut;
	}	
	
		/*
	 * fin de la méthode
	 * 
	 * 
	 */

	public JSONObject getReturnAllMsgLocal(String idUser,String idContact) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb},
				"(("+MySQLAttributs.COLUMN_ID_SEND + " in("+idUser+","+idContact+"))AND("+MySQLAttributs.COLUMN_ID_RECEIPT + " in("+idUser+","+idContact+"))) order by "+MySQLAttributs.COLUMN_ID+" ASC", null, null, null, null);
		return cursorToMsg(c); 
	
	}


public JSONObject getReturnContactEtalk() throws JSONException {
	//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
	Cursor c = bdd.query(MySQLAttributs.TABLE_NAME, new String[] {
			MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_TELEPHONE,MySQLAttributs.COLUMN_ID_CONTACT
			,MySQLAttributs.COLUMN_NOM,MySQLAttributs.COLUMN_PHOTOS,MySQLAttributs.COLUMN_BACKGROUND},null, null, null, null, null);
	return cursorToCotact(c); 
	 
}



	public JSONObject getReturnLigneMsg() throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb}, "("+MySQLAttributs.COLUMN_STATUT_MESSAGE + "='0') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursorToMsg(c); 
		 
 }
	

/**
 * 
 * methode qui me retourne les idWeb des messages dont le statut est egal a -1
 * */
	public JSONObject getReturnStatutMoins1Msg() throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb}, "("+MySQLAttributs.COLUMN_STATUT_MESSAGE + "='-1') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursorToMsg(c); 
 	 
		 
 }
	
	/**
	 * fin de la methode
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getReturnStatut1Msg() throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb}, "("+MySQLAttributs.COLUMN_STATUT_MESSAGE + "='1') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursorToMsg(c); 
 	
		
	}
	
	
	
	public JSONObject getReturnStatut0Msg() throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb},
				"("+MySQLAttributs.COLUMN_STATUT_MESSAGE + "='0') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursorToMsg(c); 
 	
		
	}
	



	
	public Boolean getReturnMessageBool(int idWeb) {
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_MESSAGE,MySQLAttributs.COLUMN_DATE_SEND
				,MySQLAttributs.COLUMN_DATE_RECEIPT,MySQLAttributs.COLUMN_IMAGE_SEND,MySQLAttributs.COLUMN_STATUT_MESSAGE
				,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,MySQLAttributs.COLUMN_IDWeb}, "("+MySQLAttributs.COLUMN_IDWeb + "="+idWeb+") order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursorToMessageBool(c); 
		 
	}
	//Cette méthode permet de convertir un cursor en un livre
	private JSONObject cursorToCotact(Cursor c) throws JSONException{
		//si aucun élément n'a été retourné dans la requête, on renvoie null
	
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	
		if (c.getCount() == 0)
		{
			jsonObjetSend.put("resultat", "Vide");
			jsonObjetSend.put("statut", false); 
			
		}
		 
		else
		{
	 
		//On créé un livre
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
		{
			 
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		
		jsonObjet = new JSONObject();
		jsonObjet.put("idContact",c.getInt(NUM_COL_COLUMN_ID_CONTACT));
		jsonObjet.put("nom",c.getString(NUM_COL_COLUMN_NOM));
		jsonObjet.put("telephone",c.getString(NUM_COL_COLUMN_TELEPHONE));
		jsonObjet.put("photos",c.getString(NUM_COL_COLUMN_PHOTOS));  
		jsonObjet.put("background",c.getString(NUM_COL_COLUMN_BACKGROUND));  
		json.put(jsonObjet);  
		 		
		} 
		//On ferme le cursor
		c.close();
		//On retourne le message
		
			jsonObjetSend.put("resultat", json);
			jsonObjetSend.put("statut", true);
		
		
		return jsonObjetSend;
		 
		}
	 
		return jsonObjetSend;
	}

	//Cette méthode permet de convertir un cursor en un livre
	private JSONObject cursorToMsg(Cursor c) throws JSONException{
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	
		if (c.getCount() == 0)
		{
			jsonObjetSend.put("resultat", "Vide");
			jsonObjetSend.put("statut", false);
			return jsonObjetSend;			
		}
			
		else
		{
		
		//On créé un livre
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
		{
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		
		jsonObjet = new JSONObject();
		jsonObjet.put("idMobile",c.getInt(NUM_COL_COLUMN_ID));
		jsonObjet.put("message",c.getString(NUM_COL_COLUMN_MESSAGE));
		jsonObjet.put("dateSender",c.getString(NUM_COL_COLUMN_DATE_SEND));
		jsonObjet.put("receiver",c.getString(NUM_COL_COLUMN_ID_RECEIPT));
		jsonObjet.put("sender", c.getString(NUM_COL_COLUMN_ID_SEND));
		jsonObjet.put("statutmessage", c.getString(NUM_COLUMN_STATUT_MESSAGE));
		jsonObjet.put("idWeb", c.getInt(NUM_COL_COLUMN_ID_WEB));
		jsonObjet.put("dateReceiver", c.getString(NUM_COL_COLUMN_DATE_RECEIPT));
		jsonObjet.put("idSender", c.getString(NUM_COL_COLUMN_ID_SEND));
		

		json.put(jsonObjet);
		
		} 
		//On ferme le cursor
		c.close();
		//On retourne le message
		
			jsonObjetSend.put("resultat", json);
			jsonObjetSend.put("statut", true);
		
		
		return jsonObjetSend;
		}
	}
	
	
	
	
	

	public JSONObject getReturnLastMsgLocal(int ID_CONTACT) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
 
		Cursor c2=	bdd.rawQuery("SELECT "+MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_ID+" ,"
				  						  +MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_TELEPHONE+","
				  						  +MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_ID_CONTACT+","
										  +MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_NOM+","
										  +MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_PHOTOS+","
										  +MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_BACKGROUND+","
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_MESSAGE+"," 
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_DATE_SEND+"," 
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_DATE_RECEIPT+"," 
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_IDWeb+","
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_ID_SEND+","
										  +MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_ID_RECEIPT
										  +" from "+MySQLAttributs.TABLE_NAME+","+MySQLAttributs.TABLE_MESSAGES
				+" where(("+MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_ID_CONTACT
				+" in("+MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_ID_SEND+","
				+MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_ID_RECEIPT+"))and("+MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_ID_CONTACT+"!="+ID_CONTACT+"))  ORDER BY "
				+MySQLAttributs.TABLE_NAME+"."+MySQLAttributs.COLUMN_ID_CONTACT +","+MySQLAttributs.TABLE_MESSAGES+"."+MySQLAttributs.COLUMN_ID +"  DESC",null);
		//si aucun élément n'a été retourné dans la requête, on renvoie null
 
		JSONArray json = new JSONArray();
		JSONObject jsonObjet = new JSONObject();
		JSONObject jsonObjetSend = new JSONObject();
	 
		if (c2.getCount() == 0)
		{
			jsonObjetSend.put("resultat", "Vide");
			jsonObjetSend.put("statut", false);
			return jsonObjetSend;			
		}
			
		else
		{		
		//On créé un livre
			for( c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext() )
			{
			//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor		
			jsonObjet = new JSONObject();
 
			jsonObjet.put("idMobile",c2.getInt(NUM_COL_COLUMN_ID));
			jsonObjet.put("idContact",c2.getInt(NUM_COL_COLUMN_ID_CONTACT));
			jsonObjet.put("message",c2.getString(NUM_COL_COLUMN_MSG));
			jsonObjet.put("telephone",c2.getString(NUM_COL_COLUMN_TELEPHONE));
			jsonObjet.put("nom", c2.getString(NUM_COL_COLUMN_NOM));
			jsonObjet.put("photos", c2.getString(NUM_COL_COLUMN_PHOTOS)); 
			jsonObjet.put("background", c2.getString(NUM_COL_COLUMN_BACKGROUND));
			jsonObjet.put("dateSender", c2.getString(6)); 
			jsonObjet.put("dateReceiver", c2.getString(7));
			jsonObjet.put("idWeb", c2.getInt(8)); 
			jsonObjet.put("sender", c2.getInt(9)); 
			jsonObjet.put("receiver", c2.getInt(10)); 
			
			json.put(jsonObjet);		
			}  
		
			jsonObjetSend.put("resultat", json);
			jsonObjetSend.put("statut", true);
		
		}
		 
		return jsonObjetSend; 
	
	}
	
 

	
	
	
	
	private Boolean cursorToMessageBool(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		Boolean retour=false;
		if (c.getCount() != 0)
			
		{
		
		retour=true;
		c.moveToFirst();
		//On créé un livre
		
		c.close();
 
		//On retourne le livre
		
		}
		
		return  retour;
	}
	
	
	
	/*
	 * ******************************************************
	 * 
	 * 
	 *  debut nouvelle methode utilsant la techonologie gcm 
	 * 
	 * 
	 * ********************************************************
	 */
	
	
	/*
	 * 
	 * debut methodde permettant d'inserer une ligne dans le bd
	 * 
	 * 
	 * 
	 */
	public void   InsertMessageGcm(int idWeb ,String dateReceiver,String dateSend,int idRecepteur,int idEmeteur, String Message,String statutMsg) throws  JSONException
	{
		 
				
					String msg=Message; 
					//livreBdd.insertMessage(""+feedObj.getInt("idEmetteur"),""+feedObj.getInt("idRecepteur"),feedObj.getString("message"),encodedecode.DecodeString(feedObj.getString("dateReceiver")),encodedecode.DecodeString(feedObj.getString("dateMessage"))," ",feedObj.getString("statutMessage"));
					 
			 bdd.execSQL("INSERT INTO "+MySQLAttributs.TABLE_MESSAGES+" ( "
					+MySQLAttributs.COLUMN_IDWeb+","+MySQLAttributs.COLUMN_DATE_RECEIPT+","+MySQLAttributs.COLUMN_DATE_SEND+","+MySQLAttributs.COLUMN_ID_RECEIPT+","+MySQLAttributs.COLUMN_ID_SEND+","+MySQLAttributs.COLUMN_IMAGE_SEND+","+MySQLAttributs.COLUMN_MESSAGE+","+MySQLAttributs.COLUMN_STATUT_MESSAGE+")"
					+" VALUES("+idWeb+",'"+dateReceiver+"','"+dateSend+"','"+idRecepteur+"','"+idEmeteur+"',' ','"+msg.replace("'","#")+"','"+statutMsg+"')");
					
				
				
		//on insère l'objet dans la BDD via le ContentValues
			
		
	}
	
	/*
	 * 
	 * fin methodde permettant d'inserer une ligne dans le bd
	 * 
	 * 
	 * 
	 */
	
	
	
	
	/*
	 * 
	 * methode qui me permet de changer le statue d'un message qui provient d'une notification a 2
	 */
	
	
	
	public void updateStatutMessage(String idSender,String idReceipt)
	{  	 
		bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET " 
				+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='3'"+
				" WHERE ("+MySQLAttributs.COLUMN_ID_SEND+"="+idSender+" and "+MySQLAttributs.COLUMN_ID_RECEIPT+"="+idReceipt+")");
	}
	
	public void updateStatutMessageSend(int idMobile)
	{  	 
		bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET " 
				+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='3'"+
				" WHERE ("+MySQLAttributs.COLUMN_ID+"="+idMobile+")");
	}
	
	/*
	 * fin de la methode
	 * 
	 */
	/*
	 * 
	 * debut méthode qui permert de verifier si un contact a envoyé un ou plusiuer message
	 * 
	 * 
	 */
	
	public int getVerifMsg(String idSender,String idRecepteur) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_MESSAGES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_ID_RECEIPT,MySQLAttributs.COLUMN_ID_SEND,
				MySQLAttributs.COLUMN_STATUT_MESSAGE},
				"("+MySQLAttributs.COLUMN_ID_RECEIPT + "='"+idRecepteur+"' and " +
						""+MySQLAttributs.COLUMN_ID_SEND + "='"+idSender+"' and " +
								""+MySQLAttributs.COLUMN_STATUT_MESSAGE + "='-1')  order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
	//""+MySQLAttributs.COLUMN_STATUT_MESSAGE + "=1) " +
		return cursoVerifMsg(c); 
 }

	//Cette méthode permet de convertir un cursor en un livre
	private int cursoVerifMsg(Cursor c) throws JSONException{
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		 int numbers = 0 ;
		if (c.getCount() == 0)
		{
			numbers = 0; 		
		}	 
		else
		{ 
		//On créé un livre
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
		{ 
			
			numbers++;
		} 
		//On ferme le cursor
		c.close();
		//On retourne le message
	 	  
		}
	 
		return numbers;
	}	
	
	
	
	
	/*
	 * fin de la methode
	 * 
	 */
	
/*
 * debut Methode qui update le statut d'un message lorsque le recpteur recoi le meassage 
 * 	
 */
	
	public void   updateStatutRexu(int idMobile,String dateRecpt,String statutMsg) throws  JSONException
	{
	

						bdd.execSQL("UPDATE "+MySQLAttributs.TABLE_MESSAGES+" SET " 
								+MySQLAttributs.COLUMN_DATE_RECEIPT+"='"+dateRecpt+"',"
								+MySQLAttributs.COLUMN_STATUT_MESSAGE+"='"+statutMsg+"' WHERE(("
								+MySQLAttributs.COLUMN_ID+"="+idMobile+") and "+
								MySQLAttributs.COLUMN_STATUT_MESSAGE+"!='3')");														

		
	}
	
/*
 * fin Methode qui update le statut d'un message lorsque le recpteur recoi le meassage 
 * 	
 */
	/*
	 * ******************************************************
	 * 
	 * 
	 *  fin nouvelle methode utilsant la techonologie gcm 
	 * 
	 * 
	 * ********************************************************
	 */
	
	
	
	
	
	
	
	
	
	
	public void createdTableContact()
	{		
		bdd.execSQL(MySQLiteCreate.TABLE_CONTACT);		
	}
	
	
	/*
	 * 
	 * debut des méthodes pour enregistrer , recuperer et supprimer les commande dans la table des commandes
	 * 
	 */
	
	public boolean GestionCommandes(String idProduit,String nomProduit,String prixProduit,String imageProduit,String unite,int qte) throws JSONException{
		boolean verif=false;
		if(getVerifCommandes(idProduit))
		{
			verif=true;
		}
		else
		{
			insertCommandes(idProduit, nomProduit, prixProduit,imageProduit,unite,qte);	
		}
		return verif;
	}
	
	public long insertCommandes(String idProduit,String nomProduit,String prixProduit,String imageProduit,String unite,int qte){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)		
		values.put(MySQLAttributs.COLUMN_ID_PRODUIT,idProduit);   
		values.put(MySQLAttributs.COLUMN_NOM_PRODUIT,nomProduit); 
		values.put(MySQLAttributs.COLUMN_PRIX_PRODUIT,prixProduit);
		values.put(MySQLAttributs.COLUMN_UNITE,unite); 
		values.put(MySQLAttributs.COLUMN_IMAGE_PRODUIT,imageProduit); 
		values.put(MySQLAttributs.COLUMN_QTE,qte);  
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(MySQLAttributs.TABLE_COMMANDES, null, values);
		
	}
 
	
	public boolean getVerifCommandes(String idProduit) throws JSONException{
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(MySQLAttributs.TABLE_COMMANDES, new String[] {
				MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_ID_PRODUIT},
				"("+MySQLAttributs.COLUMN_ID_PRODUIT + "='"+idProduit+"') order by "+MySQLAttributs.COLUMN_ID+" DESC", null, null, null, null);
		return cursoResult(c); 
 }

	//Cette méthode permet de convertir un cursor en un livre
	private boolean cursoResult(Cursor c) throws JSONException{
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		 boolean statut = false ;
		if (c.getCount() == 0)
		{
			   statut = false; 		
		}	 
		else
		{ 
		//On créé un livre
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
		{ 
			 statut = true;
		} 
		//On ferme le cursor
		c.close();
		//On retourne le message
	 	  
		}
	 
		return statut;
	}	
	
	
public JSONObject getReturnCommandes() throws JSONException {
	//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
	Cursor c = bdd.query(MySQLAttributs.TABLE_COMMANDES, new String[] {
	MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_ID_PRODUIT,MySQLAttributs.COLUMN_NOM_PRODUIT,
	MySQLAttributs.COLUMN_PRIX_PRODUIT,MySQLAttributs.COLUMN_UNITE,
	MySQLAttributs.COLUMN_IMAGE_PRODUIT,MySQLAttributs.COLUMN_QTE},null, null, null, null, null);
	return cursorToCommandes(c); 
	 
}



//Cette méthode permet de convertir un cursor en un livre
private JSONObject cursorToCommandes(Cursor c) throws JSONException{
	//si aucun élément n'a été retourné dans la requête, on renvoie null

	JSONArray json = new JSONArray();
	JSONObject jsonObjet = new JSONObject();
	JSONObject jsonObjetSend = new JSONObject();

	if (c.getCount() == 0)
	{	
		jsonObjet = new JSONObject(); 
		jsonObjet.put("idProduit","vide");
		jsonObjet.put("nomProduit","vide");
		jsonObjet.put("prixProduit","vide"); 
		jsonObjet.put("uniteProduit","vide"); 
		jsonObjet.put("imageProduit","vide"); 
		jsonObjet.put("qteProduit","vide"); 
		json.put(jsonObjet); 
		jsonObjetSend.put("resultat",json);
		jsonObjetSend.put("statut", false); 		
	}	 
	else
	{ 
	//On créé un livre
	for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
	{		 
	//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor	
	jsonObjet = new JSONObject(); 
	jsonObjet.put("idProduit",c.getInt(NUM_COL_COLUMN_IDPRODUIT));
	jsonObjet.put("nomProduit",c.getString(NUM_COL_COLUMN_NOMPRODUIT));
	jsonObjet.put("prixProduit",c.getString(NUM_COL_COLUMN_PRIXPRODUIT)); 
	jsonObjet.put("taille",c.getString(NUM_COL_COLUMN_UNITE)); 
	jsonObjet.put("imageProduit",c.getString(NUM_COL_COLUMN_IMAGEPRODUIT)); 
	jsonObjet.put("qteProduit",c.getString(NUM_COL_COLUMN_QTE));  
	json.put(jsonObjet);  	 		
	} 
	//On ferme le cursor
	c.close();
	//On retourne le message
	
		jsonObjetSend.put("resultat", json);
		jsonObjetSend.put("statut", true);
	
	
	return jsonObjetSend;
	 
	}
 
	return jsonObjetSend;
}	


public void TRUNCATETable(String table){
	bdd.execSQL("delete from "+ table); 
}

public void TRUNCATE_row_Table(String table,int idProduit){
	bdd.execSQL("delete from "+ table+" WHERE "+MySQLAttributs.COLUMN_ID_PRODUIT+"='"+idProduit+"'"); 
}




/*
 * 
 * methode qui me retourene le nombre de commande enregisteer en local sur mon tel
 */


public int getReturnNombreCommandes() throws JSONException {
//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
Cursor c = bdd.query(MySQLAttributs.TABLE_COMMANDES, new String[] {
MySQLAttributs.COLUMN_ID,MySQLAttributs.COLUMN_ID_PRODUIT,MySQLAttributs.COLUMN_NOM_PRODUIT,
MySQLAttributs.COLUMN_PRIX_PRODUIT,MySQLAttributs.COLUMN_UNITE,MySQLAttributs.COLUMN_IMAGE_PRODUIT},null, null, null, null, null);
return cursorCountResult(c); 
 
}

//Cette méthode permet de convertir un cursor en un livre
private int cursorCountResult(Cursor c) throws JSONException{
	//si aucun élément n'a été retourné dans la requête, on renvoie null
	 int statut = 0 ;
	if (c.getCount() == 0)
	{
		   statut = 0; 		
	}	 
	else
	{ 
	//On créé un livre
	for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
	{ 
		 statut++;
	} 
	//On ferme le cursor
	c.close();
	//On retourne le message
 	  
	}
 
	return statut;
}	

/*
 * fin de le methode
 * 
 */
	/*
	 * 
	 * fin de la méthode
	 */





public void addTableCmde()
{		
	bdd.execSQL(MySQLiteCreate.ALTER_TABLE_CMDE);
}
	

public void getReturnNbreColumn(){
	//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

	Cursor c=	bdd.rawQuery("pragma table_info("+MySQLAttributs.TABLE_COMMANDES+")",null);
	//si aucun élément n'a été retourné dans la requête, on renvoie null


 
	if (!(c.getCount() > 6))
	{
		
		addTableCmde();
	}
		
	
}


}
