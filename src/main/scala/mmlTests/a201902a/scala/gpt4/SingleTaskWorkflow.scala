package mmlTests.a201902a.scala.gpt4

import mmlTests.a201902a.scala.WorkflowsFactory
import mmlTests.a201902a.scala.Workflow
import java.util.{HashSet, List, ArrayList, Set, LinkedHashMap, HashMap}
import scala.jdk.CollectionConverters._

class SingleTaskWorkflow[T](task: T) extends AbstractWorkflow[T](Set.of(task), Set.of(task)) {}
