package com.example.effectivemobiletest.presentation.screen.tabs.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.dao.VacancyDao
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.adapters.VacanciesAdapter
import com.example.effectivemobiletest.databinding.FragmentSearchBinding
import com.example.effectivemobiletest.utils.RequestState
import com.example.effectivemobiletest.utils.hide
import com.example.effectivemobiletest.utils.navOptions
import com.example.effectivemobiletest.utils.show
import com.example.effectivemobiletest.viewmodel.VacancyViewModel
import com.example.shared.models.Vacancy
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val vacancyViewModel: VacancyViewModel by viewModels()
    lateinit var vacanciesAdapter: VacanciesAdapter
    lateinit var vacancyDao: VacancyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vacancyDao = AppDatabase.getInstance(requireContext()).vacancyDao()
        setAdapters()
    }

    private fun setAdapters() {
        vacanciesAdapter =
            VacanciesAdapter(arrayListOf(), vacancyDao, object : VacanciesAdapter.OnClickListeners {
                override fun onClick(vacancy: Vacancy) {
                    val bundle = Bundle()
                    bundle.putString("data", Gson().toJson(vacancy))
                    findNavController().navigate(R.id.vacancyDetailFragment, bundle, navOptions())
                }
            })
        vacancyViewModel.getVacancies()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItems()
        setButtons()
    }

    private fun setButtons() {
        binding.apply {
            btnFurtherVacancies.setOnClickListener {
                vacanciesAdapter.showMore()
            }
        }
    }

    private fun setItems() {
        binding.apply {
            rvVacancies.adapter = vacanciesAdapter
            vacancyViewModel.vacancies.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestState.Loading -> {
                        progress.show()
                    }

                    is RequestState.Error -> {
                        progress.hide()
                    }

                    is RequestState.Success -> {
                        progress.hide()
                        vacanciesAdapter.setData(it.data ?: arrayListOf())
                        btnFurtherVacancies.show()
                    }
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}