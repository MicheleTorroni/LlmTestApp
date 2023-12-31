import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters.*

trait LlmTestTestController extends LlmTestModelObserver, LlmTestViewObserver:
  def modelResponse(outputText: String): Unit
  def produceResponse(inputText: String): Unit
  def createOpenAiService(myApiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit
  def initializeChat(llmModel: String, programmingLanguage: String): Unit

object LlmTestTestController extends App:
  def apply(): LlmTestTestController = LlmTestTestControllerImpl()

  case class LlmTestTestControllerImpl() extends LlmTestTestController :
    val myModel: LlmTestModel = LlmTestModel()
    myModel.addObserver(this)

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
    override def produceResponse(inputText: String): Unit = myModel.produceResponse(inputText)
    override def createOpenAiService(myApiKey: String): Unit = myModel.createOpenAiService(myApiKey)
    override def createLocalService(address: String): Unit = myModel.createLocalService(address)
    override def initializeChat(llmModel: String, programmingLanguage: String): Unit =
      myModel.initializeChat(llmModel, config.getString("basePrompt")+programmingLanguage)
    override def runCommand(command: String): Unit = myModel.runCommand(command)