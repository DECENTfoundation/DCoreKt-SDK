package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.net.serialization.VoteId
import io.reactivex.Single
import java.math.BigInteger

class MiningApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get the number of votes each miner actually has.
   *
   * @return a list mapping account names to the number of votes
   */
  fun getActualVotes(): Single<List<MinerVotes>> = GetActualVotes.toRequest()

  /**
   * Returns a reward for a miner from a specified block.
   *
   * @param blockNum block number
   *
   * @return amount of generated DCT
   */
  fun getAssetPerBlock(blockNum: Long): Single<BigInteger> = GetAssetPerBlock(blockNum).toRequest()

  /**
   * Get a list of published price feeds by a miner.
   *
   * @param account account object id, 1.2.*
   * @param count maximum number of price feeds to fetch (must not exceed 100)
   *
   * @return a list of price feeds published by the miner
   *
   */
  // todo model
  fun getFeedsByMiner(account: ChainObject, count: Int = 100) = GetFeedsByMiner(account, count).toRequest()

  /**
   * Get the miner owned by a given account.
   *
   * @param account the account object id, 1.2.*, whose miner should be retrieved
   *
   * @return the miner object, or [ObjectNotFoundException] if the account does not have a miner
   */
  fun getMinerByAccount(account: ChainObject): Single<Miner> = GetMinerByAccount(account).toRequest()

  /**
   * Get the total number of miners registered in DCore.
   *
   * @return number of miners
   */
  fun getMinerCount(): Single<Long> = GetMinerCount.toRequest()

  /**
   * Returns list of miners by their Ids
   *
   * @param minerIds miner ids
   *
   * @return a list of miners
   */
  fun getMiners(minerIds: List<ChainObject>): Single<List<Miner>> = GetMiners(minerIds).toRequest()

  /**
   * Returns map of the first 1000 miners by their name to miner account
   *
   * @return a map of miner name to miner account
   */
  fun getMiners(): Single<Map<String, Miner>> =
      listMinersRelative().flatMap { ids -> getMiners(ids.map { it.id }).map { ids.map { it.name }.zip(it).toMap() } }

  /**
   * Returns a reward for a miner from the most recent block.
   *
   * @return amount of newly generated DCT
   */
  fun getNewAssetPerBlock(): Single<BigInteger> = GetNewAssetPerBlock.toRequest()

  /**
   * lookup names and IDs for registered miners
   *
   * @param lowerBound lower bound of the first name
   * @param limit max 1000
   *
   * @return list of found miner ids
   */
  @JvmOverloads
  fun listMinersRelative(lowerBound: String = "", limit: Int = 1000): Single<List<MinerId>> = LookupMinerAccounts(lowerBound, limit).toRequest()

  /**
   * Given a set of votes, return the objects they are voting for.
   * The results will be in the same order as the votes. null will be returned for any vote ids that are not found.
   *
   * @param voteIds set of votes
   *
   * @return a list of miners
   */
  fun findVotedMiners(voteIds: List<VoteId>): Single<List<Miner>> = LookupVoteIds(voteIds).toRequest()

  /**
   * Get miner voting info list by account that match search term.
   *
   * @param searchTerm miner name
   * @param order available options are defined in [SearchMinerVotingOrder]
   * @param id the object id of the miner to start searching from, 1.4.* or null when start from beginning
   * @param accountName account name or null when searching without account
   * @param onlyMyVotes when true it selects only votes given by account
   * @param limit maximum number of miners info to fetch (must not exceed 1000)
   *
   * @return a list of miner voting info
   */
  fun findAllVotingInfo(
      searchTerm: String,
      order: SearchMinerVotingOrder = SearchMinerVotingOrder.NAME_DESC,
      id: ChainObject? = null,
      accountName: String? = null,
      onlyMyVotes: Boolean = false,
      limit: Int = 1000
  ): Single<List<MinerVotingInfo>> = SearchMinerVoting(accountName, searchTerm, onlyMyVotes, order, id, limit).toRequest()

  /**
   * Create vote for miner operation.
   *
   * @param accountId account object id, 1.2.*
   * @param minerIds list of miner account ids
   *
   * @return a transaction confirmation
   */
  fun createVoteOperation(
      accountId: ChainObject,
      minerIds: List<ChainObject>
  ): Single<AccountUpdateOperation> =
      getMiners(minerIds).flatMap { miners ->
        api.accountApi.get(accountId).map { AccountUpdateOperation(it, miners.asSequence().map { it.voteId }.toSet()) }
      }

}