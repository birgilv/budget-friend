package org.ntnu.IDATA1002.budgetfriend.model;

/**
 * This class represents an expense. Each budget can have one or more expenses.
 * An expense is consisting
 * of a title, the name of the expense, and a value, the money cost of the
 * expense.
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class Expense {
    private String title;
    private Double value;

    /**
     * Creates an instance of an expense.
     * 
     * @param title the title of the expense.
     * @param value the value of the expense.
     */
    public Expense(String title, Double value) {
        this.setTitle(title);
        this.setValue(value);
    }

    /**
     * Sets the title of an expense.
     * 
     * @param title the title of an expense.
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Null or empty title in setTitle.");
        }
        this.title = title;
    }

    /**
     * Sets the value of an expense.
     * 
     * @param value the value of an expense.
     */
    public void setValue(Double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }
        this.value = value;
    }

    /**
     * Return the title of an expense.
     * 
     * @return the title of an expense.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Return the title of an expense.
     * 
     * @return the title of an expense.
     */
    public Double getValue() {
        return this.value;
    }

}
