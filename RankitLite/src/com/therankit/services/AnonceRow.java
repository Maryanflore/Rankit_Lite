package com.therankit.services;

import java.sql.Date;

public class AnonceRow {

    public String nomPublieur;
    public String titreArticle;
    public String id_nomPublieur;
    public String description; 
    public String  date;  
    public int pos;  
    public String nbre_comment;
	private boolean check;
	private String imageProfil;
	private String imagePublier;
	private int nbreJaimePas;
    private int nbreJaime;
    private int nbreReaction;
    private int idAnonce;
    private  Boolean voteAime;
    private  Boolean voteReaction;
    private  Boolean etudeAnonceExist;
    
 
    public AnonceRow(  int pos,boolean check,String nomPublieur,String date,String description,String nbre_comment, String imageProfil, String imagePublier,
                       String titreArticle,  String id_nomPublieur,int nbreJaimePas,int nbreJaime,int nbreReaction,int idAnonce, Boolean voteAime,Boolean voteReaction,  Boolean etudeAnonceExist) {
            super();
             this.check=check;
            this.nomPublieur = nomPublieur;
            this.description=description;
            this.date = date;
            this.id_nomPublieur=id_nomPublieur;
            this.pos = pos;
            this.nbre_comment = nbre_comment;
            this.imageProfil = imageProfil;
            this.imagePublier = imagePublier;
            this.titreArticle = titreArticle;
             this.nbreJaimePas=nbreJaimePas;
        this.nbreJaime=nbreJaime;
        this.nbreReaction=nbreReaction;
        this.idAnonce=idAnonce;
        this.voteAime=voteAime;
        this.voteReaction=voteReaction;
        this.etudeAnonceExist=etudeAnonceExist;
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
    
   
    public int getNbreJaimePas() {
        return nbreJaimePas;
}

public void setNbreJaimePas(int nbreJaimePas) {
        this.nbreJaimePas = nbreJaimePas;
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

public String getNbre_comment() {
    return nbre_comment;
}

public void setNbre_comment(String nbre_comment) {
this.nbre_comment = nbre_comment;
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

    public int getNbreJaime() {
        return nbreJaime;
    }

    public void setNbreJaime(int nbreJaime) {
        this.nbreJaime = nbreJaime;
    }

    public int getNbreReaction() {
        return nbreReaction;
    }

    public void setNbreReaction(int nbreReaction) {
        this.nbreReaction = nbreReaction;
    }

    public int getIdAnonce() {
        return idAnonce;
    }

    public void setIdAnonce(int idAnonce) {
        this.idAnonce = idAnonce;
    }

    public Boolean getVoteAime() {
        return voteAime;
    }

    public void setVoteAime(Boolean voteAime) {
        this.voteAime = voteAime;
    }

    public Boolean getVoteReaction() {
        return voteReaction;
    }

    public Boolean getEtudeAnonceExist() {
        return etudeAnonceExist;
    }

    public void setEtudeAnonceExist(Boolean etudeAnonceExist) {
        this.etudeAnonceExist = etudeAnonceExist;
    }

    public void setVoteReaction(Boolean voteReaction) {
        this.voteReaction = voteReaction;
    }

}

