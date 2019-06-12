package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Modifiable
import ch.decent.sdk.model.annotations.Unique

data class NftApple(
    val size: Int,
    @Unique val color: String,
    @Modifiable(NftDataType.ModifiableBy.BOTH) var eaten: Boolean
) : NftModel {
  companion object {
    val SYMBOL = "APPLE"
    val SYMBOL_NESTED = "APPLE.NESTED"
  }
}

data class NftNotApple(
    @Modifiable(NftDataType.ModifiableBy.BOTH) var eaten: Boolean,
    val size: Int,
    @Unique val color: String
) : NftModel {
  companion object {
    val SYMBOL = "NOTAPPLE"
  }
}

