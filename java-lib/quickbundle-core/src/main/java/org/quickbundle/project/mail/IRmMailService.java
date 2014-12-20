package org.quickbundle.project.mail;

public interface IRmMailService {

	/**
	 * @param mailto 收件人
	 * @param subject 标题
	 * @param bodyText 正文文本, 可以为NULL
	 * @param bodyHtml 正文html, 可以为NULL
	 * @param aAffix 附件数组{{"显示名", "附件"}, {"a", "/a.jpg"}}
	 */
	public void send(String mailto, String subject, String bodyText, String bodyHtml, String[][] aAffix);

}