package org.ntnu.IDATA1002.budgetfriend.model;

import java.util.*;

/**
 * This class represents a budget. A budget is the main content of the Budget
 * Friend Application.
 * A budget consists of a budget name, a budget description and a budget period.
 * The budget description
 * describes the content of one particular budget. The budget period is set by
 * the budget owner choice.
 * The period can be a week, two weeks or a month.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class Budget {
    private String title;
    private String description;
    private int period;
    private double income;
    private List<Expense> expenses;

    /**
     * Creates an instance of a budget.
     * 
     * @param title       the name of the budget
     * @param description the description of the budget
     * @param period      the time period of the budget
     */
    public Budget(String title, String description, int period) {
        this.setTitle(title);
        this.setDescription(description);
        this.setPeriod(period);
        this.expenses = new ArrayList<>();
    }

    /**
     * Set the budget name.
     * 
     * @param title the name of the budget
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Budget name cannot be null or blank.");
        }
        this.title = title;
    }

    /**
     * Returns the name of the budget.
     * 
     * @return the name of the budget.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the budget description.
     * 
     * @param description the description of the budget
     */
    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Budget description cannot be null or blank.");
        }
        this.description = description;
    }

    /**
     * Returns the description of the budget.
     * 
     * @return the description of the budget.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the budget period.
     * 
     * @param period the period of the budget
     */
    public void setPeriod(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Budget period must be greater than 0.");
        }
        this.period = period;
    }

    /**
     * Returns the budgetPeriod of the budget.
     * 
     * @return the budgetPeriod of the budget.
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Set the income for the budget.
     * 
     * @param income the income for the budget
     */
    public void setIncome(double income) {
        if (income < 0.0) {
            throw new IllegalArgumentException("Budget income must be greater than 0.");
        }
        this.income = income;
    }

    /**
     * Returns the income for the budget.
     * 
     * @return the income for the budget.
     */
    public double getIncome() {
        return income;
    }

    /**
     * Adds an expense to the list og expenses.
     * 
     * @param expense the expense to be added
     */
    public void addExpense(Expense expense) { // throws DuplicateActionException
        if (expense == null) {
            throw new IllegalArgumentException("Invalid expense name or expense value.");
        }

        this.expenses.add(expense);
    }

    /**
     * Returns all the expenses.
     * 
     * @return all the expenses
     */
    public List<Expense> getExpenses() {
        return this.expenses;
    }

    /**
     * Return the expense as a string.
     * 
     * @return the expense as a string.
     */
    public String getExpenseAsString() {
        String allExpenses = "";
        for (Expense expense : getExpenses()) {
            allExpenses += expense.getTitle() + ": " + expense.getValue() + "\n";
        }
        if (getExpenses().size() <= 0) {
            allExpenses = "No expenses found";
        }
        return allExpenses;
    }
}
