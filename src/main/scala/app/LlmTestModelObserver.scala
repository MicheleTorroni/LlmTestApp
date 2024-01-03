package app

trait LlmTestModelObserver :
  def modelResponse(outputText: String): Unit
