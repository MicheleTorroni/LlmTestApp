import akka.actor.ActorSystem
import akka.stream.Materializer
import io.cequence.openaiscala.domain.{ChatRole, MessageSpec, ModelId}
import io.cequence.openaiscala.domain.settings.{CreateChatCompletionSettings, CreateCompletionSettings}
import io.cequence.openaiscala.service.{OpenAICoreService, OpenAICoreServiceFactory, OpenAIService, OpenAIServiceFactory}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.sys.process.Process

trait LlmTestModel:
  def produceResponse(inputText: String): Unit
  def createOpenAiService(myApiKey: String): Unit
  def createLocalService(address: String): Unit
  def runCommand(command: String): Unit
  def addObserver(modelObserver: LlmTestModelObserver): Unit
  def initializeChat(llmModel: String, basePrompt: String): Unit

object LlmTestModel:
  def apply(): LlmTestModel = LlmTestModelImpl()

  private case class LlmTestModelImpl() extends LlmTestModel:
    implicit val ec: ExecutionContextExecutor = ExecutionContext.global
    implicit val materializer: Materializer = Materializer(ActorSystem())
    var modelObservers : List[LlmTestModelObserver] = List()
    var service : OpenAICoreService = null
    var createChatCompletionSettings: CreateChatCompletionSettings = null
    var message: Seq[MessageSpec] = null

    override def createOpenAiService(myApiKey: String): Unit = service = OpenAIServiceFactory(apiKey = myApiKey)
    override def createLocalService(address: String): Unit = service = OpenAICoreServiceFactory(address)
    override def initializeChat(llmModel: String, basePrompt: String): Unit =
      addMessage(basePrompt, ChatRole.System)
      createChatCompletionSettings = CreateChatCompletionSettings(model = llmModel)
      println(message)
    override def runCommand(command: String): Unit = Process(command).run()
    override def addObserver(modelObserver: LlmTestModelObserver): Unit = modelObservers = modelObserver :: modelObservers
    override def produceResponse(inputText: String): Unit =
      addMessage(inputText, ChatRole.User)
      println(message)
      service.createChatCompletion(messages = message, settings = createChatCompletionSettings
      ).map { chatCompletion => {
        val response = chatCompletion.choices.head.message.content
        addMessage(response, ChatRole.Assistant)
        modelObservers.foreach(obs => obs.modelResponse(response))}}
    def addMessage(msg: String, msgRole: ChatRole): Unit = message = message match
        case null => Seq(MessageSpec(role = msgRole, content = msg))
        case _ => message :+ MessageSpec(role = msgRole, content = msg)
