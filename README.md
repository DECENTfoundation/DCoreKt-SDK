DCore SDK for JVM
================

Set of APIs for accessing the DCore Blockchain.

Download
--------

Available through the JitPack.io

[![](https://jitpack.io/v/DECENTfoundation/DCoreKt-SDK.svg?style=flat-square)][jitpack]


Setup
-----

Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```groovy
dependencies {
    implementation 'com.github.DECENTfoundation:DCoreKt-SDK:$version_tag'
}
```

If not using `gradle`, check [jitpack] site for instructions.

Usage
-----

Use `DCoreSdk` to initialize the API.
The `DCoreApi` provides different groups of APIs for accessing the blockchain and default configuration values.

The supported operations are located in `ch.decent.sdk.model` package, suffixed with `Operation` eg. `TransferOperation(...)`.
Use the `BroadcastApi` to apply the operations to DCore or use appropriate methods in APIs.

Access api using rest
```kotlin
import ch.decent.sdk.model.toChainObject
import okhttp3.OkHttpClient

// create API for HTTP
val api = DCoreSdk.createForHttp(OkHttpClient(), "https://stagesocket.decentgo.com:8090/rpc")
// get account by name, resolves to account id '1.2.34'
val disposable = api.accountApi.get("u961279ec8b7ae7bd62f304f7c1c3d345").subscribe { account ->
  account.id == "1.2.34".toChainObject()
}
```

Access api using websocket
```kotlin
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.toChainObject
import okhttp3.OkHttpClient

// create API for websocket
val api = DCoreSdk.createForWebSocket(OkHttpClient(), "wss://stagesocket.decentgo.com:8090")
// create account credentials form account id and corresponding private key
val credentials = Credentials("1.2.34".toChainObject(), "..wif_private_key..")
// 1 DCT
val amount = AssetAmount(DCoreConstants.DCT.toRaw(1.toBigDecimal()))
// send to account '1.2.35'
val disposable = api.accountApi.transfer(credentials, "1.2.35", amount).subscribe { trxConfirmation ->
  trxConfirmation.print()
}
```

References
----------

[jitpack]: https://jitpack.io/#DECENTfoundation/DCoreKt-SDK