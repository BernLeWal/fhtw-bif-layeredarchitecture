package dataAccessLayer;

import models.MediaItem;
import models.MediaLogs;

import java.util.List;

public interface DataAccess {
    public List<MediaItem> GetItems();
    public void AddLogToItem(MediaItem item, MediaLogs logs);
}
