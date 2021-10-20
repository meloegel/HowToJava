package com.howto.exceptions;

// A custom exception to be used when a resource is found but is not supposed to be
public class ResourceFoundException extends RuntimeException{
        public ResourceFoundException(String message) {super("BEEP BOOP BLEEP BLORP ERROR...ERROR " + message);}
}
