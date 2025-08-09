# Universal Registrar Driver: did:btc1

This is a [Universal Registrar](https://github.com/decentralized-identity/universal-registrar/) driver for **did:btc1** identifiers.

(work in progress)

## Specifications

* [Decentralized Identifiers](https://w3c.github.io/did-core/)
* [DID Method Specification](https://dcdpr.github.io/did-btc1/)

## Build and Run (Docker)

```
docker compose build
docker compose up
```

## Example Requests

```shell
curl -X POST "http://localhost:9080/1.0/create?method=btc1" \
     -H "Content-Type: application/json" \
     -d '{
       "didDocument": {
         "@context": ["https//www.w3.org/ns/did/v1"]
       },
       "options": {
         "network": "mutinynet",
         "clientSecretMode": true
       },
       "secret": { }
     }'
```

## Driver Environment Variables

The driver recognizes the following environment variables:

### `uniregistrar_driver_did_btc1_bitcoinConnections`

* Specifies how the driver interacts with the Bitcoin blockchain.
* Possible values:
    * `bitcoind`: Connects to a [bitcoind](https://bitcoin.org/en/full-node) instance via JSON-RPC
    * `btcd`: Connects to a [btcd](https://github.com/btcsuite/btcd) instance via JSON-RPC
    * `bitcoinj`: Connects to Bitcoin using a local [bitcoinj](https://bitcoinj.github.io/) client
    * `blockcypherapi`: Connects to [BlockCypher's API](https://www.blockcypher.com/dev/bitcoin/)
    * `esploraelectrsrest`: Connects to Esplora/Electrs REST API
* Default value: `bitcoind`

### `uniregistrar_driver_did_btc1_bitcoinConnectionsUrls`

* Specifies the JSON-RPC URLs of the Bitcoin connections.

### `uniregistrar_driver_did_btc1_bitcoinConnectionsCerts`

* Specifies the server TLS certificates of the Bitcoin connections.
* Default value: ``

## Driver Input Options

```
{
    "network": "mutinynet"
}
```

* `network`: The name of the network where a DID should be registered. Values depend on `bitcoinConnections` environment variable, but are typically: `bitcoin`, `testnet3`, `signet`, `mutinynet`.
