package nu.placebo.whatsup.model;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nu.placebo.whatsup.network.AnnotationRetrieve;
import nu.placebo.whatsup.network.GeoLocationsRetrieve;
import nu.placebo.whatsup.network.NetworkOperationListener;
import nu.placebo.whatsup.network.NetworkQueue;

public class DataProvider {

	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		//-------------------- Constants -----------------
		private static final String DATABASE_NAME = "whatsup.db";
		private static final int DATABASE_VERSION = 2;
		private static final String GEOLOCATION_TABLE = "geolocations";
		private static final String ANNOTATION_TABLE = "anntations";
		private static final String COMMENT_TABLE = "comments";
		private static final String REFERENCE_POINT_TABLE = "reference_points";
		//------------------------------------------------

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + GEOLOCATION_TABLE + " ("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "nid INTEGER,"
					+ "latitude REAL,"
					+ "longitude REAL,"
					+ "title TEXT,"
					+ "vote INTEGER"
					+ ");");
			db.execSQL("CREATE TABLE " + ANNOTATION_TABLE + " ("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "nid INTEGER,"
					+ "body TEXT,"
					+ "author TEXT"
					+ ");");
			db.execSQL("CREATE TABLE " + COMMENT_TABLE + " ("
					+ "comment TEXT");
			db.execSQL("CREATE TABLE " + REFERENCE_POINT_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + GEOLOCATION_TABLE);
			onCreate(db);
		}
		
		public List<GeoLocation> retrieveAnnotationMarkers(double latitudeA,
			double longitudeA, double latitudeB, double longitudeB) {
			/* UNDER CONSTRUCTION
			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.query(ANNOTATION_MARKER_TABLE, null, "", selectionArgs, groupBy, having, orderBy);
			*/
			return null;
		}
		
		public Annotation retrieveAnnotation(int nid) {
			/* UNDER CONSTRUCTION
			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.query(ANNOTATION_MARKER_TABLE, null, "nid = " + nid, null, null, null, null);
			c.
			*/
			return null;
		}
	}
	
	/**
	 * Class is Singelton, disallow creation
	 */
	private DataProvider() {}

	private static final DataProvider instance = new DataProvider();

	private DatabaseHelper dbHelper = new DatabaseHelper(null);
	private NetworkQueue networkQueue = NetworkQueue.getInstance();

	public static DataProvider getDataProvider() {
		return instance;
	}
	
	public DataReturn<Annotation> getAnnotation(int nid) {
		//Calling local cache first, then putting a network call in the queue, returning an object containing the content of the local
		//cache, and means of acquiring the data fetched from remote server
		
		//dbHelper.getReadableDatabase().query(
				//DatabaseHelper.ANNOTATION_TABLE, 
				//columns, 
				//selection, 
				//selectionArgs, 
				//groupBy, 
				//having, 
				//orderBy);
		
		
		return null;
	}
	
	public DataReturn<List<GeoLocation>> getAnnotationMarkers(double latitudeA,
			double longitudeA, double latitudeB, double longitudeB) {
		return null;
	}

	public void retrieveAnnotation(int nid) {
		// Fetches an annotation from the server and stores it in the local
		// database.
		AnnotationRetrieve ar = new AnnotationRetrieve(nid);
		ar.addOperationListener(new NetworkOperationListener<Annotation>() {

			public void operationExcecuted(Annotation result) {
				// TODO Store in local db
				// TODO Notify those interested in the result (binder).				
			}
			
		});
		this.networkQueue.add(ar);

	}

	public void retrieveGeoLocations(double latitudeA, double longitudeA,
			double latitudeB, double longitudeB) {
		// Fetches GeoLocations from the server and stores them in the local
		// database.
		GeoLocationsRetrieve gr = new GeoLocationsRetrieve(latitudeA,
				longitudeA, latitudeB, longitudeB);
		gr.addOperationListener(new NetworkOperationListener<List<GeoLocation>>() {

			public void operationExcecuted(List<GeoLocation> result) {
				// TODO Store in local db
				// TODO Notify those interested in the result (binder).
			}

		});
		this.networkQueue.add(gr);
	}
}