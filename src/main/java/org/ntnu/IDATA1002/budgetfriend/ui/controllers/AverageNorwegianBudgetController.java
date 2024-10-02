package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import org.ntnu.IDATA1002.budgetfriend.ui.view.MainPageApplication;

import javafx.scene.control.Alert;

/**
 * Represents the controller for the average norwegian budget.
 * 
 * <p>
 * This class has one attribute:
 * <ul>
 * <li>Main page application: The main page application for the controller (this
 * class).</li>
 * </ul>
 * </p>
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class AverageNorwegianBudgetController {
    private MainPageApplication mainPageApplication;

    /**
     * Constructs what will be represented as a controller for the average norwegian
     * budget.
     * 
     * @param mainPageApplication the main page application for the controller.
     */
    public AverageNorwegianBudgetController(MainPageApplication mainPageApplication) {
        setMainPageApplication(mainPageApplication);
    }

    /**
     * Sets the main page application.
     * 
     * @param mainPageApplication the main page application.
     * @throws IllegalArgumentException if main page application is null.
     */
    public void setMainPageApplication(MainPageApplication mainPageApplication) {
        if (mainPageApplication == null) {
            throw new IllegalArgumentException("The main page application cannot be null");
        } else {
            this.mainPageApplication = mainPageApplication;
        }
    }

    /**
     * Returns the main page application.
     * 
     * @return the main page application.
     */
    public MainPageApplication getMainPageApplication() {
        return this.mainPageApplication;
    }

    /**
     * Displays an error alert-dialog for sex.
     */
    public void doSexDialogError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Sex not registered.");
        alert.setContentText("Please select a sex before viewing the table.");
        alert.showAndWait();
    }

    /**
     * Displays an error alert-dialog for age.
     */
    public void doAgeDialogError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Age not registered.");
        alert.setContentText("Please select an age before viewing the table.");
        alert.showAndWait();
    }

    /**
     * Displays an informastion alert-dialog for help.
     */
    public void doHelpDialogInformation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                """
                        'Average Norwegian Budget' is the function that enables one to see how the average norwegian divide their budget.

                        To use this function, one must choose an age-range from the 'Choose Age' combo-box. After an age-range is selected, one must choose a sex from the 'Choose sex' combo-box under the age combo-box. After this is done, one may press the 'View Table button' to view the chosen table from the preivious combo-box choices.

                        If one wishes to view how a child would affect the budget, please press on the 'Expecting/Have Childeren?' check-box, and then the 'View Table' button to view how it affects the average norwegian budget.

                        If one wants to change their age or sex decision, it is to do the previous steps again. Select a new age-range or a new sex, or uncheck the 'Expecting/Have Childeren' check-box and press on the 'View Table' button to see the new budget table for the average norwegian.

                        To return to the main menu, please press the 'Main Menu' button on the bottom right corner.
                        """);
        alert.setTitle("Information");
        alert.setHeaderText("How to operate this function?");
        alert.showAndWait();
    }
}