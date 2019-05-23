package ch.decent.sdk.model.operation

import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.OperationType

class EmptyOperation(type: OperationType) : BaseOperation(type, Fee()) {

  override fun toString(): String = type.toString()
}
