package businessLogic;

import models.MediaFolder;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface JavaAppManager {
    MediaFolder GetMediaFolder(String url);
    List<MediaItem> GetItems(MediaFolder folder) throws SQLException, IOException, ClassNotFoundException;
    List<MediaItem> SearchForItems(String itemName, MediaFolder folder, boolean caseSensitive) throws SQLException, IOException, ClassNotFoundException;
    MediaLog CreateItemLog(String logText, MediaItem item) throws SQLException, IOException, ClassNotFoundException;
    MediaItem CreateItem(String name, String annotation, String url, LocalDateTime creationDate) throws SQLException, IOException, ClassNotFoundException;
}