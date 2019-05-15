package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.utils.hex

/**
 * Send message operation.
 *
 * @param messagePayloadJson message payload
 * @param payer account id to pay for the operation
 * @param requiredAuths account ids required to authorize this operation
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class SendMessageOperation @JvmOverloads constructor(
    messagePayloadJson: String,
    payer: ChainObject,
    requiredAuths: List<ChainObject> = listOf(payer),
    fee: Fee = Fee()
) : CustomOperation(CustomOperationType.MESSAGING, payer, requiredAuths, messagePayloadJson.toByteArray().hex(), fee)
