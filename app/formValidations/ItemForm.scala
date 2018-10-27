package formValidations

import play.api.data.Form
import play.api.data.Forms._

case class ItemForm(name: String, quantity: Long, weight: Long, size: Long, isFragile: Boolean, refrigerationLevel: String, requestId: Option[Long])

object ItemForm {
  val itemForm: Form[ItemForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "quantity" -> longNumber,
      "weight" -> longNumber,
      "size" -> longNumber,
      "is_fragile" -> boolean,
      "refrigeration_level" -> nonEmptyText,
      "request_id" -> optional(longNumber)
    )(ItemForm.apply)(ItemForm.unapply)
  }
}