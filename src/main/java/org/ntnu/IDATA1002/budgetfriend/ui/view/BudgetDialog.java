package org.ntnu.IDATA1002.budgetfriend.ui.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA1002.budgetfriend.model.Budget;

/**
 * This class represents the budget dialog. A dialog between the application and
 * the user.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class BudgetDialog extends Dialog<Budget> {
    /**
     * The different modes.
     */
    public enum Mode {
        NEW, EDIT, INFO
    }

    private final Mode mode;
    private Budget existingBudget = null;

    /**
     * Creates an instance of budget dialog.
     */
    public BudgetDialog() {
        super();
        this.mode = Mode.NEW;
        createContent();

    }

    /**
     * Creates an instance of budget dialog.
     *
     * @param budget   the budget to handle.
     * @param editable true if mode is edit, false if not.
     */
    public BudgetDialog(Budget budget, boolean editable) {
        super();
        if (editable) {
            this.mode = Mode.EDIT;
        } else {
            this.mode = Mode.INFO;
        }
        this.existingBudget = budget;
        createContent();
    }

    /**
     * Creates the content in the dialog box. The same view for both INFO and EDIT,
     * but
     * when mode EDIT is enabled edit is set true, if else false.
     */
    private void createContent() {
        setTitle("Budget Details");

        ButtonType addExpenseButtonType = new ButtonType("Add expense");
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField title = new TextField();
        title.setPromptText("Title");

        TextField description = new TextField();
        description.setPromptText("Description");

        TextField periodTxt = new TextField();
        periodTxt.setPromptText("Period");

        TextField incomeTxt = new TextField();
        incomeTxt.setPromptText("Income NOK");

        TextArea expensesTxt = new TextArea();

        periodTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Integer.parseInt(newValue);
                }
            } catch (NumberFormatException e) {
                periodTxt.setText(oldValue);
            }
        });

        incomeTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Double.parseDouble(newValue);
                }
            } catch (NumberFormatException e) {
                incomeTxt.setText(oldValue);
            }
        });

        if ((mode == Mode.EDIT) || (mode == Mode.INFO)) {
            title.setText(existingBudget.getTitle());
            description.setText(existingBudget.getDescription());
            periodTxt.setText(Integer.toString(existingBudget.getPeriod()));
            incomeTxt.setText(Double.toString(existingBudget.getIncome()));
            expensesTxt.setText(existingBudget.getExpenseAsString());
            expensesTxt.setEditable(false);

            if (mode == Mode.INFO) {
                title.setEditable(false);
                description.setEditable(false);
                periodTxt.setEditable(false);
                incomeTxt.setEditable(false);
            } else {
                getDialogPane().getButtonTypes().add(addExpenseButtonType);
            }
        } else {
            expensesTxt.setEditable(false);
        }

        grid.add(new Label("* Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("* Description:"), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label("* Period:"), 0, 2);
        grid.add(periodTxt, 1, 2);
        grid.add(new Label("Income:"), 0, 3);
        grid.add(incomeTxt, 1, 3);
        grid.add(new Label("Expenses:"), 0, 4);
        grid.add(expensesTxt, 1, 4);

        getDialogPane().setContent(grid);

        setResultConverter(
                (ButtonType button) -> {
                    Budget result = null;
                    if (button == ButtonType.OK) {
                        int period = Integer.parseInt(periodTxt.getText());
                        double income = Double.parseDouble(incomeTxt.getText());

                        if (mode == Mode.NEW) {
                            result = new Budget(title.getText(), description.getText(), period);
                            if (income > 0.0) {
                                result.setIncome(income);
                            }
                        } else if (mode == Mode.EDIT) {

                            existingBudget.setTitle(title.getText());
                            existingBudget.setDescription(description.getText());
                            existingBudget.setPeriod(period);
                            existingBudget.setIncome(income);

                            expensesTxt.setText(existingBudget.getExpenseAsString());
                            result = existingBudget;
                        }
                    }
                    if (button == addExpenseButtonType) {
                        ExpenseDialog expenseDialog = new ExpenseDialog(existingBudget, true);
                        expenseDialog.showAndWait();
                    }
                    return result;
                });
    }
}
