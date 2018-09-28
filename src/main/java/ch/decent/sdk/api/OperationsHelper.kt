package ch.decent.sdk.api

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.*
import io.reactivex.Single

interface OperationsHelper {

  /**
   * create a transfer
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
  fun createTransfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransferOperation>

  /**
   * make a transfer
   *
   * @param credentials account credentials
   * @param to object id of the receiver, account id, content id, account name or public key
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET,
      expiration: Int = DCoreConstants.DEFAULT_EXPIRATION
  ): Single<TransactionConfirmation>

  /**
   * create a buy content operation
   *
   * @param credentials account credentials
   * @param contentId object id of the content, 2.13.*
   */
  fun createBuyContent(
      credentials: Credentials,
      contentId: ChainObject
  ): Single<BuyContentOperation>

  /**
   * create a buy content operation
   *
   * @param credentials account credentials
   * @param uri uri of the content
   */
  fun createBuyContent(
      credentials: Credentials,
      uri: String
  ): Single<BuyContentOperation>

  /**
   * vote for miner
   *
   * @param account account object id, 1.2.*
   * @param minerIds list of miner account ids
   *
   * @return a transaction confirmation
   */
  fun createVote(
      account: ChainObject,
      minerIds: Set<ChainObject>
  ): Single<AccountUpdateOperation>


}