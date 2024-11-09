package StepTracker_Pac;

import java.util.Scanner;

import static StepTracker_Pac.LoggerConfig.logger;

public class StepManager {
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
