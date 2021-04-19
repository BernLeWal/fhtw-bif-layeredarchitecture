package dataAccessLayer.fileServer;

import dataAccessLayer.common.DALFactory;
import dataAccessLayer.common.IFileAccess;
import dataAccessLayer.dao.IMediaItemDAO;
import dataAccessLayer.dao.IMediaLogDAO;
import models.MediaItem;
import models.MediaLog;
import models.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaLogFileDAO implements IMediaLogDAO {

    private IFileAccess fileAccess;
    private IMediaItemDAO mediaItemDAO;

    public MediaLogFileDAO() throws IOException {
        this.fileAccess = DALFactory.GetFileAccess();
        this.mediaItemDAO = DALFactory.CreateMediaItemDAO();
    }

    @Override
    public MediaLog AddNewItemLog(MediaLog log) throws IOException {
        return AddNewItemLog(log.LogText, log.LogMediaItem);
    }

    @Override
    public MediaLog AddNewItemLog(String logText, MediaItem item) throws IOException {
        int id = fileAccess.CreateNewMediaLogFile(logText, item.Id);
        return new MediaLog(id, logText, item);
    }

    @Override
    public MediaLog FindById(Integer logId) throws SQLException, IOException, ClassNotFoundException {
        List<File> foundFiles = fileAccess.SearchFiles(logId.toString(), MediaTypes.MediaLog);
        return QueryFromFileSystem(foundFiles).stream().findFirst().get();
    }

    @Override
    public List<MediaLog> GetLogsForItem(MediaItem item) throws SQLException, IOException, ClassNotFoundException {
        List<File> foundFiles = fileAccess.SearchFiles(item.Id.toString(), MediaTypes.MediaLog);
        return QueryFromFileSystem(foundFiles);
    }

    private List<MediaLog> QueryFromFileSystem(List<File> foundFiles) throws SQLException, IOException, ClassNotFoundException {
        List<MediaLog> foundMediaLogs = new ArrayList<MediaLog>();

        for (File file : foundFiles) {
            List<String> fileLines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            foundMediaLogs.add(new MediaLog(
                    Integer.parseInt(fileLines.get(0)),        // id
                    fileLines.get(1),                   // logText
                    mediaItemDAO.FindById(Integer.parseInt(fileLines.get(2)))         // mediaItemId
            ));
        }

        return foundMediaLogs;
    }
}
