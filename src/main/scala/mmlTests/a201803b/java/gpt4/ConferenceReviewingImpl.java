package mmlTests.a201803b.java.gpt4;

import mmlTests.a201803b.java.ConferenceReviewing;
import mmlTests.a201803b.java.ConferenceReviewing.Question;
import mmlTests.shared.java.Pair;

import java.util.*;

public class ConferenceReviewingImpl implements ConferenceReviewing {
	private Map<Integer, Map<Question, List<Integer>>> reviews = new HashMap<>();
	private Map<Integer, List<Double>> weightedFinalScores = new HashMap<>();

	public void loadReview(int article, Map<Question, Integer> scores) {
		scores.forEach((question, score) -> addScore(article, question, score));
		if (scores.containsKey(Question.CONFIDENCE) && scores.containsKey(Question.FINAL)) {
			double weightedFinalScore = ((double) scores.get(Question.CONFIDENCE)
					* scores.get(Question.FINAL)) / 10;
			weightedFinalScores.computeIfAbsent(article, a -> new ArrayList<>())
					.add(weightedFinalScore);
		}
	}

	public void loadReview(int article, int relevance, int significance, int confidence, int fin) {
		addScore(article, Question.RELEVANCE, relevance);
		addScore(article, Question.SIGNIFICANCE, significance);
		addScore(article, Question.CONFIDENCE, confidence);
		addScore(article, Question.FINAL, fin);

		double weightedFinalScore = ((double) confidence * fin) / 10;
		weightedFinalScores.computeIfAbsent(article, a -> new ArrayList<>())
				.add(weightedFinalScore);
	}

	private void addScore(int article, Question question, int score) {
		reviews.computeIfAbsent(article, a -> new EnumMap<>(Question.class))
				.computeIfAbsent(question, q -> new ArrayList<>())
				.add(score);
	}

	public List<Integer> orderedScores(int article, Question question) {
		List<Integer> scores = new ArrayList<>(reviews.get(article).get(question));
		Collections.sort(scores);
		return scores;
	}

	public double averageFinalScore(int article) {
		List<Integer> scores = reviews.get(article).get(Question.FINAL);
		return scores.stream().mapToDouble(a -> a).average().getAsDouble();
	}

	public Set<Integer> acceptedArticles() {
		Set<Integer> accepted = new HashSet<>();
		for (int article : reviews.keySet()) {
			if (averageFinalScore(article) > 5 && hasRelevanceScoreGreaterThanOrEqualTo8(article)) {
				accepted.add(article);
			}
		}
		return accepted;
	}

	private boolean hasRelevanceScoreGreaterThanOrEqualTo8(int article) {
		return reviews.get(article).get(Question.RELEVANCE).stream().anyMatch(score -> score >= 8);
	}

	public List<Pair<Integer, Double>> sortedAcceptedArticles() {
		List<Pair<Integer, Double>> pairs = new ArrayList<>();
		for (Integer article : acceptedArticles()) {
			pairs.add(new Pair<>(article, averageFinalScore(article)));
		}
		pairs.sort(Comparator.comparing(Pair::getSnd));
		return pairs;
	}

	public Map<Integer, Double> averageWeightedFinalScoreMap() {
		Map<Integer, Double> map = new HashMap<>();
		for (Map.Entry<Integer, List<Double>> entry : weightedFinalScores.entrySet()) {
			double avg = entry.getValue().stream().mapToDouble(a -> a).average().getAsDouble();
			map.put(entry.getKey(), avg);
		}
		return map;
	}
}
