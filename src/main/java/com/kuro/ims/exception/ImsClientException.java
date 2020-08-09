package com.kuro.ims.exception;

import org.springframework.security.authentication.DisabledException;

public class ImsClientException extends ImsException
{
    public ImsClientException(String message)
    {
        super(message);
    }


    public ImsClientException(String message, Throwable e)
    {
        super(message, e);
    }
}
