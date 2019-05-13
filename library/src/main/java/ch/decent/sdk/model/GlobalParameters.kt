package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName

data class GlobalParameters(
    @SerializedName("current_fees") val fees: FeeSchedule,
    @SerializedName("block_interval") @UInt8 val blockInterval: Short,
    @SerializedName("maintenance_interval") @UInt32 val maintenanceInterval: Long,
    @SerializedName("maintenance_skip_slots") @UInt8 val maintenanceSkipSlots: Short,
    @SerializedName("miner_proposal_review_period") @UInt32 val minerProposalReviewPeriod: Long,
    @SerializedName("maximum_transaction_size") @UInt32 val maximumTransactionSize: Long,
    @SerializedName("maximum_block_size") @UInt32 val maximumBlockSize: Long,
    @SerializedName("maximum_time_until_expiration") @UInt32 val maximumTimeUntilExpiration: Long,
    @SerializedName("maximum_proposal_lifetime") @UInt32 val maximumProposalLifetime: Long,
    @SerializedName("maximum_asset_feed_publishers") @UInt8 val maximumAssetFeedPublishers: Short,
    @SerializedName("maximum_miner_count") @UInt16 val maximumMinerCount: Int,
    @SerializedName("maximum_authority_membership") @UInt16 val maximumAuthorityMembership: Int,
    @SerializedName("cashback_vesting_period_seconds") @UInt32 val cashbackVestingPeriodSeconds: Long,
    @SerializedName("cashback_vesting_threshold") @Int64 val cashbackVestingThreshold: Long,
    @SerializedName("max_predicate_opcode") @UInt16 val maxPredicateOpcode: Int,
    @SerializedName("max_authority_depth") @UInt8 val maxAuthorityDepth: Short,
    @SerializedName("extensions") val extensions: List<Any>
)
