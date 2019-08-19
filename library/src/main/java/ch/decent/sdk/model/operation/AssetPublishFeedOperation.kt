package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.PriceFeed
import com.google.gson.annotations.SerializedName

/**
 * skip, cannot create monitored asset, also only miner account can publish feeds
 * asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
 * therefore throws Missing Active Authority 1.2.0
 */
class AssetPublishFeedOperation @JvmOverloads constructor(
    @SerializedName("publisher") val publisher: AccountObjectId,
    @SerializedName("asset_id") val asset: AssetObjectId,
    @SerializedName("feed") val feed: PriceFeed,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_PUBLISH_FEED_OPERATION, fee) {

  override fun toString(): String {
    return "AssetPublishFeedOperation(publisher=$publisher, asset=$asset, feed=$feed)"
  }

}
