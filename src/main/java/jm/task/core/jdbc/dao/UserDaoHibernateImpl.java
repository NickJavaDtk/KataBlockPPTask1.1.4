package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255)," +
                "last_name VARCHAR(255), age TINYINT, PRIMARY KEY (id));";
        runQuery(sql);
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users;";
        if (checkTableIfExists()) {
            runQuery(sql);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        if (checkTableIfExists()) {
            try {
                Session session = Util.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                User users = new User(name, lastName, age);
                session.save(users);
                transaction.commit();
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        if (checkTableIfExists()) {
            try {
                Session session = Util.getSessionFactory().openSession();
                User users = session.find(User.class, id);
                if (users != null) {
                    transaction = session.beginTransaction();
                    session.delete(users);
                    transaction.commit();
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        return session.createQuery("FROM User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users;";
        if (checkTableIfExists()) {
            runQuery(sql);
        }
    }

    private void runQuery(String sql) {
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private boolean checkTableIfExists() {
        String sql = "SELECT count(*) FROM information_schema.TABLES " +
                "WHERE (TABLE_SCHEMA = 'kata') AND (TABLE_NAME = 'Users');";
        Session session = Util.getSessionFactory().openSession();
        Object[] array = session.createNativeQuery(sql).stream().toArray();
        return array[0].equals(BigInteger.ONE);
    }
}
