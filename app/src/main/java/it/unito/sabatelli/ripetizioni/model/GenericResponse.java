package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class GenericResponse implements Serializable {
    boolean result = false;
    String errorOccurred;
    String sessionId;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(String errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
