/**
 * Created by len on 6/7/16.
 */
public class convert {

    /**
     *
     * @param b
     * @param awal
     * @return
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
