package StepTracker_Pac;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static String dbPassword;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // Указываем путь k xml файлу

            // Получаем свойства конфигурации
            Properties properties = configuration.getProperties();
            dbPassword = properties.getProperty("hibernate.connection.password");

            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(StepData.class)
                    .buildSessionFactory();

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }


    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutDown(){
        getSessionFactory().close();
    }
    public static String getDbPassword() {
        return dbPassword;
    }

}
