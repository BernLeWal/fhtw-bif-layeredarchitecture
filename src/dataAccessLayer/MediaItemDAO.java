package dataAccessLayer;

import models.MediaItem;
import models.MediaLogs;

import java.util.List;

public class MediaItemDAO {

    private DataAccess dataAccess;

    public MediaItemDAO() {
        // decide if data is taken from file system or DB
        if (true) {
            dataAccess = new FileAccess();
        } else {
            dataAccess = new DbConnection();
        }
    }

    public List<MediaItem> GetItems() {
        return dataAccess.GetItems();
    }

    public void AddLogToItem(MediaItem item, MediaLogs logs) {
        dataAccess.AddLogToItem(item, logs);
    }
}
