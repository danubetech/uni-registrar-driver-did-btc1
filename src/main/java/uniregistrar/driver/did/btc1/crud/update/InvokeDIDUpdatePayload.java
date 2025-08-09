package uniregistrar.driver.did.btc1.crud.update;

import foundation.identity.did.DID;
import foundation.identity.did.DIDDocument;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.Network;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;
import uniregistrar.driver.did.btc1.crud.create.Create;
import uniregistrar.driver.did.btc1.syntax.DidBtc1IdentifierEncoding;

import java.util.Map;

public class InvokeDIDUpdatePayload {

    private static final Logger log = LoggerFactory.getLogger(InvokeDIDUpdatePayload.class);

    private Update update;
    private BitcoinConnector bitcoinConnector;
    private IPFSConnection ipfsConnection;

    public InvokeDIDUpdatePayload(Update update, BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.update = update;
        this.bitcoinConnector = bitcoinConnector;
        this.ipfsConnection = ipfsConnection;
    }

    /*
     * 4.1.1 Deterministic Key-based Creation
     */

    // See https://dcdpr.github.io/did-btc1/#deterministic-key-based-creation
    public Map.Entry<DID, DIDDocument> deterministicKeybasedCreation(byte[] pubKeyBytes, Integer version, Network network, /* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {
        //if (log.isDebugEnabled()) log.debug("deterministicKeybasedCreation ({}, {}, {})", Hex.encodeHexString(pubKeyBytes), version, network);

        //

        return null;
    }

    /*
     * Getters and setters
     */

    public Update getUpdate() {
        return this.update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public BitcoinConnector getBitcoinConnector() {
        return bitcoinConnector;
    }

    public void setBitcoinConnector(BitcoinConnector bitcoinConnector) {
        this.bitcoinConnector = bitcoinConnector;
    }

    public IPFSConnection getIpfsConnection() {
        return ipfsConnection;
    }

    public void setIpfsConnection(IPFSConnection ipfsConnection) {
        this.ipfsConnection = ipfsConnection;
    }
}
