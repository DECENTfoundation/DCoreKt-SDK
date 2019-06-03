[library](../../../index.md) / [ch.decent.sdk.crypto](../../index.md) / [ECKeyPair](../index.md) / [ECDSASignature](./index.md)

# ECDSASignature

`data class ECDSASignature`

### Constructors

| [&lt;init&gt;](-init-.md) | `ECDSASignature(r: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`, s: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`)` |

### Properties

| [isCanonical](is-canonical.md) | `val isCanonical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns true if the S component is "low", that means it is below [ECKeyPair.HALF_CURVE_ORDER](#). See [BIP62](https://github.com/bitcoin/bips/blob/master/bip-0062.mediawiki#Low_S_values_in_signatures). |
| [r](r.md) | `val r: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [s](s.md) | `val s: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |

### Functions

| [toCanonicalised](to-canonicalised.md) | `fun toCanonicalised(): `[`ECDSASignature`](./index.md)<br>Will automatically adjust the S component to be less than or equal to half the curve order, if necessary. This is required because for every signature (r,s) the signature (r, -s (mod N)) is a valid signature of the same message. However, we dislike the ability to modify the bits of a Bitcoin transaction after it's been signed, as that violates various assumed invariants. Thus in future only one of those forms will be considered legal and the other will be banned. |

