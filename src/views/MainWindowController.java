package views;

import businessLogic.JavaAppManager;
import businessLogic.JavaAppManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.MediaFolder;
import models.MediaItem;

import java.util.List;

public class MainWindowController {

    public TextField searchField;
    public ListView<MediaItem> listMediaItems;

    private ObservableList<MediaItem> mediaItems;
    private JavaAppManager mediaManager;
    private MediaItem currentItem;
    private MediaFolder folder;

    @FXML
    void initialize() {
        initController();
        initListView();
    }

    private void initController() {
        this.mediaManager = JavaAppManagerFactory.GetFactoryManager();
        folder = mediaManager.GetMediaFolder("Get Media Folder From Disk");
    }

    private void initListView() {
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

    public void searchAction(ActionEvent actionEvent) {
        mediaItems.clear();
        List<MediaItem> items = mediaManager.SearchForItems(searchField.textProperty().getValue(), folder, false);

        mediaItems.addAll(items);
    }

    @FXML
    public void clearAction(ActionEvent actionEvent) {
        mediaItems.clear();
        searchField.textProperty().setValue("");

        mediaItems.addAll(mediaManager.GetItems(folder));
    }
}
