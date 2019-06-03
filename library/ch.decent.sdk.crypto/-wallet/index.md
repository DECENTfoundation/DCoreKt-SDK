[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [Wallet](./index.md)

# Wallet

`object Wallet`

### Types

| [CipherKeyPair](-cipher-key-pair/index.md) | `data class CipherKeyPair` |
| [CipherKeys](-cipher-keys/index.md) | `data class CipherKeys` |
| [DCoreWallet](-d-core-wallet/index.md) | `data class DCoreWallet` |
| [ExtraKeys](-extra-keys/index.md) | `data class ExtraKeys` |
| [PubKeyPair](-pub-key-pair/index.md) | `data class PubKeyPair` |

### Functions

| [create](create.md) | `fun create(credentials: `[`Credentials`](../-credentials/index.md)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`WalletFile`](../-wallet-file/index.md) |
| [decrypt](decrypt.md) | `fun decrypt(walletFile: `[`WalletFile`](../-wallet-file/index.md)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`Credentials`](../-credentials/index.md) |
| [exportDCoreWallet](export-d-core-wallet.md) | `fun exportDCoreWallet(credentials: `[`Credentials`](../-credentials/index.md)`, account: `[`Account`](../../ch.decent.sdk.model/-account/index.md)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`DCoreWallet`](-d-core-wallet/index.md) |
| [importDCoreWallet](import-d-core-wallet.md) | `fun importDCoreWallet(walletJson: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Credentials`](../-credentials/index.md) |

