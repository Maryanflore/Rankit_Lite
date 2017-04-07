package com.sqlLite;

public class MySQLAttributs {
	  public static final String TABLE_MESSAGES = "etalk";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_IDWeb = "id_web";
	  public static final String COLUMN_ID_SEND = "idSend";
	  public static final String COLUMN_ID_RECEIPT = "idReceipt";
	  public static final String COLUMN_MESSAGE = "message";
	  public static final String COLUMN_IMAGE_SEND = "imageMessage";
	  public static final String COLUMN_DATE_SEND = "dateEmeteur";
	  public static final String COLUMN_DATE_RECEIPT = "dateRecepteur"; 
	  public static final String COLUMN_STATUT_MESSAGE = "statut";  
	  
	  /*champ pour la tables des commandes*/
	  public static final String TABLE_COMMANDES = "commandes"; 
	  public static final String COLUMN_ID_PRODUIT = "idProduit";  
	  public static final String COLUMN_ID_UTILISATEUR = "idUser";  
	  public static final String COLUMN_NOM_PRODUIT = "nomProduit"; 
	  public static final String COLUMN_IMAGE_PRODUIT = "imageProduit";
	  public static final String COLUMN_PRIX_PRODUIT = "prix";  
	  public static final String COLUMN_UNITE = "unite";   
	  public static final String COLUMN_QTE = "quantite";  
	   
		/*champ pour la bd de contact*/  
	  public static final String TABLE_NAME = "contact"; 
	  public static final String COLUMN_PHOTOS = "photos";
	  public static final String COLUMN_TELEPHONE = "telephone";
	  public static final String COLUMN_NOM = "nomPrenom";
	  public static final String COLUMN_ID_CONTACT = "idReceipt";
	  public static final String COLUMN_BACKGROUND = "background";
	  public static final String COLUMN_STATUT_USER = "statut"; 
	  
	  
	  
	  public static final String DATABASE_NAME = "sime.db";
	  public static final int DATABASE_VERSION = 1;
	  
}
