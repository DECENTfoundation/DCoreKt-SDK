package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class DynamicGlobalProps(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("head_block_number") val headBlockNumber: Long,
    @SerializedName("head_block_id") val headBlockId: String,
    @SerializedName("time") val time: LocalDateTime,
    @SerializedName("current_miner") val currentMiner: ChainObject,
    @SerializedName("next_maintenance_time") val nextMaintenanceTime: LocalDateTime,
    @SerializedName("last_budget_time") val lastBudgetTime: LocalDateTime,
    @SerializedName("unspent_fee_budget") val unspentFeeBudget: Long,
    @SerializedName("mined_rewards") val minedRewards: Long,
    @SerializedName("miner_budget_from_fees") val minerBudgetFromFees: Long,
    @SerializedName("miner_budget_from_rewards") val minerBudgetFromRewards: Long,
    @SerializedName("accounts_registered_this_interval") val accountsRegisteredThisInterval: Long,
    @SerializedName("recently_missed_count") val recentlyMissedCount: Long,
    @SerializedName("current_aslot") val currentAslot: Long,
    @SerializedName("recent_slots_filled") val recentSlotsFilled: String,
    @SerializedName("dynamic_flags") val dynamicFlags: Int,
    @SerializedName("last_irreversible_block_num") val lastIrreversibleBlockNum: Long
)
