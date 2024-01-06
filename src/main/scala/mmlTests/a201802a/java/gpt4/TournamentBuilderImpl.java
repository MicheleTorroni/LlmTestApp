package mmlTests.a201802a.java.gpt4;

import java.util.HashMap;
import java.util.Map;

import mmlTests.a201802a.java.Tournament;
import mmlTests.a201802a.java.Tournament.Result;
import mmlTests.a201802a.java.Tournament.Type;
import mmlTests.a201802a.java.TournamentBuilder;

public class TournamentBuilderImpl implements TournamentBuilder {

	private String name;
	private Type type;
	private Map<String,Integer> priorRanking;
	private Map<String, Result> playersResults;
	private boolean isBuilt;

	public TournamentBuilderImpl() {
		this.playersResults = new HashMap<>();
		this.isBuilt = false;
	}

	public TournamentBuilder setType(Type type) {
		verifyNotBuilt();
		if (type == null) {
			throw new NullPointerException();
		}
		this.type = type;
		return this;
	}

	public TournamentBuilder setName(String name) {
		verifyNotBuilt();
		this.name = name;
		return this;
	}

	public TournamentBuilder setPriorRanking(Map<String,Integer> ranking) {
		verifyNotBuilt();
		this.priorRanking = ranking;
		return this;
	}

	public TournamentBuilder addResult(String player, Result result) {
		verifyNotBuilt();
		if (type == null || priorRanking == null) {
			throw new IllegalStateException();
		}

		if (playersResults.containsKey(player)) {
			throw new IllegalStateException();
		}

		if (result == Result.WINNER && playersResults.containsValue(Result.WINNER)) {
			throw new IllegalStateException();
		}

		playersResults.put(player, result);
		return this;
	}

	public Tournament build() {
		verifyNotBuilt();
		if (name == null || type == null || priorRanking == null
				|| !playersResults.containsValue(Result.WINNER)) {
			throw new IllegalStateException();
		}
		isBuilt = true;
		return new TournamentImpl(name, type, priorRanking, playersResults);
	}

	private void verifyNotBuilt() {
		if (isBuilt) {
			throw new IllegalStateException();
		}
	}
}