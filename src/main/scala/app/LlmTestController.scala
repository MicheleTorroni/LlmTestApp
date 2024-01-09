package app

import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters.*

/**
 * Main controller trait of the application.
 * Defines the core functionalities of the controller.
 */
trait LlmTestController extends LlmTestModelObserver, LlmTestViewObserver:
  /**
   * Handles the model's response.
   * @param outputText Output text from the model.
   */
  def modelResponse(outputText: String): Unit
  /**
   * Produces a response based on the provided input.
   * @param inputText Input text.
   */
  def produceResponse(inputText: String): Unit
  /**
   * Creates an OpenAI service using the specified API key.
   * @param myApiKey OpenAI API key.
   */
  def createOpenAiService(myApiKey: String): Unit
  /**
   * Creates a local service using the specified address.
   * @param address Local service address.
   */
  def createLocalService(address: String): Unit
  /**
   * Executes a specified command.
   * @param command Command to execute.
   */
  def runCommand(command: String): Unit
  /**
   * Initializes a chat session with the specified model and programming language.
   * @param llmModel Language model.
   * @param programmingLanguage Programming language.
   */
  def initializeChat(llmModel: String, programmingLanguage: String): Unit
  /**
   * Retrieves the chat log.
   * @return Chat log.
   */
  def getChatLog : String

/**
 * Companion object for the LlmTestController trait.
 * Provides a construction method to create an instance of the controller.
 */
object LlmTestController:
  /**
   * Creates an instance of the llmTestController.
   **/
  def apply(): LlmTestController = LlmTestControllerImpl()

  /**
   * Controller implementation.
   */
  case class LlmTestControllerImpl() extends LlmTestController :
    val model: LlmTestModel = LlmTestModel()
    model.addObserver(this)

    val config: Config = ConfigFactory.load("config")
    val view: LlmTestView = LlmTestView(
      config.getList("llmModels").unwrapped().asScala.toList.map(el => el.toString),
      config.getList("programmingLanguages").unwrapped().asScala.toList.map(el => el.toString))
    view.addObserver(this)
    view.setOpenAiApiKey(config.getString("openAiApiKey"))
    view.setAddressField(config.getString("address"))
    view.setCommandField(config.getString("cmdCommand"))
    view.visible = true

    override def modelResponse(outputText: String): Unit = view.setOutputText(outputText)
    override def produceResponse(inputText: String): Unit = model.produceResponse(inputText)
    override def createOpenAiService(myApiKey: String): Unit = model.createOpenAiService(myApiKey)
    override def createLocalService(address: String): Unit = model.createLocalService(address)
    override def initializeChat(llmModel: String, programmingLanguage: String): Unit =
      model.initializeChat(llmModel, config.getString("basePrompt")+programmingLanguage)
    override def runCommand(command: String): Unit = model.runCommand(command)
    override def getChatLog : String = model.getChatLog