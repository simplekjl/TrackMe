package com.simplekjl.trackme.ui.trackimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.simplekjl.trackme.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {

    //    val args: ResultsFragmentArgs by navArgs()
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        binding.distanceValueTextView.text = "0.00"
        binding.timeValueTextView.text = "09.0"

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}