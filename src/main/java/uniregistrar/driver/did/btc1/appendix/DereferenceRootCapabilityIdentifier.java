package uniregistrar.driver.did.btc1.appendix;

import foundation.identity.did.DID;
import foundation.identity.did.parser.ParserException;
import foundation.identity.jsonld.JsonLDObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniregistrar.RegistrationException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class DereferenceRootCapabilityIdentifier {

    private static final Logger log = LoggerFactory.getLogger(DereferenceRootCapabilityIdentifier.class);

    /*
     * 11.4.2 Dereference Root Capability Identifier
     */

    // See https://dcdpr.github.io/did-btc1/#dereference-root-capability-identifier
    public static JsonLDObject dereferenceRootCapabilityIdentifier(String capabilityId) throws RegistrationException {
        if (log.isDebugEnabled()) log.debug("dereferenceRootCapabilityIdentifier ({})", capabilityId);

        // Set rootCapability to an empty object.

        Map<String, Object> rootCapability = new LinkedHashMap<>();

        // Set components to the result of capabilityId.split(":").

        String[] components = capabilityId.split(":");

        // Validate components:
        // Assert length of components is 4.
        // components[0] == urn.
        // components[1] == zcap.
        // components[2] == root.

        if (components.length != 4 || ! "urn".equals(components[0]) || ! "zcap".equals(components[1]) || ! "root".equals(components[2])) {
            throw new RegistrationException("Invalid capabilityId: " + capabilityId);
        }

        // Set uriEncodedId to components[3].

        String uriEncodedId = components[3];

        // Set btc1Identifier the result of decodeURIComponent(uriEncodedId).

        DID btc1Identifier;
        try {
            btc1Identifier = DID.fromString(URLDecoder.decode(uriEncodedId, StandardCharsets.UTF_8));
        } catch (ParserException ex) {
            throw new RegistrationException(RegistrationException.ERROR_INVALID_DID, "Invalid identifier: " + capabilityId, ex);
        }

        // Set rootCapability.id to capabilityId.

        rootCapability.put("id", capabilityId);

        // Set rootCapability.controller to btc1Identifier.

        rootCapability.put("controller", btc1Identifier.getDidString());

        // Set rootCapability.invocationTarget to btc1Identifier.

        rootCapability.put("invocationTarget", btc1Identifier.getDidString());

        // Return rootCapability.

        JsonLDObject jsonldRootCapability = JsonLDObject.fromJsonObject(rootCapability);
        if (log.isDebugEnabled()) log.debug("dereferenceRootCapabilityIdentifier: " + jsonldRootCapability);
        return jsonldRootCapability;
    }
}
