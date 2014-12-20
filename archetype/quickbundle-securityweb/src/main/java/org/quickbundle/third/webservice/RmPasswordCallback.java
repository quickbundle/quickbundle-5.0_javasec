package org.quickbundle.third.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.quickbundle.config.RmClusterConfig;


public class RmPasswordCallback implements CallbackHandler {
    
    private Map<String, String> passwords = new HashMap<String, String>();
    
    public RmPasswordCallback() {
    	List<String> lNodeId = RmClusterConfig.getSingleton().getOtherNodeId();
    	lNodeId.add(RmClusterConfig.getSingleton().getSelfId());
    	for(String nodeId : lNodeId) {
    		passwords.putAll(RmClusterConfig.getSingleton().getAuth(nodeId));
    	}
    }

    /**
     * Here, we attempt to get the password from the private 
     * alias/passwords map.
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];

            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
        
        //
        // Password not found
        //
        throw new IOException();
    }
    
    /**
     * Add an alias/password pair to the callback mechanism.
     */
    public void setAliasPassword(String alias, String password) {
        passwords.put(alias, password);
    }
}
