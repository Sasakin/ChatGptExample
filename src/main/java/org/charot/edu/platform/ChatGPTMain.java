package org.charot.edu.platform;

import gg.acai.chatgpt.ChatGPT;
import gg.acai.chatgpt.ChatGPTAPI;
import gg.acai.chatgpt.exception.ParsedExceptionEntry;

public class ChatGPTMain {
    public static void main(String[] args) {
        /*ChatGPTAPI chatGPTAPI = ChatGPTAPI.getInstance()
                .*/
        ChatGPT chatGpt = ChatGPT.newBuilder()
                .sessionToken("token_here") // required field: get from cookies
                .cfClearance("cf_clearance_here") // required to bypass Cloudflare: get from cookies
                .userAgent("user_agent_here") // required to bypass Cloudflare: google 'what is my user agent'
                .addExceptionAttribute(new ParsedExceptionEntry("exception keyword", Exception.class)) // optional: adds an exception attribute
                .connectTimeout(60L) // optional: specify custom connection timeout limit
                .readTimeout(30L) // optional: specify custom read timeout limit
                .writeTimeout(30L) // optional: specify custom write timeout limit
                .build(); // builds the ChatGPT client
    }
}
