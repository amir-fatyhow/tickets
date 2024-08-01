import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class TicketAnalyzer {
    public static void main(String[] args) throws ParseException {
        if (args.length != 1) {
            System.err.println("Usage: java TicketAnalyzer <path_to_json_file>");
            System.exit(1);
        }

        String filePath = args[0];
        List<Ticket> tickets = readTicketsFromFile(filePath);

        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No tickets found in the specified file.");
            return;
        }

        // Filter tickets between Vladivostok (VVO) and Tel Aviv (TLV)
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> ticket.origin.equals("VVO") && ticket.destination.equals("TLV"))
                .collect(Collectors.toList());

        // Calculate minimum flight time per carrier
        Map<String, Integer> minFlightTimes = calculateMinFlightTimePerCarrier(filteredTickets);
        minFlightTimes.forEach((carrier, time) -> System.out.println("Minimum flight time for " + carrier + ": " + time + " minutes"));

        // Calculate average and median price
        double averagePrice = calculateAveragePrice(filteredTickets);
        double medianPrice = calculateMedianPrice(filteredTickets);

        // Calculate difference between average and median price
        double difference = averagePrice - medianPrice;

        System.out.println("Average price: " + averagePrice);
        System.out.println("Median price: " + medianPrice);
        System.out.println("Difference between average and median price: " + difference);
    }

    private static List<Ticket> readTicketsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Ticket>> data = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, List<Ticket>>>() {});
            return data.get("tickets");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Integer> calculateMinFlightTimePerCarrier(List<Ticket> tickets) throws ParseException {
        Map<String, Integer> minFlightTimes = new HashMap<>();
        for (Ticket ticket : tickets) {
            int flightDuration = ticket.getFlightDuration();
            minFlightTimes.merge(ticket.carrier, flightDuration, Math::min);
        }
        return minFlightTimes;
    }

    private static double calculateAveragePrice(List<Ticket> tickets) {
        return tickets.stream().mapToInt(ticket -> ticket.price).average().orElse(0);
    }

    private static double calculateMedianPrice(List<Ticket> tickets) {
        List<Integer> prices = tickets.stream().map(ticket -> ticket.price).sorted().collect(Collectors.toList());
        int size = prices.size();
        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }
}
