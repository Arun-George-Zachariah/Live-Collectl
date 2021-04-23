package edu.missouri.consumer;

import edu.missouri.constants.Constants;
import edu.missouri.util.CommonUtil;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class StreamingConsumer implements Runnable {

    protected BlockingQueue<String> queue;
    private Session session;
    private CommonUtil commonUtil;

    public StreamingConsumer(BlockingQueue<String> queue, Session session) {
        this.queue = queue;
        this.session = session;
        commonUtil = CommonUtil.getInstance();
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
                    Map<String, String> map = commonUtil.createMap(header, line.split(" "));
                    System.out.println(map);
                }
                session.getBasicRemote().sendText(line);
            } catch (Exception e) {
                System.out.println("StreamingConsumer :: run ::  Exception encountered :: " + e);
                e.printStackTrace();

            }
        }
    }

}