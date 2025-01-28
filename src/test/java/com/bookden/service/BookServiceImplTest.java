//package com.bookden.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.bookden.entity.Book;
//import com.bookden.entity.SurvivorLocation;
//import com.bookden.model.enums.Gender;
//import com.bookden.model.responses.ApiResponse;
//import com.bookden.repository.BookRepository;
//import org.hamcrest.MatcherAssert;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.hamcrest.Matchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BookServiceImplTest {
//    private ObjectMapper objectMapper = new ObjectMapper();
//    @Mock
//    private BookRepository bookRepository;
//    @InjectMocks
//    private BookServiceImpl reportService;
//
//    @Test
//    public void getSurvivorSummaryWithNoSurvivors(){
//        Mockito
//                .when(bookRepository.count())
//                .thenReturn(0L).getMock();
//
//        Mockito
//                .when(bookRepository.findAllSurvivorsByInfectionState(false))
//                .thenReturn(Optional.of(Collections.EMPTY_LIST)).getMock();
//
//        ApiResponse survivorSummary = reportService.getAllTheBooksFromLibrary();
//
//        HashMap hashMap = objectMapper.convertValue(survivorSummary, HashMap.class);
//        HashMap map = (HashMap) hashMap.getOrDefault("data", null);
//
//        MatcherAssert.assertThat(map.getOrDefault("totalSurvivors",0L), equalTo(0L));
//        MatcherAssert.assertThat(survivorSummary, hasProperty("message", equalTo("Successfully produced searchResultsOfSurvivors report")));
//    }
//
//    @Test
//    public void getSurvivorSummaryWithInfectedSurvivors(){
//        Book infectedSurvivor = new Book(
//                1L,
//                "",
//                (byte) 76,
//                Gender.MALE,
//                "ouoi",
//                true,
//                SurvivorLocation.builder().longitude("i").latitude("o").build(),
//                new ArrayList<>(),
//                new ArrayList<>(),
//                new Date(),
//                new Date());
//        Mockito
//                .when(bookRepository.count())
//                .thenReturn(1L).getMock();
//
//        Mockito
//                .when(bookRepository.findAllSurvivorsByInfectionState(true))
//                .thenReturn(Optional.of(List.of(infectedSurvivor))).getMock();
//
//        ApiResponse survivorSummary = reportService.getAllTheBooksFromLibrary();
//
//        HashMap hashMap = objectMapper.convertValue(survivorSummary, HashMap.class);
//        HashMap map = (HashMap) hashMap.getOrDefault("data", null);
//
//        MatcherAssert.assertThat(map.getOrDefault("totalSurvivors",0L), equalTo(1L));
//        MatcherAssert.assertThat(map.getOrDefault("totalInfectedSurvivors",0L), equalTo(1));
//        MatcherAssert.assertThat(map.getOrDefault("infectedSurvivorsPercentage",0L), equalTo(100.0F));
//        MatcherAssert.assertThat(survivorSummary, hasProperty("message", equalTo("Successfully produced searchResultsOfSurvivors report")));
//    }
//
//    @Test
//    public void getSurvivorSummaryWithUnInfectedSurvivors(){
//        Book unInfectedSurvivor = new Book(
//                1L,
//                "",
//                (byte) 76,
//                Gender.MALE,
//                "ouoi",
//                false,
//                SurvivorLocation.builder().longitude("i").latitude("o").build(),
//                new ArrayList<>(),
//                new ArrayList<>(),
//                new Date(),
//                new Date());
//
//        Mockito
//                .when(bookRepository.count())
//                .thenReturn(1L).getMock();
//
//        Mockito
//                .when(bookRepository.findAllSurvivorsByInfectionState(false))
//                .thenReturn(Optional.of(List.of(unInfectedSurvivor))).getMock();
//
//        ApiResponse survivorSummary = reportService.getAllTheBooksFromLibrary();
//
//        HashMap hashMap = objectMapper.convertValue(survivorSummary, HashMap.class);
//        HashMap map = (HashMap) hashMap.getOrDefault("data", null);
//
//        MatcherAssert.assertThat(map.getOrDefault("totalSurvivors",0L), equalTo(1L));
//        MatcherAssert.assertThat(map.getOrDefault("totalNonInfectedSurvivors",0L), equalTo(1));
//        MatcherAssert.assertThat(map.getOrDefault("nonInfectedSurvivorsPercentage",0L), equalTo(100.0F));
//        MatcherAssert.assertThat(survivorSummary, hasProperty("message", equalTo("Successfully produced searchResultsOfSurvivors report")));
//    }
//}
