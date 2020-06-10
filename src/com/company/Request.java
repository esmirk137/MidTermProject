package com.company;

import org.json.JSONObject;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represent a request and implement.
 * Name of my app is "Predigest".
 * @author Sayed Mohammad Ali Mirkazemi
 * @version 1.0.0
 * @since 6/10/2020
 */
public class Request implements Serializable {
    private String name;
    private String requestMethod;
    private String URL;
    private boolean hasSave;
    private boolean showResponseHeader;
    private boolean hasResponseBodySave;
    private HashMap<String,String> headers;
    private HashMap<String,String> responseHeaders;
    private String massageBodyType;
    private HashMap<String,String> massageBodyFormData;
    private String uploadPath;
    private JSONObject jsonObject;
    private String responseBody;
    private boolean followRedirect;
    private int responseCode;
    private String responseMassage;
    private float time;
    private long length;


    public Request(){
        URL="";
        hasSave=false;
        showResponseHeader=false;
        hasResponseBodySave=false;
        headers=new HashMap<>();
        responseHeaders=new HashMap<>();
        massageBodyType="multipart form";
        massageBodyFormData=new HashMap<>();
        jsonObject=new JSONObject();
        followRedirect=false;
        responseCode=0;
        responseMassage="";
        time=0;
        length=0;
        uploadPath="";
    }

    public Request(String name, String requestMethod){
        this();
        this.name=name;
        this.requestMethod=requestMethod;
    }

    public void setMassageBodyType(String massageBodyType) {
        this.massageBodyType = massageBodyType;
    }

    public HashMap<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public boolean isHasSave() {
        return hasSave;
    }

    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    public boolean isShowResponseHeader() {
        return showResponseHeader;
    }

    public void setShowResponseHeader(boolean showResponseHeader) {
        this.showResponseHeader = showResponseHeader;
    }

    public void setResponseHeaders(HashMap<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public boolean isHasResponseBodySave() {
        return hasResponseBodySave;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMassage(String responseMassage) {
        this.responseMassage = responseMassage;
    }

    public void setHasResponseBodySave(boolean hasResponseBodySave) {
        this.hasResponseBodySave = hasResponseBodySave;
    }

    public String getName() {
        return name;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getURL() {
        return URL;
    }

    public String getMassageBodyType() {
        return massageBodyType;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public HashMap<String, String> getMassageBodyFormData() {
        return massageBodyFormData;
    }

    public void setMassageBodyFormData(HashMap<String, String> massageBodyFormData) {
        this.massageBodyFormData = massageBodyFormData;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public float getTime() {
        return time;
    }

    public long getLength() {
        return length;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseMassage() {
        return responseMassage;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
