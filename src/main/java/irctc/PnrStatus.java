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
@WebServlet("/PnrStatus")
public class PnrStatus extends HttpServlet
{
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	    {
	        String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
	        String query = "select * from test.reservations where pnrNumber=?";
	        PrintWriter writer = resp.getWriter();
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection connection = DriverManager.getConnection(url);
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setString(1, req.getParameter("pnrNumber"));
	            ResultSet res = ps.executeQuery();
	            resp.setContentType("text/html");
	            if (res.next()) {
	                writer.println("<html>");
	                writer.println("<head><title>Reservation Details</title></head>");
	                writer.println("<body>");
	                writer.println("<h2> Details</h2>");
	                writer.println("<p><strong>PNR Number:</strong> " + res.getString("pnrNumber") + "</p>");
	                writer.println("<p><strong>User ID:</strong> " + res.getString("userId") + "</p>");
	                writer.println("<p><strong>Train Number:</strong> " + res.getString("trainNumber") + "</p>");
	                writer.println("<p><strong>Class Type:</strong> " + res.getString("classType") + "</p>");
	                writer.println("<p><strong>Date of Journey:</strong> " + res.getString("dateOfJourney") + "</p>");
	                writer.println("<p><strong>Source Location:</strong> " + res.getString("sourceLocation") + "</p>");
	                writer.println("<p><strong>Destination Location:</strong> " + res.getString("destinationLocation") + "</p>");
	                writer.println("</body>");
	                writer.println("</html>");
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
					writer.println("  <p>The provided PNR number does not match any reservations.</p>");
					writer.println("</div>");

					writer.println("<script>");
					writer.println("  setTimeout(function() { document.getElementById('errorMessage').style.display = 'none'; }, 3000);");
					writer.println("</script>");	               
	                RequestDispatcher dispatcher=req.getRequestDispatcher("PnrNumber.html");
					dispatcher.include(req, resp);
              }
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
