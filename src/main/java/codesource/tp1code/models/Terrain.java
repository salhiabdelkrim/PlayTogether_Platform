package codesource.tp1code.models;

public class Terrain {

    private String nom;
    private String adresse;
    private String  sport;



    public Terrain(String nom, String adresse, String sport) {
        this.nom = nom;
        this.adresse = adresse;
        this.sport=sport;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getSport() {
        return sport;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }



}
