package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AccountStatistics(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("most_recent_op") val mostRecentOp: ChainObject,
    @SerializedName("total_ops") val totalOps: Long,
    @SerializedName("total_core_in_orders") val totalCoreInOrders: BigInteger,
    @SerializedName("pending_fees") val pendingFees: BigInteger,
    @SerializedName("pending_vested_fees") val pendingVestedFees: BigInteger
)
