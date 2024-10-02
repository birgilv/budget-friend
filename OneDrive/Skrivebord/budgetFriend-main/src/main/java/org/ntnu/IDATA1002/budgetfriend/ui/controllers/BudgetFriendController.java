package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Represents the controller for Budget Friend. This includes the main page.
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class BudgetFriendController {

    /**
     * This method exits the application with a confirmation alert.
     */
    public void doExitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Exit Application ?");
        alert.setContentText("Are you sure you want to exit this application?\nAll unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

}
