package com.example.cardgame.app;

import com.example.cardgame.client.ChatClient;
import com.example.cardgame.gamelogic.Board;
import com.example.cardgame.gamelogic.Cell;
import com.example.cardgame.model.UserConfig;
import com.example.cardgame.view.BaseView;
import com.example.cardgame.view.ScoreView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File("C:\\Users\\Honor\\Desktop\\cardGameSingle\\src\\main\\java\\com\\example\\cardgame\\main.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Card Game");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public GridPane gameMatrix;

    public BorderPane body;

    Board board = new Board();

    Cell firstCard = null;
    Cell secondCard = null;

    private ScoreView scoreView;

    public UserConfig getUserConfig() {
        return userConfig;
    }

    private UserConfig userConfig;

    public ChatClient getScoreClient() {
        return scoreClient;
    }

    private ChatClient scoreClient;


    @FXML
    public void initialize() throws FileNotFoundException {
        board.fillMatrix();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                FileInputStream input = new FileInputStream(
                        "C:\\Users\\Honor\\Desktop\\cardGameSingle\\unknownCard.png");
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
                "C:\\Users\\Honor\\Desktop\\cardGameSingle\\"+image+".png");
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
            } else {
                int indexFirstCardInList = (firstCard.row * 6) + firstCard.column;

                FileInputStream questionFile = new FileInputStream(
                        "C:\\Users\\Honor\\Desktop\\cardGameSingle\\unknownCard.png");
                Image questionImage = new Image(questionFile);
                ((ImageView)gameMatrix.getChildren().get(indexFirstCardInList)).setImage(questionImage);

                int indexSecondCardInList = (secondCard.row * 6) + secondCard.column;
                ((ImageView)gameMatrix.getChildren().get(indexSecondCardInList)).setImage(questionImage);
            }

            firstCard= board.board[rowSelected][colSelected];
            secondCard = null;

        }
    }

    public BaseView getScoreView() {
        return scoreView;
    }

    public void setView(BaseView view) {
        body.setTop(view.getView());
    }

    public void startScoreClient() throws IOException {
        this.scoreClient.start();
    }

    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    public void appendMessage(String message) {
        scoreView.setMessageToScore(message);
    }

}