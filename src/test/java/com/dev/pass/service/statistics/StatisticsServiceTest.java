package com.dev.pass.service.statistics;

import com.dev.pass.repository.statistics.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 사용
class StatisticsServiceTest {

    /*
        StatisticsService 가 의존하는 StatisticsRepository 를 Mock 객체로 선언하여
         의존성을 제거함으로써 테스트하고자 하는 컴포넌트의 독립성을 보장
     */
    @Mock
    private StatisticsRepository statisticsRepository;

    /*
        @Mock으로 생성된 모의 객체나 다른 준비된 객체들을 자동으로 주입(inject)하기 위해 사용
        해당 타입의 필드 중 @Mock으로 생성된 객체를 자동으로 찾아서 주입
     */
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void test_makeChartData() {
        // Given
        final LocalDateTime to = LocalDateTime.of(2022,9,10,0,0);

        List<AggregatedStatistics> statisticsList = List.of(
                new AggregatedStatistics(to.minusDays(1), 15, 10,5),
                new AggregatedStatistics(to, 10, 8,2)
        );

        // When

        // findByStatisticsAtBetweenAndGroupBy 를 수행하면 List<AggregatedStatistics> statisticsList 를 Return 할 것이라는 설계
        when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(statisticsList);
        final ChartData chartData = statisticsService.makeChartData(to);

        // Then, 검증
        verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));

        // 해당 데이터가 비었는지 검증
        assertNotNull(chartData);
        assertEquals(new ArrayList<>(List.of("09-09", "09-10")), chartData.getLabels());
        assertEquals(new ArrayList<>(List.of(10L,8L)), chartData.getAttendedCounts()); // count Type : Long
        assertEquals(new ArrayList<>(List.of(5L, 2L)), chartData.getCancelledCounts());
    }
}