[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetIssueOperation](./index.md)

# AssetIssueOperation

`class AssetIssueOperation : `[`BaseOperation`](../-base-operation/index.md)

Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached.

### Parameters

`issuer` - asset issuer account id

`assetToIssue` - asset amount to issue

`issueToAccount` - account id receiving the created funds

`memo` - optional memo for receiver

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetIssueOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToIssue: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, issueToAccount: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached. |

### Properties

| [assetToIssue](asset-to-issue.md) | `val assetToIssue: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>asset amount to issue |
| [issueToAccount](issue-to-account.md) | `val issueToAccount: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account id receiving the created funds |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>asset issuer account id |
| [memo](memo.md) | `val memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`?`<br>optional memo for receiver |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

