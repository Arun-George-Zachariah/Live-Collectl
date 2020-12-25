package edu.missouri.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import edu.missouri.constants.Constants;
import org.apache.commons.io.input.TailerListenerAdapter;

@ServerEndpoint("/discStream")
public class StreamingProducer extends TailerListenerAdapter {

    static {
        System.setProperty("log4j.configurationFile", "log4j2.properties");
    }

    private static Set<Session> allSessions;
    private static boolean isInitialized = false;
    private static BlockingQueue<String> queue;

    private void init() {
        if(!isInitialized) {
            isInitialized = true;
            queue = new ArrayBlockingQueue<String>(2000000000);
            StreamingConsumer consumer = new StreamingConsumer(queue, allSessions);
            new Thread(consumer).start();
        }
    }

    @OnOpen
    public void streamData(Session session) {
        System.out.println("StreamingProducer :: streamData ::  Start");
        allSessions = session.getOpenSessions();
        init();
        if (allSessions != null && allSessions.size() != 0) {
            try(BufferedReader br = new BufferedReader(new FileReader(new File(Constants.DATA_FILE)))) {
                while(true) {
                    Thread.sleep(1000); // TO-DO: Make the refresh rate configurable.
                    String line = null;
                    while((line = br.readLine()) != null) {
                        queue.put(line);
                    }
                }
            } catch(Exception e) {
                System.out.println("StreamingProducer :: streamData ::  Exception :: " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(String line) {
        try {
            queue.put(line);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
