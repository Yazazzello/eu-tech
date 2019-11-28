package nl.yaz.eurailtech

import nl.yaz.eurailtech.util.OccurrenceCounter
import org.junit.Assert
import org.junit.Test

class CounterTest {
    @Test
    fun shouldCountDivProperly() {
        val bunchOfDivs = "<div> hoho </div> not so much div <div>"
        Assert.assertEquals("wrong count", 2, OccurrenceCounter.count("<div>", bunchOfDivs))
    }
}