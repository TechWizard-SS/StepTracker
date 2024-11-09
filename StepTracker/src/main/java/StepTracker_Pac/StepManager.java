package StepTracker_Pac;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.logging.Logger;
import java.util.Scanner;

import static StepTracker_Pac.LoggerConfig.logger;

public class StepManager {
    private static final Logger logger = Logger.getLogger(StepManager.class.getName());
    private final StepTracker stepTracker;
    private final Converter converter;
    static Scanner scanner = new Scanner(System.in);
    public StepManager(StepTracker stepTracker, Converter converter) {
        this.stepTracker = stepTracker;
        this.converter = converter;
    }

    public void setNewGoal(StepTracker stepTracker) {
        System.out.println("Введите новую цель по шагам:");
        try {
            int newGoal = Integer.parseInt(scanner.nextLine());
            stepTracker.setStepGoal(newGoal);
        } catch (NumberFormatException e) {
            logger.warning("Цель должна быть числом. Попробуйте снова.");
        }
    }

    public void clearAllSteps() {
        logger.warning("Запрос на очистку всех данных из базы данных.");
        String dbPassword = HibernateUtil.getDbPassword();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пароль для подтверждения очистки базы данных: ");
        String inputPassword = scanner.nextLine();



        // Логируем пароль для отладки
        logger.warning(" используемый пароль базы данных: {}" + dbPassword);

        if (dbPassword == null || dbPassword.isEmpty()) {
            logger.warning("Пароль не найден или пуст.");
            return;
        }

        // Сравниваем введенный пароль с паролем из конфигурации
        if (!inputPassword.equals(dbPassword)) {
            logger.warning("Введен неверный пароль.");
            System.out.println("Неверный пароль. Операция отменена.");
            return;
        }
        else {


            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM StepData").executeUpdate();
                transaction.commit();
                System.out.println("Все данные успешно удалены из базы данных.");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                logger.warning("Ошибка при очистке данных из базы данных" + e);
            } finally {
                session.close();
            }
        }
        }


    public void addSteps(StepTracker stepTracker) {
        Month month = InputHandler.promptForMonth();
        if (month == null) return;
        Integer day = InputHandler.promptForDay(month);
        if (day == null) return;
        Integer steps = InputHandler.promptForSteps();
        if (steps == null) return;
        try {
            stepTracker.addSteps(month, day, steps);
            System.out.println("Шаги успешно добавлены.");
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
        }
    }

    public void displayMonthlyStats(StepTracker stepTracker, Converter converter) {
        Month month = InputHandler.promptForMonth();
        if (month == null) return;

        int totalSteps = stepTracker.getTotalStepsForMonth(month);
        int maxSteps = stepTracker.getMaxStepsForMonth(month);
        double averageSteps = stepTracker.getAverageStepsForMonth(month);

        System.out.println("Общее количество шагов за " + month + ": " + totalSteps);
        System.out.println("Максимальное количество шагов за " + month + ": " + maxSteps);
        System.out.println("Среднее количество шагов за " + month + ": " + averageSteps);
    }
}
