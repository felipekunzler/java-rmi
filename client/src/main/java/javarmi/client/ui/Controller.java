package javarmi.client.ui;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class Controller implements Initializable {

    @FXML
    private JFXDatePicker final_date, initial_date;

    @FXML
    private ImageView  btn_topics, btn_news, btn_news_details, btn_notifications;

    @FXML
    private JFXButton btn_login, btn_addTopic, btn_addNews;

    @FXML
    private AnchorPane topics, news, news_details, notifications, content, menu, login;

    @FXML
    private JFXListView<Label> lv_topics, lv_news, lv_notification;

    @FXML
    private JFXTextField tb_topicName, tb_newsTitle, tb_user;

    @FXML
    private JFXPasswordField tb_password;

    @FXML
    private JFXTextArea ta_newsContent;

    @FXML
    private JFXToggleButton tg_subscriber;

    @FXML
    private DatePicker dp_initialDate, dp_finalDate;

    @FXML
    private JFXRadioButton rb_writer, rb_subscriber, rb_guest;

    @FXML
    private Text txt_newsContent,txt_newsTitle,txt_topicName;

    private void login() {
        if (rb_writer.isSelected()){
            userWriter();
        }
        if(rb_subscriber.isSelected()){
            userSubscriber();
        }
        if(rb_guest.isSelected()){
            userGuest();
        }
        menu.setVisible(true);
        topics.setVisible(true);
    }

    private void showTopics() {
        topics.setVisible(true);
    }

    private void showNews() {
        news.setVisible(true);
    }

    private void showNewsDetails() {
        news_details.setVisible(true);
    }

    private void showNotifications() {
        notifications.setVisible(true);
    }

    private void userWriter(){
        btn_notifications.setVisible(false);
        tg_subscriber.setVisible(false);
        dp_finalDate.setVisible(false); // ??? "Consultar todas as notícias publicadas."
        dp_initialDate.setVisible(false); // ???
    }

    private void userSubscriber(){
        tb_topicName.setVisible(false);
        btn_addTopic.setVisible(false);
        ta_newsContent.setVisible(false);
        tb_newsTitle.setVisible(false);
        btn_addNews.setVisible(false);
    }

    private void userGuest(){
        btn_notifications.setVisible(false);
        tb_topicName.setVisible(false);
        btn_addTopic.setVisible(false);
        tg_subscriber.setVisible(false);
        ta_newsContent.setVisible(false);
        tb_newsTitle.setVisible(false);
        btn_addNews.setVisible(false);
    }

    private void clearWindow(){
        login.setVisible(false);
        topics.setVisible(false);
        news.setVisible(false);
        news_details.setVisible(false);
        notifications.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_login.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearWindow();
                login();
            }
        });
        btn_topics.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearWindow();
                showTopics();
            }
        });
        btn_news.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearWindow();
                showNews();
            }
        });
        btn_news_details.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearWindow();
                showNewsDetails();
            }
        });
        btn_notifications.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearWindow();
                showNotifications();
            }
        });
    }


}
