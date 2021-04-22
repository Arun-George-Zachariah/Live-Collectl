package edu.missouri.consumer;

import edu.missouri.constants.Constants;

import javax.websocket.Session;
import java.util.concurrent.BlockingQueue;

public class StreamingConsumer implements Runnable {

    protected BlockingQueue<String> queue;
    private Session session;

    public StreamingConsumer(BlockingQueue<String> queue, Session session) {
        this.queue = queue;
        this.session = session;
    }

    @Override
    public void run() {
        System.out.print("StreamingConsumer :: run :: Start");
        while(true) {
            try {
                String line = queue.take();
                String[] header = null;
                if(line.startsWith(Constants.HASH)) {
                    header = line.substring(1).split(" ");
                } else {
                    System.out.println(line);
                }
                session.getBasicRemote().sendText(line);
            } catch (Exception e) {
                System.out.println("StreamingConsumer :: run ::  Exception encountered :: " + e);
                e.printStackTrace();

            }
        }
    }

}