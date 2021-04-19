package views;

import businessLogic.JavaAppManager;
import businessLogic.JavaAppManagerFactory;
import businessLogic.NameGenerator;
import dataAccessLayer.common.DALFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import models.MediaFolder;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public TextField searchField;
    public ListView<MediaItem> listMediaItems;
    public Button genItemLog;

    private ObservableList<MediaItem> mediaItems;
    private JavaAppManager mediaManager;
    private MediaItem currentItem;
    private MediaFolder folder;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DALFactory.Init();
        initController();
        initListView();
        genItemLog.disableProperty().bind(listMediaItems.getSelectionModel().selectedItemProperty().isNull());
    }

    private void initController() {
        this.mediaManager = JavaAppManagerFactory.GetFactoryManager();
        folder = mediaManager.GetMediaFolder("Get Media Folder From Disk");
    }

    private void initListView() throws SQLException, IOException, ClassNotFoundException {
        mediaItems = FXCollections.observableArrayList();
        mediaItems.addAll(mediaManager.GetItems(folder));
        listMediaItems.setItems(mediaItems);

        // format cells of listview to only show the name
        listMediaItems.setCellFactory(param -> new ListCell<MediaItem>() {
            @Override
            protected void updateItem(MediaItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // set current selected item
        listMediaItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                currentItem = newValue;
            }
        });
    }

    public void searchAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        mediaItems.clear();
        List<MediaItem> items = mediaManager.SearchForItems(searchField.textProperty().getValue(), folder, false);

        mediaItems.addAll(items);
    }

    @FXML
    public void clearAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        mediaItems.clear();
        searchField.textProperty().setValue("");

        mediaItems.addAll(mediaManager.GetItems(folder));
    }

    @FXML
    public void genItemAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        MediaItem genItem = mediaManager.CreateItem(NameGenerator.Generate(4), NameGenerator.Generate(8), NameGenerator.Generate(16), LocalDateTime.now());
        mediaItems.add(genItem);
    }

    public void genItemLogAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        MediaLog genLog = mediaManager.CreateItemLog(NameGenerator.Generate(45), currentItem);
    }
}
