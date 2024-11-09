package StepTracker_Pac;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (day <= 0 || day > month.getDays()) {
            logger.warning("Попытка добавить некорректный день: " + day);
            throw new IllegalArgumentException("Некорректный день: " + day);
        }
        if (steps <= 0 || steps > 50000) {
            logger.warning("Попытка добавить некорректное количество шагов: " + steps);
            throw new IllegalArgumentException("Количество шагов должно быть положительным и не более 50000.");
        }

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();

        StepData stepData = new StepData();
        stepData.setMonth(month.name());
        stepData.setDay(day);
        stepData.setSteps(steps);
        stepData.setGoal(stepGoal);

        session.save(stepData);
        transaction.commit();
        logger.info("Добавлено " + steps + " шагов в " + month + ", день " + day);
    } catch (Exception e) {
        if(transaction != null) transaction.rollback();
    } finally {
        session.close();
    }
}

    public void clearAllSteps() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM StepData").executeUpdate();
            transaction.commit();
            System.out.println("Все данные успешно удалены из базы данных.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public int getTotalStepsForMonth(Month month) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int totalSteps = 0;
        try {
            List<StepData> stepsList = session.createQuery(
                            "FROM StepData WHERE month = :month", StepData.class)
                    .setParameter("month", month.name())
                    .list();

            totalSteps = stepsList.stream().mapToInt(StepData::getSteps).sum();
        } finally {
            session.close();
        }

        return totalSteps;
    }

    public int getMaxStepsForMonth(Month month) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int maxSteps = 0;
        try {
            List<StepData> stepsList = session.createQuery("FROM StepData WHERE month = :month", StepData.class)
                    .setParameter("month", month.name())
                    .list();
            for (StepData step : stepsList) {
                if (step.getSteps() > maxSteps) {
                    maxSteps = step.getSteps();
                }
            }
        } finally {
            session.close();
        }
        return maxSteps;
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
}








