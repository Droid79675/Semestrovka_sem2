package com.example.cardgame.view;

import com.example.cardgame.app.App;
import javafx.scene.Parent;

public abstract class BaseView {

    public static App app;

    public static App getChatApplication() throws Exception {
        if (app != null) {
            return app;
        }
        throw new Exception("No app in base GameView");
    }

    public static void setChatApplication(App app) {
        BaseView.app = app;
    }

    public abstract Parent getView();


}
