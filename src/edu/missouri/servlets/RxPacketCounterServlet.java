package edu.missouri.servlets;

import edu.missouri.analyze.TotalPacketCount;
import edu.missouri.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/RxPacketCounter")
public class RxPacketCounterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doWork(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doWork(request, response);
    }

    protected void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            PrintWriter out = response.getWriter();

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
            Date startDate = dateFormat.parse(request.getParameter(Constants.START_DATE));
            Date endDate = dateFormat.parse(request.getParameter(Constants.END_DATE));

            SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.TIME_PATTERN);
            Date startTime = timeFormat.parse(request.getParameter(Constants.START_TIME));
            Date endTime = timeFormat.parse(request.getParameter(Constants.END_TIME));

            String device = request.getParameter(Constants.DEVICE);

            Long count = TotalPacketCount.getInstance().getTotalReceivedPackets(startDate, startTime, endDate, endTime, device);
            out.println("Total Packets: " + count);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
    }
}
