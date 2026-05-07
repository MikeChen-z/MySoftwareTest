package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeast;

/**
 * Unit tests for the {@link MapParser} class.
 */
class MapParserTest {

    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private Board board;
    private Square groundSquare;
    private Square wallSquare;
    private Ghost ghost;
    private Pellet pellet;
    private MapParser mapParser;

    @BeforeEach
    void setUp() {
        levelFactory = mock(LevelFactory.class);
        boardFactory = mock(BoardFactory.class);
        board = mock(Board.class);
        groundSquare = mock(Square.class);
        wallSquare = mock(Square.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);

        mapParser = new MapParser(levelFactory, boardFactory);

        when(boardFactory.createGround()).thenReturn(groundSquare);
        when(boardFactory.createWall()).thenReturn(wallSquare);
        when(boardFactory.createBoard(any(Square[][].class))).thenReturn(board);
        when(levelFactory.createGhost()).thenReturn(ghost);
        when(levelFactory.createPellet()).thenReturn(pellet);
        when(levelFactory.createLevel(eq(board), anyList(), anyList())).thenReturn(mock(Level.class));
    }

    @Test
    void parseValidMap() {
        List<String> map = Arrays.asList(
                "####",
                "#P.#",
                "# G#",
                "####");

        mapParser.parseMap(map);

        verify(boardFactory, times(4)).createGround();
        verify(boardFactory, times(12)).createWall();
        verify(boardFactory).createBoard(any(Square[][].class));
        verify(levelFactory).createGhost();
        verify(levelFactory).createPellet();
        verify(levelFactory).createLevel(eq(board), anyList(), anyList());
    }

    @Test
    void parseEmptySpace() {
        List<String> map = Collections.singletonList(" ");

        mapParser.parseMap(map);

        verify(boardFactory).createGround();
        verify(boardFactory).createBoard(any(Square[][].class));
        verify(levelFactory, never()).createGhost();
        verify(levelFactory, never()).createPellet();
    }

    @Test
    void parseNullInput() {
        assertThatThrownBy(() -> mapParser.parseMap((List<String>) null))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text cannot be null");
    }

    @Test
    void parseEmptyList() {
        List<String> emptyList = Collections.emptyList();

        assertThatThrownBy(() -> mapParser.parseMap(emptyList))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text must consist of at least 1 row");
    }

    @Test
    void parseEmptyLine() {
        List<String> map = Collections.singletonList("");

        assertThatThrownBy(() -> mapParser.parseMap(map))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text lines cannot be empty");
    }

    @Test
    void parseUnequalWidthLines() {
        List<String> map = Arrays.asList(
                "###",
                "##",
                "###");

        assertThatThrownBy(() -> mapParser.parseMap(map))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text lines are not of equal width");
    }

    @Test
    void parseUnrecognizedCharacter() {
        List<String> map = Collections.singletonList("X");

        assertThatThrownBy(() -> mapParser.parseMap(map))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Invalid character");
    }

    @Test
    void parseInputStream() throws IOException {
        String mapContent = "###\n#P#\n###";
        InputStream inputStream = new ByteArrayInputStream(mapContent.getBytes());

        mapParser.parseMap(inputStream);

        verify(boardFactory).createBoard(any(Square[][].class));
        verify(levelFactory).createLevel(eq(board), anyList(), anyList());
    }

    @Test
    void parseNonExistentResource() {
        assertThatThrownBy(() -> mapParser.parseMap("/nonexistent.txt"))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Could not get resource");
    }

    @Test
    void parseEmptyInputStream() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);

        assertThatThrownBy(() -> mapParser.parseMap(inputStream))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text must consist of at least 1 row");
    }

    @Test
    void parseInputStreamWithUnequalWidth() throws IOException {
        String mapContent = "###\n##\n###";
        InputStream inputStream = new ByteArrayInputStream(mapContent.getBytes());

        assertThatThrownBy(() -> mapParser.parseMap(inputStream))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessageContaining("Input text lines are not of equal width");
    }

    @Test
    void parseMapWithAllSupportedCharacters() {
        List<String> map = Arrays.asList(
                "####",
                "#P.G",
                "#. G",
                "####");

        mapParser.parseMap(map);

        verify(boardFactory, times(10)).createWall();
        verify(boardFactory, times(6)).createGround();
        verify(levelFactory, times(2)).createGhost();
        verify(levelFactory, times(2)).createPellet();
    }

    @Test
    void ghostIsAddedToListAndOccupiesSquare() {
        List<String> map = Collections.singletonList("G");

        mapParser.parseMap(map);

        verify(ghost).occupy(groundSquare);
    }

    @Test
    void pelletOccupiesSquare() {
        List<String> map = Collections.singletonList(".");

        mapParser.parseMap(map);

        verify(pellet).occupy(groundSquare);
    }

    @Test
    void playerStartPositionIsRecorded() {
        List<String> map = Collections.singletonList("P");

        mapParser.parseMap(map);

        verify(levelFactory).createLevel(eq(board), anyList(), argThat(positions -> positions.size() == 1));
    }

    @Test
    void parseExistingResourceFile() throws IOException {
        mapParser.parseMap("/simplemap.txt");

        verify(boardFactory).createBoard(any(Square[][].class));
        verify(levelFactory).createLevel(eq(board), anyList(), anyList());
    }

    @Test
    void getBoardCreatorReturnsBoardFactory() {
        assertThat(mapParser.getBoardCreator()).isEqualTo(boardFactory);
    }
}