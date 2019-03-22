package example

import java.net.HttpURLConnection._

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{Context, LambdaLogger}
import org.mockito.Mockito.when
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

class HelloHandlerSpec extends FlatSpec with Matchers with MockitoSugar {
  trait Setup {
    val helloHandler = new HelloHandler()
    val mockContext: Context = mock[Context]
    when(mockContext.getLogger).thenReturn(mock[LambdaLogger])
  }

  "The hello handler" should "say hello" in new Setup {
    val result: Either[Nothing, APIGatewayProxyResponseEvent] = helloHandler.handle(new APIGatewayProxyRequestEvent().withBody("{}"), mockContext)

    result.isRight shouldBe true
    val Right(responseEvent) = result
    responseEvent.getStatusCode shouldEqual HTTP_OK
    responseEvent.getBody shouldEqual "Hello from api-platform-example-lambda!"
  }
}
