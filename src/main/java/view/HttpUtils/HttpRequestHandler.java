package view.HttpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {

    public Map<String, String> sendPost(String resource, String requestJson) {
        Map<String, String> result = new HashMap<>();
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("http://localhost:8888/" + resource);
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");

        String responseJson = null;
        String statusCode = null;

        try {
            if (requestJson != null) {
                StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                httpPost.setEntity(requestEntity);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final HttpEntity responseEntity = response.getEntity();
                responseJson = EntityUtils.toString(responseEntity);
                statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            }
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(statusCode);
        result.put("statusCode", statusCode);
        result.put("json", responseJson);
        return result;
    }

    public String sendGet(String resource) {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpUriRequest httpGet = new HttpGet("http://localhost:8888/" + resource);

        String responseJson = null;
        try {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final HttpEntity entity1 = response.getEntity();
                responseJson = EntityUtils.toString(entity1);
            }
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseJson;
    }
}
