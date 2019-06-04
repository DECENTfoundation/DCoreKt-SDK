[library](../index.md) / [ch.decent.sdk.model](./index.md)

## Package ch.decent.sdk.model

### Types

| [Account](-account/index.md) | `data class Account` |
| [AccountBalance](-account-balance/index.md) | `data class AccountBalance` |
| [AccountOptions](-account-options/index.md) | `data class AccountOptions` |
| [AccountStatistics](-account-statistics/index.md) | `data class AccountStatistics` |
| [AddressAdapter](-address-adapter/index.md) | `object AddressAdapter : TypeAdapter<`[`Address`](../ch.decent.sdk.crypto/-address/index.md)`>` |
| [AmountWithAsset](-amount-with-asset/index.md) | `data class AmountWithAsset : `[`AssetFormatter`](-asset-formatter/index.md) |
| [ApplicationType](-application-type/index.md) | `enum class ApplicationType` |
| [Asset](-asset/index.md) | `data class Asset : `[`AssetFormatter`](-asset-formatter/index.md) |
| [AssetAmount](-asset-amount/index.md) | `data class AssetAmount` |
| [AssetData](-asset-data/index.md) | `data class AssetData` |
| [AssetFormatter](-asset-formatter/index.md) | `interface AssetFormatter` |
| [AssetOptions](-asset-options/index.md) | `data class AssetOptions` |
| [AuthMap](-auth-map/index.md) | `data class AuthMap` |
| [AuthMapAdapter](-auth-map-adapter/index.md) | `object AuthMapAdapter : TypeAdapter<`[`AuthMap`](-auth-map/index.md)`>` |
| [Authority](-authority/index.md) | `data class Authority` |
| [Balance](-balance/index.md) | `data class Balance` |
| [BalanceChange](-balance-change/index.md) | `data class BalanceChange` |
| [BlockHeader](-block-header/index.md) | `data class BlockHeader` |
| [CategoryType](-category-type/index.md) | `enum class CategoryType` |
| [ChainObject](-chain-object/index.md) | `class ChainObject` |
| [ChainObjectAdapter](-chain-object-adapter/index.md) | `object ChainObjectAdapter : TypeAdapter<`[`ChainObject`](-chain-object/index.md)`>` |
| [ChainParameters](-chain-parameters/index.md) | `data class ChainParameters` |
| [ChainProperties](-chain-properties/index.md) | `data class ChainProperties` |
| [CipherKeyPairAdapter](-cipher-key-pair-adapter/index.md) | `object CipherKeyPairAdapter : TypeAdapter<`[`CipherKeyPair`](../ch.decent.sdk.crypto/-wallet/-cipher-key-pair/index.md)`>` |
| [CoAuthors](-co-authors/index.md) | `data class CoAuthors` |
| [CoAuthorsAdapter](-co-authors-adapter/index.md) | `object CoAuthorsAdapter : TypeAdapter<`[`CoAuthors`](-co-authors/index.md)`>` |
| [Config](-config/index.md) | `data class Config` |
| [Content](-content/index.md) | `data class Content` |
| [ContentKeys](-content-keys/index.md) | `data class ContentKeys` |
| [CustodyData](-custody-data/index.md) | `data class CustodyData` |
| [DateTimeAdapter](-date-time-adapter/index.md) | `object DateTimeAdapter : TypeAdapter<LocalDateTime>` |
| [DynamicGlobalProps](-dynamic-global-props/index.md) | `data class DynamicGlobalProps` |
| [ExchangeRate](-exchange-rate/index.md) | `data class ExchangeRate` |
| [ExtraKeysAdapter](-extra-keys-adapter/index.md) | `object ExtraKeysAdapter : TypeAdapter<`[`ExtraKeys`](../ch.decent.sdk.crypto/-wallet/-extra-keys/index.md)`>` |
| [Fee](-fee/index.md) | `data class Fee`<br>Fee asset amount. |
| [FeeParamAdapter](-fee-param-adapter/index.md) | `object FeeParamAdapter : TypeAdapter<`[`FeeParameter`](-fee-parameter/index.md)`>` |
| [FeeParameter](-fee-parameter/index.md) | `data class FeeParameter` |
| [FeeSchedule](-fee-schedule/index.md) | `data class FeeSchedule` |
| [FixedMaxSupply](-fixed-max-supply/index.md) | `data class FixedMaxSupply : `[`StaticVariantSingle`](-static-variant-single/index.md)`<`[`FixedMaxSupply`](-fixed-max-supply/index.md)`>` |
| [FullAccount](-full-account/index.md) | `data class FullAccount` |
| [GlobalParameters](-global-parameters/index.md) | `data class GlobalParameters` |
| [GlobalProperties](-global-properties/index.md) | `data class GlobalProperties` |
| [KeyPart](-key-part/index.md) | `data class KeyPart` |
| [Memo](-memo/index.md) | `class Memo` |
| [Message](-message/index.md) | `data class Message` |
| [MessagePayload](-message-payload/index.md) | `data class MessagePayload` |
| [MessagePayloadReceiver](-message-payload-receiver/index.md) | `data class MessagePayloadReceiver` |
| [MessageReceiver](-message-receiver/index.md) | `data class MessageReceiver` |
| [MessageResponse](-message-response/index.md) | `data class MessageResponse` |
| [Miner](-miner/index.md) | `data class Miner` |
| [MinerId](-miner-id/index.md) | `data class MinerId` |
| [MinerIdAdapter](-miner-id-adapter/index.md) | `object MinerIdAdapter : TypeAdapter<`[`MinerId`](-miner-id/index.md)`>` |
| [MinerRewardInput](-miner-reward-input/index.md) | `data class MinerRewardInput` |
| [MinerVotes](-miner-votes/index.md) | `data class MinerVotes` |
| [MinerVotingInfo](-miner-voting-info/index.md) | `data class MinerVotingInfo` |
| [MonitoredAssetOptions](-monitored-asset-options/index.md) | `data class MonitoredAssetOptions` |
| [ObjectType](-object-type/index.md) | `enum class ObjectType`<br>Enum type used to list all possible object types and obtain their space + type id |
| [OperationHistory](-operation-history/index.md) | `data class OperationHistory` |
| [OperationTypeAdapter](-operation-type-adapter/index.md) | `object OperationTypeAdapter : TypeAdapter<`[`OperationType`](../ch.decent.sdk.model.operation/-operation-type/index.md)`>` |
| [OperationTypeFactory](-operation-type-factory/index.md) | `object OperationTypeFactory : TypeAdapterFactory` |
| [PriceFeed](-price-feed/index.md) | `data class PriceFeed` |
| [PricePerRegion](-price-per-region/index.md) | `data class PricePerRegion` |
| [ProcessedTransaction](-processed-transaction/index.md) | `data class ProcessedTransaction` |
| [PubKey](-pub-key/index.md) | `data class PubKey` |
| [PubKeyAdapter](-pub-key-adapter/index.md) | `object PubKeyAdapter : TypeAdapter<`[`PubKey`](-pub-key/index.md)`>` |
| [Publishing](-publishing/index.md) | `data class Publishing` |
| [Purchase](-purchase/index.md) | `data class Purchase` |
| [RealSupply](-real-supply/index.md) | `data class RealSupply` |
| [RegionalPrice](-regional-price/index.md) | `data class RegionalPrice` |
| [Regions](-regions/index.md) | `enum class Regions` |
| [SearchAccountHistoryOrder](-search-account-history-order/index.md) | `enum class SearchAccountHistoryOrder` |
| [SearchAccountsOrder](-search-accounts-order/index.md) | `enum class SearchAccountsOrder` |
| [SearchContentOrder](-search-content-order/index.md) | `enum class SearchContentOrder` |
| [SearchMinerVotingOrder](-search-miner-voting-order/index.md) | `enum class SearchMinerVotingOrder` |
| [SearchPurchasesOrder](-search-purchases-order/index.md) | `enum class SearchPurchasesOrder` |
| [Seeder](-seeder/index.md) | `data class Seeder` |
| [SignedBlock](-signed-block/index.md) | `data class SignedBlock` |
| [StaticVariant](-static-variant.md) | `interface StaticVariant` |
| [StaticVariant1](-static-variant1/index.md) | `data class StaticVariant1<T1> : `[`StaticVariantParametrized`](-static-variant-parametrized/index.md) |
| [StaticVariant2](-static-variant2/index.md) | `data class StaticVariant2<T1, T2> : `[`StaticVariantParametrized`](-static-variant-parametrized/index.md) |
| [StaticVariantFactory](-static-variant-factory/index.md) | `object StaticVariantFactory : TypeAdapterFactory` |
| [StaticVariantParametrized](-static-variant-parametrized/index.md) | `abstract class StaticVariantParametrized : `[`StaticVariant`](-static-variant.md) |
| [StaticVariantSingle](-static-variant-single/index.md) | `interface StaticVariantSingle<T> : `[`StaticVariant`](-static-variant.md) |
| [Subscription](-subscription/index.md) | `data class Subscription` |
| [Synopsis](-synopsis/index.md) | `data class Synopsis` |
| [Transaction](-transaction/index.md) | `data class Transaction` |
| [TransactionConfirmation](-transaction-confirmation/index.md) | `data class TransactionConfirmation` |
| [TransactionDetail](-transaction-detail/index.md) | `data class TransactionDetail` |
| [VestingBalance](-vesting-balance/index.md) | `data class VestingBalance` |
| [VoteId](-vote-id/index.md) | `data class VoteId` |
| [VoteIdAdapter](-vote-id-adapter/index.md) | `object VoteIdAdapter : TypeAdapter<`[`VoteId`](-vote-id/index.md)`>` |

### Extensions for External Classes

| [kotlin.String](kotlin.-string/index.md) |  |

### Functions

| [contentType](content-type.md) | `fun contentType(app: `[`ApplicationType`](-application-type/index.md)`, category: `[`CategoryType`](-category-type/index.md)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>create content type string |

