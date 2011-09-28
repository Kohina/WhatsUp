package nu.placebo.whatsup.network;

import java.util.ArrayList;
import java.util.List;

import nu.placebo.whatsup.model.GeoLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoLocationsRetrieve implements
		ListenableNetworkOperation<List<GeoLocation>> {

	private List<NetworkOperationListener<List<GeoLocation>>> listeners = new ArrayList<NetworkOperationListener<List<GeoLocation>>>();
	private double latitudeA;
	private double longitudeA;
	private double latitudeB;
	private double longitudeB;

	public GeoLocationsRetrieve(double latitudeA, double longitudeA,
			double latitudeB, double longitudeB) {
		this.latitudeA = latitudeA;
		this.longitudeA = longitudeA;
		this.latitudeB = latitudeB;
		this.longitudeB = longitudeB;
	}

	public void execute() {
		String result = NetworkCalls.retrieveAnnotationMarkers(latitudeA,
				longitudeA, latitudeB, longitudeB);
		// Todo: Parse the result into a list of GeoLocations
		List<GeoLocation> geoLocations = new ArrayList<GeoLocation>();
		JSONArray json = null;
		try {
			json = new JSONArray(result);
			for (int i = 0; i < json.length(); i++) {
				JSONObject j = json.getJSONObject(i);
				geoLocations.add(new GeoLocation(j.getInt("nid"), j
						.getDouble("latitude"), j.getDouble("longitude"), j
						.getString("title")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (NetworkOperationListener<List<GeoLocation>> listener : this.listeners) {
			listener.operationExcecuted(geoLocations);
		}
	}

	public void addOperationListener(
			NetworkOperationListener<List<GeoLocation>> listener) {
		listeners.add(listener);
	}
}