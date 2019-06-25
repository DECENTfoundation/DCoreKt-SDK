package ch.decent.sdk.model.operation

import ch.decent.sdk.model.Fee

class EmptyOperation(type: OperationType) : BaseOperation(type, Fee()) {

  override fun toString(): String = type.toString()
}
