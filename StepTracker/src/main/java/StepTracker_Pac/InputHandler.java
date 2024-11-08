package StepTracker_Pac;

import java.util.Scanner;

public class InputHandler {
    static Scanner scanner = new Scanner(System.in);
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
