//package com.bookden.repository;
//
//import com.bookden.entity.Book;
//import com.bookden.model.enums.Gender;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//@DataJpaTest
//public class BookRepositoryTest {
//    @Autowired
//    private BookRepository bookRepository;
//
//    @BeforeEach
//    public void beforeEach() {
//        bookRepository.save(new Book(
//                "PPP",
//                 35,
//                "Female",
//                "ID111",
//                "2222",
//                "111"));
//    }
//
//    @Test
//    public void createSurvivor(){
//        Book newSurvivor = bookRepository.save(new Book(
//                "NNN",
//                 2,
//                "Male",
//                "ID7897",
//                "12312",
//                "33312"));
//
//        assertThat(newSurvivor, hasProperty("id", equalTo(7L)));
//        assertThat(newSurvivor, hasProperty("name", equalTo("NNN")));
//        assertThat(newSurvivor, hasProperty("age", equalTo(2)));
//        assertThat(newSurvivor, hasProperty("identity", equalTo("ID7897")));
//    }
//
//    @Test
//    public void findMatchingSurvivor(){
//        Optional<Book> survivorResult = bookRepository.findByNameAndAgeAndIdentity(
//                "PPP",
//                 35,
//                "ID111");
//
//        assertThat(survivorResult.isPresent(), is(true));
//        Book survivor = survivorResult.get();
//        assertThat(survivor, hasProperty("name", equalTo("PPP")));
//        assertThat(survivor, hasProperty("age", equalTo(35)));
//        assertThat(survivor, hasProperty("identity", equalTo("ID111")));
//    }
//
//    @Test
//    public void nonMatchingSurvivor(){
//        Optional<Book> survivor = bookRepository.findByNameAndAgeAndIdentity(
//                "LLL",
//                 37,
//                "ID221");
//
//        assertThat(survivor.isPresent(), is(false));
//    }
//
//    @Test
//    public void findMatchingSurvivorByNameAndGenderAndIdentity(){
//        Optional<Book> survivorResult = bookRepository.findByNameAndGenderAndIdentity(
//                "PPP",
//                Gender.FEMALE,
//                "ID111");
//
//        assertThat(survivorResult.isPresent(), is(true));
//        Book survivor = survivorResult.get();
//        assertThat(survivor, hasProperty("name", equalTo("PPP")));
//        assertThat(survivor, hasProperty("age", equalTo(35)));
//        assertThat(survivor, hasProperty("identity", equalTo("ID111")));
//    }
//
//    @Test
//    public void findNonMatchingSurvivorByNameAndGenderAndIdentity(){
//        Optional<Book> survivor = bookRepository.findByNameAndGenderAndIdentity(
//                "YYY",
//                Gender.FEMALE,
//                "ID3331");
//
//        assertThat(survivor.isPresent(), is(false));
//    }
//
//    @Test
//    public void findAllInfectedSurvivorsNoResults(){
//        Optional<List<Book>> allSurvivorsByInfectionState = bookRepository.findAllSurvivorsByInfectionState(true);
//        List<Book> survivors = allSurvivorsByInfectionState.get();
//
//        assertThat(survivors.isEmpty(), is(true));
//    }
//
//    @Test
//    public void findAllInfectedSurvivorsWithResults(){
//        Book newSurvivor = new Book(
//                "DDD",
//                 19,
//                "Male",
//                "ID88",
//                "323",
//                "3232");
//
//        newSurvivor.setInfected(true);
//        bookRepository.save(newSurvivor);
//
//        Optional<List<Book>> allSurvivorsByInfectionState = bookRepository.findAllSurvivorsByInfectionState(true);
//        Book infectedSurvivor = allSurvivorsByInfectionState.get().get(0);
//        assertThat(infectedSurvivor, hasProperty("name", equalTo("DDD")));
//        assertThat(infectedSurvivor, hasProperty("age", equalTo(19)));
//        assertThat(infectedSurvivor, hasProperty("identity", equalTo("ID88")));
//    }
//
//    @Test
//    public void findAllNonInfectedSurvivorsWithResults(){
//        Optional<List<Book>> allSurvivorsByInfectionState = bookRepository.findAllSurvivorsByInfectionState(false);
//        List<Book> survivors = allSurvivorsByInfectionState.get();
//
//        Book survivor = survivors.get(0);
//        assertThat(survivors.size(), is(1));
//        assertThat(survivor, hasProperty("name", equalTo("PPP")));
//        assertThat(survivor, hasProperty("age", equalTo(35)));
//        assertThat(survivor, hasProperty("identity", equalTo("ID111")));
//    }
//}
