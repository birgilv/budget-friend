package org.ntnu.IDATA1002.budgetfriend.ui.view;

import org.ntnu.IDATA1002.budgetfriend.model.Budget;
import org.ntnu.IDATA1002.budgetfriend.model.Expense;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This class represents the expense dialog. A dialog between the application
 * and the user.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class ExpenseDialog extends Dialog<Budget> {
    private Budget existingBudget = null;

    /**
     * Creates an instance of expense dialog.
     *
     * @param budget   the budget to handle expenses to.
     * @param editable true if is possible to edit, false if not.
     */
    public ExpenseDialog(Budget budget, boolean editable) {
        super();
        createContent();
        this.existingBudget = budget;
    }

    /**
     * Creates the content in the dialog box.
     */
    private void createContent() {
        setTitle("Add expense");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField valueField = new TextField();
        valueField.setPromptText("Value NOK");

        valueField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Double.parseDouble(newValue);
                }
            } catch (NumberFormatException e) {
                valueField.setText(oldValue);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(valueField, 1, 1);

        getDialogPane().setContent(grid);

        setResultConverter(
                (ButtonType button) -> {
                    Budget result = null;
                    if (button == ButtonType.OK) {
                        double value;
                        try {
                            value = Double.parseDouble(valueField.getText());
                        } catch (NumberFormatException nfe) {
                            value = 0.0;
                        }

                        existingBudget.addExpense(new Expense(titleField.getText(), value));
                        result = existingBudget;

                    }
                    return result;
                });
    }
}
