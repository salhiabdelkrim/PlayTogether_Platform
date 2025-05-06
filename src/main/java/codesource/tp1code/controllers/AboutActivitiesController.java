package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutActivitiesController implements Initializable {
    @FXML
    private VBox myStage ;
    @FXML
    private ScrollPane activitiesContent ;
    @FXML
    private GridPane myGrid ;
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

    public void changerfenetre1(){
        changementFenetre("/codesource/tp1code/vues/Scene1.fxml", "Play Together - S'enregistrer/Se connecter");
    }

    public void changerfenetre2(){
        changementFenetre("/codesource/tp1code/vues/Scene3.fxml", "Play Together - Profil");
    }

    public void changerfenetre3(){
        changementFenetre("/codesource/tp1code/vues/Scene2.fxml", "Play Together - Planifier une rencontre");
    }

    public void changerfenetre4(){
        changementFenetre("/codesource/tp1code/vues/Scene5.fxml", "Play Together - Rejoindre une rencontre");
    }

    public void changerfenetre5(){
        changementFenetre("/codesource/tp1code/vues/Scene6.fxml", "Play Together - Supprimer/Modifier une rencontre");
    }
    public void changerfenetre6(){
        changementFenetre("/codesource/tp1code/vues/Scene7.fxml", "Play Together - Nos activités");
    }
    public void changerfenetre7(){
        changementFenetre("/codesource/tp1code/vues/Scene4.fxml", "Play Together - Ajouter un Sport");
    }
    public void changerfenetre8(){
        changementFenetre("/codesource/tp1code/vues/Scene8.fxml", "Play Together - Nos Terrains");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });
        javafx.application.Platform.runLater(() -> {
            SignInSignUpController.afficherAlerte(
                    "Info",
                    "Sachez que l'équipe PlayTogether procède à des mises à jour de cette liste en ajoutant les sports que nos membres rajoutent"
            );
        });
    }
}
