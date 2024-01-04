package mmlTests.a201501a.java.gpt4;

import mmlTests.a201501a.java.CoursesCalendar;
import mmlTests.shared.java.Pair;

import java.util.*;
import java.util.stream.Collectors;


public class CoursesCalendarImpl implements CoursesCalendar {
	private final Integer[] slots = {9, 10, 11, 12, 14, 15, 16, 17};
	private Map<Pair<Day, Room>, Map<Integer, String>> calendar = new HashMap<>();

	@Override
	public List<Integer> possibleSlots() {
		return Arrays.asList(slots);
	}

	@Override
	public void bookRoom(Day d, Room r, int start, int duration, String course) {
		Pair<Day, Room> key = new Pair<>(d, r);
		for (int i = 0; i < duration; i++) {
			int currentHour = start + i;
			if (!this.possibleSlots().contains(currentHour)) {
				throw new IllegalStateException();
			}

			this.calendar.putIfAbsent(key, new HashMap<>());

			if (this.calendar.get(key).putIfAbsent(currentHour, course) != null) {
				throw new IllegalStateException();
			}
		}
	}

	@Override
	public Set<Pair<Integer, String>> dayRoomSlots(Day d, Room r) {
		Pair<Day, Room> key = new Pair<>(d, r);
		Map<Integer, String> roomBooking = this.calendar.getOrDefault(key, new HashMap<>());
		Set<Pair<Integer, String>> result = new HashSet<>();
		for (Integer hour : roomBooking.keySet()) {
			if(this.possibleSlots().contains(hour)){
				result.add(new Pair<>(hour, roomBooking.get(hour)));
			}
		}
		return result;
	}

	@Override
	public Map<Pair<Day, Room>, Set<Integer>> courseSlots(String course) {
		Map<Pair<Day, Room>, Set<Integer>> result = new HashMap<>();
		for (Pair<Day, Room> key : this.calendar.keySet()) {
			for (Integer hour : this.calendar.get(key).keySet()) {
				if (this.calendar.get(key).get(hour).equals(course)) {
					result.putIfAbsent(key, new HashSet<>());
					result.get(key).add(hour);
				}
			}
		}
		return result;
	}
}
