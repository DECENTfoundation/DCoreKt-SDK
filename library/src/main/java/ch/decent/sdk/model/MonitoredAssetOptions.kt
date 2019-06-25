package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit

data class MonitoredAssetOptions(
    @SerializedName("feeds") val feeds: List<Any> = emptyList(),
    @SerializedName("current_feed") val currentFeed: PriceFeed = PriceFeed(ExchangeRate.EMPTY),
    @SerializedName("current_feed_publication_time") val currentFeedPublicationTime: LocalDateTime = LocalDateTime.now(),
    @SerializedName("feed_lifetime_sec") @UInt32 val feedLifetimeSec: Long = TimeUnit.DAYS.toSeconds(1),
    @SerializedName("minimum_feeds") @UInt8 val minimumFeeds: Short = 1
)
