import org.junit.Test;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author candra
 */
public class getSendDataRPGTest {

    byte[] message = new byte[2048];
    byte[] receiveData = new byte[1024];

    public void intKeByte(int ac, int awal)
    {

        for (int i=awal; i < awal+4; ++i)
        {
            message[awal+3] = (byte) (ac & 0xFF);
            message[awal+2] = (byte) ((ac >> 8) & 0xFF);
            message[awal+1] = (byte) ((ac >> 16) & 0xFF);
            message[awal] = (byte) ((ac >> 24) & 0xFF);

        }
    }
    public static int byteKeInt(byte[] b, int awal)
    {
        return   b[awal+3] & 0xFF |
                (b[awal+2] & 0xFF) << 8 |
                (b[awal+1] & 0xFF) << 16 |
                (b[awal] & 0xFF) << 24;
    }

    @Test
    public void cobaUDP() throws Exception
    {
        byte a=1;
        int b=0;
        char[] grade = {'A','B','C','D','F'};
        char grades = 0;

        int size                = 0;
        int numCells            = 0;
        int plus                = 0;
        int sliceNum            = 0;

        /*Testing*/
        message[4]              = 2;
        int range               = 100000;
        int rangeDiscrimination = 600;
        int pix                 = 700;
        int sliceTotal          = 360;
        int sliceNumIncr        = 1;
       
        while(true) {
            if(b>=4){
                b=0;
            }
            switch(grades)
            {
                case 'A' :
                 /*create object 1(all)*/
                    for(int i=30;i<message.length;i++){
                        message[i] = a;
                        if(a==127){
                            a=0;
                        }
                        a+=1;
                        numCells+=1;
                    }
                    break;
                case 'B' :
                    //create object 1(Begining Cell)
                    for(int i=30;i<message.length-1900;i++){
                        message[i] = 127;
                        numCells+=1;
                    }

                    for(int i=(message.length-1900);i<message.length;i++){
                        message[i] = 0;
                        numCells+=1;

                    }
                    break;
                case 'C' :
                    //create object 1(Middle Cell)
                    int mid = message.length/2;
                    for(int i=mid-50;i<mid+50;i++){
                        message[i] = 127;
                        numCells+=1;
                    }

                    for(int i=30;i<mid-50;i++){
                        message[i] = 0;
                        numCells+=1;
                    }
                    for(int i=mid+50;i<message.length;i++){
                        message[i] =0;
                        numCells+=1;
                    }
                    break;
                case 'D' :
                    //create object 1(Last Cell)
                    for(int i=message.length-100;i<message.length;i++){
                        message[i] = 127;
                        numCells+=1;
                    }

                    for(int i=30;i<message.length-100;i++){
                        message[i] = 0;
                        numCells+=1;
                    }
                    break;
                case 'F' :
                 /*create object 1(all)*/
                    for(int i=30;i<message.length;i++){
                        message[i] = a;
                        if(a==127){
                            a=0;
                        }
                        a+=1;
                        numCells+=1;
                    }
                    break;
                default :

            }
            
              /*initiated counting size*/
            for(int c=0; c<numCells; c++){
                size+=1;
            }
          
            size = size+30;
            Socket socket = new Socket("127.0.0.1", 1234);
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

            intKeByte(size,0);
            intKeByte(range,6);
            intKeByte(rangeDiscrimination,10);
            intKeByte(pix,14);
            intKeByte(sliceTotal,22);
            intKeByte(numCells,26);

            if(sliceNum<=sliceTotal){
                intKeByte(sliceNum,18);
            }
            else{
                sliceNum=0;
                b+=1;
                grades=grade[b];
            }

            sliceNum+=sliceNumIncr;
            dOut.write(message,0,size);           // write the message
            socket.close();
            Thread.sleep(30);

            numCells=0;
            size=0;
        }
    }
}

