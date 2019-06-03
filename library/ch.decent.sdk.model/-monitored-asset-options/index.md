[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [MonitoredAssetOptions](./index.md)

# MonitoredAssetOptions

`data class MonitoredAssetOptions`

### Constructors

| [&lt;init&gt;](-init-.md) | `MonitoredAssetOptions(feeds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> = emptyList(), currentFeed: `[`PriceFeed`](../-price-feed/index.md)` = PriceFeed(ExchangeRate.EMPTY), currentFeedPublicationTime: LocalDateTime = LocalDateTime.now(), feedLifetimeSec: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = TimeUnit.DAYS.toSeconds(1), minimumFeeds: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html)` = 1)` |

### Properties

| [currentFeed](current-feed.md) | `val currentFeed: `[`PriceFeed`](../-price-feed/index.md) |
| [currentFeedPublicationTime](current-feed-publication-time.md) | `val currentFeedPublicationTime: LocalDateTime` |
| [feedLifetimeSec](feed-lifetime-sec.md) | `val feedLifetimeSec: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [feeds](feeds.md) | `val feeds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
| [minimumFeeds](minimum-feeds.md) | `val minimumFeeds: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html) |

