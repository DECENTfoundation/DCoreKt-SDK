package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.MinerVotes
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal object GetActualVotes : BaseRequest<List<MinerVotes>>(
    ApiGroup.DATABASE,
    "get_actual_votes",
    TypeToken.getParameterized(List::class.java, MinerVotes::class.java).type
)