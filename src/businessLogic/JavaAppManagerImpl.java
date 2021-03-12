package businessLogic;

import models.MediaFolder;
import models.MediaItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class JavaAppManagerImpl implements JavaAppManager {
    @Override
    public MediaFolder GetMediaFolder(String url) {
        // usally located somewhere on the disk
        return new MediaFolder();
    }

    @Override
    public List<MediaItem> GetItems(MediaFolder folder) {
        // usually querying the disk, or from a DB, or ...
        MediaItem[] mediaItems = { new MediaItem("Item1"),
                new MediaItem("Item2"),
                new MediaItem("Another"),
                new MediaItem("SWEI"),
                new MediaItem("FHTW")
        };
        return new ArrayList<MediaItem>(Arrays.asList(mediaItems));
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
}