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
    private boolean hasHeader;
    private HashMap<String,String> headers;
    private HashMap<String,String> responseHeaders;
    private String massageBodyType;
    private HashMap<String,String> massageBodyFormData;
    private String uploadPath;
    private String responseBody;
    private boolean followRedirect;
    private int responseCode;
    private String responseMassage;
    private float time;
    private long length;

    /**
     * This is first constructor of this class and initialized our fields.
     */
    public Request(){
        URL="";
        hasSave=false;
        showResponseHeader=false;
        hasResponseBodySave=false;
        hasHeader=false;
        headers=new HashMap<>();
        responseHeaders=new HashMap<>();
        massageBodyType="multipart form";
        massageBodyFormData=new HashMap<>();
        followRedirect=false;
        responseCode=0;
        responseMassage="";
        time=0;
        length=0;
        uploadPath="";
    }

    /**
     * This is second constructor of this class and get two parameter.
     * @param name is name of request
     * @param requestMethod is request method of this class
     */
    public Request(String name, String requestMethod){
        this();
        this.name=name;
        this.requestMethod=requestMethod;
    }

    /**
     * This is setter method for massageBodyType field.
     * @param massageBodyType is a string as massage body type
     */
    public void setMassageBodyType(String massageBodyType) {
        this.massageBodyType = massageBodyType;
    }

    /**
     * Getter method of responseHeaders field
     * @return HashMap of ResponseHeaders
     */
    public HashMap<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Getter method of uploadPath field
     * @return path or text of uploadPath
     */
    public String getUploadPath() {
        return uploadPath;
    }

    /**
     * This is setter method for uploadPath field.
     * @param uploadPath is a string as upload path
     */
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * Getter method of HasSave field
     * @return a boolean as answer
     */
    public boolean isHasSave() {
        return hasSave;
    }

    /**
     * This is setter method for hasSave field.
     * @param hasSave is a boolean as answer
     */
    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    /**
     * Getter method of showResponseHeader field
     * @return a boolean as answer
     */
    public boolean isShowResponseHeader() {
        return showResponseHeader;
    }

    /**
     * This is setter method for showResponseHeader field.
     * @param showResponseHeader is a boolean as answer
     */
    public void setShowResponseHeader(boolean showResponseHeader) {
        this.showResponseHeader = showResponseHeader;
    }

    /**
     * This is setter method for responseHeaders field.
     * @param responseHeaders is a hash map for set to the field
     */
    public void setResponseHeaders(HashMap<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * Getter method of hasResponseBodySave field
     * @return a boolean as answer
     */
    public boolean isHasResponseBodySave() {
        return hasResponseBodySave;
    }

    /**
     * This is setter method for responseCode field.
     * @param responseCode is an integer as response code
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * This is setter method for responseMassage field.
     * @param responseMassage is a string as response massage
     */
    public void setResponseMassage(String responseMassage) {
        this.responseMassage = responseMassage;
    }

    /**
     * This is setter method for hasResponseBodySave field.
     * @param hasResponseBodySave is a boolean as answer
     */
    public void setHasResponseBodySave(boolean hasResponseBodySave) {
        this.hasResponseBodySave = hasResponseBodySave;
    }

    /**
     * Getter method of name field
     * @return a string as name of request
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method of requestMethod field
     * @return a string as name of request
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * This is setter method for requestMethod field.
     * @param requestMethod is a string as request Method
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Getter method of URL field
     * @return a string as URL of request
     */
    public String getURL() {
        return URL;
    }

    /**
     * Getter method of massageBodyType field
     * @return a string as URL of request
     */
    public String getMassageBodyType() {
        return massageBodyType;
    }

    /**
     * This is setter method for URL field.
     * @param URL is a string as URL of request
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * Getter method of massageBodyFormData field
     * @return a string as massage Body Form Data of request
     */
    public HashMap<String, String> getMassageBodyFormData() {
        return massageBodyFormData;
    }

    /**
     * This is setter method for massageBodyFormData field.
     * @param massageBodyFormData is a hash map as massage Body Form Data
     */
    public void setMassageBodyFormData(HashMap<String, String> massageBodyFormData) {
        this.massageBodyFormData = massageBodyFormData;
    }

    /**
     * Getter method of headers field
     * @return a hash map as headers of request
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Getter method of responseCode field
     * @return a response code of request
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Getter method of time field
     * @return a float as length of time of request
     */
    public float getTime() {
        return time;
    }

    /**
     * Getter method of time field
     * @return a long as length of load and upload
     */
    public long getLength() {
        return length;
    }

    /**
     * Getter method of responseBody field
     * @return a string as response body of request
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * This is setter method for responseBody field.
     * @param responseBody is a string as response body
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /**
     * Getter method of responseMassage field
     * @return a string as response massage of request
     */
    public String getResponseMassage() {
        return responseMassage;
    }

    /**
     * This is setter method for headers field.
     * @param headers is a hash map as headers request
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }
}
