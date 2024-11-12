package StepTracker_Pac;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerConfig {
    public static Logger logger = Logger.getLogger(LoggerConfig.class.getName());

public static void initializeLogger(){
    if (logger.getHandlers().length == 0) {             // Проверка, чтобы избежать повторного добавления обработчиков
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }
}

}
