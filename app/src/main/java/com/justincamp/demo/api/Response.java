package com.justincamp.demo.api;

public class Response<T> {

    public boolean isSuccessful = false;
    public T response;
    public Throwable error;

    private Response(T response, Throwable error) {
        if (response == null && error == null) {
            throw new IllegalStateException("Both response and error cannot be null");
        }
        if (response != null && error != null) {
            throw new IllegalStateException("Either response or error must be null");
        }
        isSuccessful = response != null;
        this.response = response;
        this.error = error;
    }

    public Response(T response) {
        this(response, null);
    }

    public Response(Throwable error) {
        this(null, error);
    }

}
