/* Author: Kyle Vincent
Date: 21/3/18
Description: bookingPage.java servlet that displays the booking page for a particular
seat in a theatre, and edits the booking.txt file once booking is submitted */

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns={"/bookingPage"})
public class bookingPage extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
		{
			PrintWriter out = response.getWriter();
			
			String seat=(String)request.getParameter("seat");

			//array of seats generated to check seat parameter is correct
			String[] seats = {"A1","A2","A3","A4","A5","A6","A7","A8",
					"B1","B2","B3","B4","B5","B6","B7","B8",
					"C1","C2","C3","C4","C5","C6","C7","C8",
					"D1","D2","D3","D4","D5","D6","D7","D8",
					"E1","E2","E3","E4","E5","E6","E7","E8",
					"F1","F2","F3","F4","F5","F6","F7","F8",
					"G1","G2","G3","G4","G5","G6","G7","G8",
					"H1","H2","H3","H4","H5","H6","H7","H8"};

			Scanner input=null;

			try{input = new Scanner(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt")));}
			catch(FileNotFoundException e){e.printStackTrace();}

			String fileLines[]=new String[576];
			for(int i=0;i<575;i++)
			{
				fileLines[i]=input.nextLine();
			}


			String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			String securityCode="";
			Random r=new Random();
			
			//generation of security code
			for(int i=0;i<6;i++)
			{
				securityCode+=alphabet.charAt(r.nextInt(35));
			}
				
			//HTML
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head><link rel=\"stylesheet\" href=\"style.css\"><title>Clarit Theatre - Booking Details</title></head>");
			out.println("<body>");
			out.println("<h1>Clarit Theatre - Opening Night</h1>");

			//checks if seat exists, and if it's available
			if(!Arrays.asList(seats).contains(seat)||fileLines[Arrays.asList(fileLines).indexOf(seat)+1].equals("booked:true"))
			{
				out.println("<p>Invalid seat number. Please return to the booking page.</p>");
			}
			else {
				out.println("<p>Please enter your booking details.</p>");
				out.println("<p>* = required</p>");

				//FORM VALIDATION
				out.println("<script>function validateForm(){");
				out.println("if(!document.bookingDetails.userid.value.match(/^[A-Za-z]+$/)){alert(\"Invalid UserID\");return false;}");
				//following regular expression pattern taken from  http://www.w3resource.com/javascript/form/email-validation.php
				out.println("if (/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/.test(bookingDetails.email.value)==false){alert(\"Invalid email address\");return false;}");
				out.println("if(document.bookingDetails.securitycode.value!=\"" + securityCode + "\"){alert(\"Invalid security code\");return false;}");
				out.println("return true;}");
				out.println("</script>");

				//FORM
				out.println("<form name=\"bookingDetails\" onsubmit=\"return validateForm()\" action=\"bookingPage\" method=\"post\">");
				out.println("<label>Seat: " + seat + "</label>");
				out.println("<input type=\"hidden\" name=\"seat\" value=\"" + seat + "\">");
				out.println("<label>UserID* (no numbers): <input type=\"text\" name=\"userid\" required></label>");
				out.println("<label>Phone: <input type=\"text\" name=\"phone\"></label>");
				out.println("<label>Address: <input type=\"text\" name=\"address\"></label>");
				out.println("<label>Email*: <input type=\"text\" name=\"email\" required></label>");
				out.println("<label>Please enter the following security code:<br>");
				out.println(securityCode + "</label>");
				out.println("<label>Security Code* (case sensitive): <input type=\"text\" name=\"securitycode\" required></label>");
				out.println("<input class=\"alignedbtn\" type=\"reset\" value=\"Clear Form\"><input class=\"alignedbtn\"  type=\"submit\" value=\"Submit\">");
				out.println("</form>");
			}
			out.println("</body>");
			out.println("</html>");
			out.close();
		}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
		{
			PrintWriter out=response.getWriter();

			//PARAMETER GATHERING
			String seat=(String)request.getParameter("seat");
			String userID=(String)request.getParameter("userid");
			String phone=""+(String)request.getParameter("phone");
			if(phone.equals("")){phone="~";}
			String address=""+(String)request.getParameter("address");
			if(address.equals("")){address="~";}
			String email=(String)request.getParameter("email");
			String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

			//FILE READING FOR bookings.txt
			Scanner input=null;
			try{input = new Scanner(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt")));}
			catch(FileNotFoundException e){e.printStackTrace();}
			String[] inputStr= new String[576];

			//entire file read and transformed to string array, and userid is counted
			int userCount=0, lineNumber=0;
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				inputStr[lineNumber]=line;
				if(("user:"+userID).equals(line)){userCount++;}
				lineNumber++;
			}

			//checks whether user has made 3 bookings already
			if(userCount<3)
			{
				//writer for new bookings.txt
				BufferedWriter fileWriter=null;
				try{fileWriter=new BufferedWriter(new FileWriter(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt"))));}
				catch(FileNotFoundException e){e.printStackTrace();}

				//each line of bookings.txt is functionally copied, or edited accordingly
				for(int i=0;i<576;i++)
				{
					//once the section of the file that contains seat data is found, the new data is entered
					if(inputStr[i].equals(seat))
					{
						fileWriter.write(seat);
						fileWriter.newLine();
						fileWriter.write("booked:true");
						fileWriter.newLine();
						fileWriter.write("user:"+userID);
						fileWriter.newLine();
						fileWriter.write("phone:"+phone);
						fileWriter.newLine();
						fileWriter.write("address:"+address);
						fileWriter.newLine();
						fileWriter.write("email:"+email);
						fileWriter.newLine();
						fileWriter.write("time:"+time);
						fileWriter.newLine();
						fileWriter.write("~");
						fileWriter.newLine();
						fileWriter.newLine();
						i+=8;
					}
					else
					{
						fileWriter.write(inputStr[i]);
						fileWriter.newLine();
					}
				}
				fileWriter.close();

				//script below sends a confirmation and redirects the browser to main
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<body>");
				out.println("<script>alert(\"Booking confirmed\");window.location.href=\"main\";</script>");
				out.println("</body>");
				out.println("</html>");
			}

			else{
				//script below sends an error message to the user and functionally returns to the bookingPage
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<body>");
				out.println("<script>alert(\"Error: User has 3 bookings already and can make no more.\");window.location.href=\"bookingPage?seat="+seat+"\";</script>");
				out.println("</body>");
				out.println("</html>");
			}
		}
}


















