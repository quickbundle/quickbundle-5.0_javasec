package org.quickbundle.orgauth.util;

import java.util.ArrayList;
import java.util.List;

import org.quickbundle.util.numeral.RmBaseXNumeral;

import org.quickbundle.orgauth.cache.RmPartyViewCache;

public class RmPartyRelationCode extends RmBaseXNumeral {
	
	public final static String MIN_VALUE = "000";
	
	/**
	 * 默认3位的体系，用于组织结构编码
	 * @param parentPartyCode 父全编码
	 * @param maxChildPartyCode 子全编码
	 */
	public RmPartyRelationCode(String parentPartyCode, String maxChildPartyCode) {
		super(parentPartyCode == null ? maxChildPartyCode : maxChildPartyCode.substring(parentPartyCode.length()));
	}
	
	/**
	 * 默认3位的体系，用于组织结构编码
	 * @param maxChildPartySelfCode 子自身编码
	 */
	public RmPartyRelationCode(String maxChildPartySelfCode) {
		super(maxChildPartySelfCode);
	}
	
	/**
	 * 切割party_child_code，获得包含自身、所有祖先的编码数组
	 * @param view_id
	 * @param partyCode
	 * @return
	 */
	public static String[] splitPartyCode(String partyCode, String view_id) {
		List<String> lCode = new ArrayList<String>();
		if(partyCode != null && partyCode.length() > 0) {
			String remainStr = partyCode;
			String bk = RmPartyViewCache.getPartyView(view_id).getBs_keyword();
			while(remainStr.length() > 0 && !bk.equals(remainStr)) {
				lCode.add(remainStr);
				remainStr = remainStr.substring(0, remainStr.length()-MIN_VALUE.length());
			}
		}
		return lCode.toArray(new String[0]);
	}
}
