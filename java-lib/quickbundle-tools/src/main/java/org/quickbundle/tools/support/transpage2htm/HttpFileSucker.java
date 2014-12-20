package org.quickbundle.tools.support.transpage2htm;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFileSucker {

	private String proxyHost;
	private String proxyPort;

	public HttpFileSucker() {
	}

	public HttpFileSucker(String s, String s1) {
		proxyHost = s;
		proxyPort = s1;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyHost(String s) {
		proxyHost = s;
	}

	public void setProxyPort(String s) {
		proxyPort = s;
	}

	public void suck(URL url, File file) throws HttpFileSuckerException
	//throws HttpFileSuckerException
	{
		if (proxyHost != null) {
			java.util.Properties properties = System.getProperties();
			properties.put("http.proxyHost", proxyHost);
			properties.put("http.proxyPort", proxyPort);
		}
		try {
			HttpURLConnection httpurlconnection =
				(HttpURLConnection) url.openConnection();
			BufferedInputStream bufferedinputstream =
				new BufferedInputStream(httpurlconnection.getInputStream());
			BufferedOutputStream bufferedoutputstream =
				new BufferedOutputStream(new FileOutputStream(file));
			int i;
			while ((i = bufferedinputstream.read()) != -1)
				bufferedoutputstream.write(i);
			bufferedinputstream.close();
			bufferedoutputstream.close();
			httpurlconnection.disconnect();
		} catch (IOException _ex) {
			try {
				HttpURLConnection httpurlconnection1 =
					(HttpURLConnection) url.openConnection();
				httpurlconnection1.setRequestMethod("HEAD");
				httpurlconnection1.connect();
				HttpFileSuckerException httpfilesuckerexception =
					new HttpFileSuckerException(
						httpurlconnection1.getResponseMessage());
				httpurlconnection1.disconnect();
				throw httpfilesuckerexception;
			} catch (Exception exception) {
				throw new HttpFileSuckerException(
					"Errore non gestito",
					exception);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void transNews(
		String baseURL,
		String baseFilePath,
		String channel_id,
		String fileName)
		throws HttpFileSuckerException {
		URL u = null;
		try {
			u = new URL(baseURL + channel_id);
			File f = new File(baseFilePath + fileName);
			if (u != null)
				this.suck(u, f);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void transNews(String url, String filePath)
		throws HttpFileSuckerException {
		URL u = null;
		try {
			u = new URL(url);
			File f = new File(filePath);
			if (u != null)
				this.suck(u, f);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) throws HttpFileSuckerException {
		HttpFileSucker hfs = new HttpFileSucker();
		try {
			String baseURL =
				"http://127.0.0.1:9080/Eipco/news/NewsListAction.do?cmd=listNewsByChannel_id&channel_id=";
			String baseFilePath = System.getProperty("file.separator");
			String channel_id = "001001001";
			String fileName = channel_id + ".htm";
			hfs.transNews(baseURL, baseFilePath, channel_id, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
