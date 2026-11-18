# Build an installable APK

This repository includes `.github/workflows/android-apk.yml` to build a debug APK in GitHub Actions.

1. Create a GitHub repository and upload/push the contents of this project to its `main` branch.
2. Open the repository's **Actions** tab.
3. Select **Build Android APK** and choose **Run workflow** (or wait for the push-triggered build).
4. When the workflow succeeds, open its run and download the `FNAF-Final-Night-debug-apk` artifact.
5. Extract the artifact; it contains `app-debug.apk`. Transfer it to your Android device and install it. Android may ask you to allow installation from that source.

This produces a debug APK for testing, not a Play Store release. A release APK should be signed with a private signing key and must not have its keystore or passwords committed to source control.
