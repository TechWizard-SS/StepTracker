package org.example;

public class Main {
    public static void main(String[] args) {
        StepTracker stepTracker = new StepTracker(10000); // целевой показатель шагов
        Converter converter = new Converter();
        MenuHandler menuHandler = new MenuHandler(stepTracker,converter);
        menuHandler.start();
    }
}
