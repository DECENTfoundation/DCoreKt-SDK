package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName

/**
 * Leave comment and rating operation constructor
 *
 * @param uri uri of the content
 * @param consumer chain object id of the buyer's account
 * @param rating 1-5 stars
 * @param comment max 100 chars
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class LeaveRatingAndCommentOperation @JvmOverloads constructor(
    @SerializedName("URI") val uri: String,
    @SerializedName("consumer") val consumer: ChainObject,
    @SerializedName("rating") @UInt64 val rating: Byte, // 1-5 stars
    @SerializedName("comment") val comment: String,
    fee: Fee = Fee()
) : BaseOperation(OperationType.LEAVE_RATING_AND_COMMENT_OPERATION, fee) {

  init {
    require(rating in DCoreConstants.RATING_ALLOWED) { "rating must be between 1-5" }
    require(comment.length <= DCoreConstants.COMMENT_MAX_CHARS) { "comment max length is ${DCoreConstants.COMMENT_MAX_CHARS} chars" }
  }

  override fun toString(): String {
    return "LeaveRatingAndCommentOperation(uri='$uri', consumer=$consumer, rating=$rating, comment='$comment')"
  }

}
