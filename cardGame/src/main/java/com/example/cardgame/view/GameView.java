package com.example.cardgame.view;

import com.example.cardgame.app.App;
import com.example.cardgame.gamelogic.Board;
import com.example.cardgame.gamelogic.Cell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GameView extends BaseView {

    @FXML
    private TextArea myScore;
    @FXML
    private TextArea enemyScore;
    private AnchorPane pane = null;

    Stage stage;

    Board board = new Board();

    Cell firstCard = null;
    Cell secondCard = null;
    public GridPane gameMatrix;


    private final App application = BaseView.getChatApplication();
    private final EventHandler onKeyEvent = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String score = String.valueOf(application.getUserConfig().getScore());
                application.getScoreClient().sendScore(score);
                enemyScore.appendText(score);
                myScore.clear();
                event.consume();
            }
        }
    };

    public GameView() throws Exception {
    }


    @Override
    public Parent getView() {
        if (pane == null) {
            this.createView();
        }

        return pane;
    }

    private void createView() {
        URL url = null;
        try {
            url = new File("D:\\IntelliJ IDEA Projects\\cardGame\\src\\main\\java\\com\\example\\cardgame\\main.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Card Game");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize() throws Exception {
        board.fillMatrix();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                FileInputStream input = new FileInputStream(
                        "D:\\IntelliJ IDEA Projects\\cardGame\\unknownCard.png");
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setUserData(row+","+col);
                imageView.setOnMouseClicked(event -> {
                    try {
                        cardListener(event);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                gameMatrix.add(imageView, row, col);
            }
        }

    }

    public void cardListener(MouseEvent event) throws FileNotFoundException {
        Node sourceComponent = (Node) event.getSource();
        String rowAndColumn = (String)sourceComponent.getUserData();

        int rowSelected = Integer.parseInt(rowAndColumn.split(",")[0]);
        int colSelected = Integer.parseInt(rowAndColumn.split(",")[1]);

        String image = board.board[rowSelected][colSelected].value;

        FileInputStream imageFile = new FileInputStream(
                "D:\\IntelliJ IDEA Projects\\cardGame\\"+image+".png");
        Image selectedImage = new Image(imageFile);
        ((ImageView)sourceComponent).setImage(selectedImage);
        checkPairWasFound(rowSelected,colSelected);

    }

    public void checkPairWasFound(int rowSelected, int colSelected) throws FileNotFoundException {

        if(firstCard == null){
            firstCard = board.board[rowSelected][colSelected];
        }else if(secondCard ==null){
            secondCard = board.board[rowSelected][colSelected];
        }else {
            if(firstCard.value.equals(secondCard.value)){
                board.board[firstCard.row][firstCard.column].wasGuessed = true;
                board.board[secondCard.row][secondCard.column].wasGuessed = true;
                app.getUserConfig().setScore();
            } else {
                int indexFirstCardInList = (firstCard.row * 6) + firstCard.column;

                FileInputStream questionFile = new FileInputStream(
                        "D:\\IntelliJ IDEA Projects\\cardGame\\unknownCard.png");
                Image questionImage = new Image(questionFile);
                ((ImageView)gameMatrix.getChildren().get(indexFirstCardInList)).setImage(questionImage);

                int indexSecondCardInList = (secondCard.row * 6) + secondCard.column;
                ((ImageView)gameMatrix.getChildren().get(indexSecondCardInList)).setImage(questionImage);
            }

            firstCard= board.board[rowSelected][colSelected];
            secondCard = null;

        }
    }


    public void setMessageToScore(String score) {
        if (score != null) enemyScore.setText(score);
    }
}
