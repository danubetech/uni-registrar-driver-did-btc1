# Universal Registrar Driver: did:btc1

This is a [Universal Registrar](https://github.com/decentralized-identity/universal-registrar/) driver for **did:btc1** identifiers.

(work in progress)

## Specifications

* [Decentralized Identifiers](https://w3c.github.io/did-core/)
* [DID Method Specification](https://dcdpr.github.io/did-btc1/)

## Build and Run (Docker)

```
docker build -f ./docker/Dockerfile . -t universalregistrar/driver-did-btc1
docker run -p 9080:9080 universalregistrar/driver-did-btc1
```

## Driver Configurations

The BTC1 Driver can be configured with environment variables and/or passing application properties. In case no properties file is provided, the driver will try to load [driver.properties](src/main/resources/driver.properties). If a parameter has value as an environment variable, it will be used over its field in the properties file. 

TODO

## Operations

TODO

## Implementation Notes

TODO
