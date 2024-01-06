package mmlTests.a201902a.java.gpt4;

import mmlTests.a201902a.java.Workflow;
import mmlTests.a201902a.java.WorkflowsFactory;
import mmlTests.shared.java.Pair;

import java.util.*;

public class WorkflowsFactoryImpl implements WorkflowsFactory {

	public WorkflowsFactoryImpl() {}

	@Override
	public <T> Workflow<T> singleTask(T task) {
		return new WorkflowImpl<>(Collections.singletonList(new Pair<>(Collections.singleton(task), new HashSet<>())));
	}

	@Override
	public <T> Workflow<T> tasksSequence(List<T> tasks) {
		List<Pair<Set<T>, Set<T>>> dependencies = new ArrayList<>();
		for (int i = 0; i < tasks.size() - 1; i++) {
			dependencies.add(new Pair<>(Collections.singleton(tasks.get(i)), Collections.singleton(tasks.get(i + 1))));
		}
		return new WorkflowImpl<>(dependencies);
	}

	@Override
	public <T> Workflow<T> tasksJoin(Set<T> initialTasks, T finalTask) {
		List<Pair<Set<T>, Set<T>>> dependencies = new ArrayList<>();
		dependencies.add(new Pair<>(initialTasks, Collections.singleton(finalTask)));
		return new WorkflowImpl<>(dependencies);
	}

	@Override
	public <T> Workflow<T> tasksFork(T initialTask, Set<T> finalTasks) {
		List<Pair<Set<T>, Set<T>>> dependencies = new ArrayList<>();
		for (T finalTask : finalTasks) {
			dependencies.add((new Pair<>(Collections.singleton(initialTask), Collections.singleton(finalTask))));
		}
		return new WorkflowImpl<>(dependencies);
	}

	@Override
	public <T> Workflow<T> concat(Workflow<T> first, Workflow<T> second) {
		List<Pair<Set<T>, Set<T>>> dependencies = new ArrayList<>();
		dependencies.addAll(((WorkflowImpl<T>) first).getDependencies());
		dependencies.addAll(((WorkflowImpl<T>) second).getDependencies());
		return new WorkflowImpl<>(dependencies);
	}

	public static class WorkflowImpl<T> implements Workflow<T> {
		private final Set<T> tasks;
		private final Map<T, Set<T>> dependencyMap;
		private final Set<T> completedTasks;

		WorkflowImpl(List<Pair<Set<T>, Set<T>>> dependencies) {
			this.tasks = new HashSet<>();
			this.dependencyMap = new HashMap<>();
			for (Pair<Set<T>, Set<T>> dependency : dependencies) {
				for (T t : dependency.getFst()) {
					this.tasks.add(t);
					this.dependencyMap.putIfAbsent(t, new HashSet<>());
				}
				for (T t : dependency.getSnd()) {
					this.tasks.add(t);
					this.dependencyMap.put(t, dependency.getFst());
				}
			}
			this.completedTasks = new HashSet<>();
		}

		List<Pair<Set<T>, Set<T>>> getDependencies() {
			List<Pair<Set<T>, Set<T>>> dependencies = new ArrayList<>();
			for (Map.Entry<T, Set<T>> entry : dependencyMap.entrySet()) {
				dependencies.add(new Pair<>(entry.getValue(), Collections.singleton(entry.getKey())));
			}
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
			for (Map.Entry<T, Set<T>> entry : dependencyMap.entrySet()) {
				if (!completedTasks.containsAll(entry.getValue())) {
					nextTasks.remove(entry.getKey());
				}
			}
			return nextTasks;
		}

		@Override
		public void doTask(T t) {
			if (!tasks.contains(t)) {
				throw new IllegalArgumentException("The task is not existent");
			}
			if (!getNextTasksToDo().contains(t)) {
				throw new IllegalStateException("Cannot execute task");
			}
			completedTasks.add(t);
		}

		@Override
		public boolean isCompleted() {
			return completedTasks.containsAll(tasks);
		}
	}
}