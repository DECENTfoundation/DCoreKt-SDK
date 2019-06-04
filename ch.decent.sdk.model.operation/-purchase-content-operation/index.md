[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [PurchaseContentOperation](./index.md)

# PurchaseContentOperation

`class PurchaseContentOperation : `[`BaseOperation`](../-base-operation/index.md)

Request to purchase content operation constructor

### Parameters

`uri` - uri of the content

`consumer` - chain object id of the buyer's account

`price` - price of content, can be equal to or higher then specified in content

`publicElGamal` - public el gamal key

`regionCode` - region code of the consumer

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `PurchaseContentOperation(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`Content`](../../ch.decent.sdk.model/-content/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)`)``PurchaseContentOperation(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, price: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, publicElGamal: `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md)`, regionCode: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = Regions.ALL.id, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Request to purchase content operation constructor |

### Properties

| [consumer](consumer.md) | `val consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>chain object id of the buyer's account |
| [price](price.md) | `val price: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>price of content, can be equal to or higher then specified in content |
| [publicElGamal](public-el-gamal.md) | `val publicElGamal: `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md)<br>public el gamal key |
| [regionCode](region-code.md) | `val regionCode: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>region code of the consumer |
| [uri](uri.md) | `val uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>uri of the content |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

