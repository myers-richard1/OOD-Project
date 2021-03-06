package model;

import java.util.ArrayList;

public class Message {
    private String text;
    private String key;
    private boolean encrypted;

    public Message(String text, String key, boolean encrypted){
        this.text = text;
        this.key = key;
        this.encrypted = encrypted;
    }
    
    public Message(String text, boolean encrypted){
    	this.text = text;
    	key = null;
    	this.encrypted = encrypted;
    }

    public String getText(){
        return text;
    }

    public String getKey(){
        return key;
    }

    public boolean isEncrypted(){
        return encrypted;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setEncrypted(boolean encrypted){
        this.encrypted = encrypted;
    }
}
