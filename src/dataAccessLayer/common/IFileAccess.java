package dataAccessLayer.common;

import models.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IFileAccess {
    int CreateNewMediaItemFile(String name, String annotation, String url, LocalDateTime creationTime) throws IOException;
    int CreateNewMediaLogFile(String logText, int mediaItemId) throws IOException;
    List<File> SearchFiles(String searchTerm, MediaTypes searchType) throws IOException;
    List<File> GetAllFiles(MediaTypes searchType);
}
