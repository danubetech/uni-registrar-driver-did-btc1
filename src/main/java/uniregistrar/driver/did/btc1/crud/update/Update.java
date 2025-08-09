package uniregistrar.driver.did.btc1.crud.update;

import foundation.identity.did.DID;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.Network;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;

import java.util.Map;

public class Update {

    private static final Logger log = LoggerFactory.getLogger(Update.class);

    private ConstructDIDUpdatePayload constructDIDUpdatePayload;
    private InvokeDIDUpdatePayload invokeDIDUpdatePayload;
    private AnnounceDIDUpdate announceDIDUpdate;

    public Update(BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.constructDIDUpdatePayload = new ConstructDIDUpdatePayload(this, bitcoinConnector, ipfsConnection);
        this.invokeDIDUpdatePayload = new InvokeDIDUpdatePayload(this, bitcoinConnector, ipfsConnection);
        this.announceDIDUpdate = new AnnounceDIDUpdate(this, bitcoinConnector, ipfsConnection);
    }

    /*
     * 4.3 Update
     */

    // See https://dcdpr.github.io/did-btc1/#update
    public Map.Entry<DID, DIDDocument> update(byte[] pubKeyBytes, DIDDocument intermediateDocument, Integer version, Network network, /* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {

        //

        return null;
    }

    /*
     * Getters and settes
     */

    public ConstructDIDUpdatePayload getConstructDIDUpdatePayload() {
        return this.constructDIDUpdatePayload;
    }

    public void setConstructDIDUpdatePayload(ConstructDIDUpdatePayload constructDIDUpdatePayload) {
        this.constructDIDUpdatePayload = constructDIDUpdatePayload;
    }

    public InvokeDIDUpdatePayload getInvokeDIDUpdatePayload() {
        return this.invokeDIDUpdatePayload;
    }

    public void setInvokeDIDUpdatePayload(InvokeDIDUpdatePayload invokeDIDUpdatePayload) {
        this.invokeDIDUpdatePayload = invokeDIDUpdatePayload;
    }

    public AnnounceDIDUpdate getAnnounceDIDUpdate() {
        return this.announceDIDUpdate;
    }

    public void setAnnounceDIDUpdate(AnnounceDIDUpdate announceDIDUpdate) {
        this.announceDIDUpdate = announceDIDUpdate;
    }
}
