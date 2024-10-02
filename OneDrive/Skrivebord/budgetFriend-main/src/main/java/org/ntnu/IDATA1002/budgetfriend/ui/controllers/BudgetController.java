package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.ntnu.IDATA1002.budgetfriend.model.Budget;
import org.ntnu.IDATA1002.budgetfriend.model.BudgetRegister;
import org.ntnu.IDATA1002.budgetfriend.ui.view.BudgetDialog;
import org.ntnu.IDATA1002.budgetfriend.ui.view.MainPageApplication;

import java.util.Optional;

/**
 * This class represents the budget controller. The controller is responsible
 * for handling
 * events from the application.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class BudgetController {

    private final MainPageApplication mainPageApplication;

    /**
     * Creates an instance of budget controller.
     *
     * @param mainPageApplication the main application.
     */
    public BudgetController(MainPageApplication mainPageApplication) {
        this.mainPageApplication = mainPageApplication;
    }

    /**
     * This method adds a budget to the budget register. The budget is created in
     * the budget dialog
     * and returns the complete budget. After the budget is added, the budget
     * register is updated.
     *
     * @param budgetRegister the register to be added to.
     */
    public void doAddBudget(BudgetRegister budgetRegister) {

        BudgetDialog budgetDialog = new BudgetDialog();

        Optional<Budget> result = budgetDialog.showAndWait();

        if (result.isPresent()) {
            Budget newBudget = result.get();
            budgetRegister.addBudget(newBudget);
            this.mainPageApplication.updateObservableList();
            this.mainPageApplication.updateBudgetTable(budgetRegister.getBudgetList());

        }
    }

    /**
     * This method is responsible for editing a budget. The dialog to the user is
     * created in the
     * budget dialog, and the dialog returns the edited budget. The register is
     * updated after.
     *
     * @param budget the budget to be edited.
     */
    public void doEditBudget(Budget budget, BudgetRegister budgetRegister) {
        if (budget == null) {
            showPleaseSelectItemDialog();
        } else {
            if (budget instanceof Budget) {
                Budget selectedBudget = (Budget) budget;

                BudgetDialog budgetDialog = new BudgetDialog(selectedBudget, true);

                budgetDialog.showAndWait();

                this.mainPageApplication.updateObservableList();
                this.mainPageApplication.updateBudgetTable(budgetRegister.getBudgetList());
            }
        }
    }

    public void calculatorHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                """
                        To use the calculator first select your age group and gender.
                        Then fill inn all the fields on the left side of the page.
                        Then you can use the CALCULATE button to see your suggested budget.
                        You can at anytime use the CLEAR button to clear all fields and selected items.

                        The calculator will then give you a new suggested budget.
                        The calculator might tell you to adjust some of your expenses and insert some to savings instead of spendings.

                        When you are finished you can use the MAIN MENU button to redirect to the main menu of the application.

                           """);
        alert.setTitle("Information");
        alert.setHeaderText("How to operate this function?");
        alert.showAndWait();
    }

    /**
     * Delete a given budget fom the budget register.
     *
     * @param budget         the budget to be removed.
     * @param budgetRegister the register to delete from.
     */
    public void doDeleteBudget(Budget budget, BudgetRegister budgetRegister) {
        if (budget == null) {
            showPleaseSelectItemDialog();
        } else {
            if (showDeleteConfirmationDialog()) {
                budgetRegister.remove(budget);
                this.mainPageApplication.updateObservableList();
                this.mainPageApplication.updateBudgetTable(budgetRegister.getBudgetList());
            }
        }
    }

    /**
     * This method displays details of a given budget. Editing is disabled.
     *
     * @param budget the budget to show.
     */
    public void doShowDetails(Budget budget) {
        if (budget == null) {
            showPleaseSelectItemDialog();
        } else {

            if (budget instanceof Budget) {
                Budget selectedBudget = (Budget) budget;

                BudgetDialog budgetDialogDialog = new BudgetDialog(selectedBudget, false);

                budgetDialogDialog.showAndWait();
            }
        }
    }

    /**
     * A dialog for the user to select an item, if no item was chosen.
     */
    public void showPleaseSelectItemDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information");
        alert.setHeaderText("No items selected");
        alert.setContentText("No item is selected from the table.\n"
                + "Please select an item from the table.");

        alert.showAndWait();
    }

    /**
     * A confirmation dialog for deleting a budget from the register.
     *
     * @return true if delete confirmed, false if not.
     */
    public boolean showDeleteConfirmationDialog() {
        boolean deleteConfirmed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Delete confirmation");
        alert.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            deleteConfirmed = (result.get() == ButtonType.OK);
        }
        return deleteConfirmed;
    }

    /**
     * Displays an information alert-dialog for help.
     */
    public void doHelpDialogInformation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                """
                        'Budget' is the function to be able to operate your budgets. This include show information, add, edit and delete budgets. The function can both read and save the information for keeping persistent data in local files.

                        To retrieve a previous budget register, the user must select a .txt file locally, in the same formats as budget. When retrieved, the information will be displayed in the table.

                        To show a budget, select and double-click the desired budget to display. A dialog will show the whole budget, such as title, description, period and income and expense if any added.

                        To edit a budget, a budget must be selected, then click the 'edit budget' button. By selecting this option, the user is able to edit the title, description, period and income to the budget, and also adding expenses as well.

                        To delete a budget, the user must select a budget to delete and click the 'delete budget' button. When selected a conformation dialog will show, to make sure the user is sure about their choice.

                        To save a budget register to a local file, the user must select the 'save to file' button. All budgets and following information will be written in a local file by the budget format.
                        """);
        alert.setTitle("Budget");
        alert.setHeaderText("How to operate this function?");
        alert.showAndWait();
    }

}
