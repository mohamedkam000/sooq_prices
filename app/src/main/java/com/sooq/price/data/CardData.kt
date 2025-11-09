package com.sooq.price.data

data class CardNode(
    val title: String,
    val imageUrl: String,
    val children: List<CardNode> = emptyList(),
    val description: String = "No additional details available for this item.",
    val price: Double? = null
)

private val placeholderImg = "https://picsum.photos/600/400?random=99"

val cardTreeData = listOf(
    CardNode("Khartoum", "https://images.unsplash.com/photo-1659864216522-494efbd76895?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8a2hhcnRvdW18ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=1200", children = listOf(
        CardNode("Central Market", "https://www.sudanakhbar.com/wp-content/uploads/2022/11/%D8%B5%D9%8A%D9%86%D9%8A%D8%A9-%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D9%85%D8%B1%D9%83%D8%B2%D9%8A-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85.jpg", children = listOf(
            CardNode("Fruits", "https://images.pexels.com/photos/1132047/pexels-photo-1132047.jpeg?auto=compress&cs=tinysrgb&w=600", children = listOf(
                CardNode(
                    "Apples",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTd-FnM96HFu7Qo8Ls-4g4Hv6aZcVwXCAo5wKVdhlxFDSlOQAJdVUvkJmw&s=10",
                    description = "Fresh, crispy red apples, sourced locally. Perfect for a healthy snack or baking.",
                    price = 750.00
                ),
                CardNode(
                    "Oranges",
                    "https://fruitguys.com/wp-content/uploads/2011/04/citrus-regreening-bigstock-92168057-1424x1080px-RESIZE.jpg",
                    description = "Juicy, sweet oranges, packed with Vitamin C. Great for fresh juice.",
                    price = 500.00
                ),
                CardNode("Bananas", "https://cdn.mos.cms.futurecdn.net/v2/t:0,l:464,cw:1193,ch:1193,q:80,w:1193/kzMYKaqQhtY2EzL7GABdah.jpg", price = 450.00),
                CardNode("Mangoes", "https://www.foodandwine.com/thmb/lOR4ePysSvV3jqMj6V3-80eCD3M=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6-Types-Of-Mangoes-Youll-Find-This-Season-FT-BLOG0525-Francis-Updated-2a03afd8a217456bb7219076f1bd3c25.jpg", price = 1200.00),
                CardNode("Grapes", "https://www.seriouseats.com/thmb/XwtcUWlY3TDauWPbBiID1yZ-6Jw=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/GettyImages-1289843973-karandaev-hero-ca6df1eb21504ba0965e2319ef4c26e3.jpg"),
                CardNode("Watermelon", "https://static.toiimg.com/thumb/msid-109165284,width-1280,height-720,resizemode-4/109165284.jpg")
            )),
            CardNode("Vegetables", "https://images.pexels.com/photos/1435904/pexels-photo-1435904.jpeg?auto=compress&cs=tinysrgb&w=600", children = listOf(
                CardNode("Tomatoes", "https://bittmanproject.com/wp-content/uploads/engin-akyurt-HrCatSbULFY-unsplash-scaled.jpg", price = 300.00),
                CardNode("Carrots", "https://www.tasteofhome.com/wp-content/uploads/2019/01/carrots-shutterstock_789443206.jpg"),
                CardNode("Onions", "https://www.jesmondfruitbarn.com.au/wp-content/uploads/2016/10/Jesmond-Fruit-Barn-Red-Onions.jpg", price = 250.00),
                CardNode("Potatoes", "https://cdn.mos.cms.futurecdn.net/iC7HBvohbJqExqvbKcV3pP-1920-80.jpg", price = 350.00),
                CardNode("Okra", "https://www.cookedandloved.com/wp-content/uploads/2020/02/what-is-okra-s.jpg"),
                CardNode("Eggplant", "https://cdn.britannica.com/64/143464-050-B0EC6714/Eggplant.jpg")
            )),
            CardNode("Meat", "https://t3.ftcdn.net/jpg/02/26/53/80/360_F_226538033_C42p96JDNwkSdQs86Agxd1TtaVJsyJ71.jpg", children = listOf(
                CardNode(
                    "Beef",
                    "https://www.allrecipes.com/thmb/zN81HQ86Fa2rHnkH-KUvUf6pBZE=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Cuts-of-Beef-3x2-1-a557f31f8b13462185b4f2c17ab5b746.png",
                    description = "1kg of high-quality, locally sourced ground beef.",
                    price = 8500.00
                ),
                CardNode(
                    "Mutton",
                    "https://bf1af2.akinoncloudcdn.com/products/2024/10/01/148192/1d9ad42e-f8b2-4300-a0e8-3003d6925d5e.jpg",
                    description = "1kg of tender mutton, perfect for stews and grilling.",
                    price = 9200.00
                ),
                CardNode("Fish", "https://static.vecteezy.com/system/resources/previews/007/461/070/large_2x/whole-fresh-raw-big-salmon-fish-photo.jpg", price = 6000.00),
                CardNode("Chicken", "https://media.istockphoto.com/id/1282866808/photo/fresh-raw-chicken.jpg?s=612x612&w=0&k=20&c=QtfdAhdeIGpR3JUNDmYFo6cN0el8oYMcOXMQI7Qder4=", price = 4500.00)
            )),
            CardNode("Grains", "https://media.istockphoto.com/id/481882305/photo/different-types-of-cereal-grains-with-ears.jpg?s=612x612&w=0&k=20&c=1cD0aYvSyQuZSCozhmBUojERS7QMmOQYOgkcpg7hcvk=", children = listOf(
                CardNode("Rice", "https://media.istockphoto.com/id/153737841/photo/rice.jpg?s=612x612&w=0&k=20&c=lfO7iLT0UsDDzra0uBOsN1rvr2d5OEtrG2uwbts33_c="),
                CardNode("Wheat", "https://5.imimg.com/data5/SELLER/Default/2024/1/375542488/CX/OT/AL/190754030/indian-wheat-grain.jpeg"),
                CardNode("Corn", placeholderImg),
                CardNode("Flour", "https://bobs-redmill.transforms.svdcdn.com/production/blog/uploads/2017/04/shutterstock_260258060.jpg?w=1280&h=633&auto=compress%2Cformat&fit=max&dm=1719288824&s=b383b80708da9fc7181a8e0891a67b68"),
                CardNode("Lentils", "https://nyspiceshop.com/cdn/shop/products/Split_Red_Lentils-Masoor_Dal.jpg?v=1730224120&width=1445"),
                CardNode("Beans", "https://www.ranchogordo.com/cdn/shop/files/large-white-lima-bean-960994.jpg?v=1730244122")
            ))
        )),
        CardNode("Local Market", placeholderImg, children = listOf(
            CardNode("Fruits", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQL4CPXA6CGmqjWrQKPJIYk3L5JjSnB5GMX9ka-FWSKOxCy0ImP2gk4d0VH&s=10", children = listOf(
                CardNode("Apples", "https://media.istockphoto.com/id/1302999429/photo/three-apples.jpg?s=612x612&w=0&k=20&c=gvQ3KRp5ck-oA29kcyCzMjTJiJzMLTHIOzP8oABXk4k="),
                CardNode("Oranges", "https://cdn-prod.medicalnewstoday.com/content/images/articles/272/272782/oranges-in-a-box.jpg"),
                CardNode("Bananas", "https://cdn.mos.cms.futurecdn.net/v2/t:0,l:0,cw:2120,ch:1193,q:80,w:2120/kzMYKaqQhtY2EzL7GABdah.jpg"),
                CardNode("Mangoes", "https://ichef.bbci.co.uk/images/ic/1040x1040/p06hk0h6.jpg"),
                CardNode("Grapes", "https://parade.com/.image/c_fill,w_600,h_600,g_faces:center/ODowMDAwMDAwMDAxMTE5ODc0/what-happens-to-your-body-when-you-eat-grapes-every-day.jpg"),
                CardNode("Watermelon", "https://www.watermelon.org/wp-content/uploads/2019/05/Melon-Ground-Spot-2000x1200.jpg")
            )),
            CardNode("Vegetables", placeholderImg, children = listOf(
                CardNode("Tomatoes", "https://images-prod.healthline.com/hlcmsresource/images/AN_images/tomatoes-1296x728-feature.jpg"),
                CardNode("Carrots", placeholderImg),
                CardNode("Onions", "https://en-chatelaine.mblycdn.com/ench/resized/2018/08/1600x900/types-of-onions.jpg"),
                CardNode("Potatoes", placeholderImg),
                CardNode("Okra", placeholderImg),
                CardNode("Eggplant", placeholderImg)
            )),
            CardNode("Meat", "https://www.shutterstock.com/image-photo/different-types-raw-meat-beef-600nw-2233247387.jpg", children = listOf(
                CardNode("Beef", "https://www.lovefoodhatewaste.com/sites/default/files/styles/16_9_two_column/public/2022-08/Beef-sh344681603.jpg.webp?itok=qenlRZUs"),
                CardNode("Mutton", placeholderImg),
                CardNode("Fish", "https://thecookful.com/wp-content/uploads/2022/07/Kinds-of-White-Fish-1200x1200-1-680x680.jpg"),
                CardNode("Chicken", placeholderImg)
            )),
            CardNode("Grains", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4T633lWKU0xqwOiYuaLnrp4Fwn90ZlJiDiw&usqp=CAU", children = listOf(
                CardNode("Rice", placeholderImg),
                CardNode("Wheat", "https://www.fieldstoneorganics.ca/wp-content/uploads/2022/04/Hard-White-Wheat.jpg"),
                CardNode("Corn", placeholderImg),
                CardNode("Flour", "https://img.freepik.com/premium-photo/sack-flour_929907-1730.jpg"),
                CardNode("Lentils", "https://rqn.com.au/wp-content/uploads/2016/05/red-lentil-compress.jpg"),
                CardNode("Beans", "https://www.suttonsbaytrading.com/wp-content/uploads/2013/07/baby-lima-beans.jpg")
            ))
        )),
        CardNode("Sabreen Market", "https://alnawrs.com/wp-content/uploads/2025/06/%D8%B3%D9%88%D9%82-%D8%B5%D8%A7%D8%A8%D8%B1%D9%8A%D9%86.png", children = listOf(
            CardNode("Fruits", "https://www.diagnosisdiet.com/assets/images/7/fruit-og-jcnp4h14m2mh5t9.jpg", children = listOf(
                CardNode("Apples", "https://media.istockphoto.com/id/164084111/photo/apples.jpg?s=612x612&w=0&k=20&c=eAjYVWWhrEVKMVVvzSjNzFU9dtjh2aH2ypjmclDaUi0="),
                CardNode("Oranges", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiYWJja7ljHmJKufcD2q7NcOunbKZv5Mglj67WpYJ7zSMaHKvfm6Bx2Us&s=10"),
                CardNode("Bananas", "https://www.verywellhealth.com/thmb/F18R53wqIZSm9RWjlqLjbT3Nf34=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/VWH-GettyImages-1650710915-877f191e64f54e168ae7ce99b9aa052b.jpg"),
                CardNode("Mangoes", "https://static.vecteezy.com/system/resources/thumbnails/023/732/154/small/mango-fruit-hanging-on-a-tree-with-a-rustic-wooden-table-free-photo.jpg"),
                CardNode("Grapes", "https://parade.com/.image/c_fill,w_600,h_600,g_faces:center/ODowMDAwMDAwMDAxMTE5ODc0/what-happens-to-your-body-when-you-eat-grapes-every-day.jpg"),
                CardNode("Watermelon", "https://www.chhs.colostate.edu/fsi/wp-content/uploads/sites/51/2024/07/stack-of-watermelon-800x0-c-default.jpg")
            )),
            CardNode("Vegetables", placeholderImg, children = listOf(
                CardNode("Tomatoes", "https://media.istockphoto.com/id/116027879/photo/cherry-tomatoes.jpg?s=170667a&w=0&k=20&c=U5S3uGtpIRUHlwVZOnXAQ5kSryPu4b8Ohf8iC6qAZ-Q="),
                CardNode("Carrots", placeholderImg),
                CardNode("Onions", placeholderImg),
                CardNode("Potatoes", placeholderImg),
                CardNode("Okra", placeholderImg),
                CardNode("Eggplant", placeholderImg)
            )),
            CardNode("Meat", "https://www.hayesmeats.com/wp-content/uploads/2018/11/test2.jpg", children = listOf(
                CardNode("Beef", placeholderImg),
                CardNode("Mutton", placeholderImg),
                CardNode("Fish", placeholderImg),
                CardNode("Chicken", placeholderImg)
            )),
            CardNode("Grains", placeholderImg, children = listOf(
                CardNode("Rice", placeholderImg),
                CardNode("Wheat", placeholderImg),
                CardNode("Corn", placeholderImg),
                CardNode("Flour", placeholderImg),
                CardNode("Lentils", placeholderImg),
                CardNode("Beans", placeholderImg)
            ))
        ))
    )),
    CardNode("Al-Jazeera", "https://www.alnilin.com/wp-content/uploads/2023/09/madani_kush-780x470.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
            CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("River Nile", "https://www.sudanakhbar.com/wp-content/uploads/2024/10/445.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Red Sea", "https://i0.wp.com/arabscountries.com/wp-content/uploads/2022/11/The-Red-Sea-1.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
            CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Kassala", "https://saqraljidyanews.com/wp-content/uploads/2020/08/d6e7a4bc43ae7ef56f2bcf0bb6571f6e.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
            CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Sinnar", "https://kushnews.net/wp-content/uploads/2023/09/sinnar_kush.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Northern Sudan", "https://sudanjournal.com/wp-content/uploads/2021/01/a1bd13c5fc813f0c17a64c8d52265180.jpg", children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("White Nile", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Blue Nile", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Al-Gadarif", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Southern Kordofan", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Western Kordofan", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
    CardNode("Northern Kordofan", placeholderImg, children = listOf(
        CardNode("N/A", placeholderImg, children = listOf(
             CardNode("N/A", placeholderImg, children = listOf(
                CardNode("N/A", placeholderImg)
            ))
        ))
    )),
)

fun getNodeFromPath(path: String): CardNode {
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    var node: CardNode? = null
    indices.forEach { index ->
        node = currentNodeList[index]
        currentNodeList = node!!.children
    }
    return node!!
}

fun getListFromPath(path: String): List<CardNode> {
    if (path.isEmpty()) return cardTreeData
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    indices.forEach { index ->
        currentNodeList = currentNodeList[index].children
    }
    return currentNodeList
}