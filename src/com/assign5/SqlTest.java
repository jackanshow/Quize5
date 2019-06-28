package com.assign5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


public class SqlTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<double[]> dataSet = new ArrayList<double[]>();
		
		Connection conn = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite::resource:db/cloudDB.db");
	      //conn = DriverManager.getConnection("jdbc:sqlite:"+"resource/db/xxwDB.db");
	      conn.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = conn.createStatement();
	      String sql = "SELECT * FROM TAITAN";
	      ResultSet rs = stmt.executeQuery(sql);
	      while ( rs.next() ) {
	         
	    	  double surviv = Double.parseDouble("".equals(rs.getString("SURVIVED"))?"0.0":rs.getString("SURVIVED"));
	    	  double age = Double.parseDouble("".equals(rs.getString("AGE"))?"0.0":rs.getString("AGE"));
	    	  double fare = Double.parseDouble("".equals(rs.getString("FARE"))?"0.0":rs.getString("FARE"));
	    	  
	    	  /*if(age==0 || surviv==0 ||fare==0) {
	    		  continue;
	    	  }*/
	    	  
	    	  dataSet.add(new double[] { surviv, age, fare });
	    	  
	    	   	  
	      }
	      rs.close();
	      stmt.close();
	      conn.close();
	    } 
	    catch(SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        }
	    catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    finally{
            // ��������ڹر���Դ�Ŀ�
            try{
                if(stmt!=null)
                stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	    System.out.println("Operation done successfully");
	    
	    
	    //kmeans algorithm
	    KMeansRun kRun =new KMeansRun(3, dataSet);
	    
        Set<Cluster> clusterSet = kRun.run();
        System.out.println("���ε������д�����"+kRun.getIterTimes());
        System.out.println(dataSet.size());
        int[][] count = new int[3][dataSet.size()];
        for (Cluster cluster : clusterSet) {
            //System.out.println(cluster);
        	for (Point point : cluster.getMembers()) {
               // System.out.println("\n"+(point.getId()+1)+" "+point.getClusterid()+" "
        	//+point.getDist()+" "+Arrays.toString(cluster.getCenter().getlocalArray()));
        		count[point.getClusterid()][point.getId()] = 1;
        		//�˴���point.getId()�Ǳ��е�ID-1��
        		System.out.println(point.getId());

            }
        }
        //��һ����������û��û�����
        int[]	cou= {0,0,0};
        for(int i=0;i<count[0].length;i++) {
        	if((count[0][i]==count[1][i])&&(count[1][i]==count[2][i])) {
        		cou[0]++;
        		System.out.println(cou[0]+" "+i);
        	}
        
        }
	    System.out.println(Arrays.toString(cou));
	    System.out.println(count[0].length+" "+count[1].length+" "+count[2].length);
        
        	//��ʾ������0-1310�����ݹ鵽����һ��
        System.out.println(Arrays.toString(count[0]));
        System.out.println(Arrays.toString(count[1]));
        System.out.println(Arrays.toString(count[2]));
        for(int i=0;i<3;i++) {
        	int sum=0;
        	for(int j=0;j<count[i].length;j++) {
        		sum = sum+count[i][j];
        	}
        	System.out.println(sum);
        }
	    
	}

}
