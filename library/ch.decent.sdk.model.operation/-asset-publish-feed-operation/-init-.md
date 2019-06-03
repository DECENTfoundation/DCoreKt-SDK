[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetPublishFeedOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetPublishFeedOperation(publisher: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, asset: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, feed: `[`PriceFeed`](../../ch.decent.sdk.model/-price-feed/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

skip, cannot create monitored asset, also only miner account can publish feeds
asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
therefore throws Missing Active Authority 1.2.0

