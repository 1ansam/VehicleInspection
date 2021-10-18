package com.yxf.vehicleinspection.view.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yxf.vehicleinspection.view.fragment.*

/**
 *   author:yxf
 *   time:2021/10/18
 */
class ViewPagerAdapter(manager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(manager,lifecycle){
    private val fragments = arrayListOf(
        ExteriorVehicleQueueFragment(),
        ChassisVehicleQueueFragment(),
        DynamicVehicleQueueFragment(),
        InternalQueryVehicleQueueFragment(),
        UniqueCheckVehicleQueueFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}