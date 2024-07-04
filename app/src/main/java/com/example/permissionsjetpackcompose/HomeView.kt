package com.example.permissionsjetpackcompose

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun HomeView(viewModel: PermissionViewModel) {
    Column {
        HeaderCustomer()
        Spacer(modifier = Modifier.size(48.dp))
        ScrollableContent(viewModel = viewModel)
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
fun PermissionCustomer(
    name: String,
    description: String,
    status: String,
    checkedState: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
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
            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    onCheckedChange(it)
                }
            )
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun ScrollableContent(viewModel: PermissionViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val permissionLaunchers = viewModel.requiredPermissions.associateWith { permission ->
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            viewModel.updatePermissionStatus(permission, granted)
        }
    }
    val permissionState = viewModel.checkPermissions(context)

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        permissionState.forEach { (permission, granted) ->
            val permissionName = when (permission) {
                Manifest.permission.ACCESS_COARSE_LOCATION -> stringResource(id = R.string.permission_nameLocation)
                Manifest.permission.CAMERA -> stringResource(id = R.string.permission_nameCamera)
                Manifest.permission.RECORD_AUDIO -> stringResource(id = R.string.permission_nameMicrophone)
                Manifest.permission.READ_EXTERNAL_STORAGE -> stringResource(id = R.string.permission_nameReadMemory)
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> stringResource(id = R.string.permission_nameWriteMemory)
                Manifest.permission.READ_CONTACTS -> stringResource(id = R.string.permission_nameContacts)
                else -> "Unknown"
            }

            val permissionDescription = when (permission) {
                Manifest.permission.ACCESS_COARSE_LOCATION -> stringResource(id = R.string.permission_descriptionLocation)
                Manifest.permission.CAMERA -> stringResource(id = R.string.permission_descriptionCamera)
                Manifest.permission.RECORD_AUDIO -> stringResource(id = R.string.permission_descriptionMicrophone)
                Manifest.permission.READ_EXTERNAL_STORAGE -> stringResource(id = R.string.permission_descriptionReadMemory)
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> stringResource(id = R.string.permission_descriptionWriteMemory)
                Manifest.permission.READ_CONTACTS -> stringResource(id = R.string.permission_descriptionContacts)
                else -> "No description available"
            }

            val permissionStatus =
                viewModel.permissionStatus[permission] ?: "Permission not requested"
            val checkedState = remember { mutableStateOf(granted) }

            PermissionCustomer(
                name = permissionName,
                description = permissionDescription,
                status = permissionStatus,
                checkedState = checkedState,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        permissionLaunchers[permission]?.launch(permission)
                    }
                }
            )
        }
    }
}