@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.NftDataType
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.NftCreateOperation
import io.reactivex.Single

class NftApi internal constructor(api: DCoreApi) : BaseApi(api) {

  fun createNftCreateOperation(
      symbol: String,
      options: NftOptions,
      definitions: List<NftDataType>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<NftCreateOperation> = Single.just(NftCreateOperation(symbol, options, definitions, transferable, fee))

  fun create(
      credentials: Credentials,
      symbol: String,
      maxSupply: Long,
      fixedMaxSupply: Boolean,
      description: String,
      definitions: List<NftDataType>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createNftCreateOperation(symbol, NftOptions(credentials.account, maxSupply, fixedMaxSupply, description), definitions, transferable, fee)
          .broadcast(credentials)

}
