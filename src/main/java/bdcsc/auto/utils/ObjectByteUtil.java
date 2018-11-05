package bdcsc.auto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象和字节数组之间的转化
 * Created by mawenrui on 2018/7/16.
 */
public class ObjectByteUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(ObjectByteUtil.class);

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (Exception e) {
            LOGGER.error("object to byte error: {}", e);
        }
        return bytes;
    }

    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            LOGGER.error("byte to object error: {}", e);
        }
        return obj;
    }
}
