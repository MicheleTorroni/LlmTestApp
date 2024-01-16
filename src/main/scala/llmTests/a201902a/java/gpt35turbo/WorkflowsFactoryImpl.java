package llmTests.a201902a.java.gpt35turbo;

import llmTests.a201902a.java.Workflow;
import llmTests.a201902a.java.WorkflowsFactory;

import java.util.*;
import java.util.stream.*;

public class WorkflowsFactoryImpl implements WorkflowsFactory {

	public <T> Workflow<T> singleTask(T task) {
		Set<T> tasks = new HashSet<>();
		tasks.add(task);
		Set<T> completedTasks = new HashSet<>();
		Map<T, Set<T>> dependencies = new HashMap<>();
		return new WorkflowImpl<>(tasks, completedTasks, dependencies);
	}

	public <T> Workflow<T> tasksSequence(List<T> tasks) {
		Set<T> tasksSet = new HashSet<>(tasks);
		Set<T> completedTasks = new HashSet<>();
		Map<T, Set<T>> dependencies = new HashMap<>();
		T predecessor = null;
		for (T task : tasks) {
			if (predecessor != null) {
				Set<T> dependenciesSet = new HashSet<>();
				dependenciesSet.add(predecessor);
				dependencies.put(task, dependenciesSet);
			} else {
				dependencies.put(task, new HashSet<>());
			}
			predecessor = task;
		}
		return new WorkflowImpl<>(tasksSet, completedTasks, dependencies);
	}

	public <T> Workflow<T> tasksJoin(Set<T> tasks, T joinTask) {
		Set<T> tasksSet = new HashSet<>(tasks);
		tasksSet.add(joinTask);
		Set<T> completedTasks = new HashSet<>();
		Map<T, Set<T>> dependencies = new HashMap<>();
		for (T task : tasks) {
			dependencies.put(task, new HashSet<>(Collections.singletonList(joinTask)));
		}
		dependencies.put(joinTask, new HashSet<>());
		return new WorkflowImpl<>(tasksSet, completedTasks, dependencies);
	}

	public <T> Workflow<T> tasksFork(T startTask, Set<T> tasks) {
		Set<T> tasksSet = new HashSet<>(tasks);
		tasksSet.add(startTask);
		Set<T> completedTasks = new HashSet<>();
		Map<T, Set<T>> dependencies = new HashMap<>();
		for (T task : tasks) {
			dependencies.put(startTask, new HashSet<>(tasks));
			dependencies.put(task, new HashSet<>());
		}
		return new WorkflowImpl<>(tasksSet, completedTasks, dependencies);
	}

	public <T> Workflow<T> concat(Workflow<T> w1, Workflow<T> w2) {
		Set<T> tasks = new HashSet<>();
		tasks.addAll(w1.getTasks());
		tasks.addAll(w2.getTasks());
		Set<T> completedTasks = new HashSet<>();
		Map<T, Set<T>> dependencies = new HashMap<>();
		dependencies.putAll(w1.getTasks().stream().collect(Collectors.toMap(task -> task, task -> w1.getTasks())));
		dependencies.putAll(w2.getTasks().stream().collect(Collectors.toMap(task -> task, task -> w2.getTasks())));
		return new WorkflowImpl<>(tasks, completedTasks, dependencies);
	}
}
