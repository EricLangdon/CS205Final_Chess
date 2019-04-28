package chess.core.game;

import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class JsonLoader {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * save
     *
     * @return the file to that is saved
     */
    public void save(ChessJson o, File f) {
        try {
            String chessString = mapper.writeValueAsString(o);

            FileWriter fw = new FileWriter(f);
            fw.write(chessString);
            fw.close();

        } catch (Exception e) {}
    }

    /**
     * load
     *
     * @param file the file to load from
     */
    public ChessJson load(File file) {
        ChessJson chessObj = new ChessJson();
        try {
            chessObj = mapper.readValue(file, ChessJson.class );
        } catch (Exception e) {}
        return chessObj;
    }
}
