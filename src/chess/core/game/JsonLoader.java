package chess.core.game;

import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.util.ArrayList;


public class JsonLoader {

    private ObjectMapper mapper = new ObjectMapper();
    private File loadFile;

    //stores the number of objects that are needed to save the game as a JSON

    private ArrayList<String> jsonArray;



    /**
     * default constructor
     */
    public JsonLoader() {
        loadFile = null;
        jsonArray = new ArrayList<>();
    }



    /**
     * convertToJson
     *
     * @param jsonArray the array that has all the json strings
     */
    public File convertToJson(ArrayList<String> jsonArray) {
        //open new file for write
        File file = new File("Desktop/cs205ChessGame.json");

        for (String s : jsonArray) {
            //add to file
        }
        //close file
        return file;
    }

    /**
     * save
     *
     * @return the file to that is saved
     */
    public File save() {

        //save each object as a string

        //store each string in json array

        return convertToJson(jsonArray);
    }

    /**
     * load
     *
     * @param file the file to load from
     */
    public void load(File file) {
        //write file to json string array

        //for each json string make a new object with the given fields

        //create a new game with the new objects

    }
}
