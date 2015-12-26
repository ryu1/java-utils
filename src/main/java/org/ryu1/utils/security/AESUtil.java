package org.ryu1.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author R.Ishitsuka
 */
public class AESUtil {
	private static Logger log = LoggerFactory.getLogger(AESUtil.class);

	private final Cipher encryptor, decryptor;

	public AESUtil(final int keysize, final String stringKey)
			throws Exception {
		log.info("call AESUtil#postConstruct - 初期化");
		if (stringKey == null) {
			throw new RuntimeException("AES鍵がNULLです。");
		}

		byte[] bytesKey = null;

		File file = new File(stringKey);

		if (file.exists()) {
			// プロパティが鍵の格納ファイルパスの場合
			InputStream in = null;

			try {
				in = new FileInputStream(file);
				bytesKey = new byte[keysize / 8];
				int len = in.read(bytesKey);

				if (len != (keysize / 8)) {
					throw new Exception("AES鍵[" + stringKey + "]が" + len
							+ "bytesです。(" + (keysize / 8)
							+ " bytesじゃないといけません。)");
				}
			} catch (IOException e) {
				throw new IOException("AES鍵[" + stringKey + "]の読み込みに失敗しました。");
			} finally {
				if (in != null)
					in.close();
			}
		} else {
			// プロパティがダミー鍵の場合
			bytesKey = stringKey.getBytes();

			if (bytesKey.length != (keysize / 8)) {
				// エラー処理
				throw new Exception("AES鍵が" + bytesKey.length + "bytesです。("
						+ (keysize / 8) + " bytesじゃないといけません。)");
			}
		}

		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// エラー処理
			throw new HtkBatchError("AESキー生成失敗。", e);
		}
		kgen.init(keysize); // 128, 192, 256 bits

		// Generate the secret key specs.
		SecretKeySpec skeySpec = new SecretKeySpec(bytesKey, "AES");

		// Instantiate the cipher
		try {
			encryptor = Cipher.getInstance("AES");
			encryptor.init(Cipher.ENCRYPT_MODE, skeySpec);

			decryptor = Cipher.getInstance("AES");
			decryptor.init(Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception e) {
			// エラー処理
			throw new Exception("AES暗号化・復号化Util生成失敗。", e);
		}

		skeySpec = null;
		kgen = null;
		bytesKey = null;
	}

	/**
	 * 暗号化
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(final String text) throws Exception {
		if (isBlank(text)) {
			// エラー処理
			throw new RuntimeException("文字列がNULLです。");
		}

		try {
			return encryptor.doFinal(text.getBytes());
		} catch (Exception e) {
			// エラー処理
			throw new Exception("AES暗号化失敗。", e);
		}
	}

	/**
	 * 復号化
	 * 
	 * @param encryptData
	 * @return
	 * @throws Exception
	 */
	public String decrypt(final byte[] encryptData) throws Exception {
		if (encryptData == null) {
			// エラー処理
			throw new RuntimeException("データがNULLです。");
		}

		try {
			return new String(decryptor.doFinal(encryptData));
		} catch (Exception e) {
			// エラー処理
			throw new Exception("AES復号化失敗。", e);
		}
	}
}
