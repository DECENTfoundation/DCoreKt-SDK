package ch.decent.sdk.net.model.request


import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.ObjectType
import com.google.gson.reflect.TypeToken

class GetMiners(
    contentIds: Set<ChainObject>
) : GetObjects<List<Miner>>(
    contentIds.map { it },
    TypeToken.getParameterized(List::class.java, Miner::class.java).type
) {

  init {
    check(contentIds.all { it.objectType == ObjectType.MINER_OBJECT })
  }
}