package edu.missouri.consumer;

import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class StreamingConsumer implements Runnable {

    protected BlockingQueue<String> queue;
    private Set<Session> allSessions;

    public StreamingConsumer(BlockingQueue<String> queue, Set<Session> allSessions) {
        this.queue = queue;
        this.allSessions = allSessions;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String line = queue.take();
                for (Session session : allSessions) {
                    if(line.charAt(0) != '#') {

                    }
                    session.getBasicRemote().sendText("result");

                }
            } catch (Exception e) {
                System.out.println("StreamingConsumer :: run ::  Exception encountered :: " + e);
                e.printStackTrace();

            }
        }
    }

}