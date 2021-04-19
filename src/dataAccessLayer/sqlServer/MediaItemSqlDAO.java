package dataAccessLayer.sqlServer;

import dataAccessLayer.common.DALFactory;
import dataAccessLayer.common.IDatabase;
import dataAccessLayer.dao.IMediaItemDAO;
import models.MediaFolder;
import models.MediaItem;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MediaItemSqlDAO implements IMediaItemDAO {
    private final String SQL_FIND_BY_ID = "SELECT * FROM public.\"MediaItems\" WHERE \"Id\"=CAST (? AS INTEGER);";
    private final String SQL_GET_ALL_ITEMS = "SELECT * FROM public.\"MediaItems\";";
    private final String SQL_INSERT_NEW_ITEM = "INSERT INTO public.\"MediaItems\" (\"Name\", \"Annotation\", \"Url\", \"CreationTime\") VALUES (?, ?, ?, ?);";

    private IDatabase database;

    public MediaItemSqlDAO() throws IOException {
        this(DALFactory.GetDatabase());
    }

    public MediaItemSqlDAO(IDatabase database) {
        this.database = database;
    }

    public MediaItem AddNewItem(MediaItem item) throws SQLException, ClassNotFoundException {
        return AddNewItem(item.Name, item.Annotation, item.Url, item.CreationTime);
    }

    @Override
    public MediaItem AddNewItem(String name, String annotation, String url, LocalDateTime creationTime) throws SQLException, ClassNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(name);
        parameters.add(annotation);
        parameters.add(url);
        parameters.add(creationTime.format(formatter));

        int result = database.InsertNew(SQL_INSERT_NEW_ITEM, parameters);
        return FindById(result);
    }

    @Override
    public MediaItem FindById(Integer itemId) throws SQLException, ClassNotFoundException {
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(itemId);

        List<MediaItem> mediaItems = database.ExecuteMediaItemsReader(SQL_FIND_BY_ID, parameters);
        return mediaItems.stream().findFirst().get();
    }

    @Override
    public List<MediaItem> GetItems(MediaFolder folder) throws SQLException, ClassNotFoundException {
        return database.ExecuteMediaItemsReader(SQL_GET_ALL_ITEMS);
    }
}
