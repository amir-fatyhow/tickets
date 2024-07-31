import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    public int getFlightDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime departure = LocalTime.parse(departure_time, formatter);
        LocalTime arrival = LocalTime.parse(arrival_time, formatter);
        return (int) Duration.between(departure, arrival).toMinutes();
    }
}