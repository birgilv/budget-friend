package org.ntnu.IDATA1002.budgetfriend.model;

/**
 * Represents a cell in the average norwegian budget.
 * 
 * <p>
 * This class has four attributes:
 * <ul>
 * <li>Individual Expense: The individual expense for the budget.</li>
 * <li>Individual Value: The individual value for the budget.</li>
 * <li>Household Expense: The household expense for the budget.</li>
 * <li>Household Value: The household value for the budget.</li>
 * </ul>
 * </p>
 * 
 * @author Group 04
 * @version 4/28/2023
 */
public class AverageNorwegianBudgetCell {
    private String individualExpense;
    private Integer individualValue;
    private String householdExpense;
    private Integer householdValue;

    /**
     * Constructs what will be represented as a cell in the average norwegian
     * budget.
     * 
     * @param individualExpense the individual expense for the budget.
     * @param individualValue   the individual value for the budget.
     * @param householdExpense  the household expense for the budget.
     * @param householdValue    the household value for the budget.
     */
    public AverageNorwegianBudgetCell(String individualExpense, Integer individualValue, String householdExpense,
            Integer householdValue) {
        this.individualExpense = individualExpense;
        this.individualValue = individualValue;
        this.householdExpense = householdExpense;
        this.householdValue = householdValue;
    }

    /**
     * Returns the individual expense for the budget.
     * 
     * @return the individual expense for the budget.
     */
    public String getIndividualExpense() {
        return this.individualExpense;
    }

    /**
     * Returns the individual value for the budget.
     * 
     * @return the individual value for the budget.
     */
    public Integer getIndividualValue() {
        return this.individualValue;
    }

    /**
     * Returns the household expense for the budget.
     * 
     * @return the household expense for the budget.
     */
    public String getHouseholdExpense() {
        return this.householdExpense;
    }

    /**
     * Returns the household value for the budget.
     * 
     * @return the household value for the budget.
     */
    public Integer getHouseholdValue() {
        return this.householdValue;
    }
}