package nu.placebo.whatsup.network;

import java.io.IOException;
import java.util.List;

import nu.placebo.whatsup.model.SessionInfo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import android.net.http.AndroidHttpClient;

/**
 * 
 * Static methods for making calls to the server.
 * 
 */

public class NetworkCalls {

	private static HttpClient client = getHttpClient();
	private static boolean testing;

	public static HttpResponse performGetRequest(String query) {
		HttpGet request = new HttpGet(query);
		HttpResponse response = null;
		try {
			response = execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpClient getHttpClient() {
		return AndroidHttpClient.newInstance("");
	}

	public static HttpResponse performPostRequest(String query,
			List<NameValuePair> body, SessionInfo sessionInfo) {
		HttpPost request = new HttpPost(query);

		HttpResponse response = null;
		try {
			if (body != null) {
				AbstractHttpEntity ent = new UrlEncodedFormEntity(body,
						HTTP.UTF_8);
				request.setEntity(ent);
			}
			if (sessionInfo != null) {
				request.addHeader(new BasicHeader("Cookie", sessionInfo
						.getSessionName() + "=" + sessionInfo.getSessionId()));
			}
			response = execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpResponse execute(HttpUriRequest request)
			throws ClientProtocolException, IOException {
		if(!testing) {
			return client.execute(request);
		} else {
			return getHttpClient().execute(request);
		}
	}
	public static void setTesting(boolean b) {
		testing = b;
	}
}
