/* Author: Kyle Vincent
Date: 21/3/18
Description: cancelPage.java servlet that displays the cancellation page for a particular
seat in a theatre, and edits the booking.txt file once cancellation is submitted */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet(urlPatterns={"/cancelPage"})
public class cancelPage extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        String seat=(String)request.getParameter("seat");

        //string array created to check seat exists
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
        int fieldSelector = r.nextInt(3);

        //generation of security code
        for(int i=0;i<6;i++)
        {
            securityCode+=alphabet.charAt(r.nextInt(35));
        }

        //hashmap for easy access of data
        HashMap<Integer, String> detailMap = new HashMap<>();
        detailMap.put(0, fileLines[Arrays.asList(fileLines).indexOf(seat)+3]);
        detailMap.put(1, fileLines[Arrays.asList(fileLines).indexOf(seat)+4]);
        detailMap.put(2, fileLines[Arrays.asList(fileLines).indexOf(seat)+5]);

        //HTML
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><link rel=\"stylesheet\" href=\"style.css\"><title>Clarit Theatre - Booking Details</title></head>");
        out.println("<body>");
        out.println("<h1>Clarit Theatre - Opening Night</h1>");

        //checks if seat exists and whether it's available
        if(!Arrays.asList(seats).contains(seat)||fileLines[Arrays.asList(fileLines).indexOf(seat)+1].equals("booked:false"))
        {
            out.println("<p>Invalid seat number. Please return to the booking page.</p>");
        }
        else
        {

            out.println("<p>This seat has already been booked. Please enter the details of the booking to make a cancellation.</p>");
            out.println("<p>* = required</p>");

            //FORM
            out.println("<form name=\"cancellationDetails\" action=\"cancelPage\" method=\"post\">");
            out.println("<label>Seat: " + seat + "</label>");
            out.println("<input type=\"hidden\" name=\"seat\" value=\"" + seat + "\">");
            out.println("<label>UserID* (no numbers): <input type=\"text\" name=\"userid\" required></label>");
            if(fieldSelector==0){out.println("<label>Phone: (leave blank if not provided) <input type=\"text\" name=\"phone\"></label>");}
            else if(fieldSelector==1){out.println("<label>Address: (leave blank if not provided) <input type=\"text\" name=\"address\"></label>");}
            else{out.println("<label>Email*: <input type=\"text\" name=\"email\" required></label>");}
            out.println("<label>Please enter the following security code:<br>");
            out.println(securityCode + "</label>");
            out.println("<label>Security Code* (case sensitive): <input type=\"text\" name=\"securitycode\" required></label>");
            out.println("<input type=\"hidden\" name=\"fieldSelector\" value=\""+fieldSelector+"\">");
            out.println("<input class=\"alignedbtn\" type=\"reset\" value=\"Clear Form\"><input class=\"alignedbtn\"  type=\"submit\" value=\"Submit Cancellation\">");
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
        int fieldSelector=Integer.parseInt(request.getParameter("fieldSelector"));
        String address=""+(String)request.getParameter("address");
        String email=(String)request.getParameter("email");

        //FILE READING FOR bookings.txt
        Scanner input=null;
        try{input = new Scanner(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt")));}
        catch(FileNotFoundException e){e.printStackTrace();}
        String[] inputStr= new String[576];

        //entire file read and transformed to string array, and userid is counted
        int lineNumber=0;
        while(input.hasNextLine())
        {
            String line = input.nextLine();
            inputStr[lineNumber]=line;
            lineNumber++;
        }

        //two parallel arrays created for detail comparison
        String[] parallelClientDetails={"phone:"+phone,"address:"+address,"email:"+email};
        String[] parallelServerDetails={inputStr[Arrays.asList(inputStr).indexOf(seat)+3],inputStr[Arrays.asList(inputStr).indexOf(seat)+4],inputStr[Arrays.asList(inputStr).indexOf(seat)+5]};

        //checks user details are correct, and the randomly selected detail from doGet input matches server
        if(("user:"+userID).equals(inputStr[Arrays.asList(inputStr).indexOf(seat)+2])&&parallelClientDetails[fieldSelector].equals(parallelServerDetails[fieldSelector])) {
            //writer for new bookings.txt
            BufferedWriter fileWriter = null;
            try {
                fileWriter = new BufferedWriter(new FileWriter(new File(this.getServletContext().getRealPath("/WEB-INF/bookings.txt"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //each line of bookings.txt is functionally copied, or edited accordingly
            for (int i = 0; i < 576; i++) {
                //once the section of the file that contains seat data is found, the new data is entered
                if (inputStr[i].equals(seat)) {
                    fileWriter.write(seat);
                    fileWriter.newLine();
                    fileWriter.write("booked:false");
                    fileWriter.newLine();
                    fileWriter.write("user:");
                    fileWriter.newLine();
                    fileWriter.write("phone:");
                    fileWriter.newLine();
                    fileWriter.write("address:");
                    fileWriter.newLine();
                    fileWriter.write("email:");
                    fileWriter.newLine();
                    fileWriter.write("time:");
                    fileWriter.newLine();
                    fileWriter.write("~");
                    fileWriter.newLine();
                    fileWriter.newLine();
                    i += 8;
                } else {
                    fileWriter.write(inputStr[i]);
                        fileWriter.newLine();
                }
            }
            fileWriter.close();

            //script below sends a confirmation and redirects the browser to main
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script>alert(\"Cancellation confirmed\");window.location.href=\"main\";</script>");
            out.println("</body>");
            out.println("</html>");
        }
        else
        {
            //script below sends an error and redirects back to cancelPage
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script>alert(\"Cancellation unsuccessful. Details are not correct.\");window.location.href=\"cancelPage?seat="+seat+"\";</script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}



















