# MySoftwareTest - 软件测试实验仓库

这是一个软件测试课程的实验项目集合，包含多个独立的测试实验项目。

## 📁 项目结构

```
MySoftwareTest/
├── 软件测试实验一/          # 实验一：三角形分类测试
│   ├── app/
│   ├── gradle/
│   ├── README.md            # 详见实验一说明
│   └── ...
├── 软件测试实验二/          # 实验二：边界值测试
│   ├── app/
│   ├── gradle/
│   ├── README.md            # 详见实验二说明
│   └── ...
├── .gitignore               # Git忽略规则
├── .vscode/                 # VS Code配置
├── 仓库简介                  # 项目简介文档
└── README.md                # 本文件
```

## 📚 项目清单

| 项目 | 描述 | 测试方法 |
|------|------|---------|
| **软件测试实验一** | 三角形分类测试 | 等价类、边界值、健壮性、最坏情况 |
| **软件测试实验二** | 边界值测试 | 一般边界值、健壮性边界值、最坏情况 |

## 🚀 快速开始

### 通用步骤
所有项目都使用 **Gradle** 作为构建工具。

#### 编译指定项目
```bash
cd 软件测试实验一
./gradlew build

# 或

cd 软件测试实验二
./gradlew build
```

#### 运行测试
```bash
cd 软件测试实验一
./gradlew test

# 或

cd 软件测试实验二
./gradlew test
```

#### 查看测试报告
```bash
cd <项目目录>
./gradlew build
# 报告位于: app/build/reports/tests/test/index.html
```

## 📖 详细说明

- 详见各项目的 **README.md** 文件获取特定项目信息
- 详见 **仓库简介** 文件了解整体背景

## 🛠️ 开发环境

- **Java**: JDK 8+ 推荐 JDK 11 或更高版本
- **构建工具**: Gradle 7.0+
- **IDE**: IntelliJ IDEA / VS Code
- **版本控制**: Git

## 📝 提交规范

- 新增功能/修复用 `git add` 跟踪文件
- 编译产物 (`/bin`, `/build`) 已在 `.gitignore` 中排除
- IDE配置 (`.idea`, `.vscode`) 仅本地使用，不上传

## 🔗 相关链接

- GitHub 仓库: https://github.com/MikeChen-z/MySoftwareTest
- 作者: MikeChen-z

## 📄 许可证

该项目仅用于学习目的。

---

**最后更新**: 2026年3月22日
