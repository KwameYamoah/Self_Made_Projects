package main;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private static final int RIGHT_H_GAP = 0;
    private static final int LEFT_H_GAP = 5;
    private static final int GRAPH_WIDTH = 1001;
    private static final int GRAPH_HEIGHT = 701;
    private static final int ROOT_WIDTH = GRAPH_WIDTH + Math.max(LEFT_H_GAP, RIGHT_H_GAP);
    private static final int ROOT_HEIGHT = GRAPH_HEIGHT + 128;
    private static final int GRAPH_SIZE = 200;
    private static final int GRAPH_BOX_SIZE = GRAPH_HEIGHT / GRAPH_SIZE;
    private static final int GAP_BEFORE_GRAPH_START = 5;
    private static final int CENTERING_LINE_ON_BORDER = 1;
    private static final int SCREEN_MAX_WIDTH = 1920;
    private static BorderPane UI = new BorderPane();
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
    private static final int TEXT_FIELD_SIZE = 50;
    private Stage primaryStage;
    private static final int MENU_TOOLBAR_HEIGHT = 58;
    private ToggleGroup toggleGroup1;
    private ToggleGroup toggleGroup2;
    private ArrayList<StraightLine> currentGraphStraightLines = new ArrayList<>();


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
        createWindowContent();
    }

    private void createWindowContent() {
        FlowPane menuToolbar = createMenuToolbar();
        FlowPane homeToolbar = createCommandToolbar();
        createMenus(menuToolbar, homeToolbar);
        createGraphPane();
        createGrid(false);
        setDefaultValues();
        createBottomToolbar();
    }

    private FlowPane createMenuToolbar() {
        Menu menu = createFileMenu();
        return createMenuBar(menu);
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

    private FlowPane createMenuBar(Menu menu) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(UI.widthProperty());
        menuBar.paddingProperty().setValue(Insets.EMPTY);
        FlowPane menuBarPane = new FlowPane();
        menuBarPane.setMinWidth(ROOT_WIDTH);
        menuBarPane.getChildren().add(menuBar);
        return menuBarPane;
    }


    private FlowPane createCommandToolbar() {
        FlowPane homeToolbar = new FlowPane();
        homeToolbar.setHgap(1);
        homeToolbar.setPadding(new Insets(1,0,1,0));
        homeToolbar.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        homeToolbar.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1,0))));

        RadioButton button = createPencilButton();
        RadioButton create = createLineButton();
        RadioButton rectangle = createRectangleButton();

        toggleGroup1 = new ToggleGroup();
        toggleGroup1.getToggles().addAll(button,create,rectangle);
        toggleGroup1.selectToggle(button);

        RadioButton colourBlack = createBlackButton();
        RadioButton colourRed = createRedButton();
        RadioButton colourBlue = createBlueButton();

        toggleGroup2 = new ToggleGroup();
        toggleGroup2.getToggles().addAll(colourBlack, colourRed, colourBlue);
        toggleGroup2.selectToggle(colourBlack);

        for(Toggle control : toggleGroup1.getToggles())
        {
            homeToolbar.getChildren().add((RadioButton) control);
        }

        for(Toggle control : toggleGroup2.getToggles())
        {
            homeToolbar.getChildren().add((RadioButton) control);
        }
        homeToolbar.setStyle("-fx-faint-focus-color: transparent;");
        return homeToolbar;
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

    private RadioButton createBlackButton() {
        RadioButton button = new RadioButton("Black");
        applyRadioButtonCss(button);
        return button;
    }

    private RadioButton createRedButton() {
        RadioButton button = new RadioButton("Red");
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

    private void createMenus(FlowPane menuToolbar, FlowPane homeToolbar) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuToolbar);
        borderPane.setBottom(homeToolbar);
        UI.setTop(borderPane);
    }

    private void createGraphPane() {
        graphPane = new Group();
        graphPane.setLayoutX(LEFT_H_GAP);
        graphPane.setLayoutY(GAP_BEFORE_GRAPH_START);
        graphPane.setOnMouseMoved((mouseEvent) -> {
            mouse_pointer.setText((mouseEvent.getSceneX() - LEFT_H_GAP) + ", " + (mouseEvent.getSceneY() - MENU_TOOLBAR_HEIGHT));
            System.out.println(mouseEvent.getSceneX() - LEFT_H_GAP);
        });
        createMouseEvents(graphPane);
    }

    private void createMouseEvents(Group graphPane) {

        graphPane.setOnMouseClicked((mouseEvent) ->{
            if(((ToggleButton)toggleGroup1.getSelectedToggle()).getText().equals("Pencil")){
                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                addPoint(x, y);
            }
        });

        graphPane.setOnMouseDragged((mouseEvent) ->{
            if(((ToggleButton)toggleGroup1.getSelectedToggle()).getText().equals("Pencil")){
                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                if((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)){
                    addPoint(x,y);
                }
            }
        });
    }

    private void addPoint(double x, double y){
        try {
            StraightLine straightLine = new StraightLine(x, y, x + 1, y + 1);
            currentGraphStraightLines.add(straightLine);
            drawOnGraph(straightLine);
        } catch (IncorrectInputException e) {
            System.out.println("Error adding vector - negative numbers");
        }
    }
    private void createGrid(boolean hasGridLines) {
        if (hasGridLines) {
            createGraphGrid(graphPane);
        }
        else{
            Rectangle rectangle = new Rectangle(0,0,GRAPH_WIDTH,GRAPH_HEIGHT);
            rectangle.setFill(Color.WHITE);
            graphPane.getChildren().add(rectangle);
        }
        graphPane.getChildren().add(drawingsGroup);
        StackPane graphContainer = new StackPane();
        graphContainer.setPrefWidth(SCREEN_MAX_WIDTH);
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgb(197, 207, 223);");
        pane.getChildren().add(graphPane);
        graphContainer.getChildren().add(pane);
        UI.setCenter(graphContainer);
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
        UI.setBottom(createInformationBar());
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
            StraightLine straightLine = new StraightLine(x1, y1, x2, y2);
            currentGraphStraightLines.add(straightLine);
            drawOnGraph(straightLine);
        } catch (IncorrectInputException e) {
            System.out.println("Error adding vector - negative numbers");
        }
    }

    private void drawOnGraph(StraightLine vector) {
        Line line = new Line(vector.getX1(), GRAPH_HEIGHT - vector.getY1() + CENTERING_LINE_ON_BORDER
                , vector.getX2(), GRAPH_HEIGHT - vector.getY2() + CENTERING_LINE_ON_BORDER);
        Color color = Color.GREEN;
        switch (((ToggleButton) (toggleGroup2.getSelectedToggle())).getText()) {
            case "Black":
                color = Color.BLACK;
                break;
            case "Red":
                color = Color.RED;
                break;
            case "Blue":
                color = Color.BLUE;
                break;
        }
        line.setStroke(color);
        line.setStrokeWidth(1);
        drawingsGroup.getChildren().add(line);
    }


    private void clearGraph() {
        drawingsGroup.getChildren().clear();
    }

    private GridPane createInformationBar() {
        GridPane informationBar = new GridPane();
        setDefaultValues(informationBar);
        addLabels(informationBar);
        return informationBar;
    }

    private void setDefaultValues(GridPane informationBar) {
        informationBar.setPadding(new Insets(0,0,0,2));
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
                    StraightLine straightLine = new StraightLine(Double.parseDouble(savedVector[0]), Double.parseDouble(savedVector[1]),
                            Double.parseDouble(savedVector[2]), Double.parseDouble(savedVector[3]));
                    addVectorToGraph(straightLine.getX1(), straightLine.getY1(), straightLine.getX2(), straightLine.getY2(), true);
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
                for (StraightLine straightLine : currentGraphStraightLines) {
                    writer.write(straightLine.getX1() + ", " + straightLine.getY1()
                            + ", " + straightLine.getX2() + ", " + straightLine.getY2() + "\n");
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

