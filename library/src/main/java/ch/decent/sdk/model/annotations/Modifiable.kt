package ch.decent.sdk.model.annotations

import ch.decent.sdk.model.NftDataType

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Modifiable(val modifiable: NftDataType.ModifiableBy)
