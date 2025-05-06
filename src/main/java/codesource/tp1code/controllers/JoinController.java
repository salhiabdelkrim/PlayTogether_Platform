package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import codesource.tp1code.models.DataStore;
import codesource.tp1code.models.Membre;
import codesource.tp1code.models.Rencontre;
import codesource.tp1code.models.Sport;
import codesource.tp1code.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class JoinController implements Initializable {
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
    public void changerfenetre8(){changementFenetre("/codesource/tp1code/vues/Scene8.fxml", "Play Together - Nos Terrains");}

    @FXML
    private ScrollPane joinContent;

    private DataStore data = DataStore.getInstance();
    private Membre membre = SessionManager.getInstance().getMembreConnecte();

    @FXML
    private GridPane matchGrid;
    @FXML
    private DatePicker filtreDate;
    @FXML
    private ComboBox<String> filtres;
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
        List<Rencontre> rencontres = data.getRencontres();
        // Initialisation du ComboBox avec les noms de sports uniques
        filtres.getItems().clear();
        data.getSports().stream()
                .map(Sport::getName)
                .distinct()
                .forEach(nom -> filtres.getItems().add(nom));
        int row = 0;
        int col = 0;

        for (Rencontre r : rencontres) {
            VBox card = createRencontreCard(r);
            matchGrid.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    /** Methode qui affiche les rencontres selon les filtres indqués */
    @FXML
    private void filtrerRencontres() {
        matchGrid.getChildren().clear(); // On nettoie l'affichage actuel

        String sportSelectionne = filtres.getValue();
        LocalDate dateSelectionnee = filtreDate.getValue();

        List<Rencontre> rencontres = data.getRencontres();
        int row = 0;
        int col = 0;

        for (Rencontre r : rencontres) {
            LocalDate dateRencontre = r.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String sportRencontre = r.getSport().getName();

            boolean dateOK = (dateSelectionnee == null || dateRencontre.equals(dateSelectionnee));
            boolean sportOK = (sportSelectionne == null || sportRencontre.equals(sportSelectionne));

            if (dateOK && sportOK) {
                VBox card = createRencontreCard(r);
                matchGrid.add(card, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    /** methode qui genere dynamiquement les sports pratiques */
    private VBox createRencontreCard(Rencontre rencontre) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #303030; -fx-border-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPrefWidth(230);

        Label sport = new Label(rencontre.getSport().getName());
        sport.setTextFill(javafx.scene.paint.Color.web("#99f854"));
        sport.setFont(new Font("Cambria Bold", 14));

        Label joueurs = new Label(rencontre.getListeJoueurs().size() + " joueurs inscrits / " + rencontre.getNombreJoueurs() + " nécessaires");
        joueurs.setFont(new Font("Cambria", 11));

        Label adresse = new Label(rencontre.getAdresse());
        adresse.setWrapText(true);
        adresse.setFont(new Font("Cambria", 12));

        LocalDate localDate = rencontre.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Définition du formatteur
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);


        Label date = new Label(localDate.format(formatter) + " " + rencontre.getHeureDebut());
        date.setFont(new Font("Cambria", 12));

        Label frais = new Label("Frais : " + String.format("%.2f", rencontre.getFrais()) + "$");
        frais.setFont(new Font("Cambria", 12));

        Button rejoindreBtn = new Button("Rejoindre");
        rejoindreBtn.setOnAction(e -> {
            if (rencontre.rejoindreRencontre(membre)){
                SignInSignUpController.afficherAlerte("Bravo !!","vous avez pu rejoindre cette rencontre ");
                changerfenetre4();
            }else {
                SignInSignUpController.afficherAlerte("Erreur","Le nombre maximal des participants est atteint ou la rencontre est déjà programmée pour vous ");
            }

        });

        box.getChildren().addAll(sport, joueurs, adresse, date, frais, rejoindreBtn);
        return box;
    }



}
