package andrewLuke.chess.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class Client
{
    private static final Client client = new Client();
    private Socket clientSocket;
    private final StringBuffer stringBuffer = new StringBuffer("");
    private BufferedReader bufferedReader;
    private final String[] receivedString = new String[1];
    private PrintWriter printWriter;

    private Client()
    {
        
    }
    
    public static Client getClient()
    {
        return client;
    }

    public void connectTo(String ipAddress, int port)
    {
        try
        {
            final InetAddress destinationAddress = InetAddress.getByName(ipAddress);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        clientSocket = new Socket(destinationAddress, 80);

                        System.out.println("Client started: ");
                        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receives data from the Host instance
     *
     * @return string data representing the host's move, if not ""
     */
    public String receive()
    {
        try
        {
            final CountDownLatch latch = new CountDownLatch(1);
            new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    receivedString[0] = "";

                    try
                    {
                        if (bufferedReader != null)
                        {
                            //System.out.println("AbsolutelyNothing");
                            if (!bufferedReader.ready())
                            {
                                //System.out.println("AbsolutelyNothing");
                            }
                            else
                            {
                                receivedString[0] = bufferedReader.readLine();
                                //System.out.println(receivedString[0]);
                            }
                        }
                        latch.countDown();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
            latch.await();

        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receivedString[0];
    }

    /**
     * Sends the Client's recent move to the Host instance
     *
     * @param stringOfInterest
     */
    public void send(final String stringOfInterest)
    {

        stringBuffer.append(stringOfInterest);
        printWriter.println(stringBuffer);
        printWriter.flush();
        stringBuffer.setLength(0);
        System.out.println("Client Sending: " + stringOfInterest + " ");
    }

    /**
     * Closes all sockets
     */
    public void closeAll()
    {
        try
        {
            if(clientSocket!=null)
                clientSocket.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
