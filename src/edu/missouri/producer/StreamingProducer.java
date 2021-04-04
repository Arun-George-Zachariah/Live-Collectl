package edu.missouri.producer;

import edu.missouri.constants.Constants;
import edu.missouri.consumer.StreamingConsumer;
import org.apache.commons.io.input.TailerListenerAdapter;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@ServerEndpoint("/CPU")
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
            queue = new ArrayBlockingQueue<String>(2000); // TO-DO: Make the queue size as configurable.
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
            try(BufferedReader br = new BufferedReader(new FileReader(new File(Constants.CPU_FILE)))) {
                while(true) {
                    Thread.sleep(10); // TO-DO: Make the refresh rate configurable.

                    String line = null;
                    String[] header = null;

                    while((line = br.readLine()) != null) {
                        if(line.startsWith(Constants.HASH)) {
                            header = line.substring(1).split(" ");
                        } else {
                            System.out.println(line);
                            break;
                        }
                    }
                }
            } catch(Exception e) {
                System.out.println("StreamingProducer :: streamData ::  Exception :: " + e);
                e.printStackTrace();
            }
        }
    }
}
