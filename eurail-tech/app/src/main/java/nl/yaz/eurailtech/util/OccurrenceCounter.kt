package nl.yaz.eurailtech.util

object OccurrenceCounter {

    fun count(what: String, where: String): Int {
        val pattern = Regex(what)
        return pattern.findAll(where).count()
    }

}