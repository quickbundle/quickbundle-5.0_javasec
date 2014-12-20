package org.quickbundle.tools.support.transpage2htm;

public class HttpFileSuckerException extends AppException
{

    public HttpFileSuckerException()
    {
    }

    public HttpFileSuckerException(Exception exception)
    {
        super(exception);
    }

    public HttpFileSuckerException(String s)
    {
        super(s);
    }

    public HttpFileSuckerException(String s, Exception exception)
    {
        super(s, exception);
    }
}
