package com.bookden.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateTimeUtilTest {

    @Test
    public void formatDateSuccessfully(){
        String formattedDate = DateTimeUtil.formatLongTimestampToDate(new Date("14/05/2024"));
        MatcherAssert.assertThat(formattedDate, Matchers.is("05-02-2025:12:00"));
    }
}
