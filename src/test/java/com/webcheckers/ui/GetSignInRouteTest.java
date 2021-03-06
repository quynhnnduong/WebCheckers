package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.UIProtocol.LEGIT_NAME_ATTR;
import static com.webcheckers.ui.WebServer.SIGNIN_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test suite for {@link GetSignInRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    private GetSignInRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Response response;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetSignInRoute(engine);
    }

    /**
     * tests to make sure there are no errors with a valid home page
     */
    @Test
    public void signedInView(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(LEGIT_NAME_ATTR)).thenReturn(true);

        // Invoke the test (ignore the output)
        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            System.out.println("Route returned an exception during execution");
        }

        // Analyze the content passed into the render method
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(UIProtocol.LEGIT_NAME_ATTR, true);
        testHelper.assertViewName("signIn.ftl");
    }

    /**
     * Test when player is not signedIn yet
     */
    @Test
    public void notSignedIn() {
        // Invoke the test (ignore the output)
        try {
            CuT.handle( request, response );
        } catch (Exception e) {
            System.out.println("Route returned an exception during execution");
        }
        //   * verify the session
        verify( response, never() ).redirect( any() );
    }

}
