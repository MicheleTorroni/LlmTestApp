//package mmlTests.a202003c
//
//import org.junit.Assert._
//import mmlTests.a202003c.scala._
//import mmlTests.shared.java.Pair
//
//
//object Test2020a03c { // lab e project: una enum usata in alcuni test
//  /*
//     * Implementare l'interfaccia TableFactory come indicato nel metodo initFactory
//     * qui sotto. Realizza una factory per delle Table<R,C,V>, ossia strutture dati che modellano
//     * una tabella con una informazione per riga (tipo R) una per colonna (tipo C): quindi ogni cella
//     * è identificata da un valore di riga, uno di colonna, e ha un valore (tipo V) inserito nella cella.
//     *
//     * Il commento alle interfacce fornite, e i metodi di test qui sotto
//     * costituiscono la necessaria spiegazione del problema.
//     *
//     * Sono considerati opzionali ai fini della possibilità di correggere
//     * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
//     * punteggio:
//     *
//     * - implementazione dei test chiamati optionalTestXYZ (relativi al Table.asColumnMap e a TableFactory.squareMatrix)
//     *
//     * - la buona progettazione della soluzione, in particolare utilizzando codice succinto e senza ripetizioni
//     *
//     * Si tolga il commento dal metodo initFactory.
//     *
//     * Indicazioni di punteggio:
//     *
//     * - correttezza della parte obbligatoria: 10 punti
//     *
//     * - correttezza della parte opzionale: 4 punti
//     *
//     * - qualità della soluzione: 3 punti
//     *
//     */private[a202003c]  object Esame extends Enumeration  { type Esame = Value
//    val LAB,PROJECT = Value
//  }
//}
//class Test2020a03c { private var factory = null
//  @org.junit.Before  def initFactory(): Unit =  { this.factory = new Nothing
//  }
//  @org.junit.Test  def testFromMap(): Unit =  { val map = new HashMap[Pair[String, Test2020a03c.Esame], Integer]
//    map.put(new Pair[String, Test2020a03c.Esame]("Rossi", Test2020a03c.Esame.LAB), 20)
//    map.put(new Pair[String, Test2020a03c.Esame]("Rossi", Test2020a03c.Esame.PROJECT), 28)
//    map.put(new Pair[String, Test2020a03c.Esame]("Verdi", Test2020a03c.Esame.LAB), 20)
//    map.put(new Pair[String, Test2020a03c.Esame]("Casadei", Test2020a03c.Esame.LAB), 19)
//    map.put(new Pair[String, Test2020a03c.Esame]("Grandi", Test2020a03c.Esame.PROJECT), 19)
//    // una tabella che associa persone (righe) e tipo d'esame (colonne), ad un voto
//    val table = this.factory.fromMap(map)
//    assertEquals(Set.of(Test2020a03c.Esame.LAB, Test2020a03c.Esame.PROJECT), table.columns)// colonne
//
//    assertEquals(Set.of("Rossi", "Verdi", "Casadei", "Grandi"), table.rows)// righe
//
//    assertEquals(Optional.empty, table.getValue("Casadei", Test2020a03c.Esame.PROJECT))// quanto ha preso Casadei nel progetto
//
//    assertEquals(Optional.of(19), table.getValue("Casadei", Test2020a03c.Esame.LAB))// quanto ha preso Casadei nell'esame in lab
//
//    table.putValue("Casadei", Test2020a03c.Esame.PROJECT, 25)// ora Casadei ha preso 25 nel progetto
//
//    assertEquals(Optional.of(25), table.getValue("Casadei", Test2020a03c.Esame.PROJECT))
//    assertEquals(Map.of(Test2020a03c.Esame.LAB, 20, Test2020a03c.Esame.PROJECT, 28), table.asRowMap.get("Rossi"))// i voti di Rossi
//
//    assertEquals(Map.of(Test2020a03c.Esame.LAB, 20), table.asRowMap.get("Verdi"))// i voti di Verdi
//
//    assertEquals(Map.of(Test2020a03c.Esame.PROJECT, 19), table.asRowMap.get("Grandi"))// i voti di Grandi
//
//  }
//  @org.junit.Test  def testFromFunction(): Unit =  { // la classica "tabellina del *, una tabella con interi (0..9) nelle righe e nelle colonne, e moltiplicazioni nelle celle
//    val range = Stream.iterate(0, (x: Integer) => x + 1).limit(10).collect(Collectors.toSet)
//    val table = this.factory.fromFunction(range, range, (i: Integer, j: Integer) => i * j)
//    assertEquals(range, table.columns)
//    assertEquals(range, table.rows)
//    assertEquals(Optional.empty, table.getValue(100, 100))// 100,100 non ha cella
//
//    assertEquals(Optional.of(54), table.getValue(6, 9))// riga 6, colonna 9 --> valore 54
//
//    assertEquals(Set.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45), new HashSet[Integer](table.asRowMap.get(5).values))// 5a riga, la tabellina del 5
//
//  }
//  @org.junit.Test  def testGraph(): Unit =  { val edges = Set.of(new Pair[String, String]("a", "b"), // a->b
//    new Pair[String, String]("b", "c"), // b->c
//    new Pair[String, String]("c", "d"), // c->d
//    new Pair[String, String]("d", "a"), // d->a
//    new Pair[String, String]("a", "a")// a->a)
//    // una tabella che rappresenta il grafo sopra (con true dove c'è arco, false dove non c'è)
//    val table = this.factory.graph(edges)
//    assertEquals(Set.of("a", "b", "c", "d"), table.columns)
//    assertEquals(Set.of("a", "b", "c", "d"), table.rows)
//    assertEquals(Optional.of(true), table.getValue("a", "b"))// c'è arco da a a b?
//
//    assertEquals(Optional.of(false), table.getValue("a", "c"))// c'è arco da a a c?
//
//    assertEquals(4, table.asRowMap.size)// 4 righe
//
//    assertEquals(4, table.asRowMap.get("a").size)// la prima riga ha 4 colonne
//
//    assertTrue(table.asRowMap.get("a").get("b"))
//    assertFalse(table.asRowMap.get("a").get("c"))
//  }
//  @org.junit.Test  def optionalTestArray(): Unit =  { val array = Array[Array[String]](Array[String]("a", "b"), Array[String]("c", "d"))
//    // una tabella che rappresenta una matrice, con numeri crescenti (indici) in righe e colonne
//    val table = this.factory.squareMatrix(array)
//    assertEquals(Set.of(0, 1), table.columns)
//    assertEquals(Set.of(0, 1), table.rows)
//    assertEquals(Optional.of("a"), table.getValue(0, 0))
//    assertEquals(Optional.of("b"), table.getValue(0, 1))
//    assertEquals(Optional.of("c"), table.getValue(1, 0))
//    assertEquals(Optional.of("d"), table.getValue(1, 1))
//    assertEquals(2, table.asRowMap.size)
//    assertEquals(2, table.asRowMap.get(0).size)
//    assertEquals("a", table.asRowMap.get(0).get(0))
//    assertEquals("b", table.asRowMap.get(0).get(1))
//  }
//  @org.junit.Test  def optionalTestColumnMap(): Unit =  { // come l'esempio nel testFromMap... ma testando la asColumnMap
//    val map = new HashMap[Pair[String, Test2020a03c.Esame], Integer]
//    map.put(new Pair[String, Test2020a03c.Esame]("Rossi", Test2020a03c.Esame.LAB), 20)
//    map.put(new Pair[String, Test2020a03c.Esame]("Rossi", Test2020a03c.Esame.PROJECT), 28)
//    map.put(new Pair[String, Test2020a03c.Esame]("Verdi", Test2020a03c.Esame.LAB), 20)
//    map.put(new Pair[String, Test2020a03c.Esame]("Casadei", Test2020a03c.Esame.LAB), 19)
//    map.put(new Pair[String, Test2020a03c.Esame]("Grandi", Test2020a03c.Esame.PROJECT), 19)
//    val table = this.factory.fromMap(map)
//    assertEquals(Map.of("Rossi", 20, "Verdi", 20, "Casadei", 19), table.asColumnMap.get(Test2020a03c.Esame.LAB))
//    assertEquals(Map.of("Rossi", 28, "Grandi", 19), table.asColumnMap.get(Test2020a03c.Esame.PROJECT))
//    // come l'esempio nel testGraph... ma testando la asColumnMap
//    val edges = Set.of(new Pair[String, String]("a", "b"), new Pair[String, String]("b", "c"), new Pair[String, String]("c", "d"), new Pair[String, String]("d", "a"), new Pair[String, String]("a", "a"))
//    val table2 = this.factory.graph(edges)
//    assertEquals(4, table2.asColumnMap.size)
//    assertEquals(4, table2.asColumnMap.get("a").size)
//    assertTrue(table2.asColumnMap.get("a").get("d"))
//    assertTrue(table2.asColumnMap.get("a").get("a"))
//    assertFalse(table2.asColumnMap.get("a").get("b"))
//  }
//}
