package dataAccessLayer.dao;

import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IMediaLogDAO {
    MediaLog FindById(Integer logId) throws SQLException, ClassNotFoundException, IOException;
    MediaLog AddNewItemLog(String logText, MediaItem item) throws SQLException, ClassNotFoundException, IOException;
    MediaLog AddNewItemLog(MediaLog log) throws SQLException, ClassNotFoundException, IOException;
    List<MediaLog> GetLogsForItem(MediaItem item) throws SQLException, ClassNotFoundException, IOException;
}
