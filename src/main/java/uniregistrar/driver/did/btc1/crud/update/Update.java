package uniregistrar.driver.did.btc1.crud.update;

import foundation.identity.did.DID;
import foundation.identity.did.DIDDocument;
import foundation.identity.did.VerificationMethod;
import foundation.identity.jsonld.JsonLDObject;
import jakarta.json.JsonPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class Update {

    private static final Logger log = LoggerFactory.getLogger(Update.class);

    private ConstructBTC1Update constructBTC1Update;
    private InvokeBTC1Update invokeBTC1Update;
    private AnnounceDIDUpdate announceDIDUpdate;

    public Update(BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.constructBTC1Update = new ConstructBTC1Update(this, bitcoinConnector, ipfsConnection);
        this.invokeBTC1Update = new InvokeBTC1Update(this, bitcoinConnector, ipfsConnection);
        this.announceDIDUpdate = new AnnounceDIDUpdate(this, bitcoinConnector, ipfsConnection);
    }

    /*
     * 7.3 Update
     */

    // See https://dcdpr.github.io/did-btc1/#update
    public List<Map<String, Object>> update(DID identifier, DIDDocument sourceDocument, Integer sourceVersionId, JsonPatch documentPatch, URI verificationMethodId, List<URI> beaconIds, /* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {

        // Set unsecuredUpdate to the result of passing btc1Identifier,
        // sourceDocument, sourceVersionId, and documentPatch into the Construct BTC1 Update algorithm.

        JsonLDObject unsecuredUpdate = this.getConstructDIDUpdatePayload().constructBTC1Update(identifier, sourceDocument, sourceVersionId, documentPatch, didDocumentMetadata);

        // Set verificationMethod to the result of retrieving the verificationMethod from sourceDocument using
        // the verificationMethodId.

        VerificationMethod verificationMethod = sourceDocument.getVerificationMethods().stream().filter(v -> verificationMethodId.equals(v.getId())).findFirst().orElse(null);
        if (verificationMethod == null) throw new RegistrationException("invalidVerificationMethod", "Verification method not found: " + verificationMethodId);

        // Validate the verificationMethod is a BIP340 Multikey:
        // verificationMethod.type == Multikey
        // verificationMethod.publicKeyMultibase[4] == zQ3s

        if (! "MultiKey".equals(verificationMethod.getType()) || ! "zQ3s".equals(verificationMethod.getPublicKeyMultibase().substring(0, 4))) {
            throw new RegistrationException("Invalid verification method: " + verificationMethod.getType() + ", " + verificationMethod.getPublicKeyMultibase());
        }

        // Set unsecuredBtc1Update to the result of passing btc1Identifier, unsecuredUpdate, and
        // verificationMethod` to the Invoke BTC1 Update algorithm.

        JsonLDObject btc1Update = this.getInvokeDIDUpdatePayload().invokeBTC1Update(identifier, unsecuredUpdate, verificationMethod, didDocumentMetadata);

        // Set signalsMetadata to the result of passing btc1Identifier, sourceDocument, beaconIds and
        // unsecuredBtc1Update to the Announce DID Update algorithm.

        List<Map<String, Object>> signalsMetadata = this.getAnnounceDIDUpdate().announceDIDUpdate(identifier, sourceDocument, beaconIds, btc1Update, didDocumentMetadata);

        // Return signalsMetadata. It is up to implementations to ensure that the signalsMetadata is persisted.

        if (log.isDebugEnabled()) log.debug("Update: " + signalsMetadata);
        return signalsMetadata;
    }

    /*
     * Getters and settes
     */

    public ConstructBTC1Update getConstructDIDUpdatePayload() {
        return this.constructBTC1Update;
    }

    public void setConstructDIDUpdatePayload(ConstructBTC1Update constructBTC1Update) {
        this.constructBTC1Update = constructBTC1Update;
    }

    public InvokeBTC1Update getInvokeDIDUpdatePayload() {
        return this.invokeBTC1Update;
    }

    public void setInvokeDIDUpdatePayload(InvokeBTC1Update invokeBTC1Update) {
        this.invokeBTC1Update = invokeBTC1Update;
    }

    public AnnounceDIDUpdate getAnnounceDIDUpdate() {
        return this.announceDIDUpdate;
    }

    public void setAnnounceDIDUpdate(AnnounceDIDUpdate announceDIDUpdate) {
        this.announceDIDUpdate = announceDIDUpdate;
    }
}
