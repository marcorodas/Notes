package com.as_supportpe.notes.model;

/**
 * Created by Administrator on 18/01/2016.
 */
public class Response {
    final Object outputObj;
    final String errorMessage;
    final boolean isOK;

    public Response(Object outputObj, String errorMessage, boolean isOK) {
        this.outputObj = outputObj;
        this.errorMessage = errorMessage;
        this.isOK = isOK;
    }

    public Object getOutputObj() {
        return outputObj;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOK() {
        return isOK;
    }
}
