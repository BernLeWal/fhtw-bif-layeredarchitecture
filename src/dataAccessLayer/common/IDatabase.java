package dataAccessLayer.common;

import dataAccessLayer.dao.IMediaItemDAO;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDatabase {
    int InsertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException;
    List<MediaItem> ExecuteMediaItemsReader(String sqlQuery) throws SQLException, ClassNotFoundException;
    List<MediaItem> ExecuteMediaItemsReader(String sqlQuery, ArrayList<Object> parameters) throws SQLException, ClassNotFoundException;
    List<MediaLog> ExecuteMediaLogsReader(String sqlQuery, IMediaItemDAO mediaItemDAO) throws SQLException, ClassNotFoundException, IOException;
    List<MediaLog> ExecuteMediaLogsReader(String sqlQuery, ArrayList<Object> parameters, IMediaItemDAO mediaItemDAO) throws SQLException, ClassNotFoundException, IOException;
}
