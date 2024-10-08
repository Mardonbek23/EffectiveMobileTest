package com.example.effectivemobiletest.presentation.screen.tabs.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.dao.VacancyDao
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.adapters.VacanciesAdapter
import com.example.effectivemobiletest.databinding.FragmentSavedBinding
import com.example.effectivemobiletest.utils.navOptions
import com.example.effectivemobiletest.viewmodel.VacancyViewModel
import com.example.shared.models.Vacancy
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

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
            }, true)
    }

    lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItems()
    }

    private fun setItems() {
        binding.apply {
            rvVacancies.adapter = vacanciesAdapter
            vacancyViewModel.getSavedVacancies.observe(viewLifecycleOwner) { data ->
                val vacancies: List<Vacancy> = data.map { vacancyEntity ->
                    vacancyEntity.entityToVacancy()
                }
                vacanciesAdapter.setData(vacancies)
            }
        }
    }

}