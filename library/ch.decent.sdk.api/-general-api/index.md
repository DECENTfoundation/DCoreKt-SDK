[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [GeneralApi](./index.md)

# GeneralApi

`class GeneralApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [getChainId](get-chain-id.md) | `fun getChainId(): Single<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>Get the chain ID. |
| [getChainProperties](get-chain-properties.md) | `fun getChainProperties(): Single<`[`ChainProperties`](../../ch.decent.sdk.model/-chain-properties/index.md)`>`<br>Retrieve properties associated with the chain. |
| [getConfig](get-config.md) | `fun getConfig(): Single<`[`Config`](../../ch.decent.sdk.model/-config/index.md)`>`<br>Retrieve compile-time constants. |
| [getDynamicGlobalProperties](get-dynamic-global-properties.md) | `fun getDynamicGlobalProperties(): Single<`[`DynamicGlobalProps`](../../ch.decent.sdk.model/-dynamic-global-props/index.md)`>`<br>Retrieve the dynamic properties. The returned object contains information that changes every block interval, such as the head block number, the next miner, etc. |
| [getGlobalProperties](get-global-properties.md) | `fun getGlobalProperties(): Single<`[`GlobalProperties`](../../ch.decent.sdk.model/-global-properties/index.md)`>`<br>Retrieve global properties. This object contains all of the properties of the blockchain that are fixed or that change only once per maintenance interval such as the current list of miners, block interval, etc. |
| [getTimeToMaintenance](get-time-to-maintenance.md) | `fun getTimeToMaintenance(time: LocalDateTime): Single<`[`MinerRewardInput`](../../ch.decent.sdk.model/-miner-reward-input/index.md)`>`<br>Get remaining time to next maintenance interval from given time. |

