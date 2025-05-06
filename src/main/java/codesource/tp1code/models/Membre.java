package codesource.tp1code.models;

import java.util.Date;

public class Membre {

    private String nomComplet ;
    private Date dateNaissance ;
    private String sexe ;
    private String ville ;
    private Sport[] sports ;
    private String user ;
    private String motDePasse ;
    private String id ;
    private int compteur=0;

    /* Constructeur */
    public Membre(String nomComplet,Date dateNaissance,String sexe,String ville,Sport[] sports,String user,String motDePasse){
        this.nomComplet = nomComplet;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.ville = ville;
        this.sports = sports;
        this.user = user;
        this.motDePasse = motDePasse;
        compteur++;
        this.id = "PT-"+compteur;
    }


    /* Les mutateurs et les accesseurs */
    public String getNomComplet() {
        return nomComplet;
    }
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public Sport[] getSports() {
        return sports;
    }

    public void setSports(Sport sport,int indiceSport) {
        this.sports[indiceSport] = sport;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getId() {
        return id;
    }
    public int getCompteur(){
        return compteur;
    }


}
