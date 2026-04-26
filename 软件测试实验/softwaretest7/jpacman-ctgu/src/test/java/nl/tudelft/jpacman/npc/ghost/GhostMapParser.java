package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;

import java.util.List;

/**
 * 用于编写幽灵单元测试的测试辅助工具。
 * 从地图中检索幽灵的有用方法是：
 * Navigation 类中的 findUnitInBoard 方法。
 */
public final class GhostMapParser extends MapParser {
    private final GhostFactory ghostFactory;

    /**
     * 创建一个新的增强型地图解析器。
     * 此地图解析器允许用户指定在确切位置放置哪种幽灵。
     *
     * @param levelFactory 提供 NPC 对象和关卡的工厂。
     * @param boardFactory 创建棋盘元素的工厂。
     * @param ghostFactory 创建幽灵的工厂。
     */
    public GhostMapParser(LevelFactory levelFactory, BoardFactory boardFactory,
            GhostFactory ghostFactory) {
        super(levelFactory, boardFactory);
        this.ghostFactory = ghostFactory;
    }

    // 该方法已经过修改，支持多种幽灵类型
    @Override
    protected void addSquare(Square[][] grid, List<Ghost> ghosts,
            List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case 'C':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createClyde());
                break;
            case 'I':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createInky());
                break;
            case 'B':
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createBlinky());
                break;
            default:
                super.addSquare(grid, ghosts, startPositions, x, y, c);
        }
    }

}
