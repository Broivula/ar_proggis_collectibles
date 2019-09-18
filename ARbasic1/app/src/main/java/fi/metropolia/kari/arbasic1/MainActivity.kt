package fi.metropolia.kari.arbasic1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: ArFragment
    private var testRenderable: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment;

        val renderableFuture = ViewRenderable.builder()
            .setView(this, R.layout.rendtext)
            .build()
        renderableFuture.thenAccept {it -> testRenderable = it }

        fragment.setOnTapArPlaneListener {hitResult: HitResult?, plane: Plane?, motionEvent: MotionEvent? ->
                    if (testRenderable == null) {
                        return@setOnTapArPlaneListener
                        }
                    val anchor = hitResult!!.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val viewNode = TransformableNode(fragment.transformationSystem)
                    viewNode.setParent(anchorNode)
                    viewNode.renderable = testRenderable
                    viewNode.select()
                    viewNode.setOnTapListener{hitTestRes: HitTestResult?, motionEv: MotionEvent? ->
                                Toast.makeText(applicationContext, "Ouch!!!!", Toast.LENGTH_SHORT).show()
                            }

                    }
        }
    }
