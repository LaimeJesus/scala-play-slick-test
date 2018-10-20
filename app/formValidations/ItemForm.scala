package formValidations

import play.api.data.Form
import play.api.data.Forms._

case class ItemForm(name: String, weight: Long, size: Long)

object ItemForm {
  val itemForm: Form[ItemForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "weight" -> longNumber,
      "size" -> longNumber
    )(ItemForm.apply)(ItemForm.unapply)
  }
}