package edu.missouri.util;

import edu.missouri.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PlotUtil {
    private static PlotUtil instance = null;

    private PlotUtil() {

    }

    public static PlotUtil getInstance() {
        if(instance == null) {
            instance = new PlotUtil();
        }
        return instance;
    }

    public static void getCPUPlot() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Constants.CPU_FILE)))) {
            String line = null;
            while((line = br.readLine()) != null) {
                if(!line.startsWith(Constants.HASH)) {
                    System.out.println(line);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
