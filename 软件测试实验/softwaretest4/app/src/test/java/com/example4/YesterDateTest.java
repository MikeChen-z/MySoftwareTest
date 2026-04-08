package com.example4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YesterDateTest {
    // 常量定义
    private static final int TEST_YEAR_1949 = 1949;
    private static final int TEST_YEAR_1950 = 1950;
    private static final int TEST_YEAR_2000 = 2000;
    private static final int TEST_YEAR_2024 = 2024;
    private static final int TEST_YEAR_2026 = 2026;
    private static final int TEST_YEAR_2051 = 2051;
    private static final int TEST_MONTH_0 = 0;
    private static final int TEST_MONTH_1 = 1;
    private static final int TEST_MONTH_2 = 2;
    private static final int TEST_MONTH_4 = 4;
    private static final int TEST_MONTH_5 = 5;
    private static final int TEST_MONTH_13 = 13;
    private static final int TEST_DAY_0 = 0;
    private static final int TEST_DAY_1 = 1;
    private static final int TEST_DAY_2 = 2;
    private static final int TEST_DAY_3 = 3;
    private static final int TEST_DAY_30 = 30;

    // 规则1: 无效日期
    @Test
    void testInvalidDateYearLessThan1950() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_1949, TEST_MONTH_1, TEST_DAY_1));
    }

    @Test
    void testInvalidDateYearGreaterThan2050() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_2051, TEST_MONTH_1, TEST_DAY_1));
    }

    @Test
    void testInvalidDateMonthLessThan1() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_0, TEST_DAY_1));
    }

    @Test
    void testInvalidDateMonthGreaterThan12() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_13, TEST_DAY_1));
    }

    @Test
    void testInvalidDateDayLessThan1() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_1, TEST_DAY_0));
    }

    @Test
    void testInvalidDateDayGreaterThanMonthDays() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_2, TEST_DAY_30));
    }

    // 规则2: 1950年1月1日
    @Test
    void testSpecialCase19500101() {
        assertEquals("Invalid date", YesterDate.getYesterDay(TEST_YEAR_1950, TEST_MONTH_1, TEST_DAY_1));
    }

    // 规则3: Day >= 2 的有效日期
    @Test
    void testNormalCaseDayGreaterThan2() {
        assertEquals("2026-05-02", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_5, TEST_DAY_3));
    }

    @Test
    void testNormalCaseDayEquals2() {
        assertEquals("2026-05-01", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_5, TEST_DAY_2));
    }

    // 规则4: Day=1 且 Month=1 的有效日期
    @Test
    void testEdgeCaseDay1Month1() {
        assertEquals("2025-12-31", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_1, TEST_DAY_1));
    }

    // 规则5: Day=1 且 Month!=1 的有效日期
    @Test
    void testEdgeCaseDay1MonthNot131Days() {
        assertEquals("2026-04-30", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_5, TEST_DAY_1));
    }

    @Test
    void testEdgeCaseDay1MonthNot130Days() {
        assertEquals("2026-03-31", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_4, TEST_DAY_1));
    }

    @Test
    void testEdgeCaseDay1MonthNot1FebruaryLeapYear() {
        assertEquals("2026-01-31", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_2, TEST_DAY_1));
    }

    @Test
    void testEdgeCaseDay1MonthNot1FebruaryNonLeapYear() {
        assertEquals("2026-01-31", YesterDate.getYesterDay(TEST_YEAR_2026, TEST_MONTH_2, TEST_DAY_1));
    }

    // 测试闰年3月1号的前一天（应该是2月29号）
    @Test
    void testEdgeCaseDay1Month3LeapYear() {
        assertEquals("2024-02-29", YesterDate.getYesterDay(TEST_YEAR_2024, 3, TEST_DAY_1));
    }

    // 测试能被400整除的闰年（2000年）3月1号的前一天（应该是2月29号）
    @Test
    void testEdgeCaseDay1Month3LeapYearDivisibleBy400() {
        assertEquals("2000-02-29", YesterDate.getYesterDay(TEST_YEAR_2000, 3, TEST_DAY_1));
    }
}
