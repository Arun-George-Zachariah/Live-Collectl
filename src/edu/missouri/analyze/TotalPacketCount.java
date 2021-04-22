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

    public Long getTotalPackets(Date startDate, Date startTime, Date endDate, Date endTime, String device) {
        System.out.println("PacketCounter :: getTotalPackets :: startDate :: " + startDate + " :: startTime :: " + startTime + " :: endDate :: " + endDate + " :: endTime :: " + endTime + " :: device :: " + device);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.NETWORK_FILE)))) {

            String line = null;
            String[] header = null;
            Long count = 0L;

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.COLLECTL_DATE_PATTERN);
            SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.COLLECTL_TIME_PATTERN);

            while((line = br.readLine()) != null) {
                if(line.startsWith(Constants.HASH)) {
                    header = line.substring(1).split(" ");
                } else {
                    Map<String, String> map = commonUtil.createMap(header, line.split(" "));
                    System.out.println("Input line map: " + map);
                    Date lineDate = dateFormat.parse(map.get(Constants.DATE_COLUMN));
                    Date lineTime = timeFormat.parse(map.get(Constants.TIME_COLUMN));

                    if(lineDate.after(startDate) && lineDate.before(endDate) && lineTime.after(startTime) && lineTime.before(endTime)) {
                        System.out.println("Valid");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
