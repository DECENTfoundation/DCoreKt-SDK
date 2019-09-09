package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ProposalObjectId
import com.google.gson.annotations.SerializedName

class ProposalDeleteOperation(
    @SerializedName("fee_paying_account") val payer: AccountObjectId,
    @SerializedName("proposal") val proposal: ProposalObjectId,
    @SerializedName("using_owner_authority") val usingOwnerAuthority: Boolean = false,
    fee: Fee = Fee()
) : BaseOperation(OperationType.PROPOSAL_DELETE_OPERATION, fee)
