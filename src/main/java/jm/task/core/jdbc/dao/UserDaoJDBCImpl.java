package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255)," +
                "last_name VARCHAR(255), age TINYINT, PRIMARY KEY (id));";
        runQuery(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users;";
        if (checkTableIfExists()) {
            runQuery(sql);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users(name, last_name, age) VALUES (?, ?, ?);";
        if (checkTableIfExists()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                System.out.println("User с именем " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?;";
        if (checkTableIfExists()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users;";
        List<User> userList = new ArrayList<>();
        if (checkTableIfExists()) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    User user = new User(resultSet.getString(2),
                            resultSet.getString(3), resultSet.getByte(4));
                    userList.add(user);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        if (checkTableIfExists()) {
            String sql = "TRUNCATE TABLE Users;";
            runQuery(sql);
        }
    }

    private boolean checkTableIfExists() {
        String sql = "SELECT count(*) FROM information_schema.TABLES " +
                "WHERE (TABLE_SCHEMA = 'kata') AND (TABLE_NAME = 'Users');";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void runQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
