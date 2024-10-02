package org.ntnu.IDATA1002.budgetfriend.ui.controllers;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * This is the controller class for the Budget Calculator
 *
 * <p>
 * This class defines all the methods and the calculations from the calculator
 * <p>
 * The calculations are based upon certain rules but are open to be adjusted
 * 
 * </p>
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class BudgetCalculatorController {
    private ComboBox<String> ageComboBox;
    private ToggleGroup genderGroup;
    public TextField incomeField;
    public TextField foodExpensesField;
    private TextField balanceField;
    private TextField rentExpensesField;
    private TextField transportationExpensesField;
    private TextField recommendedFoodExpenseField;
    private TextField clothingExpensesField;
    private TextField mediaExpensesField;
    private TextField recommendedClothingExpense;
    private TextField recommendedMediaExpense;
    private TextField recommendedTransportationExpense;
    private TextField recommendedSavings;

    /**
     * 
     */
    public class NegativeNumberException extends Exception {
        public NegativeNumberException(String message) {
            super(message);
        }
    }

    /**
     * 
     * 
     * @param textField
     */
    public static void setNumericOnly(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }

    /**
     * 
     */
    public class GenderNotSelectedException extends Exception {
        public GenderNotSelectedException(String message) {
            super(message);
        }
    }

    /**
     * 
     */
    public class AgeRangeNotSelectedException extends Exception {
        public AgeRangeNotSelectedException(String message) {
            super(message);
        }
    }

    /**
     * 
     * 
     * @param incomeField
     * @param foodExpensesField
     * @param rentExpensesField
     * @param transportationExpensesField
     * @param balanceField
     * @param recommendedFoodExpenseField
     * @param genderGroup
     * @param ageComboBox
     * @param clothingExpensesField
     * @param mediaExpensesField
     * @param recommendedClothingExpense
     * @param recommendedMediaExpense
     * @param recommendedTransportationExpense
     * @param recommendedSavings
     */
    public BudgetCalculatorController(TextField incomeField, TextField foodExpensesField,
            TextField rentExpensesField, TextField transportationExpensesField,
            TextField balanceField, TextField recommendedFoodExpenseField,
            ToggleGroup genderGroup, ComboBox<String> ageComboBox, TextField clothingExpensesField,
            TextField mediaExpensesField, TextField recommendedClothingExpense,
            TextField recommendedMediaExpense, TextField recommendedTransportationExpense,
            TextField recommendedSavings) {

        this.incomeField = incomeField;
        this.mediaExpensesField = mediaExpensesField;
        this.foodExpensesField = foodExpensesField;
        this.rentExpensesField = rentExpensesField;
        this.transportationExpensesField = transportationExpensesField;
        this.balanceField = balanceField;
        this.recommendedMediaExpense = recommendedMediaExpense;
        this.recommendedFoodExpenseField = recommendedFoodExpenseField;
        this.genderGroup = genderGroup;
        this.ageComboBox = ageComboBox;
        this.clothingExpensesField = clothingExpensesField;
        this.recommendedClothingExpense = recommendedClothingExpense;
        this.recommendedTransportationExpense = recommendedTransportationExpense;
        this.recommendedSavings = recommendedSavings;

    }

    /**
     * 
     * @param incomeField
     * @param foodExpensesField
     * @param rentExpensesField
     * @param transportationExpensesField
     * @param balanceField
     * @param recommendedFoodExpense
     * @param ageComboBox
     * @param genderGroup
     * @param clothingExpensesField
     * @param mediaExpensesField
     * @param RecommendedClothingExpense
     * @param recommendedMediaExpense
     * @param recommendedTransportationExpense
     * @param recommendedSavings
     */
    public void clearButton(TextField incomeField, TextField foodExpensesField,
            TextField rentExpensesField, TextField transportationExpensesField,
            TextField balanceField, TextField recommendedFoodExpense, ComboBox<String> ageComboBox,
            ToggleGroup genderGroup,
            TextField clothingExpensesField, TextField mediaExpensesField, TextField RecommendedClothingExpense,
            TextField recommendedMediaExpense, TextField recommendedTransportationExpense,
            TextField recommendedSavings) {
        incomeField.clear();
        foodExpensesField.clear();
        rentExpensesField.clear();
        transportationExpensesField.clear();
        balanceField.clear();
        recommendedMediaExpense.clear();
        recommendedFoodExpense.clear();
        clothingExpensesField.clear();
        mediaExpensesField.clear();
        genderGroup.selectToggle(null);
        RecommendedClothingExpense.clear();
        ageComboBox.getSelectionModel().clearSelection();
        recommendedTransportationExpense.clear();
        recommendedSavings.clear();

    }

    /**
     * 
     * 
     * @param maleRadioButton
     * @param femaleRadioButton
     * @param incomeField
     * @param foodExpensesField
     * @param rentExpensesField
     * @param transportationExpensesField
     * @param balanceField
     * @param recommendedFoodExpense
     * @param clothingExpensesField
     * @param mediaExpensesField
     * @param recommendedMediaExpense
     * @param recommendedClothingExpense
     * @param recommendedTransportationExpense
     * @param recommendedSavings
     */
    public void calculateButtonAction(RadioButton maleRadioButton, RadioButton femaleRadioButton, TextField incomeField,
            TextField foodExpensesField,
            TextField rentExpensesField, TextField transportationExpensesField, TextField balanceField,
            TextField recommendedFoodExpense,
            TextField clothingExpensesField, TextField mediaExpensesField, TextField recommendedMediaExpense,
            TextField recommendedClothingExpense, TextField recommendedTransportationExpense,
            TextField recommendedSavings) {
        try {
            if (!(maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
                throw new GenderNotSelectedException("Please select a gender.");
            }
            if (ageComboBox.getValue() == null) {
                throw new AgeRangeNotSelectedException("Please select an age range.");
            }

            double income = Double.parseDouble(incomeField.getText());
            double mediaExpenses = Double.parseDouble(mediaExpensesField.getText());
            double clothingExpenses = Double.parseDouble(clothingExpensesField.getText());
            double foodExpenses = Double.parseDouble(foodExpensesField.getText());
            double rentExpenses = Double.parseDouble(rentExpensesField.getText());
            double transportationExpenses = Double.parseDouble(transportationExpensesField.getText());

            double totalExpenses = foodExpenses + rentExpenses + transportationExpenses + clothingExpenses
                    + mediaExpenses;
            double balance = income - totalExpenses;

            double totalExpensesWithoutFood = rentExpenses + transportationExpenses + clothingExpenses + mediaExpenses;

            double recommendedTransportation = (income - totalExpenses + transportationExpenses) * 0.25;
            double recommendedFoodExpensesField = (income - totalExpensesWithoutFood) * 0.6;
            double recommendedMedia = (income - totalExpenses + mediaExpenses) * 0.1;
            double recommendedClothing = (income - totalExpenses + clothingExpenses) * 0.2;
            double savings = (income - totalExpenses) * 0.1;

            final double maxRecommendedFoodExpense = 15000;
            if (recommendedFoodExpensesField > maxRecommendedFoodExpense) {
                recommendedFoodExpensesField = maxRecommendedFoodExpense;
            }
            final double maxRecommendedTransportationExpense = 15000;
            if (recommendedTransportation > maxRecommendedTransportationExpense) {
                recommendedTransportation = maxRecommendedTransportationExpense;
            }
            final double maxRecommendedMediaExpense = 8000;
            if (recommendedMedia > maxRecommendedMediaExpense) {
                recommendedMedia = maxRecommendedMediaExpense;
            }
            final double maxRecommendedClothingExpense = 15000;
            if (recommendedClothing > maxRecommendedClothingExpense) {
                recommendedClothing = maxRecommendedClothingExpense;
            }
            final double maxRecommendedSavingAmount = 30000;
            if (savings > maxRecommendedSavingAmount) {
                savings = maxRecommendedSavingAmount;
            }
            final double minRecommendedSavings = 0;
            if (savings < minRecommendedSavings) {
                savings = minRecommendedSavings;
            }
            if (income < 0 || mediaExpenses < 0 || clothingExpenses < 0 || foodExpenses < 0 || rentExpenses < 0
                    || transportationExpenses < 0) {
                throw new NegativeNumberException("Input values cannot be negative!");
            }

            String gender = "";
            if (maleRadioButton.isSelected()) {
                gender = "Male";
                recommendedFoodExpensesField *= 1;
                recommendedMedia *= 1.1;
                recommendedClothing *= 0.75;

            } else if (femaleRadioButton.isSelected()) {
                gender = "Female";
                recommendedFoodExpensesField *= 0.8;
                recommendedMedia *= 0.9;
                recommendedClothing *= 1.5;

            }

            String ageRangeOptions = ageComboBox.getValue();
            switch (ageRangeOptions) {
                case "Under 18":
                    recommendedFoodExpensesField *= 1.2;
                    recommendedMedia *= 0.3;
                    recommendedClothing *= 0.5;

                    break;
                case "18-30":
                    recommendedFoodExpensesField *= 0.9;
                    recommendedMedia *= 1.7;
                    recommendedClothing *= 1.6;

                    break;
                case "31-45":
                    recommendedFoodExpensesField *= 0.8;
                    recommendedMedia *= 1.3;
                    recommendedClothing *= 1.1;

                    break;
                case "46-60":
                    recommendedFoodExpensesField *= 0.74;
                    recommendedMedia *= 1;
                    recommendedClothing *= 1;

                    break;
                case "over 60":
                    recommendedFoodExpensesField *= 0.7;
                    recommendedMedia *= 0.5;
                    recommendedClothing *= 1;

                    break;
            }

            if (balance < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Your balance is negative. Maybe you should sign up for Luksusfellen?", ButtonType.OK);
                alert.showAndWait();
            }

            balanceField.setText(String.format("%.2f", balance));
            recommendedFoodExpense.setText(String.format("%.2f", recommendedFoodExpensesField));
            recommendedMediaExpense.setText(String.format("%.2f", recommendedMedia));
            recommendedClothingExpense.setText(String.format("%.2f", recommendedClothing));
            recommendedTransportationExpense.setText(String.format("%.2f", recommendedTransportation));
            recommendedSavings.setText(String.format("%.2f", savings));

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for all fields!",
                    ButtonType.OK);
            alert.showAndWait();
        } catch (NegativeNumberException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (GenderNotSelectedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (AgeRangeNotSelectedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
