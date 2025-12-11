package processing;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Schedule;
import model.Sequence;
import model.Stop;
import model.Visitor;


public class Interpreter implements Visitor {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

    // Map: Sequence ID -> (StartTime -> Stop Name)
    private final Map<String, Map<String, String>> scheduleMap = new LinkedHashMap<>();

    public Map<String, Map<String, String>> interpret(Schedule schedule) {
        schedule.accept(this);
        return scheduleMap;
    }

    @Override
    public void visitSequence(Sequence sequence) {
        if (sequence.getTimeRange() == null || sequence.getStops() == null || sequence.getStops().isEmpty()) {
            return;
        }

        LocalTime currentTime = sequence.getTimeRange().toLocalTimes()[0];
        int totalRangeMinutes = sequence.getTimeRange().toMinutes();

        List<Stop> stops = sequence.getStops();
        int accumulatedMinutes = 0;
        int index = 0;
        int stopIndex = 0;

        Map<String, String> stopsMap = new LinkedHashMap<>();

        while (accumulatedMinutes < totalRangeMinutes) {
            Stop stop = stops.get(stopIndex);

            int stopDuration = stop.geDuration().toMinutes();
            if (index > 0) { 
                accumulatedMinutes += stopDuration;
                currentTime = currentTime.plusMinutes(stopDuration);
            }
            stopsMap.put(currentTime.format(timeFormatter), stop.getPlace().getText());
        
            stopIndex = (stopIndex + 1) % stops.size();
            index++;
        }

        scheduleMap.put(sequence.getEscapedId(), stopsMap);
    }
}
