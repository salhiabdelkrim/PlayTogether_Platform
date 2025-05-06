package codesource.tp1code.models;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;

    private List<Sport> sports = new ArrayList<>();
    private List<Membre> membres = new ArrayList<>();
    private List<Rencontre> rencontres = new ArrayList<>();
    private List<Terrain> terrains = new ArrayList<>();

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Méthodes pour ajouter et récupérer
    public void ajouterSport(Sport sport) { if (this.rechercheSport(sport.getName()) == null) sports.add(sport); }
    public List<Sport> getSports() { return sports; }

    public void ajouterMembre(Membre membre) { membres.add(membre); }
    public List<Membre> getMembres() { return membres; }

    public void ajouterRencontre(Rencontre rencontre) { rencontres.add(rencontre); }
    public List<Rencontre> getRencontres() { return rencontres; }
    public void deleterRencontre(Rencontre rencontre) {
        rencontres.remove(rencontre);
    }

    public void ajouterTerrain(Terrain terrain) { terrains.add(terrain); }
    public List<Terrain> getTerrains() { return terrains; }


    //Méthodes pour chercher
    public Membre rechercheMembre(String user, String password) {
        for (Membre membre : this.membres)
            if (membre.getUser().equals(user) && membre.getMotDePasse().equals(password))
                return membre;
        return null;
    }

    public Sport rechercheSport(String name) {
        for (Sport sport : sports) {
            if (name.equals(sport.getName())) {
                return sport;
            }
        }
        return null;
    }

    //méthode qui retourne la liste des rencontres organisés par le joueur en paramètre
    public ArrayList<Rencontre> rechercheRencontres(Membre joueur) {
        ArrayList<Rencontre> rencontres = new ArrayList<>();
        for (Rencontre rencontre : this.rencontres) {
            if (joueur.equals(rencontre.getOrganisateur())) {
                rencontres.add(rencontre);
            }
        }
        return rencontres;
    }

    // methode qui retourne toutes les rencontres d'un joueur ( Organisateur ou non )
    public ArrayList<Rencontre> getRencontresDuMembre(Membre membre) {
        ArrayList<Rencontre> resultats = new ArrayList<>();
        for (Rencontre rencontre : this.rencontres) {
            if (rencontre.getListeJoueurs().contains(membre)) {
                resultats.add(rencontre);
            }
        }
        return resultats;
    }

}
