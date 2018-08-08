package com.example.keviniswara.bookinglapang.user.home.view

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentUserFindEnemyMainBinding

class FindEnemyMainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mBinding: FragmentUserFindEnemyMainBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_user_find_enemy_main, container, false)

        val view: View = mBinding.root

        var parentHeight = 0
        var buttonHeight = 0
        var parentWidth = 0
        var buttonWidth = 0

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                parentHeight = view.height
                buttonHeight = mBinding.buttonMakeGame.height
                parentWidth = view.width
                buttonWidth = mBinding.buttonMakeGame.width

                val buttonCariLapanganY: Float = ((parentHeight - buttonHeight * 13 / 8) / 2).toFloat()

                val buttonCariLawanY: Float = (buttonCariLapanganY + buttonHeight * 3 / 4)

                val buttonCariLapanganX: Float = ((parentWidth / 2 - buttonWidth * 5 / 6)).toFloat()

                val buttonCariLawanX: Float = (buttonCariLapanganX + buttonWidth * 2 / 3)

                mBinding.buttonMakeGame.y = buttonCariLapanganY

                mBinding.buttonJoinGame.y = buttonCariLawanY

                mBinding.buttonMakeGame.x = buttonCariLapanganX

                mBinding.buttonJoinGame.x = buttonCariLawanX

                if (parentHeight > 0 && buttonHeight > 0) {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })

        mBinding.buttonMakeGame.setOnClickListener({
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.content, MakeGameFragment()).addToBackStack(MakeGameFragment()
                    .javaClass.simpleName)
            ft.commit()
        })
        mBinding.buttonJoinGame.setOnClickListener({
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.content, JoinGameFragment()).addToBackStack(JoinGameFragment()
                    .javaClass.simpleName)
            ft.commit()
        })
        return view
    }
}