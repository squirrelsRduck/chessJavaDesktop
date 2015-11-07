/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrewLuke.chess.control;

import andrewLuke.chess.audio.Sound;
import andrewLuke.chess.model.GameCore;
import static andrewLuke.chess.model.GameCore.unsetHostClientSetup;
import andrewLuke.chess.networking.Client;
import andrewLuke.chess.networking.Host;
import andrewLuke.chess.settings.OptionsMenu;
import andrewLuke.chess.time.PlayerTimer;
import andrewLuke.chess.view.GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andrew
 */
public class ButtonController implements ActionListener
{

    private final GameView gameView;
    private final GameCore gameCore;

    public ButtonController(GameView gameView, GameCore gameCore)
    {
        this.gameView = gameView;
        this.gameCore = gameCore;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Game Reset"))
        {
            Sound gameStartSound = new Sound("resetGameSound.wav");
            gameStartSound.playSound();
            if (OptionsMenu.hostMode && OptionsMenu.onlineMode && GameCore.gameStarted)
            {
                Host.getHost().send("autoReset");
            }
            else if (!OptionsMenu.hostMode && OptionsMenu.onlineMode && GameCore.gameStarted)
            {
                Client.getClient().send("autoReset");
            }
            gameCore.resetGame();
            gameView.getOptionsButton().setEnabled(true);

        }
        else if (actionCommand.equals("Reset All"))
        {
            Sound gameStartSound = new Sound("resetGameSound.wav");
            gameStartSound.playSound();
            if (OptionsMenu.hostMode && OptionsMenu.onlineMode && GameCore.gameStarted)
            {
                Host.getHost().send("autoResetAll"); //autoResetAll
            }
            else if (!OptionsMenu.hostMode && OptionsMenu.onlineMode && GameCore.gameStarted)
            {
                Client.getClient().send("autoResetAll");
            }
            if (gameCore.getOptionsMenu() != null && OptionsMenu.onlineMode)
            {
                gameCore.getOptionsMenu().closeAll();
                gameCore.setOptionsMenu(null);
                OptionsMenu.onlineMode = false;
            }
            gameView.getOptionsButton().setEnabled(true);
            unsetHostClientSetup();
            gameCore.resetGame();
            gameView.getResetButton().setEnabled(true);
        }
        else if (actionCommand.equals("Options"))
        {
            Sound gameStartSound = new Sound("optionMenuSound.wav");
            gameStartSound.playSound();
            if (gameCore.getOptionsMenu() == null)
            {
                gameCore.setOptionsMenu(new OptionsMenu());
                gameCore.getOptionsMenu().setVisible(true);
            }
            gameCore.getOptionsMenu().setVisible(true);
        }
        else if (actionCommand.equals("Start Game"))
        {
            Sound gameStartSound = new Sound("gameStartSound.wav");
            gameStartSound.playSound();
            if (OptionsMenu.hostMode && OptionsMenu.onlineMode && !GameCore.gameStarted)
            {
                if (!gameCore.getOptionsMenu().isHostFirst())
                {
                    PlayerTimer.p1Turn = !PlayerTimer.p1Turn;
                    Host.getHost().send("autoStart;2;" + GameCore.desiredMinutesPerPlayer);
                }
                else
                {
                    Host.getHost().send("autoStart;1;" + GameCore.desiredMinutesPerPlayer);
                }
            }
            else if (!OptionsMenu.hostMode && OptionsMenu.onlineMode && !GameCore.gameStarted)
            {
                Client.getClient().send("autoStart;1;" + GameCore.desiredMinutesPerPlayer);
            }

            GameCore.gameStarted = true;
            gameView.getOptionsButton().setEnabled(false);
            gameView.getStartButton().setEnabled(false);
            gameView.getChessImageContainer().requestFocusInWindow();
        }
    }
}
