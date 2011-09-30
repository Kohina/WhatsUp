package nu.placebo.whatsup;

import java.util.List;

import nu.placebo.whatsup.model.ExtendedOverlayItem;
import nu.placebo.whatsup.model.GeoLocation;
import nu.placebo.whatsup.model.Marker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * Activity that holds the map and control map actions. It's the "main" activity.
 * 
 * 
 * @author Ablim
 */
public class MapViewActivity extends MapActivity implements OnClickListener {

	private MapView mapView;
	private Marker marker;
	private List<Overlay> overlays;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		overlays = mapView.getOverlays();
		marker = new Marker(this.getResources().getDrawable(R.drawable.pin3));

		addMarker(new GeoLocation(0, 0, 0, "Första"));
		addMarker(new GeoLocation(1, 0, 10000000, "Andra"));
		Button annotationBtn = (Button) this.findViewById(R.id.showAnnotation);
		annotationBtn.setOnClickListener(this);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void addMarker(GeoLocation g) {
		marker.addOverlay(new ExtendedOverlayItem(g));
		overlays.add(marker);
	}

	public void onClick(View arg0) {
		Intent intent = new Intent(MapViewActivity.this, AnnotationActivity.class);
		this.startActivity(intent);	
	}
	
}
