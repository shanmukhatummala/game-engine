package game.mapfile.reader;

import game.map.Map;

/**
 * The FileReaderAdapter class adapts the ConquestFileReader to the MapFileReader interface.
 * It allows reading Conquest map files using the readFile method defined in MapFileReader.
 */
public class FileReaderAdapter extends MapFileReader {

    private final ConquestFileReader d_conquestFileReader;

    /**
     * Constructs a FileReaderAdapter with the specified ConquestFileReader.
     *
     * @param p_conquestFileReader The ConquestFileReader to be adapted.
     */
    public FileReaderAdapter(ConquestFileReader p_conquestFileReader) {
        this.d_conquestFileReader = p_conquestFileReader;
    }

    /**
     * Reads a Conquest map file and populates the provided Map object.
     *
     * @param p_path The filepath of the Conquest map file.
     * @param p_map The Map object to be populated.
     */
    @Override
    public void readFile(String p_path, Map p_map) {
        d_conquestFileReader.readConquestFile(p_path, p_map);
    }
}
