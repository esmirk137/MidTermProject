package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

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
    private String fileName;
    private int numberOfSavedFile;
    private ArrayList<String> arrayList;
    /**
     * This is constructor of this class and initialized our fields.
     */
    public Logic(){
        massageBodyType="multipart form";
        hasUnknownCommand=false;
        fileName=new Date().toString();
        char[] chars=fileName.toCharArray();
        for(int i=0; i<chars.length; i++){
            if(chars[i]==':') chars[i]='-';
        }
        fileName= Arrays.toString(chars);
        numberOfSavedFile=0;
        arrayList=new ArrayList<>();
    }

    /**
     * This is main method of this class and handle program and has trad with user.
     */
    public void handlerMethod(){
        File[] files=fileFinder("saveFaz2\\requests\\");
        numberOfSavedFile=files.length;
        while (true) {
            System.out.print(">Predigest ");
            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            arrayList = tokenLine(string);
            long starTime;
            Request request = new Request();
            request.setRequestMethod("GET");
            if (arrayList.get(0).equals("list")) list();
            else if (arrayList.get(0).equals("-h") || arrayList.get(0).equals("--help")) help();
            else if(arrayList.get(0).equals("fire")) {
                int counter=1;
                while (true) {
                    char c;
                    if (arrayList.get(counter).length() == 1) {
                        c = arrayList.get(counter).charAt(0);
                        if (Character.isDigit(c)) { //aways is digit
                            try {
                                System.out.println("...........Request "+counter+"............");
                                send(loadRequest(Integer.parseInt(arrayList.get(counter))));
                            } catch (IOException e) {
                                System.out.println("IOException...!"+e);
                            }
                        }
                    }
                    else break;
                    counter++;
                    if(counter>=arrayList.size()) break;
                }
            }
            else {
                try {
                    starTime = System.currentTimeMillis();
                    request.setURL("http://" + arrayList.get(0));
                    boolean last = false;
                    for (int i = 1; i < arrayList.size(); i++) {
                        if (i == arrayList.size() - 1) last = true;
                        switch (arrayList.get(i)) {
                            case "-M":
                            case "--method":
                                if (last) {
                                    System.out.println("Invalid command.");
                                    hasUnknownCommand = true;
                                } else {
                                    request.setRequestMethod(arrayList.get(++i));
                                }
                                break;
                            case "-H":
                            case "--headers":
                                if (last) {
                                    System.out.println("Invalid command.");
                                    hasUnknownCommand = true;
                                } else {
                                    request.setHasHeader(true);
                                    request.setHeaders(token(arrayList.get(++i), ':', ';'));
                                }
                                break;
                            case "-i":
                                request.setShowResponseHeader(true);
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
                                if (last) {
                                    System.out.println("Invalid command.");
                                    hasUnknownCommand = true;
                                }
                                data(token(arrayList.get(++i), '=', '&'), request);
                                break;
                            case "-j":
                            case "--json":
                                massageBodyType = "json";
                                request.setMassageBodyType("json");
                                json();
                                break;
                            case "-O":
                            case "--output":
                                request.setHasResponseBodySave(true);
                                if (!last && !arrayList.get(i + 1).startsWith("-")) {
                                    fileName = arrayList.get(++i);
                                }
                                break;
                            case "--urlencoded":
                            case "-U":
                                massageBodyType = "urlencoded";
                                break;
                            case "--upload":
                                if (last) {
                                    System.out.println("Invalid command.");
                                    hasUnknownCommand = true;
                                } else {
                                    massageBodyType = "upload";
                                    request.setUploadPath(arrayList.get(++i));
                                    request.setMassageBodyType("upload");
                                }
                                break;
                            default:
                                System.out.println("An unknown command find.");
                                hasUnknownCommand = true;
                        }
                    }
                    if (hasUnknownCommand) continue;
                    send(request);
                    long endTime = System.currentTimeMillis();
                    long time = endTime - starTime;
                    System.out.println("Time: " + time + " ms");
                } catch (MalformedURLException e) {
                    System.out.println("Malformed URL exception...!");
                } catch (IOException e) {
                    System.out.println("IOException...!"+e);
                }
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

     */
    private void headers(HashMap<String,String> headersHashMap, HttpURLConnection connection){
        for(String key:headersHashMap.keySet()){
            connection.setRequestProperty(key,headersHashMap.get(key));
        }
    }

    /**
     * This method find .src file in a folder.
     * @return these file as array
     */
    private File[] fileFinder(String path){
        File dir = new File(path);
        return dir.listFiles((dir1, filename) -> filename.endsWith(".src"));
    }

    /**
     * This method load request from text file.
     * @param number is number of request
     * @return request that load from file
     */
    @NotNull
    private Request loadRequest(int number){
        Request loadRequest=new Request();
        File[] files=fileFinder("saveFaz2\\requests\\");
        try(ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("saveFaz2\\requests\\"+files[number-1].getName()))){
            loadRequest=(Request) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"); //change to dialog massage
        } catch (IOException e) {
            System.out.println("saving process failed!"); //change to dialog massage
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadRequest.setHasSave(false);
        return loadRequest;
    }


    /**
     * Do task of command "list" and show requests of list.
     */
    private void list(){
        File[] files=fileFinder("saveFaz2\\requests\\");
        if(files.length==0)
            System.out.println("There is no request for showing.");
        else
            for(int i=1; i<=files.length; i++){
                Request request=loadRequest(i);
                System.out.print("["+i+"] url:"+request.getURL()+" | method: "+request.getRequestMethod()+" | headers: ");
                if(request.isHasHeader()) {
                    for (String key : request.getHeaders().keySet()) {
                        System.out.print(key + ": " + request.getHeaders().get(key) + " , ");
                    }
                }
                else System.out.print("empty");
                System.out.print(" | body massage: ");
                if(request.getMassageBodyFormData().size()==0) System.out.print("empty");
                else {
                    for (String key : request.getMassageBodyFormData().keySet()) {
                        System.out.print(key + ": " + request.getMassageBodyFormData().get(key) + " , ");
                    }
                }
                System.out.println();
            }
    }

    /**
     * Do task of command "-h" or "--help" and show commands and another information.
     */
    private void help(){
        System.out.println("There is following commands in this program:");
        System.out.println("-M or --method / for set method");
        System.out.println("-H or --headers / for add headers");
        System.out.println("-i / for show response header");
        System.out.println("-O or --output / for save response body");
        System.out.println("-S or --save / for save request");
        System.out.println("-d or --data / for set body (form data)");
        System.out.println("-j or --json / for set body (json)");
        System.out.println("-U or --urlencoded/ for set body (urlencoded)");
        System.out.println("--upload / for upload a file");
    }

    private void followRedirect(){
        //abstract
    }

    /**
     * Do task of command "-O" or "--output" and show requests of list.
     * Save response body in a html file.
     * @param request is current request
     * @param isFaz3 show this method run from faz 2 or 3
     */
    public void saveResponseBody(@NotNull Request request, boolean isFaz3, boolean isPng){
        if(isFaz3) {
            if (isPng) {
                fileName = "picture.png";
            }
            else {
                fileName = "picture.jpg";
            }
        }
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("saveFaz2\\bodyResponse\\"+fileName))){
            bufferedWriter.write(request.getResponseBody());
            System.out.println("Response body saved.");
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"+e); //change to dialog massage
        } catch (IOException e) {
            System.out.println("saving process failed!"+e); //change to dialog massage
        }
    }

    /**
     * This method save a request in a txt file.
     * @param request is request that use wanna save
     */
    private void save(Request request){
        try(ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("saveFaz2\\requests\\"+numberOfSavedFile+".src"))){
            objectOutputStream.writeObject(request);
            System.out.println("Request saved.");
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"+e);
        } catch (IOException e) {
            System.out.println("saving process failed!"+e);
        }
        numberOfSavedFile++;
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
     * @param request is current request
     */
    private void GET(Request request) {
        try {
            URL url = new URL(request.getURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (request.isHasHeader()) {
                headers(request.getHeaders(), connection);
            }
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(connection.getHeaderField(0));
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 1; i < 10; i++) {
                if(connection.getHeaderFieldKey(i)==null) continue;
                hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                if (request.isShowResponseHeader()) {
                    System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                }
            }
            if(connection.getHeaderFieldKey(3).equals("Content-Type") && (connection.getHeaderField(3).equals("image/png") || connection.getHeaderField(3).toString().equals("image/jpg")))
                System.out.println("image is:");
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.print("IOException...!: "+e);
            }
            System.out.println(stringBuilder);
            request.setResponseHeaders(hashMap);
            request.setResponseCode(connection.getResponseCode());
            request.setResponseMassage(connection.getResponseMessage());
            request.setResponseBody(stringBuilder.toString());
            long length=connection.getContentLengthLong();
            if(length==-1) length=0;
            System.out.println("Length: "+length+" byte");
        } catch (ProtocolException e) {
            System.out.println("Protocol exception...!");
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL exception...!");
        } catch (IOException e) {
            System.out.println("IOException...!");
        }
    }

    /**
     * This method fix body form data to the right format.
     * @param body is body of request
     * @param boundary is a custom boundary
     * @param bufferedOutputStream is buffered out put stream of http url connection
     * @throws IOException is a main exception related to input and out exception
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
     * @param request is current request
     */
    private void POST(Request request){
        if(massageBodyType.equals("multipart form")){
            try {
                String boundary = System.currentTimeMillis() + "";
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(request.getMassageBodyFormData(), boundary, bufferedOutputStream);
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 10; i++) {
                    if(connection.getHeaderFieldKey(i)==null) continue;
                    hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                    if (request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
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
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
            }
        }
        else if(massageBodyType.equals("json")){

        }
        else if(massageBodyType.equals("urlencoded")){
            try {
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                for (String key : request.getMassageBodyFormData().keySet()) {
                    if (key.contains("file")) {
                        bufferedOutputStream.write(("filename=\"" + (new File(request.getMassageBodyFormData().get(key))).getName() + "Content-Type: Auto").getBytes());
                        try {
                            BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(request.getMassageBodyFormData().get(key))));
                            byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                            bufferedOutputStream.write(filesBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bufferedOutputStream.write((key+":"+request.getMassageBodyFormData().get(key)+",").getBytes());
                    }
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 10; i++) {
                    if(connection.getHeaderFieldKey(i)==null) continue;
                    hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                    if (request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                StringBuilder stringBuilder=new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line="";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    System.out.println("IOException...!"+e);
                }
                System.out.println(stringBuilder);
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
            }
        }
        else {
            try {
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
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
                StringBuilder stringBuilder=new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line="";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    //System.out.println("IOException...!");
                }
                System.out.println(stringBuilder);
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                System.out.println(new String(bufferedInputStream.readAllBytes()));
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
            }
        }
    }

    /**
     * This method is implementing of "PUT" method for request.
     * @param request is current request
     */
    private void PUT(Request request){
        if(massageBodyType.equals("multipart form")){
            try {
                String boundary = System.currentTimeMillis() + "";
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(request.getMassageBodyFormData(), boundary, bufferedOutputStream);
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 10; i++) {
                    if(connection.getHeaderFieldKey(i)==null) continue;
                    hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                    if (request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
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
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
            }
        }
        else if(massageBodyType.equals("json")){

        }
        else if(massageBodyType.equals("urlencoded")){
            try {
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                for (String key : request.getMassageBodyFormData().keySet()) {
                    if (key.contains("file")) {
                        bufferedOutputStream.write(("filename=\"" + (new File(request.getMassageBodyFormData().get(key))).getName() + "Content-Type: Auto").getBytes());
                        try {
                            BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(request.getMassageBodyFormData().get(key))));
                            byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                            bufferedOutputStream.write(filesBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bufferedOutputStream.write((key+":"+request.getMassageBodyFormData().get(key)+",").getBytes());
                    }
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                System.out.println(connection.getHeaderField(0));
                HashMap<String,String> hashMap=new HashMap<>();
                for (int i = 1; i < 10; i++) {
                    if(connection.getHeaderFieldKey(i)==null) continue;
                    hashMap.put(connection.getHeaderFieldKey(i), connection.getHeaderField(i));
                    if (request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                StringBuilder stringBuilder=new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line="";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    System.out.println("IOException...!"+e);
                }
                System.out.println(stringBuilder);
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (ProtocolException e) {
                System.out.println("Protocol exception...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
            }
        }
        else {
            try {
                URL url = new URL(request.getURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                if (request.isHasHeader()) {
                    headers(request.getHeaders(), connection);
                }
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
                for (int i = 1; i < 10; i++) {
                    hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
                    if(request.isShowResponseHeader()) {
                        System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
                    }
                }
                StringBuilder stringBuilder=new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line="";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    //System.out.println("IOException...!");
                }
                System.out.println(stringBuilder);
                request.setResponseHeaders(hashMap);
                request.setResponseCode(connection.getResponseCode());
                request.setResponseMassage(connection.getResponseMessage());
                request.setResponseBody(stringBuilder.toString());
                System.out.println(new String(bufferedInputStream.readAllBytes()));
                long length=connection.getContentLengthLong();
                if(length==-1) length=0;
                System.out.println("Length: "+length+" byte");
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException...!");
            } catch (IOException e) {
                System.out.println("IOException...!"+e);
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
     * @param request is current request
     * @throws IOException is a main exception related to input and out exception
     */
    private void DELETE(Request request) throws IOException {
        URL url = new URL("http://"+arrayList.get(0));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
        connection.setRequestMethod("DELETE");
        if(request.isHasHeader()) {
            headers(request.getHeaders(),connection);
        }
        connection.connect();
        System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
        HashMap<String,String> hashMap=new HashMap<>();
        for (int i = 1; i < 10; i++) {
            hashMap.put(connection.getHeaderFieldKey(i),connection.getHeaderField(i));
            if(request.isShowResponseHeader()) {
                System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
            }
        }
       // System.out.println(stringBuilder);
        request.setResponseHeaders(hashMap);
        request.setResponseCode(connection.getResponseCode());
        request.setResponseMassage(connection.getResponseMessage());
        //request.setResponseBody(stringBuilder.toString());
        long length=connection.getContentLengthLong();
        if(length==-1) length=0;
        System.out.println("Length: "+length+" byte");
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
     * @param request is current request
     * @throws IOException is a main exception related to input and out exception
     */
    private void send(Request request) throws IOException {
        if(request.isHasSave()) {
            save(request);
        }
        switch (request.getRequestMethod()){
            case "GET":
                GET(request);
                break;
            case "POST":
                POST(request);
                break;
            case "PUT":
                PUT(request);
                break;
            case "DELETE":
                DELETE(request);
                break;
            default:
                System.out.println("Unknown method...!");
        }
        if(request.isHasResponseBodySave()){
            saveResponseBody(request,false, true);
        }
    }
}
