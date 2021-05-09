package edu.missouri.analyze;

import edu.missouri.constants.Constants;
import edu.missouri.util.CommonUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TotalPacketCount {

    private static TotalPacketCount instance = null;
    private static CommonUtil commonUtil = null;

    private TotalPacketCount() {

    }

    public static TotalPacketCount getInstance() {
        if(instance == null) {
            instance = new TotalPacketCount();
            commonUtil = CommonUtil.getInstance();
        }
        return instance;
    }

    public Long getTotalReceivedPackets(Date startDate, Date endDate, String device) {
        System.out.println("PacketCounter :: getTotalReceivedPackets :: startDate :: " + startDate + " :: endDate :: " + endDate + " :: device :: " + device);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.NETWORK_FILE)))) {

            String line = null;
            String[] header = null;
            Long count = 0L;

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.COLLECTL_DATE_TIME_PATTERN);

            while((line = br.readLine()) != null) {
                if(line.startsWith(Constants.HASH)) {
                    header = line.substring(1).split(" ");
                } else {
                    Map<String, String> map = commonUtil.createMap(header, line.split(" "));
                    Date lineDate = dateTimeFormat.parse(map.get(Constants.DATE_COLUMN) + Constants.SPACE + map.get(Constants.TIME_COLUMN));

                    if((lineDate.equals(startDate) || lineDate.after(startDate)) && (lineDate.equals(endDate) || lineDate.before(endDate))) {
                        String val = map.get(Constants.SQUARE_OPEN_BRACKET + Constants.NET + Constants.COLON + device + Constants.SQUARE_CLOSE_BRACKET + Constants.RECEIVED_PACKET);
                        count += Long.parseLong(val);
                    }
                }
            }

            System.out.println("PacketCounter :: getTotalReceivedPackets :: count :: " + count);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public Long getTotalTransmittedPackets(Date startDate, Date endDate, String device) {
        System.out.println("PacketCounter :: getTotalTransmittedPackets :: startDate :: " + startDate + " :: endDate :: " + endDate + " :: device :: " + device);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.NETWORK_FILE)))) {

            String line = null;
            String[] header = null;
            Long count = 0L;

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.COLLECTL_DATE_TIME_PATTERN);

            while((line = br.readLine()) != null) {
                if(line.startsWith(Constants.HASH)) {
                    header = line.substring(1).split(" ");
                } else {
                    Map<String, String> map = commonUtil.createMap(header, line.split(" "));
                    Date lineDate = dateTimeFormat.parse(map.get(Constants.DATE_COLUMN) + Constants.SPACE + map.get(Constants.TIME_COLUMN));

                    if((lineDate.equals(startDate) || lineDate.after(startDate)) && (lineDate.equals(endDate) || lineDate.before(endDate))) {
                        String val = map.get(Constants.SQUARE_OPEN_BRACKET + Constants.NET + Constants.COLON + device + Constants.SQUARE_CLOSE_BRACKET + Constants.TRANSMITTED_PACKET);
                        count += Long.parseLong(val);
                    }
                }
            }

            System.out.println("PacketCounter :: getTotalTransmittedPackets :: count :: " + count);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
