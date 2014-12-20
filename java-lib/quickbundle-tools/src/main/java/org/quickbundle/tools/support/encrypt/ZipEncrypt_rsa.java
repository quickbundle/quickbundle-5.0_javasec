package org.quickbundle.tools.support.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;

/**
 * 对文件压缩加密/解密解压缩 对象类
 * RSA压缩加密/解压缩解密
 * 
 */
public class ZipEncrypt_rsa {
	private static PrivateKey privateKey;

	private static PublicKey publicKey;

	private static void directoryZip(ZipOutputStream out, File f, String base)
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
	private static void fileZip(ZipOutputStream zos, File file)
			throws Exception {
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
	private static void fileUnZip(ZipInputStream zis, File file)
			throws Exception {
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
	private static void zip(String directory, String zipFile) {
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
	private static void unZip(String directory, String zipFile) {
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
	public static Key getKey(String keyPath) throws Exception {
		Key key = null;
		FileInputStream fis = new FileInputStream(keyPath);
		ObjectInputStream ofs = new ObjectInputStream(fis);
		key = (Key) ofs.readObject();
		return key;
	}

	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 */
	private static void encrypt(String srcFile, String destFile, Key privateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[53];
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
	private static void decrypt(String srcFile, String destFile, Key privateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[64];
		while (fis.read(b) != -1) {
			fos.write(cipher.doFinal(b));
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
	public static void encryptZip(String srcFile, String destfile,
			String keyfile) throws Exception {
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
		kg.initialize(512, sr);
		// 产生新密钥对
		KeyPair kp = kg.generateKeyPair();
		// 获得私匙
		ZipEncrypt_rsa.privateKey = kp.getPrivate();
		// 获得公钥
		ZipEncrypt_rsa.publicKey = kp.getPublic();
		File f = new File(keyfile);
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream dos = new ObjectOutputStream(fos);
		dos.writeObject(ZipEncrypt_rsa.publicKey);

		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		// 先压缩文件
		zip(srcFile, temp.getAbsolutePath());
		// 对文件加密
		encrypt(temp.getAbsolutePath(), destfile, privateKey);
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
	public static void decryptUnzip(String srcfile, String destfile,
			Key publicKey) throws Exception {
		// 先对文件解密
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		decrypt(srcfile, temp.getAbsolutePath(), publicKey);
		// 解压缩
		unZip(destfile, temp.getAbsolutePath());
		temp.delete();
	}

	public static void main(String args[]) throws Exception {
		
		ZipEncrypt_rsa.encryptZip("E:/download/tmp", "E:/download/tmp.zip", "E:/download/b.key");

//		ZipEncrypt.decryptUnzip(destZip, srcPath + 2,ZipEncrypt.getKey(keyfile));

	}
}
