package cn.nj.rocketmqprovider.controller;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author ：zty
 * @date ：Created in 2021/4/23 18:03
 * @description ：
 */
@RestController
public class ProviderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String send() throws MQClientException {
        //创建DefaultMQProducer消息生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        //设置NameServer节点地址，多个节点间用分号分割
        producer.setNamesrvAddr("192.168.31.200:9876;192.168.31.201:9876");
        //与NameServer建立长连接
        producer.start();
        try {
            //发送一百条数据
            for (int i = 0; i < 100; i++) {
                //数据正文
                String data = "{\"title\":\"X市2021年度第一季度税务汇总数据\"}";
                /*
                    Message消息三个参数
                        topic 代表消息主题，自定义为tax-data-topic说明是税务数据
                        tags 代表标志，用于消费者接收数据时进行数据筛选。2021S1代表2021年第一季度数据
                        body 代表消息内容
                 */
                Message message = new Message();
                //topic
                message.setTopic("tax-data-topic");
                //tags
                message.setTags("2021S1");
                //body
                message.setBody(data.getBytes(StandardCharsets.UTF_8));
                //发送消息，获取发送结果
                SendResult result = producer.send(message);
                //将发送结果对象打印在控制台
                logger.info("消息已发送：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (RemotingException remotingException) {
            remotingException.printStackTrace();
        } catch (MQBrokerException mqBrokerException) {
            mqBrokerException.printStackTrace();
        }finally {
            producer.shutdown();
        }

        return "success";
    }


}
