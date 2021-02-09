package dad.javafx.calendario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private CalendarController calendarioController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		calendarioController = new CalendarController();
		
		Scene scene = new Scene(calendarioController.getView());
		scene.getStylesheets().add("/css/styles.css");
		
		primaryStage.setTitle("Calendario");
		primaryStage.getIcons().add(new Image("/images/calendar-16x16.png"));
		primaryStage.getIcons().add(new Image("/images/calendar-32x32.png"));
		primaryStage.getIcons().add(new Image("/images/calendar-64x64.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
