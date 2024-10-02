package org.ntnu.IDATA1002.budgetfriend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BudgetTest {
    @Test
    public void creationOfInstanceWithValidArguments(){
        Budget budget = new Budget("BudgetName","BudgetDescription",31);
        assertEquals("BudgetName",budget.getTitle());
        assertEquals("BudgetDescription",budget.getDescription());
        assertEquals(31,budget.getPeriod());
    }

    @Test
    public void creationOfInstanceWithBudgetNameAndBudgetDescriptionAreEmptyIsInvalid(){
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            Budget budget = new Budget("","BudgetDescription",31);
                        });
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> {
                    Budget budget = new Budget("BudgetName","",31);
                });
    }

    @Test
    public void creationOfInstanceWithBudgetNameAndBudgetDescriptionAreBlankIsInvalid(){
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            Budget budget = new Budget("     ","BudgetDescription",31);
                        });

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> {
                    Budget budget = new Budget("BudgetName","   ",31);
                });
    }

    @Test
    public void creationOfInstanceWithBudgetNameAndBudgetDescriptionAreNullIsInvalid(){
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            Budget budget = new Budget(null,"BudgetDescription",31);
                        });
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> {
                    Budget budget = new Budget("BudgetName",null,31);
                });
    }

    @Test
    public void creationOfInstanceWithBudgetPeriodIsNegativeIsInvalid(){
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            Budget budget = new Budget("BudgetName","BudgetDescription",-31);
                        });

    }

    @Test
    public void creationOfAnExpenseWithExpenseNameIsEmptyIsInvalid(){
        Budget budget = new Budget("BudgetName","BudgetDescription",31);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            budget.addExpense(new Expense("",2000.0));
                        });
    }

    @Test
    public void creationOfAnExpenseWithExpenseNameIsBlankIsInvalid(){
        Budget budget = new Budget("BudgetName","BudgetDescription",31);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            budget.addExpense(new Expense("    ",2000.0));
                        });
    }

    @Test
    public void creationOfAnExpenseWithExpenseNameIsNullIsInvalid(){
        Budget budget = new Budget("BudgetName","BudgetDescription",31);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            budget.addExpense(new Expense(null, 2000.0));
                        });
    }

    @Test
    public void creationOfAnExpenseWithExpenseIsNegativeIsInvalid(){
        Budget budget = new Budget("BudgetName","BudgetDescription",31);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        ()-> {
                            budget.addExpense(new Expense("ExpenseName",-2000.0));
                        });
    }
}