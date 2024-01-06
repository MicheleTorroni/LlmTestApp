package mmlTests.a201802a.java.gpt4;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import mmlTests.a201802a.java.Tournament;
import mmlTests.a201802a.java.Tournament.Result;
import mmlTests.a201802a.java.Tournament.Type;

public class TournamentImpl implements Tournament {

    private String name;
    private Type type;
    private Map<String, Integer> initialRanking;
    private Map<String, Result> playersResults;

    public TournamentImpl(String name, Type type, Map<String, Integer> initialRanking,
                          Map<String, Result> playersResults) {
        this.name = name;
        this.type = type;
        this.initialRanking = new HashMap<>(initialRanking);
        this.playersResults = new HashMap<>(playersResults);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Optional<Result> getResult(String player) {
        return Optional.ofNullable(playersResults.get(player));
    }

    public String winner() {
        return playersResults.entrySet().stream()
                .filter(e -> e.getValue().equals(Result.WINNER))
                .findFirst()
                .map(Entry::getKey)
                .orElse(null);
    }

    public Map<String, Integer> initialRanking() {
        return new HashMap<>(initialRanking);
    }

    public Map<String, Integer> resultingRanking() {
        int pointsForWinner = type.ordinal() * 250 + 250;

        Map<String, Integer> resultingRanking = new HashMap<>(initialRanking);

        for (Entry<String, Result> playerResult : playersResults.entrySet()) {
            switch (playerResult.getValue()) {
                case WINNER:
                    resultingRanking.merge(playerResult.getKey(), pointsForWinner, Integer::sum);
                    break;
                case FINALIST:
                    resultingRanking.merge(playerResult.getKey(), pointsForWinner / 2, Integer::sum);
                    break;
                case SEMIFINALIST:
                    resultingRanking.merge(playerResult.getKey(), pointsForWinner / 5, Integer::sum);
                    break;
                case QUARTERFINALIST:
                    resultingRanking.merge(playerResult.getKey(), pointsForWinner / 10, Integer::sum);
                    break;
                default:
                    break;
            }
        }

        return resultingRanking;
    }

    public List<String> rank() {
        return resultingRanking().entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }
}

