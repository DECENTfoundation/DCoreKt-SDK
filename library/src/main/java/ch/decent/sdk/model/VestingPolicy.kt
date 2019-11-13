package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt128
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class LinearVestingPolicyCreate(
    @SerializedName("begin_timestamp") val start: LocalDateTime,
    @SerializedName("vesting_cliff_seconds") @UInt32 val cliffSeconds: Long,
    @SerializedName("vesting_duration_seconds") @UInt32 val durationSeconds: Long
) {
  constructor(start: LocalDateTime, cliff: Duration, duration: Duration) : this(start, cliff.seconds, duration.seconds)
}

data class LinearVestingPolicy(
    @SerializedName("begin_timestamp") val start: LocalDateTime,
    @SerializedName("vesting_cliff_seconds") @UInt32 val cliffSeconds: Long,
    @SerializedName("vesting_duration_seconds") @UInt32 val durationSeconds: Long,
    @SerializedName("begin_balance") @Int64 val balance: Long
)

data class CddVestingPolicyCreate(
    @SerializedName("start_claim") val start: LocalDateTime,
    @SerializedName("vesting_duration_seconds") @UInt32 val durationSeconds: Long
) {
  constructor(start: LocalDateTime, duration: Duration) : this(start, duration.seconds)
}

data class CddVestingPolicy(
    @SerializedName("vesting_seconds") @UInt32 val vestingSeconds: Long,
    @SerializedName("coin_seconds_earned") @UInt128 val coinSecondsEarned: BigInteger,
    @SerializedName("start_claim") val start: LocalDateTime,
    @SerializedName("coin_seconds_earned_last_update") val lastUpdate: LocalDateTime
)
