[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [getMinerByAccount](./get-miner-by-account.md)

# getMinerByAccount

`fun getMinerByAccount(account: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`Miner`](../../ch.decent.sdk.model/-miner/index.md)`>`

Get the miner owned by a given account.

### Parameters

`account` - the account object id, 1.2.*, whose miner should be retrieved

**Return**
the miner object, or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if the account does not have a miner

