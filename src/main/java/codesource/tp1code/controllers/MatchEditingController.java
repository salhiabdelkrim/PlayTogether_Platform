package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import codesource.tp1code.models.DataStore;
import codesource.tp1code.models.Membre;
import codesource.tp1code.models.Rencontre;
import codesource.tp1code.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MatchEditingController {
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
    @FXML
    private VBox myStage;
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

    public void changerfenetre1(){changementFenetre("/codesource/tp1code/vues/Scene1.fxml", "Play Together - S'enregistrer/Se connecter");}
    public void changerfenetre2(){changementFenetre("/codesource/tp1code/vues/Scene3.fxml", "Play Together - Profil");}
    public void changerfenetre3(){changementFenetre("/codesource/tp1code/vues/Scene2.fxml", "Play Together - Planifier une rencontre");}
    public void changerfenetre4(){changementFenetre("/codesource/tp1code/vues/Scene5.fxml", "Play Together - Rejoindre une rencontre");}
    public void changerfenetre5(){changementFenetre("/codesource/tp1code/vues/Scene6.fxml", "Play Together - Supprimer/Modifier une rencontre");}
    public void changerfenetre6(){changementFenetre("/codesource/tp1code/vues/Scene7.fxml", "Play Together - Nos activités");}
    public void changerfenetre7(){changementFenetre("/codesource/tp1code/vues/Scene4.fxml", "Play Together - Ajouter un Sport");}
    public void changerfenetre8(){changementFenetre("/codesource/tp1code/vues/Scene8.fxml", "Play Together - Nos Terrains");}

    @FXML
    private VBox matchEditingList;

    @FXML
    private VBox parent;

    private DataStore data = DataStore.getInstance();

    @FXML
    private void initialize() {
        // Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });
        List<Rencontre> matches = data.getRencontres();
        Membre membre = SessionManager.getInstance().getMembreConnecte();
        for (Rencontre match : data.rechercheRencontres(membre)) {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getStyleClass().add("vbox");

            // Label du sport
            Label sportLabel = new Label(match.getSport().getName());
            sportLabel.setFont(Font.font("Cambria Bold", 12));
            HBox.setMargin(sportLabel, new Insets(10, 0, 0, 10));

            // Nb joueurs
            VBox nbJoueursBox = createInfoBox("Nb de joueurs : " + match.getNombreJoueurs(), "nbJoueurs", match);

            // Terrain
            VBox terrainBox = createInfoBox("Terrain : " + match.getAdresse(), "adresse", match);

            // Frais
            VBox fraisBox = createInfoBox("frais par joueur : " + String.format("%.2f$", match.getFrais()), "frais", match);

            // Conversion de Date en LocalDate
            LocalDate localDate = match.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Définition du formatteur
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
            // Date et heure
            VBox dateBox = createInfoBox(localDate.format(formatter) + " à " + match.getHeureDebut(), "date", match);

            // Bouton supprimer
            Button supprimerBtn = new Button("Supprimer");
            supprimerBtn.getStyleClass().add("deleteButton");
            supprimerBtn.setFont(Font.font("Cambria Bold", 12));
            supprimerBtn.setOnAction(e -> {
                matchEditingList.getChildren().remove(hBox);
                // Suppression dans la classe DataStore
                data.deleterRencontre(match);
            });

            hBox.getChildren().addAll(sportLabel, nbJoueursBox, terrainBox, fraisBox, dateBox, supprimerBtn);
            matchEditingList.getChildren().add(hBox);
        }
    }

    private VBox createInfoBox(String labelText, String typeChamp, Rencontre match) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);

        Button modifierBtn = new Button("Modifier");
        modifierBtn.setOnAction(e -> {
            switch (typeChamp) {
                case "nbJoueurs" -> {
                    TextInputDialog dialog = new TextInputDialog(String.valueOf(match.getNombreJoueurs()));
                    dialog.setHeaderText("Modifier le nombre de joueurs");
                    dialog.showAndWait().ifPresent(result -> {
                        try {
                            int nb = Integer.parseInt(result);
                            match.setNombreJoueurs(nb);
                            label.setText("Nb de joueurs : " + nb);
                        } catch (NumberFormatException ex) {
                            showAlert("Entrée invalide", "Veuillez entrer un nombre entier.");
                        }
                    });
                }
                case "adresse" -> {
                    TextInputDialog dialog = new TextInputDialog(match.getAdresse());
                    dialog.setHeaderText("Modifier l'adresse");
                    dialog.showAndWait().ifPresent(result -> {
                        match.setAdresse(result);
                        label.setText("Terrain : " + result);
                    });
                }
                case "frais" -> {
                    TextInputDialog dialog = new TextInputDialog(String.valueOf(match.getFrais()));
                    dialog.setHeaderText("Modifier les frais par joueur");
                    dialog.showAndWait().ifPresent(result -> {
                        try {
                            float frais = Float.parseFloat(result);
                            match.setFrais(frais);
                            label.setText("frais par joueur : " + String.format("%.2f$", frais));
                        } catch (NumberFormatException ex) {
                            showAlert("Entrée invalide", "Veuillez entrer un nombre décimal.");
                        }
                    });
                }
                case "date" -> {
                    TextInputDialog dialog = new TextInputDialog(match.getHeureDebut());
                    dialog.setHeaderText("Modifier l'heure de début");
                    dialog.setContentText("Format suggéré : 18h00");
                    dialog.showAndWait().ifPresent(result -> {
                        match.setHeureDebut(result);
                        String formattedDate = match.getDateDebut().toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH));
                        label.setText(formattedDate + " à " + result);
                    });
                }
            }
        });

        box.getChildren().addAll(label, modifierBtn);
        return box;
    }


    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
