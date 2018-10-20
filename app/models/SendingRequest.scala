package models

case class SendingRequest(id: Long, origin: String, destination: String, items: List[Item], isFragile: Boolean, refrigerationLevel: String)
//owner: User, refrigerationLevel: RefrigerationLevel