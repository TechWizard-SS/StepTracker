package StepTracker_Pac;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class StepTracker {
    private static final Logger logger = Logger.getLogger(StepTracker.class.getName());

    private int stepGoal;

    public StepTracker(int stepGoal) {
        this.stepGoal = stepGoal;
    }

    public void setStepGoal(int stepGoal) {
        if (stepGoal > 0) {
            this.stepGoal = stepGoal;
            logger.info("Цель по шагам установлена на: " + stepGoal);
        } else {
            logger.warning("Попытка установить некорректную цель по шагам: " + stepGoal);
            throw new IllegalArgumentException("Цель по шагам должна быть положительным числом.");
        }
    }

    public void addSteps(Month month, int day, int steps) {
        validateDayAndSteps(month, day, steps);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                StepData stepData = new StepData();
                stepData.setMonth(month.name());
                stepData.setDay(day);
                stepData.setSteps(steps);
                stepData.setGoal(stepGoal);

                session.save(stepData);
                transaction.commit();
                logger.info("Добавлено " + steps + " шагов в " + month + ", день " + day);
            } catch (Exception e) {
                transaction.rollback();
                logger.warning("Ошибка при добавлении шагов: " + e.getMessage());
                throw e;
            }
        }
    }

    public static void clearAllSteps() {
        executeTransaction(session -> {
            session.createQuery("DELETE FROM StepData").executeUpdate();
            logger.info("Все данные успешно удалены из базы данных.");
        }, "Ошибка при очистке данных из базы данных");
    }

    public int getTotalStepsForMonth(Month month) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<StepData> stepsList = session.createQuery(
                            "FROM StepData WHERE month = :month", StepData.class)
                    .setParameter("month", month.name())
                    .list();
            return stepsList.stream().mapToInt(StepData::getSteps).sum();
        }
    }

    public int getMaxStepsForMonth(Month month) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Integer maxSteps = session.createQuery(
                            "SELECT MAX(steps) FROM StepData WHERE month = :month", Integer.class)
                    .setParameter("month", month.name())
                    .uniqueResult();
            return maxSteps != null ? maxSteps : 0;
        }
    }

    public double getAverageStepsForMonth(Month month) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        double averageSteps = 0;
        try {
            List<StepData> stepsList = session.createQuery(
                            "FROM StepData WHERE month = :month", StepData.class)
                    .setParameter("month", month.name())
                    .list();

            averageSteps = stepsList.stream().mapToInt(StepData::getSteps).average().orElse(0);
        } finally {
            session.close();
        }
        return averageSteps;
    }

    public int getStepGoal() {
        return stepGoal;
    }

    private void validateDayAndSteps(Month month, int day, int steps) {
        if (day <= 0 || day > month.getDays()) {
            logger.warning("Попытка добавить некорректный день: " + day);
            throw new IllegalArgumentException("Некорректный день: " + day);
        }
        if (steps <= 0 || steps > 50000) {
            logger.warning("Попытка добавить некорректное количество шагов: " + steps);
            throw new IllegalArgumentException("Количество шагов должно быть положительным и не более 50000.");
        }
    }

    private static void executeTransaction(Consumer<Session> action, String errorMessage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                action.accept(session);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                logger.warning(errorMessage + ": " + e.getMessage());
                throw e;
            }
        }
    }
}








