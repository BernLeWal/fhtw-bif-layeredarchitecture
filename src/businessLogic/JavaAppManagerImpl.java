package businessLogic;

import dataAccessLayer.MediaItemDAO;
import models.MediaFolder;
import models.MediaItem;
import models.MediaLogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class JavaAppManagerImpl implements JavaAppManager {

    private MediaItemDAO mediaItemDAO = new MediaItemDAO();

    @Override
    public MediaFolder GetMediaFolder(String url) {
        // usally located somewhere on the disk
        return new MediaFolder();
    }

    @Override
    public List<MediaItem> GetItems(MediaFolder folder) {
        return mediaItemDAO.GetItems();
    }

    @Override
    public List<MediaItem> SearchForItems(String itemName, MediaFolder folder, boolean caseSensitive) {
        List<MediaItem> items = GetItems(folder);

        if (caseSensitive) {
            return items
                    .stream()
                    .filter(x -> x.Name.contains(itemName))
                    .collect(Collectors.toList());
        }

        return items
                .stream()
                .filter(x -> x.Name.toLowerCase().contains(itemName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void AddItemLog(MediaItem item, MediaLogs logs) {
        mediaItemDAO.AddLogToItem(item, logs);
    }
}