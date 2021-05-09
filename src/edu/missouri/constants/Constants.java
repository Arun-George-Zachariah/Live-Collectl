package edu.missouri.constants;

public class Constants {

    public static final String HASH = "#";
    public static final String DIR_SEPARATOR = "/";
    public static final String SQUARE_OPEN_BRACKET = "[";
    public static final String SQUARE_CLOSE_BRACKET = "]";
    public static final String COLON = ":";
    public static final String SPACE = " ";
    public static final String PERCENT = "%";

    public static final String SUB_SYS_FIELD = "SubSys";
    public static final String NO_OF_CPUS_FIELD = "NumCPUs";

    public static final String DATE_COLUMN = "Date";
    public static final String TIME_COLUMN = "Time";
    public static final String START_DATE = "startDate";
    public static final String START_TIME = "startTime";
    public static final String END_DATE = "endDate";
    public static final String END_TIME = "endTime";
    public static final String DEVICE = "device";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String COLLECTL_DATE_PATTERN = "yyyyMMdd";
    public static final String COLLECTL_TIME_PATTERN = "HH:mm:ss";
    public static final String NET = "NET";
    public static final String RECEIVED_PACKET = "RxPkt";
    public static final String TRANSMITTED_PACKET = "TxPkt";
    public static final String CPU = "CPU";

    public static final String PROPERTY_FILE = "config.txt";

    public static final String DATA_DIR = "/mydata/Live-Collectl/Data"; // To-Do: Should be read from a conf file.
    public static final String CPU_FILE = DATA_DIR + DIR_SEPARATOR + "vm0-20210322.cpu"; // To-Do: Should be read from a conf file.
    public static final String NETWORK_FILE = DATA_DIR + DIR_SEPARATOR + "vm0-20210322.net"; // To-Do: Should be read from a conf file.


    
}
