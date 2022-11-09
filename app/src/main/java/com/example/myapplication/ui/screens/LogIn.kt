package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.Button1
import com.example.myapplication.ui.screens.BackgroundImage
import com.example.myapplication.ui.theme.Black
import com.example.myapplication.ui.theme.Green
import com.example.myapplication.ui.theme.Purple500


@Composable
fun LogIn(){
    var passWord by remember { mutableStateOf(TextFieldValue("")) }

    Box{
        BackgroundImage(painter = painterResource(id = R.drawable.log_in_photo))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.fillMaxWidth())
                {
                        Button(onClick = { /*TODO*/ }, shape = CircleShape, colors = ButtonDefaults.buttonColors(backgroundColor = Green), modifier = Modifier.padding(start = 18.dp, top = 15.dp))
                        {
                            Icon(Icons.Default.ArrowBack ,contentDescription = "content description", tint= Color.Black)
                        }
                        Image(

                            painter = painterResource(R.drawable.delta_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(1.5F)
                                .padding(top = 50.dp, start = 120.dp)
                        )

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.padding(top = 100.dp, start = 20.dp)) {
                    EmailTextField(onTextChange = {/*TODO*/})
                    Spacer(modifier = Modifier.height(20.dp))
                    PasswordTextField(onTextChange = {})
                    Spacer(modifier = Modifier.height(20.dp))
                   LinkedText(handler = {/*TODO*/})
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button1(fontSize = 35, text = "Log In")
                }
            }
        }

    }



}
@Composable
fun EmailTextField(onTextChange :(TextFieldValue) -> Unit){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    TextField( value = email,
        onValueChange = { newText ->
            email = newText
            onTextChange(newText);
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor =  Color.Transparent),
        label = {
            Text(text = "Email")
        },
        textStyle = TextStyle.Default.copy(fontSize = 15.sp)
    )
}
@Composable
fun PasswordTextField(onTextChange :(TextFieldValue) -> Unit){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    TextField( value = email,
        onValueChange = { newText ->
            email = newText
            onTextChange(newText);
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor =  Color.Transparent),
        label = {
            Text(text = "Password")
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icon(Icons.Default.Lock,contentDescription = "content description", tint= Color.Black)
            else  Icon(Icons.Default.Lock,contentDescription = "content description", tint= Color.Black)


            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(Icons.Default.Lock,contentDescription = "content description", tint= Color.Black)
            }
        },
        textStyle = TextStyle.Default.copy(fontSize = 15.sp)
    )
}
@Composable
fun LinkedText(handler : (Int) -> Unit){
    val mAnnotatedLinkString = buildAnnotatedString {
        // creating a string to display in the Text
        val mStr = "Forgot password?"
        append(mStr)
        addStyle(
            style = SpanStyle(
                color = Color.White,
                textDecoration = TextDecoration.Underline
            ), start = 0, end = mStr.length
        )

    }
    ClickableText(text = mAnnotatedLinkString, onClick = handler)
}

@Preview
@Composable
fun LogInPreview() {
    LogIn();
}