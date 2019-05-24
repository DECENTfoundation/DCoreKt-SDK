package ch.decent.sdk.model.annotations

import ch.decent.sdk.model.NftDataType

@Target(AnnotationTarget.FIELD)
annotation class Modifiable(val modifiable: NftDataType.Modifiable)
