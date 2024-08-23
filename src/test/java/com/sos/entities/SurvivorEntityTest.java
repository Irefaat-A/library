package com.sos.entities;

import com.sos.models.Item;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SurvivorEntityTest {

    @Test
    public void testAddInventory(){
        Survivor survivor = new Survivor();
        List<Item> allItems = new ArrayList<>();
        Item item = Item.builder().description("Beans").itemType("Food").quantity(5).build();
        allItems.add(item);
        survivor.addInventory(allItems);
        Optional<Inventory> allInventory = survivor.getAllInventory().stream().findFirst();

        assertThat(survivor.getAllInventory().size(), is(1));
        assertThat(allInventory.get().getDescription(), equalTo("Beans"));
    }

    @Test
    public void testAddContaminationReporter() {
        Survivor survivor = new Survivor();
        survivor.addContaminationReporter(1L);
        Optional<ContaminationReport> contaminationReport = survivor.getContaminationReport().stream().findFirst();

        assertThat(survivor.getContaminationReport().size(), is(1));
        assertThat(contaminationReport.get().getReporterId(), equalTo(1L));
    }
}
