package com.example.basictransitionkotlin

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_basic_transition.*

class BasicTransitionFragment : Fragment(), RadioGroup.OnCheckedChangeListener  {
    // We transition between these Scenes
    private lateinit var mScene1: Scene
    private lateinit var mScene2: Scene
    private lateinit var mScene3: Scene

    /** A custom TransitionManager */
    private lateinit var mTransitionManagerForScene3 : TransitionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basic_transition, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mScene1 = Scene(scene_root, scene_root.findViewById(R.id.container))
        mScene2 = Scene.getSceneForLayout(scene_root, R.layout.scene2, activity)
        mScene3 = Scene.getSceneForLayout(scene_root, R.layout.scene3, activity)
        mTransitionManagerForScene3 = TransitionInflater.from(activity)
            .inflateTransitionManager(R.transition.scene3_transition_manager, scene_root)
        select_scene.setOnCheckedChangeListener(this)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.select_scene_1 -> {
                // BEGIN_INCLUDE(transition_simple)
                // You can start an automatic transition with TransitionManager.go().
                TransitionManager.go(mScene1)
            }
            R.id.select_scene_2 -> {
                TransitionManager.go(mScene2)
            }
            R.id.select_scene_3 -> {
                // BEGIN_INCLUDE(transition_custom)
                // You can also start a transition with a custom TransitionManager.
                mTransitionManagerForScene3.transitionTo(mScene3)
            }
            R.id.select_scene_4 -> {
                // BEGIN_INCLUDE(transition_dynamic)
                // Alternatively, transition can be invoked dynamically without a Scene.
                // For this, we first call TransitionManager.beginDelayedTransition().
                TransitionManager.beginDelayedTransition(scene_root)
                // Then, we can just change view properties as usual.
                val square =
                    scene_root.findViewById<View>(R.id.transition_square)
                val params = square.layoutParams
                val newSize =
                    resources.getDimensionPixelSize(R.dimen.square_size_expanded)
                params.width = newSize
                params.height = newSize
                square.layoutParams = params
            }
        }
    }

}
