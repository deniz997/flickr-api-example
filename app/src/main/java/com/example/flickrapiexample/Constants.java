package com.example.flickrapiexample;

public class Constants {

    //Constant variables for easy use
    public static final String API_KEY = "31b5859674b4bef3cf6962b091d70707";
    public static final String ENDPOINT = "https://www.flickr.com/services/";
    public static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    public static final int INTERNET_PERMISSOIN_REQ = 222;
    public static final String LIST_TITLE = "Recent photos from Flickr";
    public static final String ALERT_TITLE = "Empty Response";
    public static final String ALERT_MESSAGE = "No photo found on response from Flickr\n" +
            "This might be caused by network problems\n" +
            "Please check your connection";
    public static final String ALERT_POS = "Repeat Request";
    public static final String ALERT_NEG = "Exit";
    public static final int PESPONSE_PERPAGE = 10;
    public static final int RESPONSE_PAGE = 100;
    public static final int RESPONSE_NOCALLBACK = 1;
    public static final String  RESPONSE_FORMAT = "json";
    public static final String ALERT_NOCONTITLE = "No connection";
    public static final String ALERT_NOCONMESSAGE = "No connection or connection with no internet\n" +
            "Please check your connection and try again!";
    public static final String ALERT_NOCONBTN = "Exit App";

}
