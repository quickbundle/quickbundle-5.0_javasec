/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.mail --> MailHandler.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-11-23 17:11:16 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.support.mail;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.quickbundle.tools.support.log.RmLogHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmMailHandler {
	public static String DEFAULT_MAIL_ENCODING = "UTF-8";
	
	private String mailSmtpHost;
	private String sendMailUser;
	private String sendMailPassword;
	private String mailFrom;
	
	Transport transport = null;

	/**
	 * @param mailConfig
	 *            [0]mailSmtpHost--mail1.quickbundle.org,
	 *            [1]sendMailUser--test, 
	 *            [2]sendMailPassword--******,
	 *            [3]mailFrom--noreply@quickbundle.org
	 */
	public RmMailHandler(String[] mailConfig) {
		mailSmtpHost = mailConfig[0];
		sendMailUser = mailConfig[1];
		sendMailPassword = mailConfig[2];
		mailFrom = mailConfig[3];
	}

	/**
	 * @param mailto 收件人
	 * @param subject 标题
	 * @param bodyText 正文文本, 可以为NULL
	 * @param bodyHtml 正文html, 可以为NULL
	 * @param aAffix 附件数组{{"显示名", "附件"}, {"a", "/a.jpg"}}
	 */
	public void send(String mailto, String subject, String bodyText, String bodyHtml, String[][] aAffix) {
		try {
			/**
			 * 1、设置邮件服务器，创建session
			 */
			String encoding = MimeUtility.mimeCharset(DEFAULT_MAIL_ENCODING);
			Properties props = new Properties();
			// Setup mail server
			props.put("mail.smtp.localhost", mailSmtpHost);
			props.put("mail.smtp.host", mailSmtpHost);
			props.put("mail.smtp.auth", "true"); // 这样才能通过验证
			// Get session
			Session session = Session.getDefaultInstance(props);
			// watch the mail commands go by to the mail server
			session.setDebug(true);
			/**
			 * 2、创建邮件MimeMessage
			 */
			// Define message
			MimeMessage mes = new MimeMessage(session);
			mes.setFrom(new InternetAddress(mailFrom));

			mes.addRecipients(RecipientType.TO, InternetAddress.parse(mailto, false));
			mes.setSubject(subject, encoding);
			/**
			 * 3、填充邮件的正文（文本、html、附件）
			 */
			{ 
				//设置邮件正文部分
				MimeMultipart multipart = new MimeMultipart();
				//文本、超文本、附件形式
				multipart.setSubType("mixed");
				
				//设置邮件正文文本
				if(bodyText != null && bodyText.length() > 0) {
					MimeBodyPart txtbodyPart = new MimeBodyPart();
					txtbodyPart.setText(bodyText, encoding);
					multipart.addBodyPart(txtbodyPart);

				}
				
				//设置邮件正文HTML部分
				if(bodyHtml != null && bodyHtml.length() > 0) {
					
					MimeBodyPart htmlBodyPart = new MimeBodyPart();
					htmlBodyPart.setContent(bodyHtml, "text/html;charset=" + encoding);
					multipart.addBodyPart(htmlBodyPart);
				}
				
				//发送附件
				if (aAffix != null && aAffix.length > 0) {
					for (int i = 0; i < aAffix.length; i++) {
						MimeBodyPart affixPart = new MimeBodyPart();
					       //附件的设置
						affixPart.setDataHandler(new DataHandler(new FileDataSource(aAffix[i][1])));
						affixPart.setFileName(MimeUtility.encodeText(aAffix[i][0], encoding, "Q"));
						//设置图片的id，供链接引用，暂不支持
						//affixPart.setContentID(aAffix[i][0]);
						multipart.addBodyPart(affixPart);
					}
				}
				// 加入message
				mes.setContent(multipart);
			}
			if(transport == null || !transport.isConnected()) {
				transport = session.getTransport("smtp");
				transport.connect(props.getProperty("mail.smtp.host"), sendMailUser, sendMailPassword);
			}
			transport.sendMessage(mes, mes.getAllRecipients());
			//transport.close();
		} catch (MessagingException e) {
			throw new RuntimeException("send mail:" + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("send mail:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 显式关闭连接
	 */
	public void close() {
		if(transport == null) {
			return;
		}
		try {
			transport.close();
		} catch (MessagingException e) {
			RmLogHelper.getLogger(this.getClass()).error("close():" + e.toString());
		}
	}
}