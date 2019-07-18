package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Nft
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftsBySymbol(
    symbols: List<String>
) : BaseRequest<List<Nft>>(
    ApiGroup.DATABASE,
    "get_non_fungible_tokens_by_symbols",
    TypeToken.getParameterized(List::class.java, Nft::class.java).type,
    listOf(symbols)
)
