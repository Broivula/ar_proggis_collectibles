package fi.metropolia.kari.ar_collectables_project

object DataManager {

    val discovered: LinkedHashMap<String, Boolean> = linkedMapOf(IMG_01 to false, IMG_02 to false, IMG_03 to false)
    val img_01_text = "some extra information about the first object"
    val img_02_text = "some extra information about the second object"
    val img_03_text = "some extra information about the third object"


    val extraImageInfo: Map<String, String> = mapOf(IMG_01 to img_01_text, IMG_02 to img_02_text, IMG_03 to img_03_text)
}