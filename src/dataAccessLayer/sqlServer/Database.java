package dataAccessLayer.sqlServer;

import dataAccessLayer.common.IDatabase;
import dataAccessLayer.dao.IMediaItemDAO;
import models.MediaItem;
import models.MediaLog;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {

    private String connectionString;

    public Database(String connectionString) {
        this.connectionString = connectionString;
    }

    private Connection CreateOpenConnection() {
        try {
            Connection connection = DriverManager.getConnection(connectionString, "postgres", "password");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int InsertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            // dynamically add parameter
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i).toString());
            }
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Creating data failed, no ID obtained. " + sqlQuery);
    }

    @Override
    public List<MediaItem> ExecuteMediaItemsReader(String sqlQuery) throws SQLException {
        try (   Connection connection = CreateOpenConnection();
                Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(sqlQuery);
            return QueryMediaItemsFromDb(result);
        }
    }

    @Override
    public List<MediaItem> ExecuteMediaItemsReader(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            // dynamically add parameter
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i).toString());
            }
            ResultSet result = preparedStatement.executeQuery();
            return QueryMediaItemsFromDb(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Reading MediaItem data failed. " + sqlQuery);
    }

    private List<MediaItem> QueryMediaItemsFromDb(ResultSet result) throws SQLException {
        List<MediaItem> mediaItemList = new ArrayList<MediaItem>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (result.next()) {
            mediaItemList.add(new MediaItem(
                    result.getInt("Id"),
                    result.getString("Name"),
                    result.getString("Annotation"),
                    result.getString("Url"),
                    LocalDateTime.parse(result.getString("CreationTime"), formatter)
            ));
        }

        return mediaItemList;
    }

    @Override
    public List<MediaLog> ExecuteMediaLogsReader(String sqlQuery, IMediaItemDAO mediaItemDAO) throws SQLException, ClassNotFoundException, IOException {
        try (   Connection connection = CreateOpenConnection();
                Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(sqlQuery);
            return QueryMediaLogsFromDb(result, mediaItemDAO);
        }
    }

    @Override
    public List<MediaLog> ExecuteMediaLogsReader(String sqlQuery, ArrayList<Object> parameters, IMediaItemDAO mediaItemDAO) throws ClassNotFoundException, IOException, SQLException {
        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            // dynamically add parameter
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i).toString());
            }
            ResultSet result = preparedStatement.executeQuery();
            return QueryMediaLogsFromDb(result, mediaItemDAO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Reading MediaLog data failed. " + sqlQuery);
    }

    private List<MediaLog> QueryMediaLogsFromDb(ResultSet result, IMediaItemDAO mediaItemDAO) throws SQLException, ClassNotFoundException, IOException {
        List<MediaLog> mediaLogList = new ArrayList<MediaLog>();

        while (result.next()) {
            mediaLogList.add(new MediaLog(
                    result.getInt("Id"),
                    result.getString("LogText"),
                    mediaItemDAO.FindById(result.getInt("MediaItemId"))
            ));
        }

        return mediaLogList;
    }
}
