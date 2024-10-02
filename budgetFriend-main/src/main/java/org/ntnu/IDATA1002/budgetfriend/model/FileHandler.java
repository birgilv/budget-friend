package org.ntnu.IDATA1002.budgetfriend.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class represents a file handle. The class is responsible for reading and
 * writing files to keep persistent data
 * in local files. All files for budgets follows the same format.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class FileHandler {

    /**
     * Creates an instance of file handler.
     */
    public FileHandler() {
    }

    /**
     * Saves a budget to a file. The method will collect all budgets and the
     * following information and write
     * it in the budget format, then save the file created locally.
     *
     * @param budgetRegister the budget register to save.
     * @return true, if the saving was successful. False if not.
     */
    public boolean saveBudget(BudgetRegister budgetRegister) {
        boolean saveBudgetSuccess = false;
        try (FileWriter fileWriter = new FileWriter(
                "src/main/resources/saved-budgets/" + budgetRegister.getTitle() + ".txt")) {

            fileWriter.write(budgetRegister.getTitle() + "\n");

            for (Budget budget : budgetRegister.getBudgetList()) {
                fileWriter.write(generateBudgetAsString(budget));
            }

            saveBudgetSuccess = true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Save file");
            alert.setContentText("Could not save selected file.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                this.saveBudget(budgetRegister);
            }
        }
        return saveBudgetSuccess;
    }

    /**
     * Generate a budget as a string.
     *
     * @param budget the budget to generate as a string.
     * @return the budget as a string.
     */
    private String generateBudgetAsString(Budget budget) {
        StringBuilder builder = new StringBuilder("\n" + "#" + budget.getTitle());
        builder.append("," + budget.getDescription());
        builder.append("," + budget.getPeriod());
        builder.append("," + budget.getIncome() + "\n");
        for (Expense expense : budget.getExpenses()) {
            builder.append(generateExpenseAsString(expense) + "\n");
        }
        return builder.toString();
    }

    /**
     * Generate an expense as a string.
     *
     * @param expense the expense to generate as a string.
     * @return the expense as a string.
     */
    private String generateExpenseAsString(Expense expense) {
        return expense.getTitle() + "," + expense.getValue();
    }

    /**
     * Reads a budget register from a file.
     *
     * @param fileName the file to read.
     * @return the budget register form the file.
     * @throws IOException
     */
    public BudgetRegister readBudget(Path fileName) throws IOException {
        BudgetRegister budgetRegister = null;

        String budgetRegisterBlock = Files.readString(fileName);
        budgetRegister = parseBudgetRegister(budgetRegisterBlock);

        return budgetRegister;
    }

    /**
     * Parsing the budget register.
     *
     * @param budgetRegisterBlock The budget register text from the file.
     * @return the budget register.
     */
    private BudgetRegister parseBudgetRegister(String budgetRegisterBlock) {

        Scanner inputScanner = new Scanner(budgetRegisterBlock);
        String budgetRegisterTitle = inputScanner.nextLine();

        inputScanner.useDelimiter("#");
        BudgetRegister budgetRegister = new BudgetRegister(budgetRegisterTitle);
        inputScanner.next();
        while (inputScanner.hasNext()) {
            String budgetBlock = inputScanner.next();
            Budget budget = parseBudget(budgetBlock);

            budgetRegister.addBudget(budget);
        }
        return budgetRegister;
    }

    /**
     * Parsing the budget.
     *
     * @param budgetBlock The budget text from the file.
     * @return the budget.
     */
    private Budget parseBudget(String budgetBlock) {
        String[] lines = budgetBlock.split("\\n");
        String budgetInfo = lines[0];

        String[] budgetLines = budgetInfo.split(",");
        String title = budgetLines[0];
        String description = budgetLines[1];
        String periodAsString = budgetLines[2];
        String incomeAsString = budgetLines[3];
        int period = Integer.parseInt(periodAsString);
        double income = Double.parseDouble(incomeAsString);

        Budget budget = new Budget(title, description, period);
        if (income >= 0.0) {
            budget.setIncome(income);
        }

        if (lines.length > 1) {
            for (int i = 1; i < lines.length; i++) {
                String expenseBlock = lines[i];
                if (!expenseBlock.isBlank()) {
                    Expense expense = parseExpense(expenseBlock);
                    budget.addExpense(expense);
                }
            }
        }
        return budget;
    }

    /**
     * Parsing the expense.
     *
     * @param expenseBlock The expense text from the file.
     * @return the expense.
     */
    private Expense parseExpense(String expenseBlock) {
        String[] lines = expenseBlock.split(",");
        String title = lines[0];
        String valueAsString = lines[1];
        Double value = Double.parseDouble(valueAsString);

        Expense expense = new Expense(title, value);

        return expense;
    }
}
