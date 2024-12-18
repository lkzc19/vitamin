package org.example;

public class CompressTest {

//    @Test
//    public void testCompress() throws IOException {
//        String compressedBase64String = "\\n�\\u0002\\n\\tdevice_id\\u0012\\u000F417abcabcabcbac\\u001A\\u000F417abcabcabcbac*\\u0006lkzc190\\u0001@���2J\\u0011com.growingio.app�\\u0001\\u0005zh_CN�\\u0001\\u00051.2.4�\\u0001\\beventKey�\\u0001\\u0019\\n\\fproduct_name\\u0012\\tcdp苹果�\\u0001\\\"\\n\\u0010product_classify\\u0012\\u000E苹果||香蕉�\\u0001\\u0011\\n\\u0006itemId\\u0012\\u0007itemKey�\\u0001\\u0018growing.123c12fb12f123cc�\\u0002\\u0006google�\\u0002\\u0007Nexus 5�\\u0002\\u0005PHONE�\\u0002\\f看数助手�\\u0003\\u0006lkzc19�\\u0003���2�\\u0003\\u0007account";
//
//        try {
//            // 将Base64字符串解码为字节
//            byte[] compressedBytes = compressedBase64String.getBytes(StandardCharsets.UTF_8);
//
//            // 使用ByteArrayInputStream来包装字节
//            try (InputStream in = new ByteArrayInputStream(compressedBytes);
//                 CompressorInputStream cin = new CompressorStreamFactory().createCompressorInputStream(in)) {
//
//                // 解压缩数据到ByteArrayOutputStream
//                ByteArrayOutputStream bout = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int n = 0;
//                while (-1 != (n = cin.read(buffer))) {
//                    bout.write(buffer, 0, n);
//                }
//
//                // 获取解压缩后的字符串
//                String decompressedString = bout.toString("UTF-8");
//                System.out.println(decompressedString);
//            }
//        } catch (IOException | CompressorException e) {
//            e.printStackTrace();
//        }
//
//    }
}
