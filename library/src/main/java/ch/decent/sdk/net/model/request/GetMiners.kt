package ch.decent.sdk.net.model.request


import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerObjectId
import com.google.gson.reflect.TypeToken

internal class GetMiners(
    contentIds: List<MinerObjectId>
) : GetObjects<List<Miner>>(
    contentIds.map { it },
    TypeToken.getParameterized(List::class.java, Miner::class.java).type
)
