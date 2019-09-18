package fi.metropolia.kari.ar_collectables_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: ArFragment
    private var img_01_rend: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = supportFragmentManager.findFragmentById(R.id.ar_image_fragment) as ArFragment

        val renderableFuture = ViewRenderable.builder()
            .setView(this, R.layout.rendtext)
            .build()
        renderableFuture.thenAccept {it -> img_01_rend = it }

            fragment.arSceneView.scene.addOnUpdateListener { frameTime ->
                onUpdate(frameTime)
            }
        }


    private fun onUpdate(frameTime: FrameTime){

        fragment.onUpdate(frameTime)
        val arFrame = fragment.arSceneView.arFrame
        if(arFrame == null || arFrame.camera.trackingState != TrackingState.TRACKING){
            println("not working bud")
            return
            }

        val updatedAugmentedImages = arFrame.getUpdatedTrackables(AugmentedImage::class.java)
        updatedAugmentedImages.forEach {
            when(it.trackingState){
                TrackingState.PAUSED -> println("tracking state paused, yo. fill something in here later if necessary")
                TrackingState.STOPPED -> println("tracking state has stopped, maybe call some functions bud")
                TrackingState.TRACKING -> {
                    var anchors = it.anchors
                    if(anchors.isEmpty()){
                        fit_to_scan.visibility = View.GONE
                        val pose = it.centerPose
                        val anchor = it.createAnchor(pose)
                        val anchorNode = AnchorNode(anchor)
                        anchorNode.setParent(fragment.arSceneView.scene)
                        val imgNode = TransformableNode(fragment.transformationSystem)
                        imgNode.setParent(anchorNode)

                        //fill in all the cases here, with the different pehmolelus
                        imgNode.renderable = when(it.name){
                            IMG_01 -> img_01_rend
                            else -> null
                        }
                    }
                }
            }
        }
        }
    }
