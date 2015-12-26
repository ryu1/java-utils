package org.ryu1.utils.web;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.Security;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 石塚隆一
 */
public abstract class AbstractTelegrapher {

    private PostMethod post;
    
    private HttpClient client;

    protected Log log = LogFactory.getLog(this.getClass());
    
    public static final int RETRY_COUNT_DEFALUT = 3;
    
    public static final int TIMEOUT_DEFAULT = 1000 * 30;
    
    protected Integer retryCount;
    
//    private TelegraphRequest request;
    
    /**
     * インスタンスを生成する.
     * @param requestURL
     * @param request
     * @param charset
     * @param timeout
     * @param retryCount
     */
    public AbstractTelegrapher(String requestURL, Integer timeout, Integer retryCount) {
        if (StringUtils.isEmpty(requestURL)) {
            throw new IllegalArgumentException();
        }
//        this.requestURL = requestURL;
        this.client = new HttpClient();
        this.post = new PostMethod(requestURL);
//        this.request = request;
        
        HttpMethodParams methodParams = new HttpMethodParams();
        if (timeout == null) {
            methodParams.setSoTimeout(TIMEOUT_DEFAULT); // 30秒
        } else {
            methodParams.setSoTimeout(timeout);
        }

        post.setParams(methodParams);
        
        if (retryCount == null) {
            this.retryCount = RETRY_COUNT_DEFALUT;
        } else {
            this.retryCount = retryCount;
        }

        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(retryCount, false));

        post.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=shift_jis");
    }
    
    /**
     * SSLの設定をする.
     * @param path
     */
    @SuppressWarnings("restriction")
    public void setSSL(String path) {
        String cacerts = path;
        System.setProperty("javax.net.ssl.trustStore", cacerts);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("java.protocol.handler.pkgs",
                "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }

    /**
     * 送信する.
     * @return
     * @throws Exception
     */
    public TelegraphResponse send(TelegraphRequest request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        NameValuePair[] params = build(request);
        post.setRequestBody(params);
        try {
            int statusCode = client.executeMethod(post);

            if (statusCode != HttpStatus.SC_OK) {
                throw new IllegalHttpStatusException(this.getClass(),
                        "HTTP Client {statusCode=" + statusCode + "}");
            }
            
            String resStr = post.getResponseBodyAsString();
            return parse(resStr);
        }
        catch (ConnectException e) {
            log.fatal(e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.fatal(e);
            throw e;
        } catch (IllegalHttpStatusException e) {
            log.fatal(e);
            throw e;
        } catch (HttpException e) {
            log.fatal(e);
            throw e;
        } catch (IOException e) {
            log.fatal(e);
            throw e;
        } finally {
            post.releaseConnection();
        }
    }

    /**
     * リクエスト電文を作成する.
     * @param request
     * @return
     */
    protected abstract NameValuePair[] build(TelegraphRequest request);

    /**
     * レスポンス電文をパースする.
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract TelegraphResponse parse(String response) throws Exception;

    /**
     * リクエスト.
     * @author
     * 
     */
    public interface TelegraphRequest {
        
        public String toString();
    }

    /**
     * レスポンス.
     * @author
     */
    public interface TelegraphResponse {
        public String toString();
    }
}
