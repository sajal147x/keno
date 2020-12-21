
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application{

	TextField text; 
	TextField spot;
	TextField draw;
	Button addToListBtn, sceneChangeBtn, b4,b5,b6,b7, printListBtn;  
	HashMap<String, Scene> sceneMap; 
	GenericQueue<String> myQueue;
	EventHandler<ActionEvent> returnButtons;
	EventHandler<ActionEvent> spotHandler;
	EventHandler<ActionEvent> drawHandler;
	EventHandler<ActionEvent> ticketHandler;
	EventHandler<ActionEvent> randomHandler;
	EventHandler<ActionEvent> playHandler;
	ListView<String> displayQueueItems;
	//use this to add and delete from ListView
	ObservableList<String> storeQueueItemsInListView;
	//use this for pausing between actions
	PauseTransition pause = new PauseTransition(Duration.seconds(3));
	//constant values java style
	static final int picHeight = 275;
	static final int picWidth = 250;
	Button playButton;
	BorderPane pane;
	BorderPane pane2;
	Menu menuTab;
	MenuItem iOne;
	MenuItem iTwo;
	MenuItem iThree;
	MenuItem iFour;
	TextArea t1;
	ImageView v;
	ImageView play;
	Button spot1;
	Button spot4;
	Button spot8;
	Button spot10;
	int userSpots;
	int userDrawings;
	GridPane drawingPane;
	GridPane ticket;
	Boolean spotCheck = false;
	Boolean drawCheck = false;
	ArrayList<Integer> ticketNumbers;
	ArrayList<Integer> winningList;
	ArrayList<Integer> matchList;
	GridPane winningNumbers;
	VBox ticketBox;
	Button startButton;
	Button chosenNumber;
	int colTrav=0;
	int rowTrav=0;
	int Number;
	VBox gameBox;
	Integer matchCounter = 0;
	String matchesList="";
	double Odds=0;
	int Prize=0;
	int currDrawing=0;
	Button randButton;
	TextField oddsText;
	TextField numMatches;
	TextField match;
	TextField prizeText;
	
public static void main(String[] args) {

launch(args);
}
 
public void start(Stage primaryStage) throws Exception {
	
	sceneMap = new HashMap<String,Scene>();
	primaryStage.setTitle("The mini chonk");
	
	//Menu Item related functions
	menuTab = new Menu("Menu");
	iOne = new MenuItem("rules");
	iTwo = new MenuItem("winning odds");
	iThree = new MenuItem("exit");
	
	//Rules Action
	iOne.setOnAction(e->{
		t1 = new TextArea();
		t1.setText("The game of Keno is played by picking a set of numbers\nbetween 1 and 80."
				+ "From there, 20 numbers are \ndrawn randomly. If the numbers\ndrawn match"
				+ "the numbers you selected, you win.\nThe amount of numbers\nyou picked and"
				+ "how many of them you got\nright determine the amount of your payout.");
		t1.setEditable(false);
		t1.setMaxHeight(175);
		t1.setMaxWidth(450);
		pane.setRight(t1);
		t1.setPadding(new Insets(0, 20, 0, 0));
		t1.setStyle("-fx-control-inner-background: black;" +
				"-fx-font-weight: bold;");	
	});
	
	//Winning Odds Action
	iTwo.setOnAction(e->{
		TextArea t2 = new TextArea();
		t2.setText("odds of winning:"+Odds);
		t2.setMaxHeight(150);
		t2.setMaxWidth(550);
		t2.setEditable(false);
		pane.setRight(t2);
	});
	
	//Exit Action
	iThree.setOnAction(e->System.exit(0));
	Image pic = new Image("Keno2.PNG");
	v = new ImageView(pic);
	v.setFitHeight(picHeight);
	v.setFitWidth(picWidth);
	v.setPreserveRatio(true);
	
	//Spot Action Handler
	spotHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			userSpots = Integer.parseInt(((Button)e.getSource()).getText());
			//System.out.println(userSpots);
			Button temp = (Button)e.getSource();
			temp.setDisable(true);
			if(spot1!=temp) {
				spot1.setDisable(false);
			}
			if(spot4!=temp) {
				spot4.setDisable(false);
			}
			if(spot8!=temp) {
				spot8.setDisable(false);
			}
			if(spot10!=temp) {
				spot10.setDisable(false);
			}
			ticketNumbers.clear();
			for(int x=0;x<10; x++) {
				for(int i=0;i<8;i++) {
					Button ticketB = new Button(Integer.toString(x+10*i+1));
					ticketB.setStyle("-fx-background-color: #FFFFFF;"+
							"-fx-border-style: solid inside;" + 
			                "-fx-border-width: 2;" +
			                "-fx-border-color: #F96A14;");
					ticketB.setPrefSize(45,45);
					ticketB.setDisable(true);
					ticketB.setOnAction(ticketHandler);
					ticket.add(ticketB, x, i);
				}
			}
			spotCheck = true;
			if(spotCheck==true&&drawCheck==true) {
				for(javafx.scene.Node node: ticket.getChildren()) {
					node.setDisable(false);
				}
			}
		}
	};
	
	//Draw Action Handler
	drawHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			userDrawings = Integer.parseInt(((Button)e.getSource()).getText());
			//System.out.println(userSpots);
			Button temp = (Button)e.getSource();
			temp.setDisable(true);
			for (javafx.scene.Node node : drawingPane.getChildren()) {
		        if(node!=temp) {
		        	node.setDisable(false);
		        }
		    }
			drawCheck = true;
			if(spotCheck==true&&drawCheck==true) {
				for(javafx.scene.Node node: ticket.getChildren()) {
					node.setDisable(false);
				}
			}
		}
	};
	
	//Ticket Action Handler
	ticketNumbers = new ArrayList<Integer>();
	ticketHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(ticketNumbers.size()<userSpots&&
					ticketNumbers.contains(Integer.parseInt(((Button)event.getSource()).getText()))==false) {
				ticketNumbers.add(Integer.parseInt(((Button)event.getSource()).getText()));
				Button temp = (Button)event.getSource();
				temp.setStyle("-fx-background-color: GREEN;");
			} else if(ticketNumbers.contains(Integer.parseInt(((Button)event.getSource()).getText()))==true) {
				Button temp = (Button)event.getSource();
				int index = ticketNumbers.indexOf(Integer.parseInt(((Button)event.getSource()).getText()));
				ticketNumbers.remove(index);
				temp.setStyle("-fx-background-color: #FFFFFF;"+
						"-fx-border-style: solid inside;" + 
		                "-fx-border-width: 2;" +
		                "-fx-border-color: #F96A14;");
			}
		}
		
	};
	
	//Random Action Handler
	randomHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			ticketNumbers.clear();
			for(int i=0; i<userSpots; i++) {
				int randNum = (int)(Math.random() * 80);
				while(ticketNumbers.contains(randNum)==true) {
					randNum = (int)(Math.random() * 80);
				}
				ticketNumbers.add(randNum);
			}
			for(javafx.scene.Node node: ticket.getChildren()) {
				node.setStyle("-fx-background-color: #FFFFFF;"+
						"-fx-border-style: solid inside;" + 
		                "-fx-border-width: 2;" +
		                "-fx-border-color: #F96A14;");
				int col = GridPane.getColumnIndex(node);
				int row = GridPane.getRowIndex(node);
				if(ticketNumbers.contains(col+10*row+1)==true) {
					node.setStyle("-fx-background-color: GREEN;");
				}
			}
		}
		
	};
	
	
	//Add Button Handler
	EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			Number = 1+(int)(Math.random() * 80);
			while(winningList.contains(Number)==true) {
				Number = 1+(int)(Math.random() * 80);
			}
			winningList.add(Number);
			chosenNumber = new Button(Integer.toString(Number));
			chosenNumber.setStyle("-fx-background-color: #FFFFFF;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: #F96A14;");
			chosenNumber.setPrefSize(45,45);
			winningNumbers.add(chosenNumber, colTrav, rowTrav);
			//pane.setLeft(winningNumbers);
			rowTrav++;
			if(rowTrav==4) {
				rowTrav=0;
				colTrav++;
			}
			if(ticketNumbers.contains(Number)) {
				matchList.add(Number);
				matchesList+=Integer.toString(Number);
				matchesList+=" ";
				//System.out.println(matchesList);
				matchCounter++;
			}
			if(colTrav==5) {
				//System.out.println(userSpots+" "+userDrawings);
				numMatches = new TextField();
				numMatches.setText("The number of matches is: "+matchCounter);
				numMatches.setStyle("-fx-border-width: 2;"+
						"-fx-border-color: #F96A14;"+
						"-fx-border-radius: 5;"+
						"-fx-background-color: #000000;"+
						"-fx-text-fill: #FFFFFF;");
				match = new TextField();
				match.setText("The number of matches is: "+matchesList);
				match.setStyle("-fx-border-width: 2;"+
						"-fx-border-color: #F96A14;"+
						"-fx-border-radius: 5;"+
						"-fx-background-color: #000000;"+
						"-fx-text-fill: #FFFFFF;");
				matchCounter=0;
				rowTrav=0;
				colTrav=0;
				int temp=Prize;
				Odds=0;
				if(userSpots==1) {
					Odds=4;
					if(matchList.size()==1) {
						Prize=2;
					}
				} else if(userSpots==4) {
					Odds=3.86;
					if(matchList.size()==2) {
						Prize=1;
					} else if(matchList.size()==3) {
						Prize = 5;
					} else if(matchList.size()==4) {
						Prize = 75;
					}
				} else if(userSpots==8) {
					Odds=9.77;
					if(matchList.size()==4) {
						Prize=2;
					} else if(matchList.size()==5) {
						Prize=12;
					} else if(matchList.size()==6) {
						Prize=50;
					} else if(matchList.size()==7) {
						Prize=750;
					} else if(matchList.size()==8) {
						Prize=10000;
					}
				} else if(userSpots==10) {
					Odds=9.05;
					if(matchList.size()==0) {
						Prize=5;
					} else if(matchList.size()==5) {
						Prize=2;
					} else if(matchList.size()==6) {
						Prize=15;
					} else if(matchList.size()==7) {
						Prize=40;
					} else if(matchList.size()==8) {
						Prize=450;
					} else if(matchList.size()==9) {
						Prize=4250;
					} else if(matchList.size()==10) {
						Prize=100000;
					}
				}
				Prize+=temp;
				System.out.println(Prize);
				matchList.clear();
				prizeText = new TextField();
				prizeText.setText("The Prize you have earned is: "+Prize);
				prizeText.setStyle("-fx-border-width: 2;"+
						"-fx-border-color: #F96A14;"+
						"-fx-border-radius: 5;"+
						"-fx-background-color: #000000;"+
						"-fx-text-fill: #FFFFFF;");
				oddsText = new TextField();
				oddsText.setText("The Odds of winning were: "+Odds);
				oddsText.setStyle("-fx-border-width: 2;"+
						"-fx-border-color: #F96A14;"+
						"-fx-border-radius: 5;"+
						"-fx-background-color: #000000;"+
						"-fx-text-fill: #FFFFFF;");
				gameBox.getChildren().addAll(numMatches, match, oddsText, prizeText);
			}
		}
		
	};
	
	
	//Play Action Handler
	winningList= new ArrayList<Integer>();
	playHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(ticketNumbers.size()!=userSpots||drawCheck==false||spotCheck==false) {
				return;
			}
			if(startButton.getText()=="CONTINUE") {
				gameBox.getChildren().remove(numMatches);
				gameBox.getChildren().remove(match);
				gameBox.getChildren().remove(oddsText);
				gameBox.getChildren().remove(prizeText);
			}
			if(startButton.getText()=="Restart") {
				gameBox.getChildren().remove(numMatches);
				gameBox.getChildren().remove(match);
				gameBox.getChildren().remove(oddsText);
				gameBox.getChildren().remove(prizeText);
				startButton.setText("START");
				spot1.setDisable(false);
				spot4.setDisable(false);
				spot8.setDisable(false);
				spot10.setDisable(false);
				for(javafx.scene.Node node:drawingPane.getChildren()) {
					node.setDisable(false);
				}
				randButton.setDisable(false);
				winningNumbers.getChildren().clear();
				primaryStage.setScene(sceneMap.get("welcome"));
				return;
			}
			if(startButton.getText()=="START") {
				spot1.setDisable(true);
				spot4.setDisable(true);
				spot8.setDisable(true);
				spot10.setDisable(true);
				for(javafx.scene.Node node:drawingPane.getChildren()) {
					node.setDisable(true);
				}
				randButton.setDisable(true);
			}
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.seconds(1), buttonHandler),
					new KeyFrame(Duration.millis(500))
				);
			matchList= new ArrayList<Integer>();
			timeline.setCycleCount(20);
			startButton.setText("CONTINUE");
			startButton.setStyle("-fx-background-color: #000000;"+
					"-fx-text-fill: #FFFFFF;"+
					"-fx-font-weight: bold;"+
					"-fx-border-width: 10;"+
					"-fx-border-color: #F96A14;"+
					"-fx-border-radius: 5;"+
					"-fx-font-size: 18;");
			winningList.clear();
			winningNumbers = new GridPane();
			pane2.setLeft(winningNumbers);
			timeline.play();
			winningNumbers.setPadding(new Insets(100, 0, 0, 100));
			ticketBox.setPadding(new Insets (120, 0, 0, 100));
			currDrawing++;
			if(currDrawing==userDrawings) {
				currDrawing=0;
				startButton.setText("Restart");
			}
			//numMatches.setText("The number of matches is "+count);
			//gameBox.getChildren().add(numMatches);
			
		}
		
	};
	
	//Image for Welcome screen
	Image buttonPic = new Image("Play2.jpg");
	play = new ImageView(buttonPic);
	playButton = new Button();
	playButton.setGraphic(play);
	playButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("play")));
	
	//Setting scene
	sceneMap.put("welcome",  welcomeScene());
	sceneMap.put("play", gameScene());
	primaryStage.setScene(sceneMap.get("welcome"));
	primaryStage.show();
	
	}
	

//-------------------------------------------------------------------------
	public Scene welcomeScene() {
		MenuBar menu;
		menu = new MenuBar();
		pane = new BorderPane(); 
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(v, playButton);
		menuTab.getItems().add(iOne);
		menuTab.getItems().add(iTwo);
		menuTab.getItems().add(iThree);
		menu.getMenus().addAll(menuTab);
		menu.setStyle("-fx-background-color: #2484C8;" +
				"-fx-pref-height: 35;"+
				"-fx-font-weight: bold;"+
				"-fx-font-family: sans;"+
				"-fx-font-size: 29;");
		pane.setCenter(vbox);
		pane.setTop(menu);
		pane.setStyle( "-fx-background-color: #000000;" +     
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 20;" +
                "-fx-border-color: #d80000;");
		vbox.setPadding(new Insets(80, 0 , 0 , 500));
		return new Scene(pane, 1280, 720);
	}
	
	
//------------------------------------------------------------------------
	public Scene gameScene() {
		pane2 = new BorderPane();
		gameBox = new VBox(10);
		t1 = new TextArea();
		t1.setVisible(false);
		//Recreating old menu items for uniqueness
		MenuItem iFive = new MenuItem("rules");
		MenuItem iSix = new MenuItem("winning odds");
		MenuItem iSeven = new MenuItem("exit");
		iFive.setOnAction(e->{
			System.out.println("Hi");
			gameBox.getChildren().remove(0);
			//t1 = new TextArea();
			t1.setText("The game of Keno is played by picking a set of numbers\nbetween 1 and 80."
					+ "From there, 20 numbers are \ndrawn randomly. If the numbers\ndrawn match"
					+ "the numbers you selected, you win.\nThe amount of numbers\nyou picked and"
					+ "how many of them you got\nright determine the amount of your payout.");
			t1.setEditable(false);
			t1.setMaxHeight(175);
			t1.setMaxWidth(450);
			//pane2.setRight(t1);
			gameBox.getChildren().add(0, t1);
			t1.setVisible(true);
			t1.setPadding(new Insets(0, 100, 0, 0));
			t1.setStyle("-fx-control-inner-background: black;" +
					"-fx-font-weight: bold;");
		});
		iSix.setOnAction(e->{
			TextArea t2 = new TextArea();
			t2.setText("Odds of winning: "+Odds);
			t2.setMaxHeight(150);
			t2.setMaxWidth(550);
			t2.setEditable(false);
			gameBox.getChildren().remove(0);
			gameBox.getChildren().add(0, t2);
			t2.setStyle("-fx-control-inner-background: black;" +
					"-fx-font-weight: bold;");
			t2.setPadding(new Insets(0, 100, 0, 0));
		});
		
		iSeven.setOnAction(e->System.exit(0));
		//Making the menu
		Menu gameMenuTab = new Menu("Menu");
		MenuBar newMenu = new MenuBar();
		iFour = new MenuItem("New Look");
		gameMenuTab.getItems().addAll(iFive, iSix, iSeven, iFour);
		newMenu.getMenus().addAll(gameMenuTab);
		newMenu.setStyle("-fx-background-color: #FFFFFF;" +
				"-fx-pref-height: 35;"+
				"-fx-font-weight: bold;"+
				"-fx-font-family: sans;"+
				"-fx-font-size: 29;");
		
		// Making the spots
		GridPane spotPane = new GridPane();
		spot = new TextField("Number of Spots");
		spot.setEditable(false);
		spot.setStyle("-fx-border-width: 2;"+
				"-fx-border-color: #F96A14;"+
				"-fx-border-radius: 5;"+
				"-fx-background-color: #000000;"+
				"-fx-text-fill: #FFFFFF;");
		spot1 = new Button("1");
		spot4 = new Button("4");
		spot8 = new Button("8");
		spot10 = new Button("10");
		spotPane.add(spot1, 0, 0);
		spotPane.add(spot4, 1, 0);
		spotPane.add(spot8, 2, 0);
		spotPane.add(spot10, 3, 0);
		spot1.setStyle("-fx-background-color: #FFFFFF;"+
				"-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-color: #F96A14;");
		spot4.setStyle("-fx-background-color: #FFFFFF;"+
				"-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-color: #F96A14;");
		spot8.setStyle("-fx-background-color: #FFFFFF;"+
				"-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-color: #F96A14;");
		spot10.setStyle("-fx-background-color: #FFFFFF;"+
				"-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-color: #F96A14;");
		spot1.setOnAction(spotHandler);
		spot4.setOnAction(spotHandler);
		spot8.setOnAction(spotHandler);
		spot10.setOnAction(spotHandler);
		
		HBox spots = new HBox(15, spot, spotPane);
		
		//Making the Start button
		HBox temp = new HBox();
		startButton = new Button("START");
		temp.getChildren().add(startButton);
		temp.setAlignment(Pos.CENTER_RIGHT);
		//pane2.setBottom(temp);
		//temp.setPadding(new Insets(150,0,0,50));
		startButton.setPrefSize(150, 75);
		startButton.setStyle("-fx-background-color: #000000;"+
				"-fx-text-fill: #FFFFFF;"+
				"-fx-font-weight: bold;"+
				"-fx-border-width: 10;"+
				"-fx-border-color: #F96A14;"+
				"-fx-border-radius: 5;"+
				"-fx-font-size: 25;");
		startButton.setOnAction(playHandler);
		
		//Making the RandomButton
		HBox randomTemp = new HBox();
		randButton = new Button("Quickpick");
		randomTemp.getChildren().add(randButton);
		randomTemp.setAlignment(Pos.CENTER);
		//pane2.setRight(randomTemp);
		//randomTemp.setPadding(new Insets(150, 0, 0, 50));
		randButton.setStyle("-fx-background-color: #000000;"+
				"-fx-text-fill: #FFFFFF;"+
				"-fx-font-weight: bold;"+
				"-fx-border-width: 5;"+
				"-fx-border-color: #F96A14;"+
				"-fx-border-radius: 5;"+
				"-fx-font-size: 25;");
		randButton.setOnAction(randomHandler);
		gameBox.getChildren().addAll(t1, randButton,startButton);
		gameBox.setPadding(new Insets(0, 0, 0, 100));
		pane2.setRight(gameBox);
		
		//Making the drawing
		drawingPane = new GridPane();
		for(int i=0; i<4; i++) {
			Button drawButton = new Button(Integer.toString(i+1));
			drawButton.setStyle("-fx-background-color: #FFFFFF;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: #F96A14;");
			drawButton.setOnAction(drawHandler);
			drawingPane.add(drawButton, i, 0);
		}
		draw = new TextField("Number of Drawings");
		draw.setEditable(false);
		draw.setStyle("-fx-border-width: 2;"+
				"-fx-border-color: #F96A14;"+
				"-fx-border-radius: 5;"+
				"-fx-background-color: #000000;"+
                "-fx-text-fill: #FFFFFF;");
		HBox drawings = new HBox(15, draw, drawingPane);
		
		//Making the ticket
		ticket = new GridPane();
		for(int x=0;x<10; x++) {
			for(int i=0;i<8;i++) {
				Button ticketB = new Button(Integer.toString(x+10*i+1));
				ticketB.setStyle("-fx-background-color: #FFFFFF;"+
						"-fx-border-style: solid inside;" + 
		                "-fx-border-width: 2;" +
		                "-fx-border-color: #F96A14;"
		                );
				ticketB.setMinSize(45,45);
				ticketB.setDisable(true);
				ticketB.setOnAction(ticketHandler);
				ticket.add(ticketB, x, i);
			}
		}
		
		//Making the main ticket Box
		ticketBox = new VBox(20, spots, drawings, ticket);
		
		iFour.setOnAction(e->{
			pane2.setStyle( "-fx-background-color: blue;" +     
	                "-fx-border-style: solid inside;" + 
	                "-fx-border-width: 20;" +
	                "-fx-border-color: red;");
			startButton.setStyle("-fx-background-color: white;"+
					"-fx-text-fill: black;"+
					"-fx-font-weight: bold;"+
					"-fx-border-width: 10;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-font-size: 25;");
			randButton.setStyle("-fx-background-color: white;"+
					"-fx-text-fill: black;"+
					"-fx-font-weight: bold;"+
					"-fx-border-width: 5;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-font-size: 25;");
			spot1.setStyle("-fx-background-color: white;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: red;");
			spot4.setStyle("-fx-background-color: white;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: red;");
			spot8.setStyle("-fx-background-color: white;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: red;");
			spot10.setStyle("-fx-background-color: white;"+
					"-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-color: red;");
			for(javafx.scene.Node node: drawingPane.getChildren()) {
				node.setStyle("-fx-border-width: 2;"+
						"-fx-border-color: red;"+
						"-fx-border-radius: 5;"+
						"-fx-background-color: white;"+
		                "-fx-text-fill: #000000;");
			}
			for(javafx.scene.Node node: ticket.getChildren()) {
				node.setStyle("-fx-background-color: white;"+
						"-fx-border-style: solid inside;" + 
		                "-fx-border-width: 2;" +
		                "-fx-border-color: red;"
		                );
			}
			oddsText.setStyle("-fx-border-width: 2;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-background-color: white;"+
					"-fx-text-fill: black;");
			numMatches.setStyle("-fx-border-width: 2;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-background-color: white;"+
					"-fx-text-fill: black;");
			match.setStyle("-fx-border-width: 2;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-background-color: white;"+
					"-fx-text-fill: black;");
			prizeText.setStyle("-fx-border-width: 2;"+
					"-fx-border-color: red;"+
					"-fx-border-radius: 5;"+
					"-fx-background-color: white;"+
					"-fx-text-fill: black;");
			
		});
		
		//Making the pane
		pane2.setCenter(ticketBox);
		pane2.setTop(newMenu);
		ticketBox.setPadding(new Insets (120, 0, 0, 380));
		pane2.setStyle( "-fx-background-color: #000000;" +     
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 20;" +
                "-fx-border-color: #F96A14;");
		return new Scene(pane2, 1280, 720);
	}
	
	Boolean welcomePane() {
		if(pane==null) {
			return true;
		} else {
			return false;
		}
	}
	Boolean gamePane() {
		if(pane2==null) {
			return true;
		} else {
			return false;
		}
	}
}

