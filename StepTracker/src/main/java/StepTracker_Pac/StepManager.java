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

    public void setNewGoal() {
        System.out.println("Введите новую цель по шагам: ");
        try {
            int newGoal = Integer.parseInt(scanner.nextLine());
            stepTracker.setStepGoal(newGoal);
        } catch (NumberFormatException e) {
            logger.warning("Цель должна быть числом. Попробуйте снова.");
        }
    }

    public void clearAllStepsAndPasswordVerification() {
        logger.warning("Запрос на очистку всех данных из базы данных.");
        String dbPassword = HibernateUtil.getDbPassword();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пароль для подтверждения очистки базы данных: ");
        String inputPassword = scanner.nextLine();



        // Логируем пароль для отладки


        if (dbPassword == null || dbPassword.isEmpty()) {
            logger.warning("Пароль не найден или пуст.");
            return;
        }

        // Сравниваем введенный пароль с паролем из конфигурации
        if (!inputPassword.equals(dbPassword)) {
            logger.warning("Введен неверный пароль.");
            System.out.println("Неверный пароль. Операция отменена.");
        }
        else {
            logger.warning(" используемый пароль базы данных: " + "****");
            StepTracker.clearAllSteps();
        }
    }


    public void addSteps() {
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

    public void displayMonthlyStats() {
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
