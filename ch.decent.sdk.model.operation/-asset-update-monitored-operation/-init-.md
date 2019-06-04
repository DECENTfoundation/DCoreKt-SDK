[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateMonitoredOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetUpdateMonitoredOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, newFeedLifetime: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, newMinimumFeeds: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

skip, cannot create
asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
therefore throws Missing Active Authority 1.2.0

