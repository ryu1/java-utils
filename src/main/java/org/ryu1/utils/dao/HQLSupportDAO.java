package org.ryu1.utils.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * HQL 形式のクエリを実行するための DAO が実装するインタフェースです。
 *
 * @author R.Ishitsuka
 */
public interface HQLSupportDAO {
    /**
     * HQL 形式のクエリ実行によって結果を取得します。
     *
     * @author R.Ishitsuka
     * @param hql
     *            実行するクエリ文字列
     * @return 結果が格納されたリスト
     * @throws DataAccessException
     */
    public List select(final String hql) throws DataAccessException;

    /**
     * HQL 形式のクエリ実行によって結果を取得します。
     *
     * @author R.Ishitsuka
     * @param hql
     *            実行するクエリ文字列
     * @param map
     * @return 結果が格納されたリスト
     * @throws DataAccessException
     */
    public List select(final String hql, final Map<String, Object> map)
            throws DataAccessException;

    /**
     * HQL 形式のクエリ実行によってデータを更新します。
     *
     * @author R.Ishitsuka
     * @param hql
     *            実行するクエリ文字列
     * @param map
     *            更新内容が格納された
     * @return 更新に成功した件数
     * @throws DataAccessException
     */
    public int executeUpdate(final String hql, final Map<String, Object> map)
            throws DataAccessException;

    void flushAndClear();
}
