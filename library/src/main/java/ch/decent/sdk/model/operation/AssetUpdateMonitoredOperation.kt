package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants.UIA_DESCRIPTION_MAX_CHARS
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName

/**
 * skip, cannot create
 * asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
 * therefore throws Missing Active Authority 1.2.0
 */
class AssetUpdateMonitoredOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("asset_to_update") val assetToUpdate: ChainObject,
    @SerializedName("new_description") val description: String,
    @SerializedName("new_feed_lifetime_sec") @UInt32 val newFeedLifetime: Long,
    @SerializedName("new_minimum_feeds") @UInt8 val newMinimumFeeds: Short,
    fee: Fee = Fee()
) : BaseOperation(OperationType.UPDATE_MONITORED_ASSET_OPERATION, fee) {

  init {
    require(issuer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(assetToUpdate.objectType == ObjectType.ASSET_OBJECT) { "not a valid asset object id" }
    require(description.length <= UIA_DESCRIPTION_MAX_CHARS) { "description cannot be longer then $UIA_DESCRIPTION_MAX_CHARS chars" }
  }

  override fun toString(): String {
    return "AssetUpdateMonitoredOperation(issuer=$issuer, assetToUpdate=$assetToUpdate, " +
        "description='$description', newFeedLifetime=$newFeedLifetime, newMinimumFeeds=$newMinimumFeeds)"
  }

}
