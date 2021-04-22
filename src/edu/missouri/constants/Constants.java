package edu.missouri.constants;

public class Constants {

    public static final String HASH = "#";
    public static final String DIR_SEPARATOR = "/";
    public static final String DATE_COLUMN = "Date";
    public static final String TIME_COLUMN = "Time";
    public static final String START_DATE = "startDate";
    public static final String START_TIME = "startTime";
    public static final String END_DATE = "endDate";
    public static final String END_TIME = "endTime";
    public static final String DEVICE = "device";
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String COLLECTL_DATE_PATTERN = "yyyyMMdd";
    public static final String COLLECTL_TIME_PATTERN = "HH:mm:ss";

    public static final String DATA_DIR = "/mydata/EVA_Space/Live-Collectl/Data"; // To-Do: Should be read from a conf file.
    public static final String CPU_FILE = DATA_DIR + DIR_SEPARATOR + "vm0-20210322.cpu"; // To-Do: Should be read from a conf file.
    public static final String NETWORK_FILE = DATA_DIR + DIR_SEPARATOR + "vm0-20210322.net"; // To-Do: Should be read from a conf file.


    
}
