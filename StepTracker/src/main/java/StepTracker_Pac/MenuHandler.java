package StepTracker_Pac;

import java.util.Scanner;

public class MenuHandler {
    private final StepManager stepManager;
    private static Scanner scanner = new Scanner(System.in);

    public MenuHandler(StepTracker stepTracker, Converter converter) {
        this.stepManager = new StepManager(stepTracker, converter);
    }

    public void start() {
        handleMenu();
    }


    private static void printMenu() {
        System.out.println("Выберите действие: ");
        System.out.println("1. Добавить шаги за день: ");
        System.out.println("2. Показать статистику за месяц: ");
        System.out.println("3. Установить новую цель по шагам: ");
        System.out.println("4. Очистить базу данных.");
        System.out.println("Введите 'EXIT' для завершения работы.");
        System.out.println("Введите 'BACK' для выхода в меню.");

    }

    private void handleMenu() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("EXIT")) {
                System.out.println("Программа завершена!");
                break;
            }
            processChoice(choice);
        }
    }

    private void processChoice(String choice){
        switch (choice){
            case "1":
                stepManager.addSteps();
                break;
            case "2":
                stepManager.displayMonthlyStats();
                break;
            case "3":
                stepManager.setNewGoal();
                break;
            case "4":
                stepManager.clearAllStepsAndPasswordVerification();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }
}
