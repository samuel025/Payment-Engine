package com.samwellstore.paymentengine.exceptions;

public class RoleAccessDeniedException extends RuntimeException{

    public RoleAccessDeniedException(String message) {
        super(message);
    }
    public RoleAccessDeniedException(String role, String action) {
        super(String.format("Role '%s' is not allowed to perform action: %s", role, action));
    }
}
