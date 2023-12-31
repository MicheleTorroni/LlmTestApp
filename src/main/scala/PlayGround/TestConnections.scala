import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.javadsl.Sink
import io.cequence.openaiscala.domain.settings.{CreateChatCompletionSettings, CreateCompletionSettings}
import io.cequence.openaiscala.domain.{ChatRole, MessageSpec, ModelId, SystemMessage, UserMessage, AssistantMessage}
import io.cequence.openaiscala.service.{OpenAICoreServiceFactory, OpenAIServiceFactory, OpenAIServiceStreamedFactory}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps
import scala.sys.process.Process

@main
def main(): Unit = {
  println("Start!")
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  implicit val materializer: Materializer = Materializer(ActorSystem())


  //__________CHATGPT
  val service = OpenAIServiceStreamedFactory( apiKey = "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")

  val createChatCompletionSettings = CreateChatCompletionSettings(
    model = ModelId.gpt_4_0613
  )

  val messages = Seq(
    SystemMessage("You are a helpful assistant."),
    UserMessage("Which Model version are you using?")
  )

  service.createChatCompletion(
    messages = messages,
    settings = createChatCompletionSettings
  ).map { chatCompletion =>
    println(chatCompletion.choices.head.message.content)
  }

  println("End!")
}