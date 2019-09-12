package ch.decent.sdk.net.model.request


import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerObjectId
import com.google.gson.reflect.TypeToken

internal class GetMiners(
    ids: List<MinerObjectId>
) : GetObjects<List<Miner>>(
    ids.map { it },
    TypeToken.getParameterized(List::class.java, Miner::class.java).type
)
