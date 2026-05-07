package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * 测试 Inky 幽灵的行为，特别是基于 Blinky 的位置和 Pac-Man 前方两个格子位置的 AI 移动。
 *
 * @author Jeroen Roosen
 */
public class InkyTest {

    /**
     * 提供所有游戏精灵的精灵存储。
     */
    private PacManSprites sprites;

    /**
     * 创建玩家的工厂。
     */
    private PlayerFactory playerFactory;

    /**
     * 创建幽灵的工厂。
     */
    private GhostFactory ghostFactory;

    /**
     * 创建游戏棋盘的工厂。
     */
    private BoardFactory boardFactory;

    /**
     * 创建关卡的工厂。
     */
    private LevelFactory levelFactory;

    /**
     * 用于从字符串地图创建关卡的地图解析器。
     */
    private GhostMapParser parser;

    /**
     * 测试中的玩家。
     */
    private Player player;

    /**
     * 测试中的 Inky 幽灵。
     */
    private Inky inky;

    /**
     * 测试中的 Blinky 幽灵。
     */
    private Blinky blinky;

    /**
     * 在每次测试前设置游戏基础设施。
     */
    @BeforeEach
    void setUp() {
        sprites = new PacManSprites();
        playerFactory = new PlayerFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        boardFactory = new BoardFactory(sprites);
        levelFactory = new LevelFactory(sprites, ghostFactory, mock(PointCalculator.class));
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    /**
     * 从给定的地图创建关卡并提取玩家、Inky 和 Blinky。
     *
     * @param map 表示地图的字符串数组。
     * @return 解析后的 Level 对象。
     */
    private Level createLevel(String[] map) {
        Level level = parser.parseMap(Arrays.asList(map));
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Board board = level.getBoard();
        inky = (Inky) Navigation.findNearest(Inky.class, board.squareAt(0, 0));
        blinky = (Blinky) Navigation.findNearest(Blinky.class, board.squareAt(0, 0));

        return level;
    }

    /**
     * 测试当棋盘上没有 Blinky 和玩家时，Inky 返回空的 Optional。
     */
    @Test
    void testInkyWithNoBlinkyOrPlayer() {
        String[] map = {
                "############",
                " #         I",
                "############"
        };

        Level level = parser.parseMap(Arrays.asList(map));
        Board board = level.getBoard();
        inky = (Inky) Navigation.findNearest(Inky.class, board.squareAt(0, 0));

        Optional<Direction> direction = inky.nextAiMove();

        assertThat(direction).isEmpty();
    }

    /**
     * 测试当棋盘上没有 Blinky 时，Inky 失去了跟踪对象，返回空的 Optional。
     */
    @Test
    void testInkyWithNoBlinky() {
        String[] map = {
                "############",
                " #P        I",
                "############"
        };

        Level level = parser.parseMap(Arrays.asList(map));
        Board board = level.getBoard();
        inky = (Inky) Navigation.findNearest(Inky.class, board.squareAt(0, 0));

        Optional<Direction> direction = inky.nextAiMove();

        assertThat(direction).isEmpty();
    }

    /**
     * 测试当棋盘上没有玩家时，Inky 返回空的 Optional。
     */
    @Test
    void testInkyWithNoPlayer() {
        String[] map = {
                "############",
                " #B        I",
                "############"
        };

        Level level = parser.parseMap(Arrays.asList(map));
        Board board = level.getBoard();
        inky = (Inky) Navigation.findNearest(Inky.class, board.squareAt(0, 0));

        Optional<Direction> direction = inky.nextAiMove();

        assertThat(direction).isEmpty();
    }

    /**
     * 测试当 Blinky 和玩家都存在时，Inky 的行为。
     * Inky 应该基于 Blinky 的位置和玩家的方向计算目标位置。
     */
    @Test
    void testInkyWithBlinkyAndPlayer() {
        String[] map = {
                "############",
                " #B P      I",
                "############"
        };

        createLevel(map);

        Optional<Direction> direction = inky.nextAiMove();

        assertThat(direction).isPresent();
    }

    /**
     * 测试当玩家面向不同方向时，Inky 的行为。
     * Inky 的目标位置应该根据玩家的方向而变化。
     */
    @Test
    void testInkyWithPlayerFacingDifferentDirections() {
        String[] map = {
                "############",
                " #B P      I",
                "############"
        };

        createLevel(map);

        // 测试玩家面向东方
        player.setDirection(Direction.EAST);
        Optional<Direction> directionEast = inky.nextAiMove();
        assertThat(directionEast).isPresent();

        // 测试玩家面向西方
        player.setDirection(Direction.WEST);
        Optional<Direction> directionWest = inky.nextAiMove();
        assertThat(directionWest).isPresent();
    }
}
