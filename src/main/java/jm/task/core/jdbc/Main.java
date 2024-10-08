package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            UserServiceImpl service = new UserServiceImpl();
            service.createUsersTable();
            service.saveUser("Вася", "Пупкин", Byte.valueOf("32"));
            service.saveUser("Мотя", "тетя", Byte.valueOf("88"));
            service.saveUser("Иван", "Иванов", Byte.valueOf("41"));
            service.saveUser("Петр", "Петров", Byte.valueOf("47"));
            service.getAllUsers().stream().forEach(System.out::println);
            service.cleanUsersTable();
            service.dropUsersTable();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
