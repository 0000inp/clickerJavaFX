package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.animation.SequentialTransition;
import javafx.scene.effect.Bloom;
import javafx.scene.Node;


public class Controller implements Initializable{
	
	private Stage primaryStage;
	private Scene scene;
	private Parent root;
	
	//switch scene
	public void switchScene2(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Level2.fxml"));
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		Main.bgmPlayer.pause();
		Main.bgm2Player.setVolume(0.1);
		Main.bgm2Player.play();
		timeline.pause();
	}
	
	public void playTimeline() {timeline.play();}
	
	//add player
	private static Team Player;

    public static void setPlayer(Team player) {
        Player = player;
    }
	
    private Random random = new Random();
	
	@FXML
	private Label levelLabel;
	
	@FXML
	private Label goldLabel;
	
	public void goldUpdate() {
		goldLabel.setText(Integer.toString(Player.getGold()));
		jiggleTranslateAnim(goldLabel, 2, 50, "vertical");
	}
	
	@FXML
	private Label goldpersecLabel;
	
	@FXML
	private Button buyButton;
	
	@FXML
	private TableView<Character> teamTableView;
	
	@FXML
	private TableColumn<Character, String> nameColumn;
	
	@FXML
	private TableColumn<Character, Integer> minespeedColumn;
	
	@FXML
	private ComboBox<Character> CharacterBuyComboBox;
	
	@FXML
	private ImageView pickaxeIMG;
	
	@FXML
	private ImageView goldoreIMG;
	
	@FXML
	private ImageView sparkGIF;
	
	private Image spark = new Image(new File("sparkGIF.gif").toURI().toString());
	private Timeline sparkAnim = new Timeline(new KeyFrame(Duration.millis(450), event -> {
		sparkGIF.setImage(spark);
	}));
	
	private Bloom bloomFX = new Bloom();
	
	@FXML
	void Select(ActionEvent e) {
		
	}
	
	//SFX	
	private MediaPlayer pickaxeSFX = new MediaPlayer(new Media(new File("pickaxeSFX.mp3").toURI().toString()));
	private MediaPlayer coinSFX = new MediaPlayer(new Media(new File("coinSFX.mp3").toURI().toString()));
	private MediaPlayer coindropSFX = new MediaPlayer(new Media(new File("coindropSFX.mp3").toURI().toString()));
	
	private void playSound(MediaPlayer sound, double volume) {
		sound.setVolume(volume);
		sound.stop();
		sound.seek(Duration.ZERO);
		sound.play();
	}
	
	//gameloop timeline update every second
	private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
		Player.incrementGold(Player.getTeamGoldPerSec());
		goldUpdate();
		if(Player.getTeamGoldPerSec() > 0) {
			coinSFX.setStartTime(Duration.millis(500));
			goldpersecLabel.setText("Gold per second : " + Integer.toString(Player.getTeamGoldPerSec()));
			playSound(coinSFX,0.2);
		}
		
		jiggleTranslateAnim(pickaxeIMG, 5, 500, "vertical");
		goldLabel.setTranslateY(0);
    }));
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Character dwarf = new Dwarf();
		
		CharacterBuyComboBox.getItems().addAll(dwarf);
		//this.teamTableView = new TableView<Character>();
		nameColumn = new TableColumn<Character, String>();
		minespeedColumn = new TableColumn<Character, Integer>();
		
		ObservableList<Character> charactersOL = FXCollections.observableArrayList(
					new Character("Dwarfdd", 100,100,100)
				);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("name"));
		minespeedColumn.setCellValueFactory(new PropertyValueFactory<Character, Integer>("mineSpeed"));
		//teamTableView.getColumns().addAll(nameColumn, minespeedColumn);
		charactersOL.add(dwarf);
		teamTableView.setItems(charactersOL);
		
		//play gameloop timeline
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        Main.currentScene = "Level1";
        System.out.println(Main.currentScene);
        
        //bloom effect
        goldoreIMG.setEffect(bloomFX);
        Timeline bloom = new Timeline();
        bloom.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(bloomFX.thresholdProperty(), 0.2)),
                new KeyFrame(Duration.seconds(1), new KeyValue(bloomFX.thresholdProperty(), 0.7)),
                new KeyFrame(Duration.seconds(2), new KeyValue(bloomFX.thresholdProperty(), 0.2))
        );
        bloom.setCycleCount(Timeline.INDEFINITE);
        bloom.setAutoReverse(true);
        bloom.play();
        
        
        //Label
        goldpersecLabel.setText("Gold per second : " + 0);
        
        //ImageView
        sparkAnim.setOnFinished(event -> {
        	sparkGIF.setImage(null);
        });
	}
	
	//Event
	public void click(ActionEvent e) {
		Player.incrementGold(Player.getPlayerGoldPerClick());
		//System.out.println(Player.Gold);
		//System.out.println(Player.TeamMember);
		jiggleRotateAnim(pickaxeIMG, 20, 50);
		jiggleTranslateAnim(goldoreIMG, 2, 50, "vertical");
		playSound(pickaxeSFX,0.1);
		goldUpdate();
		sparkAnim.play();
		sparkGIF.setImage(spark);
	}
	
	public void buy(ActionEvent e) {
		Character selectedOption = CharacterBuyComboBox.getSelectionModel().getSelectedItem();
		if(selectedOption == null) {System.out.println("Choose character First");}
		else {
			if(Player.getGold() >= selectedOption.getBuyPrice()) {
				Player.getTeamMember().add(selectedOption);
				Player.incrementGold(-1 * selectedOption.getBuyPrice());
				Player.teamGoldPerSecUpdate();
				goldUpdate();
				ObservableList<Character> charactersOL = FXCollections.observableList(Player.getTeamMember());
				teamTableView.setItems(charactersOL);
				playSound(coindropSFX,0.1); sparkGIF.setImage(null);
			}
			else {System.out.println("Not enough money");}
			goldpersecLabel.setText("Gold per second : " + Integer.toString(Player.getTeamGoldPerSec()));
		}
	}
	
	//Animation
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
	
	private void jiggleTranslateAnim(Label node, double x, int ms, String mode) {
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
		node.setTranslateY(0);
	}
	
	private void spawnCharacter() {
		ImageView newChar = new ImageView(new Image(getClass().getResourceAsStream("dwarf.png")));
		double xMin = 50;
		double xMax = 350;
		double yMin = 50;
		double yMax = 350;
		
		double x = random.nextDouble();
		double y = random.nextDouble();
		newChar.setLayoutX(500);
		newChar.setLayoutY(100);
	}
}
