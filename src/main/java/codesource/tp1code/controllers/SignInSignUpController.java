package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import codesource.tp1code.models.DataStore;
import codesource.tp1code.models.Membre;
import codesource.tp1code.models.Sport;
import codesource.tp1code.models.Terrain;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SignInSignUpController implements Initializable {




    /** Déclaration des controles */
    @FXML
    private VBox myStage;
    @FXML
    private TextField nomComplet;
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private RadioButton masculin;
    @FXML
    private RadioButton feminin;
    @FXML
    private TextField ville;
    @FXML
    private ChoiceBox<String> sport1;
    @FXML
    private ChoiceBox<String> sport2;
    @FXML
    private ChoiceBox<String> sport3;
    @FXML
    private TextField user;
    @FXML
    private PasswordField motDePasse;
    @FXML
    private Button enregistrer;
    @FXML
    private Button reinitialiser;
    private ToggleGroup sexeGroup;
    @FXML
    public void initialize() {
        sexeGroup = new ToggleGroup();
        masculin.setToggleGroup(sexeGroup);
        feminin.setToggleGroup(sexeGroup);


    }
    @FXML
    private TextField username ;
    @FXML
    private PasswordField password ;
    @FXML
    private Button connexion ;
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


    /** La liste des membres et des sports*/
    private DataStore data = DataStore.getInstance();


    /** Les méthodes de gestion des événements des bouttons "Enregistrer" et "Rénitialiser" */
    public void enregistrer() {
        if (creerMembre()){
            afficherAlerte("Enregistrement terminé","Bienvenue dans la famille Play Together");
            reinitialiser();
        }
    }

    /** methode pour réinitialiser touxs les champs */
    public void reinitialiser() {
        nomComplet.setText("");
        dateNaissance.setValue(null);
        masculin.setSelected(false);
        feminin.setSelected(false);
        ville.setText("");
        sport1.setValue(null);
        sport2.setValue(null);
        sport3.setValue(null);
        user.setText("");
        motDePasse.setText("");
    }

    /** La méthode de gestion de connexion */
    public void connexion(){
        if (!verifierChampsConnexion()){
            afficherAlerte("Erreur de données", "Tous les champs sont obligatoires.");
        }else {
            Membre membre = data.rechercheMembre(username.getText(), password.getText());
            if (membre!= null){
                SessionManager.getInstance().setMembreConnecte(membre); // Stockage de l'utilisateur connecté
                try {
                    // Charger la deuxième vue (MessageBienvenue.fxml)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/codesource/tp1code/vues/Scene3.fxml"));
                    Parent root = loader.load();
                    // Obtenir la fenêtre actuelle
                    Stage stage = (Stage) connexion.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
                    // Afficher la nouvelle scène
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                afficherAlerte("Erreur de connexion", "L'un des champs ou les deux ne sont pas valides");
            }
        }
    }


    /** crée un membre*/
    private boolean creerMembre() {
        if (!verifierChampsAdhesion()) {
            afficherAlerte("Erreur de données", "Tous les champs sont obligatoires.");
            return false;
        }


        // Récupération des sports renseignés
        Sport[] sports = {
                data.rechercheSport(getTextOrEmpty(sport1)),
                data.rechercheSport(getTextOrEmpty(sport2)),
                data.rechercheSport(getTextOrEmpty(sport3))
        };

        // Conversion de la date
        LocalDate selectedDate = dateNaissance.getValue();
        Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Création du membre
        Membre membre = new Membre(
                nomComplet.getText(),
                date,
                masculin.isSelected() ? masculin.getText() : feminin.getText(),
                ville.getText(),
                sports,
                user.getText(),
                motDePasse.getText()
        );

        data.ajouterMembre(membre);
        return true;
    }



    /** Vérifie si les champs sont valides */
    private boolean verifierChampsAdhesion() {
        return !(nomComplet.getText().isEmpty() ||
                dateNaissance.getValue() == null ||
                (!masculin.isSelected() && !feminin.isSelected()) ||
                ville.getText().isEmpty() ||
                sport1.getValue() == null ||
                user.getText().isEmpty() ||
                motDePasse.getText().isEmpty());
    }

    /** verifier si les champs de connexion ne sont pas vides */
    private boolean verifierChampsConnexion() {
        return !(username.getText().isEmpty() ||
                password.getText().isEmpty());
    }

    /** Affiche une alerte */
    public static void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Récupère le texte d'un champ ou une chaîne vide si null */
    private String getTextOrEmpty(ChoiceBox<String> field) {
        return (field != null && field.getValue() != null) ? field.getValue() : "";
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enregistrer.setTooltip(new Tooltip("Cliquez pour devenir membre."));
        reinitialiser.setTooltip(new Tooltip("Cliquez pour réinitialiser le formulaire."));
        connexion.setTooltip(new Tooltip("Cliquez pour se connecter."));
// Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });
        //stockage des données ( liste des sports et Terrains )
        data.ajouterSport(new Sport("Football", "Le football est un sport collectif opposant deux équipes de 11 joueurs. L'objectif est de marquer des buts en envoyant le ballon dans le but adverse, principalement avec les pieds. Il se joue sur un terrain gazonné et suit des règles strictes encadrées par un arbitre.", 18));
        data.ajouterSport(new Sport("Baseball", "Le baseball est un sport collectif où deux équipes s’affrontent en alternant attaque et défense. Le lanceur envoie la balle au batteur qui tente de la frapper et courir les bases. L’équipe défensive tente d’éliminer les coureurs avant qu’ils marquent des points.", 18));
        data.ajouterSport(new Sport("Footing", "Le footing est une course à pied pratiquée à allure modérée pour le loisir ou l’entraînement physique. Il améliore l’endurance et la condition physique. Courir régulièrement permet de renforcer les muscles, le cœur et de réduire le stress. Il se pratique seul ou en groupe.", 30));
        data.ajouterSport( new Sport("Kayak", "Le kayak est un sport nautique où l’on pagaie en position assise dans une embarcation étroite. Il se pratique sur rivières, lacs ou en mer, en loisir ou en compétition. Les disciplines incluent le slalom, la descente et la course en eau vive.", 15));
        data.ajouterSport(new Sport("Hockey", "Le hockey est un sport rapide joué sur glace ou sur gazon. Deux équipes s'affrontent pour marquer des buts en frappant un palet (sur glace) ou une balle (sur gazon) avec une crosse. Il exige vitesse, agilité et coordination entre les joueurs pour gagner.", 18));
        data.ajouterSport(new Sport("Tennis", "Le tennis est un sport de raquette opposant deux ou quatre joueurs. Ils frappent une balle au-dessus d’un filet pour marquer des points en la renvoyant dans le camp adverse. Il se pratique sur gazon, terre battue ou surface dure, en simple ou en double.", 4));

        ArrayList<String> items=new ArrayList<>() ;
        for (Sport sport : data.getSports()) { items.add(sport.getName());}
        // Ajouter des éléments à la ChoiceBox
        ObservableList<String> sports = FXCollections.observableArrayList(items);

        sport1.setItems(sports);
        sport2.setItems(sports);
        sport3.setItems(sports);
        data.ajouterTerrain(new Terrain("Université de Québec à Trois-Rivières","3351, boulevard des forges","Football"));
        data.ajouterTerrain(new Terrain("Parc Lambert","2000, 6e Rue","Football"));
        data.ajouterTerrain(new Terrain("Ecole secondaire des Pionniers","Pavillon Saint-Ursule 1725, boulevard du Carmel","Football"));
        data.ajouterTerrain(new Terrain("Parc Jaques-Buteux","Rue Maurice-L-Duplessis","Football"));
        data.ajouterTerrain(new Terrain("Parc du Père-Breton","301, rue du Père Breton","Football"));
        data.ajouterTerrain(new Terrain("Parc Roger-Guilbault","1301, rue Boisclair","Football"));
        data.ajouterTerrain(new Terrain("Parc du Moulin","Rue du Moulin des Jésuites","Tennis"));
        data.ajouterTerrain(new Terrain("Parc Masse","220, Chemin Masse","Tennis"));
        data.ajouterTerrain(new Terrain("Parc Martin-Bergeron","220, rue Party","Tennis"));
        data.ajouterTerrain(new Terrain("Parc des Plaines","3871, rue des Plaines","Tennis"));
        data.ajouterTerrain(new Terrain("Parc Quirion","6330, rue Quirion","Tennis"));
        data.ajouterTerrain(new Terrain("Parc de l'école Sainte-Catherine-de-Sienne","182, rue de Sienne","Basketball"));
        data.ajouterTerrain(new Terrain("Parc Pie-XII","","Basketball"));
        data.ajouterTerrain(new Terrain("Parc Lemire","840, rue Saint Paul","Basketball"));
        data.ajouterTerrain(new Terrain("Parc Claude-Champoux","50, rue Wilfrid Laurier","Basketball"));
        data.ajouterTerrain(new Terrain("Parc des Ormeaux","300, rue Chapleau","Basketball"));
        data.ajouterTerrain(new Terrain("Parc Jean-Perron","670, rue Guilbert","Baseball"));
        data.ajouterTerrain(new Terrain("Parc Saint-Jean-de-Brébeuf","1315, rue de la Terrière","Baseball"));
        data.ajouterTerrain(new Terrain("Parc Rosemont","5225, rue de Courcelette","Baseball"));
        data.ajouterTerrain(new Terrain("Parc Sainte-Marguerite","2000, avenue des Coopérants","Baseball"));

        // Ajouter un événement pour capturer la sélection
        sport1.setOnAction(event -> {
            String selectedSport = sport1.getValue();
        });
    }
}