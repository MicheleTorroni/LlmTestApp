package mmlTests.a201501a.java.gpt35turbo;

import mmlTests.a201501a.java.CoursesCalendar;
import mmlTests.shared.java.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class CoursesCalendarImpl implements CoursesCalendar {

	private Map<Pair<Day, Room>, Set<Pair<Integer, String>>> bookings;

	public CoursesCalendarImpl() {
		bookings = new HashMap<>();
	}

	@Override
	public List<Integer> possibleSlots() {
		return Arrays.asList(9, 10, 11, 12, 14, 15, 16, 17);
	}

	@Override
	public void bookRoom(Day d, Room r, int start, int duration, String course) {
		Pair<Day, Room> key = new Pair<>(d, r);
		if (bookings.containsKey(key)) {
			Set<Pair<Integer, String>> slots = bookings.get(key);
			for (int i = start; i < start + duration; i++) {
				Pair<Integer, String> slot = new Pair<>(i, course);
				if (slots.contains(slot)) {
					throw new IllegalStateException("Slot already booked");
				}
			}
			slots.addAll(getSlots(start, duration, course));
		} else {
			bookings.put(key, getSlots(start, duration, course));
		}
	}

	private Set<Pair<Integer, String>> getSlots(int start, int duration, String course) {
		Set<Pair<Integer, String>> slots = new HashSet<>();
		for (int i = start; i < start + duration; i++) {
			slots.add(new Pair<>(i, course));
		}
		return slots;
	}

	@Override
	public Set<Pair<Integer, String>> dayRoomSlots(Day d, Room r) {
		Pair<Day, Room> key = new Pair<>(d, r);
		if (bookings.containsKey(key)) {
			return bookings.get(key);
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public Map<Pair<Day, Room>, Set<Integer>> courseSlots(String course) {
		Map<Pair<Day, Room>, Set<Integer>> courseSlots = new HashMap<>();
		for (Pair<Day, Room> key : bookings.keySet()) {
			Set<Pair<Integer, String>> slots = bookings.get(key);
			Set<Integer> hours = new HashSet<>();
			for (Pair<Integer, String> slot : slots) {
				if (slot.getSnd().equals(course)) {
					hours.add(slot.getFst());
				}
			}
			courseSlots.put(key, hours);
		}
		return courseSlots;
	}

}
