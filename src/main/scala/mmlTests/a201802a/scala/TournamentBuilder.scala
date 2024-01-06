//package mmlTests.a201802a.scala.gpt35turbo
//
//import mmlTests.a201802a.scala.Tournament
//
//import java.util
//
///**
// * The interface for a builder of Tournament, with fluid interface
// * If something goes wrong in the ways the methods are called, IllegalStateException is to be called.
// * Note: managing of some of such Exceptions are optional in this exam
// */trait TournamentBuilder { /**
// * Sets the type, which is mandatory and can't be null!
// */
//  import mmlTests.a201802a.scala.Result.*
//  import mmlTests.a201802a.scala.Type.*
//  def setType(`type`: Type): TournamentBuilder
//  /**
//   * Sets the name, which is mandatory and can't be null!
//   */def setName(name: String): TournamentBuilder
//  /**
//   * Sets the ranking before the tournament starts, this is mandatory and can't be null!
//   * It should be called only after setType is called
//   */def setPriorRanking(ranking: util.Map[String, Integer]): TournamentBuilder
//  /**
//   * Adds the result of a player at this tournament.
//   * It should be called only after ranking has been set.
//   * Note that a player can have only a single result, and one player should be the winner.
//   */
//
//  def addResult(player: String, result: Result): TournamentBuilder
//  /**
//   * If everything is ok, it creates the Tournament, and from then this object is useless
//   */def build: Tournament
//}
