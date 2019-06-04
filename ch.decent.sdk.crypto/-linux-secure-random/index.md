[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [LinuxSecureRandom](./index.md)

# LinuxSecureRandom

`class LinuxSecureRandom : `[`SecureRandomSpi`](http://docs.oracle.com/javase/6/docs/api/java/security/SecureRandomSpi.html)

Implementation from
[BitcoinJ implementation](https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/crypto/LinuxSecureRandom.java)

A SecureRandom implementation that is able to override the standard JVM provided
implementation, and which simply serves random numbers by reading /dev/urandom. That is, it
delegates to the kernel on UNIX systems and is unusable on other platforms. Attempts to manually
set the seed are ignored. There is no difference between seed bytes and non-seed bytes, they are
all from the same source.

### Constructors

| [&lt;init&gt;](-init-.md) | `LinuxSecureRandom()`<br>Implementation from [BitcoinJ implementation](https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/crypto/LinuxSecureRandom.java) |

### Functions

| [engineGenerateSeed](engine-generate-seed.md) | `fun engineGenerateSeed(i: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [engineNextBytes](engine-next-bytes.md) | `fun engineNextBytes(bytes: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [engineSetSeed](engine-set-seed.md) | `fun engineSetSeed(bytes: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

