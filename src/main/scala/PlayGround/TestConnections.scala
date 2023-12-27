import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.javadsl.Sink
import io.cequence.openaiscala.domain.settings.{CreateChatCompletionSettings, CreateCompletionSettings}
import io.cequence.openaiscala.domain.{ChatRole, MessageSpec, ModelId}
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

  //__________FREE
  //  Process("taskkill /F /FI \"IMAGENAME eq wsl.exe\"").run()
  //  Thread.sleep(500)
  //  Process("cmd /c start wsl litellm --model ollama/llama2 --debug").run()
  //  Thread.sleep(6000)
  //val service = OpenAICoreServiceFactory("http://localhost:8000/")

  val text = """Extract the name and mailing address from this email:
               |Dear Kelly,
               |It was great to talk to you at the seminar. I thought Jane's talk was quite good.
               |Thank you for the book. Here's my address 2111 Ash Lane, Crestview CA 92002
               |Best,
               |Maya
             """.stripMargin

  val createChatCompletionSettings = CreateChatCompletionSettings(
    model = ModelId.gpt_3_5_turbo_16k
  )

  val messages: Seq[MessageSpec] = Seq(
    MessageSpec(role = ChatRole.User, content = "Can you give me simple code to produce a GUI with 3 button in language Scala 3?")
  )

  service.createChatCompletion(
    messages = messages,
    settings = createChatCompletionSettings
  ).map { chatCompletion =>
    println(chatCompletion.choices.head.message.content)
  }



//
//  service.createCompletion(text).map(completion =>
//    println(completion.choices.head.text)
//  )

  println("End!")
}