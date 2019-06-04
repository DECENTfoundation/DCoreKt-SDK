[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [verifyAccountAuthority](./verify-account-authority.md)

# verifyAccountAuthority

`fun verifyAccountAuthority(nameOrId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, keys: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`>): Single<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`

Verifies if the signers have enough authority to authorize an account.

### Parameters

`nameOrId` - account name or object id

`keys` - signer keys

**Return**
if the signers have enough authority

