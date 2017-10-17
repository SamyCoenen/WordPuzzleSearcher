import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Samy Coenen
 *
 */
public class WordPuzzle {

    private char[][] wordPuzzle;
    private ArrayList<Integer[]> lettersLocationList;

    /**
     *
     * @param letters
     * @param row
     */
    public WordPuzzle(String letters, int row){
        int lettersInRow = letters.length()/row;
        wordPuzzle = new char[row][lettersInRow];

        for(int i=0; i < row;i+=1){
            int beginOfRow = i*lettersInRow;
            int endOfRow = i*lettersInRow+lettersInRow;
            wordPuzzle[i] = letters.substring(beginOfRow,endOfRow).toCharArray();
            System.out.println(Arrays.toString(wordPuzzle[i]));
        }
    }

    /**
     *
     * @return letters in 2d char array
     */
    public char[][] getLetterArray(){
        return wordPuzzle;
    }

    /**
     *
     * Find first letter in word puzzle and second letter next to it and compare further
     *
     * @param word
     * @return
     */
    public boolean searchWord(String word){
        for(Integer[] firstLetterLocation : getLetterLocations(word.charAt(0))) {
            for (Integer[] secondLetterLocation : getSurroundingLocations(firstLetterLocation ,word.charAt(1))) {
                try {
                    lettersLocationList = new ArrayList<>();
                    lettersLocationList.add(firstLetterLocation);
                    if (searchWord(word.substring(1), firstLetterLocation, direction(firstLetterLocation, secondLetterLocation)))
                        return true;
                } catch (IndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     *
     * Recursive function from 2nd letter and up
     *
     * @param word
     * @param firstLetterLocation
     * @param direction
     * @return boolean true when word is found.
     */
    public boolean searchWord(String word,Integer[] firstLetterLocation, Integer[] direction){
        Integer[] nextLetterLocation = nextLocation(firstLetterLocation,direction);
        if (word.length() == 0){
            System.out.println("Word found in " + Direction.valueOf(direction) + " direction!");
            lettersLocationList.forEach(location -> System.out.println(Arrays.toString(location)));
            return true;
        } else {
            lettersLocationList.add(nextLetterLocation);
            boolean found = false;
            if (nextLetterLocation != null){
                //System.out.println("next letter "+wordPuzzle[nextLetterLocation[0]][nextLetterLocation[1]]+"="+firstLetterLocation[0]+","+firstLetterLocation[1] +" next letter "+word.charAt(0)+" is at " + nextLetterLocation[0] + "," + nextLetterLocation[1] + " because direction is "+ Direction.valueOf(direction));
                if (wordPuzzle[nextLetterLocation[0]][nextLetterLocation[1]] == word.charAt(0))
                    found = searchWord(word.substring(1), nextLetterLocation , direction);
            }
            return found;
        }
    }

    /**
     *
     * Checks if x and y distance isn't larger than 1 space away.
     *
     * @param firstLetterLocation
     * @param secondLetterLocation
     * @return boolean if second letter is next to first letter.
     */
    public boolean isBeside(Integer[] firstLetterLocation, Integer[] secondLetterLocation){
        boolean isBesideXLocation = (secondLetterLocation[0] - firstLetterLocation[0] >= -1 && secondLetterLocation[0] - firstLetterLocation[0] <= 1);
        boolean isBesideYLocation = (secondLetterLocation[1] - firstLetterLocation[1] >= -1 && secondLetterLocation[1] - firstLetterLocation[1] <= 1);
        return isBesideXLocation && isBesideYLocation;
    }

    /**
     *
     * @param firstLetterLocation
     * @param secondLetterLocation
     * @return the difference between the 2 locations and thus the orientation or direction
     */
    public Integer[] direction(Integer[] firstLetterLocation, Integer[] secondLetterLocation){
        return new Integer[]{secondLetterLocation[0] - firstLetterLocation[0], secondLetterLocation[1] - firstLetterLocation[1] };
    }

    /**
     * Retrieve location of next letter that is in the same direction.
     *
     * @param letterLocation
     * @param direction
     * @return Integer[], null if nothing is found
     */
    public Integer[] nextLocation(Integer[] letterLocation, Integer[] direction){
        Integer[] nextLocation = new Integer[]{letterLocation[0] + direction[0], letterLocation[1] + direction[1]};
        if ((nextLocation[0] >= 0 && nextLocation[0] <= wordPuzzle.length - 1) && (nextLocation [1] >= 0 && nextLocation[1] <= wordPuzzle[0].length - 1))
            return nextLocation;
        else
            return null;
    }

    /**
     *
     * @param letterLocation
     * @param letter
     * @return
     */
    public ArrayList<Integer[]> getSurroundingLocations(Integer[] letterLocation, char letter){
        return getLetterLocations(letter).stream().filter(loc -> isBeside(letterLocation, loc)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param letter
     * @return all locations for a letter
     */
    public ArrayList<Integer[]> getLetterLocations(char letter){
        ArrayList<Integer[]> locationList = new ArrayList<>();
        for(int i=0;i<wordPuzzle.length;i++){
            for(int j=0;j<wordPuzzle[i].length;j++){
                if (wordPuzzle[i][j] == letter)
                    locationList.add(new Integer[]{i,j});
            }
        }
        return locationList;
    }

    public static void main(String[] args) {
        WordPuzzle wordPuzzle = new WordPuzzle("VNYBKGSRORANGEETRNXWPLAEALKAPMHNWMRPOCAXBGATNOMEL",7);
        System.out.println(wordPuzzle.searchWord("BANANA"));
    }

}
enum Direction {
    UP(new Integer[]{-1,0}), LEFT(new Integer[]{0,-1}), DOWN(new Integer[]{1,0}), RIGHT(new Integer[]{0,1}),
    UP_RIGHT(new Integer[]{-1,1}), UP_LEFT(new Integer[]{-1,-1}), DOWN_RIGHT(new Integer[]{1,1}), DOWN_LEFT(new Integer[]{1,-1});

    private Integer[] directionValue;
    Direction(final Integer[] direction){
        directionValue = direction;
    }

    public static Direction valueOf(Integer[] direction){
        for (Direction directionEnum: Direction.values()){

            if (Arrays.equals(directionEnum.directionValue,direction)){
                return directionEnum;
            }
        }
        return null;
    }
}

