package ch.decent.sdk

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetDataObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.utils.PRECISION_MAX
import ch.decent.sdk.utils.RATING_MAX

object DCoreConstants {
  private const val DCT_PRECISION: Byte = 8
  private const val DCT_SUPPLY = 7319777577456900

  @Suppress("MagicNumber")
  @JvmField val PROXY_TO_SELF = AccountObjectId(3)
  @JvmField val DCT_ASSET_ID = AssetObjectId()
  @JvmField val DCT = Asset(
      DCT_ASSET_ID,
      "DCT",
      DCT_PRECISION,
      AccountObjectId(1),
      "",
      AssetOptions(ExchangeRate(AssetAmount(1), AssetAmount(1)), maxSupply = DCT_SUPPLY),
      AssetDataObjectId(0)
  )

  const val ALXT_SYMBOL = "ALXT"
  const val ALAT_SYMBOL = "ALAT"
  const val ALX_SYMBOL = "ALX"
  const val AIA_SYMBOL = "AIA"
  const val DCT_SYMBOL = "DCT"

  const val EXPIRATION_DEF = 30L // 30s
  const val MAX_SHARE_SUPPLY = 7319777577456890
  const val COMMENT_MAX_CHARS = 100
  val RATING_ALLOWED = 1..RATING_MAX
  val PRECISION_ALLOWED = 0..PRECISION_MAX
  const val UIA_DESCRIPTION_MAX_CHARS = 1000
  const val NFT_NAME_MAX_CHARS = 32
  const val BASIS_POINTS_TOTAL = 10000
  const val MAX_INSTANCE_ID = 281474976710655

}
