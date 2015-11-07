package andrewLuke.chess.application;

import andrewLuke.chess.control.ButtonController;
import andrewLuke.chess.model.GameCore;
import andrewLuke.chess.view.GameView;
import java.util.Random;

/**
 * This class creates and runs the GameCore
 * <p>
 * This class simply contains the main method. I chose to include it separate
 * from the GameCore simply for the purpose of making it clear as to where the
 * main method is. Since runtime performance is no issue for this program, we
 * can leave this class here with no significantly added delay in loading
 *
 * @author Andrew
 */
public class ChessStart
{

    private static final long serialVersionUID = 1L;

    public static int width = 800;
    public static int height = 600;

    private static GameView gameView;
    private static GameCore gameCore;
    private static ButtonController buttonController;
    public static Random random;

    /**
     * The main method for the application. Initializes everything important.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        random = new Random();
        gameView = new GameView(width,height);
        gameCore = new GameCore(gameView);
        buttonController = new ButtonController(gameView,gameCore);
        gameView.setButtonControllers(buttonController);
        gameCore.start();
    }
}