import Fonts.Fonts;
import boggle.Dictionary;
import boggle.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI implements ActionListener {
    JFrame frame;
    JButton startButton, nextButton, oneButton, twoButton, oneButton2, twoButton2, enterButton, finishButton,
    yesButton, noButton, confirmButton;
    JLabel label, label2, label3, roundStats, human, computer, wordsFound, foundNum, roundScore, scoreNum, playAgain,
    gameStats, rounds, enterWords, totalScore, averageWords, thanks;
    JTextArea t, t2, t3, t4, t5, t6;
    Fonts font;
    int boardSize;
    BoggleData d = new BoggleData();
    BoggleGrid g = new BoggleGrid(boardSize);
    Dictionary boggleDict = new Dictionary("wordlist.txt");
    Map<String, ArrayList<Position>> w = new HashMap<String, ArrayList<Position>>();

    Boolean set_inverted;

    public GUI(){
        FontFactory fontFactory = new FontFactory();
        Scanner s = new Scanner(System.in);
        System.out.println("Choose Font: (Arial, Courier, Helvetica, Verdana)");
        String word = s.nextLine();
        while(!Objects.equals(word, "Arial") && !Objects.equals(word, "Courier") && !Objects.equals(word,
                "Helvetica") && !Objects.equals(word, "Verdana")){
            System.out.println("Please try again.");
            word = s.nextLine();
        }
        font = fontFactory.createFont(word);

        Scanner x = new Scanner(System.in);
        System.out.println("Choose to invert colors: (Yes or No)");
        String choice = x.nextLine();
        while(!Objects.equals(choice, "Yes") && !Objects.equals(choice, "No")){
            System.out.println("Please try again.");
            choice = x.nextLine();
        }
        set_inverted = false;

        if (choice.equals("Yes")) {
            set_inverted = true;
        }

        frame = new JFrame("Boggle");
        frame.setVisible(true);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        if (set_inverted) {
            frame.getContentPane().setBackground(Color.black);
        }

        label = new JLabel("Welcome to Boggle!");
        label.setFont(new Font(font.getName(), Font.BOLD, 18));
        label.setBounds(10, 0, 300 ,30);
        if (set_inverted) {
            label.setForeground(Color.red);
        }
        startButton = new JButton("Start Game");
        startButton.setFont(new Font(font.getName(), Font.PLAIN, 12));
        startButton.setBounds(10, 100, 150, 30);
        if (set_inverted) {
            startButton.setBackground(Color.black);
            startButton.setForeground(Color.red);
            startButton.setOpaque(true);
            startButton.setBorderPainted(false);
        }
        startButton.addActionListener(this);


        frame.add(label);
        frame.add(startButton);
    }
    public static void main(String[] args){
        new GUI();
    }
    public void InstructionFrame(){
        label.setVisible(false);
        startButton.setVisible(false);
        frame.setVisible(false);
        frame.setVisible(true);
        label2 = new JLabel("Instructions");
        label2.setFont(new Font(font.getName(), Font.BOLD, 18));
        label2.setBounds(10, 10, 200, 30);
        t = new JTextArea("The Boggle board contains a grid of letters that are randomly placed.\n" +
                "We're both going to try to find words in this grid by joining the letters.\n" +
                "You can form a word by connecting adjoining letters on the grid.\n" +
                "Two letters adjoin if they are next to each other horizontally, \n" +
                "vertically, or diagonally. The words you find must be at least 4 letters long, \n" +
                "and you can't use a letter twice in any single word. Your points \n" +
                "will be based on word length: a 4-letter word is worth 1 point, 5-letter \n" +
                "words earn 2 points, and so on. To play, enter a word into the text box \n" +
                "and press the enter button. After you find as many word as you can, \n" +
                "press the finish button and I will find all the remaining words.");
        t.setFont(new Font(font.getName(), Font.PLAIN, 14));
        t.setBounds(10, 50, 565 ,200);
        nextButton = new JButton("Next");
        nextButton.setFont(new Font(font.getName(), Font.PLAIN, 12));
        nextButton.setBounds(10, 300, 150, 30);
        nextButton.addActionListener(this);
        frame.add(label2);
        frame.add(t);
        frame.add(nextButton);
        if (set_inverted) {
            label2.setForeground(Color.red);
            t.setBackground(Color.black);
            t.setForeground(Color.red);
            nextButton.setForeground(Color.red);
            nextButton.setBackground(Color.black);
            nextButton.setOpaque(true);
            nextButton.setBorderPainted(false);
        }
    }
    public void OptionFrame(){
        label2.setVisible(false);
        t.setVisible(false);
        nextButton.setVisible(false);
        frame.setVisible(false);
        frame.setVisible(true);
        t2 = new JTextArea("Press 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
        t2.setFont(new Font(font.getName(), Font.PLAIN, 14));
        t2.setBounds(10, 10, 500, 30);
        oneButton = new JButton("1");
        oneButton.setFont(new Font(font.getName(), Font.PLAIN, 16));
        oneButton.setBounds(10 , 50, 100, 60);
        oneButton.addActionListener(this);
        twoButton = new JButton("2");
        twoButton.setFont(new Font(font.getName(), Font.PLAIN, 16));
        twoButton.setBounds(70 , 50, 100, 60);
        twoButton.addActionListener(this);
        frame.add(t2);
        frame.add(oneButton);
        frame.add(twoButton);
        if (set_inverted) {
            label2.setForeground(Color.red);
            t2.setBackground(Color.black);
            t2.setForeground(Color.red);
            oneButton.setForeground(Color.red);
            oneButton.setBackground(Color.black);
            oneButton.setOpaque(true);
            oneButton.setBorderPainted(false);
            twoButton.setForeground(Color.red);
            twoButton.setBackground(Color.black);
            twoButton.setOpaque(true);
            twoButton.setBorderPainted(false);
        }
    }
    public void OptionFrame2(){
        t2.setVisible(false);
        oneButton.setVisible(false);
        twoButton.setVisible(false);
        frame.setVisible(false);
        frame.setVisible(true);
        t3 = new JTextArea("Press 1 to randomly assign letters to the grid; 2 to provide your own.");
        t3.setFont(new Font(font.getName(), Font.PLAIN, 14));
        t3.setBounds(10, 10, 500, 30);
        oneButton2 = new JButton("1");
        oneButton2.setFont(new Font(font.getName(), Font.PLAIN, 16));
        oneButton2.setBounds(10 , 50, 100, 60);
        oneButton2.addActionListener(this);
        twoButton2 = new JButton("2");
        twoButton2.setFont(new Font(font.getName(), Font.PLAIN, 16));
        twoButton2.setBounds(70 , 50, 100, 60);
        twoButton2.addActionListener(this);
        frame.add(t3);
        frame.add(oneButton2);
        frame.add(twoButton2);
        if (set_inverted) {
            t3.setBackground(Color.black);
            t3.setForeground(Color.red);
            oneButton2.setForeground(Color.red);
            oneButton2.setBackground(Color.black);
            oneButton2.setOpaque(true);
            oneButton2.setBorderPainted(false);
            twoButton2.setForeground(Color.red);
            twoButton2.setBackground(Color.black);
            twoButton2.setOpaque(true);
            twoButton2.setBorderPainted(false);
        }
    }
    public void OptionFrame3(){
        t3.setVisible(false);
        oneButton2.setVisible(false);
        twoButton2.setVisible(false);
        frame.setVisible(false);
        frame.setVisible(true);
        if(boardSize == 4){
            enterWords = new JLabel("Please enter 16 letters for your grid:");
        }
        else{
            enterWords = new JLabel("Please enter 25 letters for your grid:");
        }
        enterWords.setFont(new Font(font.getName(), Font.PLAIN, 14));
        enterWords.setBounds(10, 10, 300, 30);
        t6 = new JTextArea();
        t6.setFont(new Font(font.getName(), Font.PLAIN, 14));
        t6.setBounds(310, 10, 250, 25);
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font(font.getName(), Font.PLAIN, 14));
        confirmButton.setBounds(310, 40, 100, 30);
        confirmButton.addActionListener(this);
        frame.add(enterWords);
        frame.add(t6);
        frame.add(confirmButton);
        if (set_inverted) {
            enterWords.setForeground(Color.red);
            enterWords.setBackground(Color.black);
            t6.setBackground(Color.red);
            t6.setForeground(Color.black);
            confirmButton.setForeground(Color.red);
            confirmButton.setBackground(Color.black);
            confirmButton.setOpaque(true);
            confirmButton.setBorderPainted(false);
        }
    }
    public void BoardFrame(){
        t4 = new JTextArea(g.toString());
        t4.setFont(new Font(font.getName(), Font.PLAIN, 30));
        t4.setBounds(10, 10, 200,200);
        label3 = new JLabel("Please enter a word:");
        label3.setFont(new Font(font.getName(), Font.BOLD, 14));
        label3.setBounds(220, 10, 200, 30);
        t5 = new JTextArea();
        t5.setFont(new Font(font.getName(), Font.PLAIN, 14));
        t5.setBounds(220, 50, 200, 25);
        enterButton = new JButton("Enter");
        enterButton.setFont(new Font(font.getName(), Font.PLAIN, 14));
        enterButton.setBounds(220, 85, 80, 30);
        enterButton.addActionListener(this);
        finishButton = new JButton("Finish");
        finishButton.setFont(new Font(font.getName(), Font.PLAIN, 14));
        finishButton.setBounds(310, 85, 80, 30);
        finishButton.addActionListener(this);
        frame.add(t4);
        frame.add(label3);
        frame.add(t5);
        frame.add(enterButton);
        frame.add(finishButton);
        if (set_inverted) {
            label3.setForeground(Color.red);
            t4.setBackground(Color.black);
            t4.setForeground(Color.red);
            t5.setBackground(Color.red);
            t5.setForeground(Color.black);
            enterButton.setForeground(Color.red);
            enterButton.setBackground(Color.black);
            enterButton.setOpaque(true);
            enterButton.setBorderPainted(false);
            finishButton.setForeground(Color.red);
            finishButton.setBackground(Color.black);
            finishButton.setOpaque(true);
            finishButton.setBorderPainted(false);
        }
    }
    public void EndRoundFrame(){
        roundStats = new JLabel("Round Stats");
        roundStats.setFont(new Font(font.getName(), Font.BOLD, 18));
        roundStats.setBounds(10, 10, 200, 30);
        human = new JLabel("Human");
        human.setFont(new Font(font.getName(), Font.BOLD, 14));
        human.setBounds(175, 50, 100, 30);
        computer = new JLabel("Computer");
        computer.setFont(new Font(font.getName(), Font.BOLD, 14));
        computer.setBounds(325, 50, 100, 30);
        wordsFound = new JLabel("Words Found");
        wordsFound.setFont(new Font(font.getName(), Font.BOLD, 14));
        wordsFound.setBounds(10, 90, 150, 30);
        foundNum = new JLabel(String.valueOf(d.gameStats.getPlayerWords().size()) + "\t \t \t \t \t \t \t" +
                "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" +
                String.valueOf(w.size()-d.gameStats.getPlayerWords().size()));
        foundNum.setFont(new Font(font.getName(), Font.PLAIN, 14));
        foundNum.setBounds(180, 90, 300, 30);
        roundScore = new JLabel("Round Score");
        roundScore.setFont(new Font(font.getName(), Font.BOLD, 14));
        roundScore.setBounds(10, 130, 100, 30);
        scoreNum = new JLabel(String.valueOf(d.gameStats.getScore()) + "\t \t \t \t \t \t \t" +
                "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" +
                String.valueOf(d.gameStats.getcScore()));
        scoreNum.setFont(new Font(font.getName(), Font.PLAIN, 14));
        scoreNum.setBounds(180, 130, 300, 30);
        playAgain = new JLabel("Play Again?");
        playAgain.setFont(new Font(font.getName(), Font.BOLD, 14));
        playAgain.setBounds(10, 200, 100, 30);
        yesButton = new JButton("Yes");
        yesButton.setFont(new Font(font.getName(), Font.PLAIN, 14));
        yesButton.setBounds(110, 200, 80, 30);
        yesButton.addActionListener(this);
        noButton = new JButton("No");
        noButton.setFont(new Font(font.getName(), Font.PLAIN, 14));
        noButton.setBounds(200, 200, 80, 30);
        noButton.addActionListener(this);
        frame.add(roundStats);
        frame.add(human);
        frame.add(computer);
        frame.add(wordsFound);
        frame.add(foundNum);
        frame.add(roundScore);
        frame.add(scoreNum);
        frame.add(playAgain);
        frame.add(yesButton);
        frame.add(noButton);
        d.gameStats.endRound();
        if (set_inverted) {
            roundStats.setForeground(Color.red);
            human.setForeground(Color.red);
            computer.setForeground(Color.red);
            wordsFound.setForeground(Color.red);
            foundNum.setForeground(Color.red);
            roundScore.setForeground(Color.red);
            scoreNum.setForeground(Color.red);
            playAgain.setForeground(Color.red);
            yesButton.setForeground(Color.red);
            yesButton.setBackground(Color.black);
            yesButton.setOpaque(true);
            yesButton.setBorderPainted(false);
            noButton.setForeground(Color.red);
            noButton.setBackground(Color.black);
            noButton.setOpaque(true);
            noButton.setBorderPainted(false);
        }
    }
    public void EndGameFrame(){
        gameStats = new JLabel("Game Stats");
        gameStats.setFont(new Font(font.getName(), Font.BOLD, 18));
        gameStats.setBounds(10, 10, 200, 30);
        rounds = new JLabel("Rounds Played:" + "\t \t \t" + d.gameStats.getRound());
        rounds.setFont(new Font(font.getName(), Font.BOLD, 14));
        rounds.setBounds(10, 50, 200, 30);
        human = new JLabel("Human");
        human.setFont(new Font(font.getName(), Font.BOLD, 14));
        human.setBounds(175, 90, 100, 30);
        computer = new JLabel("Computer");
        computer.setFont(new Font(font.getName(), Font.BOLD, 14));
        computer.setBounds(325, 90, 100, 30);
        totalScore = new JLabel("Total Score");
        totalScore.setFont(new Font(font.getName(), Font.BOLD, 14));
        totalScore.setBounds(10, 130, 100, 30);
        scoreNum = new JLabel(String.valueOf(d.gameStats.getScoreTotal()) + "\t \t \t \t \t \t \t" +
                "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" +
                String.valueOf(d.gameStats.getcScoreTotal()));
        scoreNum.setFont(new Font(font.getName(), Font.PLAIN, 14));
        scoreNum.setBounds(180, 130, 300, 30);
        averageWords = new JLabel("Average Words");
        averageWords.setFont(new Font(font.getName(), Font.BOLD, 14));
        averageWords.setBounds(10, 170, 120, 30);
        foundNum = new JLabel(d.gameStats.getAverageWords() + "\t \t \t \t \t \t \t" +
                "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" +
                d.gameStats.getcAverageWords());
        foundNum.setFont(new Font(font.getName(), Font.PLAIN, 14));
        foundNum.setBounds(180, 170, 300, 30);
        thanks = new JLabel("Thanks for Playing!");
        thanks.setFont(new Font(font.getName(), Font.BOLD, 18));
        thanks.setBounds(10, 250, 200, 30);
        frame.add(gameStats);
        frame.add(rounds);
        frame.add(human);
        frame.add(computer);
        frame.add(totalScore);
        frame.add(scoreNum);
        frame.add(averageWords);
        frame.add(foundNum);
        frame.add(thanks);
        if (set_inverted) {
            gameStats.setForeground(Color.red);
            rounds.setForeground(Color.red);
            human.setForeground(Color.red);
            computer.setForeground(Color.red);
            totalScore.setForeground(Color.red);
            scoreNum.setForeground(Color.red);
            averageWords.setForeground(Color.red);
            foundNum.setForeground(Color.red);
            thanks.setForeground(Color.red);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            InstructionFrame();
        }
        if(e.getSource() == nextButton){
            OptionFrame();
        }
        if(e.getSource() == oneButton){ //button for 5x5 grid
            boardSize = 5;
            OptionFrame2();
        }
        else if(e.getSource() == twoButton){ //button for 4x4 grid
            boardSize = 4;
            OptionFrame2();
        }
        if(e.getSource() == oneButton2){ //button for randomly assigning letters to the grid
            BoggleGrid grid = new BoggleGrid(boardSize);
            grid.initalizeBoard(d.randomizeLetters(boardSize));
            Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
            d.findAllWords(allWords, boggleDict, grid);
            g = grid;
            w = allWords;
            t3.setVisible(false);
            oneButton2.setVisible(false);
            twoButton2.setVisible(false);
            frame.setVisible(false);
            frame.setVisible(true);
            BoardFrame();
        }
        else if(e.getSource() == twoButton2){ //button for providing own letters to grid
            OptionFrame3();
        }
        if(e.getSource() == confirmButton){
            String word = t6.getText().toUpperCase();
            if (!(word.length() == boardSize*boardSize)){
                t6.setText(null);
            }
            else{
                BoggleGrid grid = new BoggleGrid(boardSize);
                grid.initalizeBoard(word);
                Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
                d.findAllWords(allWords, boggleDict, grid);
                g = grid;
                w = allWords;
                enterWords.setVisible(false);
                t6.setVisible(false);
                confirmButton.setVisible(false);
                frame.setVisible(false);
                frame.setVisible(true);
                BoardFrame();
            }
        }
        if(e.getSource() == enterButton){
            String word = t5.getText().toUpperCase();
            if(word != "" && w.keySet().contains(word) && (!(d.gameStats.getPlayerWords().contains(word)))){
                d.gameStats.addWord(word, BoggleStats.Player.Human);
            }
            t5.setText(null);
        }
        else if(e.getSource() == finishButton){
            d.computerMove(w);
            t4.setVisible(false);
            label3.setVisible(false);
            t5.setVisible(false);
            enterButton.setVisible(false);
            finishButton.setVisible(false);
            frame.setVisible(false);
            frame.setVisible(true);
            EndRoundFrame();
        }
        if(e.getSource() == yesButton){
            roundStats.setVisible(false);
            human.setVisible(false);
            computer.setVisible(false);
            wordsFound.setVisible(false);
            foundNum.setVisible(false);
            roundScore.setVisible(false);
            scoreNum.setVisible(false);
            playAgain.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            frame.setVisible(false);
            frame.setVisible(true);
            OptionFrame();
        }
        else if(e.getSource() == noButton){
            roundStats.setVisible(false);
            human.setVisible(false);
            computer.setVisible(false);
            wordsFound.setVisible(false);
            foundNum.setVisible(false);
            roundScore.setVisible(false);
            scoreNum.setVisible(false);
            playAgain.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            frame.setVisible(false);
            frame.setVisible(true);
            EndGameFrame();
        }

    }
}
