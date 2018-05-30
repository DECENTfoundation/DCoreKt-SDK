package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.unhex
import com.google.gson.reflect.TypeToken

internal class GetRecentTransactionById(
    id: String
) : BaseRequest<ProcessedTransaction>(
    ApiGroup.DATABASE,
    "get_recent_transaction_by_id",
    ProcessedTransaction::class.java,
    listOf(id)
) {

  init {
    check(id.unhex().size == 20 , { "invalid transaction id" })
  }
}