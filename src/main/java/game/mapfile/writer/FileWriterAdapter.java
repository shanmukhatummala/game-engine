package game.mapfile.writer;

import game.map.Map;

public class FileWriterAdapter extends MapFileWriter {

    private final ConquestFileWriter d_conquestFileWriter;

    public FileWriterAdapter(ConquestFileWriter p_conquestFileWriter) {
        this.d_conquestFileWriter = p_conquestFileWriter;
    }

    @Override
    public void writeMapFile(String p_path, Map p_map) {
        d_conquestFileWriter.writeConquestFile(p_path, p_map);
    }
}
