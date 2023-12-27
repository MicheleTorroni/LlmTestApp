trait LlmTestViewObserver :
  def produceCode(inputText: String, llmModel: String, programmingLanguage: String): Unit
  def createOpenAiService(openAiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit

