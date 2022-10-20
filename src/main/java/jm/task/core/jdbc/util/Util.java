package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {

    private static SessionFactory sessionFactory = null;
    private static Connection connection = null;
    private static Util instans;

    private Util() {
    }

    public static Util getUtil() {
        if (instans == null) {
            instans = new Util();
        }
        return instans;
    }

    public SessionFactory getConnectionHibernate() {
        try {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    //    For JDBS
    public static Connection getConnectionJDBC() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties properties = getProps();
                connection = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties getProps() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(
                Util.class.getResource("/database.properties").toURI()))) {
            properties.load(in);
            return properties;

        } catch (URISyntaxException | IOException e) {
            throw new IOException("Database config file not found", e);
        }
    }
}
