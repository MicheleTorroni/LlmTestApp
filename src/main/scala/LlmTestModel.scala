import akka.actor.ActorSystem
import akka.stream.Materializer
import io.cequence.openaiscala.domain.{ChatRole, MessageSpec, ModelId}
import io.cequence.openaiscala.domain.settings.{CreateChatCompletionSettings, CreateCompletionSettings}
import io.cequence.openaiscala.service.{OpenAICoreService, OpenAICoreServiceFactory, OpenAIService, OpenAIServiceFactory}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.sys.process.Process

trait LlmTestModel:
  def produceCode(basePrompt: String,inputText: String, llmModel: String): Unit
  def createOpenAiService(myApiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit
  def addObserver(modelObserver: LlmTestModelObserver): Unit

object LlmTestModel:
  def apply(): LlmTestModel = LlmTestModelImpl()

  private case class LlmTestModelImpl() extends LlmTestModel:
    implicit val ec: ExecutionContextExecutor = ExecutionContext.global
    implicit val materializer: Materializer = Materializer(ActorSystem())
    var service : OpenAICoreService = null
    var modelObservers : List[LlmTestModelObserver] = List()

    override def createOpenAiService(myApiKey: String): Unit = service = OpenAIServiceFactory(apiKey = myApiKey)
    override def createLocalService(address: String): Unit = service = OpenAICoreServiceFactory(address)
    override def runCommand(command: String): Unit = Process(command).run()
    override def addObserver(modelObserver: LlmTestModelObserver): Unit = modelObservers = modelObserver :: modelObservers
    override def produceCode(basePrompt: String,inputText: String, llmModel: String): Unit =
      val createChatCompletionSettings = CreateChatCompletionSettings(model = llmModel)
      val message: Seq[MessageSpec] = Seq(
        MessageSpec(role = ChatRole.User, content = basePrompt),
        MessageSpec(role = ChatRole.User, content = inputText))
      service.createChatCompletion(messages = message, settings = createChatCompletionSettings
      ).map { chatCompletion =>
        modelObservers.foreach(obs => obs.modelResponse(chatCompletion.choices.head.message.content))}
