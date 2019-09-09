package ch.decent.sdk.model.operation

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ProposalObjectId
import com.google.gson.annotations.SerializedName

class ProposalUpdateOperation(
    @SerializedName("fee_paying_account") val payer: AccountObjectId,
    @SerializedName("proposal") val proposal: ProposalObjectId,
    @SerializedName("active_approvals_to_add") val activeApprovalsAdd: List<AccountObjectId> = emptyList(),
    @SerializedName("active_approvals_to_remove") val activeApprovalsRemove: List<AccountObjectId> = emptyList(),
    @SerializedName("owner_approvals_to_add") val ownerApprovalsAdd: List<AccountObjectId> = emptyList(),
    @SerializedName("owner_approvals_to_remove") val ownerApprovalsRemove: List<AccountObjectId> = emptyList(),
    @SerializedName("key_approvals_to_add") val keyApprovalsAdd: List<Address> = emptyList(),
    @SerializedName("key_approvals_to_remove") val keyApprovalsRemove: List<Address> = emptyList(),
    fee: Fee = Fee()
) : BaseOperation(OperationType.PROPOSAL_UPDATE_OPERATION, fee)
