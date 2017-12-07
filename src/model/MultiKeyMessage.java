package model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiKeyMessage extends Message{
	private ArrayList<String> multiKey;
	public MultiKeyMessage(String text, ArrayList<String> multiKey, Boolean encrypted) {
		super(text, multiKey.get(0), encrypted);
		this.multiKey = multiKey;
	}
	
	public MultiKeyMessage(Message m){
		super(m.getText(), m.getKey(), m.isEncrypted());
		if (m.getKey()!=null){
			multiKey = new ArrayList<String>(Arrays.asList(m.getKey().split(",")));
		}
		else multiKey = new ArrayList<String>();
	}
	
	public MultiKeyMessage(Message m, ArrayList<String> multiKey){
		super(m.getText(),m.getKey(),m.isEncrypted());
		this.multiKey = multiKey;
	}
	
	public ArrayList<String> getMultiKey(){
		return multiKey;
	}
	
	public void setMultiKey(ArrayList<String> multiKey){
		this.multiKey = multiKey;
	}
}
