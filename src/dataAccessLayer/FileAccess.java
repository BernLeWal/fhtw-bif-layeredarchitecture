package dataAccessLayer;

import models.MediaItem;
import models.MediaLogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccess implements DataAccess {

    private String filePath;

    public FileAccess() {
        // get filepath from config file
        filePath = "...";
    }

    @Override
    public List<MediaItem> GetItems() {
        MediaItem[] mediaItems = { new MediaItem("Item1"),
                new MediaItem("Item2"),
                new MediaItem("Another"),
                new MediaItem("SWEI"),
                new MediaItem("FHTW")
        };
        return new ArrayList<MediaItem>(Arrays.asList(mediaItems));
    }

    @Override
    public void AddLogToItem(MediaItem item, MediaLogs logs) {
        // Insert/Update logic here
    }
}
