package formValidations

import play.api.data.Form
import play.api.data.Forms._

case class CreateItemForm(name: String, weight: Long, size: Long)

object CreateItemForm {
  val itemForm: Form[CreateItemForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "weight" -> longNumber,
      "size" -> longNumber
    )(CreateItemForm.apply)(CreateItemForm.unapply)
  }
}