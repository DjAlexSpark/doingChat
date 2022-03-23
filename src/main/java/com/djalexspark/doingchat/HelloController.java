package com.djalexspark.doingchat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class HelloController implements Initializable {

    private boolean isActiveThread;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem aboutButton;

    @FXML
    private MenuItem closeConnectionButton;

    @FXML
    private MenuItem exitButton;

    @FXML
    private ListView<?> listMessageField;

    @FXML
    private TextArea messageField;

    @FXML
    private MenuItem newConnectionButton;

    @FXML
    private Button sendButton;

//методы выполняющиеся при действии
    @FXML
    void closeClientConnection(ActionEvent event) {
        messageField.setText("closeClient");
    }

    @FXML
    void sendText(ActionEvent event) {
        messageField.setText("sendButton Pressed");
    }

    Server threadServer = new Server();
    @FXML
    void startServer(ActionEvent event) {

        threadServer.start();
        isActiveThread = true;
        messageField.setText("startServer");
    }
    @FXML
    void closeServer(ActionEvent event) {
        threadServer.closeAll();
        //threadServer.interrupt();
        isActiveThread=false;
        messageField.setText("closeServer");
    }

    @FXML
    void startClientConnection(ActionEvent event) {
        messageField.setText("startClient");
    }

    @FXML
    void closeServerAndClientAndProgramm(ActionEvent event) {
        messageField.setText("Everything is closed");
    }

    @FXML
    void showAboutButton(ActionEvent event) {
        messageField.setText("showAboutButton");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo назначить на закрытие окна завешение всего,
        // включая остановку сервера если он был открыть, внутри должен быть метод наподобие is Active
    }

}
