package ch.decent.sdk

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.toChainObject

object Globals {
  const val DCT_CHAIN_ID = "17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"
  @JvmField val DCT_ASSET_ID = "1.3.0".toChainObject()
  @JvmField val DCT = Asset(
      DCT_ASSET_ID,
      "DCT",
      8,
      "1.2.1".toChainObject(),
      "",
      Asset.Options(7319777577456900, Asset.ExchangeRate(), true),
      "2.3.0".toChainObject()
  )
}