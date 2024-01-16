package llmTests.a201902a.java.gpt35turbo;

import llmTests.a201902a.java.Workflow;

import java.util.*;

class WorkflowImpl<T> implements Workflow<T> {

    private Set<T> tasks;
    private Set<T> completedTasks;
    private Map<T, Set<T>> dependencies;

    public WorkflowImpl(Set<T> tasks, Set<T> completedTasks, Map<T, Set<T>> dependencies) {
        this.tasks = tasks;
        this.completedTasks = completedTasks;
        this.dependencies = dependencies;
    }

    public Set<T> getTasks() {
        return tasks;
    }

    public boolean isCompleted() {
        return tasks.isEmpty();
    }

    public Set<T> getNextTasksToDo() {
        Set<T> nextTasks = new HashSet<>();
        for (T task : tasks) {
            boolean canBeExecuted = true;
            Set<T> taskDependencies = dependencies.get(task);
            if (taskDependencies != null) {
                for (T dependency : taskDependencies) {
                    if (!completedTasks.contains(dependency)) {
                        canBeExecuted = false;
                    }
                }
            }
            if (canBeExecuted) {
                nextTasks.add(task);
            }
        }
        return nextTasks;
    }

    public void doTask(T task) {
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException();
        }
        for (T dependency : dependencies.keySet()) {
            dependencies.get(dependency).remove(task);
        }
        tasks.remove(task);
        completedTasks.add(task);
    }
}