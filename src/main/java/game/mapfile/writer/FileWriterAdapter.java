package game.mapfile.writer;

import game.map.Map;

/** Adapter used to implement Adapter pattern for writing to the map files */
public class FileWriterAdapter extends MapFileWriter {

    private final ConquestFileWriter d_conquestFileWriter;

    /**
     * Constructor for FileWriterAdapter
     *
     * @param p_conquestFileWriter conquest file writer object for which adapter is needed
     */
    public FileWriterAdapter(ConquestFileWriter p_conquestFileWriter) {
        this.d_conquestFileWriter = p_conquestFileWriter;
    }

    /**
     * calls the writeMapFile method of the ConquestFileWriter
     *
     * @param p_path path of the map file
     * @param p_map map object
     */
    @Override
    public void writeMapFile(String p_path, Map p_map) {
        d_conquestFileWriter.writeConquestFile(p_path, p_map);
    }
}
