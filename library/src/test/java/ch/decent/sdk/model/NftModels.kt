package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Modifiable
import ch.decent.sdk.model.annotations.Unique

data class NftApple(
    val size: Int,
    @Unique val color: String,
    @Modifiable(NftDataType.Modifiable.BOTH) val eaten: Boolean
): NftData

