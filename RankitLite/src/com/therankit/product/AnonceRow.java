package com.therankit.product;

import java.sql.Date;

public class AnonceRow {

    public String nomPublieur;
    public String titreArticle;
    public String id_nomPublieur;
    public String description; 
    public String  date;  
    public int pos;  
    public String idCmde;  
    public String prixVente;
	private boolean check;
	private String imageProfil;
	private String imagePublier;
	private String monnaie; 
	private String telephone; 
    
 
    public AnonceRow(  int pos,boolean check,String nomPublieur,String date,String description,String idCmde, String prixVente
    		,String imageProfil, String imagePublier, String titreArticle, String telephone, String id_nomPublieur) {
            super();
            this.nomPublieur = nomPublieur;
            this.description=description;
            this.date = date;
            this.prixVente = prixVente;
            this.id_nomPublieur=id_nomPublieur;
            this.pos = pos;
            this.idCmde = idCmde;
            this.imageProfil = imageProfil;
            this.imagePublier = imagePublier;
            this.titreArticle = titreArticle; 
            this.telephone = telephone; 
    }
     
    
  
    public boolean isCheck() {
            return check;
    }

    public void setCheck(boolean check) {
            this.check = check;
    }

    public String getNomPublieur() {
            return nomPublieur;
    }
    
    public void setNomPublieur(String nomPublieur) {
            this.nomPublieur = nomPublieur;
    }
    
   
    public String getTelephone() {
        return telephone;
}

public void setTelephone(String telephone) {
        this.telephone = telephone;
}



    public String getDescription() {
            return description;
    }
    
    public void setDescription(String description) {
            this.description = description;
    }
    
    
 
    public String getId_nomPublieur() {
            return id_nomPublieur;
    }

public void setId_nomPublieur(String id_nomPublieur) {
        this.id_nomPublieur = id_nomPublieur;
}

public String getIdCmde() {
    return idCmde;
}

public void setIdCmde(String idCmde) {
this.idCmde = idCmde;
}

public void setPrixVente(String prixVente) {
    this.prixVente = prixVente;
}

public String getPrixVente() {
return prixVente;
}
 

public void setDate(String date) {
    this.date = date;
}

public String getDate() {
return date;
}
 

    public int getPos() {
            return pos;
    }

    public void setPos(int pos) {
            this.pos = pos;
    }
    


public void setImageProfil(String imageProfil) {
    this.imageProfil = imageProfil;
}

public String getImageProfil() {
return imageProfil;
}
 
public void setTitreArticle(String titreArticle) {
this.titreArticle = titreArticle;
}

public String getTitreArticle() {
return titreArticle;
}



public void setImagePublier(String imagePublier) {
this.imagePublier = imagePublier;
}

public String getImagePublier() {
return imagePublier;

}

public void setMonnaie(String monnaie) {
this.monnaie = monnaie;
}

public String getMonnaie() {
return monnaie;

}
}

