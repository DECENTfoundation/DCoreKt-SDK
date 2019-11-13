package ch.decent.sdk.net.model.request


import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerObjectId
import ch.decent.sdk.model.WithdrawPermission
import ch.decent.sdk.model.WithdrawPermissionObjectId
import com.google.gson.reflect.TypeToken

internal class GetWithdrawals(
    ids: List<WithdrawPermissionObjectId>
) : GetObjects<List<WithdrawPermission>>(
    ids,
    TypeToken.getParameterized(List::class.java, WithdrawPermission::class.java).type
)
