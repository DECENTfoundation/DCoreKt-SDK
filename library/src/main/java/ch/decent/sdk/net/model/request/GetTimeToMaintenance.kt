package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.MinerRewardInput
import ch.decent.sdk.net.model.ApiGroup
import org.threeten.bp.LocalDateTime

internal class GetTimeToMaintenance(time: LocalDateTime) : BaseRequest<MinerRewardInput>(
    ApiGroup.DATABASE,
    "get_time_to_maint_by_block_time",
    MinerRewardInput::class.java,
    listOf(time)
)