package com.untilled.roadcapture.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.features.comment.CommentFragment
import com.untilled.roadcapture.features.comment.CommentFragmentDirections
import com.untilled.roadcapture.features.follower.FollowersFragment
import com.untilled.roadcapture.features.follower.FollowersFragmentDirections
import com.untilled.roadcapture.features.following.FollowingsFragment
import com.untilled.roadcapture.features.following.FollowingsFragmentDirections
import com.untilled.roadcapture.features.login.LoginEmailFragment
import com.untilled.roadcapture.features.login.LoginEmailFragmentDirections
import com.untilled.roadcapture.features.login.LoginFragment
import com.untilled.roadcapture.features.login.LoginFragmentDirections
import com.untilled.roadcapture.features.picture.CommentBottomSheetDialog
import com.untilled.roadcapture.features.picture.PictureViewerFragment
import com.untilled.roadcapture.features.picture.PictureViewerFragmentDirections
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.features.root.capture.*
import com.untilled.roadcapture.features.settings.SettingsAccountFragment
import com.untilled.roadcapture.features.settings.SettingsAccountFragmentDirections
import com.untilled.roadcapture.features.settings.SettingsFragment
import com.untilled.roadcapture.features.settings.SettingsFragmentDirections
import com.untilled.roadcapture.features.signup.*
import com.untilled.roadcapture.features.studio.StudioFragment
import com.untilled.roadcapture.features.studio.StudioFragmentDirections

fun View.navigate(navDirections: NavDirections) {
    Navigation.findNavController(this).navigate(navDirections)
}

fun RootFragment.navigateToCapture() {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToCaptureFragment())
}

fun RootFragment.navigateToNotification() {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToNotificationFragment())
}

fun RootFragment.navigateToStudio(id: Long) {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToStudioFragment(id))
}

fun RootFragment.navigateToComment(id: Long, commentCount: Int) {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToCommentFragment(id,commentCount))
}

fun RootFragment.navigateToPictureViewer(id: Long) {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment(id))
}

fun RootFragment.navigateToFollower(id: Long) {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToFollowersFragment(id))
}

fun RootFragment.navigateToFollowing(id: Long) {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToFollowingsFragment(id))
}

fun RootFragment.navigateToSettings() {
    binding.root.navigate(RootFragmentDirections.actionRootFragmentToSettingsFragment())
}

fun CaptureFragment.navigateToCrop(imageUri: String) {
    binding.root.navigate(CaptureFragmentDirections.actionCaptureFragmentToCropFragment(imageUri))
}

fun CaptureFragment.navigateToCamera() {
    binding.root.navigate(CaptureFragmentDirections.actionCaptureFragmentToCameraFragment())
}

fun CaptureFragment.navigateToAlbumRegistration(picture: Picture?) {
    binding.root.navigate(CaptureFragmentDirections.actionCaptureFragmentToAlbumRegestrationFragment(picture))
}

fun CaptureFragment.navigateToPictureEditor(picture: Picture?) {
    binding.root.navigate(CaptureFragmentDirections.actionCaptureFragmentToPictureEditorFragment(picture))
}

fun CropFragment.navigateToPictureEditor(picture: Picture?) {
    binding.root.navigate(CropFragmentDirections.actionCropFragmentToPictureEditorFragment(picture))
}

fun PictureEditorFragment.navigateToSearchPlace(picture: Picture?) {
    binding.root.navigate(PictureEditorFragmentDirections.actionPictureEditorFragmentToSearchPlaceFragment(picture))
}

fun PictureEditorFragment.navigateToCapture() {
    binding.root.navigate(PictureEditorFragmentDirections.actionPictureEditorFragmentToCaptureFragment())
}

fun PlaceSearchFragment.navigateToCapture(picture: Picture?) {
    binding.root.navigate(PlaceSearchFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(picture))
}

fun PlaceSearchFragment.navigateToPictureEditor(picture: Picture?) {
    binding.root.navigate(PlaceSearchFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(picture))
}

fun SettingsAccountFragment.navigateToUsernameSetting() {
    binding.root.navigate(SettingsAccountFragmentDirections.actionAccountSettingFragmentToUsernameSettingFragment())
}

fun SettingsAccountFragment.navigateToPasswordSetting() {
    binding.root.navigate(SettingsAccountFragmentDirections.actionAccountSettingFragmentToPasswordSettingFragment())
}

fun SettingsAccountFragment.navigateToServiceWithdrawal() {
    binding.root.navigate(SettingsAccountFragmentDirections.actionAccountSettingFragmentToServiceWithdrawalFragment())
}

fun SettingsFragment.navigateToAccountSetting() {
    binding.root.navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountSettingFragment())
}

fun SignupEmailFragment.navigateToSignupPassword() {
    binding.root.navigate(SignupEmailFragmentDirections.actionSignupEmailFragmentToSignupPasswordFragment())
}

fun SignupPasswordFragment.navigateToSignupUsername() {
    binding.root.navigate(SignupPasswordFragmentDirections.actionSignupPasswordFragmentToSignupUsernameFragment())
}

fun SignupFragment.navigateToTermsOfService() {
    binding.root.navigate(SignupFragmentDirections.actionSignupFragmentToTermsOfServiceFragment())
}

fun SignupFragment.navigateToRoot() {
    binding.root.navigate(SignupFragmentDirections.actionSignupFragmentToRootFragment())
}

fun StudioFragment.navigateToFollower(id: Long) {
    binding.root.navigate(StudioFragmentDirections.actionStudioFragmentToFollowersFragment(id))
}

fun StudioFragment.navigateToFollowing(id: Long) {
    binding.root.navigate(StudioFragmentDirections.actionStudioFragmentToFollowingsFragment(id))
}

fun LoginFragment.navigateToEmailLogin() {
    binding.root.navigate(LoginFragmentDirections.actionLoginFragmentToEmailLoginFragment())
}

fun LoginFragment.navigateToSignup() {
    binding.root.navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
}

fun LoginFragment.navigateToRoot() {
    binding.root.navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
}

fun LoginEmailFragment.navigateToRoot() {
    binding.root.navigate(LoginEmailFragmentDirections.actionEmailLoginFragmentToRootFragment())
}

fun LoginEmailFragment.navigateToPasswordFind() {
    binding.root.navigate(LoginEmailFragmentDirections.actionEmailLoginFragmentToPasswordFindFragment())
}

fun CommentFragment.navigateToStudio(id: Long) {
    binding.root.navigate(CommentFragmentDirections.actionCommentFragmentToStudioFragment(id))
}

fun FollowersFragment.navigateToStudio(id: Long) {
    binding.root.navigate(FollowersFragmentDirections.actionFollowersFragmentToStudioFragment(id))
}

fun FollowingsFragment.navigateToStudio(id: Long) {
    binding.root.navigate(FollowingsFragmentDirections.actionFollowingsFragmentToStudioFragment(id))
}

fun CameraFragment.navigateToCropFragment(imageUri: String) {
    binding.root.navigate(CameraFragmentDirections.actionCameraFragmentToCropFragment(imageUri))
}

fun PictureViewerFragment.navigateToStudio(id: Long) {
    binding.root.navigate(PictureViewerFragmentDirections.actionPictureViewerContainerFragmentToStudioFragment(id))
}

fun Fragment.navigateToLogin() {
    findNavController().navigate(R.id.action_global_loginFragment)
}