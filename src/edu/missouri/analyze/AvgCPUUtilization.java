package edu.missouri.analyze;

import edu.missouri.constants.Constants;
import edu.missouri.util.CommonUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AvgCPUUtilization {

    private static AvgCPUUtilization instance = null;
    private static CommonUtil commonUtil = null;

    private AvgCPUUtilization() {

    }

    public static AvgCPUUtilization getInstance() {
        if(instance == null) {
            instance = new AvgCPUUtilization();
            commonUtil = CommonUtil.getInstance();
        }
        return instance;
    }

    public int getAvgCPUUtilization(Date startDate, Date startTime, Date endDate, Date endTime) {
        System.out.println("AvgCPUUtilization :: getAvgCPUUtilization :: startDate :: " + startDate + " :: startTime :: " + startTime + " :: endDate :: " + endDate + " :: endTime :: " + endTime);

        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.CPU_FILE)))) {

            String line = null;
            String[] header = null;
            int count = 0;
            int total = 0;
            int numOfCPUs = commonUtil.getNoOfCPUs();

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.COLLECTL_DATE_PATTERN);
            SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.COLLECTL_TIME_PATTERN);

            while((line = br.readLine()) != null) {
                if(line.startsWith(Constants.HASH)) {
                    header = line.substring(1).split(Constants.SPACE);
                } else {
                    Map<String, String> map = commonUtil.createMap(header, line.split(" "));
                    Date lineDate = dateFormat.parse(map.get(Constants.DATE_COLUMN));
                    Date lineTime = timeFormat.parse(map.get(Constants.TIME_COLUMN));

                    if((lineDate.equals(startDate) || lineDate.after(startDate)) && (lineDate.equals(endDate) || lineDate.before(endDate)) && lineTime.after(startTime) && lineTime.before(endTime)) {
                        for(int i=0; i<numOfCPUs; i++) {
                            String val = map.get(Constants.SQUARE_OPEN_BRACKET + Constants.CPU + Constants.COLON + i + Constants.SQUARE_CLOSE_BRACKET + Constants.USER + Constants.PERCENT);
                            count += Integer.parseInt(val);
                        }
                        total += 1;
                    }
                }
            }

            int avgVal = count/total;
            System.out.println("AvgCPUUtilization :: getAvgCPUUtilization :: avgVal :: " + avgVal);

            return avgVal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
