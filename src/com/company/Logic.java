package com.company;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is logic part of my project and send request to determinate url.
 * Name of my app is "Predigest".
 * @author Sayed Mohammad Ali Mirkazemi
 * @version 1.0.0
 * @since 6/10/2020
 */
public class Logic {
    private String massageBodyType;
    private boolean hasUnknownCommand;
    private ArrayList<Request> savedRequest;
    private String htmlFileName;

    /**
     * This is constructor of this class and initialized out fields.
     */
    public Logic(){
        massageBodyType="multipart form";
        hasUnknownCommand=false;
        savedRequest=new ArrayList<>();
        htmlFileName="";
    }

    /**
     * This is main method of this class and handle program and has trad with user.
     */
    public void handlerMethod(){
        while (true) {
            System.out.print(">Predigest ");
            Scanner scanner = new Scanner(System.in);
            String string=scanner.nextLine();
            ArrayList<String> arrayList;
            arrayList=tokenLine(string);
            HttpURLConnection connection;
            htmlFileName=new Date().toString();
            long starTime;
            String charset="UTF-8";
            Request request=new Request();
            request.setRequestMethod("GET");
            try {
                starTime=System.currentTimeMillis();
                URL url=new URL("http://"+arrayList.get(0));
                connection=(HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Charset",charset);
                boolean last=false;
                for (int i=1; i<arrayList.size(); i++) {
                    if(i==arrayList.size()-1) last=true;
                    switch (arrayList.get(i)){
                        case "-M":
                        case "--method":
                            request.setRequestMethod(arrayList.get(++i));
                            break;
                        case "-H":
                        case "--headers":
                            headers(token(arrayList.get(++i),':',';'),connection,request);
                            break;
                        case "-i":
                            request.setShowResponseHeader(true);
                            break;
                        case "-h":
                        case "--help":
                            help();
                            break;
                        case "-f":
                            followRedirect();
                            break;
                        case "-S":
                        case "--save":
                            request.setHasSave(true);
                            break;
                        case "-d":
                        case "--data:":
                            //by default is form data
                            data(token(arrayList.get(++i),'=','&'),request);
                            break;
                        case "-j":
                        case "--json":
                            massageBodyType="json";
                            request.setMassageBodyType("json");
                            json();
                            break;
                        case "-O":
                        case "--output":
                            request.setHasResponseBodySave(true);
                            if(!last && !arrayList.get(i+1).startsWith("-")){
                                htmlFileName=arrayList.get(++i);
                            }
                            break;
                        case "--upload":
                            massageBodyType="upload";
                            request.setUploadPath(arrayList.get(++i));
                            request.setMassageBodyType("upload");
                            break;
                        case "fire":
                            int counter=1;
                            while (true){
                                char c;
                                if(arrayList.get(i+counter).length()==1) {
                                    c = arrayList.get(i + counter).charAt(0);
                                    if(Character.isDigit(c)){
                                        setConnectionAfterLoadRequest(loadRequest());
                                    }
                                }
                                counter++;
                            }
                        default:
                            System.out.println("An unknown command find.");
                            hasUnknownCommand=true;
                    }
                }
                if(hasUnknownCommand) continue;
                long endTime=System.currentTimeMillis();
                long time=endTime-starTime;
                long length=connection.getContentLengthLong();
                System.out.println("Time: "+time+" ms");
                System.out.println("Length: "+length+" byte");
                send(connection,request);
            } catch (MalformedURLException e){
                System.out.println("Malformed URL exception...!");
                System.err.println(e);
            } catch (IOException e) {
                System.out.println("IOException...!");
                e.printStackTrace();
            }
        }

    }

    /**
     * This method apart commands in input line.
     * @param string in input line
     * @return commands partition form as array list of string.
     */
    @NotNull
    private ArrayList<String> tokenLine(@NotNull String string){
        ArrayList<String> arrayList=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0; i<string.length(); i++){
            if(string.charAt(i)==' '){
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            else {
                stringBuilder.append(string.charAt(i));
            }
        }
        arrayList.add(stringBuilder.toString());
        return arrayList;
    }

    /**
     * This method apart input headers and massage body (data form) pack.
     * @param string is headers pack as string
     * @param c1 is the character that exist between key value (key_c1_value).
     * @param c2 is the character that exist between key value (value_c2_key).
     * @return return a hash map as headers include key and value
     */
    @NotNull
    private HashMap<String,String> token(@NotNull String string, char c1, char c2){
        HashMap<String,String> headers=new HashMap<>();
        StringBuilder stringBuilder=new StringBuilder();
        String key="";
        for(int i=1; i<string.length()-1; i++){
            if(string.charAt(i)==c1){
                key=stringBuilder.toString();
                stringBuilder.setLength(0);
            }
            else if(string.charAt(i)==c2){
                headers.put(key,stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            else {
                stringBuilder.append(string.charAt(i));
            }
        }
        headers.put(key,stringBuilder.toString());
        return headers;
    }

    /**
     * Do task of command "-H" or "--headers".
     * @param headersHashMap is hash map of headers
     * @param connection is view Http URL Connection
     * @param request is current request
     */
    private void headers(@NotNull HashMap<String,String> headersHashMap, HttpURLConnection connection, Request request){
        for(String key:headersHashMap.keySet()){
            connection.setRequestProperty(key,headersHashMap.get(key));
        }
        request.setHeaders(headersHashMap);
    }

    /**
     * This method find .txt file in a folder.
     * @return these file as array
     */
    private File[] textFileFinder(String path){
        File dir = new File(path);
        return dir.listFiles((dir1, filename) -> filename.endsWith(".txt"));
    }

    /**
     * This method load request from text file.
     */
    @NotNull
    private Request loadRequest(){
        Request loadRequest=new Request();
        return loadRequest;
    }

    /**
     * This method create connection after loading a request.
     * @param request is current request
     * @return an HttpURLConnection
     */
    @Nullable
    private HttpURLConnection setConnectionAfterLoadRequest(@NotNull Request request){
        HttpURLConnection httpURLConnection;
        try {
            URL url=new URL(request.getURL());
            httpURLConnection=(HttpURLConnection) url.openConnection();
            //set header
            if(request.getHeaders().size()!=0) {
                for (String key : request.getHeaders().keySet()) {
                    httpURLConnection.setRequestProperty(key, request.getHeaders().get(key));
                }
            }
            //set body
            if(request.getRequestMethod().equals("POST") || request.getRequestMethod().equals("PUT")) {
                if (request.getMassageBodyType().equals("multipart form")) {

                } else if (request.getMassageBodyType().equals("json")) {

                } else {

                }
            }
            return httpURLConnection;
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL exception...!");
        } catch (IOException e) {
            System.out.println("IOException...!");
        }
        return null;
    }

    /**
     * Do task of command "-h" or "--help" and show requests of list.
     */
    private void help(){
        File[] files=textFileFinder("saveFaz2\\textFiles\\");
        if(files.length==0)
            System.out.println("There is no request for showing.");
        else
            for(File file:files){
                file.getPath(); //..............
            }

    }

    private void followRedirect(){
        //abstract
    }

    /**
     * Do task of command "-O" or "--output" and show requests of list.
     * Save response body in a html file.
     */
    private void saveResponseBody(@NotNull Request request){
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("saveFaz2\\htmlFiles\\"+htmlFileName+".html")); ){
            bufferedWriter.write(request.getResponseBody());
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"); //change to dialog massage
        } catch (IOException e) {
            System.out.println("saving process failed!"); //change to dialog massage
        }
    }

    /**
     * This method save a request in a txt file.
     * @param request is request that use wanna save
     */
    private void save(Request request){
        try(ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("saveFaz2\\hashMap.txt"))){
            objectOutputStream.writeObject(request);
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"); //change to dialog massage
        } catch (IOException e) {
            System.out.println("saving process failed!"); //change to dialog massage
        }
        savedRequest.add(request);
    }

    /**
     * This method
     * @param massageBodyFormData is a hash map as massage Body (Form Data)
     * @param request is current request
     */
    private void data(HashMap<String,String> massageBodyFormData, @NotNull Request request){
        request.setMassageBodyFormData(massageBodyFormData);
    }

    private void json(){
        //abstract
    }

    /**
     * This method is implementing of "GET" method for request.
     * @param connection is view Http URL Connection
     * @param request is current request
     */
    private void GET(@NotNull HttpURLConnection connection, @NotNull Request request){
        try {
            connection.setRequestMethod("GET");
            StringBuilder stringBuilder=new StringBuilder();
            System.out.println(connection.getHeaderField(0));
            HashMap<String,String> hashMap=new HashMap<>();
            for (int i = 1; i < 8; i++) {
                hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                if(request.isShowResponseHeader()) {
                    System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
            }
            request.setResponseHeaders(hashMap);
            request.setResponseCode(connection.getResponseCode());
            request.setResponseMassage(connection.getResponseMessage());
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
            request.setResponseBody(stringBuilder.toString());
            System.out.println(stringBuilder.toString());
        } catch (ProtocolException e) {
            System.out.println("Protocol exception...!");
        } catch (IOException e) {
            System.out.println("IOException...!");
        }
    }

    /**
     * This method fix body form data to the right format.
     * @param body is body of request
     * @param boundary is a custom boundary
     * @param bufferedOutputStream is buffered out put stream of http url connection
     * @throws IOException is a main exception related to input and out exception.
     */
    private void bufferOutFormData(@NotNull HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
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

    /**
     * This method is implementing of "POST" method for request.
     */
    private void POST(HttpURLConnection connection,Request request){
        if(massageBodyType.equals("multipart form")){
            try {
                String boundary = System.currentTimeMillis() + "";
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(request.getMassageBodyFormData(), boundary, bufferedOutputStream);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 8; i++) {
                    hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                    if(request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(new String(bufferedInputStream.readAllBytes()));
                System.out.println(new String(bufferedInputStream.readAllBytes()));
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
        }
        else if(massageBodyType.equals("json")){

        }
        else {
            try {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                File file = new File(request.getUploadPath());
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
                bufferedOutputStream.write(fileInputStream.readAllBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 8; i++) {
                    hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                    if(request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(new String(bufferedInputStream.readAllBytes()));
                System.out.println(new String(bufferedInputStream.readAllBytes()));
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException...!");
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
        }
    }

    /**
     * This method is implementing of "PUT" method for request.
     */
    private void PUT(HttpURLConnection connection,Request request){
        if(massageBodyType.equals("multipart form")){
            try {
                String boundary = System.currentTimeMillis() + "";
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(request.getMassageBodyFormData(), boundary, bufferedOutputStream);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 8; i++) {
                    hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                    if(request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(new String(bufferedInputStream.readAllBytes()));
                System.out.println(new String(bufferedInputStream.readAllBytes()));
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
        }
        else if(massageBodyType.equals("json")){

        }
        else {
            try {
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                File file = new File(request.getUploadPath());
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
                bufferedOutputStream.write(fileInputStream.readAllBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 8; i++) {
                    hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                    if(request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(new String(bufferedInputStream.readAllBytes()));
                System.out.println(new String(bufferedInputStream.readAllBytes()));
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException...!");
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
        }
    }

    /**
     * This method is implementing of "PATCH" method for request.
     */
    private void PATCH(){
        //abstract
    }

    /**
     * This method is implementing of "DELETE" method for request.
     */
    private void DELETE(@NotNull HttpURLConnection connection, Request request){
        try {
            connection.setRequestMethod("DELETE");
            StringBuilder stringBuilder=new StringBuilder();
            System.out.println(connection.getHeaderField(0));
            HashMap<String,String> hashMap=new HashMap<>();
            for (int i = 1; i < 8; i++) {
                hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                if(request.isShowResponseHeader()) {
                    System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                }
            }
            request.setResponseHeaders(hashMap);
            request.setResponseCode(connection.getResponseCode());
            request.setResponseMassage(connection.getResponseMessage());
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("IOException...!");
            }
            request.setResponseBody(stringBuilder.toString());
            System.out.println(stringBuilder.toString());
        } catch (ProtocolException e) {
            System.out.println("Protocol exception...!");
        } catch (IOException e) {
            System.out.println("IOException...!");
        }
    }

    /**
     * This method is implementing of "OPTIONS" method for request.
     */
    private void OPTIONS(){
        //abstract
    }

    /**
     * This method is implementing of "HEAD" method for request.
     */
    private void HEAD(){
        //abstract
    }


    /**
     * This method choose method request or handle them.
     * @param connection is view Http URL Connection
     * @param request is current request
     */
    private void send(HttpURLConnection connection, @NotNull Request request){
        switch (request.getRequestMethod()){
            case "GET":
                GET(connection,request);
                break;
            case "POST":
                POST(connection,request);
                break;
            case "PUT":
                PUT(connection,request);
                break;
            case "DELETE":
                DELETE(connection,request);
                break;
            default:
                System.out.println("Unknown method...!");
        }
        if(request.isHasSave()) {
            save(request);
        }
        if(request.isHasResponseBodySave()){
            saveResponseBody(request);
        }
    }
}
