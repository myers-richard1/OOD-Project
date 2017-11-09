package model;

public interface EncryptionStrategy {
	public Message encrypt(Message message);
	public Message decrypt(Message message);
}
