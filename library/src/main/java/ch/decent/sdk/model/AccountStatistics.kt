package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AccountStatistics(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("most_recent_op") val mostRecentOp: ChainObject,
    @SerializedName("total_ops") val totalOps: Long,
    @SerializedName("total_core_in_orders") @Int64 val totalCoreInOrders: Long,
    @SerializedName("pending_fees") @Int64 val pendingFees: Long,
    @SerializedName("pending_vested_fees") @Int64 val pendingVestedFees: Long
)
