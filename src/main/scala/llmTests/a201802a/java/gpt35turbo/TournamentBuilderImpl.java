//package mmlTests.a201802a.java.solMV;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import mmlTests.a201802a.java.Tournament;
//import mmlTests.a201802a.java.TournamentBuilder;
//
//public class TournamentBuilderImpl implements TournamentBuilder {
//
//	private Tournament.Type type;
//	private String name;
//	private Map<String, Integer> priorRanking;
//	private Map<String, Tournament.Result> results;
//	private Tournament tournament;
//
//	public TournamentBuilderImpl() {
//		results = new HashMap<>();
//	}
//
//	public TournamentBuilder setType(Tournament.Type type) {
//		this.type = type;
//		return this;
//	}
//
//	public TournamentBuilder setName(String name) {
//		this.name = name;
//		return this;
//	}
//
//	public TournamentBuilder setPriorRanking(Map<String, Integer> ranking) {
//		this.priorRanking = ranking;
//		return this;
//	}
//
//	public TournamentBuilder addResult(String player, Tournament.Result result) {
//		if (results.containsKey(player)) {
//			throw new IllegalStateException("Player already has a result");
//		}
//		results.put(player, result);
//		return this;
//	}
//
//	public Tournament build() {
//		if (type == null) {
//			throw new IllegalStateException("Type not specified");
//		}
//
//		if (name == null) {
//			throw new IllegalStateException("Name not specified");
//		}
//
//		if (results.isEmpty()) {
//			throw new IllegalStateException("No results specified");
//		}
//
//		for (String player : results.keySet()) {
//			if (!priorRanking.containsKey(player)) {
//				throw new IllegalStateException("Player " + player + " not in prior ranking");
//			}
//		}
//
//		Map<String, Integer> resultingRanking = new HashMap<>(priorRanking);
//		for (String player : results.keySet()) {
//			Tournament.Result result = results.get(player);
//			switch (result) {
//				case WINNER:
//					int points = type == Tournament.Type.MAJOR ? 2500 : type == Tournament.Type.ATP1000 ? 1000
//							: type == Tournament.Type.ATP500 ? 500 : 250;
//					resultingRanking.put(player, priorRanking.get(player) + points);
//					break;
//				case FINALIST:
//					resultingRanking.put(player, priorRanking.get(player) + (points * 50) / 100);
//					break;
//				case SEMIFINALIST:
//					resultingRanking.put(player, priorRanking.get(player) + (points * 20) / 100);
//					break;
//				case QUARTERFINALIST:
//					resultingRanking.put(player, priorRanking.get(player) + (points * 10) / 100);
//					break;
//				case PARTICIPANT:
//					// no points added for participant
//					break;
//			}
//		}
//
//		List<String> rank = new ArrayList<>();
//		results.entrySet().stream().sorted((e1, e2) -> resultingRanking.get(e2.getKey())
//				.compareTo(resultingRanking.get(e1.getKey()))).forEach(e -> rank.add(e.getKey()));
//
//		String winner = rank.get(0);
//
//		tournament = new Tournament() {
//
//			@Override
//			public Type getType() {
//				return type;
//			}
//
//			@Override
//			public String getName() {
//				return name;
//			}
//
//			@Override
//			public Optional<Result> getResult(String player) {
//				if (results.containsKey(player)) {
//					return Optional.of(results.get(player));
//				} else {
//					return Optional.empty();
//				}
//			}
//
//			@Override
//			public String winner() {
//				return winner;
//			}
//
//			@Override
//			public Map<String, Integer> initialRanking() {
//				return priorRanking;
//			}
//
//			@Override
//			public Map<String, Integer> resultingRanking() {
//				return resultingRanking;
//			}
//
//			@Override
//			public List<String> rank() {
//				return rank;
//			}
//
//		};
//
//		return tournament;
//	}
//}