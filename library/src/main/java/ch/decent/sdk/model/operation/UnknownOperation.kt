package ch.decent.sdk.model.operation

import ch.decent.sdk.model.Fee

data class UnknownOperation(val id: Int) : BaseOperation(OperationType.UNKNOWN_OPERATION, Fee()) {
  override fun toString(): String {
    return "UnknownOperation(id=$id)"
  }
}
