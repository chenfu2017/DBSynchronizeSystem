package com.chenfu.dome;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public class SynchronizationController{
    //同步源
    static  String url_source="jdbc:mysql://localhost:3306/count?user=root&password=root&useUnicode=true&characterEncoding=UTF8";
    //目标库
    static String url_destination="jdbc:mysql://localhost:3306/count2?user=root&password=root&useUnicode=true&characterEncoding=UTF8";

    static Connection conn_source = null;

    static Connection conn_destination = null;

    static String sql_read;

    static String sql_insert;

    static final int batchSize = 15000;

    static final int max_thread_size=5;

    public static void init(){

    }

    public static void writeData(){

    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
             Class.forName("com.mysql.jdbc.Driver");
             conn_source = DriverManager.getConnection("jdbc:mysql://localhost:3306/count","root","root");
             conn_destination= DriverManager.getConnection("jdbc:mysql://localhost:3306/count2","root","root");
             conn_destination.setAutoCommit(false); 
             synchronizationTables(conn_source, conn_destination);
             addData(conn_source, conn_destination);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
                while(true){
                    if(InsertThread.getThreadCounts()>0){
                        Thread.sleep(1000);
                    }else{
                        break;
                    }
                }
            conn_source.close();
            conn_destination.close();
        }

    }



    //本地获取表名获取表名
    public static Set<String> getTableName(Connection con) {
        Set<String> set = new HashSet<String>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,new String[] { "TABLE" });
            while (rs.next()) {
                  set.add(rs.getString("TABLE_NAME"));
//                String s = rs.getString("TABLE_NAME");
//                String type = rs.getString("TABLE_TYPE");
//                System.out.println(s+"======"+type);
//                getTableDDL(rs.getString("TABLE_NAME"), con);
            }

        } catch (Exception e) {           
            e.printStackTrace();
        }
        return set;
    }
    //目标数据库
    public static Map<String,String> getTableNameToMap(Connection con) {
        Map<String,String> map=new HashMap<String,String>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,new String[] { "TABLE" });
            while (rs.next()) {
                map.put(rs.getString("TABLE_NAME"),"1");
            }

        } catch (Exception e) {           
            e.printStackTrace();
        }
        return map;
    }

    //创建表
    public static void createTable(String sql_ddl) throws SQLException {
            Statement stmt = conn_destination.createStatement();
            int result = stmt.executeUpdate(sql_ddl);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result != -1) {
                System.out.println("表创建成功");
            }else{
                System.out.println("表创建失败："+sql_ddl);
            }
    }

    //创建sql
    public static String getTableField(String tableName,Connection con) throws SQLException{
           String sql = "select * from "+tableName;
           Statement state = con.createStatement();
           ResultSet rs = state.executeQuery(sql);
           ResultSetMetaData rsd = rs.getMetaData() ;
           StringBuffer sql_model=new StringBuffer("insert into "+ tableName +" (");
           StringBuffer sql_param=new StringBuffer(" VALUES(");
           for(int i = 1; i <= rsd.getColumnCount(); i++) {  
               sql_model.append(rsd.getColumnName(i));
               sql_param.append("?");
               if (i < rsd.getColumnCount()) {
                   sql_model.append(",");
                   sql_param.append(",");
               }
              }
           sql_model.append(") ");sql_param.append(") ");
           System.out.println(sql_model.toString()+sql_param.toString());
          return sql_model.toString()+sql_param.toString();
    }

    public static void getTableField2(String tableName,Connection conn) throws SQLException{

            ResultSet rs = conn.getMetaData().getColumns(null, conn.getMetaData().getUserName(),tableName.toUpperCase(), "%");

            while(rs.next()){  
                String colName = rs.getString("COLUMN_NAME"); 
                String remarks = rs.getString("REMARKS");  
                String dbType = rs.getString("TYPE_NAME");  

                System.out.println(colName+","+remarks+","+dbType);
            }
    }

    //获取表结构ddl
    public static String getTableDDL(String tableName,Connection conn) throws SQLException{
        ResultSet rs = null;
        PreparedStatement ps = null;
        ps = conn.prepareStatement("show create table "+tableName);
        rs = ps.executeQuery();
        StringBuffer ddl=new StringBuffer(); 
        while (rs.next()) {
            ddl.append(rs.getString(rs.getMetaData().getColumnName(2)));
        }
        return ddl.toString();
    }

    /**
     * 检查本地库所有表在B库里是否存在，是否一致
     * A本地库  B目标库
     */
    public static void synchronizationTables(Connection conA,Connection conB) throws SQLException{
        Set<String> a_set=getTableName(conA);
        Map<String,String> b_map=getTableNameToMap(conB);

        Iterator<String> it=a_set.iterator();
        while(it.hasNext()){
            String n=it.next();
            if(b_map.get(n)==null){
                System.out.println("表名："+n+"   不在目标库里");
                String create_table_ddl=getTableDDL(n, conA);
                createTable(create_table_ddl);
            }
        }
    }

    //清楚表数据
    public static boolean clearTableData(String tableName,Connection con){
        try {
            Statement stmt = con.createStatement();
            String sql = "TRUNCATE TABLE  "+tableName;
            stmt.executeUpdate(sql);
            System.out.println(tableName+":表数据已被清空");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常表："+tableName+"----数据清空失败");
            return false;
        }
        return true;
    }


    public static void addData(Connection conA,Connection conB) throws SQLException, InterruptedException{
        Statement stmt_source = conA.createStatement();
         Set<String> tableNameSet=getTableName(conn_source);
         Iterator<String> it = tableNameSet.iterator(); 
         //遍历表
         while (it.hasNext()) {  
              long start = System.currentTimeMillis();
              String str = it.next();  
              if(!clearTableData(str, conB)){
                  continue;
              }
              while(true){
                  if(InsertThread.getThreadCounts()>0){
                      Thread.sleep(3000);
                  }else{
                      break;
                  }
              }
              String sql_insert=getTableField(str, conA);
              //获取总条数 分页查询
              String sql_count="select count(*) from "+ str;
              ResultSet rs = stmt_source.executeQuery(sql_count);
              rs.next();
              int totalCount=rs.getInt(1);
              if(totalCount>batchSize){
                  int max=totalCount%batchSize==0 ? totalCount/batchSize : totalCount/batchSize+1;
                  for(int i=0;i<max;i++){
                      synchronized (InsertThread.class) { 
                          String sql_data="select * from "+str+" limit "+ i*batchSize + " , "+batchSize;
                          int tCount = InsertThread.getThreadCounts();  
                          while (tCount >= max_thread_size) {  
                                System.out.println("系统当前线程数为：" + tCount+ "，已达到最大线程数 "+max_thread_size+"，请等待其他线程执行完毕并释放系统资源");  
                                InsertThread.class.wait();  
                                tCount = InsertThread.getThreadCounts();  
                            }  
                            // 重新启动一个子线程  
                            Thread td = new InsertThread(sql_data, sql_insert, conB, conA);
                            td.start();  
                            System.out.println("已创建新的子线程: " + td.getName());  
                      }      
                  }
              }else{
                  String sql_data="select * from "+str;
                  Thread td = new InsertThread(sql_data, sql_insert, conB, conA);
                  td.start();  
              }
              long end = System.currentTimeMillis();
              System.out.println(str+"  表数据导入完成,耗时："+(end-start)/1000+"秒,"+(end-start)/60000+"分钟");

         }
    }

}