package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt128
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class DynamicGlobalProps(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("head_block_number") @UInt32 val headBlockNumber: Long,
    @SerializedName("head_block_id") val headBlockId: String,
    @SerializedName("time") val time: LocalDateTime,
    @SerializedName("current_miner") val currentMiner: ChainObject,
    @SerializedName("next_maintenance_time") val nextMaintenanceTime: LocalDateTime,
    @SerializedName("last_budget_time") val lastBudgetTime: LocalDateTime,
    @SerializedName("miner_budget_from_fees") @Int64 val minerBudgetFromFees: Long,
    @SerializedName("unspent_fee_budget") @Int64 val unspentFeeBudget: Long,
    @SerializedName("mined_rewards") @Int64 val minedRewards: Long,
    @SerializedName("miner_budget_from_rewards") @Int64 val minerBudgetFromRewards: Long,
    @SerializedName("accounts_registered_this_interval") @UInt32 val accountsRegisteredThisInterval: Long,
    @SerializedName("recently_missed_count") @Int64 val recentlyMissedCount: Long,
    @SerializedName("current_aslot") @UInt64 val currentAslot: BigInteger,
    @SerializedName("recent_slots_filled") @UInt128 val recentSlotsFilled: BigInteger,
    @SerializedName("dynamic_flags") @UInt32 val dynamicFlags: Long,
    @SerializedName("last_irreversible_block_num") @UInt32 val lastIrreversibleBlockNum: Long
)
