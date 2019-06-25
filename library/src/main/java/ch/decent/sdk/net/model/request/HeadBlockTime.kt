package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import org.threeten.bp.LocalDateTime

internal object HeadBlockTime : BaseRequest<LocalDateTime>(
    ApiGroup.DATABASE,
    "head_block_time",
    LocalDateTime::class.java
)
