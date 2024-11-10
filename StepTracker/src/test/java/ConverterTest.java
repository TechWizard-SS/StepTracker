import org.junit.jupiter.api.Test;
import  StepTracker_Pac.Converter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    private final Converter converter = new Converter();

    @Test
    public void testToDistance() {
        assertEquals(0, converter.toDistance(0), "Конвертация 0 шагов должна вернуть 0 км.");
        assertEquals(0.75, converter.toDistance(1), "Конвертация 1 шага должна вернуть 0.75 м.");
    }

    @Test
    public void testToCalories() {
        assertEquals(0, converter.toCalories(0), "Конвертация 0 шагов должна вернуть 0 калорий.");
        assertEquals(0.05, converter.toCalories(1), "Конвертация 1 шага должна вернуть 0.05 калорий.");
    }
}
