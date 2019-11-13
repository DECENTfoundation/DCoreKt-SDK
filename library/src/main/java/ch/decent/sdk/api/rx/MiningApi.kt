@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerId
import ch.decent.sdk.model.MinerObjectId
import ch.decent.sdk.model.MinerVotes
import ch.decent.sdk.model.MinerVotingInfo
import ch.decent.sdk.model.SearchMinerVotingOrder
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.VoteId
import ch.decent.sdk.model.operation.AccountUpdateOperation
import ch.decent.sdk.model.operation.MinerCreateOperation
import ch.decent.sdk.model.operation.MinerUpdateOperation
import ch.decent.sdk.net.model.request.GetActualVotes
import ch.decent.sdk.net.model.request.GetAssetPerBlock
import ch.decent.sdk.net.model.request.GetFeedsByMiner
import ch.decent.sdk.net.model.request.GetMinerByAccount
import ch.decent.sdk.net.model.request.GetMinerCount
import ch.decent.sdk.net.model.request.GetMiners
import ch.decent.sdk.net.model.request.GetNewAssetPerBlock
import ch.decent.sdk.net.model.request.LookupMinerAccounts
import ch.decent.sdk.net.model.request.LookupVoteIds
import ch.decent.sdk.net.model.request.SearchMinerVoting
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
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
  @JvmOverloads
  fun getFeedsByMiner(account: AccountObjectId, count: Int = REQ_LIMIT_MAX): Single<List<Any>> = GetFeedsByMiner(account, count).toRequest()

  /**
   * Get the miner owned by a given account.
   *
   * @param account the account object id, 1.2.*, whose miner should be retrieved
   *
   * @return the miner object, or [ObjectNotFoundException] if the account does not have a miner
   */
  fun getMinerByAccount(account: AccountObjectId): Single<Miner> = GetMinerByAccount(account).toRequest()

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
  fun getMiners(minerIds: List<MinerObjectId>): Single<List<Miner>> = GetMiners(minerIds).toRequest()

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
  fun listMinersRelative(lowerBound: String = "", limit: Int = REQ_LIMIT_MAX_1K): Single<List<MinerId>> = LookupMinerAccounts(lowerBound, limit).toRequest()

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
  @JvmOverloads
  fun findAllVotingInfo(
      searchTerm: String,
      order: SearchMinerVotingOrder = SearchMinerVotingOrder.NAME_DESC,
      id: MinerObjectId? = null,
      accountName: String? = null,
      onlyMyVotes: Boolean = false,
      limit: Int = REQ_LIMIT_MAX_1K
  ): Single<List<MinerVotingInfo>> = SearchMinerVoting(accountName, searchTerm, onlyMyVotes, order, id, limit).toRequest()

  /**
   * Create vote for miner operation. For more options see [AccountUpdateOperation] constructor.
   *
   * @param accountId account object id, 1.2.*
   * @param minerIds list of miner account ids
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return an account update operation
   */
  @JvmOverloads
  fun createVoteOperation(
      accountId: AccountObjectId,
      minerIds: List<MinerObjectId>,
      fee: Fee = Fee()
  ): Single<AccountUpdateOperation> =
      getMiners(minerIds).flatMap { miners ->
        api.accountApi.get(accountId).map { AccountUpdateOperation(it, miners.asSequence().map { VoteId.parse(it.voteId) }.toSet(), fee) }
      }

  /**
   * Vote for miner.
   *
   * @param credentials account credentials
   * @param minerIds list of miner account ids
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun vote(
      credentials: Credentials,
      minerIds: List<MinerObjectId>,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createVoteOperation(credentials.account, minerIds, fee).broadcast(credentials)

  /**
   * Create miner create operation. Account primary public key is used as miner's public key. For more options see [MinerCreateOperation] constructor.
   *
   * @param accountId account object id, 1.2.*. The account which owns the miner. This account pays the fee for this operation.
   * @param url url pointing to miner info web page
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return an account update operation
   */
  @JvmOverloads
  fun createMinerCreateOperation(
      accountId: AccountObjectId,
      url: String,
      fee: Fee = Fee()
  ): Single<MinerCreateOperation> = api.accountApi.get(accountId).map { MinerCreateOperation(accountId, url, it.primaryAddress, fee) }

  /**
   * Create miner. Account primary public key is used as miner's signing key.
   *
   * @param credentials account credentials. The account which owns the miner. This account pays the fee for this operation.
   * @param url url pointing to miner info web page
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun create(
      credentials: Credentials,
      url: String,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createMinerCreateOperation(credentials.account, url, fee).broadcast(credentials)

  /**
   * Create miner update operation. Fills model with actual account values. For more options see [MinerUpdateOperation] constructor.
   *
   * @param accountId account object id, 1.2.*. The account which owns the miner. This account pays the fee for this operation.
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return an account update operation
   */
  @JvmOverloads
  fun createMinerUpdateOperation(
      accountId: AccountObjectId,
      fee: Fee = Fee()
  ): Single<MinerUpdateOperation> = getMinerByAccount(accountId).map {
    MinerUpdateOperation(it.id, it.minerAccount, it.url, it.signingKey, fee)
  }

  /**
   * Update accounts's miner.
   *
   * @param credentials account credentials. The account which owns the miner. This account pays the fee for this operation.
   * @param url an optional new url pointing to miner info web page
   * @param signingKey an optional new public key address used to sign blocks by miner
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return an account update operation
   */
  @JvmOverloads
  fun update(
      credentials: Credentials,
      url: String? = null,
      signingKey: Address? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createMinerUpdateOperation(credentials.account, fee).map {
    it.apply {
      this.url = url
      this.signingKey = signingKey
    }
  }.broadcast(credentials)

}
