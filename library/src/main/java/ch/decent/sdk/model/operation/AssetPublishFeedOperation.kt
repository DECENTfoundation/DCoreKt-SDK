package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.PriceFeed
import com.google.gson.annotations.SerializedName

/**
 * skip, cannot create monitored asset, also only miner account can publish feeds
 * asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
 * therefore throws Missing Active Authority 1.2.0
 */
class AssetPublishFeedOperation @JvmOverloads constructor(
    @SerializedName("publisher") val publisher: ChainObject,
    @SerializedName("asset_id") val asset: ChainObject,
    @SerializedName("feed") val feed: PriceFeed,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_PUBLISH_FEED_OPERATION, fee) {
  init {
    require(publisher.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(asset.objectType == ObjectType.ASSET_OBJECT) { "not a valid asset object id" }
  }

  override fun toString(): String {
    return "AssetPublishFeedOperation(publisher=$publisher, asset=$asset, feed=$feed)"
  }

}
