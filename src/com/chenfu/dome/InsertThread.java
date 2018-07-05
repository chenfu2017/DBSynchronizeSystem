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

    // �̼߳�����  
    static private int threadCounts;  
    // �߳����Ƴ�  
    static private String threadNames[];  

    static {  
        // ������������ϵͳͬʱ��������߳���Ϊ10��  
        int maxThreadCounts = 5;  
        threadNames = new String[maxThreadCounts];  
        // ��ʼ���߳����Ƴ�  
        for (int i = 1; i <= maxThreadCounts; i++) {  
            threadNames[i - 1] = "���߳�_" + i;  
        }  
    }  

    public InsertThread() {  
        // �ٽ���Դ����  
        synchronized (InsertThread.class) {  
            // �߳�������1  
            threadCounts++;  
            // ���߳����Ƴ���ȡ��һ��δʹ�õ��߳���  
            for (int i = 0; i < threadNames.length; i++) {  
                if (threadNames[i] != null) {  
                    String temp = threadNames[i];  
                    // ����ռ�ú����  
                    threadNames[i] = null;  
                    // ��ʼ���߳�����  
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
            System.out.println(this.getName()+",��ʱ��"+(end-start)/1000 + "��");
            stmt_source.close();
            rs_sql_data.close();
            ps.close();
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            synchronized (InsertThread.class) {  

                // �ͷ��߳�����  
                String[] threadName = this.getName().split("_");  
                // �߳���ʹ�����������Ƴ�  
                threadNames[Integer.parseInt(threadName[1]) - 1] = this.getName();  

                // �߳�������Ϻ��1  
                threadCounts--;  
                /* 
                 * ֪ͨ�������������̣߳�����������߳�Ҫִ�У����ͬ����һ��Ҫ���н�������ֱ 
                 * ���ͷ�ռ�������������̲߳��л���ִ�У����������ֻ�ǻ����ڴ˶���������ϵȴ� 
                 * �������̣߳������Ǵӵȴ����н���������ض����У�����Щ�߳���������ʱ����һ�� 
                 * Ҫ��Ҫ�ø�����ſ���ִ�У������notifyAll�ǲ����ͷ����ģ����Ű������˯���� 
                 * ��ע��ȥ������ʹ���ѵ�����notify����������CreateThread�е�ͬ���黹�Ǻ� 
                 * ��һֱ���ڶ���ȴ�״̬����ʵ����notify������CreateThread�߳̽����˶����� 
                 * �ض������ˣ�ֻҪ��һ��ȡ������CreateThread�����߳̾ͻ�����ı����Ѳ����С� 
                 */  
                InsertThread.class.notifyAll();  

                System.out.println("----" + this.getName() + " ��ռ����Դ�ͷ���ϣ���ǰϵͳ�������е����߳�����"  
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
        // �ٽ���Դ����  
        synchronized (InsertThread.class) {  
            // �߳�������1  
            threadCounts++;  
            // ���߳����Ƴ���ȡ��һ��δʹ�õ��߳���  
            for (int i = 0; i < threadNames.length; i++) {  
                if (threadNames[i] != null) {  
                    String temp = threadNames[i];  
                    // ����ռ�ú����  
                    threadNames[i] = null;  
                    // ��ʼ���߳�����  
                    this.setName(temp);  
                    break;  
                }  
            }  
        }  
    }





}