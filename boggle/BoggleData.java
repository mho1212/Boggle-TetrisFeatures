package boggle;

import java.util.*;

public class BoggleData {
    public BoggleStats gameStats;
    /**
     * dice used to randomize letter assignments for a small grid
     */
    private final String[] dice_small_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    public BoggleData() {
        this.gameStats = new BoggleStats();
    }

    /*
     * This method should return a String of letters (length 16 or 25 depending on the size of the grid).
     * There will be one letter per grid position, and they will be organized left to right,
     * top to bottom. A strategy to make this string of letters is as follows:
     * -- Assign a one of the dice to each grid position (i.e. dice_big_grid or dice_small_grid)
     * -- "Shuffle" the positions of the dice to randomize the grid positions they are assigned to
     * -- Randomly select one of the letters on the given die at each grid position to determine
     *    the letter at the given position
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    public String randomizeLetters(int size){
        String letters = "";
        if(size == 4){ //for size of 4
            List<String> smallDice = Arrays.asList(dice_small_grid); //change dice array to list
            Collections.shuffle(smallDice); //shuffles the positions of the dice
            for(String s: smallDice){
                //finds a random integer between 0 and the max index of each string in the list
                //use the random integer to pick out a letter for each string in the list
                int random = (int)Math.round(Math.random()*(s.length()-1));
                letters += s.substring(random, random+1); //add each random letter to output
            }
        }
        else{ //for size of 5
            List<String> bigDice = Arrays.asList(dice_big_grid);
            Collections.shuffle(bigDice);
            for(String s: bigDice) {
                int random = (int) Math.round(Math.random() * (s.length() - 1));
                letters += s.substring(random, random + 1);
            }
        }
        return letters;
    }

    /*
     * This should be a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found should be entered into the allWords HashMap.  This HashMap
     * will be consulted as we play the game.
     *
     * Note that this function will be a recursive function.  You may want to write
     * a wrapper for your recursion. Note that every legal word on the Boggle grid will correspond to
     * a list of grid positions on the board, and that the Position class can be used to represent these
     * positions. The strategy you will likely want to use when you write your recursion is as follows:
     * -- At every Position on the grid:
     * ---- add the Position of that point to a list of stored positions
     * ---- if your list of stored positions is >= 4, add the corresponding word to the allWords Map
     * ---- recursively search for valid, adjacent grid Positions to add to your list of stored positions.
     * ---- Note that a valid Position to add to your list will be one that is either horizontal, diagonal, or
     *      vertically touching the current Position
     * ---- Note also that a valid Position to add to your list will be one that, in conjunction with those
     *      Positions that precede it, form a legal PREFIX to a word in the Dictionary (this is important!)
     * ---- Use the "isPrefix" method in the Dictionary class to help you out here!!
     * ---- Positions that already exist in your list of stored positions will also be invalid.
     * ---- You'll be finished when you have checked EVERY possible list of Positions on the board, to see
     *      if they can be used to form a valid word in the dictionary.
     * ---- Food for thought: If there are N Positions on the grid, how many possible lists of positions
     *      might we need to evaluate?
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    boolean[][] visited;
    public void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        String word = ""; //variable to store letter combinations made by boggleGrid
        ArrayList<Position> p = new ArrayList<Position>(); //stores position of each letter of the word
        //finds words using all positions on the boggleGrid as starting points
        for(int row = 0; row < boggleGrid.numRows(); row++){
            for(int col = 0; col < boggleGrid.numCols(); col++){
                //new visited array for every position on the boggleGrid, default = false
                visited = new boolean[boggleGrid.numRows()][boggleGrid.numCols()];
                search(row, col, word, p, allWords, boggleGrid, boggleDict);
            }
        }
    }
    //recursive search method
    private void search(int row, int col, String word, ArrayList<Position> p, Map<String,ArrayList<Position>> allWords,
                        BoggleGrid boggleGrid, Dictionary boggleDict){
        Position newP = new Position(row, col); //stores current position after every recursive call
        visited[row][col] = true; //sets current position to true
        p.add(newP); //adds current position to a position array
        word += boggleGrid.getCharAt(row,col); //adds a letter to the word
        //updates Map allWords if word length >= 4 and the boggleDict contains the word
        if(word.length() >= 4 && boggleDict.containsWord(word)){
            allWords.put(word,p);
        }
        if(boggleDict.isPrefix(word)){
            //recursively traverses through adjacent positions of the boggleGrid if they are in bounds and not visited
            for(int r = row-1; r <= row+1; r++){
                for(int c = col-1; c <= col+1; c++){
                    if(r >= 0 && r < boggleGrid.numRows() && c >= 0 && c < boggleGrid.numCols() && !(visited[r][c])) {
                        search(r, c, word, p, allWords, boggleGrid, boggleDict);
                    }
                }
            }
            visited[row][col] = false; //sets visited to false after finishing traversing that path
            p.remove(newP); //removes current position from map as it finished traversing that path
        }
        visited[row][col] = false; //sets visited to false after finishing traversing that path
        p.remove(newP); //removes current position from map as it finished traversing that path
    }

    /*
     * Gets words from the computer.  The computer should find words that are
     * both valid and not in the player's word list.  For each word that the computer
     * finds, update the computer's word list and increment the
     * computer's score (stored in boggleStats).
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    public void computerMove(Map<String,ArrayList<Position>> all_words){
        for(String s: all_words.keySet()){
            if(!this.gameStats.getPlayerWords().contains(s)){
                this.gameStats.addWord(s, BoggleStats.Player.Computer);
            }
        }
    }
}


