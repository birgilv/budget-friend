package org.ntnu.IDATA1002.budgetfriend.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a budget register. This register contains the created budgets.
 *
 * @author Group 04
 * @version 4/28/2023
 */

public class BudgetRegister {
    private String title;

    /**
     * The collection of budget instances.
     */
    private final ArrayList<Budget> budgetRegisterList;

    /**
     * Creates an instance of the budget-register.
     */
    public BudgetRegister(String title) {
        this.setTitle(title);
        this.budgetRegisterList = new ArrayList<>();
    }

    /**
     * Sets the title of the budget register.
     * 
     * @param title the title of the budget register.
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Budget register title cannot be null or blank.");
        }
        this.title = title;
    }

    /**
     * Returns the title of the budget register.
     * 
     * @return the title of the budget register.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the list of budgets.
     *
     * @return the list of budgets.
     */
    public List<Budget> getBudgetList() {
        return this.budgetRegisterList;
    }

    /**
     * Add a budget instance to be added.
     *
     * @param budget the budget to add
     */
    public void addBudget(Budget budget) {
        this.budgetRegisterList.add(budget);
    }

    /**
     * Returns the first instance of Budget with a name matching the name
     * provided.
     *
     * @param title the title to search for
     * @return returns a Budget object if a match is found. If not,
     *         <code>null</code> is returned.
     */
    public Budget findBudgetByTitle(String title) {
        Budget result = null;
        boolean foundBudget = false;
        Iterator<Budget> it = this.budgetRegisterList.iterator();
        while (it.hasNext() && !foundBudget) {
            Budget budget = it.next();
            if (budget.getTitle().equals(title)) {
                foundBudget = true;
                result = budget;
            }
        }
        return result;
    }

    /**
     * Returns the number of budgets in the budget register.
     *
     * @return the number of budgets in the budget register.
     */
    public int getSize() {
        return this.budgetRegisterList.size();
    }

    /**
     * Removes the budget provided by the parameter, from the register.
     * Returns <code>true</code> is remove was successful.
     *
     * @param budget the budget object to remove
     * @return <code>true</code> is remove was successful
     */
    public boolean remove(Budget budget) {
        return this.budgetRegisterList.remove(budget);
    }

}
