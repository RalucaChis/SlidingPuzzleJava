package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import sample.Controller;
import sample.model.Tile;
import sample.repository.Repository;

public class Main extends Application {
    private Button empty;
    private Boolean gameOver;
    private List<Tile> tiles = new ArrayList<Tile>(), newTiles = new ArrayList<Tile>();
    private static List<Button> buttons = new ArrayList<Button>();
    private Controller controller;
    private Scene scene;
    private GridPane gridePane;
    private int countMoves = 0;
    Integer n;
    private sample.Time time;
    Image imgUser;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        scene = new Scene(new Group());
        Button newGameButton = new Button("New Game");
        Button exitButton = new Button("Exit");

        // horizontal box hBox
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: lightblue;");
        hBox.setPadding(new Insets(15, 20, 15, 20));
        hBox.prefWidthProperty().bind(scene.widthProperty().multiply(1));
        hBox.getChildren().addAll(newGameButton, exitButton);
        hBox.setSpacing(50);

        newGameButton.setPrefSize(120, 20);
        exitButton.setPrefSize(120, 20);

        //set icon of the application
        Image applicationIcon = new Image(getClass().getResourceAsStream("imagini\\puzzle.png"));
        stage.getIcons().add(applicationIcon);

        // newGameButton action
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                tiles.clear();
                newTiles.clear();
                buttons.clear();
                countMoves = 0;
                if (gridePane != null)
                    gridePane.getChildren().clear();

                try {
                    newGame();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        };
        newGameButton.setOnAction(event);

        // exitButton action
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                time.timer.cancel();
                stage.close();
            }
        };
        exitButton.setOnAction(event2);

        ((Group) scene.getRoot()).getChildren().addAll(hBox);

        // choose 'n' window
        Stage beginning = new Stage();
        TextField textField = new TextField();
        Label nlabel = new Label("Enter the size of the puzzle: ");
        Label wronglabel = new Label();
        Button enter = new Button("Go!");
        Button upload = new Button("Upload Picture");

        VBox vb = new VBox();
        vb.setPadding(new Insets(16.0, 16.0, 16.0, 16.0));
        vb.setSpacing(10);
        vb.getChildren().addAll(nlabel, textField, upload,wronglabel,enter);
        vb.setStyle("-fx-background-color: lightblue;");

        EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.png", "*.jpg", "*.gif"));
                chooser.setTitle("Open File");
                File file = chooser.showOpenDialog(new Stage());
                if(file != null) {
                    String imagepath = null;
                    try {
                        imagepath = file.toURI().toURL().toString();
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("file:"+imagepath);
                    imgUser = new Image(imagepath, 300, 300, false, false);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Please Select a File");
                    alert.showAndWait();
                }
            }
        };
        upload.setOnAction(event4);

        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int number;
                try {
                    number = Integer.parseInt(textField.getText());
                    n = number;
                    beginning.close();
                    stage.show();
                } catch (Exception e1) {
                    wronglabel.setText("Try again");
                }
            }
        };
        enter.setOnAction(event3);

        Scene sc = new Scene(vb, 300, 200);
        beginning.getIcons().add(applicationIcon);
        beginning.setTitle("Sliding Puzzle");
        beginning.setScene(sc);
        beginning.show();

        stage.setTitle("Sliding Puzzle");
        stage.setWidth(500);
        stage.setHeight(450);
        stage.setScene(scene);
        //stage.show();
    }

    /**
     * creates the list of buttons and adds them to the gridPane
     * @throws FileNotFoundException if the source of the image is not found
     */
    public void puzzle() throws FileNotFoundException {

        //create the grid pane
        gridePane = new GridPane();
        gridePane.setPadding(new Insets(80, 15, 15, 20));

        for (int i = 0; i < newTiles.size(); i++) {
            if (newTiles.get(i).getPos().compareTo(n*n-1) == 0) {
                ImageView imageView = controller.find(newTiles.get(i).getPos());
                empty = new Button("", imageView);
                Integer nn = n*n-1;
                double s = 300.0/n;
                empty.setId(nn.toString());
                empty.setStyle("-fx-padding: 0;");
                empty.setPrefSize(s,s);
                empty.setOpacity(0);
                gridePane.add(empty, newTiles.get(i).getY(), newTiles.get(i).getX());
                buttons.add(empty);
            } else {
                ImageView imageView = controller.find(newTiles.get(i).getPos());
                Button b = new Button("", imageView);
                b.setId(newTiles.get(i).getPos().toString());
                b.setStyle("-fx-padding: 0;");
                double s = 300.0/n;
                b.setPrefSize(s, s);
                b.setOpacity(1);
                gridePane.add(b, newTiles.get(i).getY(), newTiles.get(i).getX());
                buttons.add(b);
            }
        }

        ((Group) scene.getRoot()).getChildren().addAll(gridePane);
    }

    /**
     * sets action for the buttons from the gridPane
     */
    public void action() {
        for (Button b : buttons) {
            Integer nn = n*n-1;
            if (b.getText().compareTo(nn.toString()) != 0) {
                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        moveButton(b, empty);
                        countMoves++;
                        controller.check(buttons);
                        gameOver = controller.check(buttons);
                        if (time.isOver()) {
                            Label secondLabel = new Label("Time's up!");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().addAll(secondLabel);

                            secondLabel.setFont(new Font("Arial", 15));
                            secondLabel.setTextFill(Color.web("#0076a3"));
                            Scene secondScene = new Scene(secondaryLayout, 300, 100);
                            Stage newWindow = new Stage();
                            newWindow.setTitle(";(");
                            newWindow.setScene(secondScene);

                            //set icon of the window
                            Image applicationIcon = new Image(getClass().getResourceAsStream("imagini\\castle.png"));
                            newWindow.getIcons().add(applicationIcon);

                            newWindow.show();
                            gridePane.getChildren().clear();
                        }
                        if (gameOver.compareTo(true) == 0) {
                            time.timer.cancel();
                            Label secondLabel = new Label("The citizens of the Kingdom of Arendelle" + "\n" + "thank you!" +
                                    "\n" + "Number of moves: " + countMoves);
                            countMoves = 0;

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().addAll(secondLabel);

                            secondLabel.setFont(new Font("Arial", 15));
                            secondLabel.setTextFill(Color.web("#0076a3"));
                            Scene secondScene = new Scene(secondaryLayout, 300, 100);

                            // New window (Stage)
                            Stage newWindow = new Stage();
                            newWindow.setTitle("We have a winner!");
                            newWindow.setScene(secondScene);

                            //set icon of the window
                            Image applicationIcon = new Image(getClass().getResourceAsStream("imagini\\castle.png"));
                            newWindow.getIcons().add(applicationIcon);

                            newWindow.show();
                            gridePane.getChildren().clear();
                        }
                    }
                };
                b.setOnAction(event);
            }
        }
    }

    /**
     * swaps 2 buttons if they are neighbours
     * @param pressedButton a button
     * @param emptyButton the empty button
     */
    public void moveButton(Button pressedButton, Button emptyButton) {
        int x1, y1, x2, y2;
        x1 = GridPane.getRowIndex(pressedButton);
        y1 = GridPane.getColumnIndex(pressedButton);
        x2 = GridPane.getRowIndex(emptyButton);
        y2 = GridPane.getColumnIndex(emptyButton);
        if (x1 == x2 && (y1 == y2 + 1 || y1 == y2 - 1)) {
            swap(pressedButton, emptyButton);

        } else if (y1 == y2 && (x1 == x2 + 1 || x1 == x2 - 1)) {
            swap(pressedButton, emptyButton);
        }
    }

    /**
     * swaps 2 nodes
     * @param n1 a node
     * @param n2 a node
     */
    public static void swap(Node n1, Node n2) {
        List<Button> newButtons = new ArrayList<Button>();
        for (Button b : buttons) {
            if (b == n1)
                newButtons.add((Button) n2);
            else if (b == n2)
                newButtons.add((Button) n1);
            else
                newButtons.add(b);
        }

        Integer temp = GridPane.getRowIndex(n1);
        GridPane.setRowIndex(n1, GridPane.getRowIndex(n2));
        GridPane.setRowIndex(n2, temp);

        temp = GridPane.getColumnIndex(n1);
        GridPane.setColumnIndex(n1, GridPane.getColumnIndex(n2));
        GridPane.setColumnIndex(n2, temp);

        buttons.clear();
        buttons = newButtons;
    }

    /**
     * initialises the list of tiles
     */
    public void initTiles() {
        // create the list of positions
        Repository repo = new Repository(tiles);
        int x = 0, y = -1;
        for (int i = 0; i < n * n; i++) {
            if (i % n == 0 && i != 0) {
                x += 1;
                y = 0;
            }
            else y += 1;

            Tile t = new Tile(x, y, i);
            try {
                repo.save(t);
            }catch (sample.AlreadyExistsException e){
                System.out.println("Already exists!");
            }
        }
        controller = new Controller(repo);
        newTiles = controller.shuffle(n);
    }

    /**
     * set for each tile an image
     * @throws FileNotFoundException if it doesn't find the image
     */
    public void initImageView() throws FileNotFoundException {
        for (int i = 0; i < tiles.size(); i++) {
            ImageView iw = new ImageView(imgUser);
            double s = 300.0/n;
            Rectangle2D viewportRect = new Rectangle2D(tiles.get(i).getY() * s, tiles.get(i).getX() * s, s, s);
            iw.setViewport(viewportRect);
            tiles.get(i).setIw(iw);
        }
        // background image for puzzle
        ImageView imageView = new ImageView(imgUser);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        imageView.setX(20);
        imageView.setY(80);
        imageView.setOpacity(0.10);

        //vertical box vbox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(80, 20, 20, 340));
        ImageView smallImageView = new ImageView(imgUser);
        smallImageView.setFitHeight(100);
        smallImageView.setFitWidth(100);
        Text text = new Text("Help Elsa redo her portrait. " +
                "Hurry up! The moment of the coronation is approaching!");
        text.setFont(new Font("Arial", 15));
        text.setFill(Color.web("#0076a3"));
        text.setWrappingWidth(140);

        vbox.getChildren().addAll(smallImageView, text);
        vbox.setSpacing(20);
        ((Group) scene.getRoot()).getChildren().addAll(imageView,vbox);
    }

    /**
     * creates a new game
     * @throws FileNotFoundException if it doesn't find the images for initImageView()
     */
    public void newGame() throws FileNotFoundException {
        initTiles();
        initImageView();
        puzzle();
        action();
        time = new sample.Time(100);
        gameOver = false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
