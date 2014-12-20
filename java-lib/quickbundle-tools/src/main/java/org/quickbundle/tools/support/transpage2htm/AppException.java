package org.quickbundle.tools.support.transpage2htm;


import java.io.PrintWriter;

public class AppException extends Exception
{

    private Exception _exception;

    public AppException()
    {
    }

    public AppException(Exception exception)
    {
        _exception = exception;
    }

    public AppException(String s)
    {
        super(s);
    }

    public AppException(String s, Exception exception)
    {
        super(s);
        _exception = exception;
    }

    public String getMessage()
    {
        String s = super.getMessage();
        if(_exception != null)
            s = s + " InnerException --> " + _exception.getMessage();
        return s;
    }

    public void printStackTrace()
    {
        if(_exception != null)
        {
            System.out.println(getMessage());
            _exception.printStackTrace();
        } else
        {
            super.printStackTrace();
        }
    }

    public void printStackTrace(PrintWriter printwriter)
    {
        if(_exception != null)
        {
            printwriter.println(getMessage());
            _exception.printStackTrace(printwriter);
        } else
        {
            super.printStackTrace(printwriter);
        }
    }
}
