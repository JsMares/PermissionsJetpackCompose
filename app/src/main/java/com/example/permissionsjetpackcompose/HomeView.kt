package com.example.permissionsjetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun HomeView() {
    Column {
        HeaderCustomer()
        Spacer(modifier = Modifier.size(48.dp))
        ScrollableContent()
    }
}

@Composable
fun HeaderCustomer() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.shield),
            contentDescription = "Icon app",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = R.string.header_welcome),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.header_indication),
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 16.sp,
            color = Color.Black,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PermissionCustomer(name: String, description: String, status: String) {
    val checkedState = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(
            text = description,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = status,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 12.dp)
            )
            Switch(checked = checkedState.value, onCheckedChange = { checkedState.value = it })
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun ScrollableContent() {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameLocation),
            description = stringResource(id = R.string.permission_descriptionLocation),
            status = stringResource(id = R.string.permission_statusLocation)
        )
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameCamera),
            description = stringResource(id = R.string.permission_descriptionCamera),
            status = stringResource(id = R.string.permission_statusCamera)
        )
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameMicrophone),
            description = stringResource(id = R.string.permission_descriptionMicrophone),
            status = stringResource(id = R.string.permission_statusMicrophone)
        )
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameReadMemory),
            description = stringResource(id = R.string.permission_descriptionReadMemory),
            status = stringResource(id = R.string.permission_statusReadMemory)
        )
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameWriteMemory),
            description = stringResource(id = R.string.permission_descriptionWriteMemory),
            status = stringResource(id = R.string.permission_statusWriteMemory)
        )
        PermissionCustomer(
            name = stringResource(id = R.string.permission_nameContacts),
            description = stringResource(id = R.string.permission_descriptionContacts),
            status = stringResource(id = R.string.permission_statusContacts)
        )
    }
}