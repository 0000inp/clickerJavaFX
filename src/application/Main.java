package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.scene.image.ImageView;


public class Main extends Application {
	
	public static double gameDiffScaling = 1.5;
	
	public static File bgmfile = new File("bgm.mp3");
	public static File bgm2file = new File("bgm2.mp3");
	
	public static Media bgm = new Media(bgmfile.toURI().toString());
	public static Media bgm2 = new Media(bgm2file.toURI().toString());
	
	public static MediaPlayer bgmPlayer = new MediaPlayer(bgm);
	public static MediaPlayer bgm2Player = new MediaPlayer(bgm2);
	
	public static String currentScene = "Level1";;
	
	@Override
	public void start(Stage primaryStage) {
		FXMLLoader scene1 = new FXMLLoader(getClass().getResource("Main.fxml"));
		FXMLLoader scene2 = new FXMLLoader(getClass().getResource("Level2.fxml"));
		
		Controller controller1 = scene1.getController();
		Controller2 controller2 = scene2.getController();
		
		try {
			Parent root = scene1.load();
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Clicker");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Team Player = new Team();
		Controller.setPlayer(Player);
		Controller2.setPlayer(Player);
		Player.setTeamGoldPerSec(10);
		Player.teamGoldPerSecUpdate();
		
		//background music
		bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		bgmPlayer.setVolume(0.1);
		bgmPlayer.setAutoPlay(true);
		bgmPlayer.setOnEndOfMedia(() -> {
			bgmPlayer.seek(Duration.ZERO);
            bgmPlayer.play();
        });
		bgmPlayer.play();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			System.out.println(bgmPlayer.getStatus());
        }));
		timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
