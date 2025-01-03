import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ACP extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Saisir la Taille de la Matrice");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label labelLignes = new Label("Nombre de lignes:");
        grid.add(labelLignes, 0, 0);

        TextField fieldLignes = new TextField();
        grid.add(fieldLignes, 1, 0);

        Label labelColonnes = new Label("Nombre de colonnes:");
        grid.add(labelColonnes, 0, 1);

        TextField fieldColonnes = new TextField();
        grid.add(fieldColonnes, 1, 1);

        Button btn = new Button("Valider");
        grid.add(btn, 1, 2);

        btn.setOnAction(e -> {
            int nombreDeLignes, nombreDeColonnes;

            try {
                nombreDeLignes = Integer.parseInt(fieldLignes.getText());
                nombreDeColonnes = Integer.parseInt(fieldColonnes.getText());

                if (nombreDeLignes <= 0 || nombreDeColonnes <= 0) {
                    afficherMessageErreur("Les nombres de lignes et de colonnes doivent être positifs.");
                } else {
                    saisirMatrice(nombreDeLignes, nombreDeColonnes);
                    primaryStage.close();
                }
            } catch (NumberFormatException ex) {
                afficherMessageErreur("Veuillez entrer des valeurs numériques valides.");
            }
        });

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saisirMatrice(int nombreDeLignes, int nombreDeColonnes) {
        Scanner scanner = new Scanner(System.in);

        // Initialisation de la matrice
        List<List<Float>> matrice = new ArrayList<>();
        System.out.println("Veuillez entrer les valeurs de la matrice :");
        for (int i = 0; i < nombreDeLignes; i++) {
            List<Float> ligne = new ArrayList<>();
            for (int j = 0; j < nombreDeColonnes; j++) {
                System.out.printf("Entrez la valeur pour la ligne %d, colonne %d : ", i + 1, j + 1);
                while (!scanner.hasNextFloat()) {
                    System.out.println("Veuillez entrer un nombre flottant valide.");
                    scanner.next(); // consommer l'entrée incorrecte
                }
                ligne.add(scanner.nextFloat());
            }
            matrice.add(ligne);
        }

        // Affichage de la matrice initiale
        System.out.println("------------ La matrice est ------------");
        for (List<Float> ligne : matrice) {
            for (Float valeur : ligne) {
                System.out.printf("%f   ", valeur);
            }
            System.out.println();
        }
        afficherMatrice(matrice);

        // Calcul des moyennes
        float somme1 = 0, somme2 = 0, somme3 = 0;
        for (List<Float> ligne : matrice) {
            somme1 += ligne.get(0);
            somme2 += ligne.get(1);
            somme3 += ligne.get(2);
        }
        float moyenne1 = somme1 / nombreDeLignes;
        float moyenne2 = somme2 / nombreDeLignes;
        float moyenne3 = somme3 / nombreDeLignes;

        // Calcul de la matrice centrée
        List<List<Float>> matriceCentree = new ArrayList<>();
        for (List<Float> ligne : matrice) {
            List<Float> nouvelleLigne = new ArrayList<>();
            nouvelleLigne.add(ligne.get(0) - moyenne1);
            nouvelleLigne.add(ligne.get(1) - moyenne2);
            nouvelleLigne.add(ligne.get(2) - moyenne3);
            matriceCentree.add(nouvelleLigne);
        }

        // Affichage de la matrice centrée
        System.out.println("------------ La matrice centrée est ------------");
        for (List<Float> ligne : matriceCentree) {
            if (ligne.size() >= 3) {
                System.out.printf("%f      %f      %f \n", ligne.get(0), ligne.get(1), ligne.get(2));
            } else {
                System.out.println("Erreur : la taille de la ligne est inférieure à 3.");
            }
        }
        
        
        // Calcul de la matrice réduite
     // Calcul des écarts-types
        float ecartType1 = 0, ecartType2 = 0, ecartType3 = 0;
        for (List<Float> ligne : matriceCentree) {
            ecartType1 += ligne.get(0) * ligne.get(0);
            ecartType2 += ligne.get(1) * ligne.get(1);
            ecartType3 += ligne.get(2) * ligne.get(2);
        }

        ecartType1 = ecartType1 / nombreDeLignes;
        ecartType1 = (float) Math.sqrt(ecartType1);
        ecartType2 = ecartType2 / nombreDeLignes;
        ecartType2 = (float) Math.sqrt(ecartType2);
        ecartType3 = ecartType3 / nombreDeLignes;
        ecartType3 = (float) Math.sqrt(ecartType3);

        // Affichage des écarts-types
        System.out.println("\n\n\t******** Ecarts-types : **********\n");
        System.out.printf("Ecart-type 1 : %f\n", ecartType1);
        System.out.printf("Ecart-type 2 : %f\n", ecartType2);
        System.out.printf("Ecart-type 3 : %f\n\n", ecartType3);

        // Calcul de la matrice centrée réduite
        List<List<Float>> matriceCentreeReduite = new ArrayList<>();
        for (List<Float> ligne : matriceCentree) {
            List<Float> nouvelleLigne = new ArrayList<>();
            nouvelleLigne.add(ligne.get(0) / ecartType1);
            nouvelleLigne.add(ligne.get(1) / ecartType2);
            nouvelleLigne.add(ligne.get(2) / ecartType3);
            matriceCentreeReduite.add(nouvelleLigne);
        }

        // Affichage de la matrice centrée réduite
        System.out.println("\n\n\t******** Matrice Centrée Réduite : **********\n\n\t ");
        for (List<Float> ligne : matriceCentreeReduite) {
            for (float valeur : ligne) {
                System.out.printf("\t%f\t  ", valeur);
            }
            System.out.println("\n");
        }


        // Calcul de la matrice centrée normée
        List<List<Float>> matriceCentreeNormee = new ArrayList<>();
        for (List<Float> ligne : matriceCentreeReduite) {
            List<Float> nouvelleLigne = new ArrayList<>();
            nouvelleLigne.add((float) (ligne.get(0) / Math.sqrt(nombreDeLignes)));
            nouvelleLigne.add((float) (ligne.get(1) / Math.sqrt(nombreDeLignes)));
            nouvelleLigne.add((float) (ligne.get(2) / Math.sqrt(nombreDeLignes)));
            matriceCentreeNormee.add(nouvelleLigne);
        }

        System.out.println("------------ La matrice centrée normée est ------------");
        for (List<Float> ligne : matriceCentreeNormee) {
            System.out.printf(" %f      %f     %f   |\n", ligne.get(0), ligne.get(1), ligne.get(2));
        }

        // Calcul de la matrice transposée
        List<List<Float>> matriceTransposee = new ArrayList<>();
        for (int i = 0; i < nombreDeColonnes; i++) {
            List<Float> nouvelleLigne = new ArrayList<>();
            for (int k = 0; k < nombreDeLignes; k++) {
                nouvelleLigne.add(matriceCentreeNormee.get(k).get(i));
            }
            matriceTransposee.add(nouvelleLigne);
        }

        // Affichage de la matrice transposée
        System.out.println("------------ La matrice transposée est ------------");
        for (List<Float> ligne : matriceTransposee) {
            for (Float valeur : ligne) {
                System.out.printf("%f  ", valeur);
            }
            System.out.println();
        }

        // Calcul de la matrice de corrélation
        float[][] matriceCorrelation = new float[nombreDeColonnes][nombreDeColonnes];
        for (int k = 0; k < nombreDeColonnes; k++) {
            for (int l = 0; l < nombreDeColonnes; l++) {
                matriceCorrelation[k][l] = 0;
                for (int i = 0; i < nombreDeLignes; i++) {
                    matriceCorrelation[k][l] += matriceTransposee.get(k).get(i) * matriceCentreeNormee.get(i).get(l);
                }
            }
        }

        // Affichage de la matrice de corrélation
        System.out.println("\n La matrice de corrélation est ");
        for (int k = 0; k < nombreDeColonnes; k++) {
            for (int i = 0; i < nombreDeColonnes; i++) {
                System.out.printf("%f   ", matriceCorrelation[k][i]);
            }
            System.out.println();
        }

        // Affichage des coordonnées des individus
        System.out.println("Coordonnées des individus (Lignes) :");
        for (int i = 0; i < nombreDeLignes; i++) {
            System.out.print("Individu " + (i + 1) + " : ");
            for (int j = 0; j < nombreDeColonnes; j++) {
                System.out.print(matriceCentreeNormee.get(i).get(j) + " ");
            }
            System.out.println();
        }

        // Affichage des coordonnées des variables
        System.out.println("\nCoordonnées des variables (Colonnes) :");
        for (int j = 0; j < nombreDeColonnes; j++) {
            System.out.print("Variable " + (j + 1) + " : ");
            for (int i = 0; i < nombreDeLignes; i++) {
                System.out.print(matriceTransposee.get(j).get(i) + " ");
            }
            System.out.println();
        }

        // Création du graphique
        Stage stage = new Stage(); // Déclarer la variable stage

        stage.setTitle("Graphique");
        final NumberAxis axeX = new NumberAxis(-2, 2, 0.1);
        final NumberAxis axeY = new NumberAxis(-2, 2, 0.1);
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(axeX, axeY);
        XYChart.Series series1 = new XYChart.Series();
        for (List<Float> ligne : matriceCentree) {
            series1.getData().add(new XYChart.Data(ligne.get(0), ligne.get(1)));
        }
        scatterChart.getData().addAll(series1);
        Scene scene = new Scene(scatterChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    private void afficherMatrice(List<List<Float>> matrice) {
        Stage matriceStage = new Stage();
        matriceStage.setTitle("Matrice Initiale");

        GridPane grid = new GridPane();
        grid.setHgap(10); // Espacement horizontal entre les labels
        grid.setVgap(10); // Espacement vertical entre les labels
        grid.setPadding(new Insets(10)); // Marge autour de la grille

        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.get(i).size(); j++) {
                Label label = new Label(String.format("%.4f", matrice.get(i).get(j)));
                label.setStyle("-fx-font-size: 14;"); // Ajustez la taille de la police si nécessaire
                grid.add(label, j, i);
            }
        }

        Scene scene = new Scene(grid);
        matriceStage.setScene(scene);
        matriceStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}