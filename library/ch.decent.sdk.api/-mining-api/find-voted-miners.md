[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [findVotedMiners](./find-voted-miners.md)

# findVotedMiners

`fun findVotedMiners(voteIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VoteId`](../../ch.decent.sdk.model/-vote-id/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Miner`](../../ch.decent.sdk.model/-miner/index.md)`>>`

Given a set of votes, return the objects they are voting for.
The results will be in the same order as the votes. null will be returned for any vote ids that are not found.

### Parameters

`voteIds` - set of votes

**Return**
a list of miners

