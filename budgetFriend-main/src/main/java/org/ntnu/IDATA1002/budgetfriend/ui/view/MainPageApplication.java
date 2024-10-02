package org.ntnu.IDATA1002.budgetfriend.ui.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.ntnu.IDATA1002.budgetfriend.model.*;
import org.ntnu.IDATA1002.budgetfriend.ui.controllers.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Represents the main page application.
 *
 * <p>
 * This class will have four main functions:
 * <ul>
 * <li>Budget Calculator: Gives the user a recommended outcome for
 * expenses.</li>
 * <li>Budget: Creates, stores, edits and removes budgets.</li>
 * <li>Reciept Gallery: Stores images/reciepts in a gallery</li>
 * <li>Average Norwegain Budget: Displays the average norwegian budget for each
 * age and sex.</li>
 * </ul>
 * </p>
 *
 * @author Group 04
 * @version 4/28/2023
 */
public class MainPageApplication extends Application {

    private BudgetFriendController controller = new BudgetFriendController();
    private ReceiptGalleryController receiptGalleryController = new ReceiptGalleryController();
    private BudgetController budgetController;
    private FileHandlerController fileHandlerController;
    private AverageNorwegianBudgetController averageNorwegianBudgetController;
    private BudgetRegister budgetRegister;
    private ObservableList<Budget> budgetList;
    private TableView<Budget> budgetTableView;
    private TableView<AverageNorwegianBudgetCell> averageNorwegianBudgetTableView;
    private Stage primaryStage;
    private Scene scene;
    private Stage stage;
    private Scene budgetScene;
    private Scene calculatorScene;
    private Scene receiptScene;
    private Scene averageNorwegianBudgetScene;

    private static final String FEMALE = "Female";
    private static final String MALE = "Male";
    private static final String UNDER_EIGHTEEN = "Under 18";
    private static final String EIGHTEEN_THIRTY = "18 - 30";
    private static final String THIRTYONE_FORTYFIVE = "31 - 45";
    private static final String FORTYSIX_SIXTY = "46 - 60";
    private static final String OVER_SIXTY = "Over 60";

    @Override
    public void init() {
        budgetRegister = new BudgetRegister("Test");
        this.fillBudgetListWithDummyData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.budgetController = new BudgetController(this);
        this.fileHandlerController = new FileHandlerController(this);
        this.averageNorwegianBudgetController = new AverageNorwegianBudgetController(this);
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest((event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Exit Application ?");
            alert.setContentText("Are you sure you want to exit this application?\nAll unsaved changes will be lost.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    System.exit(0);
                    Platform.exit();
                }
            }
        }));

        try {
            this.fileHandlerController.createDataDirectory();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error creating data directory");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }

        Button button1 = new Button("Budget Calculator");
        button1.setId("startButtons");
        this.calculatorScene = createBudgetCalculatorWindow();
        button1.setOnAction(event -> primaryStage.setScene(calculatorScene));

        Button button2 = new Button("Budget");
        button2.setId("startButtons");
        this.budgetScene = createBudgetWindow();
        button2.setOnAction(event -> primaryStage.setScene(budgetScene));

        Button button3 = new Button("Receipt galley");
        button3.setId("startButtons");
        this.receiptScene = createReceiptWindow();
        button3.setOnAction(event -> primaryStage.setScene(receiptScene));

        Button button4 = new Button("Average \nNorwegian \nBudget");
        button4.setId("startButtons");
        button4.setTextAlignment(TextAlignment.CENTER);
        this.averageNorwegianBudgetScene = createAverageNorwegianWindow();
        button4.setOnAction(event -> primaryStage.setScene(averageNorwegianBudgetScene));

        HBox startButtons = new HBox(20);
        startButtons.getChildren().addAll(button1, button2, button3, button4);
        startButtons.setAlignment(Pos.CENTER);

        HBox topBar = new HBox(50);
        topBar.setPadding(new Insets(100, 0, 100, 0));
        topBar.getStyleClass().add("topBar");

        Button exitButton = new Button("EXIT");
        exitButton.setPadding(new Insets(5, 5, 5, 5));
        BorderPane.setAlignment(exitButton, Pos.TOP_RIGHT);
        BorderPane.setMargin(exitButton, new Insets(10, 10, 10, 10));

        exitButton.setOnAction(event -> controller.doExitApplication());

        BorderPane mainPage = new BorderPane();
        mainPage.setCenter(startButtons);
        mainPage.setTop(topBar);
        mainPage.setBottom(exitButton);

        this.scene = new Scene(mainPage, 1300, 700);
        scene.getStylesheets().add("/style.css");

        Image logo = new Image("budget_friend_logo.png");

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("Budget Friend");
        primaryStage.show();
    }

    /**
     * Represents the budget calculator in the application.
     */
    public Scene createBudgetCalculatorWindow() {

        Button helpButton = new Button("?");
        helpButton.setId("helpButton");
        helpButton.setOnAction(event -> budgetController.calculatorHelpDialog());

        Label welcomeLabel = new Label("Budget Calculator");
        HBox welcomeBox = new HBox(welcomeLabel, helpButton);
        welcomeLabel.getStyleClass().add("welcomeLabel");

        Label genderLabel = new Label("Select Your Gender:");
        RadioButton maleRadioButton = new RadioButton("Male");
        RadioButton femaleRadioButton = new RadioButton("Female");

        ToggleGroup genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(genderLabel, maleRadioButton, femaleRadioButton);

        Label ageLabel = new Label("Select Your Age Range:");
        ComboBox ageComboBox = new ComboBox<String>();
        ObservableList<String> ageRangeOptions = FXCollections.observableArrayList(
                "under 18", "18-30", "31-45", "46-60", "over 60");
        ageComboBox.setItems(ageRangeOptions);
        HBox ageBox = new HBox(ageLabel, ageComboBox);

        Label incomeLabel = new Label("Enter your monthly income after tax:");
        TextField incomeField = new TextField();
        BudgetCalculatorController.setNumericOnly(incomeField);
        VBox incomeBox = new VBox(incomeLabel, incomeField);
        incomeBox.setAlignment(Pos.CENTER_LEFT);

        Label foodExpensesLabel = new Label("Enter your food expenses:");
        TextField foodExpensesField = new TextField();
        BudgetCalculatorController.setNumericOnly(foodExpensesField);
        VBox foodExpensesBox = new VBox(foodExpensesLabel, foodExpensesField);
        foodExpensesBox.setAlignment(Pos.CENTER_LEFT);

        Label rentExpenseLabel = new Label("Enter your rent expenses:");
        TextField rentExpensesField = new TextField();
        BudgetCalculatorController.setNumericOnly(rentExpensesField);
        VBox rentBox = new VBox(rentExpenseLabel, rentExpensesField);
        rentBox.setAlignment(Pos.CENTER_LEFT);

        Label transportationExpensesLabel = new Label("Enter your transportation expenses:");
        TextField transportationExpensesField = new TextField();
        BudgetCalculatorController.setNumericOnly(transportationExpensesField);
        VBox transportationBox = new VBox(transportationExpensesLabel, transportationExpensesField);
        transportationBox.setAlignment(Pos.CENTER_LEFT);

        Label clothingExpensesLabel = new Label("Enter your clothing expenses:");
        TextField clothingExpensesField = new TextField();
        BudgetCalculatorController.setNumericOnly(clothingExpensesField);
        VBox clothingBox = new VBox(clothingExpensesLabel, clothingExpensesField);
        clothingBox.setAlignment(Pos.CENTER_LEFT);

        Label mediaExpensesLabel = new Label("Enter your media expenses:");
        TextField mediaExpensesField = new TextField();
        BudgetCalculatorController.setNumericOnly(mediaExpensesField);
        VBox mediaBox = new VBox(mediaExpensesLabel, mediaExpensesField);
        mediaBox.setAlignment(Pos.CENTER_LEFT);

        Label balanceLabel = new Label("Remaining Balance:");
        TextField balanceField = new TextField();
        balanceField.setEditable(false);
        VBox balanceBox = new VBox(balanceLabel, balanceField);
        balanceBox.setAlignment(Pos.CENTER_LEFT);

        Label recommendedFoodExpenseLabel = new Label("This is the recommended expense for food:");
        TextField recommendedFoodExpense = new TextField();
        recommendedFoodExpense.setEditable(false);
        VBox recommendedFoodExpenseBox = new VBox(recommendedFoodExpenseLabel, recommendedFoodExpense);
        recommendedFoodExpenseBox.setAlignment(Pos.CENTER_LEFT);

        Label recommendedClothingExpenseLabel = new Label("This is the recommended expense for clothing:");
        TextField recommendedClothingExpense = new TextField();
        recommendedClothingExpense.setEditable(false);
        VBox recommendedClothingBox = new VBox(recommendedClothingExpenseLabel, recommendedClothingExpense);
        recommendedClothingBox.setAlignment(Pos.CENTER_LEFT);

        Label recommendedMediaExpenseLabel = new Label("This is the recommended expense for media:");
        TextField recommendedMediaExpense = new TextField();
        recommendedMediaExpense.setEditable(false);
        VBox recommendedMediaBox = new VBox(recommendedMediaExpenseLabel, recommendedMediaExpense);
        recommendedMediaBox.setAlignment(Pos.CENTER_LEFT);

        Label recommendedTransportationExpensesLabel = new Label(
                "This is your recommended expense for transportation:");
        TextField recommendedTransportationExpense = new TextField();
        recommendedTransportationExpense.setEditable(false);
        VBox recommendedTransportationBox = new VBox(recommendedTransportationExpensesLabel,
                recommendedTransportationExpense);
        recommendedTransportationBox.setAlignment(Pos.CENTER_LEFT);

        Label recommendedSavingsLabel = new Label("This is your recommended amount for saving:");
        TextField recommendedSavings = new TextField();
        recommendedSavings.setEditable(false);
        VBox recommendedSavingsBox = new VBox(recommendedSavingsLabel, recommendedSavings);
        recommendedSavingsBox.setAlignment(Pos.CENTER_LEFT);

        BudgetCalculatorController BudgetCalculatorController = new BudgetCalculatorController(incomeField,
                foodExpensesField, rentExpensesField, transportationExpensesField,
                balanceField, recommendedFoodExpense, genderGroup, ageComboBox, clothingExpensesField,
                mediaExpensesField, recommendedClothingExpense, recommendedMediaExpense,
                recommendedTransportationExpense, recommendedSavings);

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(event -> BudgetCalculatorController.calculateButtonAction(maleRadioButton,
                femaleRadioButton, incomeField, foodExpensesField,
                rentExpensesField, transportationExpensesField, balanceField, recommendedFoodExpense,
                clothingExpensesField, mediaExpensesField, recommendedClothingExpense, recommendedMediaExpense,
                recommendedTransportationExpense, recommendedSavings));
        HBox calculateBox = new HBox(calculateButton);
        calculateButton.setPrefSize(200, 50);
        calculateButton.setPadding(new Insets(5, 5, 5, 5));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> BudgetCalculatorController.clearButton(incomeField, foodExpensesField,
                transportationExpensesField, rentExpensesField,
                balanceField, recommendedFoodExpense, ageComboBox, genderGroup, clothingExpensesField,
                mediaExpensesField, recommendedClothingExpense, recommendedMediaExpense,
                recommendedTransportationExpense, recommendedSavings));
        VBox clearBox = new VBox(clearButton);
        clearButton.setPrefSize(200, 50);
        clearButton.setPadding(new Insets(5, 5, 5, 5));

        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(event -> primaryStage.setScene(scene));
        mainMenu.setPrefSize(200, 50);
        mainMenu.setPadding(new Insets(5, 5, 5, 5));

        HBox bottomMenu = new HBox(10, clearButton, calculateButton, mainMenu);
        bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.setPadding(new Insets(10, 0, 10, 0));

        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.add(welcomeBox, 1, 0);
        gridPane.add(genderBox, 1, 2);
        gridPane.add(ageBox, 1, 3);
        gridPane.add(incomeBox, 1, 4);
        gridPane.add(rentBox, 1, 5);
        gridPane.add(foodExpensesBox, 1, 6);
        gridPane.add(transportationBox, 1, 7);
        gridPane.add(clothingBox, 1, 8);
        gridPane.add(mediaBox, 1, 9);

        gridPane.add(balanceBox, 3, 4);
        gridPane.add(recommendedFoodExpenseBox, 3, 5);
        gridPane.add(recommendedClothingBox, 3, 6);
        gridPane.add(recommendedMediaBox, 3, 7);
        gridPane.add(recommendedTransportationBox, 3, 8);
        gridPane.add(recommendedSavingsBox, 3, 9);

        gridPane.getStyleClass().add("gridPane");

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setBottom(bottomMenu);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setCenter(scrollPane);

        this.calculatorScene = new Scene(root, 1300, 700);
        calculatorScene.getStylesheets().add("/style.css");

        return calculatorScene;

    }

    /**
     * Creates the table to display all the budgets from the register.
     */
    public void createBudgetTable() {
        TableColumn<Budget, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Budget, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Budget, String> periodColumn = new TableColumn<>("Period");
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("period"));

        this.budgetTableView = new TableView<>();
        budgetTableView.setItems(this.getBudgetList());
        budgetTableView.getColumns().add(titleColumn);
        budgetTableView.getColumns().add(descriptionColumn);
        budgetTableView.getColumns().add(periodColumn);

        this.budgetTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        budgetTableView.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
                Budget selectedBudget = budgetTableView.getSelectionModel().getSelectedItem();
                budgetController.doShowDetails(selectedBudget);
            }
        });
    }

    /**
     * Creates the row of buttons. The buttons represent the different actions, who
     * can
     * be applied to a budget. Such as add, edit and delete.
     *
     * @return the button row as a HBox.
     */
    public HBox createBudgetButtonRow() {
        HBox buttonRow = new HBox(20);
        buttonRow.setAlignment(Pos.CENTER);

        Button readFileButton = new Button("Read budget file");
        readFileButton.setOnAction(event -> this.fileHandlerController.doOpenFile(event));

        Button createNewBudgetButton = new Button("Create new budget");
        createNewBudgetButton.setOnAction(event -> budgetController.doAddBudget(this.budgetRegister));

        Button editBudgetButton = new Button("Edit budget");
        editBudgetButton.setOnAction(event -> budgetController
                .doEditBudget(budgetTableView.getSelectionModel().getSelectedItem(), this.budgetRegister));

        Button deleteBudgetButton = new Button("Delete budget");
        deleteBudgetButton.setOnAction(event -> budgetController
                .doDeleteBudget(budgetTableView.getSelectionModel().getSelectedItem(), this.budgetRegister));

        Button saveFileButton = new Button("Save budget file");
        saveFileButton.setOnAction(event -> this.fileHandlerController.doSaveFile(this.budgetRegister));

        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(event -> primaryStage.setScene(scene));

        buttonRow.getChildren().addAll(readFileButton, createNewBudgetButton, editBudgetButton,
                deleteBudgetButton, saveFileButton, mainMenu);

        buttonRow.setPadding(new Insets(10, 10, 10, 10));

        return buttonRow;
    }

    /**
     * Creates the budget window.
     *
     * @return the scene for the budget.
     */
    public Scene createBudgetWindow() {

        BorderPane root = new BorderPane();
        VBox rootNode = new VBox();
        rootNode.setSpacing(20);
        root.setTop(rootNode);

        Label label = new Label("BUDGET  ");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Button helpButton = new Button("?");
        helpButton.setId("helpButton");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label, helpButton);
        hBox.setAlignment(Pos.CENTER);

        helpButton.setOnAction(e -> budgetController.doHelpDialogInformation());
        rootNode.getChildren().add(hBox);

        createBudgetTable();
        root.setCenter(budgetTableView);

        createBudgetButtonRow();
        root.setBottom(createBudgetButtonRow());

        Scene budgetScene = new Scene(root, 1300, 700);
        budgetScene.getStylesheets().add("/style.css");

        return budgetScene;
    }

    /**
     * A temporary method to test the actions for a budget.
     */
    public void fillBudgetListWithDummyData() {
        Budget march = new Budget("March 2022", "A budget for the month March", 31);
        march.addExpense(new Expense("Food", 200.0));
        march.addExpense(new Expense("Rent", 500.0));
        this.budgetRegister.addBudget(march);

        this.budgetRegister.addBudget(new Budget("April 2022", "A budget for the month April", 30));
        this.budgetRegister.addBudget(new Budget("May 2022", "A budget for the month May", 31));
        this.budgetRegister.addBudget(new Budget("June 2022", "A budget for the month June", 30));
        this.budgetRegister.addBudget(new Budget("July 2022", "A budget for the month July", 31));
        this.budgetRegister.addBudget(new Budget("August 2022", "A budget for the month August", 31));
    }

    /**
     * Creates a new scene representing the average norwegian budget.
     */
    public Scene createAverageNorwegianWindow() {
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Insets insets = new Insets(10, 10, 10, 10);
        vBox.setSpacing(20);

        Label head = new Label("AVERAGE NORWEGIAN BUDGET \n(For One Person & One Month)");
        head.getStyleClass().add("welcomeLabel");
        head.setPadding(insets);

        Button helpButton = new Button("?");
        helpButton.setId("helpButton");
        helpButton.setOnAction(event -> this.averageNorwegianBudgetController.doHelpDialogInformation());

        hBox.getChildren().addAll(head, helpButton);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setTop(hBox);
        BorderPane.setAlignment(hBox, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(hBox, insets);

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(event -> primaryStage.setScene(scene));
        mainMenuButton.setPrefSize(200, 50);
        mainMenuButton.setPadding(insets);
        borderPane.setBottom(mainMenuButton);
        BorderPane.setAlignment(mainMenuButton, Pos.TOP_RIGHT);
        BorderPane.setMargin(mainMenuButton, insets);

        Button viewButton = new Button("View Table");
        viewButton.setPrefSize(200, 50);
        viewButton.setPadding(insets);
        BorderPane.setMargin(viewButton, insets);

        ComboBox<String> ageComboBox = new ComboBox<>();
        ageComboBox.getItems().addAll(UNDER_EIGHTEEN, EIGHTEEN_THIRTY, THIRTYONE_FORTYFIVE, FORTYSIX_SIXTY, OVER_SIXTY);
        ageComboBox.setValue("- Choose Age -");
        ageComboBox.setPrefSize(200, 50);
        BorderPane.setMargin(ageComboBox, insets);

        Label ageLabel = new Label("(Age in Years)");
        ageLabel.setGraphic(ageComboBox);
        ageLabel.setContentDisplay(ContentDisplay.BOTTOM);

        ComboBox<String> sexComboBox = new ComboBox<>();
        sexComboBox.getItems().addAll(FEMALE, MALE);
        sexComboBox.setValue("- Choose Sex -");
        sexComboBox.setPrefSize(200, 50);
        BorderPane.setMargin(sexComboBox, insets);

        CheckBox infantCheckBox = new CheckBox();
        infantCheckBox.setPrefSize(50, 50);

        Label childLabel = new Label("Expecting/Have Childeren?");
        childLabel.setGraphic(infantCheckBox);
        childLabel.setContentDisplay(ContentDisplay.RIGHT);

        vBox.getChildren().addAll(ageLabel, ageComboBox, sexComboBox, childLabel, infantCheckBox, viewButton);
        vBox.setPadding(insets);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setRight(vBox);

        borderPane.setCenter(createAverageNorwegianTable());

        viewButton.setOnAction(event -> {
            switch (ageComboBox.getValue()) {
                case UNDER_EIGHTEEN:
                    switch (sexComboBox.getValue()) {
                        case FEMALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataUnderEighteenFemaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataUnderEighteenFemale());
                            }
                            break;

                        case MALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataUnderEighteenMaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataUnderEighteenMale());
                            }
                            break;

                        default:
                            this.averageNorwegianBudgetController.doSexDialogError();
                    }
                    break;

                case EIGHTEEN_THIRTY:
                    switch (sexComboBox.getValue()) {
                        case FEMALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataEighteenThirtyFemaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataEighteenThirtyoneFemale());
                            }
                            break;

                        case MALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataEighteenThirtyoneMaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataEighteenThirtyoneMale());
                            }
                            break;

                        default:
                            this.averageNorwegianBudgetController.doSexDialogError();
                    }
                    break;

                case THIRTYONE_FORTYFIVE:
                    switch (sexComboBox.getValue()) {
                        case FEMALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataThirtyoneFortyfiveFemaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataThirtyoneFortyfiveFemale());
                            }
                            break;

                        case MALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataThirtyoneFortyfiveMaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataThirtyoneFortyfiveMale());
                            }
                            break;

                        default:
                            this.averageNorwegianBudgetController.doSexDialogError();
                    }
                    break;

                case FORTYSIX_SIXTY:
                    switch (sexComboBox.getValue()) {
                        case FEMALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataFortyfiveSixtyFemaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataFortyfiveSixtyFemale());
                            }
                            break;

                        case MALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataFortyfiveSixtyMaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataFortyfiveSixtyMale());
                            }
                            break;

                        default:
                            this.averageNorwegianBudgetController.doSexDialogError();
                    }
                    break;

                case OVER_SIXTY:
                    switch (sexComboBox.getValue()) {
                        case FEMALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataSixtyAndAboveFemaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataSixtyAndAboveFemale());
                            }
                            break;

                        case MALE:
                            if (infantCheckBox.isSelected()) {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataSixtyAndAboveMaleInfantReady());
                            } else {
                                this.averageNorwegianBudgetTableView
                                        .setItems(AverageNorwegianBudgetData.dataSixtyAndAboveMale());
                            }
                            break;

                        default:
                            this.averageNorwegianBudgetController.doSexDialogError();
                    }
                    break;

                default:
                    this.averageNorwegianBudgetController.doAgeDialogError();
            }
        });

        Scene averageNorwegianBudgetWindow = new Scene(borderPane, 1300, 700);
        averageNorwegianBudgetWindow.getStylesheets().add("style.css");
        return averageNorwegianBudgetWindow;
    }

    /**
     * Creates and returns a tableView holding numbers representing the average
     * norwegian budget.
     *
     * @return a tableView holding numbers representing the average norwegian
     *         budget.
     */
    public TableView<AverageNorwegianBudgetCell> createAverageNorwegianTable() {
        this.averageNorwegianBudgetTableView = new TableView<>();
        this.averageNorwegianBudgetTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<AverageNorwegianBudgetCell, String> individualColumn = new TableColumn<>("Individual");

        TableColumn<AverageNorwegianBudgetCell, String> individualExpenseColumn = new TableColumn<>("Expense");
        individualExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("individualExpense"));

        TableColumn<AverageNorwegianBudgetCell, String> individualValueColumn = new TableColumn<>("Value (NOK)");
        individualValueColumn.setCellValueFactory(new PropertyValueFactory<>("individualValue"));

        TableColumn<AverageNorwegianBudgetCell, String> householdColumn = new TableColumn<>("Household");

        TableColumn<AverageNorwegianBudgetCell, String> householdExpenseColumn = new TableColumn<>("Expense");
        householdExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("householdExpense"));

        TableColumn<AverageNorwegianBudgetCell, String> householdValueColumn = new TableColumn<>("Value (NOK)");
        householdValueColumn.setCellValueFactory(new PropertyValueFactory<>("householdValue"));

        individualColumn.getColumns().addAll(individualExpenseColumn, individualValueColumn);
        householdColumn.getColumns().addAll(householdExpenseColumn, householdValueColumn);
        this.averageNorwegianBudgetTableView.getColumns().addAll(individualColumn, householdColumn);

        return this.averageNorwegianBudgetTableView;
    }

    /**
     * Creates a new scene representing the receipt gallery.
     *
     * @return receiptScene
     */
    public Scene createReceiptWindow() {
        GridPane imagePane = receiptGalleryController.imagePane;
        ScrollPane scrollPane = new ScrollPane(imagePane);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        Scene receiptScene = new Scene(root, 1300, 700);
        receiptScene.getStylesheets().add("/style.css");

        Label label = new Label("RECEIPT GALLERY  ");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Button helpButton = new Button("?");
        helpButton.setId("helpButton");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label, helpButton);
        hBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add a receipt to the gallery");
        VBox vBox = new VBox(15);
        vBox.getChildren().addAll(hBox, addButton);
        root.setTop(vBox);
        vBox.setAlignment(Pos.CENTER);

        helpButton.setOnAction(e -> receiptGalleryController.helpPopUp());

        addButton.setOnAction(e -> receiptGalleryController.chooseImageFile());

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setCenter(scrollPane);
        BorderPane.setMargin(scrollPane, new Insets(10, 10, 10, 10));

        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(event -> primaryStage.setScene(scene));
        root.setBottom(mainMenu);
        BorderPane.setAlignment(mainMenu, Pos.TOP_RIGHT);

        return receiptScene;
    }

    /**
     * Returns the budget register as a list.
     *
     * @return the budget register as a list.
     */
    public ObservableList<Budget> getBudgetList() {
        budgetList = FXCollections.observableArrayList(this.budgetRegister.getBudgetList());
        return budgetList;
    }

    /**
     * Updates the budget list.
     */
    public void updateObservableList() {
        this.budgetList.setAll(this.budgetRegister.getBudgetList());
    }

    /**
     * Updates the budget table with a new budget list.
     * 
     * @param budgetList the budget list.
     */
    public void updateBudgetTable(List<Budget> budgetList) {
        ObservableList<Budget> budgetObservableList = FXCollections.observableArrayList((budgetList));
        this.budgetTableView.setItems(budgetObservableList);
    }

    /**
     * Updates the budget register.
     *
     * @param budgetRegister the budget register.
     */
    public void updateBudgetRegister(BudgetRegister budgetRegister) {
        this.budgetRegister = budgetRegister;
    }

    /**
     * Returns the stage.
     * 
     * @return the stage.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * The main starting point of the application. The operating system
     * of the computer expects to find a publicly available method it can
     * call without having to create objects first.
     * 
     * @param args a fixed size array of Strings holding arguments provided
     *             from the command line during the startup of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}