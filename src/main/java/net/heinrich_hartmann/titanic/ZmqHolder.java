package net.heinrich_hartmann.titanic;

import org.apache.log4j.Logger;
import org.jeromq.ZMQ;
import org.junit.Assert;

import java.io.IOException;

/**
 * Created by hartmann on 2/9/14.
 */
public class ZmqHolder implements AutoCloseable {
    private Logger LOG = Logger.getLogger(ZmqHolder.class);

    private ZMQ.Context ctx;
    private ZMQ.Socket socket;

    public ZmqHolder(ZmqConfig cfg) throws ZmqException {
        try {

        ctx = ZMQ.context();
        Assert.assertNotNull(ctx);

        socket  = ctx.socket(ZMQ.PULL);
        Assert.assertNotNull(socket);

        socket.setReceiveTimeOut(1000);

        int suc = socket.bind(cfg.getUrl());
        Assert.assertNotEquals(suc, -1);

        } catch (AssertionError e){
            throw new ZmqException(e);
        }
    }

    @Override
    public void close() {
        socket.close();
        ctx.term();
    }

    public String recv() throws InterruptedException {
        String msg = socket.recvStr();

        // if (msg == null) throw new InterruptedException("Recv Interrupted");

        LOG.info("Received message: " + msg);

        return msg;
    }

    public class ZmqException extends IOException {
        public ZmqException(String s, Exception e) {
            super(s, e);
        }

        public ZmqException(AssertionError e) {
            super(e);
        }
    }
}
