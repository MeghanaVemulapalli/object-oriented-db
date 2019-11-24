
/* /****************************************************************************
CSE532 -- Project 2
File name: DatabaseConnection.java
Author(s): Harshini Kanaparthi (112687951 )
Meghana vemulapalli (112670836 )
Brief description: This file is the sevlet file that uses the post gres connection object and executes the queries in the database,
retrieves the results and display them in the UI on the request from user via clicking buttons
****************************************************************************/





package postgres;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.io.IOException; 
import java.io.PrintWriter; 
import java.sql.Connection; 
import java.sql.PreparedStatement;
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertData") 
public class PostgresSqlServlet extends HttpServlet { 
    private static final long serialVersionUID = 1L;  
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
    {  
       PrintWriter out = res.getWriter();  // Request and Response Objects
       res.setContentType("text/html;charset=UTF-8");
       Connection conn = null; 
       Statement stmt;
       
       out.println("<head>"); //CSS Styling
       out.println("<style>\r\n" + ".button {\r\n" + 
       		"  border: none;\r\n" + 
       		"  background-color: inherit;\r\n" + 
       		"  padding: 14px 28px;\r\n" + 
       		"  font-size: 16px;\r\n" + 
       		"  cursor: pointer;\r\n" + 
       		"  display: inline-block;\r\n" + 
       		"}" + 
       		"table {\r\n" + 
       		"  border-collapse: collapse;\r\n" + "border: 1px solid black;\r\n" +
       		"  width: 50%;\r\n" + 
       		"}\r\n" + 
       		"\r\n" + 
       		"th, td {\r\n" + 
       		"  text-align: center;\r\n" + "vertical-align: middle;"+
       		"  padding: 0px;\r\n" + 
       		"}\r\n" + 
       		"\r\n" + 
       		"tr:nth-child(even){background-color: #f2f2f2}\r\n" + 
       		"\r\n" +
       		"th {\r\n" + 
       		"  background-color: #4CAF50;\r\n" + 
       		"  color: white;\r\n" + 
       		"}\r\n" + 
       		"</style>\r\n");
       out.println("</head>");
       out.println("<html><body>"); 
       
       String name=req.getParameter("q1"); 
       try 
       {  
           
           conn = DatabaseConnection.initializeDatabase();  //DB Connection
           
           stmt = conn.createStatement();
           ResultSet rs=null;
           
           out.println("<table border=1 width=50% height=50%>");  
           
           if(name.equals("query1")) //Query 1
           {
        	   out.println("<tr><th>Company"+"</th><tr>");
        	   rs=stmt.executeQuery("SELECT comp.name \r\n" + 
        	   		"FROM company comp, board b, person per, unnest(per.ownership) as owns \r\n" + 
        	   		"WHERE per.id = b.pid and comp.id = owns.cid and comp.id = b.cid and owns.ownedshares>0 \r\n" + 
        	   		"ORDER BY comp.name;");
        	   while (rs.next()) 
               {  
            	   String attr1 = rs.getString(1);
                   out.println("<tr><td>" + attr1 + "</td><tr>");   
               } 
           }
           // Query 2
           else if(name.equals("query2"))
           {
        	   out.println("<tr><th>Person</th><th>Net Worth</th><tr>");
        	   rs = stmt.executeQuery("SELECT per.name, sum(owns.ownedshares*s.shareprice) \r\n" + 
        	   		"FROM person per, company comp, unnest(per.ownership) as owns, shares s \r\n" + 
        	   		"WHERE owns.cid = comp.id and comp.id = s.cid and owns.ownedshares>0 \r\n" + 
        	   		"GROUP BY per.name \r\n" + 
        	   		"ORDER BY per.name;"); 
        	   while (rs.next()) 
               {  
            	   String attr1 = rs.getString(1);
                   Integer attr2 = rs.getInt(2);  
                   out.println("<tr><td>" + attr1 + "</td><td>" + attr2 + "</td><tr>");   
               } 
           }
           // Query 3
           else if(name.equals("query3"))
           {
        	   out.println("<tr><th>Company</th><th>Top Board Member</th><tr>");
        	   rs = stmt.executeQuery("SELECT comp.name, per.name \r\n" + 
        	   		"FROM company comp, person per, board b, unnest(per.ownership) as owns \r\n" + 
        	   		"WHERE b.pid = per.id and comp.id = b.cid and owns.cid = comp.id and owns.ownedshares > 0 and owns.ownedshares = (SELECT max(own.ownedshares) \r\n" + 
        	   		"														 FROM person p, unnest(p.ownership) as own \r\n" + 
        	   		"														 WHERE b.pid = p.id and own.cid = comp.id and own.ownedshares>0) \r\n" + 
        	   		"ORDER BY comp.name;\r\n" + 
        	   		"");
        	   while (rs.next()) 
               {  
            	   String attr1 = rs.getString(1);
                   String attr2 = rs.getString(2);     
                   out.println("<tr><td>" + attr1 + "</td><td>" + attr2 + "</td><tr>");   
               } 
        	   
           }
           // Query 4
           else if(name.equals("query4"))
           {
        	   out.println("<tr><th>Company1</th><th>Company2</th><tr>");
        	   rs = stmt.executeQuery("SELECT comp1.name, comp2.name \r\n" + 
        	   		"FROM company comp1, company comp2, unnest(comp1.industry) as indus1, unnest(comp2.industry) as indus2 \r\n" + 
        	   		"WHERE comp1.id <> comp2.id and indus1=indus2 and NOT EXISTS(SELECT * \r\n" + 
        	   		"					      FROM person per2, board b, unnest(per2.ownership) as owns \r\n" + 
        	   		"					      WHERE comp2.id = b.cid and per2.id = b.pid and owns.ownedshares > 0 and NOT EXISTS(SELECT * \r\n" + 
        	   		"														    FROM person per1, board b1, unnest(per1.ownership) as own \r\n" + 
        	   		"                                                                                                                    WHERE comp1.id = b1.cid and b1.pid = per1.id and own.cid = owns.cid and own.ownedshares >= owns.ownedshares)) \r\n" + 
        	   		"GROUP BY comp1.name, comp2.name;\r\n" + 
        	   		""); 
        	   while (rs.next()) 
               {  
            	   String attr1 = rs.getString(1);
            	   String attr2 = rs.getString(2);
                     
                   //int s = rs.getInt("sal");   
                   out.println("<tr><td>" + attr1 + "</td><td>" + attr2 + "</td><tr>");   
               } 
           }
           // Query 5
           else if(name.equals("query5"))
           {
        	   out.println("<tr><th>Person</th><th>Company</th><th>Percentage</th><tr>");
        	   rs = stmt.executeQuery("with control_per \r\n" + 
        	   		"as (select p.name as pname, c.name as cname, cast(sum(ind.perc*100) as numeric(10,4)) as percentage \r\n" + 
        	   		"from person p, company c, indirect_ownership ind \r\n" + 
        	   		"where p.id = ind.entity and c.id = ind.cid \r\n" + 
        	   		"group by p.name, c.name) \r\n" + 
        	   		"select * \r\n" + 
        	   		"from control_per \r\n" + 
        	   		"where percentage > 10 \r\n" + 
        	   		"order by pname;\r\n"); 
        	   while (rs.next()) 
               {  
            	   String attr1 = rs.getString(1);
            	   String attr2 = rs.getString(2);
            	   String attr3 = rs.getString(3);     
                   out.println("<tr><td>" + attr1 + "</td><td>" + attr2 +"</td><td>" + attr3 + "</td><tr>");   
               } 
           } 
           // Creating table with the output
           out.println("</table>");  
           out.println("<div><p><button onclick=\"goBack()\" >OK</button>\r\n" + 
           		"\r\n" + 
           		"<script>\r\n" + 
           		"function goBack() {\r\n" + 
           		"  window.history.back();\r\n" + 
           		"}\r\n" + 
           		"</script></p></div>");
           out.println("</html></body>"); 
           // Closing the db connection
           conn.close();  
          }  
           catch (Exception e) 
          {  
           out.println("error");  
       }  
   }  
} 