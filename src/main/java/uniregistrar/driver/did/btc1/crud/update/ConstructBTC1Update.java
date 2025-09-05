package uniregistrar.driver.did.btc1.crud.update;

import foundation.identity.did.DID;
import foundation.identity.did.DIDDocument;
import foundation.identity.did.validation.Validation;
import foundation.identity.jsonld.JsonLDObject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonPatch;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;
import uniregistrar.driver.did.btc1.appendix.JsonCanonicalizationAndHash;
import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnector;
import uniregistrar.driver.did.btc1.connections.ipfs.IPFSConnection;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConstructBTC1Update {

    private static final Logger log = LoggerFactory.getLogger(ConstructBTC1Update.class);

    private Update update;
    private BitcoinConnector bitcoinConnector;
    private IPFSConnection ipfsConnection;

    public ConstructBTC1Update(Update update, BitcoinConnector bitcoinConnector, IPFSConnection ipfsConnection) {
        this.update = update;
        this.bitcoinConnector = bitcoinConnector;
        this.ipfsConnection = ipfsConnection;
    }

    /*
     * 7.3.1 Construct BTC1 Update
     */

    // See https://dcdpr.github.io/did-btc1/#construct-btc1-update
    public JsonLDObject constructBTC1Update(DID identifier, DIDDocument sourceDocument, Integer sourceVersionId, JsonPatch documentPatch, /* TODO: extra, not in spec */ Map<String, Object> didDocumentMetadata) throws RegistrationException {
        if (log.isDebugEnabled()) log.debug("constructBTC1Update ({}, {}, {}, {})", identifier, sourceDocument, sourceVersionId, documentPatch);

        // Check that sourceDocument.id equals btc1Identifier else MUST raise invalidDidUpdate error.

        if (! sourceDocument.getId().equals(identifier.toUri())) {
            throw new RegistrationException("invalidDidUpdate", "Invalid DID Update: sourceDocument.id " + sourceDocument.getId() + " does not match identifier " + identifier);
        }

        // Initialize unsecuredBtc1Update to an empty object.

        Map<String, Object> unsecuredBtc1Update = new LinkedHashMap<>();

        // Set unsecuredBtc1Update.@context to the following list. ["https://w3id.org/zcap/v1",
        // "https://w3id.org/security/data-integrity/v2", "https://w3id.org/json-ld-patch/v1", "https://btc1.dev/context/v1"]

        unsecuredBtc1Update.put("@context", List.of("https://w3id.org/zcap/v1", "https://w3id.org/security/data-integrity/v2", "https://w3id.org/json-ld-patch/v1", "https://btc1.dev/context/v1"));

        // Set unsecuredBtc1Update.patch to documentPatch.

        unsecuredBtc1Update.put("patch", documentPatch.toJsonArray());

        // Set targetDocument to the result of applying the documentPatch to the sourceDocument, following the JSON Patch specification.

        JsonObject didDocumentObject = Json.createObjectBuilder(sourceDocument.toMap()).build();
        JsonObject patchedDidDocumentObject = documentPatch.apply(didDocumentObject);
        StringWriter stringWriter = new StringWriter();
        Json.createWriter(stringWriter).write(patchedDidDocumentObject);
        DIDDocument targetDocument = DIDDocument.fromJson(stringWriter.toString());

        // Validate targetDocument is a conformant DID document, else MUST raise invalidDidUpdate error.

        try {
            Validation.validate(targetDocument);
        } catch (Exception ex) {
            throw new RegistrationException("invalidDidUpdate", "Invalid DID document: " + ex.getMessage(), ex);
        }

        // Set sourceHashBytes to the result of passing sourceDocument into the JSON Canonicalization and Hash algorithm.

        byte[] sourceHashBytes = JsonCanonicalizationAndHash.jsonCanonicalizationAndHash(sourceDocument);

        // Set unsecuredBtc1Update.sourceHash to the base64 of sourceHashBytes.

        unsecuredBtc1Update.put("sourceHash", Base64.encodeBase64String(sourceHashBytes));

        // Set targetHashBytes to the result of passing targetDocument into the JSON Canonicalization and Hash algorithm.

        byte[] targetHashBytes = JsonCanonicalizationAndHash.jsonCanonicalizationAndHash(targetDocument);

        // Set unsecuredBtc1Update.targetHash to the base64 of targetHashBytes.

        unsecuredBtc1Update.put("targetHash", Base64.encodeBase64String(targetHashBytes));

        // Set unsecuredBtc1Update.targetVersionId to sourceVersionId + 1

        unsecuredBtc1Update.put("targetVersionId", sourceVersionId + 1);

        // Return unsecuredBtc1Update.

        JsonLDObject jsonldUnsecuredBtc1Update = JsonLDObject.fromJsonObject(unsecuredBtc1Update);
        if (log.isDebugEnabled()) log.debug("constructBTC1Update: " + jsonldUnsecuredBtc1Update);
        return jsonldUnsecuredBtc1Update;
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
        return this.bitcoinConnector;
    }

    public void setBitcoinConnector(BitcoinConnector bitcoinConnector) {
        this.bitcoinConnector = bitcoinConnector;
    }

    public IPFSConnection getIpfsConnection() {
        return this.ipfsConnection;
    }

    public void setIpfsConnection(IPFSConnection ipfsConnection) {
        this.ipfsConnection = ipfsConnection;
    }
}
