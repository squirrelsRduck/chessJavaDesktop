package andrewLuke.chess.view;

import andrewLuke.chess.application.ChessStart;
import andrewLuke.chess.control.Keyboard;
import andrewLuke.chess.control.Mouse;
import andrewLuke.chess.control.ButtonController;
import andrewLuke.chess.graphics.SpriteSheet;
import andrewLuke.chess.model.GameCore;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Andrew
 */
public class GameView
{

    private static final long serialVersionUID = 1L;
    private final int width, height;
    private JFrame outerFrame;
    private BufferedImage mainImage;
    public static int[] pixelArray;
    private Mouse mouse;
    private Keyboard keyboard;
    private JPanel mainFrame;
    private ImageIcon imageIcon;
    private JButton btnOptions;
    private static JButton btnStartButton;
    public static JTextField criticalGameMessage;
    private JTextArea p1Timer;
    private JTextArea p2Timer;
    private JTextArea history;
    private JTextArea rowLabels;
    private JTextArea columnLabels;
    private JButton btnReset;
    private JButton btnResetAll;
    private JLabel actualChessImageContainer;

    public GameView(int width, int height)
    {
        this.width = width;
        this.height = height;
        try
        {
            pixelArray = new int[width * height];
            // Creates the JFrame object
            outerFrame = new JFrame("Chess");
            outerFrame.getContentPane().setBackground(Color.black);
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]
            {
                0, 410, 1, 10
            };
            gridBagLayout.rowHeights = new int[]
            {
                0, 20, 82, 40, 40, 72, 41, 20, 20
            };
            gridBagLayout.columnWeights = new double[]
            {
                .08, 1., .001, .35
            };
            gridBagLayout.rowWeights = new double[]
            {
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
            };
            outerFrame.getContentPane().setLayout(gridBagLayout);
            BufferedImage frameIcon = ImageIO.read(SpriteSheet.class.getResource("TitleBarIcon.png"));
            BufferedImage frameIcon2 = ImageIO.read(SpriteSheet.class.getResource("TitleBarIcon2.png"));
            BufferedImage frameIcon3 = ImageIO.read(SpriteSheet.class.getResource("TitleBarIcon3.png"));
            BufferedImage frameIcon4 = ImageIO.read(SpriteSheet.class.getResource("TitleBarIcon4.png"));
            ArrayList<BufferedImage> list = new ArrayList();
            list.add(frameIcon);
            list.add(frameIcon2);
            list.add(frameIcon3);
            list.add(frameIcon4);
            outerFrame.setIconImages(list);
            mainFrame = new JPanel();
            mainFrame.setBackground(Color.black);
            mainFrame.setPreferredSize(new Dimension(810, 633));
            try
            {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
            {
                Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
            }
            GridBagConstraints gbc_internalFrame = new GridBagConstraints();
            gbc_internalFrame.insets = new Insets(0, 0, 5, 5);
            gbc_internalFrame.gridx = 1;
            gbc_internalFrame.gridy = 1;
            gbc_internalFrame.gridwidth = 1;
            gbc_internalFrame.gridheight = 6;
            actualChessImageContainer = new JLabel("");
            actualChessImageContainer.setBounds(0, 0, 800, 600);
            mainFrame.add(actualChessImageContainer);
            outerFrame.getContentPane().add(mainFrame, gbc_internalFrame);
            // Honestly, these other widgets for the outerFrame will probably
            // each get their own class designation
            int randomColor = ChessStart.random.nextInt();

            JTextField txtHistoryLabel = new JTextField();
            txtHistoryLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
            txtHistoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            txtHistoryLabel.setText("History");
            txtHistoryLabel.setEditable(false);
            GridBagConstraints gbc_txtHistoryLabel = new GridBagConstraints();
            gbc_txtHistoryLabel.insets = new Insets(0, 0, 5, 0);
            gbc_txtHistoryLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtHistoryLabel.gridx = 3;
            gbc_txtHistoryLabel.gridy = 1;
            outerFrame.getContentPane().add(txtHistoryLabel, gbc_txtHistoryLabel);
            txtHistoryLabel.setColumns(10);
            txtHistoryLabel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            JScrollPane historyScrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 3;
            gbc_scrollPane.gridy = 2;
            gbc_scrollPane.gridheight = 1;
            historyScrollPane.setPreferredSize(new Dimension(150, 200));
            //scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
            outerFrame.getContentPane().add(historyScrollPane, gbc_scrollPane);

            history = new JTextArea();
            history.setEditable(false);
            history.setFont(new Font("Monospaced", Font.BOLD, 18));
            historyScrollPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            historyScrollPane.setViewportView(history);

            JTextArea p1timerLabel = new JTextArea();
            p1timerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
            p1timerLabel.setText(" Player\n 1 Timer");
            p1timerLabel.setEditable(false);
            GridBagConstraints gbc_p1timerLabel = new GridBagConstraints();
            gbc_p1timerLabel.insets = new Insets(0, 0, 5, 0);
            gbc_p1timerLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_p1timerLabel.gridx = 0;
            gbc_p1timerLabel.gridy = 5;
            outerFrame.getContentPane().add(p1timerLabel, gbc_p1timerLabel);
            p1timerLabel.setColumns(10);
            p1timerLabel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));

            rowLabels = new JTextArea();
            rowLabels.setBackground(Color.BLACK);
            rowLabels.setForeground(Color.white);
            rowLabels.setFont(new Font("Monospaced", Font.BOLD, 28));
            rowLabels.setEditable(false);
            rowLabels.setText("a\n\nb\n\nc\n\nd\n\ne\n\nf\n\ng\n\nh");
            GridBagConstraints gbc_rowLabels = new GridBagConstraints();
            gbc_rowLabels.gridx = 2;
            gbc_rowLabels.gridy = 1;
            rowLabels.setPreferredSize(new Dimension(22, 590));
            gbc_rowLabels.gridheight = 6;
            outerFrame.getContentPane().add(rowLabels, gbc_rowLabels);
            p1timerLabel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));

            columnLabels = new JTextArea();
            columnLabels.setBackground(Color.BLACK);
            columnLabels.setForeground(Color.white);
            columnLabels.setFont(new Font("Monospaced", Font.BOLD, 28));
            columnLabels.setText(" Player\n 1 Timer");
            columnLabels.setEditable(false);
            columnLabels.setText("  1     2     3     4     5     6     7     8");
            GridBagConstraints gbc_columnLabels = new GridBagConstraints();
            gbc_columnLabels.gridx = 1;
            gbc_columnLabels.gridy = 7;
            columnLabels.setPreferredSize(new Dimension(800, 40));
            gbc_columnLabels.gridheight = 1;
            outerFrame.getContentPane().add(columnLabels, gbc_columnLabels);

            btnOptions = new JButton("Options");
            btnOptions.setBackground(Color.getColor("", 0xff00CCFF));
            GridBagConstraints gbc_gameOptions = new GridBagConstraints();
            gbc_gameOptions.gridx = 0;
            gbc_gameOptions.gridy = 4;
            outerFrame.getContentPane().add(btnOptions, gbc_gameOptions);

            btnStartButton = new JButton("Start Game");
            btnStartButton.setBackground(Color.green);
            GridBagConstraints gbc_startButton = new GridBagConstraints();
            gbc_startButton.gridx = 0;
            gbc_startButton.gridy = 3;
            outerFrame.getContentPane().add(btnStartButton, gbc_startButton);

            JTextArea p2timerLabel = new JTextArea();
            p2timerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
            p2timerLabel.setText(" Player\n 2 Timer");
            p2timerLabel.setEditable(false);
            GridBagConstraints gbc_p2timerLabel = new GridBagConstraints();
            gbc_p2timerLabel.insets = new Insets(0, 0, 5, 0);
            gbc_p2timerLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_p2timerLabel.gridx = 0;
            gbc_p2timerLabel.gridy = 1;
            p2timerLabel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            outerFrame.getContentPane().add(p2timerLabel, gbc_p2timerLabel);
            p2timerLabel.setColumns(10);

            p1Timer = new JTextArea();
            p1Timer.setEditable(false);
            p1Timer.setFont(new Font("Monospaced", Font.PLAIN, 16));
            GridBagConstraints gbc_p1Timer = new GridBagConstraints();
            gbc_p1Timer.insets = new Insets(0, 0, 5, 5);
            gbc_p1Timer.fill = GridBagConstraints.BOTH;
            gbc_p1Timer.gridx = 0;
            gbc_p1Timer.gridy = 6;
            gbc_p1Timer.gridheight = 1;
            p1Timer.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            outerFrame.getContentPane().add(p1Timer, gbc_p1Timer);

            p2Timer = new JTextArea();
            p2Timer.setEditable(false);
            p2Timer.setFont(new Font("Monospaced", Font.PLAIN, 16));
            GridBagConstraints gbc_p2Timer = new GridBagConstraints();
            gbc_p2Timer.insets = new Insets(0, 0, 5, 5);
            gbc_p2Timer.fill = GridBagConstraints.BOTH;
            gbc_p2Timer.gridx = 0;
            gbc_p2Timer.gridy = 2;
            p2Timer.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            outerFrame.getContentPane().add(p2Timer, gbc_p2Timer);

            JTextArea txtInstructions = new JTextArea();
            txtInstructions.setText("Toggles:\nt: tile color\nm: militant color");
            txtInstructions.setEditable(false);
            GridBagConstraints gbc_txtrAndrewElek = new GridBagConstraints();
            gbc_txtrAndrewElek.insets = new Insets(0, 0, 0, 5);
            gbc_txtrAndrewElek.fill = GridBagConstraints.BOTH;
            gbc_txtrAndrewElek.gridx = 0;
            gbc_txtrAndrewElek.gridy = 8;
            txtInstructions.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            outerFrame.getContentPane().add(txtInstructions, gbc_txtrAndrewElek);

            criticalGameMessage = new JTextField();
            criticalGameMessage.setFont(new Font("Tempus Sans ITC", Font.BOLD, 33));
            criticalGameMessage.setHorizontalAlignment(SwingConstants.CENTER);
            criticalGameMessage.setText("CriticalGameMessage");
            criticalGameMessage.setEditable(false);
            criticalGameMessage.setBackground(Color.getColor("", 0x5CF7E5));// Color.getColor("", player1.getColor1()));
            criticalGameMessage.setForeground(Color.getColor("", 0x2E19BD));//player1.getColor2()));
            GridBagConstraints gbc_txtCriticalgamemessage = new GridBagConstraints();
            //gbc_txtCriticalgamemessage.insets = new Insets(0, 0, 0, 5);
            gbc_txtCriticalgamemessage.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtCriticalgamemessage.gridwidth = 1;
            gbc_txtCriticalgamemessage.gridx = 1;
            gbc_txtCriticalgamemessage.gridy = 8;
            criticalGameMessage.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.getColor("", randomColor)));
            outerFrame.getContentPane().add(criticalGameMessage, gbc_txtCriticalgamemessage);

            btnReset = new JButton("Game Reset");
            btnReset.setBackground(Color.red);
            btnReset.setForeground(Color.white);
            btnReset.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
            GridBagConstraints gbc_btnReset = new GridBagConstraints();
            gbc_btnReset.gridx = 3;
            gbc_btnReset.gridy = 8;
            outerFrame.getContentPane().add(btnReset, gbc_btnReset);

            btnResetAll = new JButton("Reset All");
            btnResetAll.setBackground(Color.getColor("", 0xffB00B0B));
            btnResetAll.setForeground(Color.white);
            btnResetAll.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
            GridBagConstraints gbc_btnResetAll = new GridBagConstraints();
            gbc_btnResetAll.gridx = 3;
            gbc_btnResetAll.gridy = 7;
            outerFrame.getContentPane().add(btnResetAll, gbc_btnResetAll);

            mainImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR); // creates bufferedImage object
            // This statement links mainImage to pixelArray behind-the-scenes.  When we write to pixelArray
            // after this statement, the data will be linked to mainImage
            pixelArray = ((DataBufferInt) mainImage.getRaster().getDataBuffer()).getData();
            outerFrame.setSize(width + 400, height + 220);
            // Sets properties of JFrame
            imageIcon = new ImageIcon(mainImage);
            actualChessImageContainer.setIcon(imageIcon);
            actualChessImageContainer.setDoubleBuffered(true);
            actualChessImageContainer.repaint();
            mainFrame.add(actualChessImageContainer);  // Adds to canvas
            // Input listeners
            mouse = new Mouse();
            actualChessImageContainer.addMouseListener(mouse);
            actualChessImageContainer.addMouseMotionListener(mouse);
            keyboard = new Keyboard();
            actualChessImageContainer.addKeyListener(keyboard);
            outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            outerFrame.setLocationRelativeTo(null);
            // Makes sure to listen for events without requiring clicking in window first
            mainFrame.setVisible(true);
            outerFrame.setVisible(true);
            actualChessImageContainer.requestFocusInWindow();
        }
        catch (IOException ex)
        {
            Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the reset button
     * @return
     */
    public JButton getResetButton()
    {
        return btnReset;
    }

    /**
     * Gets the critical text display area
     * @return
     */
    public JTextField getCriticalText()
    {
        return criticalGameMessage;
    }

    /**
     * Gets the start button
     * @return
     */
    public JButton getStartButton()
    {
        return btnStartButton;
    }

    /**
     * Sets the timer's text
     * @param playerID
     * @param text
     */
    public void setTimerText(int playerID, String text)
    {
        if (playerID == 1)
        {
            p1Timer.setText(text);
        }
        else
        {
            p2Timer.setText(text);
        }
    }

    /**
     * Refreshes the chess image icon
     */
    public void refreshActualChessImage()
    {
        actualChessImageContainer.setIcon(new ImageIcon(mainImage));
    }

    /**
     * Returns the JLabel thats used to store the chess game's ImageIcon
     * @return
     */
    public JLabel getChessImageContainer()
    {
        return actualChessImageContainer;
    }

    /**
     * Gets the history JTextArea
     * @return
     */
    public JTextArea getHistory()
    {
        return history;
    }

    /**
     * Enables the start button
     */
    public static void enableStartButton()
    {
        btnStartButton.setEnabled(true);
    }

    /**
     * Disables the start button
     */
    public static void disableStartButton()
    {
        btnStartButton.setEnabled(false);
    }

    /**
     * Sets the actionListeners
     * @param buttonController
     */
    public void setButtonControllers(ButtonController buttonController)
    {
        btnReset.addActionListener(buttonController);
        btnResetAll.addActionListener(buttonController);
        btnOptions.addActionListener(buttonController);
        btnStartButton.addActionListener(buttonController);
    }

    /**
     * Gets the options button
     * @return
     */
    public JButton getOptionsButton()
    {
        return btnOptions;
    }
}