package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.ContentKeys
import ch.decent.sdk.net.model.ApiGroup

internal class GenerateContentKeys(
    seeders: List<AccountObjectId>
) : BaseRequest<ContentKeys>(
    ApiGroup.DATABASE,
    "generate_content_keys",
    ContentKeys::class.java,
    listOf(seeders)
)
