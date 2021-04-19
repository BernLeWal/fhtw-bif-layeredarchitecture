package dataAccessLayer.common;

import dataAccessLayer.dao.IMediaItemDAO;
import dataAccessLayer.dao.IMediaLogDAO;
import dataAccessLayer.fileServer.FileAccess;
import dataAccessLayer.fileServer.MediaItemFileDAO;
import dataAccessLayer.fileServer.MediaLogFileDAO;
import dataAccessLayer.sqlServer.Database;
import dataAccessLayer.sqlServer.MediaItemSqlDAO;
import dataAccessLayer.sqlServer.MediaLogSqlDAO;

import java.io.IOException;

public class DALFactory {
    private static Boolean useFileSystem = false;
    private static String assemblyName;
    private static ClassLoader dalPackage;
    private static IDatabase database;
    private static IFileAccess fileAccess;

    // load DAL assembly
    public static void Init() throws IOException {
        useFileSystem = Boolean.parseBoolean(ConfigurationManager.GetConfigPropertyValue("useFileSystem"));
        assemblyName = ConfigurationManager.GetConfigPropertyValue("DALSqlAssembly");

        if (useFileSystem) {
            assemblyName = ConfigurationManager.GetConfigPropertyValue("DALFileAssembly");
        }
    }

    // create database object with connection string from config
    public static IDatabase GetDatabase() throws IOException {
        if (database == null) {
            database = CreateDatabase();
        }
        return database;
    }

    private static IDatabase CreateDatabase() throws IOException {
        String connectionString = ConfigurationManager.GetConfigPropertyValue("PostgresSQLConnectionString");
        return CreateDatabase(connectionString);
    }

    // create database object with specific connection string
    private static IDatabase CreateDatabase(String connectionString) {
        try {
            Class<Database> cls = (Class<Database>) Class.forName(assemblyName + ".Database");
            return cls.getConstructor(String.class).newInstance(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IFileAccess GetFileAccess() throws IOException {
        if (fileAccess == null) {
            fileAccess = CreateFileAccess();
        }
        return fileAccess;
    }

    private static IFileAccess CreateFileAccess() throws IOException {
        String startFolder = ConfigurationManager.GetConfigPropertyValue("StartFolderFilePath");
        return CreateFileAccess(startFolder);
    }

    private static IFileAccess CreateFileAccess(String startFolder) {
        try {
            Class<FileAccess> cls = (Class<FileAccess>) Class.forName(assemblyName + ".FileAccess");
            return cls.getConstructor(String.class).newInstance(startFolder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // create media item sql/file dao object
    public static IMediaItemDAO CreateMediaItemDAO() {
        try {
            if (useFileSystem) {
                Class<MediaItemFileDAO> cls = (Class<MediaItemFileDAO>) Class.forName(assemblyName + ".MediaItemFileDAO");
                return cls.getConstructor().newInstance();
            }
            Class<MediaItemSqlDAO> cls = (Class<MediaItemSqlDAO>) Class.forName(assemblyName + ".MediaItemSqlDAO");
            return cls.getConstructor().newInstance();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // create media log sql/file dao object
    public static IMediaLogDAO CreateMediaLogDAO() {
        try {
            if (useFileSystem) {
                Class<MediaLogFileDAO> cls = (Class<MediaLogFileDAO>) Class.forName(assemblyName + ".MediaLogFileDAO");
                return cls.getConstructor().newInstance();
            }
            Class<MediaLogSqlDAO> cls = (Class<MediaLogSqlDAO>) Class.forName(assemblyName + ".MediaLogSqlDAO");
            return cls.getConstructor().newInstance();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
