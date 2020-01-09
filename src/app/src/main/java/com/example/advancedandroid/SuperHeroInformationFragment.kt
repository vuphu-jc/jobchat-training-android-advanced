package com.example.advancedandroid


/**
 * A simple [Fragment] subclass.
 */
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_super_hero_infomation.*


class SuperHeroInformationFragment: Fragment() {

    lateinit var data: SuperHero

    companion object {
        fun getInstance(data: SuperHero): SuperHeroInformationFragment {
            val fragment = SuperHeroInformationFragment()
            fragment.data = data
            fragment.retainInstance = true
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_super_hero_infomation, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = data.name
        Utils.setSourceForImageView(context as Context, imgImage, data.imageUri)
        tvDescription.text = data.description
        btnDetail.setOnClickListener {
            val browserIntent: Intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(data.url)
            )
            context?.startActivity(browserIntent)
        }
    }
}
