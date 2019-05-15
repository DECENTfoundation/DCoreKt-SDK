package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32

//sums to 10000 so Int is ok
data class CoAuthors(@UInt32 val authors: Map<ChainObject, Int>)
