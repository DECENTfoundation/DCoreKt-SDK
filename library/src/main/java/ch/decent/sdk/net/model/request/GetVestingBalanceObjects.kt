package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.VestingBalance
import ch.decent.sdk.model.VestingBalanceObjectId
import com.google.gson.reflect.TypeToken

internal class GetVestingBalanceObjects(
    ids: List<VestingBalanceObjectId>
) : GetObjects<List<VestingBalance>>(
    ids,
    TypeToken.getParameterized(List::class.java, VestingBalance::class.java).type
)
