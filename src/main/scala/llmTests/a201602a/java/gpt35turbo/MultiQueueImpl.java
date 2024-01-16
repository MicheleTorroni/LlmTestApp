package llmTests.a201602a.java.gpt35turbo;

import llmTests.a201602a.java.MultiQueue;

import java.util.*;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    private Map<Q, LinkedList<T>> queueMap = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        return new HashSet<>(queueMap.keySet());
    }

    @Override
    public void openNewQueue(Q queue) {
        if (queueMap.containsKey(queue)) {
            throw new IllegalArgumentException("Queue is already available");
        } else {
            queueMap.put(queue, new LinkedList<>());
        }
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        checkQueueAvailability(queue);
        return queueMap.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        checkQueueAvailability(queue);
        queueMap.get(queue).addLast(elem);
    }

    @Override
    public Optional<T> dequeue(Q queue) {
        checkQueueAvailability(queue);
        LinkedList<T> linkedList = queueMap.get(queue);
        if (linkedList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(linkedList.pollFirst());
        }
    }

    @Override
    public Map<Q, Optional<T>> dequeueOneFromAllQueues() {
        Map<Q, Optional<T>> map = new HashMap<>();
        for (Q queue : queueMap.keySet()) {
            map.put(queue, dequeue(queue));
        }
        return map;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> elementsSet = new HashSet<>();
        for (LinkedList<T> queue : queueMap.values()) {
            elementsSet.addAll(queue);
        }
        return elementsSet;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        checkQueueAvailability(queue);
        LinkedList<T> linkedList = queueMap.get(queue);
        List<T> elements = new ArrayList<>(linkedList);
        linkedList.clear();
        return elements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        checkQueueAvailability(queue);
        if (queueMap.size() <= 1) {
            throw new IllegalStateException("No alternative queue available for reallocation");
        } else {
            LinkedList<T> closingQueue = queueMap.remove(queue);
            while (!closingQueue.isEmpty()) {
                for (Q q : queueMap.keySet()) {
                    queueMap.get(q).addLast(closingQueue.pollFirst());
                    if (closingQueue.isEmpty()) break;
                }
            }
        }
    }

    private void checkQueueAvailability(Q queue) {
        if (!queueMap.containsKey(queue)) {
            throw new IllegalArgumentException("Queue is not available");
        }
    }
}
