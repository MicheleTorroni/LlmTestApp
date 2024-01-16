package llmTests.a201706.scala

import java.util
import java.util.List
import java.util.stream.*


/**
 * An immutable, infinite sequence of elements of type X, to be accessed once and sequentially
 */trait Sequence[X] { /**
 * @return the next element; when this is returned, the next call will give the successive element without exceptions
 */def nextElement: X
  /**
   * @param size
   * @return a list with the next size elements (already implemented via default)
   */def nextListOfElements(size: Int): util.List[X] = Stream.generate(() => this.nextElement).limit(size).collect(Collectors.toList)
}
