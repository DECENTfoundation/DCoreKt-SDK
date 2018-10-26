package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.*
import io.reactivex.Single

class OperationsHelper internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Create a transfer.
   *
   * @param credentials account credentials
   * @param to object id of the receiver, account id, content id, account name or public key
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun createTransfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransferOperation> =
      api.accountApi.getAccount(to).map { receiver ->
        if (memo.isNullOrBlank() || !encrypted) {
          TransferOperation(credentials.account, receiver.id, amount, memo?.let { Memo(it) }, fee)
        } else {
          TransferOperation(credentials.account, receiver.id, amount, Memo(memo!!, credentials.keyPair, receiver.active.keyAuths.first().value), fee)
        }
      }

  /**
   * Create a transfer.
   *
   * @param credentials account credentials
   * @param to object id of the receiver, account id, content id, account name or public key
   * @param amount amount to send with asset type
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  fun createTransfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      fee: AssetAmount
  ): Single<TransferOperation> = createTransfer(credentials, to, amount, null, false, fee)

  /**
   * Make a transfer.
   *
   * @param credentials account credentials
   * @param to object id of the receiver, account id, content id, account name or public key
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransactionConfirmation> =
      createTransfer(credentials, to, amount, memo, encrypted, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Make a transfer.
   *
   * @param credentials account credentials
   * @param to object id of the receiver, account id, content id, account name or public key
   * @param amount amount to send with asset type
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransactionConfirmation> =
      createTransfer(credentials, to, amount, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Create a buy content operation.
   *
   * @param credentials account credentials
   * @param contentId object id of the content, 2.13.*
   */
  fun createBuyContent(
      credentials: Credentials,
      contentId: ChainObject
  ): Single<BuyContentOperation> =
      api.contentApi.getContent(contentId).map { BuyContentOperation(credentials, it) }


  /**
   * Create a buy content operation.
   *
   * @param credentials account credentials
   * @param uri uri of the content
   */
  fun createBuyContent(
      credentials: Credentials,
      uri: String
  ): Single<BuyContentOperation> =
      api.contentApi.getContent(uri).map { BuyContentOperation(credentials, it) }

  /**
   * Create vote for miner operation.
   *
   * @param accountId account object id, 1.2.*
   * @param minerIds list of miner account ids
   *
   * @return a transaction confirmation
   */
  fun createVote(
      accountId: ChainObject,
      minerIds: List<ChainObject>
  ): Single<AccountUpdateOperation> =
      api.miningApi.getMiners(minerIds).flatMap { miners ->
        api.accountApi.getAccount(accountId.objectId).map { AccountUpdateOperation(it, miners.asSequence().map { it.voteId }.toSet()) }
      }


}