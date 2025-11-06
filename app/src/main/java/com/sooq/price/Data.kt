package com.sooq.price

data class State(
    val id: String,
    val name: String,
    val img: String,
    val markets: List<Market>
)

data class Market(
    val id: String,
    val name: String,
    val img: String,
    val goods: List<Good>
)

data class Good(
    val id: String,
    val name: String,
    val img: String,
    val items: List<Item>
)

data class Item(
    val id: String,
    val name: String,
    val img: String?,
    val price: String?
)

val sampleStates = listOf(
    State(
        "khartoum", "Khartoum",
        "https://images.unsplash.com/photo-1659864216522-494efbd76895",
        listOf(
            Market(
                "central", "Central Market",
                "https://www.sudanakhbar.com/wp-content/uploads/2022/11/khartoum.jpg",
                listOf(
                    Good(
                        "vegetables", "Vegetables",
                        "https://sudafax.com/wp-content/uploads/2018/10/vegetables.jpg",
                        listOf(
                            Item("tomatoes", "Tomatoes", null, "1000 SDG"),
                            Item("carrots", "Carrots", null, "1500 SDG")
                        )
                    ),
                    Good(
                        "fruits", "Fruits",
                        "https://arabic.news.cn/2022-05/01/1310581082_16513444999741n.jpg",
                        listOf(
                            Item("apples", "Apples", null, "1200 SDG"),
                            Item("bananas", "Bananas", null, "800 SDG")
                        )
                    )
                )
            )
        )
    )
)