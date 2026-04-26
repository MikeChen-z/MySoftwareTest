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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * 测试 Clyde 幽灵的行为，尤其是它的移动方式。
 *
 * @author Jeroen Roosen
 */
public class ClydeTest {

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
     * 测试中的 Clyde 幽灵。
     */
    private Clyde clyde;

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
     * 从给定的地图创建关卡并提取玩家和 Clyde。
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
        clyde = (Clyde) Navigation.findNearest(Clyde.class, board.squareAt(0, 0));

        return level;
    }

    /**
     * 测试当距离大于 8 格时，Clyde 向玩家移动。
     * 根据AI移动逻辑，当 Clyde 远离 PacMan（超过八个格子）时，
     * 它应该向玩家的位置移动。
     */
    @Test
    void testClydeMovesTowardsPlayerWhenFarAway() {
        String[] map = {
                "############",
                " #P        C",
                "############"
        };

        createLevel(map);

        Optional<Direction> direction = clyde.nextAiMove();

        assertThat(direction).isPresent();
        assertThat(direction.get()).isEqualTo(Direction.WEST);
    }

    /**
     * 测试当距离为 8 格或更少时，Clyde 远离玩家移动。
     * 根据 AI 逻辑，当 Clyde 进入 Pac-Man 的八个格子范围内时，
     * 它应该向相反方向移动（逃跑）。
     */
    @Test
    void testClydeRunsAwayWhenClose() {
        String[] map = {
                "############",
                " #P   C    #",
                "############"
        };

        createLevel(map);

        Optional<Direction> direction = clyde.nextAiMove();

        assertThat(direction).isPresent();
        assertThat(direction.get()).isIn(Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }

    /**
     * 测试当棋盘上没有玩家时，Clyde 失去了跟踪对象，返回空的 Optional。
     */
    @Test
    void testClydeWithNoPlayer() {
        String[] map = {
                "############",
                " #P        C",
                "############"
        };

        Level level = createLevel(map);

        player.leaveSquare();

        Optional<Direction> direction = clyde.nextAiMove();

        assertThat(direction).isEmpty();
    }

    /**
     * 测试当 Clyde 与玩家相邻时，它会远离玩家。
     * 当 Clyde 和玩家之间的距离为 1（相邻格子）时，
     * Clyde 应该向相反方向逃跑。
     */
    @Test
    void testClydeAdjacentToPlayerRunsAway() {
        String[] map = {
                "############",
                " #P C      #",
                "############"
        };

        createLevel(map);

        Optional<Direction> direction = clyde.nextAiMove();

        assertThat(direction).isPresent();
        assertThat(direction.get()).isIn(Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }
}
