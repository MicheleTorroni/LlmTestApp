package llmTests.a201602a.java.gpt4;

import llmTests.a201602a.java.MultiQueue;

import java.util.*;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    private Map<Q, Queue<T>> queues;

    public MultiQueueImpl() {
        queues = new HashMap<>();
    }

    @Override
    public Set<Q> availableQueues() {
        return queues.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {
        if (queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue already available");
        }
        queues.put(queue, new LinkedList<>());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if (!queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue not available");
        }
        return queues.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        if (!queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue not available");
        }
        queues.get(queue).add(elem);
    }

    @Override
    public Optional<T> dequeue(Q queue) {
        if (!queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue not available");
        }
        Queue<T> selectedQueue = queues.get(queue);
        if (selectedQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(selectedQueue.poll());
    }

    @Override
    public Map<Q, Optional<T>> dequeueOneFromAllQueues() {
        Map<Q, Optional<T>> dequeuedElements = new HashMap<>();

        for (Q queue : queues.keySet()) {
            Optional<T> element = dequeue(queue);
            dequeuedElements.put(queue, element);
        }

        return dequeuedElements;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> enqueuedElements = new HashSet<>();

        for (Queue<T> queue : queues.values()) {
            enqueuedElements.addAll(queue);
        }

        return enqueuedElements;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if (!queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue not available");
        }
        Queue<T> selectedQueue = queues.get(queue);
        List<T> dequeuedElements = new ArrayList<>(selectedQueue);
        selectedQueue.clear();
        return dequeuedElements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        if (!queues.containsKey(queue)) {
            throw new IllegalArgumentException("Queue not available");
        }
        if (queues.size() == 1) {
            throw new IllegalStateException("There's no other queue to move elements to");
        }

        Queue<T> selectedQueue = queues.get(queue);
        queues.remove(queue);

        for (Q otherQueue : queues.keySet()) {
            queues.get(otherQueue).addAll(selectedQueue);
        }
    }
}