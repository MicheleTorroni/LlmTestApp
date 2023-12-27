import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters.*

trait LlmTestTestController extends LlmTestModelObserver, LlmTestViewObserver:
  def modelResponse(outputText: String): Unit
  def produceCode(inputText: String, llmModel: String, programmingLanguage: String): Unit
  def createOpenAiService(myApiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit

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

    def modelResponse(outputText: String): Unit = view.setOutputText(outputText)
    def produceCode(inputText: String, llmModel: String, programmingLanguage: String): Unit =
      myModel.produceCode(config.getString("basePrompt")+programmingLanguage, inputText, llmModel)
    def createOpenAiService(myApiKey: String): Unit = myModel.createOpenAiService(myApiKey)
    def createLocalService(address: String): Unit = myModel.createLocalService(address)
    def runCommand(command: String): Unit = myModel.runCommand(command)


