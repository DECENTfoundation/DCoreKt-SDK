[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Fee](./index.md)

# Fee

`data class Fee`

Fee asset amount.

### Parameters

`assetId` - asset id of the fee, by default DCT

`amount` - fee amount, if null the fee amount will be fetched from DCore

### Constructors

| [&lt;init&gt;](-init-.md) | `Fee(assetId: `[`ChainObject`](../-chain-object/index.md)` = DCoreConstants.DCT_ASSET_ID, amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`? = null)`<br>Fee asset amount. |

### Properties

| [amount](amount.md) | `val amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?`<br>fee amount, if null the fee amount will be fetched from DCore |
| [assetId](asset-id.md) | `val assetId: `[`ChainObject`](../-chain-object/index.md)<br>asset id of the fee, by default DCT |

