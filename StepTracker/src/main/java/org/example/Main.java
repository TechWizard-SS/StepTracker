package org.example;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        StepTracker stepTracker = new StepTracker(10000); // целевой показатель шагов
        Converter converter = new Converter();

        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("EXIT")) {
                System.out.println("Программа завершена!");
                break;
            }

            switch (choice) {
                case "1":
                    setNewGoal(stepTracker);
                    break;
                case "2":
                    addSteps(stepTracker);
                    break;
                case "3":
                    displayMonthlyStats(stepTracker, converter);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }



    private static void printMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Установить новую цель по шагам");
        System.out.println("2. Добавить шаги за день");
        System.out.println("3. Показать статистику за месяц");
        System.out.println("Введите 'EXIT' для завершения работы");
    }

    private static void setNewGoal(StepTracker stepTracker) {
        System.out.println("Введите новую цель по шагам:");
        try {
            int newGoal = Integer.parseInt(scanner.nextLine());
            stepTracker.setStepGoal(newGoal);
        } catch (NumberFormatException e) {
            logger.warning("Цель должна быть числом. Попробуйте снова.");
        }
    }

    private static void addSteps(StepTracker stepTracker) {
        Month month = promptForMonth();
        if (month == null) return;

        Integer day = promptForDay(month);
        if (day == null) return;

        Integer steps = promptForSteps();
        if (steps == null) return;

        try {
            stepTracker.addSteps(month, day, steps);
            System.out.println("Шаги успешно добавлены.");
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
        }
    }

    private static void displayMonthlyStats(StepTracker stepTracker, Converter converter) {
        Month month = promptForMonth();
        if (month == null) return;

        System.out.println("Общее количество шагов за " + month + ": " + stepTracker.getTotalStepsForMonth(month));
        System.out.println("Максимальное количество шагов за " + month + ": " + stepTracker.getMaxStepsForMonth(month));
        System.out.println("Среднее количество шагов за " + month + ": " + stepTracker.getAverageStepsForMonth(month));
    }

    private static Month promptForMonth() {
        logger.info("Введите месяц (например, JANUARY): ");
        String input = scanner.nextLine().toUpperCase();
        try {
            return Month.valueOf(input);
        } catch (IllegalArgumentException e) {
            logger.warning("Некорректный месяц. Попробуйте снова.");
            return null;
        }
    }

    private static Integer promptForDay(Month month) {
        System.out.println("Введите день: ");
        String input = scanner.nextLine();

        try {
            int day = Integer.parseInt(input);
            if (day > 0 && day <= month.getDays()) {
                return day;
            } else {
                logger.warning("Некорректный день. Попробуйте снова.");
                return null;
            }
        } catch (NumberFormatException e) {
            logger.warning("Введите число для дня.");
            return null;
        }
    }

    private static Integer promptForSteps() {
        System.out.println("Введите количество шагов: ");
        String input = scanner.nextLine();

        try {
            int steps = Integer.parseInt(input);
            if (steps > 0) {
                return steps;
            } else {
                logger.warning("Количество шагов должно быть положительным.");
                return null;
            }
        } catch (NumberFormatException e) {
            logger.warning("Введите число для количества шагов.");
            return null;
        }
    }

    private static void initializeLogger() {
        if (logger.getHandlers().length == 0) { // Проверка, чтобы избежать повторного добавления обработчиков
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        }
    }

}
