[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createAssetCreateOperation](./create-asset-create-operation.md)

# createAssetCreateOperation

`@JvmOverloads fun createAssetCreateOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, options: `[`AssetOptions`](../../ch.decent.sdk.model/-asset-options/index.md)`, monitoredOptions: `[`MonitoredAssetOptions`](../../ch.decent.sdk.model/-monitored-asset-options/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetCreateOperation`](../../ch.decent.sdk.model.operation/-asset-create-operation/index.md)`>`

Create asset create operation.

### Parameters

`issuer` - account id issuing the asset

`symbol` - the string symbol, 3-16 uppercase chars

`precision` - base unit precision, decimal places used in string representation

`description` - optional description

`options` - asset options

`monitoredOptions` - options for monitored asset

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough