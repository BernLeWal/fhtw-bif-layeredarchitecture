package dataAccessLayer.dao;

import models.MediaFolder;
import models.MediaItem;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IMediaItemDAO {
    MediaItem FindById(Integer itemId) throws SQLException, ClassNotFoundException, IOException;
    MediaItem AddNewItem(String name, String annotation, String url, LocalDateTime creationTime) throws SQLException, IOException, ClassNotFoundException;
    List<MediaItem> GetItems(MediaFolder folder) throws SQLException, ClassNotFoundException, IOException;
}
