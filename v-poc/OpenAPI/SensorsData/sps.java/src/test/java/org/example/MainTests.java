package org.example;

import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.example.utils.JacksonUtils;
import org.example.utils.Md5Utils;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainTests {

    @Test
    public void fileUpload() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        File file = new File("./data/input/test.txt");

        HttpUrl url = HttpUrl.parse("http://10.129.20.17:8107/api/v2/sps/file/upload").newBuilder()
                .addQueryParameter("project", "default")
                .addQueryParameter("token", "83ad0a3c719da0dfd250861e219427bdf6027540ef5db4ed97c5343be1b7abaf")
                .addQueryParameter("file_size", String.valueOf(file.length()))
                .addQueryParameter("slice_amount", String.valueOf(1))
                .addQueryParameter("index", String.valueOf(0))
                .addQueryParameter("file_name", "test.txt")
                .addQueryParameter("file_md5", Md5Utils.md5(file))
                .addQueryParameter("current_md5", Md5Utils.md5(file))
                .build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","test.txt", RequestBody.create(MediaType.parse("text/plain"), file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void tagUpload() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        HttpUrl url = HttpUrl.parse("http://10.129.20.17:8107/api/v2/sps/user_tags/upload").newBuilder()
                .addQueryParameter("project", "default")
                .addQueryParameter("token", "83ad0a3c719da0dfd250861e219427bdf6027540ef5db4ed97c5343be1b7abaf")
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("name", "zxc");
        map.put("cname", "自行车");
        map.put("data_type", "STRING");
        map.put("source_type", 5);
        map.put("status", "RUNNING");
        map.put("dir_id", 1);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("upload_filename", "dGVzdC50eHQCEqxgrAQi");
        map1.put("matched_field", "user.$first_id");
        map1.put("sync_profile", true);
        map1.put("upload_type", "CREATE");
        map1.put("data_format", "JSON");

        map.put("upload_content", map1);

        RequestBody body = RequestBody.create(JacksonUtils.object2Json(map), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void dir() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        HttpUrl url = HttpUrl.parse("http://10.129.20.17:8107/api/v2/sps/user_tag_dir").newBuilder()
                .addQueryParameter("project", "default")
                .addQueryParameter("token", "83ad0a3c719da0dfd250861e219427bdf6027540ef5db4ed97c5343be1b7abaf")
                .addQueryParameter("need_user_tag", String.valueOf(false))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void test() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/input/test.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void chunk() throws Exception {
        File inputFile = new File("./data/input/gen.txt");
        double MB = inputFile.length() / (1024.0 * 1024.0);
        int partCount = (int) (MB / 100) + ((MB % 100) > 0 ? 1 : 0);

        for (int i = 0; i < partCount; i++) {
            try (
                    RandomAccessFile raf = new RandomAccessFile(inputFile, "r");
                    FileOutputStream fos = new FileOutputStream("./data/output/test_" + i + ".part");
                    BufferedOutputStream bos = new BufferedOutputStream(fos)
            ) {
                long offset = (long) i * 100 * 1024 * 1024;
                raf.seek(offset);
                byte[] buffer = new byte[1024];
                int bytesRead;

                long end = (long) (i + 1) * 100 * 1024 * 1024;

                while ((bytesRead = raf.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                    offset += bytesRead;
                    if (offset >= end) {
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void fileGenerator() {
        String filePath = "./data/input/gen.txt";
        int fileSizeInBytes = 1000 * 1024 * 1024; // 100MB
        int lineSize = 100; // 假设每行日志大约100字节
        int numberOfLines = fileSizeInBytes / lineSize;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < numberOfLines; i++) {
                String logEntry = dateFormat.format(new Date()) + " - Log message " + i + "\n";
                writer.write(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
