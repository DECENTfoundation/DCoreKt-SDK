package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object SetPendingTransactionCallback : BaseRequest<Unit>(
    ApiGroup.DATABASE,
    "set_pending_transaction_callback",
    Unit::class.java
), WithCallback
