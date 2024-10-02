package org.ntnu.IDATA1002.budgetfriend.ui.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * This class represents the save budget dialog. A dialog between the
 * application and the user.
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class SaveBudgetDialog extends Dialog<String> {

    /**
     * Creates an instance of save budget dialog.
     */
    public SaveBudgetDialog() {
        super();
        createContent();
    }

    /**
     * Creates the content in the dialog box.
     */
    private void createContent() {
        setTitle("Save budget register");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);

        getDialogPane().setContent(grid);

        setResultConverter(
                (ButtonType button) -> {
                    String result = "";
                    if (button == ButtonType.OK) {

                        try {
                            result = titleField.getText();
                            if (result.isBlank()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Information");
                                alert.setHeaderText("No title selected");
                                alert.setContentText("No title is selected for the budget register.\n"
                                        + "Please select a title.");

                                alert.showAndWait();
                            }
                        }catch (IllegalArgumentException e){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Create content");
                            alert.setContentText("Illegal argument.");
                        };

                    }
                    return result;
                });
    }
}
