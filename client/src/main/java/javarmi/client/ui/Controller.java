package javarmi.client.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javarmi.client.core.QueueConsumer;
import javarmi.client.core.WrapperService;
import javarmi.core.Config;
import javarmi.core.Service;
import javarmi.core.Util;
import javarmi.core.model.News;
import javarmi.core.model.Topic;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView btn_topics, btn_news, btn_news_details, btn_notifications;

    @FXML
    private JFXButton btn_login, btn_addTopic, btn_addNews;

    @FXML
    private AnchorPane topics, news, news_details, notifications, menu, login;

    @FXML
    private JFXListView<Label> lv_topics;

    @FXML
    private JFXListView<News> lv_news, lv_notification;

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
    private Text txt_newsContent, txt_newsTitle, txt_topicNameLabel, txt_topicNews, txt_topicName;

    @FXML
    private JFXComboBox<Label> cb_topic;

    private WrapperService service;
    private QueueConsumer queueConsumer;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        queueConsumer = new QueueConsumer(Config.getRabbitHost(), Config.getRabbitUser(), Config.getRabbitPassword());
        service = lookupService();

        btn_login.setOnAction(e -> login());
        btn_topics.setOnMouseClicked(e -> {
            clearWindow();
            showTopics();
        });
        btn_news.setOnMouseClicked(e -> showNews());
        btn_news_details.setOnMouseClicked(e -> showNewsDetails());
        btn_notifications.setOnMouseClicked(e -> {
            clearWindow();
            showNotifications();
        });

        handleNewsClicked(lv_news);
        handleNewsClicked(lv_notification);

        lv_topics.setOnMouseClicked(e -> {
            if (!user.isWriter() && e.getClickCount() == 2) {
                String topic = lv_topics.getSelectionModel().getSelectedItem().getText();
                txt_topicName.setText(topic);
                tg_subscriber.setDisable(false);
                tg_subscriber.setSelected(service.isSubscribed(topic, user.getUser()));
                showNews();
            }
        });
        tg_subscriber.setOnAction(e -> {
            if (tg_subscriber.isSelected()) {
                service.subscribeTopic(txt_topicName.getText(), user.getUser());
            }
            else {
                service.unsubscribeTopic(txt_topicName.getText(), user.getUser());
            }
        });
        dp_initialDate.setOnAction(e -> updateNews());
        dp_finalDate.setOnAction(e -> updateNews());
        toggleGroup(rb_guest, true, true);
        toggleGroup(rb_subscriber, false, true);
        toggleGroup(rb_writer, false, false);
    }

    private void toggleGroup(JFXRadioButton rb, boolean disableUser, boolean disablePassword) {
        rb.setOnMouseClicked(e -> {
            tb_password.setDisable(disablePassword);
            tb_user.setDisable(disableUser);
        });
    }

    private void handleNewsClicked(JFXListView<News> lv) {
        lv.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                News news = lv.getSelectionModel().getSelectedItem();
                txt_newsTitle.setText(news.getTitle());
                txt_newsContent.setText(news.getFormattedContent());
                showNewsDetails();
            }
        });
    }

    private void handleUpcomingNews(News news) {
        Platform.runLater(() -> {
            ObservableList<News> items = lv_notification.getItems();
            if (items.size() >= Config.getMaxNews()) {
                items.remove(items.size() - 1);
            }
            items.add(0, news);
        });
    }

    @FXML
    public void onAddTopic() throws IOException {
        String topic = tb_topicName.getText();
        if (StringUtils.isNotBlank(topic)) {
            service.addTopic(new Topic(topic), user.getPassword());
            updateTopics();
            tb_topicName.clear();
        }
    }

    @FXML
    public void onAddNews() throws IOException {
        Label selectedItem = cb_topic.getSelectionModel().getSelectedItem();
        String title = tb_newsTitle.getText();
        String content = ta_newsContent.getText();
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content) && selectedItem != null) {
            if (title.length() + content.length() <= News.MAX_CONTENT_SIZE) {
                News news = new News();
                news.setTopicName(selectedItem.getText());
                news.setTitle(title);
                news.setContent(content);
                news.setPublisher(user.getUser());
                service.addNews(news, user.getPassword());
                updateNews();
                cb_topic.getSelectionModel().clearSelection();
                tb_newsTitle.clear();
                ta_newsContent.clear();
            }
        }
    }

    private void updateTopics() {
        lv_topics.getItems().removeIf(i -> true);
        for (Topic topic : service.getTopics()) {
            lv_topics.getItems().add(new Label(topic.getName()));
        }
    }

    private void updateNews() {
        List<News> newsList;
        if (user.isWriter()) {
            int selected = cb_topic.getSelectionModel().getSelectedIndex();
            cb_topic.getItems().removeIf(i -> true);
            for (Topic topic : service.getTopics()) {
                cb_topic.getItems().add(new Label(topic.getName()));
            }
            if (!cb_topic.getItems().isEmpty()) {
                cb_topic.getSelectionModel().select(selected);
            }
            newsList = service.getNews(user.getPassword());
        }
        else {
            LocalDateTime start = defaultIfNull(dp_initialDate, LocalDate.MIN).atStartOfDay();
            LocalDateTime end = defaultIfNull(dp_finalDate, LocalDate.MAX).atTime(LocalTime.MAX);
            newsList = service.getNews(start, end, txt_topicName.getText());
        }
        lv_news.getItems().removeIf(i -> true);
        for (News news : newsList) {
            lv_news.getItems().add(news);
        }
    }

    private LocalDate defaultIfNull(DatePicker dp, LocalDate defaultDate) {
        return ObjectUtils.defaultIfNull(dp.getValue(), defaultDate);
    }

    private WrapperService lookupService() {
        try {
            Service service = (Service) Naming.lookup(Util.getRemoteBinding());
            return new WrapperService(service);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        String user = tb_user.getText();
        String pass = tb_password.getText();
        if (rb_writer.isSelected()) {
            if (StringUtils.isAnyBlank(user, pass) || !service.checkPassword(pass)) return;
            this.user = new User(user, pass, User.Group.WRITER);
            userWriter();
        }
        else if (rb_subscriber.isSelected()) {
            if (StringUtils.isBlank(user)) return;
            this.user = new User(user, pass, User.Group.SUBSCRIBER);
            queueConsumer.consume(user, this::handleUpcomingNews);
            userSubscriber();
        }
        else {
            this.user = new User(user, pass, User.Group.GUEST);
            userGuest();
        }
        clearWindow();
        menu.setVisible(true);
        showTopics();
    }

    private void showTopics() {
        updateTopics();
        topics.setVisible(true);
    }

    private void showNews() {
        clearWindow();
        updateNews();
        news.setVisible(true);
    }

    private void showNewsDetails() {
        clearWindow();
        news_details.setVisible(true);
    }

    private void showNotifications() {
        notifications.setVisible(true);
    }

    private void userWriter() {
        txt_topicNameLabel.setText("All News");
        hide(btn_notifications, tg_subscriber, dp_finalDate, dp_initialDate, txt_topicNews);
    }

    private void userSubscriber() {
        hide(tb_topicName, btn_addTopic, ta_newsContent, tb_newsTitle, btn_addNews, cb_topic);
    }

    private void userGuest() {
        hide(btn_notifications, tb_topicName, btn_addTopic, tg_subscriber, ta_newsContent, tb_newsTitle, btn_addNews, cb_topic);
    }

    private void clearWindow() {
        hide(login, topics, news, news_details, notifications);
    }

    private void hide(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(false);
        }
    }

}
