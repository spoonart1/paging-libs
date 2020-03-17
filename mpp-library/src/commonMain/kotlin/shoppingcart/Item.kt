package shoppingcart

interface Item {

    fun name(): String

    fun price(): Double

    fun sku(): Int

    fun guid(): String

}