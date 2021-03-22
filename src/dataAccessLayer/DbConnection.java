package dataAccessLayer;

import models.MediaItem;
import models.MediaLogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbConnection implements DataAccess {

    private String connectionString;
    private Object connectionObject;

    public DbConnection() {
        // get connection string from configfile
        connectionString = "...";
        // establish connection with DB
        connectionObject = new Object();
    }

    @Override
    public List<MediaItem> GetItems() {
        //List<Object> itemsList = connectionObject.SelectItems();
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
