package com.example.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

    @ExperimentalPermissionsApi
    @Composable
    fun HomeScreen(){
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ){
            val context = LocalContext.current
            val storagePermissionState = rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            PermissionRequired(
                permissionState = storagePermissionState,
                permissionNotGrantedContent = {
                    Button(
                        onClick = {
                            storagePermissionState.launchPermissionRequest()
                        }
                    ) { Text("Grant Storage Permission") }
                },
                permissionNotAvailableContent = {
                    Column(
                        Modifier.fillMaxWidth(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ){
                        Text("Storage permission denied.", fontSize = 22.sp, color = Color.Red)
                        Spacer(Modifier.height(10.dp))
                        Text("Open App Setting & Grant Storage Permission.", fontSize = 16.sp)
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.parse("package:${context.packageName}")
                                context.startActivity(intent)
                            }
                        ){
                            Text("Open App Setting")
                        }
                    }
                },
                content = {
                    Column(
                        Modifier.fillMaxWidth(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ){
                        Text("Storage permission Granted", fontSize = 22.sp, color = Color.Green)
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_GET_CONTENT)
                                intent.type = "image/*"
                                context.startActivity(intent)
                            }
                        ){
                            Text("Open App Storage")
                        }
                    }
                }
            )
        }
    }
