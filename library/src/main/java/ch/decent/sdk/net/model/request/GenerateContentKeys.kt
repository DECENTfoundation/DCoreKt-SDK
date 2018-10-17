package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ContentKeys
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup

internal class GenerateContentKeys(
    seeders: List<ChainObject>
) : BaseRequest<ContentKeys>(
    ApiGroup.DATABASE,
    "generate_content_keys",
    ContentKeys::class.java,
    listOf(seeders)
) {

  init {
    require(seeders.all { it.objectType == ObjectType.ACCOUNT_OBJECT }) { "not a valid account object id" }
  }
}