package org.example;

import java.util.Scanner;

import static org.example.LoggerConfig.logger;

public class StepManager {
    private final StepTracker stepTracker;
    private final Converter converter;
    static Scanner scanner = new Scanner(System.in);
    public StepManager(StepTracker stepTracker, Converter converter) {
        this.stepTracker = stepTracker;
        this.converter = converter;
    }

    public static void setNewGoal(StepTracker stepTracker) {
        System.out.println("Введите новую цель по шагам:");
        try {
            int newGoal = Integer.parseInt(scanner.nextLine());
            stepTracker.setStepGoal(newGoal);
        } catch (NumberFormatException e) {
            logger.warning("Цель должна быть числом. Попробуйте снова.");
        }
    }


    public static void addSteps(StepTracker stepTracker) {
        Month month = MenuHandler.promptForMonth();
        if (month == null) return;
        Integer day = MenuHandler.promptForDay(month);
        if (day == null) return;
        Integer steps = MenuHandler.promptForSteps();
        if (steps == null) return;
        try {
            stepTracker.addSteps(month, day, steps);
            System.out.println("Шаги успешно добавлены.");
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
        }
    }

    public static void displayMonthlyStats(StepTracker stepTracker, Converter converter) {
        Month month = MenuHandler.promptForMonth();
        if (month == null) return;
        System.out.println("Общее количество шагов за " + month + ": " + stepTracker.getTotalStepsForMonth(month));
        System.out.println("Максимальное количество шагов за " + month + ": " + stepTracker.getMaxStepsForMonth(month));
        System.out.println("Среднее количество шагов за " + month + ": " + stepTracker.getAverageStepsForMonth(month));
    }
}
