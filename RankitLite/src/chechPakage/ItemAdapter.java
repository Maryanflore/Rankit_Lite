package chechPakage;

/**
 * Created by Martin Pendja on 29/06/2016.
 */
public class ItemAdapter {

    private  int pos;
    private  int idEntreprise;
     private String nameEntreprise;
     private int idSecteur;
     private String logo;


   public ItemAdapter(int pos, int idEntreprise, String nameEntreprise, int idSecteur, String logo)
    {
        this.pos = pos;
        this.idEntreprise = idEntreprise;
        this.nameEntreprise = nameEntreprise;
        this.idSecteur = idSecteur;
        this.logo = logo;

    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getNameEntreprise() {
        return nameEntreprise;
    }

    public void setNameEntreprise(String nameEntreprise) {
        this.nameEntreprise = nameEntreprise;
    }

    public int getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(int idSecteur) {
        this.idSecteur = idSecteur;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
