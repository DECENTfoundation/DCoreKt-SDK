package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.net.model.ApiGroup

internal class RestoreEncryptionKey(
    elGamalPrivate: PubKey,
    purchaseId: ChainObject
) : BaseRequest<String>(
    ApiGroup.DATABASE,
    "restore_encryption_key",
    String::class.java,
    listOf(elGamalPrivate, purchaseId)
) {

  init {
    require(purchaseId.objectType == ObjectType.PURCHASE_OBJECT) { "not a valid purchase object id" }
  }
}
