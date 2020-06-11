package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * This class shows how to send a DELETE Request using 'HttpDelete' method of Apache HttpClient library.
 * @author Deepak Verma
 */

public class Delete_Request_Apache_HttpClient {
    public static void main(String[] args) {
        try {
            String boundary = System.currentTimeMillis() + "";
            URL url = new URL("http://apapi.haditabatabaei.ir/tests/put/formdata/222");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            HashMap<String,String> hashMapp=new HashMap<>();
            hashMapp.put("aaaaa","bbbbb");
            hashMapp.put("ccccc","ddddd");
            bufferOutFormData(hashMapp, boundary, bufferedOutputStream);
            System.out.println(connection.getHeaderField(0));
            HashMap<String,String> hashMap=new HashMap<>();
            for (int i = 1; i < 10; i++) {
                if(connection.getHeaderFieldKey(i)==null) continue;
                hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
            }
            StringBuilder stringBuilder=new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
            System.out.println(stringBuilder);
        } catch (ProtocolException e) {
            System.out.println("Protocol exception...!");
        } catch (IOException e) {
            System.out.println("IOException...!"+e);
        }
    }
    /**
     * This method fix body form data to the right format.
     * @param body is body of request
     * @param boundary is a custom boundary
     * @param bufferedOutputStream is buffered out put stream of http url connection
     * @throws IOException is a main exception related to input and out exception.
     */
    private static void bufferOutFormData(@NotNull HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}