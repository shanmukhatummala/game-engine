package game.states;

public interface Phase {

    public void loadMapHandler();
    public void gamePlayerHandler();
    public void editMapHandler();
    public void showMapHandler();
    public void assignCountriesHandler();
}
