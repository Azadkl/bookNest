package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booknest.Book
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1

@Composable
fun MyBooksPage(modifier: Modifier=Modifier) {

    var search by remember { mutableStateOf("") }


    Column (modifier= Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        OutlinedTextField(
            value = search,
            placeholder = { Text("Search my books",modifier=Modifier.align(Alignment.CenterHorizontally)) },
            onValueChange = { search = it },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .size(width = 320.dp, height = 56.dp)
                .border(width = 0.dp, color = Color.Transparent)
                .focusable(),
            shape = RoundedCornerShape(20.dp),

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.DarkGray,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),

        )



        Column (modifier=Modifier.fillMaxHeight().fillMaxWidth().padding(start = 15.dp),
            horizontalAlignment = Alignment.Start){
            Row (modifier=Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                BookCard(onClick = {})
                Text(text="Books I've Read",modifier=Modifier.padding(end = 50.dp), style = TextStyle(fontSize = 25.sp))
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Divider(
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(end = 30.dp),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row (modifier=Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                BookCard(onClick = {})
                Text(text="Books I Want to Read", modifier = Modifier.padding(end = 20.dp),style = TextStyle(fontSize = 25.sp))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Divider(
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(end = 30.dp),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row (modifier=Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                BookCard(onClick = {})
                Text(text="Currently Reading", modifier=Modifier.padding(end = 30.dp),style = TextStyle(fontSize = 25.sp))
            }
        }





}}

@Composable
fun BookCard(onClick:()-> Unit){
        Card(
            modifier = Modifier
                .width(130.dp)
                .height(190.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(30.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,) {
                Image(
                    painter = painterResource(R.drawable.loginimage),
                    contentDescription = "Book Cover Image",

                )

            }
        }
}


