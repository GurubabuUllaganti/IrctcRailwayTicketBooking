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
@WebServlet("/User")
public class User extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
		PrintWriter writer=resp.getWriter();
//		String userId=req.getParameter("userId");
		String userName=req.getParameter("userName");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String url="jdbc:mysql://localhost:3306?user=root&password=12345";
		String query ="Insert into test.user( userName, email, password) values(?,?,?)";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(query);
			//ps.setString(1, userId);
			ps.setString(1, userName);
			ps.setString(2, email);
			ps.setString(3, password);
			int inser=ps.executeUpdate();
			if(inser!=0)
			{
				writer.println("Record Inserted..");
				RequestDispatcher dispatcher=req.getRequestDispatcher("Reservation.html");
				dispatcher.forward(req, resp);
			}
			else
			{
				writer.println("Records Not Inserted...");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
