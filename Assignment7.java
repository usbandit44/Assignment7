// Assignment #7, CSE205, Arizona State University 
// Name: Arvin Edouard
// StudentID: 1222200512
// Lecture time: Tuesday Thursday 1:30 to 2:45 
// Description: Creates drawing pad comboboxes and radiovos that allows the user to draw circles, lines, and rectangles

/**
 * Assignment #7 
 * Don't forget to add your info to the header! 
**/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Assignment7 extends Application
{
    public static final int WINSIZE_X = 800, WINSIZE_Y = 800;
    private final String WINTITLE = "Sketchy";

    @Override
    public void start(Stage stage) throws Exception
    {
        SketchPane rootPane = new SketchPane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Technically this is not needed for JavaFX applications. Added just in case.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}