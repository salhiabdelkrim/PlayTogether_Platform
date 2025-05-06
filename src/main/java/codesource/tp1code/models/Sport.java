package codesource.tp1code.models;

public class Sport {

    private String name ;
    private String description ;
    private int nombreJoueur ;

    public Sport(String name, String description, int nombreJoueur) {
        this.name = name;
        this.description = description;
        this.nombreJoueur = nombreJoueur;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNombreJoueur() {
        return nombreJoueur;
    }


}
