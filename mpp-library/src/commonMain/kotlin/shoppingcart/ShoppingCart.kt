package shoppingcart


class ShoppingCart {

    private var param = ShoppingCartParam()
    private var isCalculated = false
    var totalAmount: Double = 0.0
        get() {
            if (!isCalculated) {
                throw IllegalStateException("please call calculate() first before trying to get the final amount")
            }
            return param.totalAmount
        }

    fun addItem(item: Item) {
        param.items.add(item)
    }

    fun addItems(newItems: MutableList<Item>) {
        param.items = newItems
    }

    fun addDiscountPercent(percent: Double) {
        param.discountPercentAmount = percent
    }

    fun addTax(taxPercent: Double) {
        param.taxPercent = taxPercent
    }

    fun calculate(): ShoppingCart {
        var totalAmount = 0.0
        param.items.forEach { item ->
            totalAmount += item.price()
        }

        val discount = param.discountPercentAmount * totalAmount
        val tax = param.taxPercent * totalAmount
        totalAmount = totalAmount - discount + tax

        param.totalAmount = totalAmount
        isCalculated = true
        return this
    }

    fun log() {
        if (param.taxPercent > 0) {
            println("tax included")
        }

        if (param.discountPercentAmount > 0) {
            println("discount is included")
        }

        println("total amount: $totalAmount")
    }

}

private class ShoppingCartParam {
    var totalAmount: Double = 0.0
    var taxPercent: Double = 0.0
    var discountPercentAmount: Double = 0.0
    lateinit var items: MutableList<Item>
}