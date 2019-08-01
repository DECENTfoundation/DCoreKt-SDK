package ch.decent.sdk.model.operation

import ch.decent.sdk.api.rx.DCoreApi
import ch.decent.sdk.DCoreClient
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.MessagePayload
import ch.decent.sdk.model.OperationTypeFactory
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SendMessageOperation : CustomOperation {

  @Transient val message: MessagePayload

  /**
   * Send message operation.
   *
   * @param gson GSON object to serialize message payload, [DCoreApi.gson] or [DCoreClient.gsonBuilder] can be used
   * @param message message payload
   * @param payer account id to pay for the operation
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads constructor(
      gson: Gson,
      message: MessagePayload,
      payer: AccountObjectId,
      fee: Fee = Fee()
  ) : super(CustomOperationType.MESSAGING.ordinal, payer, listOf(payer), gson.toJson(message).toByteArray().hex(), fee) {
    this.message = message
  }

  internal constructor(gson: Gson, op: CustomOperation) : super(op.id, op.payer, op.requiredAuths, op.data) {
    this.fee = op.fee
    this.message = gson.getDelegateAdapter(OperationTypeFactory, TypeToken.get(MessagePayload::class.java)).fromJson(String(op.data.unhex()))
  }

  override fun toString(): String {
    return "SendMessageOperation(payer=$payer, requiredAuths=$requiredAuths, data=$data, id=$id, message=$message)"
  }

}
