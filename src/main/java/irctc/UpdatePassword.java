package irctc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String url="jdbc:mysql://localhost:3306?user=root&password=12345";
		String update="update test.user set password=? where email=?";
		PrintWriter writer=resp.getWriter();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(update);
			ps.setString(1, req.getParameter("password"));
			ps.setString(2, req.getParameter("email"));
			int result = ps.executeUpdate();
			if(result!=0)
			{
				writer.println("<style>");
				writer.println("  #Message {");
				writer.println("    position: fixed;");
				writer.println("    top: 10px;");
				writer.println("    right: 10px;");
				writer.println("    background-color:green;");
				writer.println("    color: white;");
				writer.println("    display-flex:center;");
				writer.println("    justify-content: center;");
//				writer.println("    padding: 10px;");
				writer.println("  }");
				writer.println("</style>");

				writer.println("<div id='Message'>");
				writer.println("  <p>Password Updated..!!.</p>");
				writer.println("</div>");

				writer.println("<script>");
				writer.println("  setTimeout(function() { document.getElementById('Message').style.display = 'none'; }, 7000);");
				writer.println("</script>");
				RequestDispatcher dispatcher=req.getRequestDispatcher("UpdatePassword.html");
				 dispatcher.include(req, resp);
			}
			else
			{
				writer.println("<style>");
				writer.println("  #errorMessage {");
				writer.println("    position: fixed;");
				writer.println("    top: 10px;");
				writer.println("    right: 10px;");
				writer.println("    background-color: red;");
				writer.println("    color: white;");
//				writer.println("    padding: 10px;");
				writer.println("  }");
				writer.println("</style>");

				writer.println("<div id='errorMessage'>");
				writer.println("  <p>Provide Valid Email.</p>");
				writer.println("</div>");

				writer.println("<script>");
				writer.println("  setTimeout(function() { document.getElementById('errorMessage').style.display = 'none'; }, 3000);");
				writer.println("</script>");	
				 RequestDispatcher dispatcher=req.getRequestDispatcher("UpdatePassword.html");
				 dispatcher.include(req, resp);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
