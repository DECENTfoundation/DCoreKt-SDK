package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Miner
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.net.serialization.VoteId
import com.google.gson.reflect.TypeToken

internal class LookupVoteIds(
    voteIds: List<VoteId>
) : BaseRequest<List<Miner>>(
    ApiGroup.DATABASE,
    "lookup_vote_ids",
    TypeToken.getParameterized(List::class.java, Miner::class.java).type,
    listOf(voteIds)
)