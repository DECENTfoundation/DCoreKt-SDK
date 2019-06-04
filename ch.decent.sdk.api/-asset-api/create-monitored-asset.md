[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createMonitoredAsset](./create-monitored-asset.md)

# createMonitoredAsset

`@JvmOverloads fun createMonitoredAsset(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, options: `[`MonitoredAssetOptions`](../../ch.decent.sdk.model/-monitored-asset-options/index.md)` = MonitoredAssetOptions(), fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

cannot create
asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
therefore throws Missing Active Authority 1.2.0

