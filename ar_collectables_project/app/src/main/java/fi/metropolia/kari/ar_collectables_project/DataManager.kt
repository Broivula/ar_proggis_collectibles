package fi.metropolia.kari.ar_collectables_project

import com.google.ar.sceneform.rendering.ModelRenderable
import kotlin.collections.LinkedHashMap

class Item(val tag: String, val model: String, val name: String, val description: String)


object DataManager {
    var complete = false
    val options: Array<Item> = arrayOf(
        Item(
            IMG_01,
            "urban_art_1.sfb",
            "Electrical Utility Box",
            "This electrical utility box has been decorated with beautiful graffiti by the local youths."
        ),
        Item(
            IMG_02,
            "urban_art_2.sfb",
            "Friendly Playground Ladybug",
        "A ladybug toy found on a local playground, designed by some corporation."
        ),
        Item(
            IMG_03,
            "Porsas.sfb",
            "Concrete Pig",
            "A marvel of modern design. Safe, easy to use, and pleases the eye with its bright colours."
        )
    )
    var renderables: MutableMap<Item, ModelRenderable> = mutableMapOf()
    val discovered: LinkedHashMap<Item, Boolean> = linkedMapOf()
}