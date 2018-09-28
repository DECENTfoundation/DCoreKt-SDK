package ch.decent.sdk

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.toChainObject

object DCoreConstants {
  const val DEFAULT_EXPIRATION = 30 //seconds
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

  const val ALXT_SYMBOL = "ALXT"
  const val ALAT_SYMBOL = "ALAT"
  const val ALX_SYMBOL = "ALX"
  const val ALA_SYMBOL = "ALA"
}