package uniregistrar.driver.did.btc1.crud.update;

import foundation.identity.did.DID;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;

import java.util.Map;

public class AnnounceDIDUpdate {

    private static final Logger log = LoggerFactory.getLogger(AnnounceDIDUpdate.class);

    private Update update;
    private BitcoinConnector bitcoinConnector;
    private IPFSConnection ipfsConnection;

    public AnnounceDIDUpdate(Update update, BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.update = update;
        this.bitcoinConnector = bitcoinConnector;
        this.ipfsConnection = ipfsConnection;
    }

    /*
     * 4.3.3 Announce DID Update
     */

    // See https://dcdpr.github.io/did-btc1/#announce-did-update
    public Map.Entry<DID, DIDDocument> announceDIDUpdate(/* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {
        //if (log.isDebugEnabled()) log.debug("announceDIDUpdate ({}, {}, {})", Hex.encodeHexString(pubKeyBytes), version, network);

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
