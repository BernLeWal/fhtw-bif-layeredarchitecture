package dataAccessLayer.fileServer;

import dataAccessLayer.common.DALFactory;
import dataAccessLayer.common.IFileAccess;
import dataAccessLayer.dao.IMediaItemDAO;
import models.MediaFolder;
import models.MediaItem;
import models.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MediaItemFileDAO implements IMediaItemDAO {

    private IFileAccess fileAccess;

    public MediaItemFileDAO() throws IOException {
        this(DALFactory.GetFileAccess());
    }

    public MediaItemFileDAO(IFileAccess fileAccess) {
        this.fileAccess = fileAccess;
    }

    @Override
    public MediaItem AddNewItem(String name, String annotation, String url, LocalDateTime creationTime) throws IOException {
        int id = fileAccess.CreateNewMediaItemFile(name, annotation, url, creationTime);
        return new MediaItem(id, name, annotation, url, creationTime);
    }

    @Override
    public MediaItem FindById(Integer itemId) throws IOException {
        List<File> foundFiles = fileAccess.SearchFiles(itemId.toString(), MediaTypes.MediaItem);
        return QueryFromFileSystem(foundFiles).stream().findFirst().get();
    }

    @Override
    public List<MediaItem> GetItems(MediaFolder folder) throws IOException {
        List<File> foundFiles = fileAccess.GetAllFiles(MediaTypes.MediaItem);
        return QueryFromFileSystem(foundFiles);
    }

    private List<MediaItem> QueryFromFileSystem(List<File> foundFiles) throws IOException {
        List<MediaItem> foundMediaItems = new ArrayList<MediaItem>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (File file : foundFiles) {
            List<String> fileLines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            foundMediaItems.add(new MediaItem(
                Integer.parseInt(fileLines.get(0)),        // id
                fileLines.get(1),                   // name
                fileLines.get(2),                   // annotation
                fileLines.get(3),                   // url
                LocalDateTime.parse(fileLines.get(4), formatter)    // creation date
            ));
        }

        return foundMediaItems;
    }
}
