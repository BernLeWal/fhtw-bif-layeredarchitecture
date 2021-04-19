package dataAccessLayer.sqlServer;

import dataAccessLayer.common.DALFactory;
import dataAccessLayer.common.IDatabase;
import dataAccessLayer.dao.IMediaItemDAO;
import dataAccessLayer.dao.IMediaLogDAO;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaLogSqlDAO implements IMediaLogDAO {
    private final String SQL_FIND_BY_ID = "SELECT * FROM public.\"MediaLogs\" WHERE \"Id\"=CAST (? AS INTEGER)";
    private final String SQL_FIND_BY_MEDIA_ITEM = "SELECT * FROM public.\"MediaLogs\" WHERE \"MediaItemId\"=CAST (? AS INTEGER)";

    private final String SQL_INSERT_NEW_ITEM = "INSERT INTO public.\"MediaLogs\" (\"LogText\", \"MediaItemId\") VALUES (?, CAST (? AS INTEGER));";

    private IDatabase database;
    private IMediaItemDAO mediaItemDAO;

    public MediaLogSqlDAO() throws IOException {
        this.database = DALFactory.GetDatabase();
        this.mediaItemDAO = DALFactory.CreateMediaItemDAO();
    }

    @Override
    public MediaLog FindById(Integer logId) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(logId);

        List<MediaLog> mediaLogs = database.ExecuteMediaLogsReader(SQL_FIND_BY_ID, parameters, mediaItemDAO);
        return mediaLogs.stream().findFirst().get();
    }

    @Override
    public MediaLog AddNewItemLog(MediaLog log) throws SQLException, ClassNotFoundException, IOException {
        return AddNewItemLog(log.LogText, log.LogMediaItem);
    }

    @Override
    public MediaLog AddNewItemLog(String logText, MediaItem item) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(logText);
        parameters.add(item.getId());

        int result = database.InsertNew(SQL_INSERT_NEW_ITEM, parameters);
        return FindById(result);
    }

    @Override
    public List<MediaLog> GetLogsForItem(MediaItem item) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(item.getId());

        return database.ExecuteMediaLogsReader(SQL_FIND_BY_MEDIA_ITEM, mediaItemDAO);
    }
}
