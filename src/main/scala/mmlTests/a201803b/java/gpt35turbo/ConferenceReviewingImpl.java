package mmlTests.a201803b.java.gpt35turbo;

import mmlTests.a201803b.java.ConferenceReviewing;

import java.util.*;

import mmlTests.shared.java.Pair;

public class ConferenceReviewingImpl implements ConferenceReviewing {

	private Map<Integer, List<Integer>> scores;
	private Map<Integer, Map<Question, Integer>> reviewMap;

	public ConferenceReviewingImpl() {
		scores = new HashMap<>();
		reviewMap = new HashMap<>();
	}

	@Override
	public void loadReview(int article, Map<Question, Integer> scores) {
		reviewMap.put(article, scores);
		for (Map.Entry<Question, Integer> entry : scores.entrySet()) {
			Question question = entry.getKey();
			int score = entry.getValue();
			List<Integer> scoreList = this.scores.getOrDefault(question.ordinal(), new ArrayList<>());
			scoreList.add(score);
			this.scores.put(question.ordinal(), scoreList);
		}
	}

	@Override
	public void loadReview(int article, int relevance, int significance, int confidence, int fin) {
		Map<Question, Integer> scores = new EnumMap<>(Question.class);
		scores.put(Question.RELEVANCE, relevance);
		scores.put(Question.SIGNIFICANCE, significance);
		scores.put(Question.CONFIDENCE, confidence);
		scores.put(Question.FINAL, fin);
		loadReview(article, scores);
	}

	@Override
	public List<Integer> orderedScores(int article, Question question) {
		List<Integer> scoreList = scores.get(question.ordinal());
		Collections.sort(scoreList);
		return scoreList;
	}

	@Override
	public double averageFinalScore(int article) {
		Map<Question, Integer> review = reviewMap.get(article);
		int finalScore = review.get(Question.FINAL);
		return (double) finalScore;
	}

	@Override
	public Set<Integer> acceptedArticles() {
		Set<Integer> acceptedArticles = new HashSet<>();
		for (Map.Entry<Integer, Map<Question, Integer>> entry : reviewMap.entrySet()) {
			int article = entry.getKey();
			Map<Question, Integer> review = entry.getValue();
			int finalScore = review.get(Question.FINAL);
			int relevanceScore = review.get(Question.RELEVANCE);
			if (finalScore > 5 && relevanceScore >= 8) {
				acceptedArticles.add(article);
			}
		}
		return acceptedArticles;
	}

	@Override
	public List<Pair<Integer, Double>> sortedAcceptedArticles() {
		List<Pair<Integer, Double>> sortedArticles = new ArrayList<>();
		for (int article : acceptedArticles()) {
			double avgFinalScore = averageFinalScore(article);
			sortedArticles.add(new Pair<>(article, avgFinalScore));
		}
		Collections.sort(sortedArticles, Comparator.comparing(Pair::getSnd));
		return sortedArticles;
	}

	@Override
	public Map<Integer, Double> averageWeightedFinalScoreMap() {
		Map<Integer, Double> avgWeightedFinalScores = new HashMap<>();
		for (Map.Entry<Integer, Map<Question, Integer>> entry : reviewMap.entrySet()) {
			int article = entry.getKey();
			Map<Question, Integer> review = entry.getValue();
			int confidenceScore = review.get(Question.CONFIDENCE);
			int finalScore = review.get(Question.FINAL);
			double avgWeightedFinalScore = (double) (confidenceScore * finalScore) / 10;
			avgWeightedFinalScores.put(article, avgWeightedFinalScore);
		}
		return avgWeightedFinalScores;
	}
}