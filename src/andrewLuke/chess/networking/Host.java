package andrewLuke.chess.networking;

import andrewLuke.chess.settings.OptionsMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The host
 *
 * @author Andrew
 */
public class Host
{
    private static final Host host = new Host();
    private Socket socket;
    private ServerSocket serverSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private final String[] receivedString = new String[1];
    private StringBuffer stringBuffer;

    private Host()
    {
        
    }
    
    /**
     * Gets the host
     * @return
     */
    public static Host getHost()
    {
        return host;
    }
    
    /**
     * Creates a Host instance 
     */
    public void startHost()
    {
        new Thread("serverThread")
        {
            @Override
            public void run()
            {
                try
                {
                    serverSocket = new ServerSocket(80);

                    socket = serverSocket.accept();
                    OptionsMenu.setConnectionEstablished();

                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    stringBuffer = new StringBuffer("");

                }
                catch (IOException ex)
                {
                    Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    /**
     * Receives data from the Client instance
     *
     * @return string data representing the client's move, if not ""
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
                    try
                    {

                        receivedString[0] = "";

                        if (bufferedReader != null)
                        {
                            //System.out.print("Host received: ");
                            //System.out.println("debug"+ bufferedReader.toString());
                            if (!bufferedReader.ready())
                            {
                                //System.out.println("nothing");
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
                        Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
            latch.await();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receivedString[0];
    }

    /**
     * Sends the Host's recent move to the Client instance
     *
     * @param stringOfInterest
     */
    public void send(final String stringOfInterest)
    {
        stringBuffer.append(stringOfInterest);
        printWriter.println(stringBuffer);
        printWriter.flush();
        stringBuffer.setLength(0);
        System.out.println("Host Sending:" + stringOfInterest);
    }

    /**
     * Closes all sockets
     */
    public void closeAll()
    {
        try
        {
            if (socket != null)
            {
                socket.close();
            }
            serverSocket.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
