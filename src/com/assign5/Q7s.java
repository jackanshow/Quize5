package com.assign5;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Q7s
 */
@WebServlet("/Q7s")
public class Q7s extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String att1 = request.getParameter("att1");
		String att2 = request.getParameter("att2");
		System.out.println(att1+att2);
		
	      
	      
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
		      int c=8;
		   // 设置响应内容类型
		      response.setContentType("text/html");
			// 实际的逻辑是在这里
		      PrintWriter out = response.getWriter();
		      out.println("<table border=\"1\">");
		      out.println("<tr><th>"+"name"+"</th><th>"+"age"+
		        			 "<th>"
		        			 +"</tr>");
		      rs.close();
		      int[] r ={ (int)(Math.random()*50),(int)(Math.random()*50),(int)(Math.random()*50),(int)(Math.random()*50),(int)(Math.random()*50),(int)(Math.random()*50)
		    		  ,(int)(Math.random()*50),(int)(Math.random()*50),(int)(Math.random()*50)};
		      while(c>0) {
		    	  ResultSet rs1 = stmt.executeQuery(sql);
		      while ( rs.next() ) {


			    	  if(Integer.parseInt(rs.getString("ID"))==r[c] ) {
			    	  out.println("<tr><th>"+rs1.getString("FNAME")+" "
			    	  +rs1.getString("LNAME")+"</th><th>"+rs1.getString("AGE")+
			        			 "<th>"
			        			 +"</tr>");
			    		 // out.println("<p>"+rs.getString("FNAME")+" "+rs.getString("LNAME")+"&ensp;&ensp;&ensp; "+rs.getString("AGE")+"</p>");
			    	  System.out.println(rs.getString("LNAME"));
			    	  c--;
			    	  break;
			    	  }
			    	
		    	  


		    	  
		      }
		      rs1.close();
		    }
		      out.println("</table>"); 
		      out.flush();
		      out.close();
		      
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
		    
		    
	      
	      
	      
	}

}
