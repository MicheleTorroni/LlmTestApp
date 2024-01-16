package llmTests.a201501a.java.gpt4;

import llmTests.a201501a.java.CoursesCalendar;
import llmTests.shared.java.Pair;
import java.util.*;

public class CoursesCalendarImpl implements CoursesCalendar {

	private Map<Pair<Day, Room>, TreeMap<Integer, String>> schedule;

	public CoursesCalendarImpl() {
		schedule = new HashMap<>();
	}

	@Override
	public List<Integer> possibleSlots() {
		return new ArrayList<>(Arrays.asList(9, 10, 11, 12, 14, 15, 16, 17));
	}

	@Override
	public void bookRoom(Day d, Room r, int start, int duration, String course) {
		if (duration <= 0 || start < 9 || start + duration - 1 > 17) {
			throw new IllegalStateException("Invalid starting time or duration");
		}
		Pair<Day, Room> pair = new Pair<>(d, r);
		TreeMap<Integer, String> daySchedule = schedule.get(pair);
		if (daySchedule == null) {
			daySchedule = new TreeMap<>();
		}
		for (int i = start; i < start + duration; i++) {
			if(i == 13) continue;
			if(daySchedule.get(i) != null) throw new IllegalStateException("Cannot book due to conflict");
			daySchedule.put(i, course);
		}
		schedule.put(pair, daySchedule);
	}

	@Override
	public Set<Pair<Integer, String>> dayRoomSlots(Day d, Room r) {
		Set<Pair<Integer,String>> bookings = new HashSet<>();
		TreeMap<Integer, String> daySchedule = schedule.get(new Pair<>(d, r));
		if(daySchedule != null) {
			for (Map.Entry<Integer, String> entry : daySchedule.entrySet()) {
				bookings.add(new Pair<>(entry.getKey(), entry.getValue()));
			}
		}
		return bookings;
	}

	@Override
	public Map<Pair<Day, Room>, Set<Integer>> courseSlots(String course) {
		Map<Pair<Day, Room>, Set<Integer>> bookings = new HashMap<>();
		for (Map.Entry<Pair<Day, Room>, TreeMap<Integer, String>> entry : schedule.entrySet()) {
			Set<Integer> set = new HashSet<>();
			for (Map.Entry<Integer, String> timeEntry : entry.getValue().entrySet()) {
				if(timeEntry.getValue().equals(course)) {
					set.add(timeEntry.getKey());
				}
			}
			if(set.size() > 0) {
				bookings.put(entry.getKey(), set);
			}
		}
		return bookings;
	}
}