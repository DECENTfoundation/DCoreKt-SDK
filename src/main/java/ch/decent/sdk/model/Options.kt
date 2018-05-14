package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.bytes
import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.utils.bytes
import ch.decent.sdk.utils.parseVoteId
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("memo_key") val memoKey: Address,
    @SerializedName("voting_account") val votingAccount: ChainObject,
    @SerializedName("num_miner") val numMiner: Short,
    @SerializedName("votes") val votes: Set<String>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("allow_subscriptions") val allowSubscriptions: Boolean,
    @SerializedName("price_per_subscribe") val pricePerSubscribe: AssetAmount,
    @SerializedName("subscription_period") val subscriptionPeriod: Int
) : ByteSerializable {

  override val bytes: ByteArray
    get() = Bytes.concat(
        memoKey.bytes(),
        votingAccount.bytes,
        numMiner.bytes(),
        ByteArray(1, { votes.size.toByte() }),
        *votes.map { it.parseVoteId() }.toTypedArray(),
        ByteArray(1, { 0 }),
        allowSubscriptions.bytes(),
        pricePerSubscribe.bytes,
        subscriptionPeriod.bytes()
    )
}