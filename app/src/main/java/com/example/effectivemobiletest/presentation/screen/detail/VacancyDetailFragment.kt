package com.example.effectivemobiletest.presentation.screen.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.dao.VacancyDao
import com.example.data.local.room.entities.VacancyEntity
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.adapters.QuestionsAdapter
import com.example.effectivemobiletest.databinding.FragmentVacancyDetailBinding
import com.example.effectivemobiletest.utils.hideTabs
import com.example.effectivemobiletest.utils.showTabs
import com.example.shared.models.Vacancy
import com.google.gson.Gson

class VacancyDetailFragment : Fragment() {

    private var vacancy: Vacancy? = null
    lateinit var vacancyDao: VacancyDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vacancyDao = AppDatabase.getInstance(requireContext()).vacancyDao()
        arguments?.let {
            vacancy = Gson().fromJson(it.getString("data"), Vacancy::class.java)
        }
    }

    lateinit var binding: FragmentVacancyDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVacancyDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItems()
    }

    private fun setItems() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
            ivLike.setOnClickListener {
                vacancy?.let {
                    if (vacancyDao.isItemExists(it.id)) {
                        vacancyDao.deleteById(it.id)
                        ivLike.setImageResource(R.drawable.ic_like)
                        ivLike.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    } else {
                        vacancyDao.insert(
                            VacancyEntity(
                                it.id,
                                it.address,
                                it.appliedNumber,
                                it.company,
                                it.description,
                                it.experience,
                                true,
                                it.lookingNumber,
                                it.publishedDate,
                                it.questions,
                                it.responsibilities,
                                it.salary,
                                it.schedules,
                                it.title
                            )
                        )
                        ivLike.setImageResource(R.drawable.ic_favourite)
                        ivLike.imageTintList = ColorStateList.valueOf(Color.parseColor("#2B7EFE"))
                    }
                }
            }
            vacancy?.let {
                setData(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().hideTabs()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().showTabs()
    }


    private fun setData(vacancy: Vacancy) {
        binding.apply {
            tvJob.text = vacancy.title
            tvSalary.text = vacancy.salary.full
            tvExperience.text = vacancy.experience.previewText
            tvJobType.text = vacancy.schedules.toString().replace("[", "").replace("]", "")
            tvAppliedNumber.text = "${vacancy.appliedNumber} человек уже откликнулись"
            tvLookingNumber.text = "${vacancy.lookingNumber} человека сейчас смотрят"
            tvCompany.text = vacancy.company
            tvAddress.text =
                "${vacancy.address.town}, ${vacancy.address.street}, ${vacancy.address.house}"
            tvDescription.text = vacancy.description
            tvResponsibilities.text = vacancy.responsibilities
            rvResponsibilities.adapter = QuestionsAdapter(vacancy.questions)
            if (vacancyDao.isItemExists(vacancy.id)) {
                ivLike.setImageResource(R.drawable.ic_favourite)
                ivLike.imageTintList = ColorStateList.valueOf(Color.parseColor("#2B7EFE"))
            } else {
                ivLike.setImageResource(R.drawable.ic_like)
                ivLike.imageTintList = ColorStateList.valueOf(Color.WHITE)
            }
        }
    }
}