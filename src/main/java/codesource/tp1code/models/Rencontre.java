package codesource.tp1code.models;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public class Rencontre {

    private ArrayList<Membre> listeJoueurs;
    private Sport sport;
    private String adresse ;
    private int nombreJoueurs ;
    private float frais ;
    private Date dateDebut;
    private String heureDebut;

    // Constructeur :
    public Rencontre(Membre organisateur,Sport sport, String adresse, int nombreJoueurs, float frais, Date dateDebut, String heureDebut) {
        this.sport = sport;
        this.adresse = adresse;
        this.nombreJoueurs = nombreJoueurs;
        this.frais = frais;
        this.dateDebut = dateDebut;
        this.heureDebut = heureDebut;
        this.listeJoueurs = new ArrayList<Membre>();
        this.listeJoueurs.add(organisateur);
    }


    //accesseurs & mutateurs
    public Sport getSport() {
        return sport;
    }
    public void setSport(Sport sport) {
        this.sport = sport;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public int getNombreJoueurs() {
        return nombreJoueurs;
    }
    public void setNombreJoueurs(int nombreJoueurs) {
        this.nombreJoueurs = nombreJoueurs;
    }
    public float getFrais() {
        return frais;
    }
    public void setFrais(float frais) {
        this.frais = frais;
    }
    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public String getHeureDebut() {
        return heureDebut;
    }
    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }
    public Membre getOrganisateur() {
        return listeJoueurs.getFirst();
    }
    public ArrayList<Membre> getListeJoueurs() {
        return listeJoueurs;
    }
    public boolean rejoindreRencontre(Membre membre){
        if ( listeJoueurs.size() >= nombreJoueurs || rechercheJoueur(membre) ){
            return false ;
        }
        else listeJoueurs.add(membre);
        return true;
    }
    private boolean rechercheJoueur(Membre membre){
        for ( Membre m : listeJoueurs ){
            if (m.equals(membre)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        // Conversion de Date en LocalDate
        LocalDate localDate = this.dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Définition du formatteur
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

        return this.sport.getName()+" à "+this.adresse+" vers "+this.heureDebut+" "+localDate.format(formatter);
    }




}
