package org.quickbundle.orgauth.custom;

import org.quickbundle.orgauth.rmuser.vo.RmUserVo;

public interface IRmCustomOrgService {
    /**
     * 添加新用户并增加组织关系
     * @param vo
     * @param pratyViewId
     * @param name
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public String insertParty(RmUserVo vo,String pratyViewId,String partyTypeId,String[] parentPartyIds ,String[] parentPartyName,String isInherit);
    
    /**
     * 更新用户信息和团体关系信息
     * @param vo
     * @param pratyViewId
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public int updateParty(RmUserVo vo,String pratyViewId,String oldName,String partyTypeId,String[] parentPartyIds ,String[] oldParentPartyIds,String isInherit);
    
    /**
     * 删除用户和团体团体关系
     * @param ids
     */
    public int deleteParty(String[] ids);

}
