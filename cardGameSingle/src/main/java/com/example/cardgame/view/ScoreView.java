package com.example.cardgame.view;

import com.example.cardgame.app.App;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ScoreView extends BaseView {

    private TextArea input;
    private TextArea conversation;
    private AnchorPane pane = null;

    private final App application = BaseView.getChatApplication();
    private final EventHandler onKeyEvent = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String score = String.valueOf(application.getUserConfig().getScore());
                application.getScoreClient().sendScore(score);
                conversation.appendText(score);
                input.clear();
                event.consume();
            }
        }
    };

    public ScoreView() throws Exception {
    }


    @Override
    public Parent getView() {
        if (pane == null) {
            this.createView();
        }

        return pane;
    }

    private void createView() {
        pane = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);


        AnchorPane.setTopAnchor(conversation, 4.0);
        AnchorPane.setLeftAnchor(conversation, 40.0);
        AnchorPane.setRightAnchor(conversation, 60.0);

        input = new TextArea();
        input.setMaxHeight(50);

        AnchorPane.setBottomAnchor(input, 4.0);
        AnchorPane.setLeftAnchor(input, 60.0);
        AnchorPane.setRightAnchor(input, 40.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        pane.getChildren().addAll(input, conversation);
    }

    public void setMessageToScore(String score) {
        if (score != null) conversation.setText(score);
    }
}
