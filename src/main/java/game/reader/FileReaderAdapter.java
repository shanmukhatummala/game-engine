package game.reader;

import game.map.Map;

public class FileReaderAdapter extends MapFileReader {

    private final ConquestFileReader d_conquestFileReader;

    public FileReaderAdapter(ConquestFileReader p_conquestFileReader) {
        this.d_conquestFileReader = p_conquestFileReader;
    }

    @Override
    public void readFile(String p_path, Map p_map) {
        d_conquestFileReader.readConquestFile(p_path, p_map);
    }
}
