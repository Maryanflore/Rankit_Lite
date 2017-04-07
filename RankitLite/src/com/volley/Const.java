package com.volley;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Const {

	/*****PAREMATRAGE DE BASE************/
	public static final String PREFRENCES_NAME = "myprefrences";
    /*projet id pour la notification*/
	 public static final String PROJECT_ID = "256852383586";
	/*param�tre pour ma machine:local*/
	//public static final String ip="http://192.168.43.94:8080/";
	//public static final String ip="http://192.168.1.231:8080/";
	/*param�tre du serveur en ligne**/
	public static final String ip="http://50.21.180.24:8080/";
	//public static final String nameProjet="Rankit/";
	public static final String nameProjet="RankitWeb/";
	//public static final String nameProjetPHP="http://192.168.43.94/TheRankit/";
	//public static final String nameProjetPHP="http://www.therankit.com/";
	public static final String nameProjetPHP="http://50.21.180.24/therankit/";

	/*****PAREMATRAGE DE BASE************/
	
	
	/*****param�tre de stokage des images**/
	
	public static final String ipUriImageUsers=ip+nameProjet+"profil_rank/";
	public static final String ipUriUploadImage=ip+nameProjet+"mobile_gestion_upload/file/upload";
	public static final String TYPE_PRODUIT = "PROD";
	public static final String TYPE_SERVICE = "SERV";
	public static final String imageNameInscription="inscription_";
    public static final int sizeCompress=80;
    /******param�tre de stokage des images**/
	
	/*******PAREMATRAGE DE GESTION COMPTE(CONNEXION ET INSCRIPTION)*********/	
    public static final String urlContry=ip+nameProjet+"gestion_compte/contry/";
    public static final String ipUriUploadPictureUsers=ip+nameProjet+"gestion_compte/file/upload";
    public static final String ipUriImage=ip+nameProjet+"profil_rank/";
	public static final String TAG_NAME = "randkiteUser_name";
	public static final String TAG_PRENOM = "randkiteUser_surname";
	public static final String TAG_SEXE = "randkiteUser_sexe";
	public static final String TAG_PICTURE = "randkiteUser_picture";
	public static final String TAG_DATE = "randkiteUser_DateNaissance"; 
	public static final String TAG_PHONE_MOBILE = "randkiteUser_phone";
	public static final String TAG_EMAIL = "randkiteUser_email";
	public static final String TAG_GCM = "randkiteUser_gcm";
	public static final String TAG_PAYS_ID = "pays_id";
	public static final String TAG_LOGIN = "connexion_login";
	public static final String TAG_PWD = "connexion_pswd";


	public static final String urlGestionCompte=ip+nameProjet+"gestion_compte";
	public static final String urlIncriptionConnexion=urlGestionCompte+"/compte/";
	public static final String image = "image";
	public static final int count=5;
	public static final String URL_JSON_INSCRIPTION =urlIncriptionConnexion+"doinscription";
	public static final String URL_JSON_VERIF_USERS = urlIncriptionConnexion+"dovalidation";
	public static final String URL_JSON_CONNEXION = urlIncriptionConnexion+"dologin";
	public static final String URL_JSON_UTILISATEUR = urlIncriptionConnexion+"showutilisateurs";
	/*******PAREMATRAGE DE GESTION COMPTE(CONNEXION ET INSCRIPTION)*********/

	/*******PAREMATRAGE DE GESTION BEST ENTREPRISE*********/	
	  public static final String urlBest=ip+nameProjet+"gestion_best/best/";
	 
	  public static final String ipUriLogo=ip+nameProjet+"logo_entreprise/";
	  public static final String ipUriProduitPicture=nameProjetPHP+"entreprises/upload_produit/";
	public static final String ipUriLogo2=nameProjetPHP+"/upload_logos/";
	public static final String ipUriImageAnonce=nameProjetPHP+"entreprises/upload_annonce/";
	/*******PAREMATRAGE DE GESTION BEST ENTREPRISE*********/
	  

		/*******PAREMATRAGE DE GESTION NOTES SERVICES ET PRODCUT*********/
		  public static final String urlNote=ip+nameProjet+"gestion_note/note/";


		/*******PAREMATRAGE DE GESTION DES ANNONCES*********/

	/*******PAREMATRAGE DE GESTION NOTES SERVICES ET PRODCUT*********/
	public static final String urlGestionAnonce=ip+nameProjet+"gestion_note/anonce/";


	/*******PAREMATRAGE DE GESTION DES ANNONCES*********/
	
	/******URI  SUGGESTION **********/	

	public static final String URL_JSON_UTILISATEUR_SUGGESTION=ip+nameProjet+"mobile_suggestion/suggestions/doinsert";
	/**************************************************/
	//public static final String ip_temp="http://74.208.164.209:8080/";
	public static final String ip_temp="http://192.168.43.94:8080/";
	public static final String nameProjet_tmp="e-yamo/";
	public static final String TAG_IDEND = "idEnd";
	public static final String urlGestionPublicationEpeople=ip_temp+nameProjet_tmp+"mobile_gestion_people";
	public static final String urlPublicationEpeople=urlGestionPublicationEpeople+"/people/";
	public static final String URL_JSON_AFFICHAGE_E_PEOPLE_COMMENT=urlPublicationEpeople+"showComment";
	public static final String ipUriUploadImageEnews=ip+"media/image_news/";
	public static final String URL_JSON_AFFICHAGE_E_PEOPLE=urlPublicationEpeople+"showPeople";
	public static final String URL_JSON_PUBLICATION_E_PEOPLE_COMMENT=urlPublicationEpeople+"postComment";

	

}