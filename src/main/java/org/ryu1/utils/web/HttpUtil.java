package org.ryu1.utils.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP関連のユーティリティクラス.
 * 
 * @author R.Ishitsuka
 * 
 */
public final class HttpUtil {

    /**
     * logger.
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ヘッダー: Content-Type.
     */
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * HTTPクライアント.
     */
    private final DefaultHttpClient httpClient = new DefaultHttpClient();

    /**
     * JSON形式でリクエスト(GETメソッド)を送信する.
     * 
     * @param queryStrings
     *        QueryStringのリスト
     * @param uri
     *        URI
     * @param headers
     *        ヘッダー
     * @param jsessionId
     *        セッションID
     * @return レスポンスオブジェクト
     * @throws URISyntaxException
     *         URISyntaxException
     * @throws IOException
     *         IOException
     * @throws ClientProtocolException
     *         ClientProtocolException
     */
    public HttpResponse executeGet(final List<QueryString> queryStrings,
            final String uri, final Map<String, String> headers,
            final String jsessionId) throws URISyntaxException,
            ClientProtocolException, IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("Request Post Paramter ----");
            logger.debug(String.format("queryStrings : %s",
                    queryStrings != null ? queryStrings.toString() : ""));
            logger.debug(String.format("URI : %s", uri));
            logger.debug(String.format("headers : %s",
                    headers != null ? headers.toString() : ""));
            logger.debug(String.format("jsessionId : %s", jsessionId));
        }

        // リクエスト情報の設定
        HttpGet httpGet = new HttpGet();

        // リクエストヘッダーの追加
        this.addHeader(httpGet, headers);

        // URIとQueryStringの作成
        String requestUri = this.setQueryString(uri, queryStrings);

        // URLの設定
        httpGet.setURI(new URI(requestUri));

        // SessionIdの設定
        this.setSessionId(httpGet, jsessionId);

        HttpResponse response = httpClient.execute(httpGet);
        ;

        // requestの送信
        // return httpClient.execute(httpGet);
        return response;
    }

    /**
     * JSON形式でリクエスト(PUTメソッド)を送信する.
     * 
     * @param queryStrings
     *        QueryStringのリスト
     * @param dto
     *        Body情報
     * @param uri
     *        URI
     * @param headers
     *        ヘッダー
     * @param jsessionId
     *        セッションID
     * @return レスポンスオブジェクト
     * @throws URISyntaxException
     *         URISyntaxException
     * @throws IOException
     *         IOException
     * @throws JsonMappingException
     *         JsonMappingException
     * @throws JsonGenerationException
     *         JsonGenerationException
     */
    public HttpResponse executePut(final List<QueryString> queryStrings,
            final BaseDto dto, final String uri,
            final Map<String, String> headers, final String jsessionId)
            throws URISyntaxException, JsonGenerationException,
            JsonMappingException, IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("Request Post Paramter ----");
            logger.debug(String.format("queryStrings : %s",
                    queryStrings != null ? queryStrings.toString() : ""));
            logger.debug(String.format("dto : %s",
                    dto != null ? dto.toString() : ""));
            logger.debug(String.format("URI : %s", uri));
            logger.debug(String.format("headers : %s",
                    headers != null ? headers.toString() : ""));
            logger.debug(String.format("jsessionId : %s", jsessionId));
        }

        // リクエスト情報の設定
        HttpPut httpPut = new HttpPut();

        // リクエストヘッダーの追加
        this.addHeader(httpPut, headers);

        // URIとQueryStringの作成
        String requestUri = this.setQueryString(uri, queryStrings);

        // URLの設定
        httpPut.setURI(new URI(requestUri));

        if (dto != null) {
            // リクエストオブジェクトからJSON文字列を生成
            String requestJson = JsonUtil.object2Json(dto);

            // リクエストパラメータの設定
            StringEntity params;
            params = new StringEntity(requestJson, HttpConstants.BASE_CHARSET);

            httpPut.addHeader(HEADER_CONTENT_TYPE,
                    HttpConstants.HEADER_CONTENT_TYPE_VALUE_JSON);
            httpPut.setEntity(params);
        }

        // SessionIdの設定
        this.setSessionId(httpPut, jsessionId);

        // requestの送信
        return httpClient.execute(httpPut);

    }

    /**
     * JSON形式でリクエスト(DELETEメソッド)を送信する.
     * 
     * @param queryStrings
     *        QueryStringのリスト
     * @param uri
     *        URI
     * @param headers
     *        ヘッダー
     * @param jsessionId
     *        セッションID
     * @return レスポンスオブジェクト
     * @throws URISyntaxException
     *         URISyntaxException
     * @throws IOException
     *         IOException
     * @throws ClientProtocolException
     *         ClientProtocolException
     */
    public HttpResponse executeDelete(final List<QueryString> queryStrings,
            final String uri, final Map<String, String> headers,
            final String jsessionId) throws URISyntaxException,
            ClientProtocolException, IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("Request Post Paramter ----");
            logger.debug(String.format("queryStrings : %s",
                    queryStrings != null ? queryStrings.toString() : ""));
            logger.debug(String.format("URI : %s", uri));
            logger.debug(String.format("headers : %s",
                    headers != null ? headers.toString() : ""));
            logger.debug(String.format("jsessionId : %s", jsessionId));
        }

        // リクエスト情報の設定
        HttpDelete httpDelete = new HttpDelete();

        // リクエストヘッダーの追加
        this.addHeader(httpDelete, headers);

        // URIとQueryStringの作成
        String requestUri = this.setQueryString(uri, queryStrings);

        // URLの設定
        httpDelete.setURI(new URI(requestUri));

        // SessionIdの設定
        this.setSessionId(httpDelete, jsessionId);

        // requestの送信
        return httpClient.execute(httpDelete);
    }

    /**
     * JSON形式でリクエスト(POSTメソッド)を送信する.
     * 
     * @param queryStrings
     *        QueryStringのリスト
     * @param dto
     *        Body情報
     * @param uri
     *        URI
     * @param headers
     *        ヘッダー
     * @param jsessionId
     *        jsessionId
     * @return レスポンスオブジェクト
     * @throws IOException
     *         IOException
     * @throws ClientProtocolException
     *         ClientProtocolException
     * @throws URISyntaxException
     *         URISyntaxException
     */
    public HttpResponse executePost(final List<QueryString> queryStrings,
            final BaseDto dto, final String uri,
            final Map<String, String> headers, final String jsessionId)
            throws ClientProtocolException, IOException, URISyntaxException {

        if (logger.isDebugEnabled()) {
            logger.debug("Request Post Paramter ----");
            logger.debug(String.format("queryStrings : %s",
                    queryStrings != null ? queryStrings.toString() : ""));
            logger.debug(String.format("dto : %s",
                    dto != null ? dto.toString() : ""));
            logger.debug(String.format("URI : %s", uri));
            logger.debug(String.format("headers : %s",
                    headers != null ? headers.toString() : ""));
            logger.debug(String.format("jsessionId : %s", jsessionId));
        }

        // リクエスト情報の設定
        HttpPost httpPost = new HttpPost();

        // リクエストヘッダーの追加
        this.addHeader(httpPost, headers);

        // URIとQueryStringの作成
        String requestUri = this.setQueryString(uri, queryStrings);

        // URLの設定
        httpPost.setURI(new URI(requestUri));

        if (dto != null) {
            // リクエストオブジェクトからJSON文字列を生成
            String requestJson = JsonUtil.object2Json(dto);

            // リクエストパラメータの設定
            StringEntity params;
            params =
                    new StringEntity(requestJson, StringConstants.BASE_CHARSET);

            httpPost.addHeader(HEADER_CONTENT_TYPE,
                    HttpConstants.HEADER_CONTENT_TYPE_VALUE_JSON);
            httpPost.setEntity(params);
        }

        // SessionIdの設定
        this.setSessionId(httpPost, jsessionId);

        // requestの送信
        return httpClient.execute(httpPost);
    }

    /**
     * SessionIdの設定.
     * 
     * @param requestBase
     *        リクエストメソッド
     * @param jsessionId
     *        CloudManagerのセッションID
     */
    private void setSessionId(final HttpRequestBase requestBase,
            final String jsessionId) {
        // NULLチェック
        if (StringUtils.isEmpty(jsessionId)) {
            return;
        }
        requestBase.addHeader(HttpConstants.REQUEST_HEADER_COOKIE,
                HttpConstants.REQUEST_COOKIE_JSESSIONID + "=" + jsessionId);
    }

    /**
     * プロキシ設定.
     * 
     * @param isProxy
     *        プロキシを利用するかどうか?
     * @param httpHost
     *        HttpHostクラス.
     */
    private void setProxy(final boolean isProxy, final HttpHost httpHost) {
        // Proxy設定の要否を切り替えれるようにする
        if (isProxy) {
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                    httpHost);
        }
    }

    /**
     * プロキシ設定.
     * 
     * @param isProxy
     *        プロキシを利用するかどうか?
     * @param proxyHost
     *        プロキシのホスト名
     * @param proxyPort
     *        プロキシのポート番号
     * @param proxySheme
     *        プロキシのスキーマ
     */
    public void setProxy(final boolean isProxy, final String proxyHost,
            final int proxyPort, final String proxySheme) {
        HttpHost httpHost = new HttpHost(proxyHost, proxyPort, proxySheme);
        this.setProxy(isProxy, httpHost);
    }

    /**
     * プロキシ設定.
     * 
     * @param isProxy
     *        プロキシを利用するかどうか?
     * @param proxyHost
     *        プロキシのホスト名
     * @param proxyPort
     *        プロキシのポート番号
     * @param proxySheme
     *        プロキシのスキーマ
     * @param userName
     *        プロキシのユーザ名
     * @param password
     *        プロキシのパスワード
     */
    public void setProxy(final boolean isProxy, final String proxyHost,
            final int proxyPort, final String proxySheme,
            final String userName, final String password) {
        if (isProxy) {
            httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(proxyHost, proxyPort),
                    new UsernamePasswordCredentials(userName, password));
        }
        HttpHost httpHost = new HttpHost(proxyHost, proxyPort, proxySheme);
        this.setProxy(isProxy, httpHost);
    }

    /**
     * リクエストにヘッダーを追加する.
     * 
     * @param httpRequestBase
     *        リクエスト
     * @param headers
     *        ヘッダー
     */
    private void addHeader(final HttpRequestBase httpRequestBase,
            final Map<String, String> headers) {
        // ヘッダーが設定されていない場合は、何もしない
        if (headers == null || headers.size() == 0) {
            return;
        }
        // ヘッダーを追加
        for (Entry<String, String> entry : headers.entrySet()) {
            httpRequestBase.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * URIにQueryStringを付与する.
     * 
     * @param uri
     *        URI
     * @param queryStrings
     *        QueryStringのリスト
     * @return QueryString付きURI
     */
    private String setQueryString(final String uri,
            final List<QueryString> queryStrings) {

        if (queryStrings == null || queryStrings.size() == 0) {
            return uri;
        }

        // 結果用URI
        String addedUri = uri;

        for (int i = 0; i < queryStrings.size(); i++) {
            if (i == 0) {
                addedUri += HttpConstants.SIGN_QST;
            }
            addedUri +=
                    queryStrings.get(i).getKey() + HttpConstants.SIGN_EQL
                            + queryStrings.get(i).getValue();
            if (i < queryStrings.size() - 1) {
                addedUri += HttpConstants.SIGN_AMP;
            }
        }
        return addedUri;
    }
}
