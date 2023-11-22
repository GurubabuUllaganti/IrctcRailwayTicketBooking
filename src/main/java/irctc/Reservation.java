package irctc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Reservation")
public class Reservation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get reservation details from the form
        String userId = req.getParameter("userId");
        String trainNumber = req.getParameter("trainNumber");
        String classType = req.getParameter("classType");
        String dateOfJourney = req.getParameter("dateOfJourney");
        String sourceLocation = req.getParameter("sourceLocation");
        String destinationLocation = req.getParameter("destinationLocation");

        // Generate a PNR number using UUID
        String pnrNumber = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        if (pnrNumber.length() > 6) {
            pnrNumber = pnrNumber.substring(0, 6);
        }

        String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
        String query = "INSERT INTO test.reservations ( userId, trainNumber, classType, dateOfJourney, sourceLocation, destinationLocation,pnrNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PrintWriter writer = resp.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement(query);
           
            ps.setString(1, userId);
            ps.setString(2, trainNumber);
            ps.setString(3, classType);
            ps.setString(4, dateOfJourney);
            ps.setString(5, sourceLocation);
            ps.setString(6, destinationLocation);
            ps.setString(7, pnrNumber);

            int insert = ps.executeUpdate();

            if (insert != 0)
            {
            	writer.println("<style>");
				writer.println("  #errorMessage {");
				writer.println("    position: fixed;");
				writer.println("    top: 10px;");
				writer.println("    left: 500px;");
				writer.println("    background-color:gray;");
				writer.println("    color: white;");
				writer.println("    padding: 10px;");
				
				  writer.println("#an{");
			      writer.println("width:200px;");
			      writer.println("height:100px;");
			      writer.println( "background-color:blue;");
			      writer.println("justify-content:center;");
			      writer.println( "text-align:center;");
			      writer.println( "text-align:center;");
				writer.println(" }");
				writer.println("</style>");

				writer.println("<div id='errorMessage'>");
//				writer.println("  <i>You don't have an account. Please register.</i>");
				
                writer.println("Your Ticket is Booked ");
                writer.println("<br> ");
                writer.println("Your Details...");
                writer.println("<br> ");
                writer.println("PNR Number: " + pnrNumber);
                writer.println("<br> ");
                writer.println("User ID: " + userId);
                writer.println("<br> ");
                writer.println("Train Number: " + trainNumber);
                writer.println("<br> ");
                writer.println("Class Type: " + classType);
                writer.println("<br> ");
                writer.println("Date of Journey: " + dateOfJourney);
                writer.println("<br> ");
                writer.println("Source Location: " + sourceLocation);
                writer.println("<br> ");
                writer.println("Destination Location: " + destinationLocation);
                writer.println("<br> ");
                writer.println("<a href='Login.html' id='an'>Home</a>");
                writer.println("</div>");
               ps.close();
            } else 
            {
                writer.println("Reservation Failed...");
                RequestDispatcher dispatcher=req.getRequestDispatcher("Reservation.html");
				dispatcher.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
