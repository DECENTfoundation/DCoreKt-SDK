package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class AccountOptions(
    @SerializedName("memo_key") val memoKey: Address,
    @SerializedName("voting_account") val votingAccount: AccountObjectId,
    @SerializedName("num_miner") @UInt16 val numMiner: Int,
    @SerializedName("votes") val votes: Set<VoteId>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("allow_subscription") val allowSubscription: Boolean,
    @SerializedName("price_per_subscribe") val pricePerSubscribe: AssetAmount,
    @SerializedName("subscription_period") @UInt32 val subscriptionPeriod: Long
) {

  constructor(public: Address) : this(
      public,
      DCoreConstants.PROXY_TO_SELF,
      0,
      emptySet(),
      emptyList(),
      false,
      AssetAmount(0),
      0
  )
}
