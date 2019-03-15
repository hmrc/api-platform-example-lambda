package example

import com.amazonaws.services.lambda.runtime.Context
import io.circe.generic.auto._
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

case class Hello(outputMsg: String)

class HelloHandler extends Lambda[None.type, Hello] {
  override def handle(input: None.type, context: Context): Either[Nothing, Hello] = {
    Right(Hello("Hello from api-platform-example-lambda!"))
  }
}
