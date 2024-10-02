package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.ntnu.IDATA1002.budgetfriend.model.BudgetRegister;
import org.ntnu.IDATA1002.budgetfriend.model.FileHandler;
import org.ntnu.IDATA1002.budgetfriend.ui.view.MainPageApplication;
import org.ntnu.IDATA1002.budgetfriend.ui.view.SaveBudgetDialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * This class represents a file handler controller. The controller is
 * responsible for handling
 * * events from the application.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class FileHandlerController {
    private MainPageApplication mainPageApplication;
    private FileHandler fileHandler;
    private Path dataDirectory;
    private static final String DATA_DIRECTORY = "src/main/resources/saved-budgets/";

    /**
     * Creates an instance of a file handler controller.
     *
     * @param mainPageApplication the main page application.
     */
    public FileHandlerController(MainPageApplication mainPageApplication) {
        this.mainPageApplication = mainPageApplication;
        this.fileHandler = new FileHandler();
    }

    /**
     * Creates a create a data directory.
     *
     * @throws IOException
     */
    public void createDataDirectory() throws IOException {
        this.dataDirectory = Path.of(DATA_DIRECTORY);
        if (!Files.exists(this.dataDirectory)) {
            Files.createDirectory(this.dataDirectory);
        }
    }

    /**
     * Retrieve a previous budget register from a file.
     *
     * @param event the event.
     */
    public void doOpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Budget file");
        fileChooser.setInitialDirectory(Path.of(DATA_DIRECTORY).toFile());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Budget Files", "*.txt"));

        File file = fileChooser.showOpenDialog(this.mainPageApplication.getStage());
        if (file != null) {
            try {
                BudgetRegister budgetRegister = this.fileHandler.readBudget(file.toPath());
                this.mainPageApplication.updateBudgetRegister(budgetRegister);
                this.mainPageApplication.updateBudgetTable(budgetRegister.getBudgetList());
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Open file");
                alert.setContentText("Could not open selected file.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    this.doOpenFile(event);
                }
            }
        }
    }

    /**
     * Saves a budget register to a file.
     *
     * @param budgetRegister the budget register to save.
     */
    public void doSaveFile(BudgetRegister budgetRegister) {
        SaveBudgetDialog saveBudgetDialog = new SaveBudgetDialog();

        Optional<String> result = saveBudgetDialog.showAndWait();

        if (result.isPresent()) {
            String title = result.get();
            budgetRegister.setTitle(title);
            this.fileHandler.saveBudget(budgetRegister);
            this.mainPageApplication.updateBudgetTable(budgetRegister.getBudgetList());
        }
    }
}
