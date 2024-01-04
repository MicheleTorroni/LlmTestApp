package mmlTests.shared.scala

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
    if (this == obj) return true
    if (obj == null) return false
    if (!obj.isInstanceOf[Pair[_, _]]) return false
    val other = obj.asInstanceOf[Pair[_, _]]
    if (fst == null) {
      if (other.fst != null) return false
    } else if (!fst.equals(other.fst)) return false
    if (snd == null) {
      if (other.snd != null) return false
    } else if (!snd.equals(other.snd)) return false
    true
  }

  override def toString: String = s"[$fst;$snd]"
}
