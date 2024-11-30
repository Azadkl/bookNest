package com.example.booknest.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.DummyDataGroups
import com.example.booknest.Model.Group
import com.example.booknest.Model.SearchResult
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2
import com.example.booknest.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsPage(navController: NavController) {
    var searchQuery by remember{ mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val dummyData = DummyDataGroups()
    var allGroups by remember { mutableStateOf(dummyData.dummyGroups) }
    val addGroup: (String) -> Unit = { groupName ->
        val newGroup = Group(id = allGroups.size + 1, name = groupName, imageResId = R.drawable.sharp_groups_24)
        allGroups = allGroups + newGroup
    }

    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.Top).padding(top = 15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.padding(bottom = 12.dp).size(35.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(85.dp))
                    Text("Groups Page", fontSize = 25.sp)
                }

            }

        }
        SearchBar(
            modifier = Modifier
                .let { if (active) it.fillMaxWidth() else it.width(273.dp) }
                .border(width = 0.dp, color = Color.Transparent),
            query = searchQuery,
            onQueryChange ={searchQuery=it},
            onSearch ={active=false },
            placeholder = { Text(text = "Search groups")},
            active =active , onActiveChange ={active=it},
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = Color.Black,

                ),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            },
            trailingIcon = {
                if(active){
                    Icon(
                        modifier = Modifier.clickable {
                            if (searchQuery.isNotEmpty()){
                                searchQuery = ""
                            }else{
                                active=false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "CloseIcon"
                    )
                }
            })

        {

            LazyColumn(   modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
                contentPadding = PaddingValues(bottom = 100.dp),) {


            }
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            Text("My Groups",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 25.sp)
            Card (
                modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )){
                LazyColumn (  modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp, end = 10.dp)
                    .size(width = 450.dp, height = 420.dp),
                )
                {
                    items(allGroups){
                            groupName ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(shape = RoundedCornerShape(8.dp))

                                .clickable {  },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =  Color(0xFFFFA500)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.sharp_groups_24),
                                    contentDescription = "User Image",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(48.dp))
                                Text(groupName.name, fontSize = 20.sp)



                            }
                        }


                    }
                }
            }


            Button( onClick = { showBottomSheet = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(end = 25.dp, top = 15.dp).width(175.dp).height(45.dp).align(Alignment.End)
            ) {
                Text("Create group")

            }
        }
    }
    if (showBottomSheet) {
        BottomSheet(onDismiss = { showBottomSheet = false }, onGroupAdded = addGroup)  // showBottomSheet kapanacak
    }

    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit,onGroupAdded: (String) -> Unit){
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val focusManager = LocalFocusManager.current
    val groupNameFocusRequester = remember { FocusRequester() }
    val aboutFocusRequester = remember { FocusRequester() }
    val rulesFocusRequester = remember { FocusRequester() }
    var groupName by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var rules by remember { mutableStateOf("") }

    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) false
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {onDismiss()},
        shape = RoundedCornerShape(0.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ButtonColor1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    "Create Group", style = TextStyle(
                        fontSize = 25.sp
                    ), modifier = Modifier.padding(top = 30.dp)
                )
                OutlinedTextField(
                    value = groupName,
                    placeholder = { Text("Group name") },
                    onValueChange = { groupName = it },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(groupNameFocusRequester),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.sharp_groups_24),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            aboutFocusRequester.requestFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = about,
                    placeholder = { Text("About") },
                    onValueChange = { about = it },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(aboutFocusRequester),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            rulesFocusRequester.requestFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = rules,
                    placeholder = { Text("Rules") },
                    onValueChange = { rules = it },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(rulesFocusRequester),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Gavel,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {

                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )

                Button(
                    onClick = { // Add new group
                        onGroupAdded(groupName)
                        groupName = ""
                        about = ""
                        rules = ""
                        onDismiss() // Close the bottom sheet after adding group
                         },
                    modifier = Modifier.padding(top = 40.dp).size(width = 280.dp, height = 40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(ButtonColor2)

                ) {
                    Text(
                        "Create", style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
        }
    }
}
