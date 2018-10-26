package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.net.serialization.VoteId
import io.reactivex.Single
import java.math.BigInteger

class MiningApi internal constructor(api: DCoreApi) : BaseApi(api) {


  fun getNewAssetPerBlock(): Single<BigInteger> = GetNewAssetPerBlock.toRequest()

  fun getAssetPerBlock(blockNum: Long): Single<BigInteger> = GetAssetPerBlock(blockNum).toRequest()

  /**
   * Returns list of miners by their Ids
   *
   * @param minerIds miner ids
   *
   * @return a list of miners
   */
  fun getMiners(minerIds: List<ChainObject>): Single<List<Miner>> = GetMiners(minerIds).toRequest()

  fun getMinerByAccount(account: ChainObject): Single<Miner> = GetMinerByAccount(account).toRequest()

  /**
   * lookup names and IDs for registered miners
   *
   * @param lowerBound lower bound of the first name
   * @param limit max 1000
   *
   * @return list of found miner ids
   */
  @JvmOverloads
  fun lookupMiners(lowerBound: String = "", limit: Int = 1000): Single<List<MinerId>> = LookupMinerAccounts(lowerBound, limit).toRequest()

  fun getMinerCount(): Single<Long> = GetMinerCount.toRequest()

  // todo model
  fun getFeedsByMiner(account: ChainObject, count: Long = 100) = GetFeedsByMiner(account, count).toRequest()

  fun lookupVoteIds(voteIds: List<VoteId>): Single<List<Miner>> = LookupVoteIds(voteIds).toRequest()

  fun getActualVotes(): Single<List<MinerVotes>> = GetActualVotes.toRequest()

  fun searchMinerVoting(
      searchTerm: String,
      order: SearchMinerVotingOrder = SearchMinerVotingOrder.NAME_DESC,
      id: ChainObject? = null,
      accountName: String? = null,
      onlyMyVotes: Boolean = false,
      limit: Int = 1000
  ) = SearchMinerVoting(accountName, searchTerm, onlyMyVotes, order, id, limit).toRequest()

  /**
   * Returns map of the first 1000 miners by their name to miner account
   *
   * @return a map of miner name to miner account
   */
  fun getMiners(): Single<Map<String, Miner>> =
      lookupMiners().flatMap { ids -> getMiners(ids.map { it.id }).map { ids.map { it.name }.zip(it).toMap() } }

}