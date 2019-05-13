package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class GlobalParameters(
    @SerializedName("current_fees") val fees: FeeSchedule,
    @SerializedName("block_interval") val blockInterval: Short,
    @SerializedName("maintenance_interval") val maintenanceInterval: Long,
    @SerializedName("maintenance_skip_slots") val maintenanceSkipSlots: Short,
    @SerializedName("miner_proposal_review_period") val minerProposalReviewPeriod: Long,
    @SerializedName("maximum_transaction_size") val maximumTransactionSize: Long,
    @SerializedName("maximum_block_size") val maximumBlockSize: Long,
    @SerializedName("maximum_time_until_expiration") val maximumTimeUntilExpiration: Long,
    @SerializedName("maximum_proposal_lifetime") val maximumProposalLifetime: Long,
    @SerializedName("maximum_asset_feed_publishers") val maximumAssetFeedPublishers: Short,
    @SerializedName("maximum_miner_count") val maximumMinerCount: Int,
    @SerializedName("maximum_authority_membership") val maximumAuthorityMembership: Int,
    @SerializedName("cashback_vesting_period_seconds") val cashbackVestingPeriodSeconds: Long,
    @SerializedName("cashback_vesting_threshold") val cashbackVestingThreshold: BigInteger,
    @SerializedName("max_predicate_opcode") val maxPredicateOpcode: Int,
    @SerializedName("max_authority_depth") val maxAuthorityDepth: Short,
    @SerializedName("extensions") val extensions: List<Any>
)
