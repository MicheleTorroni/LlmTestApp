trait LlmTestViewObserver :
  def produceResponse(inputText: String): Unit
  def createOpenAiService(myApiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit
  def initializeChat(llmModel: String, programmingLanguage: String): Unit
  def getChatLog() : String
