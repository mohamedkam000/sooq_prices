package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.input.nestedscroll.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import java.text.*
import java.util.*
import coil.compose.*
import coil.request.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class CardNode(
    val title: String,
    val imageUrl: String,
    val children: List<CardNode> = emptyList()
)

private val cardTreeData = listOf(
    CardNode("Khartoum", "https://images.unsplash.com/photo-1659864216522-494efbd76895?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8a2hhcnRvdW18ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=1200", children = listOf(
        CardNode("Central Market", "https://www.sudanakhbar.com/wp-content/uploads/2022/11/%D8%B5%D9%8A%D9%86%D9%8A%D8%A9-%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D9%85%D8%B1%D9%83%D8%B2%D9%8A-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85.jpg", children = listOf(
            CardNode("Fruits", "https://images.pexels.com/photos/1132047/pexels-photo-1132047.jpeg?auto=compress&cs=tinysrgb&w=600", children = listOf(
                CardNode("Apples", "https://hips.hearstapps.com/hmg-prod/images/apples-at-farmers-market-royalty-free-image-1627321463.jpg?crop=0.796xw:1.00xh;0.103xw,0&resize=640:*"),
                CardNode("Oranges", "https://fruitguys.com/wp-content/uploads/2011/04/citrus-regreening-bigstock-92168057-1424x1080px-RESIZE.jpg"),
                CardNode("Bananas", "https://cdn.mos.cms.futurecdn.net/v2/t:0,l:464,cw:1193,ch:1193,q:80,w:1193/kzMYKaqQhtY2EzL7GABdah.jpg"),
                CardNode("Mangoes", "https://www.foodandwine.com/thmb/lOR4ePysSvV3jqMj6V3-80eCD3M=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6-Types-Of-Mangoes-Youll-Find-This-Season-FT-BLOG0525-Francis-Updated-2a03afd8a217456bb7219076f1bd3c25.jpg"),
                CardNode("Grapes", "https://www.seriouseats.com/thmb/XwtcUWlY3TDauWPbBiID1yZ-6Jw=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/GettyImages-1289843973-karandaev-hero-ca6df1eb21504ba0965e2319ef4c26e3.jpg"),
                CardNode("Watermelon", "https://static.toiimg.com/thumb/msid-109165284,width-1280,height-720,resizemode-4/109165284.jpg")
            )),
            CardNode("Vegetables", "https://images.pexels.com/photos/1435904/pexels-photo-1435904.jpeg?auto=compress&cs=tinysrgb&w=600", children = listOf(
                CardNode("Tomatoes", "https://bittmanproject.com/wp-content/uploads/engin-akyurt-HrCatSbULFY-unsplash-scaled.jpg"),
                CardNode("Carrots", "https://www.tasteofhome.com/wp-content/uploads/2019/01/carrots-shutterstock_789443206.jpg"),
                CardNode("Onions", "https://www.jesmondfruitbarn.com.au/wp-content/uploads/2016/10/Jesmond-Fruit-Barn-Red-Onions.jpg"),
                CardNode("Potatoes", "https://cdn.mos.cms.futurecdn.net/iC7HBvohbJqExqvbKcV3pP-1920-80.jpg"),
                CardNode("Okra", "https://www.cookedandloved.com/wp-content/uploads/2020/02/what-is-okra-s.jpg"),
                CardNode("Eggplant", "https://cdn.britannica.com/64/143464-050-B0EC6714/Eggplant.jpg")
            )),
            CardNode("Meat", "https://t3.ftcdn.net/jpg/02/26/53/80/360_F_226538033_C42p96JDNwkSdQs86Agxd1TtaVJsyJ71.jpg", children = listOf(
                CardNode("Beef", "https://www.allrecipes.com/thmb/zN81HQ86Fa2rHnkH-KUvUf6pBZE=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Cuts-of-Beef-3x2-1-a557f31f8b13462185b4f2c17ab5b746.png"),
                CardNode("Mutton", "https://bf1af2.akinoncloudcdn.com/products/2024/10/01/148192/1d9ad42e-f8b2-4300-a0e8-3003d6925d5e.jpg"),
                CardNode("Fish", "https://static.vecteezy.com/system/resources/previews/007/461/070/large_2x/whole-fresh-raw-big-salmon-fish-photo.jpg"),
                CardNode("Chicken", "https://media.istockphoto.com/id/1282866808/photo/fresh-raw-chicken.jpg?s=612x612&w=0&k=20&c=QtfdAhdeIGpR3JUNDmYFo6cN0el8oYMcOXMQI7Qder4=")
            )),
            CardNode("Grains", "https://media.istockphoto.com/id/481882305/photo/different-types-of-cereal-grains-with-ears.jpg?s=612x612&w=0&k=20&c=1cD0aYvSyQuZSCozhmBUojERS7QMmOQYOgkcpg7hcvk=", children = listOf(
                CardNode("Rice", "https://media.istockphoto.com/id/153737841/photo/rice.jpg?s=612x612&w=0&k=20&c=lfO7iLT0UsDDzra0uBOsN1rvr2d5OEtrG2uwbts33_c="),
                CardNode("Wheat", "https://5.imimg.com/data5/SELLER/Default/2024/1/375542488/CX/OT/AL/190754030/indian-wheat-grain.jpeg"),
                CardNode("Corn", ""),
                CardNode("Flour", "https://bobs-redmill.transforms.svdcdn.com/production/blog/uploads/2017/04/shutterstock_260258060.jpg?w=1280&h=633&auto=compress%2Cformat&fit=max&dm=1719288824&s=b383b80708da9fc7181a8e0891a67b68"),
                CardNode("Lentils", "https://nyspiceshop.com/cdn/shop/products/Split_Red_Lentils-Masoor_Dal.jpg?v=1730224120&width=1445"),
                CardNode("Beans", "https://www.ranchogordo.com/cdn/shop/files/large-white-lima-bean-960994.jpg?v=1730244122")
            ))
        )),
        CardNode("Local Market", "https://picsum.photos/600/400?random=11", children = listOf(
            CardNode("Fruits", "https://picsum.photos/600/400?random=111", children = listOf(
                CardNode("Apples", "https://media.istockphoto.com/id/1302999429/photo/three-apples.jpg?s=612x612&w=0&k=20&c=gvQ3KRp5ck-oA29kcyCzMjTJiJzMLTHIOzP8oABXk4k="),
                CardNode("Oranges", "https://cdn-prod.medicalnewstoday.com/content/images/articles/272/272782/oranges-in-a-box.jpg"),
                CardNode("Bananas", "https://cdn.mos.cms.futurecdn.net/v2/t:0,l:0,cw:2120,ch:1193,q:80,w:2120/kzMYKaqQhtY2EzL7GABdah.jpg"),
                CardNode("Mangoes", "https://ichef.bbci.co.uk/images/ic/1040x1040/p06hk0h6.jpg"),
                CardNode("Grapes", "https://parade.com/.image/c_fill,w_600,h_600,g_faces:center/ODowMDAwMDAwMDAxMTE5ODc0/what-happens-to-your-body-when-you-eat-grapes-every-day.jpg"),
                CardNode("Watermelon", "https://www.watermelon.org/wp-content/uploads/2019/05/Melon-Ground-Spot-2000x1200.jpg")
            )),
            CardNode("Vegetables", "", children = listOf(
                CardNode("Tomatoes", "https://images-prod.healthline.com/hlcmsresource/images/AN_images/tomatoes-1296x728-feature.jpg"),
                CardNode("Carrots", ""),
                CardNode("Onions", "https://en-chatelaine.mblycdn.com/ench/resized/2018/08/1600x900/types-of-onions.jpg"),
                CardNode("Potatoes", ""),
                CardNode("Okra", ""),
                CardNode("Eggplant", "")
            )),
            CardNode("Meat", "https://www.shutterstock.com/image-photo/different-types-raw-meat-beef-600nw-2233247387.jpg", children = listOf(
                CardNode("Beef", "https://www.lovefoodhatewaste.com/sites/default/files/styles/16_9_two_column/public/2022-08/Beef-sh344681603.jpg.webp?itok=qenlRZUs"),
                CardNode("Mutton", ""),
                CardNode("Fish", "https://thecookful.com/wp-content/uploads/2022/07/Kinds-of-White-Fish-1200x1200-1-680x680.jpg"),
                CardNode("Chicken", "")
            )),
            CardNode("Grains", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4T633lWKU0xqwOiYuaLnrp4Fwn90ZlJiDiw&usqp=CAU", children = listOf(
                CardNode("Rice", ""),
                CardNode("Wheat", "https://www.fieldstoneorganics.ca/wp-content/uploads/2022/04/Hard-White-Wheat.jpg"),
                CardNode("Corn", ""),
                CardNode("Flour", "https://img.freepik.com/premium-photo/sack-flour_929907-1730.jpg"),
                CardNode("Lentils", "https://rqn.com.au/wp-content/uploads/2016/05/red-lentil-compress.jpg"),
                CardNode("Beans", "https://www.suttonsbaytrading.com/wp-content/uploads/2013/07/baby-lima-beans.jpg")
            ))
        )),
        CardNode("Sabreen Market", "https://picsum.photos/600/400?random=11", children = listOf(
            CardNode("Fruits", "https://picsum.photos/600/400?random=111", children = listOf(
                CardNode("Apples", "https://media.istockphoto.com/id/164084111/photo/apples.jpg?s=612x612&w=0&k=20&c=eAjYVWWhrEVKMVVvzSjNzFU9dtjh2aH2ypjmclDaUi0="),
                CardNode("Oranges", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiYWJja7ljHmJKufcD2q7NcOunbKZv5Mglj67WpYJ7zSMaHKvfm6Bx2Us&s=10"),
                CardNode("Bananas", "https://www.verywellhealth.com/thmb/F18R53wqIZSm9RWjlqLjbT3Nf34=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/VWH-GettyImages-1650710915-877f191e64f54e168ae7ce99b9aa052b.jpg"),
                CardNode("Mangoes", "https://static.vecteezy.com/system/resources/thumbnails/023/732/154/small/mango-fruit-hanging-on-a-tree-with-a-rustic-wooden-table-free-photo.jpg"),
                CardNode("Grapes", "https://parade.com/.image/c_fill,w_600,h_600,g_faces:center/ODowMDAwMDAwMDAxMTE5ODc0/what-happens-to-your-body-when-you-eat-grapes-every-day.jpg"),
                CardNode("Watermelon", "https://www.chhs.colostate.edu/fsi/wp-content/uploads/sites/51/2024/07/stack-of-watermelon-800x0-c-default.jpg")
            )),
            CardNode("Vegetables", "", children = listOf(
                CardNode("Tomatoes", "https://media.istockphoto.com/id/116027879/photo/cherry-tomatoes.jpg?s=170667a&w=0&k=20&c=U5S3uGtpIRUHlwVZOnXAQ5kSryPu4b8Ohf8iC6qAZ-Q="),
                CardNode("Carrots", ""),
                CardNode("Onions", ""),
                CardNode("Potatoes", ""),
                CardNode("Okra", ""),
                CardNode("Eggplant", "")
            )),
            CardNode("Meat", "https://www.hayesmeats.com/wp-content/uploads/2018/11/test2.jpg", children = listOf(
                CardNode("Beef", ""),
                CardNode("Mutton", ""),
                CardNode("Fish", ""),
                CardNode("Chicken", "")
            )),
            CardNode("Grains", "", children = listOf(
                CardNode("Rice", ""),
                CardNode("Wheat", ""),
                CardNode("Corn", ""),
                CardNode("Flour", ""),
                CardNode("Lentils", ""),
                CardNode("Beans", "")
            ))
        ))
    )),
    CardNode("Al-Jazeera", "https://www.alnilin.com/wp-content/uploads/2023/09/madani_kush-780x470.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
            CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("River Nile", "https://www.sudanakhbar.com/wp-content/uploads/2024/10/445.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Red Sea", "https://i0.wp.com/arabscountries.com/wp-content/uploads/2022/11/The-Red-Sea-1.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
            CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Kassala", "https://saqraljidyanews.com/wp-content/uploads/2020/08/d6e7a4bc43ae7ef56f2bcf0bb6571f6e.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
            CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Sinnar", "https://kushnews.net/wp-content/uploads/2023/09/sinnar_kush.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Northern Sudan", "https://sudanjournal.com/wp-content/uploads/2021/01/a1bd13c5fc813f0c17a64c8d52265180.jpg", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("White Nile", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Blue Nile", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Al-Gadarif", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Southern Kordofan", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Western Kordofan", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
    CardNode("Northern Kordofan", "", children = listOf(
        CardNode("N/A", "", children = listOf(
             CardNode("N/A", "", children = listOf(
                CardNode("N/A", "")
            ))
        ))
    )),
)

private fun getNodeFromPath(path: String): CardNode {
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    var node: CardNode = currentNodeList[indices[0]]
    indices.drop(1).forEach { index ->
        node = currentNodeList[index]
        currentNodeList = node.children
    }
    return node
}

private fun getListFromPath(path: String): List<CardNode> {
    if (path.isEmpty()) return cardTreeData
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    indices.forEach { index ->
        currentNodeList = currentNodeList[index].children
    }
    return currentNodeList
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceContainerHighest) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppMaterialTheme {
                        AppShell()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .width(360.dp)
            .height(760.dp)
            .clip(RoundedCornerShape(48.dp))
            .shadow(20.dp, shape = RoundedCornerShape(48.dp)),
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list/",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("list/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path") ?: ""
                val cards = getListFromPath(path)
                CardList(
                    navController = navController,
                    cards = cards,
                    currentPath = path,
                    contentPadding = innerPadding
                )
            }
            
            composable("detail/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path")!!
                val card = getNodeFromPath(path)
                DetailScreen(
                    navController = navController,
                    card = card,
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@Composable
fun AppMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
                      else dynamicLightColorScheme(LocalContext.current)

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyElevatedCard(title: String, imageUrl: String, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(36.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun CardList(
    navController: NavHostController,
    cards: List<CardNode>,
    currentPath: String,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 100.dp,
            bottom = contentPadding.calculateBottomPadding() + 100.dp,
            start = 0.dp,
            end = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(cards) { index, card ->
            MyElevatedCard(
                title = card.title,
                imageUrl = card.imageUrl,
                onClick = {
                    val newPath = if (currentPath.isEmpty()) "$index" else "${currentPath}_$index"
                    if (card.children.isEmpty()) {
                        navController.navigate("detail/$newPath")
                    } else {
                        navController.navigate("list/$newPath")
                    }
                }
            )
        }
    }
}

@Composable
fun DetailScreen(
    navController: NavHostController,
    card: CardNode,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(card.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = card.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = card.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This is the shared UI screen. This content is the same for all screens, only the title and image above have changed based on the card you clicked.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppShellPreview() {
    AppMaterialTheme {
        AppShell()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkAppShellPreview() {
    AppMaterialTheme(darkTheme = false) {
        AppShell()
    }
}

@Preview
@Composable
fun MyElevatedCardPreview() {
    AppMaterialTheme {
        MyElevatedCard(
            title = "Preview Title",
            imageUrl = "https://picsum.photos/600/400?random=0",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppMaterialTheme {
        DetailScreen(
            navController = rememberNavController(),
            card = cardTreeData[0],
            contentPadding = PaddingValues(0.dp)
        )
    }
}