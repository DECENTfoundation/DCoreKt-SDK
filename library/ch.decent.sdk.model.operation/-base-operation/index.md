[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [BaseOperation](./index.md)

# BaseOperation

`abstract class BaseOperation`

### Constructors

| [&lt;init&gt;](-init-.md) | `BaseOperation(type: `[`OperationType`](../-operation-type/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)`)` |

### Properties

| [fee](fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [equals](equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| [AccountCreateOperation](../-account-create-operation/index.md) | `class AccountCreateOperation : `[`BaseOperation`](./index.md)<br>Request to create account operation constructor |
| [AccountUpdateOperation](../-account-update-operation/index.md) | `class AccountUpdateOperation : `[`BaseOperation`](./index.md)<br>Request to account update operation constructor |
| [AddOrUpdateContentOperation](../-add-or-update-content-operation/index.md) | `class AddOrUpdateContentOperation : `[`BaseOperation`](./index.md)<br>Request to submit content operation constructor |
| [AssetClaimFeesOperation](../-asset-claim-fees-operation/index.md) | `class AssetClaimFeesOperation : `[`BaseOperation`](./index.md)<br>Claim fees operation constructor. Claim funds from asset pool, only the asset issuer can clam. |
| [AssetCreateOperation](../-asset-create-operation/index.md) | `class AssetCreateOperation : `[`BaseOperation`](./index.md)<br>Create Asset operation constructor. |
| [AssetFundPoolsOperation](../-asset-fund-pools-operation/index.md) | `class AssetFundPoolsOperation : `[`BaseOperation`](./index.md)<br>Fund asset pool operation constructor. Any account can fund a pool. |
| [AssetIssueOperation](../-asset-issue-operation/index.md) | `class AssetIssueOperation : `[`BaseOperation`](./index.md)<br>Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached. |
| [AssetPublishFeedOperation](../-asset-publish-feed-operation/index.md) | `class AssetPublishFeedOperation : `[`BaseOperation`](./index.md)<br>skip, cannot create monitored asset, also only miner account can publish feeds asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |
| [AssetReserveOperation](../-asset-reserve-operation/index.md) | `class AssetReserveOperation : `[`BaseOperation`](./index.md)<br>Reserve funds operation constructor. Return issued funds to the issuer of the asset. |
| [AssetUpdateAdvancedOperation](../-asset-update-advanced-operation/index.md) | `class AssetUpdateAdvancedOperation : `[`BaseOperation`](./index.md)<br>Update advanced options for the asset. |
| [AssetUpdateMonitoredOperation](../-asset-update-monitored-operation/index.md) | `class AssetUpdateMonitoredOperation : `[`BaseOperation`](./index.md)<br>skip, cannot create asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |
| [AssetUpdateOperation](../-asset-update-operation/index.md) | `class AssetUpdateOperation : `[`BaseOperation`](./index.md)<br>Update asset operation constructor. |
| [CustomOperation](../-custom-operation/index.md) | `open class CustomOperation : `[`BaseOperation`](./index.md)<br>Custom operation |
| [EmptyOperation](../-empty-operation/index.md) | `class EmptyOperation : `[`BaseOperation`](./index.md) |
| [LeaveRatingAndCommentOperation](../-leave-rating-and-comment-operation/index.md) | `class LeaveRatingAndCommentOperation : `[`BaseOperation`](./index.md)<br>Leave comment and rating operation constructor |
| [PurchaseContentOperation](../-purchase-content-operation/index.md) | `class PurchaseContentOperation : `[`BaseOperation`](./index.md)<br>Request to purchase content operation constructor |
| [RemoveContentOperation](../-remove-content-operation/index.md) | `class RemoveContentOperation : `[`BaseOperation`](./index.md)<br>Remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database. |
| [TransferOperation](../-transfer-operation/index.md) | `class TransferOperation : `[`BaseOperation`](./index.md)<br>Transfer operation constructor |

