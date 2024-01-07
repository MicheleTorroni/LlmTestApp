package app

/**
 * Observer trait for the view in the application.
 * Defines methods to respond to various user interactions.
 */
trait LlmTestViewObserver :
  /**
   * Produces a response based on the provided input text.
   * @param inputText Input text.
   */
  def produceResponse(inputText: String): Unit
  /**
   * Creates an OpenAI service with the provided API key.
   * @param myApiKey OpenAI API key.
   */
  def createOpenAiService(myApiKey: String): Unit
  /**
   * Creates a local service with the specified address.
   * @param address Address for the local service.
   */
  def createLocalService(address: String): Unit
  /**
   * Runs a command in the application.
   * @param command Command to be executed.
   */
  def runCommand(command: String): Unit
  /**
   * Initializes the chat with the specified language model and programming language.
   * @param llmModel Language model for the chat.
   * @param programmingLanguage Programming language context for the chat.
   */
  def initializeChat(llmModel: String, programmingLanguage: String): Unit
  /**
   * Retrieves the chat log.
   * @return String representing the chat log.
   */
  def getChatLog() : String
