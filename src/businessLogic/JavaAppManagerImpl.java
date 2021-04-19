package businessLogic;

import dataAccessLayer.common.DALFactory;
import dataAccessLayer.dao.IMediaItemDAO;
import dataAccessLayer.dao.IMediaLogDAO;
import models.MediaFolder;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class JavaAppManagerImpl implements JavaAppManager {

    @Override
    public MediaFolder GetMediaFolder(String url) {
        // usally located somewhere on the disk
        return new MediaFolder();
    }

    @Override
    public List<MediaItem> GetItems(MediaFolder folder) throws SQLException, IOException, ClassNotFoundException {
        IMediaItemDAO mediaItemDao = DALFactory.CreateMediaItemDAO();
        return mediaItemDao.GetItems(folder);
    }

    @Override
    public List<MediaItem> SearchForItems(String itemName, MediaFolder folder, boolean caseSensitive) throws SQLException, IOException, ClassNotFoundException {
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
    public MediaLog CreateItemLog(String logText, MediaItem item) throws SQLException, IOException, ClassNotFoundException {
        IMediaLogDAO mediaLogDao = DALFactory.CreateMediaLogDAO();
        return mediaLogDao.AddNewItemLog(logText, item);
    }

    @Override
    public MediaItem CreateItem(String name, String annotation, String url, LocalDateTime creationDate) throws SQLException, IOException, ClassNotFoundException {
        IMediaItemDAO mediaItemDao = DALFactory.CreateMediaItemDAO();
        return mediaItemDao.AddNewItem(name, annotation, url, creationDate);
    }
}