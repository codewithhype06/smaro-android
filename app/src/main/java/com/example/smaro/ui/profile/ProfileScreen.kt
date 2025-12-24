package com.example.smaro.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.smaro.R
import com.example.smaro.ui.profile.add.*
import com.example.smaro.viewmodel.ProfileViewModel

private const val PROFILE_IMAGE_SIZE = 120
private const val EDIT_ICON_SIZE = 30

@Composable
fun ProfileScreen(
    onBuildResume: () -> Unit,
    onOpenDeveloper: () -> Unit,          // ✅ NEW
    viewModel: ProfileViewModel = viewModel()
) {
    var showAddSkill by remember { mutableStateOf(false) }
    var showAddProject by remember { mutableStateOf(false) }
    var showAddInternship by remember { mutableStateOf(false) }
    var showAddCertificate by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        /* ---------------- PROFILE CARD ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1C1C1E)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "Nikhil Kumar",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "nikhil@email.com",
                            color = Color.White.copy(alpha = 0.75f)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "UIET, CSJMU · B.Tech IT",
                            color = Color.White.copy(alpha = 0.7f)
                        )

                        Text(
                            text = "+91 90000 00000",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    Box(
                        modifier = Modifier.size(PROFILE_IMAGE_SIZE.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.profile_placeholder),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )

                        Surface(
                            modifier = Modifier
                                .size(EDIT_ICON_SIZE.dp)
                                .align(Alignment.BottomEnd)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { },
                            shape = CircleShape,
                            color = Color(0xFF22D3EE),
                            shadowElevation = 4.dp
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit profile picture",
                                tint = Color.Black,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }
        }

        /* ---------------- RESUME BUILDER ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF181818)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "Resume Builder",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "Select role → Get instant resume",
                            color = Color.White.copy(alpha = 0.75f)
                        )

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = onBuildResume,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Build Resume")
                        }
                    }

                    val resumeComposition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.resume)
                    )

                    LottieAnimation(
                        composition = resumeComposition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        }

        /* ---------------- CREDENTIALS ⭐ ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF181818)
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Column {
                        Text(
                            text = "Credentials ⭐",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Your skills, work & achievements",
                            color = Color.White.copy(alpha = 0.65f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    CredentialItem(
                        icon = R.drawable.skillicon,
                        title = "Skills",
                        subtitle = "Add technical & soft skills you know"
                    ) { showAddSkill = true }

                    CredentialItem(
                        icon = R.drawable.projecticon,
                        title = "Projects",
                        subtitle = "Highlight real work & personal projects"
                    ) { showAddProject = true }

                    CredentialItem(
                        icon = R.drawable.internshipicon,
                        title = "Internships",
                        subtitle = "Add industry exposure & training"
                    ) { showAddInternship = true }

                    CredentialItem(
                        icon = R.drawable.certificateicon,
                        title = "Certificates",
                        subtitle = "Courses & achievements you earned"
                    ) { showAddCertificate = true }
                }
            }
        }

        /* ---------------- ABOUT THE DEVELOPER ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF181818)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "About the Developer",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFD166),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Know the mind and vision behind Smaro 🚀🧠",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    val devComposition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.developer)
                    )

                    LottieAnimation(
                        composition = devComposition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onOpenDeveloper()   // ✅ NAVIGATION HERE
                            }
                    )

                    Text(
                        text = "Tap the animation 👆 to know more about the developer",
                        color = Color.White.copy(alpha = 0.65f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        item { Spacer(Modifier.height(40.dp)) }
    }

    /* ---------------- DIALOGS ---------------- */

    if (showAddSkill) {
        AddSkillDialog(
            onDismiss = { showAddSkill = false },
            onSave = { viewModel.addSkill(it) }
        )
    }

    if (showAddProject) {
        AddProjectDialog(
            onDismiss = { showAddProject = false },
            onSave = { viewModel.addProject(it) }
        )
    }

    if (showAddInternship) {
        AddInternshipDialog(
            onDismiss = { showAddInternship = false },
            onSave = { viewModel.addInternship(it) }
        )
    }

    if (showAddCertificate) {
        AddCertificateDialog(
            onDismiss = { showAddCertificate = false },
            onSave = { viewModel.addCertificate(it) }
        )
    }
}

/* ---------------- CHILD ITEM ---------------- */

@Composable
private fun CredentialItem(
    icon: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A3441)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = "→",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22D3EE)
            )
        }
    }
}
