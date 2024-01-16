//package mmlTests.a201902a.scala.gpt35turbo
//
//import java.util
//import java.util.{List, Set}
//import mmlTests.a201902a.scala.{Workflow,WorkflowsFactory}
//import mmlTests.a201902a.scala.gpt35turbo.Task
//
//
//class WorkflowsFactoryImpl extends WorkflowsFactory {
//  def singleTask[T](task: T): Workflow[T] = {
//    val t = new Task(task, Set.empty)
//    new Workflow(Set(t))
//  }
//
//  def tasksSequence[T](tasks: util.List[T]): Workflow[T] = {
//    var previousTask: Option[Task[T]] = None
//    val taskSet = tasks.map { t =>
//      val prerequisites = previousTask.map(task => Set(task.task)).getOrElse(Set.empty)
//      previousTask = Some(new Task(t, prerequisites))
//      previousTask.get
//    }.toSet
//
//    new Workflow(taskSet)
//  }
//
//  def tasksJoin[T](initialTasks: util.Set[T], finalTask: T): Workflow[T] = {
//    val finalTaskDependencies = initialTasks.map(Task(_, Set(finalTask)))
//    val initialTaskDependencies = initialTasks.flatMap(t => Set(Task(t, Set.empty, true), Task(finalTask, Set(t))))
//
//    new Workflow(initialTaskDependencies ++ finalTaskDependencies, initialTasks)
//  }
//
//  def tasksFork[T](initialTask: T, finalTasks: util.Set[T]): Workflow[T] = {
//    val initialTaskDependencies = Set(Task(initialTask, finalTasks))
//    val finalTaskDependencies = finalTasks.map(Task(_, Set.empty))
//
//    new Workflow(initialTaskDependencies ++ finalTaskDependencies, Set(initialTask))
//  }
//
//  def concat[T](first: Workflow[T], second: Workflow[T]): Workflow[T] = {
//    val combinedTasks = first.getTasks ++ second.getTasks
//    val combinedCompletedTasks = first.completedTasks ++ second.completedTasks
//    val combinedTaskSet = Set(combinedTasks.map(t => new Task[T](t, Set.empty, combinedCompletedTasks.contains(t))): _*)
//
//    new Workflow(combinedTaskSet, combinedCompletedTasks)
//  }
//}
