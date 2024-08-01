import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Ticket {
    public String origin;
    public String origin_name;
    public String destination;
    public String destination_name;
    public String departure_date;
    public String departure_time;
    public String arrival_date;
    public String arrival_time;
    public String carrier;
    public int stops;
    public int price;

    // Method to calculate flight duration in minutes
    public int getFlightDuration() throws ParseException {
        String departureDateTime = departure_date + " " + departure_time;
        String arrivalDateTime = arrival_date + " " + arrival_time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date departure = formatter.parse(departureDateTime);
        Date arrival = formatter.parse(arrivalDateTime);
        long differenceInMSeconds = arrival.getTime() - departure.getTime();
        return (int) (differenceInMSeconds / (60 * 1000));
    }
}