import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    public static void main(String[] args) {
        String inputDateString = "20231223213000";

        try {
            // Parse the input string using SimpleDateFormat
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = inputFormat.parse(inputDateString);

            // Format the date into the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String outputDateString = outputFormat.format(date);

            // Print the result
            System.out.println(outputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
