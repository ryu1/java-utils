package org.ryu1.utils.dao;

import java.io.Serializable;
import java.util.List;

import org.slf
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DAOの共通処理を記述します。
 *
 * @author R.Ishitsuka
 */
//@Transactional
public abstract class AbstractCommonDAO {
    // ログ
    private Logger log = LoggerFactory.getLog(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    /**
     * 一レコードを取得します。
     *
     * @author R.Ishitsuka
     * @param clazz
     *            取得対象のクラスタイプ
     * @param pk
     *            キー
     * @return 検索結果
     */
    protected Object getByPK(Class clazz, Serializable pk) {
        log.debug("PK : " + pk);
        return this.sessionFactory.getCurrentSession().get(clazz, pk);
    }

    /**
     * 全検索件数を取得します。
     *
     * @author R.Ishitsuka
     * @param criteria
     *            検索条件
     * @return 全検索件数
     */
    protected long getRowCount(DetachedCriteria detachedCriteria) {

        Criteria criteria = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Long rowCount = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();

        if (rowCount == null) {
        	throw new NullPointerException("Row count is null");
        }

        return (long)rowCount;
    }

    /**
     * テーブルを検索します。
     *
     * @author R.Ishitsuka
     * @param criteria
     *            検索条件
     * @param pageNum
     *            ページナンバー
     * @param pageSize
     *            ページサイズ
     * @return テーブル検索結果
     */
    protected List select(DetachedCriteria detachedCriteria, int pageNum, int pageSize, boolean lockMode) {

    	Criteria criteria = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (log.isDebugEnabled()) {
            log.debug("検索条件 = " + detachedCriteria.toString());
            log.debug("pageNum = " + pageNum);
            log.debug("pageSize = " + pageSize);
        }

        List list = null;
        if (pageNum > 0 && pageSize > 0) {
            int firstResult = (pageNum - 1) * pageSize;
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(pageSize);
            list = criteria.list();
        } else {
        	list = criteria.list();
        }

        /* これは必要ないらしい
        // LockModeが指定されていた場合、行ロックをかける
        if(lockMode) {
            criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
         }
        */

        if (lockMode) {
            for (Object obj : list) {
                sessionFactory.getCurrentSession().buildLockRequest(LockOptions.UPGRADE).lock(obj);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("検索件数 = " + list.size());
        }

        return list;
    }

    /**
     * テーブルを検索します。
     *
     * @author R.Ishitsuka
     * @param criteria
     *            検索条件
     * @param pageNum
     *            ページナンバー
     * @param pageSize
     *            ページサイズ
     * @return テーブル検索結果
     */
    protected List select(DetachedCriteria criteria, int pageNum, int pageSize) {
               return select(criteria, pageNum, pageSize, false);
    }

    /**
     * テーブル登録処理をします。
     *
     * @author R.Ishitsuka
     * @param obj
     *            登録対象
     * @return 成功判断
     */
    protected Serializable insert(Object obj) {
    	return sessionFactory.getCurrentSession().save(obj);
    }

    /**
     * テーブル更新処理をします。
     *
     * @author R.Ishitsuka
     * @param obj
     *            更新対象
     * @return 成功判断
     */
    protected boolean update(Object obj) {
        // HibernateTemplate取得
//        log.debug("HibernateTemplate取得");
//        HibernateTemplate template = getHibernateTemplate();
//        log.debug("HibernateTemplate = [" + template + "]");

        // Update
//    	sessionFactory.getCurrentSession().lock(obj, LockMode.UPGRADE_NOWAIT);
        sessionFactory.getCurrentSession().saveOrUpdate(obj);
//        template.update(obj, LockMode.UPGRADE_NOWAIT);

        return true;
    }

    /**
     * テーブル削除処理をします。
     *
     * @author R.Ishitsuka
     * @param obj
     *            削除対象
     * @return 成功判断
     */
    protected boolean delete(Object obj) {
        // HibernateTemplate取得
//        log.debug("HibernateTemplate取得");
//        HibernateTemplate template = getHibernateTemplate();
//        log.debug("HibernateTemplate = [" + template + "]");

        // DELETE
//        template.delete(obj, LockMode.UPGRADE_NOWAIT);
		sessionFactory.getCurrentSession().delete(obj);

        return true;
    }

    /**
     * シーケンスの取得をします。
     *
     * @author R.Ishitsuka
     * @param String seqName
     * @return シーケンスの値
     */
    protected String getSequence(String seqName) {
//        Long nextVal = (Long)sessionFactory.getCurrentSession().createSQLQuery("SELECT nextval('" + seqName + "')").uniqueResult();

        String nextVal = sessionFactory.getCurrentSession().createSQLQuery("SELECT nextval('" + seqName + "')").uniqueResult().toString();

        if (null == nextVal) {
            throw new NullPointerException("Sequence is null: " + seqName);
        }

        return nextVal;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
