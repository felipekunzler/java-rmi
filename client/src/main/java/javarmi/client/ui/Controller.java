package javarmi.client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private ImageView btn_topics, btn_news, btn_news_details, btn_notifications;

    @FXML
    private AnchorPane topics, news, news_details, notifications, content;

    @FXML
    private JFXListView<Label> topics_listview,news_listview,notification_listview;

    @FXML
    private void handleButtonAction(MouseEvent event){
        topics.setVisible(false);
        news.setVisible(false);
        news_details.setVisible(false);
        notifications.setVisible(false);
        content.setVisible(true);

        if(event.getTarget() == btn_topics){
            topics.setVisible(true);
        }
        if(event.getTarget() == btn_news){
            news.setVisible(true);
        }
        if(event.getTarget() == btn_news_details){
            news_details.setVisible(true);
        }
        if(event.getTarget() == btn_notifications){
            notifications.setVisible(true);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb){
        for (int i = 0; i <= 5; i++){
            try {
                Label lb = new Label("Item "+i);
                news_listview.getItems().add(lb);
            }catch (Exception ex){

            }
        }
        for (int i = 0; i <= 5; i++){
            try {
                Label lb = new Label("Item "+i);
                topics_listview.getItems().add(lb);
            }catch (Exception ex){

            }
        }
        for (int i = 0; i <= 5; i++){
            try {
                Label lb = new Label("Item "+i);
                notification_listview.getItems().add(lb);
            }catch (Exception ex){

            }
        }
    }


}
