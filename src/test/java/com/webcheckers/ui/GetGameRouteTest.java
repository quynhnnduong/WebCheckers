package com.webcheckers.ui;


import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.ReplayLoader;
import com.webcheckers.model.ReplaySaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test suite for {@link GetGameRoute} component
 * @author Joel Clyne, Quynh Duong
 */
@Tag("UI-tier")
public class GetGameRouteTest {
    // Component under test (CuT)
    private GetGameRoute CuT;

    // Friendlies (already tested, no bugs will come from this)
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Game game;
    private ReplaySaver replaySaver;
    private ReplayLoader replayLoader;

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;


    private Player player1;
    private Player player2;

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
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        replaySaver = new ReplaySaver();
        replayLoader = new ReplayLoader(replaySaver);

        player1 = mock(Player.class);
        player2 = mock(Player.class);


        // create a unique CuT for each test
        CuT = new GetGameRoute(engine, gameCenter, playerLobby, replaySaver);
    }
    /**
     * Test that the Game view will create a new game when a player initiates a game
     */
    @Test
    public void newGame(){
        game = new Game("1234", player1, player2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.joinGame(true);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player2.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(game.getId());
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(player2);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(playerLobby.getPlayer(request.queryParams("opponent"))).thenReturn(player2);
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertFalse(player1.inGame());
        CuT.handle(request, response);

    }

    @Test
    public void current_player_not_in_game(){
        game = new Game("1234", player1, player2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.joinGame(false);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player2.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(game.getId());
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(player2);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(playerLobby.getPlayer(request.queryParams("opponent"))).thenReturn(player2);
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertFalse(player1.inGame());
        assertFalse(player1.isCalledForGame());
        CuT.handle(request, response);
    }

    @Test //null pointer exception
    public void current_player_in_game(){
        String gameID = CuT.makeGameID(player2, player1);
        game = new Game(gameID, player1, player2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.joinGame(false);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player1.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(player1.inGame()).thenReturn(true);

        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(gameID);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(player2);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(player1.isCalledForGame()).thenReturn(true);
        when(playerLobby.getPlayer(request.queryParams("opponent"))).thenReturn(player2);
        when(gameCenter.getGame(gameID)).thenReturn(game);

        assertTrue(player1.inGame());
        assertTrue(player1.isCalledForGame());
        assertThrows(HaltException.class, () -> CuT.handle(request, response));


    }

    @Test
    public void current_player_is_called_from_Player_object(){
        game = new Game("1234", player1, player2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.joinGame(true);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player1.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn("1234");
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(player2);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(player1.getOpponent()).thenReturn(player2);
        when(player1.inGame()).thenReturn(true);
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertTrue(player1.inGame());
        CuT.handle(request, response);


    }

    @Test
    public void opponent_match_current_player(){
        game = new Game("1234", player1, player2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.joinGame(false);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player2.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(game.getId());
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(false);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(playerLobby.getPlayer(request.queryParams("opponent"))).thenReturn(player2);
        when(player2.getOpponent()).thenReturn(player1);
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertFalse(player1.inGame());
        assertFalse(player1.isCalledForGame());
        CuT.handle(request, response);
    }
    @Test
    public void opponent_does_not_match_current_player(){
        game = new Game("1234", player1, player2);
        player2.setOpponent(new Player("randomPlayer"));
        player1.joinGame(false);
        gameCenter.addGame(game);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(game.getId());
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(false);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(player2.getOpponent()).thenReturn(new Player("randomPlayer"));
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertThrows(HaltException.class, () -> CuT.handle(request, response));
    }



    @Test
    public void opponent_is_null(){
        game = new Game("1234", player1, player2);
        player1.joinGame(false);
        playerLobby.setOpponentMatch(player1, player2);
        gameCenter.addGame(game);
        player2.call();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(game.getId());
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(session.attribute(UIProtocol.LEGIT_OPPONENT_ATTR)).thenReturn(null);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        when(player1.getOpponent()).thenReturn(null);
        when(gameCenter.getGame("1234")).thenReturn(game);

        assertThrows(HaltException.class, () -> CuT.handle(request, response));
    }







}
