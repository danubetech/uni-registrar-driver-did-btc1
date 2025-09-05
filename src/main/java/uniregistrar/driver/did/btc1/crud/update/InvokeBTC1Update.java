package uniregistrar.driver.did.btc1.crud.update;

import com.danubetech.dataintegrity.signer.DataIntegrityProofLdSigner;
import foundation.identity.did.DID;
import foundation.identity.did.VerificationMethod;
import foundation.identity.jsonld.JsonLDException;
import foundation.identity.jsonld.JsonLDObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.appendix.DeriveRootCapabilityFromDidBtc1Identifier;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class InvokeBTC1Update {

    private static final Logger log = LoggerFactory.getLogger(InvokeBTC1Update.class);

    private Update update;
    private BitcoinConnector bitcoinConnector;
    private IPFSConnection ipfsConnection;

    public InvokeBTC1Update(Update update, BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.update = update;
        this.bitcoinConnector = bitcoinConnector;
        this.ipfsConnection = ipfsConnection;
    }

    /*
     * 7.3.2 Invoke BTC1 Update
     */

    // See https://dcdpr.github.io/did-btc1/#invoke-btc1-update
    public JsonLDObject invokeBTC1Update(DID identifier, JsonLDObject unsecuredBtc1Update, VerificationMethod verificationMethod, /* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {
        if (log.isDebugEnabled()) log.debug("invokeBTC1Update ({}, {}, {})", identifier, unsecuredBtc1Update, verificationMethod);

        // Set privateKeyBytes to the result of retrieving the private key bytes
        // associated with the verificationMethod value. How this is achieved is left to the implementation.

        byte[] privateKeyBytes = null; /* TODO */

        // Set rootCapability to the result of passing btc1Identifier into the Derive Root Capability from did:btc1 Identifier algorithm.

        JsonLDObject rootCapability = DeriveRootCapabilityFromDidBtc1Identifier.deriveRootCapabilityFromDidBtc1Identifier(identifier);

        // Initialize proofOptions to an empty object.
        // Set proofOptions.type to DataIntegrityProof.
        // Set proofOptions.cryptosuite to bip340-jcs-2025.
        // Set proofOptions.verificationMethod to verificationMethod.id.
        // Set proofOptions.proofPurpose to capabilityInvocation.
        // Set proofOptions.capability to rootCapability.id.
        // Set proofOptions.capabilityAction to Write.
        // Set cryptosuite to the result of executing the Cryptosuite Instantiation algorithm from the BIP340 Data Integrity specification passing in proofOptions.

        DataIntegrityProofLdSigner cryptosuite = new DataIntegrityProofLdSigner(null /* TODO */);
        cryptosuite.setCryptosuite("bip340-jcs-2025");
        cryptosuite.setVerificationMethod(verificationMethod.getId());
        cryptosuite.setProofPurpose("capabilityInvocation");
        cryptosuite.setCapability(rootCapability.getId());
        cryptosuite.setCapabilityAction("Write");

        // Set btc1Update to the result of executing the Add Proof algorithm from VC Data Integrity passing
        // unsecuredBtc1Update as the input document, cryptosuite, and the set of proofOptions.

        JsonLDObject btc1Update;

        try {
            cryptosuite.sign(unsecuredBtc1Update, true, false);
            btc1Update = unsecuredBtc1Update;
        } catch (IOException | GeneralSecurityException | JsonLDException ex) {
            throw new RegistrationException("Cannot sign the BTC1 Update: " + ex.getMessage(), ex);
        }

        // Return btc1Update.

        if (log.isDebugEnabled()) log.debug("invokeBTC1Update: " + btc1Update);
        return btc1Update;
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
