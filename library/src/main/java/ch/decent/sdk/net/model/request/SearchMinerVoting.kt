package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class SearchMinerVoting(
    accountName: String? = null,
    searchTerm: String,
    onlyMyVotes: Boolean = false,
    order: SearchMinerVotingOrder = SearchMinerVotingOrder.NAME_DESC,
    id: ChainObject? = null,
    limit: Int = 1000
) : BaseRequest<List<MinerVotingInfo>>(
    ApiGroup.DATABASE,
    "search_miner_voting",
    TypeToken.getParameterized(List::class.java, MinerVotingInfo::class.java).type,
    listOf(accountName, searchTerm, onlyMyVotes, order, id, limit)
) {

  init {
    require(accountName?.let { Account.isValidName(it) } ?: true) { "not a valid account name" }
    require(id?.objectType?.equals(ObjectType.MINER_OBJECT) ?: true) { "not a valid miner object id" }
  }
}