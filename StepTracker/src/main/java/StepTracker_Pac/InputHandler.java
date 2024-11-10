package StepTracker_Pac;

import java.util.function.IntPredicate;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputHandler {
    static Scanner scanner = new Scanner(System.in);


    public static Month promptForMonth() {
        while (true) {
            System.out.println("Введите месяц (например, JANUARY): ");
            String input = scanner.nextLine().toUpperCase();
            if ("BACK".equals(input)) return null;
            try {
                return Month.valueOf(input);
            } catch (IllegalArgumentException e) {
                LoggerConfig.logger.warning("Некорректный месяц. Попробуйте снова.");
            }
        }
    }

    public static Integer promptForDay(Month month) {
        return promptForInt("Введите день: ", "Некорректный день. Попробуйте снова.", input -> (int) input > 0 && (int) input <= month.getDays());
    }

    public static Integer promptForSteps() {
        return promptForInt("Введите количество шагов: ", "Количество шагов должно быть положительным и менее 50000", input -> ((int) input > 0 || (int) input < 50000));
    }

    private static Integer promptForInt(String promptMessage, String errorMessage, Predicate condition) {
        while (true) {
            System.out.println(promptMessage);
            String input = scanner.nextLine();
            String k = "BACK";
            if (k.equalsIgnoreCase(input)) {
                return null;
            } else {
                try {
                    int value = Integer.parseInt(input);
                    if (condition.test(value)) {
                        return value;
                    } else
                        LoggerConfig.logger.warning(errorMessage);
                } catch (NumberFormatException e) {
                    LoggerConfig.logger.warning("Введите корректное число.");
                }
            }
        }
    }
}
