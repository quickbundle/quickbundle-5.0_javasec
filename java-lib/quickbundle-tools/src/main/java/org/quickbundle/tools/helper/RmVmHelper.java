package org.quickbundle.tools.helper;

import java.util.Map;

//import bsh.EvalError;
//import bsh.Interpreter;

public class RmVmHelper {

    /**
     * 运行一段java代码
     * @deprecated
     * 
     * @param javaCode
     * @return
     */
    public static Object runJavaCode(String javaCode) {
    	return runJavaCode(javaCode, null);
    }
    
    /**
     * 运行一段java代码,带上下文
     * @deprecated
     * 
     * @param javaCode
     * @param mParam
     * @return
     */
    public static Object runJavaCode(String javaCode, Map<String, Object> mContext) {
        Object rtObj = null;
//        Interpreter interpreter = null;
//        try {
//            interpreter = new Interpreter();
//            if(mContext != null) {
//            	for(Map.Entry<String, Object> en: mContext.entrySet()) {
//            		interpreter.set(en.getKey(), en.getValue());
//            	}
//            }
//            interpreter.setStrictJava(false);
//            rtObj = interpreter.eval(javaCode);
//        } catch (EvalError e) {
//            throw new RuntimeException(e);
//        }
        return rtObj;
    }
}
