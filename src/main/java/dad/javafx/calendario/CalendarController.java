package dad.javafx.calendario;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CalendarController implements Initializable {

	private static final int COLUMNS = 4;

	// ---------------------
	// modelo

	private IntegerProperty year = new SimpleIntegerProperty();

	// ---------------------
	// vista

	private MonthCalendar[] monthCalendars;

	@FXML
	private BorderPane view;

	@FXML
	private Label yearLabel;

	@FXML
	private GridPane monthsPane;

	public CalendarController() {

		// cargamos la vista desde el fichero FXML
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Calendar.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.monthCalendars = new MonthCalendar[12];
		for (int month = 0; month < 12; month++) {
			int col = month % COLUMNS;
			int row = month / COLUMNS;
			monthCalendars[month] = new MonthCalendar();
			monthCalendars[month].setMonth(month + 1);
			monthCalendars[month].yearProperty().bind(year);
			monthsPane.add(monthCalendars[month], col, row);
		}

		this.yearLabel.textProperty().bind(this.year.asString());

		this.year.set(LocalDate.now().getYear());

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	private void onPreviousButtonAction(ActionEvent e) {
		int width = (int) monthsPane.getWidth();
		int height = (int) monthsPane.getHeight();

		SnapshotParameters param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);

		Image prevCalendarSnapshot = monthsPane.snapshot(param, new WritableImage(width, height));
		ImageView prevImageView = new ImageView(prevCalendarSnapshot);

		year.set(year.get() - 1);

		Image newCalendarSnapshot = monthsPane.snapshot(param, new WritableImage(width, height));
		ImageView newImageView = new ImageView(newCalendarSnapshot);

		Pane pane = new Pane(newImageView, prevImageView);
		pane.setClip(new Rectangle(width, height));

		view.setCenter(pane);

		Duration duration = Duration.seconds(0.5);

		TranslateTransition movePrev = new TranslateTransition();
		movePrev.setNode(prevImageView);
		movePrev.setDuration(duration);
		movePrev.setFromX(0);
		movePrev.setToX(width);

		TranslateTransition moveNew = new TranslateTransition();
		moveNew.setNode(newImageView);
		moveNew.setDuration(duration);
		moveNew.setFromX(-width);
		moveNew.setToX(0);

		ParallelTransition allTransitions = new ParallelTransition();
		allTransitions.setInterpolator(Interpolator.EASE_OUT);
		allTransitions.getChildren().addAll(movePrev, moveNew);
		allTransitions.setOnFinished(p -> view.setCenter(monthsPane));
		allTransitions.play();
	}

	@FXML
	private void onNextButtonAction(ActionEvent e) {
		int width = (int) monthsPane.getWidth();
		int height = (int) monthsPane.getHeight();

		SnapshotParameters param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);

		Image prevCalendarSnapshot = monthsPane.snapshot(param, new WritableImage(width, height));
		ImageView prevImageView = new ImageView(prevCalendarSnapshot);

		year.set(year.get() + 1);

		Image newCalendarSnapshot = monthsPane.snapshot(param, new WritableImage(width, height));
		ImageView newImageView = new ImageView(newCalendarSnapshot);

		Pane pane = new Pane(newImageView, prevImageView);
		pane.setClip(new Rectangle(width, height));

		view.setCenter(pane);

		Duration duration = Duration.seconds(0.5);
		
		TranslateTransition movePrev = new TranslateTransition();
		movePrev.setNode(prevImageView);
		movePrev.setDuration(duration);
		movePrev.setFromX(0);
		movePrev.setToX(-width);

		TranslateTransition moveNew = new TranslateTransition();
		moveNew.setNode(newImageView);
		moveNew.setDuration(duration);
		moveNew.setFromX(width);
		moveNew.setToX(0);

		ParallelTransition allTransitions = new ParallelTransition();
		allTransitions.setInterpolator(Interpolator.EASE_OUT);
		allTransitions.getChildren().addAll(movePrev, moveNew);
		allTransitions.setOnFinished(p -> view.setCenter(monthsPane));
		allTransitions.play();
		
	}

}
