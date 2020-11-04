package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSignOutRouteTest {

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player currentUser;
    private GameCenter gameCenter;
    private PostSignOutRoute CuT;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        currentUser = mock(Player.class);

        //Component under test
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void testLoadSignOutPage(){

        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(request.queryParams("sign_out")).thenReturn("not true or false");


        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Sign Out");
        testHelper.assertViewModelAttribute("message", Message.info("Are you sure you want to sign out?"));

    }
}
