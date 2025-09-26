package com.aakash.runningapp.ui.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aakash.runningapp.R
import com.aakash.runningapp.ui.theme.LocalDimens
import com.aakash.runningapp.ui.theme.appTypography
import com.aakash.runningapp.ui.theme.backgroundDark
import com.aakash.runningapp.ui.theme.primaryGreen
import com.aakash.runningapp.ui.theme.primaryGreenDark

@Composable
fun DashboardScreen(
    onAddRunClick: () -> Unit,
) {

    val accentGreen = Color(0xFF00E676)

    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    val dashboardState by dashboardViewModel.dashboardState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRunClick,
                containerColor = primaryGreenDark,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Run")
            }
        },
        containerColor = backgroundDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryGreen)
                    .padding(horizontal = LocalDimens.current.large, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                dashboardState.user?.profilePicture?.let { bytes ->
                    val bitmap = remember(bytes) {
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(bitmap)
                            .crossfade(true)
                            .error(R.drawable.ic_run)
                            .build(),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Icon(
                        painter = painterResource(R.drawable.ic_run),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "Run Tracker",
                    style = appTypography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Home",
                    style = appTypography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(Modifier.height(60.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_run), // running icon
                    contentDescription = null,
                    tint = accentGreen,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "No runs recorded yet.",
                    color = accentGreen,
                    style = appTypography.bodyMedium
                )
                Text(
                    text = "Tap the '+' button to add a new run.",
                    color = accentGreen.copy(alpha = 0.7f),
                    style = appTypography.bodySmall
                )
            }
        }
    }
}