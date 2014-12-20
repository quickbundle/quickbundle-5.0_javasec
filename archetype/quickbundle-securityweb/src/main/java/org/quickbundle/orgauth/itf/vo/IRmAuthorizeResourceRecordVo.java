package org.quickbundle.orgauth.itf.vo;

public interface IRmAuthorizeResourceRecordVo extends IRmAuthorizeResourceVo{
    /**
     * @return 授权情况(允许或拒绝)
     */
	
    public String getAuthorize_status();
}
