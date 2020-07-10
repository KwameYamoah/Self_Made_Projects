package main;

//TODO NEXT RIGHT CLICK ISSUE WHEN USING LINES -STARTS NEW LINE IF LEFT CLICK IS DRAGGED
//TODO NEXT : Circle - Later when revisiting project - needs major refactoring

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
    private static Group hoverGroup = new Group();
    private Label mouse_pointer;
    private Label graph_size;
    private Stage primaryStage;
    private static final int MENU_TOOLBAR_HEIGHT = 58;
    private ToggleGroup toggleGroup1;
    private ToggleGroup toggleGroup2;
    private ArrayList<CustomLine> currentGraphStraightLines = new ArrayList<>();
    private Line previousPoint;
    private Boolean noPreviousPoint = true;
    private Point startOfDrawing = new Point();
    private Point endOfDrawing = new Point();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint Application");
        createUI();
        Scene scene = new Scene(UI, ROOT_WIDTH, ROOT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
        createBoard(false);
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
        FlowPane homeToolbar = createHomeToolbar();
        RadioButton pencilButton = createPencilButton();
        RadioButton lineButton = createLineButton();
        RadioButton rectangleButton = createRectangleButton();


        addShapeButtonsToToggleGroup(pencilButton, lineButton, rectangleButton);

        RadioButton colourBlack = createBlackButton();
        RadioButton colourRed = createRedButton();
        RadioButton colourBlue = createBlueButton();
        RadioButton colourGreen = createGreenButton();
        addColourButtonsToToggleGroup(colourBlack, colourRed, colourBlue, colourGreen);
        addButtonsToPane(homeToolbar);
        return homeToolbar;
    }




    private FlowPane createHomeToolbar() {
        FlowPane homeToolbar = new FlowPane();
        homeToolbar.setHgap(1);
        homeToolbar.setPadding(new Insets(1, 0, 1, 0));
        homeToolbar.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        homeToolbar.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
        homeToolbar.setStyle("-fx-faint-focus-color: transparent;");
        return homeToolbar;
    }

    private void addShapeButtonsToToggleGroup(RadioButton pencil, RadioButton create, RadioButton rectangle) {
        toggleGroup1 = new ToggleGroup();
        toggleGroup1.getToggles().addAll(pencil, create, rectangle);
        toggleGroup1.selectToggle(pencil);
    }

    private void addColourButtonsToToggleGroup(RadioButton colourBlack, RadioButton colourRed, RadioButton colourBlue, RadioButton colourGreen) {
        toggleGroup2 = new ToggleGroup();
        toggleGroup2.getToggles().addAll(colourBlack, colourRed, colourBlue,colourGreen);
        toggleGroup2.selectToggle(colourBlack);
    }

    private void addButtonsToPane(FlowPane homeToolbar) {
        for (Toggle control : toggleGroup1.getToggles()) {
            homeToolbar.getChildren().add((RadioButton) control);
        }

        for (Toggle control : toggleGroup2.getToggles()) {
            homeToolbar.getChildren().add((RadioButton) control);
        }
    }

    private RadioButton createPencilButton() {
        RadioButton pencilButton = new RadioButton("Pencil");
        applyRadioButtonCss(pencilButton);
        return pencilButton;
    }

    private RadioButton createLineButton() {
        RadioButton lineButton = new RadioButton("Line");
        applyRadioButtonCss(lineButton);
        return lineButton;
    }

    private RadioButton createRectangleButton() {
        RadioButton rectangleButton = new RadioButton("Rectangle");
        applyRadioButtonCss(rectangleButton);
        return rectangleButton;
    }


    private RadioButton createBlackButton() {
        RadioButton button = new RadioButton("Black");
        applyRadioButtonCss(button);
        button.setStyle("-fx-background-color: black");
        button.setTextFill(Color.BLACK);
        return button;
    }

    private RadioButton createRedButton() {
        RadioButton button = new RadioButton("Red");
        applyRadioButtonCss(button);
        button.setStyle("-fx-background-color: red");
        button.setTextFill(Color.RED);
        return button;
    }

    private RadioButton createBlueButton() {
        RadioButton button = new RadioButton("Blue");
        applyRadioButtonCss(button);
        button.setStyle("-fx-background-color: blue");
        button.setTextFill(Color.BLUE);
        return button;
    }

    private RadioButton createGreenButton() {
        RadioButton button = new RadioButton("Green");
        applyRadioButtonCss(button);
        button.setStyle("-fx-background-color: green");
        button.setTextFill(Color.GREEN);
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
        graphPane.setOnMouseMoved((mouseEvent) -> mouse_pointer.setText((mouseEvent.getSceneX() - LEFT_H_GAP) + ", " + (mouseEvent.getSceneY() - MENU_TOOLBAR_HEIGHT)));
        createMouseClickEvent(graphPane);
        createMouseDragEvent(graphPane);
        createMousePressedAndReleasedEvent(graphPane);
    }

    private void createMouseClickEvent(Group graphPane) {
        graphPane.setOnMouseClicked((mouseEvent) -> {
            System.out.println("Mouse Clicked");
            if (mouseEvent.isPrimaryButtonDown()) {
                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                if (((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Pencil")) {
                    previousPoint = new Line(x, y, x + 1, y + 1);
                    addPoint(x, y);
                }
            }
        });


    }

    private void createMouseDragEvent(Group graphPane) {
        graphPane.setOnMouseDragged((mouseEvent) -> {
            System.out.println("Mouse Drag");
            hoverGroup.getChildren().clear();
            if (mouseEvent.isPrimaryButtonDown()) {
                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                if (((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Pencil")) {

                    if (noPreviousPoint) {
                        previousPoint = new Line(x, y, x + 1, y + 1);
                        noPreviousPoint = false;
                    }
                    if ((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)) {
                        addPoint(x, y);
                        previousPoint = new Line(x, y, x + 1, y + 1);
                    }
                } else if (((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Line")){
                    if ((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)) {
                        endOfDrawing.setLocation(x, y);
                    }
                    Line line = new Line(startOfDrawing.x, startOfDrawing.y, endOfDrawing.x, endOfDrawing.y);
                    line.setStrokeWidth(1);
                    drawOnGraph(line, true, false);

                }else if(((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Rectangle")){
                    if ((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)) {
                        endOfDrawing.setLocation(x, y);
                    }
                    drawRectangle(startOfDrawing.x, startOfDrawing.y, endOfDrawing.x, endOfDrawing.y, true);
                }
            }
        });
    }

    private void createMousePressedAndReleasedEvent(Group graphPane) {
        graphPane.setOnMousePressed((mouseEvent) -> {
            System.out.println("Mouse Pressed");
            if (mouseEvent.isPrimaryButtonDown()) {
                double x = mouseEvent.getX();
                double y = GRAPH_HEIGHT - mouseEvent.getY();
                if (((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Line") || (((ToggleButton) toggleGroup1.getSelectedToggle()).getText().equals("Rectangle"))) {
                    if ((x >= 0) && (x <= GRAPH_WIDTH) && (y >= 0) & (y <= GRAPH_HEIGHT)) {
                        startOfDrawing.setLocation(x, y);
                    }

                }
            }

        });

        graphPane.setOnMouseReleased((mouseEvent) -> {
            hoverGroup.getChildren().clear();
            switch (((ToggleButton) toggleGroup1.getSelectedToggle()).getText()) {
                case "Pencil":
                    noPreviousPoint = true;
                    break;
                case "Line":
                    if (!(mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown())) {
                        if (startOfDrawing.x >= 0) {
                            Line line = new Line(startOfDrawing.x, startOfDrawing.y, endOfDrawing.x, endOfDrawing.y);
                            line.setStrokeWidth(1);
                            drawOnGraph(line, false, false);
                            startOfDrawing.setLocation(-1, -1);
                            endOfDrawing.setLocation(-1, -1);
                        }
                    }
                    break;
                case "Rectangle":
                    drawRectangle(startOfDrawing.x, startOfDrawing.y, endOfDrawing.x, endOfDrawing.y, false);
                    break;
            }


        });
    }



    private void drawRectangle(double x1,double y1,double x2, double y2, boolean hover) {
        Line line1 = new Line(x1,y1,x2,y1);
        Line line2 = new Line(x2,y1,x2,y2);
        Line line3 = new Line(x2,y2,x1,y2);
        Line line4 = new Line(x1,y2,x1,y1);
        line1.setStrokeWidth(1);
        line2.setStrokeWidth(1);
        line3.setStrokeWidth(1);
        line4.setStrokeWidth(1);
        drawOnGraph(line1, hover, false);
        drawOnGraph(line2, hover, false);
        drawOnGraph(line3, hover, false);
        drawOnGraph(line4, hover, false);
    }


    private void addPoint(double x, double y) {
        if ((Math.abs(previousPoint.getStartX() - x) + Math.abs(previousPoint.getStartY() - y)) > 2) {
            Line line = new Line(previousPoint.getEndX() + 1, previousPoint.getEndY(), x - 1, y);
            line.setStrokeWidth(1);
            drawOnGraph(line, false, false);
        }
        Line straightLine = new Line(x, y, x + 1, y + 1);
        drawOnGraph(straightLine, false, false);
    }

    private void createBoard(boolean hasGridLines) {
        createGrid(hasGridLines);
        createBoardPanes();
    }

    private void createBoardPanes() {
        graphPane.getChildren().add(drawingsGroup);
        graphPane.getChildren().add(hoverGroup);
        StackPane graphContainer = new StackPane();
        graphContainer.setPrefWidth(SCREEN_MAX_WIDTH);
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgb(197, 207, 223);");
        pane.getChildren().add(graphPane);
        graphContainer.getChildren().add(pane);
        UI.setCenter(graphContainer);
    }

    private void createGrid(boolean hasGridLines) {
        if (hasGridLines) {
            createGraphGrid(graphPane);
        } else {
            Rectangle rectangle = new Rectangle(0, 0, GRAPH_WIDTH, GRAPH_HEIGHT);
            rectangle.setFill(Color.WHITE);
            graphPane.getChildren().add(rectangle);
        }
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
        initializeLabels();
        setLabelsRange();
        setDefaultLabelValues();
    }


    private void initializeLabels() {
        mouse_pointer = new Label();
        graph_size = new Label();
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

    private void addDrawingToGraph(double x1, double y1, double x2, double y2,Color color, boolean fromFile) {
        addLineToGraph(x1, y1, x2, y2, color, fromFile);
    }

    private void addLineToGraph(double x1, double y1, double x2, double y2, Color color, boolean fromFile) {
        Line line = new Line(x1, GRAPH_HEIGHT - y1 + CENTERING_LINE_ON_BORDER, x2, GRAPH_HEIGHT - y2 + CENTERING_LINE_ON_BORDER);
        line.setStroke(color);
        drawOnGraph(line, false, fromFile);

    }

    private void drawOnGraph(Line line, boolean hover, boolean fromFile) {
        line.setStartY(GRAPH_HEIGHT - line.getStartY() + CENTERING_LINE_ON_BORDER);
        line.setEndY(GRAPH_HEIGHT - line.getEndY() + CENTERING_LINE_ON_BORDER);
        line.setStrokeWidth(1);
        Color color;
        if(fromFile){
            color = (Color)line.getStroke();
        }
        else{
            color = getColor();
            line.setStroke(color);
        }

        if(!hover) {
            currentGraphStraightLines.add(new CustomLine(line, color));
            drawingsGroup.getChildren().add(line);
        }
        else{
            hoverGroup.getChildren().add(line);
        }
    }

    private Color getColor() {
        switch (((ToggleButton) (toggleGroup2.getSelectedToggle())).getText()) {
            case "Black":
                return Color.BLACK;
            case "Red":
                return Color.RED;
            case "Blue":
                return Color.BLUE;
            case "Green":
                return Color.GREEN;
        }
        return Color.BLACK;
    }

    private void clearGraph() {
        drawingsGroup.getChildren().clear();
        currentGraphStraightLines.clear();
    }

    private GridPane createInformationBar() {
        GridPane informationBar = new GridPane();
        setDefaultValues(informationBar);
        addLabels(informationBar);
        return informationBar;
    }

    private void setDefaultValues(GridPane informationBar) {
        informationBar.setPadding(new Insets(0, 0, 0, 2));
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
            File selectedFile = getFilePath();
            if (selectedFile == null) {
                return;
            }
            System.out.println("Opening saved graph");
            load(selectedFile);
        }

        private File getFilePath() {
            File workingDirectory = new File(System.getProperty("user.dir"));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(workingDirectory);
            return fileChooser.showOpenDialog(primaryStage);
        }

        private void load(File selectedFile) {
            try (Scanner scanner = new Scanner(new FileReader(selectedFile.getPath()))) {
                scanner.useDelimiter("\n");
                clearGraph();
                while (scanner.hasNext()) {
                    parseDetails(scanner);
                }
                System.out.println("Successfully opened file");
            } catch (NumberFormatException e) {
                System.out.println("Saved file contains non-numbers");
            } catch (IOException e) {
                System.out.println("Error while opening file");
            }
        }

        private void parseDetails(Scanner scanner) {
            String[] savedLine = scanner.next().split(",");
            Line straightLine = new Line(Double.parseDouble(savedLine[0]), Double.parseDouble(savedLine[1]),
                    Double.parseDouble(savedLine[2]), Double.parseDouble(savedLine[3]));

            Color color = Color.web(savedLine[4].trim());
            addDrawingToGraph(straightLine.getStartX(), straightLine.getStartY(), straightLine.getEndX(), straightLine.getEndY(), color, true);
        }
    }

    class SaveGraph implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            File selectedFolder = getSaveDirectory();
            if (selectedFolder == null) {
                return;
            }
            System.out.println("Saving graph");
            save(selectedFolder);
        }

        private File getSaveDirectory() {
            File workingDirectory = new File(System.getProperty("user.dir"));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(workingDirectory);
            return directoryChooser.showDialog(primaryStage);
        }

        private void save(File selectedFolder) {
            try (FileWriter writer = new FileWriter(selectedFolder.getPath() + "/graph.dat")) {
                for (CustomLine lineToSave : currentGraphStraightLines) {
                    Line line = lineToSave.getLine();
                    writer.write(line.getStartX() + ", " + line.getStartY()
                            + ", " + line.getEndX() + ", " + line.getEndY() + ", " + lineToSave.getColor() + "\n");
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

