package org.example.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * <a href="https://github.com/FasterXML/jackson-dataformat-xml/wiki/Jackson-XML-annotations">jackson xml 注解文档</a>
 */
public class XmlUtilsTests {

    @Test
    public void map2Xml() throws JsonProcessingException {
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("mobile", "13222222222");
        transaction.put("smscontent", " 测试 ");
        transaction.put("businesskind", "Z");
        transaction.put("sysflag", "99990001");
        transaction.put("tradetime", "20181115121030");

        XmlMapper xmlMapper = XmlMapper.xmlBuilder()
                .defaultUseWrapper(true)
                .build();

        System.out.println(xmlMapper.writeValueAsString(transaction));
    }

    @Test
    public void obj2Xml() throws JsonProcessingException, UnsupportedEncodingException {
        TestXmlModel model = new TestXmlModel("qwe", "zxc");
        XmlMapper xmlMapper = XmlMapper.xmlBuilder().build();
        String str = xmlMapper.writeValueAsString(model);
        byte[] bytesInUtf8 = str.getBytes();
        System.out.println(new String(bytesInUtf8, "GBK"));
    }
}
