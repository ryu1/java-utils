package org.ryu1.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 暗号化文字列(SHA-1やMD5アルゴリズムによるハッシュ値)を生成したり、暗号化文字列の比較をするクラスです。
 *
 * <p>
 * 暗号化文字列は次の通信時に利用されます。
 * <ul>
 * <li>morawinの各サブシステム(サーバ)間での通信時(SHA-1)
 * <li>NETCASHからmorawinシステムに戻ってくるときの通信時(MD5)
 * </ul>
 * 基本的にこのクラスはサブシステム間での通信に特化した実装となっているので、SHA-1をデフォルトアルゴリズムとして設定しています。<u>NETCASHとの通信の時にはアルゴリズム及び共通パスワード(秘密文字列)を指定する必要があります</u>ので注意してください。
 * </p>
 *
 * @author R.Ishitsuka
 * @version 1.1
 */
public class SignatureManager {

    /** 利用するアルゴリズムをSHA-1とすることを指定するための定数です。 */
    public static final String ALGORITHM_SHA1 = "SHA1";

    /** 利用するアルゴリズムをMD5とすることを指定するための定数です。 */
    public static final String ALGORITHM_MD5 = "MD5";

    /** メッセージダイジェストするときのデフォルトアルゴリズム */
    private static final String defaultAlgorithm = ALGORITHM_SHA1;

    /** 暗号化文字列(ハッシュ値)を生成するときのサフィックスとして利用される秘密文字列 */
    private static final String defaultKeyString = "password2";

    /**
     * 与えられた文字列よりハッシュ値を生成します。
     *
     * <p>
     * キー情報及び暗号化アルゴリズムはデフォルト値が使用されます。詳細については
     * {@link #digest(String, String, String)} を参照してください。
     * </p>
     *
     * @param data
     *            暗号化文字列を生成する元データ
     * @return 生成された暗号化文字列(ハッシュ値)
     * @throws IllegalParameterException
     *             引数として渡された文字列が不正なときにスローされます。
     * @throws NoSuchAlgorithmException
     *             アルゴリズムの指定は内部的に行っているのでこの<code>NoSuchAlgorithmException</code>が発生することはありえません。
     * @see #digest(String, String)
     * @see #digest(String, String, String)
     */
    public static String digest(String data) throws IllegalParameterException,
            NoSuchAlgorithmException {
        return digest(data, defaultKeyString, defaultAlgorithm);
    }

    /**
     * 与えられた文字列、及びキー情報を元に指定アルゴリズムによって暗号化された文字列(ハッシュ値)を生成します。
     *
     * @author R.Ishitsuka
     * @param data
     *            暗号化文字列を生成する元データ
     * @param keyString
     *            暗号化に用いる鍵文字列
     * @param algorithm
     *            暗号化に用いるアルゴリズム
     *            <p>
     *            このクラスの定数である {@link SignatureManager#ALGORITHM_SHA1} または
     *            {@link SignatureManager#ALGORITHM_MD5} を指定します。
     *            </p>
     * @return 生成された暗号化文字列(ハッシュ値)
     * @throws IllegalParameterException
     *             引数として渡された文字列が不正なときにスローされます。
     * @throws NoSuchAlgorithmException
     *             アルゴリズムの指定は内部的に行っているのでこの<code>NoSuchAlgorithmException</code>が発生することはありえません。
     */
    public static String digest(String data, String keyString, String algorithm)
            throws IllegalParameterException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalParameterException(
                    "The parameter must be valid argument.");
        }

        // アルゴリズムを指定してメッセージダイジェストインスタンスを取得する。
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] dat = (data + keyString).getBytes();
        md.update(dat);
        byte[] digest = md.digest();

        return StringUtils.bytes2HexString(digest);
    }

    /**
     * 第1引数に渡される文字列の妥当性と等価性をチェックします。
     *
     * <p>
     * 詳細の説明は {@link #isValid(String, String, String, String)} を参照してください。
     * </p>
     *
     * @author R.Ishitsuka
     * @see SignatureManager#isValid(String, String, String)
     */
    public static boolean isValid(String data, String signature)
            throws IllegalParameterException, NoSuchAlgorithmException {
        return isValid(data, signature, defaultKeyString, defaultAlgorithm);
    }

    /**
     * 第1引数に渡される文字列の妥当性と等価性をチェックします。
     *
     * <p>
     * サーバ間通信においては送信元のデータが改竄されていないこと、また信頼された送信元から送られてきていることを確認するために次のような処理がされています。
     * </p>
     * <ol>
     * <li>送信元サーバでは「送信したい文字列」よりSHA-1ハッシュ値を求めます。</li>
     * <li>「送信したい文字列」と「ハッシュ値」の2つを別サーバにリクエストパラメータとして送ります。</li>
     * <li>受信サーバでは「受信した文字列」よりSHA-1ハッシュ値を求めます。</li>
     * <li>送られてきた「ハッシュ値」と算出した「ハッシュ値」の等価性をチェックします。</li>
     * </ol>
     *
     * @author R.Ishitsuka
     * @param data
     *            暗号化文字列を生成する元データ
     * @param signature
     *            第1引数(<code>data</code>)で渡される文字列を元に作成された暗号化文字列です。この文字列は別サーバで生成されており、<code>data</code>と<code>signature</code>から等価性のチェックを行うために必要です。
     * @param keyString
     *            暗号化に用いる鍵文字列
     * @param algorithm
     *            暗号化に用いるアルゴリズム
     *            <p>
     *            このクラスの定数である {@link SignatureManager#ALGORITHM_SHA1} または
     *            {@link SignatureManager#ALGORITHM_MD5} を指定します。
     *            </p>
     * @return <code>data</code>の内容が改竄されている恐れがある場合に<code>false</code>を、正しいと思われる場合に<code>true</code>を返します。
     * @throws IllegalParameterException
     *             引数として渡された文字列が不正なときにスローされます。
     * @throws NoSuchAlgorithmException
     *             アルゴリズムの指定は内部的に行っているのでこの<code>NoSuchAlgorithmException</code>が発生することはありえません。
     */
    public static boolean isValid(String data, String signature,
            String keyString, String algorithm)
            throws IllegalParameterException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(data) || StringUtils.isEmpty(signature)
                || StringUtils.isEmpty(keyString)) {
            throw new IllegalParameterException(
                    "The parameter must be valid argument.");
        }
        return signature.equals(digest(data, keyString, algorithm));
    }

}
