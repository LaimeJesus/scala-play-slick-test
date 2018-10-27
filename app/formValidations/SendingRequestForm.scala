package formValidations

import play.api.data.Form
import play.api.data.Forms._

case class SendingRequestForm(name: String, origin: String, destination: String, items: List[ItemForm])

object SendingRequestForm {
  val sendingRequestForm: Form[SendingRequestForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "origin" -> nonEmptyText,
      "destination" -> nonEmptyText,
      "items" -> list(ItemForm.itemForm.mapping)
    )(SendingRequestForm.apply)(SendingRequestForm.unapply)
  }
}