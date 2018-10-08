DCore SDK for JVM
================

Set of APIs for accessing the DCore Blockchain.

Download
--------

Available through the JitPack.io

[![](https://jitpack.io/v/DECENTfoundation/DCoreKt-SDK.svg?style=flat-square)](https://jitpack.io/#DECENTfoundation/DCoreKt-SDK)


Setup
-----

Add the JitPack repository to your build file

```groovy
	allprojects {
	    repositories {
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

Usage
-----

Use `DCoreSdk` to initialize the API. The HTTP provides database read only access.
For `HistoryApi` and `BroadcastApi` the WS has to be setup. 

The `DCoreApi` provides different groups of APIs, an operations helper class and default configuration values.

The supported operations are located in `ch.decent.sdk.model` package, suffixed with `Operation` eg. `TransferOperation(...)`.
Use the `BroadcastApi` to apply the operations to DCore.