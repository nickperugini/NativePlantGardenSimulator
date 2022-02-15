package mainPkg;

import java.util.Comparator;

public class GardenCompare implements Comparator<Plant>, java.io.Serializable{

	@Override
	public int compare(Plant p1, Plant p2) {
		int res = (p1.getSciName().compareTo(p2.getSciName()));
		return res;
	}
}
