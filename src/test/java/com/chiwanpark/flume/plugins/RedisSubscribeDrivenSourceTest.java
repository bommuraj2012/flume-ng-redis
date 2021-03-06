package com.chiwanpark.flume.plugins;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.apache.flume.*;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.channel.MemoryChannel;
import org.apache.flume.channel.ReplicatingChannelSelector;
import org.apache.flume.conf.Configurables;
import org.apache.flume.source.AbstractSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

@RunWith(JUnit4.class)
public class RedisSubscribeDrivenSourceTest {

    private Logger logger = LoggerFactory.getLogger(RedisSubscribeDrivenSourceTest.class);
    private Context context = new Context();
    private Channel channel = new MemoryChannel();
    private ChannelSelector channelSelector = new ReplicatingChannelSelector();
    private AbstractSource source;

    @Before
    public void setUp() throws Exception {
        context.clear();
        context.put("redisChannel", "flume-ng-redis-test");

        Configurables.configure(channel, context);
        channelSelector.setChannels(Lists.newArrayList(channel));

        source = new RedisSubscribeDrivenSource();
        source.setChannelProcessor(new ChannelProcessor(channelSelector));
        Configurables.configure(source, context);

        logger.info("Try to start RedisSubscribeDrivenSource.");
        source.start();
        Thread.sleep(1000);
    }

    @After
    public void tearDown() throws Exception {
        source.stop();
    }

    @Test
    public void testSubscribe() throws Exception {
        String message = "testSubscribeMessage!";

        logger.info("Try to send message to redis source.");
        sendMessageToRedis("flume-ng-redis-test", message);
        Thread.sleep(2000);

        Assert.assertEquals(message, getMessageFromChannel());
    }

    private void sendMessageToRedis(String channel, String message) throws Exception {
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.publish(channel, message);
    }

    private String getMessageFromChannel() throws Exception {
        Transaction transaction = channel.getTransaction();
        try {
            transaction.begin();

            Event event;

            do {
                event = channel.take();
            } while (event == null);
            transaction.commit();

            return new String(event.getBody(), "UTF-8");
        } finally {
            transaction.close();
        }
    }
}
