package com.kuro.ims.exception;

public class ImsException extends RuntimeException
{
    public ImsException(String message)
    {
        super(message);
    }


    public ImsException(String message, Throwable e)
    {
        super(message, e);
    }
}
