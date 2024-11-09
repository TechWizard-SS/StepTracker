package StepTracker_Pac;
import javax.persistence.*;

@Entity
@Table(name = "steps")
public class StepData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "steps", nullable = false)
    private int steps;

    @Column(name = "goal", nullable = false)
    private int goal;

    public Long getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "StepData{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", day=" + day +
                ", steps=" + steps +
                ", goal=" + goal +
                '}';
    }
}
