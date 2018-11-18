package net.pipsi.java.pipsidiscordbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WebPage {

    private String userAgent;
    private String referrer;

    private int responseCode;
    private Map<String, List<String>> headers;
    private String body;

    public WebPage(String url) throws IOException {
        URL searchUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) searchUrl.openConnection();
        downloadWebPage(conn);
    }

    public WebPage(HttpURLConnection conn) throws IOException {
        downloadWebPage(conn);
    }

    private void downloadWebPage(HttpURLConnection conn) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (userAgent != null) {
            conn.setRequestProperty("User-Agent", userAgent);
        }
        if (referrer != null) {
            conn.setRequestProperty("Referer", referrer);
        }

        responseCode = conn.getResponseCode();
        headers = conn.getHeaderFields();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line).append("\n");
        }
        in.close();

        this.body = sb.toString();
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

}
