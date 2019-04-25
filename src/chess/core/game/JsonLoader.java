package chess.core.game;

import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class JsonLoader {

    private ObjectMapper mapper = new ObjectMapper();
    private ChessJson chessObj = new ChessJson();

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
    public void load(File file) {

    }
}
