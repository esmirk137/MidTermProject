package com.company;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFieldPromptStylingTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        textField.setPromptText("Enter text");

        PseudoClass empty = PseudoClass.getPseudoClass("empty");
        textField.pseudoClassStateChanged(empty, true);
        textField.textProperty().addListener((obs, oldText, newText) -> {
            textField.pseudoClassStateChanged(empty, newText.isEmpty());
        });

        Button okButton = new Button("OK");
        VBox root = new VBox(10, textField, okButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
