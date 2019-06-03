[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [ObjectType](./index.md)

# ObjectType

`enum class ObjectType`

Enum type used to list all possible object types and obtain their space + type id

### Enum Values

| [NULL_OBJECT](-n-u-l-l_-o-b-j-e-c-t.md) |  |
| [BASE_OBJECT](-b-a-s-e_-o-b-j-e-c-t.md) |  |
| [ACCOUNT_OBJECT](-a-c-c-o-u-n-t_-o-b-j-e-c-t.md) |  |
| [ASSET_OBJECT](-a-s-s-e-t_-o-b-j-e-c-t.md) |  |
| [MINER_OBJECT](-m-i-n-e-r_-o-b-j-e-c-t.md) |  |
| [CUSTOM_OBJECT](-c-u-s-t-o-m_-o-b-j-e-c-t.md) |  |
| [PROPOSAL_OBJECT](-p-r-o-p-o-s-a-l_-o-b-j-e-c-t.md) |  |
| [OPERATION_HISTORY_OBJECT](-o-p-e-r-a-t-i-o-n_-h-i-s-t-o-r-y_-o-b-j-e-c-t.md) |  |
| [WITHDRAW_PERMISSION_OBJECT](-w-i-t-h-d-r-a-w_-p-e-r-m-i-s-s-i-o-n_-o-b-j-e-c-t.md) |  |
| [VESTING_BALANCE_OBJECT](-v-e-s-t-i-n-g_-b-a-l-a-n-c-e_-o-b-j-e-c-t.md) |  |
| [GLOBAL_PROPERTY_OBJECT](-g-l-o-b-a-l_-p-r-o-p-e-r-t-y_-o-b-j-e-c-t.md) |  |
| [DYNAMIC_GLOBAL_PROPERTY_OBJECT](-d-y-n-a-m-i-c_-g-l-o-b-a-l_-p-r-o-p-e-r-t-y_-o-b-j-e-c-t.md) |  |
| [RESERVED_OBJECT](-r-e-s-e-r-v-e-d_-o-b-j-e-c-t.md) |  |
| [ASSET_DYNAMIC_DATA](-a-s-s-e-t_-d-y-n-a-m-i-c_-d-a-t-a.md) |  |
| [ACCOUNT_BALANCE_OBJECT](-a-c-c-o-u-n-t_-b-a-l-a-n-c-e_-o-b-j-e-c-t.md) |  |
| [ACCOUNT_STATISTICS_OBJECT](-a-c-c-o-u-n-t_-s-t-a-t-i-s-t-i-c-s_-o-b-j-e-c-t.md) |  |
| [TRANSACTION_OBJECT](-t-r-a-n-s-a-c-t-i-o-n_-o-b-j-e-c-t.md) |  |
| [BLOCK_SUMMARY_OBJECT](-b-l-o-c-k_-s-u-m-m-a-r-y_-o-b-j-e-c-t.md) |  |
| [ACCOUNT_TRANSACTION_HISTORY_OBJECT](-a-c-c-o-u-n-t_-t-r-a-n-s-a-c-t-i-o-n_-h-i-s-t-o-r-y_-o-b-j-e-c-t.md) |  |
| [CHAIN_PROPERTY_OBJECT](-c-h-a-i-n_-p-r-o-p-e-r-t-y_-o-b-j-e-c-t.md) |  |
| [MINER_SCHEDULE_OBJECT](-m-i-n-e-r_-s-c-h-e-d-u-l-e_-o-b-j-e-c-t.md) |  |
| [BUDGET_RECORD_OBJECT](-b-u-d-g-e-t_-r-e-c-o-r-d_-o-b-j-e-c-t.md) |  |
| [PURCHASE_OBJECT](-p-u-r-c-h-a-s-e_-o-b-j-e-c-t.md) |  |
| [CONTENT_OBJECT](-c-o-n-t-e-n-t_-o-b-j-e-c-t.md) |  |
| [PUBLISHER_OBJECT](-p-u-b-l-i-s-h-e-r_-o-b-j-e-c-t.md) |  |
| [SUBSCRIPTION_OBJECT](-s-u-b-s-c-r-i-p-t-i-o-n_-o-b-j-e-c-t.md) |  |
| [SEEDING_STATISTICS_OBJECT](-s-e-e-d-i-n-g_-s-t-a-t-i-s-t-i-c-s_-o-b-j-e-c-t.md) |  |
| [TRANSACTION_DETAIL_OBJECT](-t-r-a-n-s-a-c-t-i-o-n_-d-e-t-a-i-l_-o-b-j-e-c-t.md) |  |
| [MESSAGING_OBJECT](-m-e-s-s-a-g-i-n-g_-o-b-j-e-c-t.md) |  |

### Properties

| [genericId](generic-id.md) | `val genericId: `[`ChainObject`](../-chain-object/index.md)<br>This method is used to return the generic object type in the form space.type.0. |
| [space](space.md) | `val space: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |
| [type](type.md) | `val type: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |

### Companion Object Functions

| [fromSpaceType](from-space-type.md) | `fun fromSpaceType(space: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, type: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`ObjectType`](./index.md) |

