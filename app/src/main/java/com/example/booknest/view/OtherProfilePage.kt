package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.booknest.Model.DummyData
import com.example.booknest.R

@Composable
fun OtherProfilePage(userName:String,userImageResId:Int) {
    Box(modifier= Modifier
        .fillMaxSize()
        .background(Color.Transparent)) {
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))

                .background(Color.Gray)

        ) {

        }
        Column(
            modifier= Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box (
                modifier= Modifier
                    .size(140.dp)

            ){
                Box (
                    modifier= Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(35.dp))
                        .size(140.dp)

                ){
                    Image(
                        painter = painterResource(id= R.drawable.azad), contentDescription = "Profile Image",
                        modifier= Modifier
                            .clip(shape = RoundedCornerShape(35.dp))
                            .size(140.dp)

                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = userName,

                )



            Spacer(modifier = Modifier.height(40.dp))

            // Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Stat(title = "Books Read", value = 35)
                Stat(title = "Medals", value = 5) // Example of the number of medals
                Stat(title = "Friends", value = 128)
            }
            Spacer(modifier= Modifier.height(70.dp))
            Row (modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically){
                ProfileCard(onClick = {}, title = "Books Read")
                ProfileCard(onClick = {}, title = "Medals")
            }
            Spacer(modifier= Modifier.height(20.dp))
            Row (modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically){
                ProfileCard(onClick = {}, title = "Friends")
                ProfileCard(onClick = {}, title = "Challenge")
            }
        }
    }
}