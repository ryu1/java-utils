package org.ryu1.utils.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * ネイティブクエリを実行するための DAO が実装するインタフェースです。
 *
 * @author R.Ishitsuka
 */
public interface SQLSupportDAO {
    /**
     * ネイティブクエリ実行によって結果を取得します。
     *
     * @author R.Ishitsuka
     * @param sql
     * @return
     * @throws DataAccessException
     */
    public List select(final String sql) throws DataAccessException;

    /**
     * ネイティブクエリ実行によって結果を取得します。
     *
     * @author R.Ishitsuka
     * @param sql
     *            実行するクエリ文字列
     * @return 結果が格納されたリスト
     * @return
     * @throws DataAccessException
     */
    public List select(final String sql, final Map<String, Object> map)
            throws DataAccessException;

    /**
     * ネイティブクエリ実行によってデータを更新します。
     *
     * @author R.Ishitsuka
     * @param sql
     *            実行するクエリ文字列
     * @return 更新に成功した件数
     * @throws DataAccessException
     */
    public int executeUpdate(final String sql) throws DataAccessException;

    /**
     * ネイティブクエリ実行によってデータを更新します。
     *
     * @author R.Ishitsuka
     * @param sql
     *            実行するクエリ文字列
     * @param map
     *            更新内容が格納された
     * @return 更新に成功した件数
     * @throws DataAccessException
     */
    public int executeUpdate(final String sql, final Map<String, Object> map)
            throws DataAccessException;

    void flushAndClear();
}
