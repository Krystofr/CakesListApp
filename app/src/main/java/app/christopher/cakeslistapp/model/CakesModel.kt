package app.christopher.cakeslistapp.model


data class CakesModel(
    var title: String?,
    var desc: String?,
    var image: String?
) : Comparable<CakesModel> {
    override fun compareTo(other: CakesModel): Int {
        TODO("Return the title")

    }
    companion object : Comparator<CakesModel> {
        override fun compare(o1: CakesModel?, o2: CakesModel?): Int {
            TODO("To compare the title and the next in the list")
           // return o2?.title?.let { it -> o1?.title?.compareTo(it})
        }
    }
}