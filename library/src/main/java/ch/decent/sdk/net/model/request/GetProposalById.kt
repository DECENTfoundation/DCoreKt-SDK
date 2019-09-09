package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Proposal
import ch.decent.sdk.model.ProposalObjectId
import com.google.gson.reflect.TypeToken

internal class GetProposalById(
    ids: List<ProposalObjectId>
) : GetObjects<List<Proposal>>(
    ids,
    TypeToken.getParameterized(List::class.java, Proposal::class.java).type
)
