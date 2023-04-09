package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Controller2 implements Initializable{
	
	private Stage primaryStage;
	private Scene scene;
	private Parent root;
	
	//switch scene
	public void switchScene1(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		Main.bgm2Player.pause();
		Main.bgmPlayer.setVolume(0.1);
		Main.bgmPlayer.play();
		timeline.stop();
		countdown.stop();
	}

	
	//add player
	private static Team Player;

    public static void setPlayer(Team player) {
        Player = player;
    }
    
    private MediaPlayer swordSFX = new MediaPlayer(new Media(new File("swordSFX.wav").toURI().toString()));
    
    private void playSound(MediaPlayer sound, double volume) {
		sound.setVolume(volume);
		sound.stop();
		sound.seek(Duration.ZERO);
		sound.play();
	}
    
    @FXML
    private ImageView backgroundIMG;
    
    @FXML
    private ImageView emberIMG1;

    @FXML
    private ImageView emberIMG2;
    
    @FXML
    private ImageView bossIMG;
    
    @FXML
    private ProgressBar bossHPBar;
    
    @FXML
    private Label countdownLabel;
    
    @FXML
    private Label hpLabel;
    
    private void hpLabelUpdate() {hpLabel.setText(Integer.toString(boss1.getBossHP()));}
    
    @FXML
    private Button switchlevel1Button;
    
    //Image
    private Image backgroundimg = new Image(new File("HOME_BACKGROUND3.jpg").toURI().toString());
  	private Image bossimg = new Image(new File("bossGIF.gif").toURI().toString());
  	private Image emberimg = new Image(new File("emberGIF.gif").toURI().toString());
  	
    //BOSS
    private Boss boss1;
    
    //damage per second
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
    	boss1.bossHPdecrease(Player.getTeamGoldPerSec(), bossHPBar);
    	if(Player.getTeamGoldPerSec() > 0) {
    		playSound(swordSFX, 0.1);
    		jiggleTranslateAnim(bossIMG, 2, 50, "vertical");
    	}
    }));
    //countdown
    private Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
    	boss1.setCount(boss1.getCount() - 1);
    	countdownLabel.setText(Integer.toString(boss1.getCount()));
    	System.out.println(boss1.getCount());
    	if(boss1.getCount() <= 0) {switchlevel1Button.fireEvent(new ActionEvent());/*switch to scene1*/}
    }));
    
    //update when boss hp change
    private ChangeListener<Number> changelistener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
			hpLabelUpdate();
			if(boss1.getBossHP() <= 0) {
				int goldReward = (int)(100 * Player.getDifficultyScale());
				Player.setGold(Player.getGold() + goldReward);
				System.out.println("Gold + " + Integer.toString(goldReward));
				Player.setDifficultyScale(Player.getDifficultyScale() * Main.gameDiffScaling);
				switchlevel1Button.fireEvent(new ActionEvent()); //switch to scene1
			}
		}};
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.currentScene = "Level2";
		//set Image
		backgroundIMG.setImage(backgroundimg);
		emberIMG1.setImage(emberimg);
		emberIMG2.setImage(emberimg);
		bossIMG.setImage(bossimg);
		
		boss1 = new Boss(100 * Player.getDifficultyScale() ,15);
		System.out.println(boss1.getBossHP());
		
		hpLabelUpdate();
		bossHPBar.setProgress(boss1.getBossHP());
		bossHPBar.setStyle("-fx-accent: red;");
		boss1.setBossHP(boss1.getBossHP());
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		countdownLabel.setText(Integer.toString(boss1.getCount()));
		countdown.setCycleCount(Timeline.INDEFINITE);
		countdown.play();
		
		bossHPBar.progressProperty().addListener(changelistener);
		
		bossIMG.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	attack();
		    }
		});
	}
	
	@FXML
	public void attack() {
		playSound(swordSFX, 0.1);
		jiggleTranslateAnim(bossIMG, 2, 50, "horizontal");
		boss1.bossHPdecrease(Player.getPlayerGoldPerClick(), bossHPBar);
	}
	
	private void jiggleRotateAnim(ImageView node, double x, int ms) {
		RotateTransition rotate1 = new RotateTransition(Duration.millis(ms), node);
		RotateTransition rotate2 = new RotateTransition(Duration.millis(ms), node);
		rotate1.setByAngle(x);
		rotate1.setAutoReverse(false);
		rotate2.setByAngle(-1*x);
		rotate2.setAutoReverse(false);
		SequentialTransition sequence = new SequentialTransition(rotate1, rotate2);
		sequence.play();
		//node.setRotate(0);
	}

	private void jiggleTranslateAnim(ImageView node, double x, int ms, String mode) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(ms), node);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(ms), node);
		switch(mode) {
		case "horizontal":
			t1.setByX(x);
			t2.setByX(-1*x);
		case "vertical":
			t1.setByY(x);
			t2.setByY(-1*x);
		}
		SequentialTransition sequence = new SequentialTransition(t1, t2);
		sequence.play();
	}
}
