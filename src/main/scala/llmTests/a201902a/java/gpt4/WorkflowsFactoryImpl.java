package llmTests.a201902a.java.gpt4;

import java.util.*;
import llmTests.a201902a.java.Workflow;
import llmTests.a201902a.java.WorkflowsFactory;

public class WorkflowsFactoryImpl implements WorkflowsFactory {
	@Override
	public <T> Workflow<T> singleTask(T task) {
		return new WorkflowImpl<>(Collections.singleton(task));
	}

	@Override
	public <T> Workflow<T> tasksSequence(List<T> tasks) {
		Map<T, Set<T>> dependencies = new HashMap<>();
		for (int i = 0; i < tasks.size() - 1; i++) {
			dependencies.put(tasks.get(i + 1), Collections.singleton(tasks.get(i)));
		}
		return new WorkflowImpl<>(new HashSet<>(tasks), dependencies);
	}

	@Override
	public <T> Workflow<T> tasksJoin(Set<T> initialTasks, T finalTask) {
		Map<T, Set<T>> dependencies = new HashMap<>();
		dependencies.put(finalTask, new HashSet<>(initialTasks));
		Set<T> tasks = initialTasks == null ? new HashSet<>() : new HashSet<>(initialTasks);
		tasks.add(finalTask);
		return new WorkflowImpl<>(tasks, dependencies);
	}

	@Override
	public <T> Workflow<T> tasksFork(T initialTask, Set<T> finalTasks) {
		Map<T, Set<T>> dependencies = new HashMap<>();
		for (T task : finalTasks) {
			dependencies.put(task, Collections.singleton(initialTask));
		}
		Set<T> tasks = finalTasks == null ? new HashSet<>() : new HashSet<>(finalTasks);
		tasks.add(initialTask);
		return new WorkflowImpl<>(tasks, dependencies);
	}

	@Override
	public <T> Workflow<T> concat(Workflow<T> first, Workflow<T> second) {
		Map<T, Set<T>> dependencies = new HashMap<>(((WorkflowImpl<T>) first).getDependencies());
		dependencies.putAll(((WorkflowImpl<T>) second).getDependencies());

		for (T task : second.getTasks()) {
			if (!first.getTasks().contains(task)) {
				dependencies.putIfAbsent(task, new HashSet<>());
				dependencies.get(task).addAll(first.getTasks());
			}
		}

		Set<T> tasks = new HashSet<>(first.getTasks());
		tasks.addAll(second.getTasks());
		return new WorkflowImpl<>(tasks, dependencies);
	}

	private static class WorkflowImpl<T> implements Workflow<T> {
		private final Set<T> tasks;
		private final Map<T, Set<T>> dependencies;
		private final Set<T> completedTasks = new HashSet<>();

		public WorkflowImpl(Set<T> tasks) {
			this(tasks, new HashMap<>());
		}

		public WorkflowImpl(Set<T> tasks, Map<T, Set<T>> dependencies) {
			this.tasks = tasks;
			this.dependencies = dependencies;
		}

		public Map<T, Set<T>> getDependencies() {
			return dependencies;
		}

		@Override
		public Set<T> getTasks() {
			return tasks;
		}

		@Override
		public Set<T> getNextTasksToDo() {
			Set<T> nextTasks = new HashSet<>(tasks);
			nextTasks.removeAll(completedTasks);
			for (Map.Entry<T, Set<T>> entry : dependencies.entrySet()) {
				if (!completedTasks.containsAll(entry.getValue())) {
					nextTasks.remove(entry.getKey());
				}
			}
			return nextTasks;
		}

		@Override
		public void doTask(T t) {
			if (!tasks.contains(t)) {
				throw new IllegalArgumentException();
			}

			if (!getNextTasksToDo().contains(t)) {
				throw new IllegalStateException();
			}

			completedTasks.add(t);
		}

		@Override
		public boolean isCompleted() {
			return completedTasks.size() == tasks.size();
		}
	}
}