package edu.missouri.stream;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import javax.websocket.Session;

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
                    session.getBasicRemote().sendText("result");

                }
            } catch (Exception e) {
                System.out.println("StreamingConsumer :: run ::  Exception encountered :: " + e);
                e.printStackTrace();

            }
        }
    }

}