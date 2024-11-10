import StepTracker_Pac.Month;
import StepTracker_Pac.StepTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StepTrackerTest {
    private StepTracker tracker = new StepTracker(10000);
    @Test
    public void testSetStepGoalPositive(){

        StepTracker stepTracker = new StepTracker(10000);
        stepTracker.setStepGoal(15000);
        assertEquals(15000, stepTracker.getStepGoal(),"Step goal should be updated to 15000" );
    }

    @Test
    public void testSetStepGoalNegative(){
        StepTracker stepTracker = new StepTracker(10000);
        assertThrows(IllegalArgumentException.class, () -> stepTracker.setStepGoal(-5000), "Negative step goal should throw exception");
    }

    @Test
    public void testAddSteps(){
        StepTracker stepTracker = new StepTracker(10000);
        Month month = Month.JANUARY;

        stepTracker.addSteps(month, 1, 5000);

        assertEquals(5000, stepTracker.getTotalStepsForMonth(month),"Total steps for January should be 5000");
    }

    @Test
    public void testGetMaxStepsForMonth(){
        StepTracker stepTracker = new StepTracker(10000);
        Month month = Month.JANUARY;

        stepTracker.addSteps(month,1,3000);
        stepTracker.addSteps(month,2,7000);
        stepTracker.addSteps(month,3,5000);

        assertEquals(7000,stepTracker.getMaxStepsForMonth(month),"Max steps for January should be 7000");
    }

    @Test
    public void testClearAllSteps(){
        Month month = Month.JANUARY;
        tracker.addSteps(month, 1, 5000);
        tracker.addSteps(month,2,3000);

        tracker.clearAllSteps();

        assertEquals(0, tracker.getTotalStepsForMonth(month), "После очистки шаги должны быть равны 0.");
    }

    @Test
    public void testAddStepsWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> tracker.addSteps(Month.JANUARY, 1, -100), "Отрицательное значение шагов должно вызвать исключение.");
    }

    @Test
    public void testAddStepsExceedingMax() {
        assertThrows(IllegalArgumentException.class, () -> tracker.addSteps(Month.JANUARY, 1, 60000), "Значение шагов, превышающее 50000, должно вызвать исключение.");
    }

    @Test
    public void testSetNegativeGoal() {
        assertThrows(IllegalArgumentException.class, () -> tracker.setStepGoal(-10000), "Отрицательная цель должна вызвать исключение.");
    }
}
