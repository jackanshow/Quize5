package com.assign5;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Servlet implementation class Q8Chart
 */
@WebServlet("/Q8Chart")
public class Q8Chart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Q8Chart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("接收到get请求");
		Integer clusteee = Integer.parseInt(request.getParameter("clust"));
		String att1 = request.getParameter("att1");
		String att2 = request.getParameter("att2");
		Double att1N1 = Double.parseDouble(request.getParameter("att1Num1"));
		Double att1N2 = Double.parseDouble(request.getParameter("att1Num2"));
		Double att2N1 = Double.parseDouble(request.getParameter("att2Num1"));
		Double att2N2 = Double.parseDouble(request.getParameter("att2Num2"));
		
		
						
		long timeBegin = Clock.systemDefaultZone().millis();
		
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
	      String sql = "SELECT * FROM MINNOW";
	      ResultSet rs = stmt.executeQuery(sql);
	      while ( rs.next() ) {
	         
	    	  double atte1 = Double.parseDouble("".equals(rs.getString(att1))?"0.0":rs.getString(att1));
	    	  double atte2 = Double.parseDouble("".equals(rs.getString(att2))?"0.0":rs.getString(att2));
	    	  
	    	  
	    	  /*if(atte1==0 || atte2==0) {
	    		  continue;
	    	  }*/
	    	  
	    	  if(atte1<att1N1 || atte1>att1N2 ||atte2<att2N1||atte2>att2N2 ) {
	    		  continue;
	    	  }
	    	  
	    	  //dataSet.add(new double[] { surviv, age, fare });
	    	  dataSet.add(new double[] { atte1, atte2});
	    	   	  
	      }
	      rs.close();
	      stmt.close();
	      conn.close();
	    } 
	    catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
	    catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    finally{
            // 最后是用于关闭资源的块
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
	    
	    
	    
	    int[] countC = new int[clusteee];
	    DefaultCategoryDataset ds = new DefaultCategoryDataset(); 
	    //kmeans algorithm
	    KMeansRun kRun =new KMeansRun(clusteee, dataSet);
	    
        Set<Cluster> clusterSet = kRun.run();
        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
        System.out.println(dataSet.size());
        int[][] count = new int[clusteee][dataSet.size()];
        for (Cluster cluster : clusterSet) {
            //System.out.println(cluster);
        	for (Point point : cluster.getMembers()) {
               // System.out.println("\n"+(point.getId()+1)+" "+point.getClusterid()+" "
        	//+point.getDist()+" "+Arrays.toString(cluster.getCenter().getlocalArray()));
        		//count[point.getClusterid()][point.getId()] = 1;
        		//此处的point.getId()是表中的ID-1；
/*        		System.out.println(Arrays.toString(cluster.getCenter().getlocalArray()));
        		countC[point.getClusterid()]++;*/
        		countC[point.getClusterid()]++;
        		ds.addValue(countC[point.getClusterid()], Arrays.toString(cluster.getCenter().getlocalArray()), "");

            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart3D(  
                "total register", //图表标题  
                "group", //目录轴的显示标签  
                "register", //数值轴的显示标签  
                ds, //数据集  
                PlotOrientation.VERTICAL, //图表方向  
                true, //是否显示图例，对于简单的柱状图必须为false  
                false, //是否生成提示工具  
                false);         //是否生成url链接  
        
        
        
        
        
        response.setContentType("image/jpeg");
		try {
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(),chart,800,600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
