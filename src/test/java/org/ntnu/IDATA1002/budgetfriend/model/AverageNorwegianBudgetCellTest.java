package org.ntnu.IDATA1002.budgetfriend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AverageNorwegianBudgetCellTest 
{
    @Test
    public void testCreationOfAverageNorwegianBudgetCell()
    {
        AverageNorwegianBudgetCell averageNorwegianBudgetCell = new AverageNorwegianBudgetCell("Individual Expense", 0, "Household Expense", 1);

        assertEquals("Individual Expense", averageNorwegianBudgetCell.getIndividualExpense());
        assertEquals(0, averageNorwegianBudgetCell.getIndividualValue());
        assertEquals("Household Expense", averageNorwegianBudgetCell.getHouseholdExpense());
        assertEquals(1, averageNorwegianBudgetCell.getHouseholdValue());
    }

    @Test
    public void testCreationOfAverageNorwegianBudgetCellNull()
    {
        AverageNorwegianBudgetCell averageNorwegianBudgetCell = new AverageNorwegianBudgetCell(null, null, null, null);

        assertEquals(null, averageNorwegianBudgetCell.getIndividualExpense());
        assertEquals(null, averageNorwegianBudgetCell.getIndividualValue());
        assertEquals(null, averageNorwegianBudgetCell.getHouseholdExpense());
        assertEquals(null, averageNorwegianBudgetCell.getHouseholdValue());
    }
}