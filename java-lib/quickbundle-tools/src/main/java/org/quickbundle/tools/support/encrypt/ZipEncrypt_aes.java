package org.quickbundle.tools.support.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对文件加密/解密和压缩/解压缩对象类
 * AES压缩加密/解压缩解密，网上一般用base64来对byte[]编码,其实不需要，指定AES/CBC/PKCS5Padding
来指定加密解密时候位数不对的情况下，用pkcs5padding来附加位数，不过这个时候读解密的文件的时候，要多读16位的验证位就不会报异常

 * 
 * @author 赵成明
 */
public class ZipEncrypt_aes {
	private void directoryZip(ZipOutputStream out, File f, String base)
			throws Exception {
		// 如果传入的是目录
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			// 创建压缩的子目录
			out.putNextEntry(new ZipEntry(base + "/"));
			if (base.length() == 0) {
				base = "";
			} else {
				base = base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				directoryZip(out, fl[i], base + fl[i].getName());
			}
		} else {
			// 把压缩文件加入rar中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] bb = new byte[2048];
			int aa = 0;
			while ((aa = in.read(bb)) != -1) {
				out.write(bb, 0, aa);
			}
			in.close();
		}
	}

	/**
	 * 压缩文件
	 * 
	 * @param zos
	 * @param file
	 * @throws Exception
	 */
	private void fileZip(ZipOutputStream zos, File file) throws Exception {
		if (file.isFile()) {
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileInputStream fis = new FileInputStream(file);
			byte[] bb = new byte[2048];
			int aa = 0;
			while ((aa = fis.read(bb)) != -1) {
				zos.write(bb, 0, aa);
			}
			fis.close();
			System.out.println(file.getName());
		} else {
			directoryZip(zos, file, "");
		}
	}

	/**
	 * 解压缩文件
	 * 
	 * @param zis
	 * @param file
	 * @throws Exception
	 */
	private void fileUnZip(ZipInputStream zis, File file) throws Exception {
		ZipEntry zip = zis.getNextEntry();
		if (zip == null)
			return;
		String name = zip.getName();
		File f = new File(file.getAbsolutePath() + "/" + name);
		if (zip.isDirectory()) {
			f.mkdirs();
			fileUnZip(zis, file);
		} else {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			byte b[] = new byte[2048];
			int aa = 0;
			while ((aa = zis.read(b)) != -1) {
				fos.write(b, 0, aa);
			}
			fos.close();
			fileUnZip(zis, file);
		}
	}

	/**
	 * 对directory目录下的文件压缩，保存为指定的文件zipFile
	 * 
	 * @param directory
	 * @param zipFile
	 */
	private void zip(String directory, String zipFile) {
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					zipFile));
			fileZip(zos, new File(directory));
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩文件zipFile保存在directory目录下
	 * 
	 * @param directory
	 * @param zipFile
	 */
	private void unZip(String directory, String zipFile) {
		try {
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipFile));
			File f = new File(directory);
			f.mkdirs();
			fileUnZip(zis, f);
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key的路径文件获得持久化成文件的key
	 * <P>
	 * 例子: RsaEncrypt.getKey("c:/systemkey/private.key");
	 * 
	 * @param keyPath
	 * @return
	 */
	private Key getKey(String keyPath) throws Exception {
		FileInputStream fis = new FileInputStream(keyPath);
		byte[] b = new byte[16];
		fis.read(b);
		SecretKeySpec dks = new SecretKeySpec(b, "AES");
		fis.close();
		return dks;
	}

	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 */
	private void encrypt(String srcFile, String destFile, Key privateKey)
			throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2048];
		while (fis.read(b) != -1) {
			fos.write(cipher.doFinal(b));
		}
		fos.close();
		fis.close();
	}

	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param privateKey
	 * @throws Exception
	 */
	private void decrypt(String srcFile, String destFile, Key privateKey)
			throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher ciphers = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		ciphers.init(Cipher.DECRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2064];
		while (fis.read(b) != -1) {
			fos.write(ciphers.doFinal(b));
		}
		fos.close();
		fis.close();
	}

	/**
	 * 对目录srcFile下的所有文件目录进行先压缩后操作,然后保存为destfile
	 * 
	 * @param srcFile
	 *            要操作的目录 如c:/test/test
	 * @param destfile
	 *            压缩加密后存放的文件名 如c:/加密压缩文件.zip
	 * @param keyfile
	 *            公钥存放地点
	 */
	public void encryptZip(String srcFile, String destfile, String keyfile)
			throws Exception {
		SecureRandom sr = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(128, sr);
		SecretKey key = kg.generateKey();
		File f = new File(keyfile);
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key.getEncoded());
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		// 先压缩文件
		zip(srcFile, temp.getAbsolutePath());
		// 对文件加密
		encrypt(temp.getAbsolutePath(), destfile, key);
		temp.delete();
	}

	/**
	 * 对文件srcfile进行先解密后解压缩,然后解压缩到目录destfile下
	 * 
	 * @param srcfile
	 *            要解密和解压缩的文件名 如c:/目标.zip
	 * @param destfile
	 *            解压缩后的目录 如c:/abc
	 * @param publicKey
	 *            公钥
	 */
	public void decryptUnzip(String srcfile, String destfile, String keyfile)
			throws Exception {
		// 先对文件解密
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		decrypt(srcfile, temp.getAbsolutePath(), this.getKey(keyfile));
		// 解压缩
		unZip(destfile, temp.getAbsolutePath());
		temp.delete();
	}

	public static void main(String args[]) throws Exception {
		long a = System.currentTimeMillis();
		new ZipEncrypt_aes().encryptZip("e:/com", "e:/comXXX/page.zip",
				"e:/comXXX/public.key");

		System.out.println(System.currentTimeMillis() - a);
		a = System.currentTimeMillis();

		new ZipEncrypt_aes().decryptUnzip("e:/comXXX/page.zip", "e:/comxxx",
				"e:/comXXX/public.key");
		System.out.println(System.currentTimeMillis() - a);
	}
}
