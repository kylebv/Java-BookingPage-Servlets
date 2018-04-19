/* Author: Kyle Vincent
Date: 21/3/18
Description: main.java servlet that displays the seat selection page for 
a theatre, and opens bookingPage once seat is selected */

import java.io.*;
import java.util.Scanner;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns={"/main"})
public class main extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
		{
			PrintWriter out = response.getWriter();
			Scanner input=null;
			
			try{input = new Scanner(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt")));}
			catch(FileNotFoundException e){e.printStackTrace();}
			
			String fileLines[]=new String[576];
			for(int i=0;i<575;i++)
			{
				fileLines[i]=input.nextLine();
			}
			String[] tdCssClass = new String[64];
			
			//567 is the last line of the bookings.txt file that contains whether a seat is available
			for(int i=0;i<=567;i=i+9)
			{
				//seat availability is on the second line of bookings.txt, and every 9 lines after that
				if(fileLines[i+1].equals("booked:false")){tdCssClass[i/9]="available";}
				else{tdCssClass[i/9]="unavailable";}
			}

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head><link rel=\"stylesheet\" href=\"style.css\"><title>Clarit Theatre - Book a Seat</title></head>");
			out.println("<body>");
			out.println("<h1>Clarit Theatre - Opening Night</h1>");
			out.println("<h2>Book a Seat</h2>");
			out.println("<p>Please select a seat from the table below. &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span id=\"time\"></span></p>");
			
			//embedding the external dateFormat library
			out.println("<script src=\"http://stevenlevithan.com/assets/misc/date.format.js\"></script>");
			
			//below script puts the current time on the page and updates it every second
			//in the (very strange) display format of DD-MM-YY SS-MM-HH as specified in the assignment guideline
			out.println("<script>");
			out.println("var timeElement=document.getElementById(\"time\");");
			out.println("function updateTime(time){time.innerHTML=dateFormat(new Date(), \"dd-mm-yy ss-MM-hh\");}");
			out.println("setInterval(function (){updateTime(timeElement);}, 1000);");
			out.println("</script>");

			//below script throws the user to bookingPage if seat available, and cancelPage if seat unavailable
			out.println("<script>");
			out.println("function selectSeat(seatSelection, availability){");
			out.println("if(availability==\"available\"){window.location.href=\"bookingPage?seat=\"+seatSelection;}");
			out.println("else{window.location.href=\"cancelPage?seat=\"+seatSelection;}}");
			out.println("</script>");
			
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\"></td>");
			out.println("<td class=\"outside\">1</td>");
			out.println("<td class=\"outside\">2</td>");
			out.println("<td class=\"outside\">3</td>");
			out.println("<td class=\"outside\">4</td>");
			out.println("<td class=\"outside\">5</td>");
			out.println("<td class=\"outside\">6</td>");
			out.println("<td class=\"outside\">7</td>");
			out.println("<td class=\"outside\">8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">A</td>");
			out.println("<td id=\"A1\" class=\""+tdCssClass[0]+"\" onclick=\"selectSeat('A1', '"+tdCssClass[0]+"')\">A1</td>");
			out.println("<td id=\"A2\" class=\""+tdCssClass[1]+"\" onclick=\"selectSeat('A2', '"+tdCssClass[1]+"')\">A2</td>");
			out.println("<td id=\"A3\" class=\""+tdCssClass[2]+"\" onclick=\"selectSeat('A3', '"+tdCssClass[2]+"')\">A3</td>");
			out.println("<td id=\"A4\" class=\""+tdCssClass[3]+"\" onclick=\"selectSeat('A4', '"+tdCssClass[3]+"')\">A4</td>");
			out.println("<td id=\"A5\" class=\""+tdCssClass[4]+"\" onclick=\"selectSeat('A5', '"+tdCssClass[4]+"')\">A5</td>");
			out.println("<td id=\"A6\" class=\""+tdCssClass[5]+"\" onclick=\"selectSeat('A6', '"+tdCssClass[5]+"')\">A6</td>");
			out.println("<td id=\"A7\" class=\""+tdCssClass[6]+"\" onclick=\"selectSeat('A7', '"+tdCssClass[6]+"')\">A7</td>");
			out.println("<td id=\"A8\" class=\""+tdCssClass[7]+"\" onclick=\"selectSeat('A8', '"+tdCssClass[7]+"')\">A8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">B</td>");
			out.println("<td id=\"B1\" class=\""+tdCssClass[8]+"\" onclick=\"selectSeat('B1', '"+tdCssClass[8]+"')\">B1</td>");
			out.println("<td id=\"B2\" class=\""+tdCssClass[9]+"\" onclick=\"selectSeat('B2', '"+tdCssClass[9]+"')\">B2</td>");
			out.println("<td id=\"B3\" class=\""+tdCssClass[10]+"\" onclick=\"selectSeat('B3', '"+tdCssClass[10]+"')\">B3</td>");
			out.println("<td id=\"B4\" class=\""+tdCssClass[11]+"\" onclick=\"selectSeat('B4', '"+tdCssClass[11]+"')\">B4</td>");
			out.println("<td id=\"B5\" class=\""+tdCssClass[12]+"\" onclick=\"selectSeat('B5', '"+tdCssClass[12]+"')\">B5</td>");
			out.println("<td id=\"B6\" class=\""+tdCssClass[13]+"\" onclick=\"selectSeat('B6', '"+tdCssClass[13]+"')\">B6</td>");
			out.println("<td id=\"B7\" class=\""+tdCssClass[14]+"\" onclick=\"selectSeat('B7', '"+tdCssClass[14]+"')\">B7</td>");
			out.println("<td id=\"B8\" class=\""+tdCssClass[15]+"\" onclick=\"selectSeat('B8', '"+tdCssClass[15]+"')\">B8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">C</td>");
			out.println("<td id=\"C1\" class=\""+tdCssClass[16]+"\" onclick=\"selectSeat('C1', '"+tdCssClass[16]+"')\">C1</td>");
			out.println("<td id=\"C2\" class=\""+tdCssClass[17]+"\" onclick=\"selectSeat('C2', '"+tdCssClass[17]+"')\">C2</td>");
			out.println("<td id=\"C3\" class=\""+tdCssClass[18]+"\" onclick=\"selectSeat('C3', '"+tdCssClass[18]+"')\">C3</td>");
			out.println("<td id=\"C4\" class=\""+tdCssClass[19]+"\" onclick=\"selectSeat('C4', '"+tdCssClass[19]+"')\">C4</td>");
			out.println("<td id=\"C5\" class=\""+tdCssClass[20]+"\" onclick=\"selectSeat('C5', '"+tdCssClass[20]+"')\">C5</td>");
			out.println("<td id=\"C6\" class=\""+tdCssClass[21]+"\" onclick=\"selectSeat('C6', '"+tdCssClass[21]+"')\">C6</td>");
			out.println("<td id=\"C7\" class=\""+tdCssClass[22]+"\" onclick=\"selectSeat('C7', '"+tdCssClass[22]+"')\">C7</td>");
			out.println("<td id=\"C8\" class=\""+tdCssClass[23]+"\" onclick=\"selectSeat('C8', '"+tdCssClass[23]+"')\">C8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">D</td>");
			out.println("<td id=\"D1\" class=\""+tdCssClass[24]+"\" onclick=\"selectSeat('D1', '"+tdCssClass[24]+"')\">D1</td>");
			out.println("<td id=\"D2\" class=\""+tdCssClass[25]+"\" onclick=\"selectSeat('D2', '"+tdCssClass[25]+"')\">D2</td>");
			out.println("<td id=\"D3\" class=\""+tdCssClass[26]+"\" onclick=\"selectSeat('D3', '"+tdCssClass[26]+"')\">D3</td>");
			out.println("<td id=\"D4\" class=\""+tdCssClass[27]+"\" onclick=\"selectSeat('D4', '"+tdCssClass[27]+"')\">D4</td>");
			out.println("<td id=\"D5\" class=\""+tdCssClass[28]+"\" onclick=\"selectSeat('D5', '"+tdCssClass[28]+"')\">D5</td>");
			out.println("<td id=\"D6\" class=\""+tdCssClass[29]+"\" onclick=\"selectSeat('D6', '"+tdCssClass[29]+"')\">D6</td>");
			out.println("<td id=\"D7\" class=\""+tdCssClass[30]+"\" onclick=\"selectSeat('D7', '"+tdCssClass[30]+"')\">D7</td>");
			out.println("<td id=\"D8\" class=\""+tdCssClass[31]+"\" onclick=\"selectSeat('D8', '"+tdCssClass[31]+"')\">D8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">E</td>");
			out.println("<td id=\"E1\" class=\""+tdCssClass[32]+"\" onclick=\"selectSeat('E1', '"+tdCssClass[32]+"')\">E1</td>");
			out.println("<td id=\"E2\" class=\""+tdCssClass[33]+"\" onclick=\"selectSeat('E2', '"+tdCssClass[33]+"')\">E2</td>");
			out.println("<td id=\"E3\" class=\""+tdCssClass[34]+"\" onclick=\"selectSeat('E3', '"+tdCssClass[34]+"')\">E3</td>");
			out.println("<td id=\"E4\" class=\""+tdCssClass[35]+"\" onclick=\"selectSeat('E4', '"+tdCssClass[35]+"')\">E4</td>");
			out.println("<td id=\"E5\" class=\""+tdCssClass[36]+"\" onclick=\"selectSeat('E5', '"+tdCssClass[36]+"')\">E5</td>");
			out.println("<td id=\"E6\" class=\""+tdCssClass[37]+"\" onclick=\"selectSeat('E6', '"+tdCssClass[37]+"')\">E6</td>");
			out.println("<td id=\"E7\" class=\""+tdCssClass[38]+"\" onclick=\"selectSeat('E7', '"+tdCssClass[38]+"')\">E7</td>");
			out.println("<td id=\"E8\" class=\""+tdCssClass[39]+"\" onclick=\"selectSeat('E8', '"+tdCssClass[39]+"')\">E8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">F</td>");
			out.println("<td id=\"F1\" class=\""+tdCssClass[40]+"\" onclick=\"selectSeat('F1', '"+tdCssClass[40]+"')\">F1</td>");
			out.println("<td id=\"F2\" class=\""+tdCssClass[41]+"\" onclick=\"selectSeat('F2', '"+tdCssClass[41]+"')\">F2</td>");
			out.println("<td id=\"F3\" class=\""+tdCssClass[42]+"\" onclick=\"selectSeat('F3', '"+tdCssClass[42]+"')\">F3</td>");
			out.println("<td id=\"F4\" class=\""+tdCssClass[43]+"\" onclick=\"selectSeat('F4', '"+tdCssClass[43]+"')\">F4</td>");
			out.println("<td id=\"F5\" class=\""+tdCssClass[44]+"\" onclick=\"selectSeat('F5', '"+tdCssClass[44]+"')\">F5</td>");
			out.println("<td id=\"F6\" class=\""+tdCssClass[45]+"\" onclick=\"selectSeat('F6', '"+tdCssClass[45]+"')\">F6</td>");
			out.println("<td id=\"F7\" class=\""+tdCssClass[46]+"\" onclick=\"selectSeat('F7', '"+tdCssClass[46]+"')\">F7</td>");
			out.println("<td id=\"F8\" class=\""+tdCssClass[47]+"\" onclick=\"selectSeat('F8', '"+tdCssClass[47]+"')\">F8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">G</td>");
			out.println("<td id=\"G1\" class=\""+tdCssClass[48]+"\" onclick=\"selectSeat('G1', '"+tdCssClass[48]+"')\">G1</td>");
			out.println("<td id=\"G2\" class=\""+tdCssClass[49]+"\" onclick=\"selectSeat('G2', '"+tdCssClass[49]+"')\">G2</td>");
			out.println("<td id=\"G3\" class=\""+tdCssClass[50]+"\" onclick=\"selectSeat('G3', '"+tdCssClass[50]+"')\">G3</td>");
			out.println("<td id=\"G4\" class=\""+tdCssClass[51]+"\" onclick=\"selectSeat('G4', '"+tdCssClass[51]+"')\">G4</td>");
			out.println("<td id=\"G5\" class=\""+tdCssClass[52]+"\" onclick=\"selectSeat('G5', '"+tdCssClass[52]+"')\">G5</td>");
			out.println("<td id=\"G6\" class=\""+tdCssClass[53]+"\" onclick=\"selectSeat('G6', '"+tdCssClass[53]+"')\">G6</td>");
			out.println("<td id=\"G7\" class=\""+tdCssClass[54]+"\" onclick=\"selectSeat('G7', '"+tdCssClass[54]+"')\">G7</td>");
			out.println("<td id=\"G8\" class=\""+tdCssClass[55]+"\" onclick=\"selectSeat('G8', '"+tdCssClass[55]+"')\">G8</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=\"outside\">H</td>");
			out.println("<td id=\"H1\" class=\""+tdCssClass[56]+"\" onclick=\"selectSeat('H1', '"+tdCssClass[56]+"')\">H1</td>");
			out.println("<td id=\"H2\" class=\""+tdCssClass[57]+"\" onclick=\"selectSeat('H2', '"+tdCssClass[57]+"')\">H2</td>");
			out.println("<td id=\"H3\" class=\""+tdCssClass[58]+"\" onclick=\"selectSeat('H3', '"+tdCssClass[58]+"')\">H3</td>");
			out.println("<td id=\"H4\" class=\""+tdCssClass[59]+"\" onclick=\"selectSeat('H4', '"+tdCssClass[59]+"')\">H4</td>");
			out.println("<td id=\"H5\" class=\""+tdCssClass[60]+"\" onclick=\"selectSeat('H5', '"+tdCssClass[60]+"')\">H5</td>");
			out.println("<td id=\"H6\" class=\""+tdCssClass[61]+"\" onclick=\"selectSeat('H6', '"+tdCssClass[61]+"')\">H6</td>");
			out.println("<td id=\"H7\" class=\""+tdCssClass[62]+"\" onclick=\"selectSeat('H7', '"+tdCssClass[62]+"')\">H7</td>");
			out.println("<td id=\"H8\" class=\""+tdCssClass[63]+"\" onclick=\"selectSeat('H8', '"+tdCssClass[63]+"')\">H8</td>");
			out.println("</tr>");
			out.println("</table>");
			
			out.println("</body>");
			out.println("</html>");
			out.close();
		}
}