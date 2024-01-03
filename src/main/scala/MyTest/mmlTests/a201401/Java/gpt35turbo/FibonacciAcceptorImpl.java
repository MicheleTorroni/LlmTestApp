package MyTest.mmlTests.a201401.Java.gpt35turbo;

import java.util.*;
import MyTest.mmlTests.a201401.Java.FibonacciAcceptor;

public class FibonacciAcceptorImpl implements FibonacciAcceptor {

    private Map<String, List<Long>> sequences;
    private List<Long> currentSequence;

    public FibonacciAcceptorImpl() {
        sequences = new HashMap<>();
        currentSequence = null;
    }

    @Override
    public void reset(String sequenceName) {
        if (sequences.containsKey(sequenceName)) {
            throw new IllegalArgumentException("Sequence name already exists");
        }
        currentSequence = new ArrayList<>();
        sequences.put(sequenceName, currentSequence);
    }

    @Override
    public boolean consumeNext(long l) {
        if (currentSequence == null) {
            throw new IllegalStateException("No reset was initially done");
        }

        if (isFibonacciNumber(l)) {
            currentSequence.add(l);
            return true;
        }
        return false;
    }

    @Override
    public List<Long> getCurrentSequence() {
        if (currentSequence == null) {
            throw new IllegalStateException("No reset was initially done");
        }
        return currentSequence;
    }

    @Override
    public Map<String, List<Long>> getAllSequences() {
        return new HashMap<>(sequences);
    }

    private boolean isFibonacciNumber(long num) {
        if (currentSequence.isEmpty() || currentSequence.size() == 1) {
            return true;
        }

        long secondLast = currentSequence.get(currentSequence.size() - 2);
        long last = currentSequence.get(currentSequence.size() - 1);

        return num == (last + secondLast);
    }
}