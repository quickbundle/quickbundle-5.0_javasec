package org.quickbundle.project.mail.impl;

import org.quickbundle.project.mail.IRmMailService;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.support.mail.RmMailHandler;

public class RmMailService implements IRmMailService {
	private String mailSmtpHost;
	private String sendMailUser;
	private String sendMailPassword;
	private String mailFrom;
	
	public String getMailSmtpHost() {
		return mailSmtpHost;
	}
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}
	public String getSendMailUser() {
		return sendMailUser;
	}
	public void setSendMailUser(String sendMailUser) {
		this.sendMailUser = sendMailUser;
	}
	public String getSendMailPassword() {
		return sendMailPassword;
	}
	public void setSendMailPassword(String sendMailPassword) {
		this.sendMailPassword = sendMailPassword;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	

	/**
	 * @param mailto 收件人
	 * @param subject 标题
	 * @param bodyText 正文文本, 可以为NULL
	 * @param bodyHtml 正文html, 可以为NULL
	 * @param aAffix 附件数组{{"显示名", "附件"}, {"a", "/a.jpg"}}
	 */
	public void send(String mailto, String subject, String bodyText, String bodyHtml, String[][] aAffix) {
		RmMailHandler mh = new RmMailHandler(new String[]{mailSmtpHost, sendMailUser, sendMailPassword, mailFrom});
		mh.send(mailto, subject, bodyText, bodyHtml, aAffix);
	}
	
	public static void main0(String[] args) {
		RmMailHandler mh = new RmMailHandler(new String[]{"mail.163.com","qb_test","quickbundle","qb_test@163.com"});
		mh.send("test@quickbundle.org", RmDateHelper.getSysDateTime(), "rt", null, null);
	}
}
