package model;

import java.util.ArrayList;

public class Message {
	private String text;
	private ArrayList<String> key;
	private boolean encrypted;
	
	public Message(String text) {
		this.text = text;
	}
	
	public Message(String text, ArrayList<String> key){
		this.text = text;
		this.key = key;
	}
	
	public String getText(){
		return text;
	}
	
	public ArrayList<String> getKey(){
		return key;
	}
	
	public boolean isEncrypted(){
		return encrypted;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setKey(ArrayList<String> key){
		this.key = key;
	}
	
	public void setEncrypted(boolean encrypted){
		this.encrypted = encrypted;
	}
}