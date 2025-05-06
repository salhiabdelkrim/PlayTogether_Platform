package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import codesource.tp1code.models.DataStore;
import codesource.tp1code.models.Membre;
import codesource.tp1code.models.Rencontre;
import codesource.tp1code.models.Sport;
import codesource.tp1code.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.Tooltip;

public class CreateMatchController {
    /** implementation du menu */
    @FXML
    MenuBar menuApp;
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
    public void changerfenetre8(){changementFenetre("/codesource/tp1code/vues/Scene8.fxml", "Play Together - Nos Terrains");}


    @FXML
    private VBox myStage;
    @FXML
    private AnchorPane header;
    @FXML
    private StackPane footer;
    @FXML
    private VBox leftPart;
    @FXML
    private SplitPane mySplitPane;
    @FXML
    private VBox middlePart;
    @FXML
    private ListView<String> matchList;
    @FXML
    private ComboBox<String> sports ;
    @FXML
    private TextField adresse ;
    @FXML
    private Spinner<Integer> nombreJoueurs ;
    @FXML
    private TextField frais ;
    @FXML
    private DatePicker dateRencontre ;
    @FXML
    private ComboBox<String> heureRencontre;
    @FXML
    private ComboBox<String> minuteRencontre;
    @FXML
    private Button enregistrer ;
    @FXML
    private Button reinitialiser ;
    @FXML
    private ComboBox<Double> taille;
    @FXML
    private Label erreur ;
    private DataStore data = DataStore.getInstance();

    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
    @FXML
    public void initialize() {
        // Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });
        // Créer une liste observable avec des éléments dynamiques
        ArrayList<String> items=new ArrayList<>() ;
        for (Rencontre rencontre : data.getRencontres()) {
            items.add(rencontre.toString());
        }
        ObservableList<String> rencontresListe = FXCollections.observableArrayList(items);

        //remplissage du comboBox pour les choix d'activités
        ArrayList<String> items1=new ArrayList<>() ;
        for (Sport sport : data.getSports()) { items1.add(sport.getName());}
        // Ajouter des éléments à la ChoiceBox
        ObservableList<String> sportsNames = FXCollections.observableArrayList(items1);
        sports.setItems(sportsNames);
        // Ajouter la liste observable à la ListView
        matchList.setItems(rencontresListe);

        //exiger des valeurs numériques à l'utilisateur pour les frais par joueur
        frais.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                frais.setText(oldValue); // on remet l'ancienne valeur
            }
        });
        enregistrer.setTooltip(new Tooltip("Cliquez pour enregistrer la rencontre."));
        reinitialiser.setTooltip(new Tooltip("Cliquez pour réinitialiser le formulaire."));
        //initialisation des champs heure et minute de rencontre
        for (int h = 0; h < 24; h++) {
            heureRencontre.getItems().add(String.format("%02d", h));
        }
        for (int m = 0; m < 60; m += 5) { // toutes les 5 minutes, par exemple
            minuteRencontre.getItems().add(String.format("%02d", m));
        }

        // initialisation du spinner nombre de joueurs
        valueFactory.setValue(1); // valeur initiale
        nombreJoueurs.setValueFactory(valueFactory);
    }



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

    /** Méthode pour enregistrer une nouvelle rencontre */
    public void enregistrer() {
        if (creerRencontre()){
            SignInSignUpController.afficherAlerte("Enregistrement terminé","Votre rencontre est bien planifiée ");
            reinitialiser();
        }
    }


    /** Méthode pour réinitialiser tous les champs*/
    public void reinitialiser() {
        sports.setValue(null);
        adresse.setText("");
        valueFactory.setValue(1); // valeur initiale
        nombreJoueurs.setValueFactory(valueFactory);
        frais.setText("");
        dateRencontre.setValue(null);
        heureRencontre.setValue(null);
        minuteRencontre.setValue(null);
        erreur.setText("");
    }
    /** Méthode pour ajouter une rencontre*/
    private boolean creerRencontre() {
        if (!verifierChampsCreation()) {
            erreur.setText("Veuillez remplir tous les champs ");
            return false;
        }


        // Récupération du sport renseigné
        Sport sport = data.rechercheSport(getText(sports));

        // Conversion de la date
        LocalDate selectedDate = dateRencontre.getValue();
        Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Création de la rencontre
        Rencontre rencontre = new Rencontre(
                SessionManager.getInstance().getMembreConnecte(),
                sport,
                adresse.getText(),
                nombreJoueurs.getValue(),
                Float.parseFloat(frais.getText()),
                date,
                heureRencontre.getValue()+":"+minuteRencontre.getValue()
        );

        data.ajouterRencontre(rencontre);
        return true;


    }



    /**méthode qui vérifie si tout les chaps sans valides avant la création d'une rencontre*/
    private boolean verifierChampsCreation(){

        return !(sports.getValue() == null ||
                adresse.getText().isEmpty()||
                nombreJoueurs.getValue() == null ||
                nombreJoueurs.getValue() == 0 ||
                frais.getText().isEmpty() ||
                dateRencontre.getValue() == null ||
                heureRencontre.getValue() == null ||
                minuteRencontre.getValue() == null );
    }

    /** Récupère le texte d'un champ ou une chaîne vide si null */
    private String getText(ComboBox<String> field) {
        return (field != null && field.getValue() != null) ? field.getValue() : "";
    }




}
