package llmTests.shared.scala

class Pair[X, Y](val fst: X, val snd: Y) {
  def getFst(): X = fst
  def getSnd(): Y = snd
  def getX(): X = fst
  def getY(): Y = snd

  override def hashCode(): Int = {
    val prime = 31
    var result = 1
    result = prime * result + (if (fst == null) 0 else fst.hashCode())
    result = prime * result + (if (snd == null) 0 else snd.hashCode())
    result
  }

  override def equals(obj: Any): Boolean = {
    if (this.eq(obj.asInstanceOf[AnyRef])) return true
    obj match {
      case other: Pair[_, _] =>
        if ((fst == null && other.fst != null) || (fst != null && !fst.equals(other.fst))) false
        else (snd == null && other.snd == null) || (snd != null && snd.equals(other.snd))
      case _ => false
    }
  }

  override def toString: String = s"[$fst;$snd]"
}
