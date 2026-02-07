package io.github.wdpm.maple.db;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author evan
 * @date 2020/4/22
 */
public class MapleDB {
    private static Sql2o sql2o;

    public MapleDB() {
    }

    public static void init(String url, String user, String pass) {
        if (sql2o != null) {
            return;
        }
        sql2o = new Sql2o(url, user, pass);
    }

    public static void init(DataSource dataSource) {
        if (sql2o != null) {
            return;
        }
        sql2o = new Sql2o(dataSource);
    }

    public static <T> T get(String sql, Class<T> clazz) {
        return get(sql, clazz, null);
    }

    public static <T> List<T> getList(String sql, Class<T> clazz) {
        return getList(sql, clazz, null);
    }

    public static Map<String, Object> getMap(String sql) {
        return getMap(sql, null);
    }

    public static List<Map<String, Object>> getMapList(String sql) {
        return getMapList(sql, null);
    }

    /**
     * insert into table1(id,name,age) values(1,"hello",23);
     *
     * @param sql
     * @param params
     * @return
     */
    public static int insert(String sql, Object... params) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(" values (");

        int      start  = sql.indexOf("(") + 1;
        int      end    = sql.indexOf(")");
        String   a      = sql.substring(start, end);
        String[] fields = a.split(",");

        Map<String, Object> map = new HashMap<>();

        int i = 0;
        for (String name : fields) {
            sqlBuilder.append(":" + name.trim() + " ,");
            map.put(name.trim(), params[i]);
            i++;
        }

        String newSql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")";

        Connection con   = sql2o.open();
        Query      query = con.createQuery(newSql);

        addParameters(query, map);

        int res = query.executeUpdate()
                       .getResult();
        con.close();
        return res;
    }

    public static int update(String sql) {
        return update(sql, null);
    }

    public static int update(String sql, Map<String, Object> params) {
        Connection con   = sql2o.open();
        Query      query = con.createQuery(sql);
        addParameters(query, params);
        int res = query.executeUpdate()
                       .getResult();
        con.close();
        return res;
    }

    public static <T> T get(String sql, Class<T> clazz, Map<String, Object> params) {
        // todo use template pattern to avoid duplicate template conn code
        Connection con   = sql2o.open();
        Query      query = con.createQuery(sql);
        addParameters(query, params);
        T t = query.executeAndFetchFirst(clazz);
        con.close();
        return t;
    }

    private static <T> List<T> getList(String sql, Class<T> clazz, Map<String, Object> params) {
        Connection con   = sql2o.open();
        Query      query = con.createQuery(sql);
        addParameters(query, params);
        List<T> list = query.executeAndFetch(clazz);
        con.close();
        return list;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getMap(String sql, Map<String, Object> params) {
        Connection con   = sql2o.open();
        Query      query = con.createQuery(sql);
        addParameters(query, params);
        Map<String, Object> t = (Map<String, Object>) query.executeScalar();
        con.close();
        return t;
    }

    private static List<Map<String, Object>> getMapList(String sql, Map<String, Object> params) {
        Connection con   = sql2o.open();
        Query      query = con.createQuery(sql);
        addParameters(query, params);
        List<Map<String, Object>> t = query.executeAndFetchTable()
                                           .asList();
        con.close();
        return t;
    }

    private static void addParameters(Query query, Map<String, Object> params) {
        if (null != params && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                query.addParameter(key, params.get(key));
            }
        }
    }

}
