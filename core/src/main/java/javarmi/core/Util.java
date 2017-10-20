package javarmi.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Util {

    public static String getRemoteBinding() {
        return "//" + Config.getRabbitHost()+ "/" + Service.REMOTE_BINDING;
    }

    public static void rethrow(ExceptionalRunnable r) {
        try {
            r.run();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T rethrow(ExceptionalSupplier<T> s) {
        try {
            return s.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ExceptionalRunnable {
        void run() throws Exception;
    }

    public interface ExceptionalSupplier<T> {
        T get() throws Exception;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static <T> T deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return (T) is.readObject();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
