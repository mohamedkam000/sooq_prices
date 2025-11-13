package com.sooq.price.data

data class CardNode(
    val title: String,
    val imageUrl: String,
    val children: List<CardNode> = emptyList(),
    val description: String? = null,
    val prices: Map<String, Int> = emptyMap()
)

fun PriceData.toCardNodeList(): List<CardNode> {
    return this.states.map { it.toCardNode() }
}

private fun StateNode.toCardNode(): CardNode {
    return CardNode(
        title = this.name,
        imageUrl = this.img,
        children = this.markets.map { it.toCardNode() }
    )
}

private fun MarketNode.toCardNode(): CardNode {
    return CardNode(
        title = this.name,
        imageUrl = this.img,
        children = this.goods.map { it.toCardNode() }
    )
}

private fun GoodNode.toCardNode(): CardNode {
    return CardNode(
        title = this.name,
        imageUrl = this.img,
        children = this.items.map { it.toCardNode() }
    )
}

private fun ItemNode.toCardNode(): CardNode {
    return CardNode(
        title = this.name,
        imageUrl = this.img,
        children = emptyList(),
        prices = this.prices
    )
}

fun getNodeFromPath(path: String, cardTree: List<CardNode>): CardNode {
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTree
    var node: CardNode? = null
    indices.forEach { index ->
        node = currentNodeList[index]
        currentNodeList = node.children
    }
    return node!!
}

fun getListFromPath(path: String, cardTree: List<CardNode>): List<CardNode> {
    if (path.isEmpty()) return cardTree
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTree
    indices.forEach { index ->
        currentNodeList = currentNodeList[index].children
    }
    return currentNodeList
}