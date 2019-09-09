package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Proposal(
    @SerializedName("id") val id: ProposalObjectId,
    @SerializedName("expiration_time") val expirationTime: LocalDateTime,
    @SerializedName("review_period_time") val reviewPeriodTime: LocalDateTime?,
    @SerializedName("proposed_transaction") val transaction: Transaction,
    @SerializedName("required_active_approvals") val requiredActiveApprovals: List<AccountObjectId>,
    @SerializedName("available_active_approvals") val availableActiveApprovals: List<AccountObjectId>,
    @SerializedName("required_owner_approvals") val requiredOwnerApprovals: List<AccountObjectId>,
    @SerializedName("available_owner_approvals") val availableOwnerApprovals: List<AccountObjectId>,
    @SerializedName("available_key_approvals") val availableKeyApprovals: List<Address>
)
