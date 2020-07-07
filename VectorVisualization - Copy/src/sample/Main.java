package sample;
//TODO Refactor whole project
//TODO NEXT : Colour Picker
//TODO NEXT : Pencil
//TODO NEXT : Rectangle
//TODO NEXT : Circle

import Exceptions.IncorrectInputException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private static final int RIGHT_H_GAP = 0;
    private static final int LEFT_H_GAP = 5;
    private static final int GRAPH_WIDTH = 1000;
    private static final int GRAPH_HEIGHT = 700;
    private static final int ROOT_WIDTH = GRAPH_WIDTH + Math.max(LEFT_H_GAP, RIGHT_H_GAP);
    private static final int ROOT_HEIGHT = GRAPH_HEIGHT + 128;
    private static final int GRAPH_SIZE = 200;
    private static final int GRAPH_BOX_SIZE = GRAPH_HEIGHT / GRAPH_SIZE;
    private static final int GAP_BEFORE_GRAPH_START = 5;
    private static final int INPUT_BAR_LEFT_V_GAP = 8;
    private static final int CENTERING_LINE_ON_BORDER = 1;
    private static final int MENU_BAR_HEIGHT = 25;
    private static final int SCREEN_MAX_WIDTH = 1920;
    private static Pane UI = new Pane();
    private static Group graphPane;
    private static Group drawingsGroup = new Group();
    private TextField vector_x1;
    private TextField vector_y1;
    private TextField vector_x2;
    private TextField vector_y2;
    private Label vector_angle;
    private Label vector_magnitude;
    private Label mouse_pointer;
    private Label graph_size;
    private ArrayList<Vector> currentGraphVectors = new ArrayList<>();
    private static final int TEXT_FIELD_SIZE = 50;
    private Stage primaryStage;
    private static final int HOME_TOOLBAR_HEIGHT = 28;
    private ToggleGroup toggleGroup1;
    private ToggleGroup toggleGroup2;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint Application");
        createUI();
        Scene scene = new Scene(UI, ROOT_WIDTH, ROOT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    private void createUI() {
        createGraph();
    }

    private void createGraph() {
        createMenuToolbar();
        createHomeToolbar();
        createGraphPane();
        createGrid(false);
        setDefaultValues();
        createBottomToolbar();
    }

    private void createMenuToolbar() {
        Menu menu = createFileMenu();
        MenuBar menuBar = createMenuBar(menu);
        addMenuBar(menuBar);
    }

    private Menu createFileMenu() {
        Menu menu = new Menu("File");
        MenuItem newGraph = createNewGraphMenuItem();
        MenuItem openGraph = createOpenGraphMenuItem();
        MenuItem saveGraph = createSaveGraphMenuItem();
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((event) -> Platform.exit());
        menu.getItems().addAll(newGraph, openGraph, saveGraph, exit);
        return menu;
    }

    private MenuItem createNewGraphMenuItem() {
        MenuItem newGraph = new MenuItem("New");
        newGraph.setOnAction(new NewGraph());
        return newGraph;
    }

    private MenuItem createOpenGraphMenuItem() {
        MenuItem openGraph = new MenuItem("Open Existing Graph");
        openGraph.setOnAction(new OpenGraph());
        return openGraph;
    }

    private MenuItem createSaveGraphMenuItem() {
        MenuItem saveGraph = new MenuItem("Save Graph");
        saveGraph.setOnAction(new SaveGraph());
        return saveGraph;
    }

    private MenuBar createMenuBar(Menu menu) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(UI.widthProperty());
        menuBar.paddingProperty().setValue(Insets.EMPTY);
        return menuBar;
    }

    private void addMenuBar(MenuBar menuBar) {
        FlowPane menuBarPane = new FlowPane();
        menuBarPane.setMinWidth(ROOT_WIDTH);
        menuBarPane.getChildren().add(menuBar);
        UI.getChildren().add(menuBarPane);
    }

    private void createHomeToolbar() {
        FlowPane homeToolbar = new FlowPane();
        homeToolbar.setHgap(10);
        homeToolbar.setPadding(new Insets(1,0,1,0));
        homeToolbar.setPrefWidth(SCREEN_MAX_WIDTH);
        homeToolbar.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1,0))));
        homeToolbar.setLayoutY(MENU_BAR_HEIGHT);
        toggleGroup1 = new ToggleGroup();
        RadioButton button = createPencilButton();
        RadioButton create = createLineButton();
        RadioButton rectangle = createRectangleButton();
        toggleGroup1.getToggles().addAll(button,create,rectangle);
        toggleGroup1.selectToggle(button);

        RadioButton colourRed = createRedButton();
        RadioButton colourGreen = createGreenButton();
        RadioButton colourBlue = createBlueButton();

        toggleGroup2 = new ToggleGroup();
        toggleGroup2.getToggles().addAll(colourRed, colourGreen, colourBlue);
        toggleGroup2.selectToggle(colourRed);

        for(Toggle control : toggleGroup1.getToggles())
        {
            homeToolbar.getChildren().add((RadioButton) control);
        }

        for(Toggle control : toggleGroup2.getToggles())
        {
            homeToolbar.getChildren().add((RadioButton) control);
        }
        homeToolbar.setStyle("-fx-faint-focus-color: transparent;");
        UI.getChildren().add(homeToolbar);
    }

    private RadioButton createPencilButton() {
        RadioButton button = new RadioButton("Pencil");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createLineButton() {
        RadioButton button = new RadioButton("Line");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createRectangleButton() {
        RadioButton button = new RadioButton("Rectangle");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createRedButton() {
        RadioButton button = new RadioButton("Red");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createGreenButton() {
        RadioButton button = new RadioButton("Green");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createBlueButton() {
        RadioButton button = new RadioButton("Blue");
        applyRadioButtonCss(button);
        return button;
    }

    private void applyRadioButtonCss(RadioButton button) {
        button.getStyleClass().remove("radio-button");
        button.getStyleClass().add("toggle-button");
        button.setStyle("-fx-focus-color:transparent;");
    }

    private void createGraphPane() {
        graphPane = new Group();
        graphPane.setLayoutX(LEFT_H_GAP);
        graphPane.setLayoutY(GAP_BEFORE_GRAPH_START);
        graphPane.setOnMouseMoved((mouseEvent) -> {
            mouse_pointer.setText((mouseEvent.getSceneX() - LEFT_H_GAP) + ", " + (mouseEvent.getSceneY() - GAP_BEFORE_GRAPH_START));
        });
        graphPane.setOnMouseClicked((mouseEvent) ->{
            if(((ToggleButton)toggleGroup1.getSelectedToggle()).getText().equals("Pencil")){
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                try {
                    Vector vector = new Vector(x,y,x+1,y+1);
                    currentGraphVectors.add(vector);
                    drawOnGraph(vector);
                    System.out.println(currentGraphVectors.toString());
                } catch (IncorrectInputException e) {
                    System.out.println("Error adding vector - negative numbers");
                }
            }
        });

        graphPane.setOnMouseDragged((mouseEvent) ->{
            if(((ToggleButton)toggleGroup1.getSelectedToggle()).getText().equals("Pencil")){

                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                if((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)){
                    try {
                        Vector vector = new Vector(x, y, x + 1, y + 1);
                        currentGraphVectors.add(vector);
                        drawOnGraph(vector);
                        System.out.println(currentGraphVectors.toString());
                    } catch (IncorrectInputException e) {
                        System.out.println("Error adding vector - negative numbers");
                    }
                }
            }
        });
    }

    private void createGrid(boolean hasGridLines) {

        if (hasGridLines) {
            createGraphGrid(graphPane);
        }
        else{
            Rectangle rectangle = new Rectangle(0,0,1000,700);
            rectangle.setFill(Color.WHITE);
            graphPane.getChildren().add(rectangle);
        }
        graphPane.getChildren().add(drawingsGroup);
        StackPane graphContainer = new StackPane();
        graphContainer.setPrefWidth(SCREEN_MAX_WIDTH);
        graphContainer.setLayoutY(MENU_BAR_HEIGHT + HOME_TOOLBAR_HEIGHT);
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgb(197, 207, 223);");
        pane.getChildren().add(graphPane);
        graphContainer.getChildren().add(pane);
        UI.getChildren().add(graphContainer);
    }

    private void createGraphGrid(Group graphPane) {
        for (int x = 0; x < GRAPH_WIDTH; x += GRAPH_BOX_SIZE) {
            for (int y = 0; y < GRAPH_HEIGHT; y += GRAPH_BOX_SIZE) {
                GraphGridBox graphGridBox = new GraphGridBox(x, y, GRAPH_BOX_SIZE);
                graphPane.getChildren().add(graphGridBox);
            }
        }
    }

    private void setDefaultValues() {
        initializeTextFields();
        initializeLabels();
        initializeLabelsRange();
        setTextFieldsRange();
        setLabelsRange();
        setDefaultLabelValues();
    }

    private void initializeTextFields() {
        vector_x1 = new TextField();
        vector_y1 = new TextField();
        vector_x2 = new TextField();
        vector_y2 = new TextField();
    }

    private void initializeLabels() {
        vector_angle = new Label();
        vector_magnitude = new Label();
        mouse_pointer = new Label();
        graph_size = new Label();
    }

    private void initializeLabelsRange() {
        vector_angle.setMinSize(40, 15);
        vector_angle.setMaxSize(40, 15);
        vector_magnitude.setMinSize(40, 15);
        vector_magnitude.setMaxSize(80, 15);
    }

    private void setTextFieldsRange() {
        vector_x1.setMaxWidth(TEXT_FIELD_SIZE);
        vector_y1.setMaxWidth(TEXT_FIELD_SIZE);
        vector_x2.setMaxWidth(TEXT_FIELD_SIZE);
        vector_y2.setMaxWidth(TEXT_FIELD_SIZE);
    }

    private void setLabelsRange() {
        mouse_pointer.setMinSize(40, 15);
        mouse_pointer.setMaxSize(80, 15);
        mouse_pointer.setMinSize(40, 15);
        mouse_pointer.setMaxSize(80, 15);
    }

    private void setDefaultLabelValues() {
        graph_size.setText(GRAPH_WIDTH + ", " + GRAPH_HEIGHT);
    }

    private void createBottomToolbar() {
        UI.getChildren().add(createInformationBar(MENU_BAR_HEIGHT + GRAPH_HEIGHT + HOME_TOOLBAR_HEIGHT + GAP_BEFORE_GRAPH_START + INPUT_BAR_LEFT_V_GAP));
    }

    private void addVectorToGraph(double x1, double y1, double x2, double y2, boolean fromFile) {
        if (!fromFile) {
            try {
                x1 = Double.parseDouble(vector_x1.getText());
                y1 = Double.parseDouble(vector_y1.getText());
                x2 = Double.parseDouble(vector_x2.getText());
                y2 = Double.parseDouble(vector_y2.getText());
            } catch (NumberFormatException e) {
                System.out.println("Incorrect Input, Please enter numbers");
                return;
            }
        }
        addLineToGraph(x1, y1, x2, y2);
    }

    private void addLineToGraph(double x1, double y1, double x2, double y2) {
        try {
            Vector vector = new Vector(x1, y1, x2, y2);
            currentGraphVectors.add(vector);
            drawOnGraph(vector);
        } catch (IncorrectInputException e) {
            System.out.println("Error adding vector - negative numbers");
        }
    }

    private void drawOnGraph(Vector vector) {
        Line line = new Line(vector.getX1(), GRAPH_HEIGHT - vector.getY1() + CENTERING_LINE_ON_BORDER
                , vector.getX2(), GRAPH_HEIGHT - vector.getY2() + CENTERING_LINE_ON_BORDER);
        line.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        line.setStrokeWidth(1);
        drawingsGroup.getChildren().add(line);
    }


    private void clearGraph() {
        drawingsGroup.getChildren().clear();
    }

    private GridPane createInformationBar(double yPosition) {
        GridPane informationBar = new GridPane();
        setDefaultValues(yPosition, informationBar);
        addLabels(informationBar);
        return informationBar;
    }

    private void setDefaultValues(double yPosition, GridPane informationBar) {
        informationBar.setLayoutX(LEFT_H_GAP);
        informationBar.setLayoutY(yPosition + 40);
        informationBar.setHgap(50);
    }

    private void addLabels(GridPane informationBar) {
        informationBar.add(new Text("Graph Size"), 0, 1);
        informationBar.add(graph_size, 1, 1);
        informationBar.add(new Text("Pointer"), 2, 1);
        informationBar.add(mouse_pointer, 3, 1);
    }

    class NewGraph implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create new graph? Current Graph will be deleted?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                clearGraph();
            }
        }
    }

    class OpenGraph implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            File workingDirectory = new File(System.getProperty("user.dir"));
            FileChooser fileChooser = new FileChooser();
            System.out.println(workingDirectory);
            fileChooser.setInitialDirectory(workingDirectory);
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile == null) {
                return;
            }
            System.out.println("Opening saved graph");
            try (Scanner scanner = new Scanner(new FileReader(selectedFile.getPath()))) {
                scanner.useDelimiter("\n");
                clearGraph();
                while (scanner.hasNext()) {
                    String[] savedVector = scanner.next().split(",");
                    Vector vector = new Vector(Double.parseDouble(savedVector[0]), Double.parseDouble(savedVector[1]),
                            Double.parseDouble(savedVector[2]), Double.parseDouble(savedVector[3]));
                    addVectorToGraph(vector.getX1(), vector.getY1(), vector.getX2(), vector.getY2(), true);
                }
                System.out.println("Successfully opened file");
            } catch (NumberFormatException e) {
                System.out.println("Saved file contains non-numbers");
            } catch (IOException e) {
                System.out.println("Error while opening file");
            } catch (IncorrectInputException e) {
                System.out.println("Incorrect input in saved file");
            }
        }
    }

    class SaveGraph implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            File workingDirectory = new File(System.getProperty("user.dir"));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(workingDirectory);
            File selectedFolder = directoryChooser.showDialog(primaryStage);
            if (selectedFolder == null) {
                return;
            }
            System.out.println("Saving graph");
            try (FileWriter writer = new FileWriter(selectedFolder.getPath() + "/graph.dat")) {
                for (Vector vector : currentGraphVectors) {
                    writer.write(vector.getX1() + ", " + vector.getY1()
                            + ", " + vector.getX2() + ", " + vector.getY2() + "\n");
                }
                System.out.println("File successfully saved");
            } catch (IOException e) {
                System.out.println("Error Saving");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

