package com.sqlLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteCreate  extends SQLiteOpenHelper {


	/***
	 *
	 * Creation table Secteur
	 */
	private static final String TABLE_SECTEUR = "create table if not exists " + SecteurTable. TABLE_SECTEUR+ "("
			+ SecteurTable.COLUMN_ID+ " integer primary key autoincrement, "
			+ SecteurTable.COLUMN_ID_SECTEUR+ " integer not null, "
			+ SecteurTable.COLUMN_TYPE_SECTEUR+ " text not null, "
			+ SecteurTable.COLUMN_ID_LANGUE+ " integer not null,"
			+ SecteurTable.COLUMN_SECTEUR_NAME+ " text not null,"
			+ SecteurTable.COLUMN_STATUT+ " integer not null);";



	/***
	 *
	 * Creation table Secteur
	 */
	private static final String TABLE_SOUSCAT = "create table if not exists " + SousCatTable. TABLE_SOUSCAT+ "("
			+ SousCatTable.COLUMN_ID+ " integer primary key autoincrement, "
			+ SousCatTable.COLUMN_ID_SOUSCAT+ " integer not null, "
			+ SousCatTable.COLUMN_ID_SECTEUR+ " integer not null, "
			+ SousCatTable.COLUMN_ID_LANGUE+ " integer not null,"
			+ SousCatTable.COLUMN_SOUSCAT_NAME+ " text not null,"
			+ SousCatTable.COLUMN_STATUT+ " integer not null);";


	/***
		 * 
		 * Creation table Service
		 */
		  public static final String TABLE_CRITERE = "create table if not exists " + CritereNotationTable. TABLE_CRITERE+ "("
				  + CritereNotationTable.COLUMN_ID+ " integer primary key autoincrement, "
				  + CritereNotationTable.COLUMN_ID_CRITERE+ " integer not null, "
				  + CritereNotationTable.COLUMN_ID_SECTEUR+ " integer not null, "
				  + CritereNotationTable.COLUMN_ID_ENTREPRISE+ " integer not null, "
			     + CritereNotationTable.COLUMN_ID_SOUSCAT+ " integer not null, "
			      + CritereNotationTable.COLUMN_ID_PRODUCT+ " integer not null, "
				   + CritereNotationTable.COLUMN_ID_LANGUE+ " integer not null,"
					      + CritereNotationTable.COLUMN_CRITERE_NAME+ " text not null,"
	                               + CritereNotationTable.COLUMN_STATUT+ " integer not null);";
	 
	  
		  
		  /***
			 * 
			 * Creation table Critere de NOtation
			 */
			  public static final String TABLE_CRITERE_NOTATION = "create table if not exists " + ProducTable.TABLE_PRODUCT+ "(" 
					  + ProducTable.COLUMN_ID+ " integer primary key autoincrement, "
					  + ProducTable.COLUMN_ID_CRITERE_NOTATION+ " integer not null, "
					  + ProducTable.COLUMN_ID_SOUS_CAT+ " integer not null, "
					  + ProducTable.COLUMN_ID_PRODUCT+ " integer not null, "
					   + ProducTable.COLUMN_ID_LANGUE+ " integer not null," 
						      + ProducTable.COLUMN_CRITERE_NAME+ " text not null," 
		                               + ProducTable.COLUMN_STATUT+ " integer not null);";
		 
		  

		/***
		 * 
		 * Creation table Langue
		 */
		  private static final String TABLE_LANGUE = "create table if not exists " + LangueTable.TABLE_LANGUE+ "(" 
				  + LangueTable.COLUMN_LANGUE_ID+ " integer primary key autoincrement, " 
	                + LangueTable.COLUMN_LANGUE_AB+ " text not null);";
		  
		  /***
			 * 
			 * Creation table Verification Notes
			 */
			  private static final String TABLE_VERIFICATION_NOTE = "create table if not exists " + VerifNoteTable.TABLE_VERIFICATION_NOTE+ "(" 
					  + VerifNoteTable.COLUMN_VERIFICATION_ID+ " integer primary key autoincrement, "
					  +VerifNoteTable.COLUMN_VERIFICATION_IDNote+ " integer not null," 
		                + VerifNoteTable.COLUMN_VERIFICATION_Date+ " text not null);";
	  
	  /*base de donnee qui enregistre les contact qui sont inscris sur etalk*/	  
	   static final String TABLE_CONTACT = "create table if not exists " + MySQLAttributs.TABLE_NAME + "(" 
			  +MySQLAttributs. COLUMN_ID+ " integer primary key autoincrement, " 
			   +MySQLAttributs.COLUMN_NOM+ " text not null," 
					      + MySQLAttributs.COLUMN_TELEPHONE+ " text not null," 
							 + MySQLAttributs.COLUMN_ID_CONTACT+ " integer not null," 
						    	+ MySQLAttributs.COLUMN_PHOTOS+ " text not null,"  
						    		+ MySQLAttributs.COLUMN_BACKGROUND+ " text not null);";
	  

	
	  
static final String ALTER_TABLE_CMDE="ALTER TABLE "+MySQLAttributs.TABLE_COMMANDES+" ADD COLUMN "+MySQLAttributs.COLUMN_QTE+" integer";
	  
	  public MySQLiteCreate(Context context, String name, CursorFactory factory, int version) {
			super(context,MySQLAttributs.DATABASE_NAME, factory, MySQLAttributs.DATABASE_VERSION);
	  }
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(TABLE_SECTEUR);
		  database.execSQL(TABLE_SOUSCAT);
	    database.execSQL(TABLE_CRITERE);
	    database.execSQL(TABLE_CRITERE_NOTATION);
	    
	    database.execSQL(TABLE_LANGUE);
	    database.execSQL(TABLE_VERIFICATION_NOTE);
	    database.execSQL(TABLE_CONTACT);		
	    
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteCreate.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + MySQLAttributs.TABLE_MESSAGES+";");	 
	    db.execSQL("DROP TABLE IF EXISTS " + MySQLAttributs.TABLE_NAME+";");	 
	  
	    onCreate(db);
	  }
}
