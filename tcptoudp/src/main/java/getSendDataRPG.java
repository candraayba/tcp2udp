import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

/**
 * @author candra.
 */
public class getSendDataRPG {

    private static int sizeData,version,numCell,n,m,counting,range,sliceNum,sliceTotal;
    private static int cell = 0, totalSize = 0, q=0,  g=0;

    /**
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     *
     * Get TCP/IP packet and convert it to UDP, hardcoded port and IP address.
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        int tcpPort = 1234;                                                             //set TCP port
        int udpPort = 4304;                                                             //set UDP port
        String IP = "127.0.0.1";                                                        //set IPAddress destination
        ServerSocket serverSocketTCP = new ServerSocket (tcpPort);                      //create server socket TCP
        DatagramSocket clientSocketUDP = new DatagramSocket();                          //create client socket UDP
        InetAddress IPAddress = InetAddress.getByName(IP);                              //set IP address for UDP
        convert con = new convert();
        byte[] messTemp = new byte[2048];

        /*Looping until program stop. */
        while(true) {

            Socket clientSocket = serverSocketTCP.accept ();                            //creating Tcp socket
            DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
            n=18;
            m=6;
            totalSize = 0;
            counting = 0;

            /*procced if message have data*/
            dIn.read(messTemp, 0, messTemp.length);// read the message
            sizeData = con.byteKeInt(messTemp,0);
            byte[] message = new byte[sizeData];
            for(int g=0; g<sizeData; g++){
                message[g]=messTemp[g];
            }

            for(int a=0;a<sizeData-30;a++){
                cell+=1;
            }
            if(numCell!=cell){
                System.out.println("Number of Cell Is not Matched!!");
            }
            else {
                version = message[4] + message[5];
                range = con.byteKeInt(message, 6);
                sliceNum = con.byteKeInt(message, 18);
                sliceTotal = con.byteKeInt(message, 22);
                numCell = con.byteKeInt(message, 26);
                System.out.println("-----------------------");
                System.out.println("Version          " + version);
                System.out.println("Size Data before " + sizeData);
                System.out.println("Slice Number     " + sliceNum);
                System.out.println("Slice Total      " + sliceTotal);
                System.out.println("numCell          " + numCell);
                System.out.println("range            " + range);
                System.out.println("-----------------------");
                cell=0;
            }

            /*counting message body*/
            for(int b=30; b<sizeData; b++){
                cell += 1;
            }

            for(int f=30; f<message.length; f++){
                if(message[f]==0){
                    message[f]=1;
                }
            }

            /*cut versionNumber*/
            for(int c=4; c < 8;c++){
                if(m<10){
                    message[c] = message[m];
                    m=m+1;

                }
            }

            /*cut pix * rangeDiscrimination*/
            for(int b=8; b< message.length;b++){
                if(n< message.length){
                    message[b] = message[n];
                    n=n+1;
                }
            }

            numCell     = con.byteKeInt(message,16);
            sizeData    = sizeData - 10;
            totalSize   = 20 + cell;

            for (int i=0; i < 0+4; ++i)
            {
                message[3] = (byte) (sizeData & 0xFF);
                message[2] = (byte) ((sizeData >> 8) & 0xFF);
                message[1] = (byte) ((sizeData >> 16) & 0xFF);
                message[0] = (byte) ((sizeData >> 24) & 0xFF);

            }

            if (version == 2 && sizeData == (message.length-10) && cell == numCell) {
                DatagramPacket sendPacket = new DatagramPacket(message, sizeData , IPAddress, udpPort);

                /*checking similarities data*/
                clientSocketUDP.send(sendPacket);  //send the UDP packet
                System.out.println(message);
                for(int b=0; b<sizeData; b++){
                    System.out.println(message[b]);
                }
                System.out.println("Total Size: "+totalSize);
                System.out.println("Version " + version);
                System.out.println("Size Data After "+ sizeData);
                System.out.println("numCell "+ numCell);
                System.out.println("Cell "+ cell);
                System.out.println("Data Send");
                cell=0;
            }

            /*if not similar*/
            else{
                System.out.println("Total Size: "+totalSize);
                System.out.println("Version " + version);
                System.out.println("Size Data "+ sizeData);
                System.out.println("numCell "+ numCell);
                System.out.println("Cell "+ cell);
                System.out.println("Data not Send!");
                cell=0;
            }
        }
    }

}


