import java.io.UnsupportedEncodingException;

public interface EncryptionStrategy {

    public Message encrypt(Message message, String key) throws UnsupportedEncodingException,NumberFormatException;
    public Message decrypt(Message message, String key)throws ArrayIndexOutOfBoundsException;
}
