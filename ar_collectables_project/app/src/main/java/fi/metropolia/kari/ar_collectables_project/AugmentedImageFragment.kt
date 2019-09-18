package fi.metropolia.kari.ar_collectables_project

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class AugmentedImageFragment: ArFragment(){
    private val SAMPLE_IMAGE_DATABASE = "sample_images.imgdb"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        arSceneView.planeRenderer.isEnabled = false
        return view
    }

    override fun getSessionConfiguration(session: Session?): Config {
        val config = super.getSessionConfiguration(session)
        setupAugmentedImageDatabase(config, session)
        return config
    }

    private fun setupAugmentedImageDatabase(config: Config, session: Session?){
        val augmentedImageDb: AugmentedImageDatabase
        val assetManager = context!!.assets

        val inputStream1 = assetManager.open(IMG_01)
        val augmentedImageBitmap1 = BitmapFactory.decodeStream(inputStream1)

        val inputStream2 = assetManager.open(IMG_02)
        val augmentedImageBitmap2 = BitmapFactory.decodeStream(inputStream1)

        val inputStream3 = assetManager.open(IMG_03)
        val augmentedImageBitmap3 = BitmapFactory.decodeStream(inputStream1)

        augmentedImageDb = AugmentedImageDatabase(session)
        augmentedImageDb.addImage(IMG_01, augmentedImageBitmap1)
        augmentedImageDb.addImage(IMG_02, augmentedImageBitmap2)
        augmentedImageDb.addImage(IMG_03, augmentedImageBitmap3)

        config.augmentedImageDatabase = augmentedImageDb
    }
}