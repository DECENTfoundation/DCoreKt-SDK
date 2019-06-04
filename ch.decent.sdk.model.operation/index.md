[library](../index.md) / [ch.decent.sdk.model.operation](./index.md)

## Package ch.decent.sdk.model.operation

### Types

| [AccountCreateOperation](-account-create-operation/index.md) | `class AccountCreateOperation : `[`BaseOperation`](-base-operation/index.md)<br>Request to create account operation constructor |
| [AccountUpdateOperation](-account-update-operation/index.md) | `class AccountUpdateOperation : `[`BaseOperation`](-base-operation/index.md)<br>Request to account update operation constructor |
| [AddOrUpdateContentOperation](-add-or-update-content-operation/index.md) | `class AddOrUpdateContentOperation : `[`BaseOperation`](-base-operation/index.md)<br>Request to submit content operation constructor |
| [AssetClaimFeesOperation](-asset-claim-fees-operation/index.md) | `class AssetClaimFeesOperation : `[`BaseOperation`](-base-operation/index.md)<br>Claim fees operation constructor. Claim funds from asset pool, only the asset issuer can clam. |
| [AssetCreateOperation](-asset-create-operation/index.md) | `class AssetCreateOperation : `[`BaseOperation`](-base-operation/index.md)<br>Create Asset operation constructor. |
| [AssetFundPoolsOperation](-asset-fund-pools-operation/index.md) | `class AssetFundPoolsOperation : `[`BaseOperation`](-base-operation/index.md)<br>Fund asset pool operation constructor. Any account can fund a pool. |
| [AssetIssueOperation](-asset-issue-operation/index.md) | `class AssetIssueOperation : `[`BaseOperation`](-base-operation/index.md)<br>Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached. |
| [AssetPublishFeedOperation](-asset-publish-feed-operation/index.md) | `class AssetPublishFeedOperation : `[`BaseOperation`](-base-operation/index.md)<br>skip, cannot create monitored asset, also only miner account can publish feeds asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |
| [AssetReserveOperation](-asset-reserve-operation/index.md) | `class AssetReserveOperation : `[`BaseOperation`](-base-operation/index.md)<br>Reserve funds operation constructor. Return issued funds to the issuer of the asset. |
| [AssetUpdateAdvancedOperation](-asset-update-advanced-operation/index.md) | `class AssetUpdateAdvancedOperation : `[`BaseOperation`](-base-operation/index.md)<br>Update advanced options for the asset. |
| [AssetUpdateMonitoredOperation](-asset-update-monitored-operation/index.md) | `class AssetUpdateMonitoredOperation : `[`BaseOperation`](-base-operation/index.md)<br>skip, cannot create asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |
| [AssetUpdateOperation](-asset-update-operation/index.md) | `class AssetUpdateOperation : `[`BaseOperation`](-base-operation/index.md)<br>Update asset operation constructor. |
| [BaseOperation](-base-operation/index.md) | `abstract class BaseOperation` |
| [CustomOperation](-custom-operation/index.md) | `open class CustomOperation : `[`BaseOperation`](-base-operation/index.md)<br>Custom operation |
| [CustomOperationType](-custom-operation-type/index.md) | `enum class CustomOperationType` |
| [EmptyOperation](-empty-operation/index.md) | `class EmptyOperation : `[`BaseOperation`](-base-operation/index.md) |
| [LeaveRatingAndCommentOperation](-leave-rating-and-comment-operation/index.md) | `class LeaveRatingAndCommentOperation : `[`BaseOperation`](-base-operation/index.md)<br>Leave comment and rating operation constructor |
| [OperationType](-operation-type/index.md) | `enum class OperationType`<br>The order of operation types is important |
| [PurchaseContentOperation](-purchase-content-operation/index.md) | `class PurchaseContentOperation : `[`BaseOperation`](-base-operation/index.md)<br>Request to purchase content operation constructor |
| [RemoveContentOperation](-remove-content-operation/index.md) | `class RemoveContentOperation : `[`BaseOperation`](-base-operation/index.md)<br>Remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database. |
| [SendMessageOperation](-send-message-operation/index.md) | `class SendMessageOperation : `[`CustomOperation`](-custom-operation/index.md)<br>Send message operation. |
| [TransferOperation](-transfer-operation/index.md) | `class TransferOperation : `[`BaseOperation`](-base-operation/index.md)<br>Transfer operation constructor |

