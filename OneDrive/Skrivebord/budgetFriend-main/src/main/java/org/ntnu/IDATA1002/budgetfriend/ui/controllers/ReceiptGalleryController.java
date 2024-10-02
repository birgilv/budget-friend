package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class represent the receipt gallery controller. The controller is
 * responsible for handling
 * events from the application.
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class ReceiptGalleryController {
    private List<File> imageFiles = new ArrayList<>();
    public GridPane imagePane = new GridPane();

    /**
     * This method is responsible for letting the user choose an image file.
     */
    public void chooseImageFile() {
        // Show a file chooser dialog for selecting images
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                if (file.isFile()) {
                    imageFiles.add(file);
                    addImage(file);
                }
            }
        }
    }

    /**
     * This method is responsible for adding an image to the screen
     * and provide its functionality such as the ability to view the
     * image, see its filename and delete it from the application.
     * 
     * @param file the file that the user have imported
     */
    public void addImage(File file) {
        // Load the image from file
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Add a delete button to the image
        Button deleteButton = new Button("X");
        deleteButton.getStyleClass().add("deleteButton");
        Label fileLabel = new Label(file.getName());
        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, fileLabel, deleteButton);
        hBox.setSpacing(20);

        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this receipt?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.setTitle("Delete Receipt");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                imageFiles.remove(file);
                imagePane.getChildren().remove(hBox);
            }
        });

        // Add a click handler to view the image in full size
        imageView.setOnMouseClicked(e -> {
            ImageView fullImageView = new ImageView(image);
            fullImageView.setSmooth(true);

            if (image.getWidth() > 1000) {
                fullImageView.setFitWidth(500);
            }
            if (image.getHeight() > 1000) {
                fullImageView.setFitHeight(500);
            }

            StackPane fullStackPane = new StackPane(fullImageView);
            Scene fullScene = new Scene(fullStackPane);
            Stage fullStage = new Stage();
            fullStage.setScene(fullScene);
            fullStage.show();
        });

        imagePane.addRow(imagePane.getChildren().size(), hBox);
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setHgap(50);
        imagePane.setVgap(30);
    }

    /**
     * Pop up window for the help button.
     */
    public void helpPopUp() {
        ButtonType okButton = new ButtonType("Thank you");
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                """
                        Receipt gallery is a place where you can
                        gather all you receipts in on place. All
                        you need to do is click the "add receipt to
                        the gallery" button and choose your receipt.\n
                        NB! This gallery only accept jpg, png and jpeg
                        files.\n
                        The receipt wil appear on the screen, as
                        a small picture, its file name and a delete button.\n
                        To view the receipt in a bigger format, you simply
                        click on the small picture.\n
                        To delete a receipt from the receipt gallery
                        you can click on the red "x" button.

                        """,
                okButton);
        alert.setTitle("HOW DOES THE RECEIPT GALLERY WORK");
        alert.setHeaderText("THIS IS RECEIPT GALLERY!");
        alert.showAndWait();
    }
}