package model;
import java.io.UnsupportedEncodingException;

public interface EncryptionStrategy {

    public Message encrypt(Message message);
    public Message decrypt(Message message);
}
