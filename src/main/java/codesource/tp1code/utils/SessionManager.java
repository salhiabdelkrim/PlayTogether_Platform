package codesource.tp1code.utils;

import codesource.tp1code.models.Membre;

public class SessionManager {
    private static SessionManager instance;
    private Membre membreConnecte;

    private SessionManager() {}  // Constructeur privé pour éviter l'instanciation multiple

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setMembreConnecte(Membre membre) {
        this.membreConnecte = membre;
    }

    public Membre getMembreConnecte() {
        return membreConnecte;
    }

    public void clearSession() {
        membreConnecte = null;
    }


}
