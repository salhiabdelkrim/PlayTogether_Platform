package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import codesource.tp1code.models.DataStore;
import codesource.tp1code.models.Membre;
import codesource.tp1code.models.Rencontre;
import codesource.tp1code.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class ProfilController implements Initializable {

    /** implementation du menu */
    @FXML
    private MenuBar menuApp ;
    @FXML
    private MenuItem signInSignUp;
    @FXML
    private MenuItem profil ;
    @FXML
    private MenuItem createMatch ;
    @FXML
    private MenuItem joinMatch ;
    @FXML
    private MenuItem matchEditing ;
    @FXML
    private MenuItem aboutActivities ;
    @FXML
    private MenuItem ajouterActivities ;
    @FXML
    private MenuItem stadiumsMap ;


    private void changementFenetre(String chemin, String titreFenetre){
        try {
            // Charger la deuxième vue (MessageBienvenue.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource(chemin));
            Parent root = loader.load();
            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) menuApp.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
            stage.setTitle(titreFenetre);
            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changerfenetre1(){changementFenetre("/codesource/tp1code/vues/Scene1.fxml", "Play Together - S'enregistrer/Se connecter");}
    public void changerfenetre2(){changementFenetre("/codesource/tp1code/vues/Scene3.fxml", "Play Together - Profil");}
    public void changerfenetre3(){changementFenetre("/codesource/tp1code/vues/Scene2.fxml", "Play Together - Planifier une rencontre");}
    public void changerfenetre4(){changementFenetre("/codesource/tp1code/vues/Scene5.fxml", "Play Together - Rejoindre une rencontre");}
    public void changerfenetre5(){changementFenetre("/codesource/tp1code/vues/Scene6.fxml", "Play Together - Supprimer/Modifier une rencontre");}
    public void changerfenetre6(){changementFenetre("/codesource/tp1code/vues/Scene7.fxml", "Play Together - Nos activités");}
    public void changerfenetre7(){changementFenetre("/codesource/tp1code/vues/Scene4.fxml", "Play Together - Ajouter un Sport");}
    public void changerfenetre8(){changementFenetre("/codesource/tp1code/vues/Scene8.fxml", "Play Together - Nos terrains");}

    @FXML
    private VBox myStage ;
    @FXML
    private AnchorPane information ;

    @FXML
    private VBox changeSpace ;

    @FXML
    private VBox matchsList ;

    @FXML
    private Label nomComplet ;
    @FXML
    private Button editNomComplet;
    @FXML
    private Label dateNaissance ;
    @FXML
    private Button editDateNaissance;
    @FXML
    private Label sexe ;
    @FXML
    private Button editSexe;
    @FXML
    private Label ville ;
    @FXML
    private Button editVille;
    @FXML
    private Label sport1 ;
    @FXML
    private Button editSport1;
    @FXML
    private Label sport2 ;
    @FXML
    private Button editSport2;
    @FXML
    private Label sport3 ;
    @FXML
    private Button editSport3;
    @FXML
    private ListView<String> rencontreList;
    @FXML
    private ComboBox<Double> taille ;

    public void changerTaillePolice(double nouvelleTaille) {
        changerTaillePoliceRecursive(myStage, nouvelleTaille);
    }

    /** Méthode pour changer la taille du texte de la page */
    private void changerTaillePoliceRecursive(Node node, double facteur) {
        // Vérifier si le node a un font
        if (node instanceof Labeled labeledNode) { // Vérifie si c'est un composant qui a un texte (par exemple un Button, Label)
            Font anciennePolice = labeledNode.getFont(); // Récupérer la police actuelle
            if (anciennePolice != null) {
                double tailleActuelle = anciennePolice.getSize(); // Taille actuelle de la police
                double nouvelleTaille = tailleActuelle * facteur; // Multiplier par le facteur
                labeledNode.setFont(Font.font(anciennePolice.getFamily(), nouvelleTaille)); // Appliquer la nouvelle taille
            }
        }

        // Si le node est un Parent, explorer ses enfants
        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                changerTaillePoliceRecursive(child, facteur); // Appeler récursivement sur les enfants
            }
        }
    }


    private DataStore data = DataStore.getInstance();


    /**Initialisation des controls */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });

        Membre membre = SessionManager.getInstance().getMembreConnecte();
        if (membre != null) {
            nomComplet.setText(membre.getNomComplet());
            // Conversion de Date en LocalDate
            LocalDate localDate = membre.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Définition du formatteur
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

            // Affichage de la date formatée
            dateNaissance.setText("né le : "+localDate.format(formatter));

            sexe.setText("je suis un(e) "+membre.getSexe());
            ville.setText("j'habite à "+membre.getVille());

            // Vérifier les sports pour éviter les erreurs si null
            sport1.setText(membre.getSports().length > 0 && membre.getSports()[0] != null ? "Mon sport préféré est le "+membre.getSports()[0].getName() : "Aucun");
            sport2.setText(membre.getSports().length > 1 && membre.getSports()[1] != null ? "sinon j'aime aussi le "+membre.getSports()[1].getName() : "");
            sport3.setText(membre.getSports().length > 2 && membre.getSports()[2] != null ? "et le "+membre.getSports()[2].getName() : "");
        }
        // Créer une liste observable avec des éléments dynamiques pour afficher les matchs organisés par le membre connecté
        ArrayList<String> items=new ArrayList<>() ;
        for (Rencontre rencontre : data.getRencontresDuMembre(membre)) {
            items.add(rencontre.toString());
        }
        ObservableList<String> rencontresListe = FXCollections.observableArrayList(items);
        rencontreList.setItems(rencontresListe);
    }

    /**
     * Méthode Générique pour afficher une boîte de dialogue  */
    private String afficherBoiteDeDialogue(String titre, String message, String valeurActuelle) {
        TextInputDialog dialog = new TextInputDialog(valeurActuelle);
        dialog.setTitle(titre);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        return dialog.showAndWait().orElse(null);
    }


    /**
     * Méthodes pour la modification des informations de l'utilisateur */

    @FXML
    private void modifierNomComplet() {
        String nouveauNom = afficherBoiteDeDialogue("Modifier le nom", "Entrez votre nouveau nom :", nomComplet.getText());
        if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
            nomComplet.setText(nouveauNom);
            SessionManager.getInstance().getMembreConnecte().setNomComplet(nouveauNom);
        }
    }

    @FXML
    private void modifierDateNaissance() {
        String nouvelleDate = afficherBoiteDeDialogue("Modifier la date de naissance", "Entrez votre nouvelle date (format : dd/MM/yyyy) :", dateNaissance.getText());
        if (nouvelleDate != null && !nouvelleDate.trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(nouvelleDate, formatter);
                dateNaissance.setText("né le : " + localDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH)));
                SessionManager.getInstance().getMembreConnecte().setDateNaissance(java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (Exception e) {
                SignInSignUpController.afficherAlerte("Erreur", "Format de date invalide. Utilisez dd/MM/yyyy.");
            }
        }
    }

    @FXML
    private void modifierSexe() {
        String nouveauSexe = afficherBoiteDeDialogue("Modifier le sexe", "Entrez votre sexe (Masculin / Féminin) :", sexe.getText().replace("je suis un(e) ", ""));
        if (nouveauSexe != null && (nouveauSexe.equalsIgnoreCase("Masculin") || nouveauSexe.equalsIgnoreCase("Féminin"))) {
            sexe.setText("je suis un(e) " + nouveauSexe);
            SessionManager.getInstance().getMembreConnecte().setSexe(nouveauSexe);
        } else {
            SignInSignUpController.afficherAlerte("Erreur", "Veuillez entrer 'Masculin' ou 'Féminin'.");
        }
    }

    @FXML
    private void modifierVille() {
        String nouvelleVille = afficherBoiteDeDialogue("Modifier la ville", "Entrez votre nouvelle ville :", ville.getText().replace("j'habite à ", ""));
        if (nouvelleVille != null && !nouvelleVille.trim().isEmpty()) {
            ville.setText("j'habite à " + nouvelleVille);
            SessionManager.getInstance().getMembreConnecte().setVille(nouvelleVille);
        }
    }

    @FXML
    private void modifierSport1() {
        String nouveauSport = afficherBoiteDeDialogue("Modifier le sport préféré", "Entrez votre sport préféré :", sport1.getText().replace("Mon sport préféré est le ", ""));
        if (nouveauSport != null && !nouveauSport.trim().isEmpty()) {
            sport1.setText("Mon sport préféré est le " + nouveauSport);
            SessionManager.getInstance().getMembreConnecte().getSports()[0].setName(nouveauSport);
        }
    }

    @FXML
    private void modifierSport2() {
        String nouveauSport = afficherBoiteDeDialogue("Modifier le second sport", "Entrez un second sport :", sport2.getText().replace("sinon j'aime aussi le ", ""));
        if (nouveauSport != null && !nouveauSport.trim().isEmpty()) {
            sport2.setText("sinon j'aime aussi le " + nouveauSport);
            SessionManager.getInstance().getMembreConnecte().getSports()[1].setName(nouveauSport);
        }
    }

    @FXML
    private void modifierSport3() {
        String nouveauSport = afficherBoiteDeDialogue("Modifier le troisième sport", "Entrez un troisième sport :", sport3.getText().replace("et le ", ""));
        if (nouveauSport != null && !nouveauSport.trim().isEmpty()) {
            sport3.setText("et le " + nouveauSport);
            SessionManager.getInstance().getMembreConnecte().getSports()[2].setName(nouveauSport);
        }
    }


}
