package game.map;

import java.util.List;

import game.pojo.Country;

public class MapValidator {
	
	private List<Country> countryList;
	private Map mapToValidate;
	
	public MapValidator(Map mapToValidate) {
		this.mapToValidate = mapToValidate;
		countryList = mapToValidate.getCountries();
	}
	

}
