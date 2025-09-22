
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mobile.nativeandroidapis.qr_code.QRCodeAnalyzer
import com.mobile.nativeandroidapis.router.Navigator

@Composable
fun CameraPreview(
    modifier: Modifier,
    accountDetails: (String) -> Unit, onFailed: () -> Unit,
    navigator: Navigator
) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.Builder().build()

            preview.setSurfaceProvider(previewView.surfaceProvider)

            val imageAnalysis = ImageAnalysis.Builder().build()

            try {
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QRCodeAnalyzer(
                        urlCallback = { url ->
                            accountDetails(url)
                        },
                        onFailed = {
//                            navigator.goBack()
                            onFailed()
                        },
                        onFailedMessage = {}
                    )
                )
            } catch (e: Error) {
                navigator.navigateBack()
            }

            ProcessCameraProvider.getInstance(context).get().bindToLifecycle(
                lifeCycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )

            previewView
        }
    )
}
