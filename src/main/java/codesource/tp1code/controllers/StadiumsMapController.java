package codesource.tp1code.controllers;

import codesource.tp1code.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StadiumsMapController implements Initializable {
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
    private VBox myStage ;


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
    private Label adresse;
    @FXML
    private CubicCurve soccers, soccers1, soccers2, soccers3, soccers4, soccers5;
    @FXML
    private CubicCurve tennis, tennis1, tennis2, tennis3, tennis4;
    @FXML
    private CubicCurve basketball, basketball1, basketball2, basketball3, basketball4;
    @FXML
    private CubicCurve baseball, baseball1, baseball2, baseball3;
    @FXML
    private ListView<String> list;

    private CubicCurve selectedCurve = null; // Track selected curve
    private String[] option = {"all activities", "Soccer", "Tennis", "BasketBall", "BaseBall"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exemple : Ajouter des tailles dans le ComboBox (si ce n'est pas fait ailleurs)
        taille.getItems().addAll(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

        // Ajouter un listener pour détecter les changements de sélection
        taille.setOnAction(event -> {
            Double facteur  = taille.getValue(); // Récupérer la valeur choisie
            if (facteur  != null) {
                changerTaillePolice(facteur); // Appeler ta méthode
            }
        });
        resetLabels();
        list.getItems().addAll(option);

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                resetLabels();
                updateCurveVisibility(newValue);
            }
        });

        setupCurveClickEvents();
    }

    private void resetLabels() {
        adresse.setText("");
    }

    private void setupCurveClickEvents() {
        setCurveClickEvent(soccers, () -> soc());
        setCurveClickEvent(soccers1, () -> soc1());
        setCurveClickEvent(soccers2, () -> soc2());
        setCurveClickEvent(soccers3, () -> soc3());
        setCurveClickEvent(soccers4, () -> soc4());
        setCurveClickEvent(soccers5, () -> soc5());

        setCurveClickEvent(tennis, () -> tenn());
        setCurveClickEvent(tennis1, () -> tenn1());
        setCurveClickEvent(tennis2, () -> tenn2());
        setCurveClickEvent(tennis3, () -> tenn3());
        setCurveClickEvent(tennis4, () -> tenn4());

        setCurveClickEvent(basketball, () -> ball());
        setCurveClickEvent(basketball1, () -> ball1());
        setCurveClickEvent(basketball2, () -> ball2());
        setCurveClickEvent(basketball3, () -> ball3());
        setCurveClickEvent(basketball4, () -> ball4());

        setCurveClickEvent(baseball, () -> bas());
        setCurveClickEvent(baseball1, () -> bas1());
        setCurveClickEvent(baseball2, () -> bas2());
        setCurveClickEvent(baseball3, () -> bas3());
    }

    private void setCurveClickEvent(CubicCurve curve, Runnable action) {
        if (curve != null) {
            curve.setOnMouseClicked(event -> {
                System.out.println("Clicked on: " + curve.getId()); // Debugging
                if (selectedCurve != null) {
                    resetCurve(selectedCurve);
                }
                applySelectedEffect(curve);
                selectedCurve = curve;
                action.run(); // Updates label
            });
        } else {
            System.out.println("Warning: A curve is null"); // Debugging
        }
    }

    private void applySelectedEffect(CubicCurve curve) {
        curve.setStroke(Color.DARKGREEN);
        curve.setStrokeWidth(3.0);
        curve.setEffect(new DropShadow(15, Color.DARKGREEN));
    }

    private void resetCurve(CubicCurve curve) {
        curve.setStroke(Color.BLACK);
        curve.setStrokeWidth(1.0);
        curve.setEffect(null);
    }

    private void updateCurveVisibility(String selectedOption) {
        boolean showSoccer = "all activities".equals(selectedOption) || "Soccer".equals(selectedOption);
        boolean showTennis = "all activities".equals(selectedOption) || "Tennis".equals(selectedOption);
        boolean showBasketball = "all activities".equals(selectedOption) || "BasketBall".equals(selectedOption);
        boolean showBaseball = "all activities".equals(selectedOption) || "BaseBall".equals(selectedOption);

        setCurveVisibility(new CubicCurve[]{soccers, soccers1, soccers2, soccers3, soccers4, soccers5}, showSoccer);
        setCurveVisibility(new CubicCurve[]{tennis, tennis1, tennis2, tennis3, tennis4}, showTennis);
        setCurveVisibility(new CubicCurve[]{basketball, basketball1, basketball2, basketball3, basketball4}, showBasketball);
        setCurveVisibility(new CubicCurve[]{baseball, baseball1, baseball2, baseball3}, showBaseball);
    }

    private void setCurveVisibility(CubicCurve[] curves, boolean visible) {
        for (CubicCurve curve : curves) {
            if (curve != null) {
                curve.setVisible(visible);
            }
        }
    }

    // Address update methods
    @FXML
    protected void soc() { adresse.setText("Soccer-Université de Québec à \nTrois-Rivières\n3351, boulevard des forges"); }
    @FXML
    protected void soc1() { adresse.setText("Soccer- Parc Lambert\n2000, 6e Rue"); }
    @FXML
    protected void soc2() { adresse.setText("Soccer- Ecole secondaire des Pionniers - \nPavillon Saint-Ursule\n1725, boulevard du Carmel"); }
    @FXML
    protected void soc3() { adresse.setText("Soccer - Parc Jaques-Buteux\nRue Maurice-L-Duplessis"); }
    @FXML
    protected void soc4() { adresse.setText("Soccer - Parc du Père-Breton\n301, rue du Père Breton"); }
    @FXML
    protected void soc5() { adresse.setText("Soccer - Parc Roger-Guilbault\n1301, rue Boisclair"); }

    @FXML
    protected void tenn() { adresse.setText("Terrains de tennis - Parc du Moulin\nRue du Moulin des Jésuites"); }
    @FXML
    protected void tenn1() { adresse.setText("Terrains de tennis - Parc Masse\n220, Chemin Masse"); }
    @FXML protected void tenn2() { adresse.setText("Terrains de tennis - Parc Martin-Bergeron\n220, rue Party"); }
    @FXML protected void tenn3() { adresse.setText("Terrains de tennis - Parc des Plaines\n3871, rue des Plaines"); }
    @FXML protected void tenn4() { adresse.setText("Terrains de tennis - Parc Quirion\n6330, rue Quirion"); }

    @FXML protected void ball() { adresse.setText("Basketball - Parc de l'école \nSainte-Catherine-de-Sienne\n182, rue de Sienne"); }
    @FXML protected void ball1() { adresse.setText("Basketball - Parc Pie-XII"); }
    @FXML protected void ball2() { adresse.setText("Basketball - Parc Lemire\n840, rue Saint Paul"); }
    @FXML protected void ball3() { adresse.setText("Basketball - Parc Claude-Champoux\n50, rue Wilfrid Laurier"); }
    @FXML protected void ball4() { adresse.setText("Basketball - Parc des Ormeaux\n300, rue Chapleau"); }

    @FXML protected void bas() { adresse.setText("Baseball - Parc Jean-Perron\n670, rue Guilbert"); }
    @FXML protected void bas1() { adresse.setText("Baseball - Parc Saint-Jean-de-Brébeuf\n1315, rue de la Terrière"); }
    @FXML protected void bas2() { adresse.setText("Baseball - Parc Rosemont\n5225, rue de Courcelette"); }
    @FXML protected void bas3() { adresse.setText("Baseball - Parc Sainte-Marguerite\n2000, avenue des Coopérants"); }
}
