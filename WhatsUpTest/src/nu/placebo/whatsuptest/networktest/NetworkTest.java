package nu.placebo.whatsuptest.networktest;

import nu.placebo.whatsup.model.Annotation;
import nu.placebo.whatsup.model.SessionInfo;
import nu.placebo.whatsup.network.AnnotationRetrieve;
import nu.placebo.whatsup.network.Login;
import nu.placebo.whatsup.network.NetworkOperationListener;
import nu.placebo.whatsup.network.OperationResult;
import android.test.AndroidTestCase;

public class NetworkTest extends AndroidTestCase {

	public NetworkTest(){
		super();
	}
	
	public void setUp() throws Exception{
		
		super.setUp();
	}
	
	public void testAnnotationRetrieve(){
		AnnotationRetrieve a = new AnnotationRetrieve(1234);
		assertTrue(true);
		a.addOperationListener(new NetworkOperationListener<Annotation>() {

			@Override
			public void operationExcecuted(OperationResult<Annotation> result) {
				assertFalse("Errors when loading annotation", result.hasErrors());
				assertNotNull("Annotation reurned was null", result.getResult());
				assertEquals("Incorrect nid on the Annotation returned.", 123, result.getResult().getId());
			}
			
		});
		OperationResult<Annotation> result = a.execute();
		a.notifyListeners(result);
	}
	
	public void testLogIn() {
		Login l = new Login("test", "WhatsUp!");
		l.addOperationListener(new NetworkOperationListener<SessionInfo>() {

			@Override
			public void operationExcecuted(OperationResult<SessionInfo> result) {
				assertFalse("Errors when loggin in", result.hasErrors());
				assertNotNull("SessionInfo reutrned was null",
						result.getResult());
			}

		});
		OperationResult<SessionInfo> result = l.execute();
		l.notifyListeners(result);
	}
}
