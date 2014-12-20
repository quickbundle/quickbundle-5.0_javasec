package org.quickbundle.tools.support.mail;

public class SendPassword {
	private final static int DEFAULT_PASSWORD_LENGTH = 6;
	private final static String passwordSet = "abcdefghijklmnopqrstuvwxyz0123456789";

	/**
	 * @param mailConfig
	 *            [0]mailSmtpHost--mail.quickbundle.org,
	 *            [1]sendMailUser--test, 
	 *            [2]sendMailPassword--******,
	 *            [3]mailFrom--noreply@quickbundle.org
	 * @param contentConfig
	 *            [0] title--mail title 支持带有${user,password,mailto} , 
	 *            [1] bodyText--可包含${user|password|mailto}
	 *            [2] bodyHtml--可包含${user|password|mailto}
	 *            [3] aAffix 附件数组{{"显示名", "附件"}, {"a", "/a.jpg"}}
	 * @param aUser_email 二维数组{{"user1", "email1"}, {"user2", "email2"}}
	 * @return user1:password2 \n user2:password2的格式
	 */
	public static String doSendPasswordMail(String[] mailConfig, Object[] contentConfig, String[][] aUser_email) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < aUser_email.length; i++) {
			String user = aUser_email[i][0];
			String password = getRandomPassword(DEFAULT_PASSWORD_LENGTH);
			sb.append(aUser_email[i][0]);
			sb.append(":");
			sb.append(password);
			sb.append("\n");
			//发邮件
			String mailto = aUser_email[i][1];
			String subject = String.valueOf(contentConfig[0]);
			String bodyText = null;
			String bodyHtml = null;
			String[][] aAffix = null;
			if(contentConfig.length > 1 && contentConfig[1].toString().length() > 0) {
				bodyText = contentConfig[1].toString();
				bodyText = bodyText.replaceAll("\\$\\{user\\}", user);
				bodyText = bodyText.replaceAll("\\$\\{password\\}", password);
				bodyText = bodyText.replaceAll("\\$\\{mailto\\}", mailto);
			}
			
			if(contentConfig.length > 2 && contentConfig[2] != null && contentConfig[2].toString().length() > 0) {
				bodyHtml = contentConfig[2].toString();
				bodyHtml = bodyHtml.replaceAll("\\$\\{user\\}", user);
				bodyHtml = bodyHtml.replaceAll("\\$\\{password\\}", password);
				bodyHtml = bodyHtml.replaceAll("\\$\\{mailto\\}", mailto);
			}
			
			if(contentConfig.length > 3 && contentConfig[3] != null && contentConfig[3] instanceof String[][]) {
				aAffix = (String[][])contentConfig[3];
			}
			
			RmMailHandler rmh = new RmMailHandler(mailConfig);
			rmh.send(mailto, subject, bodyText, bodyHtml, aAffix);
		}
		return sb.toString();
	}

	/**
	 * 得到指定位数的随机密码，小写字母+数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomPassword(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(passwordSet.charAt((int) (Math.random() * passwordSet.length())));
		}
		return sb.toString();
	}
}