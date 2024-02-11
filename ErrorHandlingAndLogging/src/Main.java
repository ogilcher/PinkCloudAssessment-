import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Main {

    //-- Instance Variables
    private static final Logger logger = LogManager.getLogger(ErrorHandlingAndLoggingDemo.class);

    //-- Executes the Program
    public static void main(String[] args) {

        try {
            // Simulating a division by zero error
            int result = divide(10, 0);
            System.out.println("Result: " + result); // This line won't be executed due to the exception
        } catch (ArithmeticException e) {
            // Log the exception and relevant information
            logger.error("An arithmetic exception occurred: {}", e.getMessage());
            logger.error("Exception stack trace:", e);
        }
    }

    //-- Division function
    public static int divide(int dividend, int divisor) {

        // Throw exception if there is an attempt to divide by 0
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return dividend / divisor;
    }
}