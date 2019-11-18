package com.along.outboundmanage.utill;

import com.along.outboundmanage.model.HisGpsData;
import org.apache.commons.dbcp2.BasicDataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;

	private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

	private JdbcUtils() {
	}

	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		/*Properties conf = loadProps("dbconfig.properties");
		String driverClass = conf.getProperty("jdbc.driverClass");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");*/
		DATA_SOURCE.setDriverClassName("com.mysql.jdbc.Driver");
		DATA_SOURCE.setUrl("jdbc:mysql://120.77.252.208:3306/outboundmanage?useUnicode=true&characterEncoding=utf8&useSSL=false" +
				"&useCursorFetch=true&rewriteBatchedStatements=true&useServerPrepStmts=true&cachePrepStmts=true&autoReconnect=true");
		DATA_SOURCE.setUsername("root");
		DATA_SOURCE.setPassword("123456");


	}
	private static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}

	//资源的释放
	private static void release(Connection conn, PreparedStatement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				rs = null;
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				st = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
				CONNECTION_HOLDER.remove();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				conn = null;
			}
		}
	}
	public static void query(String sql){
		Connection conn = getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			System.out.println("start.....");
			prepareStatement = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			prepareStatement.setFetchSize(Integer.MIN_VALUE);
			result = prepareStatement.executeQuery();
			int i=0;
			while (result.next()) {
				i++;
			}
			System.out.println("end....."+i);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(conn, prepareStatement, result);
		}
	}


	/**
	 * @author PQF
	 * @category 根据SQL进行查询返回一个List实体集合
	 * @param sql 传送一个查询的sql语句
	 * @param list sql中使用占位符(?)所对应的实际值
	 * @param column 你要查询的列
	 * @throws Exception
	 */
	public static List<Map<String, String>> queryEntityList(String sql,
	                                                        List<String> list, String[] column) throws Exception {
		List<Map<String, String>> entityList = new ArrayList<>();
		if (sql.isEmpty() || column.length < 1) {
			throw new Exception("SQL语句不能为空,并且列的长度不能小于1");
		}
		Connection conn = getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			prepareStatement.setFetchSize(Integer.MIN_VALUE);
			prepareStatement.setFetchDirection(ResultSet.FETCH_REVERSE);

			if(ObjectNotNull(list)){
				for (int i = 1; i <= list.size(); i++) {
					prepareStatement.setString(i, list.get(i-1));
				}
			}
			long startTime = System.currentTimeMillis();//获取当前时间
			result = prepareStatement.executeQuery();
			Map<String, String> mapList = null;
			long endTime = System.currentTimeMillis();//获取当前时间
			System.out.println("时间1111 ："+(endTime-startTime)+"ms");
			while (result.next()) {
				mapList = new HashMap<>();
				for (Object col : column) {
					mapList.put((String) col, result.getString((String) col));
				}
				entityList.add(mapList);
			}
			long endTime2 = System.currentTimeMillis();//获取当前时间
			System.out.println(" 时间："+(endTime2-endTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(conn, prepareStatement, result);
		}
		return entityList;
	}
	public static boolean ObjectNotNull(Object object){
		return object != null && !"".equals(object) && !object.equals("null") ? true : false;
	}


	/**
	 * 查询历史轨迹数据(流式查询本地测试查询10w条数据不超过1s,表分区;服务器查询依旧慢)
	 * @param sql
	 * @param list  参数
	 * @param column 字段名
	 * @return
	 * @throws Exception
	 */
	public static Map<String, HisGpsData>  queryHisGpsList(String sql,
	                                               List<String> list, String[] column) throws Exception {
		Map<String, HisGpsData> resultMap = new HashMap<>();
		if (sql.isEmpty() || column.length < 1) {
			throw new Exception("SQL语句不能为空,并且列的长度不能小于1");
		}
		Connection conn = getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		//System.out.println(sql);
		try {
			prepareStatement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//prepareStatement.setFetchSize(Integer.MIN_VALUE);
			prepareStatement.setFetchSize(500);
			prepareStatement.setFetchDirection(ResultSet.FETCH_REVERSE);
			if(ObjectNotNull(list)){
				for (int i = 1; i <= list.size(); i++) {
					prepareStatement.setString(i, list.get(i-1));
				}
			}
			result = prepareStatement.executeQuery();
			List<BigDecimal[]>gpslist=null;
			HisGpsData hisGpsData= new HisGpsData();
			String equipCard=null;
			while (result.next()) {
				if(resultMap.get(equipCard=result.getString("equipCard"))!=null){
					resultMap.get(equipCard).getGpsData().add(new BigDecimal[]{result.getBigDecimal("longitude"),result.getBigDecimal("latitude")});
				}else{//首次加载
					gpslist=new ArrayList<>();
					gpslist.add(new BigDecimal[]{result.getBigDecimal("longitude"),result.getBigDecimal("latitude")});
					resultMap.put(result.getString("equipCard"),new HisGpsData.Builder().color(result.getString("color"))
							.equipCard(result.getString("equipCard"))
							.prisoner(result.getString("prisoner"))
							.police(result.getString("police"))
							.gpsData(gpslist).build()
					);
				}


			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(conn, prepareStatement, result);
		}

		return resultMap;
	}


}
