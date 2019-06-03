[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [WalletFile](./index.md)

# WalletFile

`data class WalletFile`

### Constructors

| [&lt;init&gt;](-init-.md) | `WalletFile(version: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, cipherText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, salt: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, iv: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, mac: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, id: `[`UUID`](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html)` = UUID.randomUUID())` |

### Properties

| [accountId](account-id.md) | `val accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md) |
| [cipherText](cipher-text.md) | `val cipherText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | `val id: `[`UUID`](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html) |
| [iv](iv.md) | `val iv: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mac](mac.md) | `val mac: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [salt](salt.md) | `val salt: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [version](version.md) | `val version: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Extension Functions

| [credentials](../credentials.md) | `fun `[`WalletFile`](./index.md)`.credentials(password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`Credentials`](../-credentials/index.md) |

