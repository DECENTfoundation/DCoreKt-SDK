package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.bytes
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.VoteId
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("memo_key") val memoKey: Address,
    @SerializedName("voting_account") val votingAccount: ChainObject,
    @SerializedName("num_miner") val numMiner: Short,
    @SerializedName("votes") val votes: Set<VoteId>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("allow_subscription") val allowSubscription: Boolean,
    @SerializedName("price_per_subscribe") val pricePerSubscribe: AssetAmount,
    @SerializedName("subscription_period") val subscriptionPeriod: Int
) : ByteSerializable {

  constructor(public: Address) : this(
      public,
      ChainObject.parse("1.2.3"),
      0,
      emptySet(),
      emptyList(),
      false,
      AssetAmount(0),
      0
  )

  override val bytes: ByteArray
    get() = Bytes.concat(
        memoKey.bytes(),
        votingAccount.bytes,
        numMiner.bytes(),
        votes.bytes(),
        byteArrayOf(0),
        allowSubscription.bytes(),
        pricePerSubscribe.bytes,
        subscriptionPeriod.bytes()
    )
}
