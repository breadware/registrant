package br.com.breadware.google.cloud.authorization;

import br.com.breadware.bo.CloudStorageBo;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.util.EnvironmentUtil;
import br.com.breadware.util.EnvironmentVariables;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Optional;

@Component
public class ClientIdRetriever {

    private final EnvironmentUtil environmentUtil;
    private final CloudStorageBo cloudStorageBo;

    public ClientIdRetriever(EnvironmentUtil environmentUtil, CloudStorageBo cloudStorageBo) {
        this.environmentUtil = environmentUtil;
        this.cloudStorageBo = cloudStorageBo;
    }

    public InputStreamReader retrieve() {
        String clientIdFileLocation = environmentUtil.retrieveMandatoryVariable(EnvironmentVariables.CLIENT_ID_FILE_LOCATION);
        try {
            return createClientIdInputStreamReader(clientIdFileLocation);
        } catch (IOException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_RETRIEVING_GOOGLE_CLIENT_ID);
        }
    }

    private InputStreamReader createClientIdInputStreamReader(String clientIdFileLocation) throws IOException {
        InputStream inputStream = retrieveClientIdInputStream(clientIdFileLocation);
        return new InputStreamReader(inputStream);
    }

    private InputStream retrieveClientIdInputStream(String clientIdFileLocation) throws FileNotFoundException {
        if (cloudStorageBo.isValidObjectUri(clientIdFileLocation)) {
            Optional<InputStream> optionalInputStream = cloudStorageBo.retrieveAsInputStream(clientIdFileLocation);
            return optionalInputStream.orElseThrow(() -> new RegistrantRuntimeException(ErrorMessage.COULD_NOT_LOCATE_CLIENT_ID_FILE, clientIdFileLocation));
        } else {
            return new FileInputStream(clientIdFileLocation);
        }
    }
}
