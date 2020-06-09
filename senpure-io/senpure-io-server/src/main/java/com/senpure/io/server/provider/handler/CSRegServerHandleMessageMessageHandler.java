package com.senpure.io.server.provider.handler;

import com.senpure.base.util.Spring;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.protocol.message.CSRegServerHandleMessageMessage;
import io.netty.channel.Channel;

/**
 * CSRegServerHandleMessageMessageHandler
 *
 * @author senpure
 * @time 2019-07-26 15:36:12
 */
public class CSRegServerHandleMessageMessageHandler extends AbstractInnerMessageHandler<CSRegServerHandleMessageMessage> {
    @Override
    public void execute(Channel channel, long token, long playerId, CSRegServerHandleMessageMessage message) {

        if (message.isSuccess()) {
            logger.info("{} 注册服务成功 {}", ChannelAttributeUtil.getLocalServerKey(channel), channel);

            if (message.getMessage() != null) {
                logger.info("\n{}", message.getMessage());
            }
        } else {
            logger.error("{} 注册服务失败 {}", ChannelAttributeUtil.getLocalServerKey(channel), channel);
            if (message.getMessage() != null) {
                logger.error("\n{}", message.getMessage());
            }
            Spring.exit();
        }

    }

    @Override
    public int handlerId() {
        return CSRegServerHandleMessageMessage.MESSAGE_ID;
    }

    @Override
    public CSRegServerHandleMessageMessage getEmptyMessage() {
        return new CSRegServerHandleMessageMessage();
    }
}
