package com.chenfu.dome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class InsertThread extends Thread {

    private String sql_data;

    private String sql_insert;

    private Connection conB;

    private Connection conA;

    static  int batchSize = 2500;

    // 线程计数器  
    static private int threadCounts;  
    // 线程名称池  
    static private String threadNames[];  

    static {  
        // 假设这里允许系统同时运行最大线程数为10个  
        int maxThreadCounts = 5;  
        threadNames = new String[maxThreadCounts];  
        // 初始化线程名称池  
        for (int i = 1; i <= maxThreadCounts; i++) {  
            threadNames[i - 1] = "子线程_" + i;  
        }  
    }  

    public InsertThread() {  
        // 临界资源锁定  
        synchronized (InsertThread.class) {  
            // 线程总数加1  
            threadCounts++;  
            // 从线程名称池中取出一个未使用的线程名  
            for (int i = 0; i < threadNames.length; i++) {  
                if (threadNames[i] != null) {  
                    String temp = threadNames[i];  
                    // 名被占用后清空  
                    threadNames[i] = null;  
                    // 初始化线程名称  
                    this.setName(temp);  
                    break;  
                }  
            }  
        }  
    }  

    public void run() {  
        try {
            Long start = System.currentTimeMillis();
            Statement stmt_source = conA.createStatement();
            ResultSet rs_sql_data = stmt_source.executeQuery(sql_data);
            ResultSetMetaData rsmd = rs_sql_data.getMetaData();
            PreparedStatement ps = conB.prepareStatement(sql_insert);
            int columnCount=rsmd.getColumnCount();
            int count=1;
            while (rs_sql_data.next()) {
                count++;
                for(int k=1;k<=columnCount;k++){
                  ps.setString(k, rs_sql_data.getString(k));
                }                
                ps.addBatch();
                if(count % batchSize == 0) {
                    ps.executeBatch();
                    conB.commit();
                }
            }
            ps.executeBatch();
            conB.commit();
            Long end = System.currentTimeMillis();
            System.out.println(this.getName()+",耗时："+(end-start)/1000 + "秒");
            stmt_source.close();
            rs_sql_data.close();
            ps.close();
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            synchronized (InsertThread.class) {  

                // 释放线程名称  
                String[] threadName = this.getName().split("_");  
                // 线程名使用完后放入名称池  
                threadNames[Integer.parseInt(threadName[1]) - 1] = this.getName();  

                // 线程运行完毕后减1  
                threadCounts--;  
                /* 
                 * 通知其他被阻塞的线程，但如果其他线程要执行，则该同步块一定要运行结束（即直 
                 * 到释放占的锁），其他线程才有机会执行，所以这里的只是唤醒在此对象监视器上等待 
                 * 的所有线程，让他们从等待池中进入对象锁池队列中，而这些线程重新运行时它们一定 
                 * 要先要得该锁后才可能执行，这里的notifyAll是不会释放锁的，试着把下面的睡眠语 
                 * 句注释去掉，即使你已调用了notify方法，发现CreateThread中的同步块还是好 
                 * 像一直处于对象等待状态，其实调用notify方法后，CreateThread线程进入了对象锁 
                 * 池队列中了，只要它一获取到锁，CreateThread所在线程就会真真的被唤醒并运行。 
                 */  
                InsertThread.class.notifyAll();  

                System.out.println("----" + this.getName() + " 所占用资源释放完毕，当前系统正在运行的子线程数："  
                        + threadCounts);  
            }  
        }  
    }  


    static public int getThreadCounts() {  
        synchronized (InsertThread.class) {  
            return threadCounts;  
        }  
    }

    public InsertThread(String sql_data, String sql_insert, Connection conB, Connection conA) {
        super();
        this.sql_data = sql_data;
        this.sql_insert = sql_insert;
        this.conB = conB;
        this.conA = conA;
        // 临界资源锁定  
        synchronized (InsertThread.class) {  
            // 线程总数加1  
            threadCounts++;  
            // 从线程名称池中取出一个未使用的线程名  
            for (int i = 0; i < threadNames.length; i++) {  
                if (threadNames[i] != null) {  
                    String temp = threadNames[i];  
                    // 名被占用后清空  
                    threadNames[i] = null;  
                    // 初始化线程名称  
                    this.setName(temp);  
                    break;  
                }  
            }  
        }  
    }





}