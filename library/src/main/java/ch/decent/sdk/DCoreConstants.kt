package ch.decent.sdk

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.toChainObject

object DCoreConstants {
  private const val DCT_PRECISION: Short = 8
  private const val DCT_SUPPLY = 7319777577456900

  @JvmField val DCT_ASSET_ID = "1.3.0".toChainObject()
  @JvmField val DCT = Asset(
      DCT_ASSET_ID,
      "DCT",
      DCT_PRECISION,
      "1.2.1".toChainObject(),
      "",
      Asset.Options(DCT_SUPPLY, Asset.ExchangeRate(), true),
      "2.3.0".toChainObject()
  )

  const val ALXT_SYMBOL = "ALXT"
  const val ALAT_SYMBOL = "ALAT"
  const val ALX_SYMBOL = "ALX"
  const val AIA_SYMBOL = "AIA"
  const val DCT_SYMBOL = "DCT"

  const val EXPIRATION_DEF = 30L // 30s
  const val MAX_SHARE_SUPPLY = 7319777577456890
  const val COMMENT_MAX_CHARS = 100
  val RATING_ALLOWED = 1..5
}
