package ch.decent.sdk.model.operation

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Content
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.utils.publicElGamal
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.regex.Pattern

/**
 * Request to purchase content operation constructor
 *
 * @param uri uri of the content
 * @param consumer chain object id of the buyer's account
 * @param price price of content, can be equal to or higher then specified in content
 * @param publicElGamal public el gamal key
 * @param regionCode region code of the consumer
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class PurchaseContentOperation @JvmOverloads constructor(
    @SerializedName("URI") val uri: String,
    @SerializedName("consumer") val consumer: ChainObject,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("pubKey") val publicElGamal: PubKey,
    @SerializedName("region_code_from") @UInt32 val regionCode: Long = Regions.ALL.id,
    fee: Fee = Fee()
) : BaseOperation(OperationType.REQUEST_TO_BUY_OPERATION, fee) {

  constructor(credentials: Credentials, content: Content) :
      this(content.uri, credentials.account, content.price(), if (URL(content.uri).protocol != "ipfs") PubKey() else credentials.keyPair.publicElGamal())

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(price.amount >= 0) { "amount must be >= 0" }
  }

  override fun toString(): String {
    return "PurchaseContentOperation(uri='$uri', consumer=$consumer, price=$price, publicElGamal=$publicElGamal, regionCode=$regionCode)"
  }

}
