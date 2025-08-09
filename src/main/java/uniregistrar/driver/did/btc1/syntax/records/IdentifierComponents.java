package uniregistrar.driver.did.btc1.syntax.records;

import uniregistrar.driver.did.btc1.Network;

public record IdentifierComponents(
        String idType,
        Integer version,
        Network network,
        byte[] genesisBytes) {
}
