package fi.metropolia.kari.ar_collectables_project

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = supportFragmentManager.findFragmentById(R.id.ar_image_fragment) as ArFragment

        collection_button.setOnClickListener { view ->
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        // Load all models
        for(option in DataManager.options) {
            ModelRenderable.builder()
                .setSource(fragment.context, Uri.parse(option.model))
                .build()
                .thenAccept {
                    DataManager.renderables.put(option, it)
                }
        }

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
                TrackingState.PAUSED -> {if(!DataManager.complete)fit_to_scan.visibility = View.VISIBLE}
                TrackingState.STOPPED -> println("KIKKEL tracking state has stopped, maybe call some functions bud")
                TrackingState.TRACKING -> {
                    var anchors = it.anchors
                    if(anchors.isEmpty()) {
                        var item: Item? = null
                        for(option in DataManager.options) {
                            if (option.tag == it.name) {
                                item = option
                                break
                            }
                        }

                        if (item == null) return
                        fit_to_scan.visibility = View.GONE
                        val pose = it.centerPose
                        val anchor = it.createAnchor(pose)
                        val anchorNode = AnchorNode(anchor)
                        anchorNode.setParent(fragment.arSceneView.scene)


                        val imgNode = TransformableNode(fragment.transformationSystem)
                        imgNode.renderable = DataManager.renderables[item]
                        imgNode.setParent(anchorNode)

                        imgNode.setOnTapListener { hitTestResult, motionEvent ->
                            objectFound(item, imgNode)
                        }
                    }
                }
            }
        }
        }

    private fun objectFound(item: Item, imgNode: TransformableNode){
        DataManager.discovered.put(item, true)
        imgNode.renderable = null
        Toast.makeText(applicationContext, "Discovered \"${item.name}\"", Toast.LENGTH_LONG).show()
        if(DataManager.discovered.size >= 3){DataManager.complete = true}
    }
}
