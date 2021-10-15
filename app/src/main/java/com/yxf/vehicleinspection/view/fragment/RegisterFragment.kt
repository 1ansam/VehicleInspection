package com.yxf.vehicleinspection.view.fragment

import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentRegisterBinding
import com.yxf.vehicleinspection.utils.DialogUtil

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {
    override fun init() {
        val dialog = DialogUtil(this.requireContext())
        dialog.showLoadingDialog()
    }

}