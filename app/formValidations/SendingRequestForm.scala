package formValidations

import models.Item
import play.api.data.Form
import play.api.data.Forms._

case class SendingRequestForm(origin: String, destination: String,isFragile: Boolean, items: List[Item], refrigerationLevel: String)

object SendingRequestForm {
  val sendingRequestForm: Form[SendingRequestForm] = Form {
    mapping(
      "origin" -> nonEmptyText,
      "destination" -> nonEmptyText,
      "isFragile" -> boolean,
      "items" -> ItemForm.itemForm.mapping(), // raro, deberia romper y funcionar con list(ItemForm.itemForm.mapping())
      "refrigerationLevel" -> nonEmptyText,
    )(SendingRequestForm.apply)(SendingRequestForm.unapply)
  }
}