package com.bird.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateHelper {

    private static Logger       logger      = LoggerFactory.getLogger(TemplateHelper.class);
    private static final String SAY_HI_PATH = "sayHi.vm";

    public static void main(String[] args) throws IOException {
        URL clzPath = TemplateHelper.class.getResource("");
        logger.info("当前类所在路径：" + clzPath.getPath());
        URL u = TemplateHelper.class.getResource("/");
        logger.info("classpath路径：" + u.getPath());

        new TemplateHelper().sayHi();
    }

    public void sayHi() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("input.encoding", "UTF-8");
        engine.setProperty("output.encoding", "UTF-8");
        // 下面两行代码是等价的
        // engine.setProperty("file.resource.loader.path", this.getClass().getResource("/").getPath());
        engine.setProperty("file.resource.loader.class",
                           "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        //
        engine.init();
        Template template = engine.getTemplate(SAY_HI_PATH);
        VelocityContext context = new VelocityContext();
        context.put("welcome", "欢迎");
        context.put("userName", "系统管理员");

        List<String> randList = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            randList.add(RandomStringUtils.randomAlphanumeric(20));
        }
        context.put("randList", randList);
        Writer writer = new StringWriter();
        template.merge(context, writer);
        logger.info(writer.toString());
    }

}
