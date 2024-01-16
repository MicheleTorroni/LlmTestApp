package llmTests.a201802a.java.gpt4;


import java.util.*;
import java.util.stream.Collectors;
import llmTests.a201802a.java.Tournament;
import llmTests.a201802a.java.TournamentBuilder;

import llmTests.a201802a.java.Tournament;
import llmTests.a201802a.java.Tournament.Result;
import llmTests.a201802a.java.Tournament.Type;
import llmTests.a201802a.java.TournamentBuilder;

public class TournamentBuilderImpl implements TournamentBuilder {
	private String name;
	private Tournament.Type type;
	private Map<String, Integer> ranking;
	private Map<String, Tournament.Result> results;
	private boolean built = false;

	public TournamentBuilderImpl() {
		this.results = new HashMap<>();
	}

	@Override
	public TournamentBuilder setType(Tournament.Type type) {
		if (type == null || built)
			throw new IllegalStateException();
		this.type = type;
		return this;
	}

	@Override
	public TournamentBuilder setName(String name) {
		if (name == null || built)
			throw new IllegalStateException();
		this.name = name;
		return this;
	}

	@Override
	public TournamentBuilder setPriorRanking(Map<String, Integer> ranking) {
		if (ranking == null || type == null || built)
			throw new IllegalStateException();
		this.ranking = new HashMap<>(ranking);
		return this;
	}

	@Override
	public TournamentBuilder addResult(String player, Tournament.Result result) {
		if (ranking == null || results.containsKey(player) || built)
			throw new IllegalStateException();
		if (result == Tournament.Result.WINNER && results.values().contains(Tournament.Result.WINNER))
			throw new IllegalStateException();
		this.results.put(player, result);
		return this;
	}

	@Override
	public Tournament build() {
		if (name == null || type == null || ranking == null || !results.containsValue(Tournament.Result.WINNER) || built)
			throw new IllegalStateException();
		built = true;
		return new TournamentImpl(name, type, ranking, results);
	}

	private class TournamentImpl implements Tournament {
		private String name;
		private Type type;
		private Map<String, Integer> initialRank;
		private Map<String, Integer> resultingRank;
		private Map<String, Result> results;

		private TournamentImpl(String name, Type type, Map<String, Integer> initialRank, Map<String, Result> results) {
			this.name = name;
			this.type = type;
			this.initialRank = initialRank;
			this.resultingRank = calculateFinalRanking(initialRank, results, type);
			this.results = results;
		}

		private Map<String, Integer> calculateFinalRanking(Map<String, Integer> initialRank, Map<String, Result> results, Type type) {
			Map<String, Integer> finalRank = new HashMap<>();
			int points;
			for(String player : initialRank.keySet()) {
				finalRank.put(player, initialRank.get(player) + pointsAwarded(type, results.getOrDefault(player, Result.PARTICIPANT)));
			}
			for (Map.Entry<String, Result> entry : results.entrySet()) {
				if (!finalRank.containsKey(entry.getKey())) {
					finalRank.put(entry.getKey(), pointsAwarded(type, entry.getValue()));
				}
			}
			return finalRank;
		}

		private int pointsAwarded(Type type, Result result) {
			int points = 0;
			switch (type) {
				case MAJOR: {
					points = 2500;
					break;
				}
				case ATP1000: {
					points = 1000;
					break;
				}
				case ATP500: {
					points = 500;
					break;
				}
				case ATP250: {
					points = 250;
					break;
				}
			}

			switch (result) {
				case FINALIST: {
					points = points/2;
					break;
				}
				case SEMIFINALIST: {
					points = points/5;
					break;
				}
				case QUARTERFINALIST: {
					points = points/10;
					break;
				}
				case PARTICIPANT: {
					points = 0;
					break;
				}
			}
			return points;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Optional<Result> getResult(String player) {
			return Optional.ofNullable(results.get(player));
		}

		@Override
		public String winner() {
			return results.entrySet().stream().filter(entry -> entry.getValue() == Result.WINNER).findFirst().get().getKey();
		}

		@Override
		public Map<String, Integer> initialRanking() {
			return initialRank;
		}

		@Override
		public Map<String, Integer> resultingRanking() {
			return resultingRank;
		}

		@Override
		public List<String> rank() {
			return resultingRank.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
		}
	}
}
