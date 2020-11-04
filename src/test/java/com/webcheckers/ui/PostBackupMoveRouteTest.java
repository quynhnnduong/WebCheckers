package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test suite for {@link PostBackupMove} component
 * @author Sasha Persaud
 */
@Tag("UI-tier")
public class PostBackupMoveRouteTest {
    private PostBackupMove CuT;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Game game;
    private Turn turn;
    private Gson gson;

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
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        turn = mock(Turn.class);

        CuT = new PostBackupMove(gameCenter);
        gson = new Gson();
    }

    @Test
    public void testInvalidBackupMove() {
        // Set up
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn("gameID");
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getCurrentTurn()).thenReturn(turn);
        when(turn.hasMoves()).thenReturn(false);

        // Invoke
        Object result = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("No moves made this turn. Cannot backup")), result);
    }
}
