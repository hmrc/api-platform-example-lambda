package example

import java.net.HttpURLConnection._

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import io.circe.generic.auto._
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

class HelloHandler extends Lambda[String, APIGatewayProxyResponseEvent] {
  override def handle(input: String, context: Context): Either[Nothing, APIGatewayProxyResponseEvent] = {
    val logger = context.getLogger

    logger.log(input)

    val response = new APIGatewayProxyResponseEvent().withStatusCode(HTTP_OK).withBody("Hello from api-platform-example-lambda!")
    Right(response)
  }
}
