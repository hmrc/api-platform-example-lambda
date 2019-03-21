package example

import java.net.HttpURLConnection._

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger}
import org.scalatest._

class HelloHandlerSpec extends FlatSpec with Matchers {
  trait Setup {
    val helloHandler = new HelloHandler()
  }

  "The hello handler" should "say hello" in new Setup {
    val result: Either[Nothing, APIGatewayProxyResponseEvent] = helloHandler.handle(new APIGatewayProxyRequestEvent(), TestContext)

    result.isRight shouldBe true
    val Right(responseEvent) = result
    responseEvent.getStatusCode shouldEqual HTTP_OK
    responseEvent.getBody shouldEqual "Hello from api-platform-example-lambda!"
  }
}

object TestContext extends Context {
  override def getAwsRequestId: String = ???
  override def getLogGroupName: String = ???
  override def getLogStreamName: String = ???
  override def getFunctionName: String = ???
  override def getFunctionVersion: String = ???
  override def getInvokedFunctionArn: String = ???
  override def getIdentity: CognitoIdentity = ???
  override def getClientContext: ClientContext = ???
  override def getRemainingTimeInMillis: Int = ???
  override def getMemoryLimitInMB: Int = ???
  override def getLogger: LambdaLogger = ???
}
