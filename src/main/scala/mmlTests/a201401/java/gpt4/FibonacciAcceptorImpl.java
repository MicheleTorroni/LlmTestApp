package mmlTests.a201401.java.gpt4;

import mmlTests.a201401.java.FibonacciAcceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FibonacciAcceptorImpl implements FibonacciAcceptor {
    private Map<String, List<Long>> sequences = new HashMap<>();
    private String currentSequence;
    private List<Long> sequence;

    public void reset(String sequenceName){
        if(sequences.containsKey(sequenceName))
            throw new IllegalArgumentException();

        currentSequence = sequenceName;
        sequence = new ArrayList<>();
        sequences.put(currentSequence, sequence);
    }

    public boolean consumeNext(long l){
        if(currentSequence == null || sequences.get(currentSequence) == null)
            throw new IllegalStateException();

        int len = sequences.get(currentSequence).size();

        if(len < 2){
            sequences.get(currentSequence).add(l);
            return true;
        }else {
            long lastNum = sequences.get(currentSequence).get(len - 1);
            long secondLastNum = sequences.get(currentSequence).get(len - 2);

            if (l == lastNum + secondLastNum){
                sequences.get(currentSequence).add(l);
                return true;
            }else
                return false;
        }
    }

    public List<Long> getCurrentSequence(){
        if(currentSequence == null || sequences.get(currentSequence) == null)
            throw new IllegalStateException();

        return sequences.get(currentSequence);
    }

    public Map<String, List<Long>> getAllSequences(){
        return sequences;
    }
}