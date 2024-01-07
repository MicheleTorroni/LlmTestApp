package app

/**
 * Model observer trait.
 * Defines the interface for model observers.
 */
trait LlmTestModelObserver :
  /**
   * Notifies the observer of the model's response.
   * @param outputText Model's output text.
   */
  def modelResponse(outputText: String): Unit
