package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.net.model.ApiGroup

internal class RestoreEncryptionKey(
    elGamalPrivate: PubKey,
    purchaseId: PurchaseObjectId
) : BaseRequest<String>(
    ApiGroup.DATABASE,
    "restore_encryption_key",
    String::class.java,
    listOf(elGamalPrivate, purchaseId)
)
