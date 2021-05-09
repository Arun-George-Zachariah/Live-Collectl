package edu.missouri.util;

import edu.missouri.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonUtil {
    private static CommonUtil instance = null;

    private CommonUtil() {

    }

    public static CommonUtil getInstance() {
        if(instance == null) {
            instance = new CommonUtil();
        }
        return instance;
    }

    public static Map<String, String> createMap(String[] header , String[] data) {
        Map<String, String> map = new LinkedHashMap();
        for(int i=0; i<header.length; i++) {
            map.put(header[i], data[i]);
        }
        return map;
    }

    public static int getNoOfCPUs() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.CPU_FILE)))) {

            String line = null;

            while((line = br.readLine()) != null) {
                if(line.startsWith(Constants.HASH + Constants.SPACE + Constants.SUB_SYS_FIELD)) {
                    String[] splits = line.substring(1).split(Constants.COLON);
                    for(int i=0; i<splits.length; i++) {
                        System.out.println("splits[i] :: " + splits[i]);
                        if(splits[i].contains(Constants.NO_OF_CPUS_FIELD)) {
                            String count = splits[i+1].replaceAll("\\D+","");
                            System.out.println("count::" + count + "::");
                            return 0;
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
