package irctc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Registration")
public class Registration extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String url="jdbc:mysql://localhost:3306?user=root&password=12345";
		String query="select * from test.user where email=? and password=?";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, req.getParameter("email"));
			ps.setString(2, req.getParameter("password"));
			ResultSet res=ps.executeQuery();
			PrintWriter writer=resp.getWriter();
			resp.setContentType("text/html");
			if (res.next()) 
			{
				writer.println("Login Successfully ....!!!");
				RequestDispatcher dispatcher=req.getRequestDispatcher("Reservation.html");
				dispatcher.forward(req, resp);
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
				writer.println("    padding: 10px;");
				writer.println("  }");
				writer.println("</style>");

				writer.println("<div id='errorMessage'>");
				writer.println("  <i>You don't have an account. Please register.</i>");
				writer.println("</div>");

				writer.println("<script>");
				writer.println("  setTimeout(function() { document.getElementById('errorMessage').style.display = 'none'; }, 3000);");
				writer.println("</script>");

				RequestDispatcher dispatcher=req.getRequestDispatcher("Login.html");
				dispatcher.include(req, resp);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
