package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Miner
import ch.decent.sdk.net.model.ApiGroup

internal class GetMinerByAccount(
    account: AccountObjectId
) : BaseRequest<Miner>(
    ApiGroup.DATABASE,
    "get_miner_by_account",
    Miner::class.java,
    listOf(account)
)
