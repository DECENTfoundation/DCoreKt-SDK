package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Modifiable
import ch.decent.sdk.model.annotations.Unique
import com.google.gson.JsonArray

data class NftApple(
    val size: Int,
    @Unique val color: String,
    @Modifiable(NftDataType.ModifiableBy.BOTH) val eaten: Boolean
): NftModel

