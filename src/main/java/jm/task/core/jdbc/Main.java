package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try (Connection connection = Util.getConnection()) {
//            UserServiceImpl service = new UserServiceImpl();
//            service.createUsersTable();
//            service.saveUser("Вася", "Пупкин",  Byte.valueOf("7"));
//            service.saveUser("тетя", "Мотя",  Byte.valueOf("64"));
//            service.saveUser("дядя", "Вася",  Byte.valueOf("57"));
//            service.saveUser("бабка", "На скамейке",  Byte.valueOf("81"));
//            service.getAllUsers().stream().forEach(System.out::println);
//            service.cleanUsersTable();
//            service.dropUsersTable();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            UserServiceImpl service = new UserServiceImpl();
            service.createUsersTable();
            service.dropUsersTable();
            service.createUsersTable();
            service.saveUser("Вася", "Пупкин", Byte.valueOf("32"));
            service.saveUser("тетя", "Мотя", Byte.valueOf("88"));
            service.saveUser("дядя", "Петя", Byte.  valueOf("41"));
            service.saveUser("дядя", "Петя", Byte.  valueOf("41"));
            service.getAllUsers().stream().forEach(System.out :: println);
            service.removeUserById(1L);
            service.getAllUsers().stream().forEach(System.out :: println);
            service.cleanUsersTable();
            service.dropUsersTable();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
