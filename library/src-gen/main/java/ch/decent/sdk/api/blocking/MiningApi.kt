@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.MinerObjectId
import ch.decent.sdk.model.SearchMinerVotingOrder
import ch.decent.sdk.model.VoteId
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

class MiningApi internal constructor(
  private val api: ch.decent.sdk.api.MiningApi
) {
  fun getActualVotes() = api.getActualVotes().blockingGet()
  fun getAssetPerBlock(blockNum: Long) = api.getAssetPerBlock(blockNum).blockingGet()
  fun getFeedsByMiner(account: AccountObjectId, count: Int = REQ_LIMIT_MAX) =
      api.getFeedsByMiner(account, count).blockingGet()
  fun getMinerByAccount(account: AccountObjectId) = api.getMinerByAccount(account).blockingGet()
  fun getMinerCount() = api.getMinerCount().blockingGet()
  fun getMiners(minerIds: List<MinerObjectId>) = api.getMiners(minerIds).blockingGet()
  fun getMiners() = api.getMiners().blockingGet()
  fun getNewAssetPerBlock() = api.getNewAssetPerBlock().blockingGet()
  fun listMinersRelative(lowerBound: String = "", limit: Int = REQ_LIMIT_MAX_1K) =
      api.listMinersRelative(lowerBound, limit).blockingGet()
  fun findVotedMiners(voteIds: List<VoteId>) = api.findVotedMiners(voteIds).blockingGet()
  fun findAllVotingInfo(
    searchTerm: String,
    order: SearchMinerVotingOrder = SearchMinerVotingOrder.NAME_DESC,
    id: MinerObjectId? = null,
    accountName: String? = null,
    onlyMyVotes: Boolean = false,
    limit: Int = REQ_LIMIT_MAX_1K
  ) = api.findAllVotingInfo(searchTerm, order, id, accountName, onlyMyVotes, limit).blockingGet()
  fun createVoteOperation(
    accountId: AccountObjectId,
    minerIds: List<MinerObjectId>,
    fee: Fee = Fee()
  ) = api.createVoteOperation(accountId, minerIds, fee).blockingGet()
  fun vote(
    credentials: Credentials,
    minerIds: List<MinerObjectId>,
    fee: Fee = Fee()
  ) = api.vote(credentials, minerIds, fee).blockingGet()}
