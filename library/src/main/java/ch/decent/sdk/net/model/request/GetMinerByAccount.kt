package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Miner
import ch.decent.sdk.net.model.ApiGroup

internal class GetMinerByAccount(
    account: ChainObject
) : BaseRequest<Miner>(
    ApiGroup.DATABASE,
    "get_miner_by_account",
    Miner::class.java,
    listOf(account)
)