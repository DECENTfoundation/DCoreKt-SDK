@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.OperationType
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

class ValidationApi internal constructor(
  private val api: ch.decent.sdk.api.ValidationApi
) {
  fun getRequiredSignatures(transaction: Transaction, keys: List<Address>) =
      api.getRequiredSignatures(transaction, keys).blockingGet()
  fun getPotentialSignatures(transaction: Transaction) =
      api.getPotentialSignatures(transaction).blockingGet()
  fun verifyAuthority(transaction: Transaction) = api.verifyAuthority(transaction).blockingGet()
  fun verifyAccountAuthority(nameOrId: String, keys: List<Address>) =
      api.verifyAccountAuthority(nameOrId, keys).blockingGet()
  fun validateTransaction(transaction: Transaction) =
      api.validateTransaction(transaction).blockingGet()
  fun getFees(op: List<BaseOperation>, assetId: AssetObjectId = DCoreConstants.DCT_ASSET_ID) =
      api.getFees(op, assetId).blockingGet()
  fun getFee(op: BaseOperation, assetId: AssetObjectId = DCoreConstants.DCT_ASSET_ID) =
      api.getFee(op, assetId).blockingGet()
  fun getFeesForType(types: List<OperationType>, assetId: AssetObjectId =
      DCoreConstants.DCT_ASSET_ID) = api.getFeesForType(types, assetId).blockingGet()
  fun getFeeForType(type: OperationType, assetId: AssetObjectId = DCoreConstants.DCT_ASSET_ID) =
      api.getFeeForType(type, assetId).blockingGet()}
