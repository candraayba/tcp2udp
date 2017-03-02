/**
 * @author candra
 */
public class convert {

    /**
     * convert from byte to Integer
     * @param b array byte
     * @param awal array index
     * @return byte[]
     *
     * convert byte array to integer data
     */
    public static int byteKeInt(byte[] b, int awal)
    {
        return   b[awal+3] & 0xFF |
                (b[awal+2] & 0xFF) << 8 |
                (b[awal+1] & 0xFF) << 16 |
                (b[awal] & 0xFF) << 24;
    }

}
