package StepTracker_Pac;

import java.util.Scanner;

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
                case "3":
                    stepManager.setNewGoal(stepTracker);
                    break;
                case "1":
                    stepManager.addSteps(stepTracker);
                    break;
                case "2":
                    stepManager.displayMonthlyStats(stepTracker, converter);
                    break;

                case "4":
                    stepManager.clearAllSteps();

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите действие: ");
        System.out.println("1. Добавить шаги за день: ");
        System.out.println("2. Показать статистику за месяц: ");
        System.out.println("3. Установить новую цель по шагам: ");
        System.out.println("4. Очистить базу данных.");
        System.out.println("Введите 'EXIT' для завершения работы.");

    }

}
