[library](../index.md) / [ch.decent.sdk.crypto](./index.md)

## Package ch.decent.sdk.crypto

### Types

| [Address](-address/index.md) | `data class Address`<br>Class used to encapsulate address-related operations. |
| [Credentials](-credentials/index.md) | `data class Credentials` |
| [DumpedPrivateKey](-dumped-private-key/index.md) | `class DumpedPrivateKey`<br>Parses and generates private keys in the form used by the Bitcoin "dumpprivkey" command. This is the private key bytes with a header byte and 4 checksum bytes at the end. If there are 33 private keyPair bytes instead of 32, then the last byte is a discriminator value for the compressed pubkey. |
| [ECKeyPair](-e-c-key-pair/index.md) | `class ECKeyPair` |
| [LinuxSecureRandom](-linux-secure-random/index.md) | `class LinuxSecureRandom : `[`SecureRandomSpi`](http://docs.oracle.com/javase/6/docs/api/java/security/SecureRandomSpi.html)<br>Implementation from [BitcoinJ implementation](https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/crypto/LinuxSecureRandom.java) |
| [Passphrase](-passphrase/index.md) | `class Passphrase` |
| [Wallet](-wallet/index.md) | `object Wallet` |
| [WalletFile](-wallet-file/index.md) | `data class WalletFile` |

### Exceptions

| [CipherException](-cipher-exception/index.md) | `class CipherException : `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) |

### Extensions for External Classes

| [kotlin.String](kotlin.-string/index.md) |  |
| [org.bouncycastle.math.ec.ECPoint](org.bouncycastle.math.ec.-e-c-point/index.md) |  |

### Functions

| [address](address.md) | `fun `[`ECKeyPair`](-e-c-key-pair/index.md)`.address(): `[`Address`](-address/index.md) |
| [base58](base58.md) | `fun `[`ECKeyPair`](-e-c-key-pair/index.md)`.base58(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [bytes](bytes.md) | `fun `[`Address`](-address/index.md)`.bytes(): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [credentials](credentials.md) | `fun `[`WalletFile`](-wallet-file/index.md)`.credentials(password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`Credentials`](-credentials/index.md) |
| [dpk](dpk.md) | `fun `[`ECKeyPair`](-e-c-key-pair/index.md)`.dpk(): `[`DumpedPrivateKey`](-dumped-private-key/index.md) |
| [ecKey](ec-key.md) | `fun `[`DumpedPrivateKey`](-dumped-private-key/index.md)`.ecKey(): `[`ECKeyPair`](-e-c-key-pair/index.md) |
| [generatePrivateFromPassPhrase](generate-private-from-pass-phrase.md) | `fun generatePrivateFromPassPhrase(phrase: `[`Passphrase`](-passphrase/index.md)`, sequence: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, normalized: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`ECKeyPair`](-e-c-key-pair/index.md)<br>Method generates private key from pass phrase provided by parameter of type [Passphrase](-passphrase/index.md). If parameter [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) is true, provided pass phrase will be converted to upper case before private key calculation. In other case word stays as it was provided. Default value for [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) parameter is true. |
| [generatePrivateFromStringPhrase](generate-private-from-string-phrase.md) | `fun generatePrivateFromStringPhrase(phrase: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, sequence: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, normalized: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`ECKeyPair`](-e-c-key-pair/index.md)<br>Method generates private key from phrase provided by parameter of type [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). If parameter [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) is true, provided pass phrase will be converted to upper case before private key calculation. In other case word stays as it was provided. Default value for [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) parameter is true. |
| [walletFile](wallet-file.md) | `fun `[`Credentials`](-credentials/index.md)`.walletFile(password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`WalletFile`](-wallet-file/index.md) |

