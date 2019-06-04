[library](../../index.md) / [ch.decent.sdk.utils](../index.md) / [Base58](./index.md)

# Base58

`object Base58`

Base58 is a way to encode Bitcoin addresses (or arbitrary data) as alphanumeric strings.

Note that this is not the same base58 as used by Flickr, which you may find referenced around the Internet.

You may want to consider working with [ch.decent.sdk.model.VersionedChecksummedBytes](#) instead, which
adds support for testing the prefix and suffix bytes commonly found in addresses.

Satoshi explains: why base-58 instead of standard base-64 encoding?

* Don't want 0OIl characters that look the same in some fonts and could be used to create visually identical looking account numbers.
* A string with non-alphanumeric characters is not as easily accepted as an account number.
* E-mail usually won't line-break if there's no punctuation to break at.
* Doubleclicking selects the whole number as one word if it's all alphanumeric.

However, note that the encoding/decoding runs in O(n) time, so it is not useful for large data.

The basic idea of the encoding is to treat the data bytes as a large number represented using
base-256 digits, convert the number to be represented using base-58 digits, preserve the exact
number of leading zeros (which are otherwise lost during the mathematical operations on the
numbers), and finally represent the resulting base-58 digits as alphanumeric ASCII characters.

### Functions

| [decode](decode.md) | `fun decode(input: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>Decodes the given base58 string into the original data bytes. |
| [decodeChecked](decode-checked.md) | `fun decodeChecked(input: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>Decodes the given base58 string into the original data bytes, using the checksum in the last 4 bytes of the decoded data to verify that the rest are correct. The checksum is removed from the returned data. |
| [encode](encode.md) | `fun encode(inp: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Encodes the given bytes as a base58 string (no checksum is appended). |
| [encodeChecked](encode-checked.md) | `fun encodeChecked(version: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, payload: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, compressed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Encodes the given version and bytes as a base58 string. A checksum is appended. |

