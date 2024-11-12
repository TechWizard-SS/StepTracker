package StepTracker_Pac;

public class Converter {
    private static final double STEP_LENGTH = 0.75; //в метрах
    private static final double CALORIES_PER_STEP = 0.05;

    public static double toDistance(int steps){
        return steps * STEP_LENGTH;
    }

    public static double toCalories(int steps){
        return steps * CALORIES_PER_STEP;
    }
}
