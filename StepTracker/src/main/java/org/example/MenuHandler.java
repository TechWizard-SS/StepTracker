package org.example;

import java.util.Scanner;
import java.util.logging.Logger;

public class MenuHandler {
    private final StepManager stepManager;
    private static final Scanner scanner = new Scanner(System.in);
    private final StepTracker stepTracker;
    private final Converter converter;

    public MenuHandler(StepTracker stepTracker, Converter converter){
        this.stepTracker = stepTracker;
        this.converter = converter;
        this.stepManager = new StepManager(stepTracker, converter);
    }

    public void start(){
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("EXIT")) {
                System.out.println("Программа завершена!");
                break;
            }
            switch (choice) {
                case "1":
                    stepManager.setNewGoal(stepTracker);
                    break;
                case "2":
                    stepManager.addSteps(stepTracker);
                    break;
                case "3":
                    StepManager.displayMonthlyStats(stepTracker, converter);
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


    public static Month promptForMonth() {
        LoggerConfig.logger.info("Введите месяц (например, JANUARY): ");
        String input = scanner.nextLine().toUpperCase();
        try {
            return Month.valueOf(input);
        } catch (IllegalArgumentException e) {
            LoggerConfig.logger.warning("Некорректный месяц. Попробуйте снова.");
            return null;
        }
    }
    public static Integer promptForDay(Month month) {
        System.out.println("Введите день: ");
        String input = scanner.nextLine();
        try {
            int day = Integer.parseInt(input);
            if (day > 0 && day <= month.getDays()) {
                return day;
            } else {
                LoggerConfig.logger.warning("Некорректный день. Попробуйте снова.");
                return null;
            }
        } catch (NumberFormatException e) {
            LoggerConfig.logger.warning("Введите число для дня.");
            return null;
        }
    }

    public static Integer promptForSteps() {
        System.out.println("Введите количество шагов: ");
        String input = scanner.nextLine();
        try {
            int steps = Integer.parseInt(input);
            if (steps > 0) {
                return steps;
            } else {
                LoggerConfig.logger.warning("Количество шагов должно быть положительным.");
                return null;
            }
        } catch (NumberFormatException e) {
            LoggerConfig.logger.warning("Введите число для количества шагов.");
            return null;
        }
    }

}
