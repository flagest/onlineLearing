package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 监听MQ，接受页面发布的消息
 *
 * @author wu on 2020/2/7 0007
 */
@Component
public class ConsumerPostPage {
    @Autowired
    PageService pageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg) {
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        //得到消息中的页面id
        String pageId = (String) map.get("pageId");
        //校验页面是否合法
        CmsPage cmsPage = pageService.findCmsPageById(pageId);
        if (cmsPage == null) {
            LOGGER.error("recive postpage is empty，pageId{}", pageId);
            return;
        }
        //调用PageServer中的方法把将页面GradFs下载到服务器
        pageService.savePageToServerPath(pageId);

    }
}
