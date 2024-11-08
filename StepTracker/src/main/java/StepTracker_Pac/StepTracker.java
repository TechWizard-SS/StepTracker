package StepTracker_Pac;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class StepTracker {
    private static final Logger logger = Logger.getLogger(StepTracker.class.getName());
    private final Map<Month, Map<Integer, Integer>> stepsData = new HashMap<>();
    private int stepGoal;

    public StepTracker(int stepGoal) {
        this.stepGoal = stepGoal;
    }

    public void setStepGoal(int stepGoal) {
        if (stepGoal > 0) {
            this.stepGoal = stepGoal;
            logger.info("Цель по шагам установлена на: " + stepGoal);
        } else {
            logger.warning("Попытка установить некорректную цель по шагам: " + stepGoal);
            throw new IllegalArgumentException("Цель по шагам должна быть положительным числом.");
        }
    }

    public void addSteps(Month month, int day, int steps) {
        if (day <= 0 || day > month.getDays()) {
            logger.warning("Попытка добавить некорректный день: " + day);
            throw new IllegalArgumentException("Некорректный день: " + day);
        }
        if (steps <= 0) {
            logger.warning("Попытка добавить некорректное количество шагов: " + steps);
            throw new IllegalArgumentException("Количество шагов должно быть положительным.");
        }

        stepsData.putIfAbsent(month, new HashMap<>());
        stepsData.get(month).put(day, steps);
        logger.info("Добавлено " + steps + " шагов в " + month + ", день " + day);
    }

    public int getTotalStepsForMonth(Month month) {
        return stepsData.getOrDefault(month, new HashMap<>())
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getMaxStepsForMonth(Month month) {
        return stepsData.getOrDefault(month, new HashMap<>())
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    public double getAverageStepsForMonth(Month month) {
        Map<Integer, Integer> monthData = stepsData.getOrDefault(month, new HashMap<>());
        return monthData.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }
}








