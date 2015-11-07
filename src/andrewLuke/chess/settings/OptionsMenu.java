package andrewLuke.chess.settings;

/**
 *
 * @author Andrew
 */
import andrewLuke.chess.model.GameCore;
import andrewLuke.chess.networking.Client;
import andrewLuke.chess.audio.Sound;
import andrewLuke.chess.networking.Host;
import andrewLuke.chess.view.GameView;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

/**
 * The settings menu
 *
 * @author Andrew
 */
public class OptionsMenu extends JFrame
{

    private static boolean connectionEstablished = false;
    private JTextField minutesSelection;
    private final JTextField hostIpAddress;
    private final JTextField hostPort;
    private JRadioButton rdbtnNotTimed;
    private JRadioButton rdbtnTimedGame;
    private JRadioButton rdbtnOnline;
    private JRadioButton rdbtnOffline;
    public static boolean timerOn = true;
    public static boolean onlineMode = false;
    public static boolean player1First = true;
    private final JCheckBox chckbxStartAsHost;
    private final JCheckBox chckbxHostMovesFirst;
    private JLabel lblHostPort;
    private JButton btnShowMeTheGame;
    private JLabel lblHostIpAddress;
    private JLabel lblMinutes;
    private JButton btnDisconnect;
    private JButton btnConnect;
    private JLabel lblLocalMachineInfo;
    private JTextField localMachineInfo;
    public static boolean hostMode = false;

    /**
     * Creates an OptionsMenu instance
     */
    public OptionsMenu()
    {
        setTitle("Options");
        setBounds(0, 0, 319, 664);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        btnShowMeTheGame = new JButton("Confirm Selections");
        btnShowMeTheGame.setFont(new Font("Tahoma", Font.PLAIN, 23));
        btnShowMeTheGame.setBackground(Color.green);
        btnShowMeTheGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                Sound gameStartSound = new Sound("okSound.wav");
                gameStartSound.playSound();
                GameCore.desiredMinutesPerPlayer = Integer.parseInt(minutesSelection.getText());
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                GameCore.setHostClientSetup();
            }
        });
        btnShowMeTheGame.setBounds(27, 485, 238, 37);
        getContentPane().add(btnShowMeTheGame);

        rdbtnTimedGame = new JRadioButton("Timed");
        rdbtnTimedGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnTimedGame.setSelected(true);
        rdbtnTimedGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (rdbtnTimedGame.isSelected())
                {
                    minutesSelection.setVisible(true);
                    lblMinutes.setVisible(true);
                    timerOn = true;
                    rdbtnNotTimed.setSelected(false);
                }
                else
                {
                    minutesSelection.setVisible(false);
                    lblMinutes.setVisible(false);
                    timerOn = false;
                    rdbtnNotTimed.setSelected(true);
                }
            }
        });
        rdbtnTimedGame.setBounds(11, 12, 104, 29);
        getContentPane().add(rdbtnTimedGame);

        rdbtnNotTimed = new JRadioButton("Not Timed");
        rdbtnNotTimed.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnNotTimed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (rdbtnNotTimed.isSelected())
                {
                    minutesSelection.setVisible(false);
                    lblMinutes.setVisible(false);
                    timerOn = false;
                    rdbtnTimedGame.setSelected(false);
                }
                else
                {
                    minutesSelection.setVisible(true);
                    lblMinutes.setVisible(true);
                    timerOn = true;
                    rdbtnTimedGame.setSelected(true);
                }
            }
        });
        rdbtnNotTimed.setBounds(160, 12, 131, 29);
        getContentPane().add(rdbtnNotTimed);

        minutesSelection = new JTextField("28");
        minutesSelection.setFont(new Font("Tahoma", Font.PLAIN, 20));
        minutesSelection.setBounds(84, 96, 143, 37);
        getContentPane().add(minutesSelection);
        minutesSelection.setColumns(10);

        lblMinutes = new JLabel("Minutes Per Player");
        lblMinutes.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblMinutes.setBounds(38, 53, 256, 32);
        getContentPane().add(lblMinutes);

        rdbtnOnline = new JRadioButton("Online");
        rdbtnOnline.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnOnline.setBounds(11, 145, 104, 29);
        rdbtnOnline.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (rdbtnOnline.isSelected())
                {
                    onlineMode = true;
                    rdbtnOffline.setSelected(false);
                    chckbxStartAsHost.setVisible(true);
                    lblHostPort.setVisible(true);
                    hostPort.setVisible(true);
                    if (chckbxStartAsHost.isSelected())
                    {
                        chckbxHostMovesFirst.setVisible(true);
                    }
                    else
                    {
                        hostIpAddress.setVisible(true);
                        lblHostIpAddress.setVisible(true);
                    }
                    btnConnect.setVisible(true);
                    btnDisconnect.setVisible(false);
                    btnShowMeTheGame.setEnabled(false);
                    lblLocalMachineInfo.setVisible(true);
                    localMachineInfo.setVisible(true);
                }
                else
                {
                    onlineMode = false;
                    rdbtnOffline.doClick();

                    chckbxStartAsHost.setVisible(false);
                    lblHostPort.setVisible(false);
                    hostPort.setVisible(false);
                    if (chckbxStartAsHost.isSelected())
                    {
                        chckbxHostMovesFirst.setVisible(false);
                    }
                    else
                    {
                        hostIpAddress.setVisible(false);
                        lblHostIpAddress.setVisible(false);

                        chckbxHostMovesFirst.setVisible(false);
                    }
                    btnConnect.setVisible(false);
                    btnDisconnect.setVisible(false);
                    btnShowMeTheGame.setEnabled(true);
                    lblLocalMachineInfo.setVisible(false);
                    localMachineInfo.setVisible(false);
                }
            }
        });
        getContentPane().add(rdbtnOnline);

        rdbtnOffline = new JRadioButton("Offline");
        rdbtnOffline.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnOffline.setBounds(182, 145, 109, 29);
        rdbtnOffline.setSelected(true);
        rdbtnOffline.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (rdbtnOffline.isSelected())
                {
                    onlineMode = false;
                    hostIpAddress.setVisible(false);
                    lblHostIpAddress.setVisible(false);
                    lblHostPort.setVisible(false);
                    hostPort.setVisible(false);
                    chckbxHostMovesFirst.setVisible(false);
                    chckbxStartAsHost.setVisible(false);
                    rdbtnOnline.setSelected(false);
                    btnConnect.setVisible(false);
                    btnDisconnect.setVisible(false);
                    btnShowMeTheGame.setEnabled(true);
                    lblLocalMachineInfo.setVisible(false);
                    localMachineInfo.setVisible(false);

                }
                else
                {
                    if (chckbxStartAsHost.isSelected())
                    {
                        chckbxHostMovesFirst.setVisible(true);
                    }
                    else
                    {
                        hostIpAddress.setVisible(true);
                        lblHostIpAddress.setVisible(true);

                    }
                    btnConnect.setVisible(true);
                    btnDisconnect.setVisible(false);
                    lblHostPort.setVisible(true);
                    hostPort.setVisible(true);
                    onlineMode = true;
                    rdbtnOnline.doClick();
                    btnShowMeTheGame.setEnabled(false);
                    lblLocalMachineInfo.setVisible(true);
                    localMachineInfo.setVisible(true);
                }
            }
        });
        getContentPane().add(rdbtnOffline);

        lblLocalMachineInfo = new JLabel();
        lblLocalMachineInfo.setBounds(55, 525, 200, 32);
        lblLocalMachineInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
        getContentPane().add(lblLocalMachineInfo);
        lblLocalMachineInfo.setVisible(false);
        lblLocalMachineInfo.setText("Local IP4 Address: ");

        localMachineInfo = new JTextField();
        localMachineInfo.setBounds(5, 560, 280, 32);
        localMachineInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(localMachineInfo);
        localMachineInfo.setColumns(10);
        localMachineInfo.setBackground(Color.pink);
        localMachineInfo.setVisible(false);
        
        String systemInfo="";
        try
        {
            systemInfo = InetAddress.getLocalHost().toString();
        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        String systemInfoTokens[] = systemInfo.split("/");
        systemInfo = systemInfoTokens[systemInfoTokens.length - 1];
        localMachineInfo.setText(systemInfo);
        localMachineInfo.setEditable(false);

        hostIpAddress = new JTextField();
        hostIpAddress.setBounds(35, 271, 227, 32);
        hostIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(hostIpAddress);
        hostIpAddress.setColumns(10);
        hostIpAddress.setVisible(false);
        hostIpAddress.setText("localhost");

        lblHostIpAddress = new JLabel("Host IP Address");
        lblHostIpAddress.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHostIpAddress.setBounds(52, 234, 209, 40);
        getContentPane().add(lblHostIpAddress);
        lblHostIpAddress.setVisible(false);

        lblHostPort = new JLabel("Host Port");
        lblHostPort.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHostPort.setBounds(90, 313, 116, 29);
        getContentPane().add(lblHostPort);
        lblHostPort.setVisible(false);

        hostPort = new JTextField();
        hostPort.setFont(new Font("Tahoma", Font.PLAIN, 20));
        hostPort.setColumns(10);
        hostPort.setBounds(75, 343, 146, 29);
        getContentPane().add(hostPort);
        hostPort.setVisible(false);
        hostPort.setText("6478");

        chckbxStartAsHost = new JCheckBox("Start as Host");
        chckbxStartAsHost.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxStartAsHost.setBounds(63, 179, 170, 37);
        getContentPane().add(chckbxStartAsHost);
        chckbxStartAsHost.setVisible(false);
        chckbxStartAsHost.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (chckbxStartAsHost.isSelected())
                {
                    chckbxHostMovesFirst.setVisible(true);
                    hostIpAddress.setVisible(false);
                    lblHostIpAddress.setVisible(false);
                    lblHostPort.setVisible(true);
                    hostPort.setVisible(true);
                    GameView.enableStartButton();
                }
                else
                {
                    chckbxHostMovesFirst.setVisible(false);
                    hostIpAddress.setVisible(true);
                    lblHostIpAddress.setVisible(true);
                    lblHostPort.setVisible(true);
                    hostPort.setVisible(true);
                    GameView.disableStartButton();
                }
            }
        });
        chckbxStartAsHost.setSelected(true);

        chckbxHostMovesFirst = new JCheckBox("Host Moves First");
        chckbxHostMovesFirst.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxHostMovesFirst.setBounds(54, 400, 188, 29);
        getContentPane().add(chckbxHostMovesFirst);
        chckbxHostMovesFirst.setVisible(false);
        chckbxHostMovesFirst.setSelected(true);

        btnConnect = new JButton("Connect");
        btnConnect.setBackground(Color.getColor("", 0xff7CDAFC));
        btnConnect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (chckbxStartAsHost.isSelected() && onlineMode)
                {
                    Host.getHost().startHost();
                    hostMode = true;
                }
                else if ((!chckbxStartAsHost.isSelected()) && onlineMode)
                {
                    Client.getClient().connectTo(hostIpAddress.getText(), Integer.parseInt(hostPort.getText()));
                    hostMode = false;
                }
                btnConnect.setVisible(false);
                btnDisconnect.setVisible(true);
                btnShowMeTheGame.setEnabled(true);
                rdbtnOffline.setVisible(false);
                rdbtnOnline.setEnabled(false);
                chckbxStartAsHost.setVisible(false);
                chckbxHostMovesFirst.setVisible(false);
            }
        });
        btnConnect.setBounds(27, 441, 104, 29);
        getContentPane().add(btnConnect);
        btnConnect.setVisible(false);

        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setBackground(Color.getColor("", 0xffFF0000));
        btnDisconnect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                closeAll();
                btnConnect.setVisible(true);
                btnDisconnect.setVisible(false);
                rdbtnOffline.setVisible(true);
                chckbxStartAsHost.setVisible(true);
                if (chckbxStartAsHost.isSelected())
                {
                    chckbxHostMovesFirst.setVisible(true);
                }
                btnShowMeTheGame.setEnabled(false);
                rdbtnOnline.setEnabled(true);
            }
        });
        btnDisconnect.setBounds(143, 441, 122, 29);
        getContentPane().add(btnDisconnect);
        btnDisconnect.setVisible(false);
    }

    /**
     * Returns whether the playerTimer is on
     *
     * @return
     */
    public static boolean getTimerOn()
    {
        return timerOn;
    }

    /**
     * Closes all sockets
     */
    public void closeAll()
    {
        if (hostMode)
        {
            Host.getHost().closeAll();
        }
        else
        {
            Client.getClient().closeAll();
        }
        GameCore.unsetHostClientSetup();
    }

    /**
     * Notifies game that a network connection is established
     */
    public static void setConnectionEstablished()
    {
        connectionEstablished = true;
    }

    /**
     * True means that a connection is established
     * @return
     */
    public static boolean getConnectionEstablished()
    {
        return connectionEstablished;
    }
    
    /**
     * Returns true if the host moves first
     * @return
     */
    public boolean isHostFirst()
    {
        return chckbxHostMovesFirst.isSelected();
    }
    
    /**
     * Sets the client as moving first
     */
    public void setClientFirst()
    {
        chckbxHostMovesFirst.setSelected(false);
    }
}