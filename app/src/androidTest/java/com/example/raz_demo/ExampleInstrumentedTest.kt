package com.example.raz_demo
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addingItemsWorksCorrectly() {
        composeTestRule.clearAndSetContent {
            MainActivity().MyApp1()
        }

        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertExists()

        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Name 1 LastName 1").assertExists()
    }

    @Test
    fun removingItemsWorkCorrectly() {
        composeTestRule.clearAndSetContent {
            MainActivity().MyApp1()
        }
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertExists()

        composeTestRule.onNodeWithContentDescription("remove").performClick()
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertDoesNotExist()
    }

    @Test
    fun loadingIsVisibleWhenAddingNewItem() {
        composeTestRule.clearAndSetContent {
            MainActivity().MyApp(users = emptyList(), isLoading = true)
        }

        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithTag("loadingCard").assertExists()
    }
    fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clearAndSetContent(content: @Composable () -> Unit) {
        (this.activity.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0) as? ComposeView)?.setContent(content)
            ?: this.setContent(content)
    }

}