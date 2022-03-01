/*		@(#)counter.java	Jan 17, 2022
 * Student: Jingmei Li
 * Professor: Reg Dyer
 * Lab Section: 301
 */

package Lab1FX;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author jingmei Li
 * This program is simply mimics word counter in MicrosoftWord
 * Inherit from Application class. The main JavaFX class must inherit from Application.
 */

/**
 * Summary:
 * Details:
 * @author jingm
 *
 */
public class Counter extends Application {

	/**
	 * To start a JavaFX application we must call the static method
	 * @param args in the main method. This method is from the Application Class.
	 */
	public static void main(String[] args) {
		launch(args);
	}

//---------------------------------------------------------------------

	private TextArea textInput;    // For the user's input text.
	private Label lineCountLabel;  // Label for counting lines.
	private Label wordCountLabel;  // Label for counting words.
	private Label charCountLabel;  // Label for counting chars.
	private Label digitCountLabel; // Label for counting digits.
	private ToolBar statusBar;     // Contains statusBar.

	/**
	 * The constructor creates components and lays out the window.
	 */
	public void start(Stage stage) {

		textInput = new TextArea();
		textInput.setPrefRowCount(20);
		textInput.setPrefColumnCount(50);
		statusBar = new ToolBar();

		/*
		 * call the method createStatusBar
		 */
		createStatusBar();
		
		/*
		 * Create a new button "process the text" .
		 */
		Button countButton = new Button("Process the text");
		
		/*call setOnKeyPressed on this button and pass a lambda to it. In the
		 * lambda if the key pressed, invoke processInput method
		 */
		countButton.setOnKeyPressed(e -> processInput());
		
		/* set button style as
		 * "-fx-background-color: darkslateblue; -fx-text-fill: white;"
		 */
		countButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

		/* when escape key is pressed close the application */

		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {

			if (!event.isConsumed() && KeyCode.ESCAPE == event.getCode()) {
				stage.hide();
			}
		});


		/* Create each of the labels, and set their properties. */

		lineCountLabel = new Label("  Number of lines:");
		lineCountLabel.setStyle(
				"-fx-text-fill: green; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
		lineCountLabel.setMaxWidth(1000);

		/*
		 * Create wordCountLabel, and set their properties.
		 */
		wordCountLabel = new Label(" Number of words: ");
		wordCountLabel.setStyle(
				"-fx-text-fill: red; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
		wordCountLabel.setMaxWidth(1000);

		/* 
		 *  Create charCountLabel, and set their properties.
		 */
		charCountLabel = new Label(" Number of chars: ");
		charCountLabel.setStyle(
				"-fx-text-fill: blue; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
		charCountLabel.setMaxWidth(1000);

		 /* 
		 *  Create digitCountLabel, and set their properties.
		 */
		digitCountLabel = new Label(" Number of digits: ");
		digitCountLabel.setStyle(
				"-fx-text-fill: black; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
		digitCountLabel.setMaxWidth(1000);

		VBox root = new VBox(6, new BorderPane(statusBar), textInput, new BorderPane(countButton), lineCountLabel,
				wordCountLabel, charCountLabel, digitCountLabel);

		root.setStyle("-fx-background-color: lightgrey; -fx-border-color: grey; -fx-border-width:5px");

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("MicrosoftWord/Word/Char/digit Counter");
		stage.setResizable(false);
		stage.show();

	}
	
	/**
	 * To creates toolBar.
	 */
	private void createStatusBar() {

		/*
		 * create a new label to show "Type your text here "
		 */
		Label lable1 = new Label("Type your text here ");
		
		/*
		 * set the style
		 */
		lable1.setStyle("-fx-text-fill: black; -fx-font: bold  12pt serif;-fx-padding: 4px;");
		
		/* 
		 * create a new Pane object\
		 */
		Pane pane = new Pane();
		
		/* 
		 * call the static method setHgrow from HBox and pass to it the pane object
		 * and Priority.ALWAYS.
		 */
		HBox.setHgrow(pane, Priority.ALWAYS);
		
		/* 
		 *  create a new label and set the text as "Press Esc to Exit".
		 */
		Label lable2 = new Label("Press Esc to Exit");
		
		/* 
		 * use the statusBar object to store the 3 object we created here. to
		 * access the list of children in a ToolBar use the method getItems. getItems
		 * method will return a list. use addAll on it to add all the Nodes.
		 */
		statusBar.getItems().addAll(lable1, pane, lable2);
	}

	/**
	 * To count input numbers.
	 */
	public void processInput() {

		String text;

		int charCt, wordCt, lineCt, digitCt; // Char, word, line and digits counts.

		text = textInput.getText();
		
		charCt = text.length();
		
		/*
		 * count the number of characters occurs in the text.
		 */
		int cha = 0;
		for ( int i = 0; i < text.length();i++) {
			if (!Character.isWhitespace(text.charAt(i)))
				cha ++;
		}

		/*
		 * Compute the wordCt by counting the number of characters in the text that lie
		 * at the beginning of a word. The beginning of a word is a letter such that the
		 * preceding character is not a letter. If the letter is the first character in
		 * the text, then it is the beginning of a word. If the letter is preceded by an
		 * apostrophe, and the apostrophe is preceded by a letter, than its not the
		 * first character in a word.
		 */

		wordCt = 0;
		for (int i = 0; i < charCt; i++) {
			boolean startOfWord;
			if (Character.isLetter(text.charAt(i)) == false)
				startOfWord = false;
			else if (i == 0)
				startOfWord = true;
			else if (Character.isLetter(text.charAt(i - 1)))
				startOfWord = false;
			else if (text.charAt(i - 1) == '\'' && i > 1 && Character.isLetter(text.charAt(i - 2)))
				startOfWord = false;
			else
				startOfWord = true;
			if (startOfWord)
				wordCt++;
		}

		/*
		 * count the number of lines occurs in the text.
		 */
		lineCt = 0;
		for (int i = 0; i < charCt; i++) {
			if (text.charAt(i) == '\n')
				lineCt++;
		}

		/*
		 * count the number of digits occurs in the text.
		 */

		digitCt = 0;
		for (int i = 0; i < charCt; i++) {
			if (Character.isDigit(text.charAt(i)))
				digitCt++;
		}

		lineCountLabel.setText("  Number of lines:  " + lineCt);
		wordCountLabel.setText("  Number of words:  " + wordCt);
		charCountLabel.setText("  Number of chars:  " + cha);
		digitCountLabel.setText("  Number of digits:  " + digitCt);

	}

}
