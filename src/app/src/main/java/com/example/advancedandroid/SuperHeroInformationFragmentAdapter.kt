package com.example.advancedandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class SuperHeroInformationFragmentAdapter
    (private val context: Context,
        fragmentManager: FragmentManager,
        repository: SuperHeroRepository): FragmentPagerAdapter(fragmentManager) {

    private var data: MutableList<SuperHero> = repository.getAll()

    fun initializeTab(tabs: TabLayout, callback: (String,String,String)->Unit) {
        for (i in 0 until tabs.tabCount) {
            val customView = CustomTab(context)
            tabs.getTabAt(i)?.customView = customView
            customView.setFirstLetter(data[i].name[0])
        }

        val selectTab: (Int) -> Unit = {
            position: Int ->
                callback.invoke(getName( position - 1), getName(position), getName(position + 1))
        }

        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                (p0?.customView as CustomTab).unselected()
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                (p0?.customView as CustomTab).selected()
                selectTab(p0?.position)
            }
        })

        (tabs.getTabAt(0)?.customView as CustomTab).selected()
        selectTab(0)
    }

    private fun getName(position: Int) : String {
        if (position < 0 || position >= data.size)
            return ""
        return data[position].name
    }

    override fun getItem(position: Int): Fragment {
        return SuperHeroInformationFragment.getInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }
}