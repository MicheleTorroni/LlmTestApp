//package mmlTests.a201802a.scala.gpt4
//
//import scala.jdk.CollectionConverters.*
//import scala.collection.mutable
//import mmlTests.a201802a.scala.TournamentBuilder
//import mmlTests.a201802a.scala.Tournament
//import mmlTests.a201802a.scala.Tournament.Type
//import mmlTests.a201802a.scala.Tournament.Result
//
//import java.util.{ArrayList, Collections, HashMap, List, Map}
//import java.util.Optional
//
//case class TournamentBuilderImpl() {
//  private var typeOfTournament: Tournament.Type = null
//  private var nameOfTournament: String = null
//  private var priorRanking: Map[String, Integer] = null
//  private var resultOfPlayer = new HashMap[String, Tournament.Result]()
//  private var tournamentBuilt = false
//  private val finalResultingRanking = new HashMap[String, Integer]
//  private var tournamentWinner: String = null
//
//  def winner = tournamentWinner
//
//  def resultingRanking = Collections.unmodifiableMap(finalResultingRanking)
//
//  def setType(t: Tournament.Type) = {
//    if(tournamentBuilt || t == null)
//      throw new IllegalStateException
//    typeOfTournament = t
//    this
//  }
//
//  def setName(s: String) = {
//    if(tournamentBuilt || s == null || typeOfTournament == null)
//      throw new IllegalStateException
//    nameOfTournament = s
//    this
//  }
//
//  def setPriorRanking(r: Map[String, Integer]) = {
//    if (tournamentBuilt || r == null || nameOfTournament == null)
//      throw new IllegalStateException
//    priorRanking = r
//    this
//  }
//
//  def addResult(player: String, result: Tournament.Result) = {
//    if (tournamentBuilt || resultOfPlayer.containsKey(player) || priorRanking == null)
//      throw new IllegalStateException
//    if (result == Tournament.Result.WINNER && resultOfPlayer.containsValue(Tournament.Result.WINNER))
//      throw new IllegalStateException
//    resultOfPlayer.put(player, result)
//    this
//  }
//
//  def build: Tournament = {
//    if (tournamentBuilt || typeOfTournament == null || nameOfTournament == null || priorRanking == null)
//      throw new IllegalStateException
//    tournamentBuilt = true
//    new TournamentImpl
//  }
//
//  private class TournamentImpl extends Tournament {
//    private val resultingRanking = new HashMap[String, Integer]
//    private var winner: String = null
//
//    resultOfPlayer.forEach((player: String, result: Tournament.Result) => {
//      var points = typeOfTournament match {
//        case Type.MAJOR => 2500
//        case Type.ATP1000 => 1000
//        case Type.ATP500 => 500
//        case Type.ATP250 => 250
//      }
//      points = result match {
//        case Result.WINNER => points
//        case Result.FINALIST => points / 2
//        case Result.SEMIFINALIST => points / 5
//        case Result.QUARTERFINALIST => points / 10
//        case Result.PARTICIPANT => 0
//      }
//      resultingRanking.put(player, priorRanking.getOrDefault(player, 0) + points)
//      if (result == Result.WINNER)
//        winner = player
//    })
//
//    priorRanking.forEach((player: String, _: Integer) => {
//      if (!resultingRanking.containsKey(player))
//        resultingRanking.put(player, priorRanking.get(player))
//    })
//
//    def getType = typeOfTournament
//
//    def getName = nameOfTournament
//
//    def getResult(player: String) = Optional.ofNullable(resultOfPlayer.get(player))
//
//    def winner = winner
//
//    def initialRanking = Collections.unmodifiableMap(priorRanking)
//
//    def resultingRanking = Collections.unmodifiableMap(resultingRanking)
//
//    def rank: List[String] = {
//      val ranking = new ArrayList[String](resultingRanking.keySet)
//      Collections.sort(ranking, (p1: String, p2: String) => resultingRanking.get(p2).compareTo(resultingRanking.get(p1)))
//      Collections.unmodifiableList(ranking)
//    }
//  }
//}