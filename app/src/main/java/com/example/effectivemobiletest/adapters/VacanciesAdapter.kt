package com.example.effectivemobiletest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.dao.VacancyDao
import com.example.data.local.room.entities.VacancyEntity
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ItemVacancyBinding
import com.example.effectivemobiletest.utils.hide
import com.example.shared.models.Vacancy


class VacanciesAdapter(
    var list: ArrayList<Vacancy>,
    var vacancyDao: VacancyDao,
    var listener: OnClickListeners,
    var showAllVacancies: Boolean = false
) :
    RecyclerView.Adapter<VacanciesAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onClick(vacancy: Vacancy, position: Int) {
            binding.apply {
                ivLike.setOnClickListener {
                    if (vacancy.isFavorite) {
                        vacancyDao.deleteById(vacancy.id)
                        list[position].isFavorite = false
                        notifyItemChanged(position)
                    } else {
                        vacancyDao.insert(
                            VacancyEntity(
                                vacancy.id,
                                vacancy.address,
                                vacancy.appliedNumber,
                                vacancy.company,
                                vacancy.description,
                                vacancy.experience,
                                true,
                                vacancy.lookingNumber,
                                vacancy.publishedDate,
                                vacancy.questions,
                                vacancy.responsibilities,
                                vacancy.salary,
                                vacancy.schedules,
                                vacancy.title
                            )
                        )
                        list[position].isFavorite = true
                        notifyItemChanged(position)
                    }
                }
                root.setOnClickListener {
                    listener.onClick(vacancy)
                }
            }

        }

        fun onBind(vacancy: Vacancy) {
            binding.apply {
                tvApplicationReviewCount.text =
                    "Сейчас просматривает ${vacancy.lookingNumber} человек"
                vacancy.isFavorite =
                    vacancyDao.isItemExists(vacancy.id)
                if (vacancy.isFavorite) {
                    ivLike.setImageResource(R.drawable.ic_favourite)
                } else {
                    ivLike.setImageResource(R.drawable.ic_like)
                }
                tvJob.text = vacancy.title
                tvAddress.text = vacancy.address.town
                tvCompany.text = vacancy.company
                tvExperience.text = vacancy.experience.previewText
                tvDate.text = "Опубликовано ${vacancy.publishedDate}"
                if (vacancy.salary.short != null) {
                    tvSalary.text = vacancy.salary.short
                } else tvSalary.hide()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemVacancyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onClick(list[position], position)
        holder.onBind(list[position])
    }

    fun showMore() {
        showAllVacancies = true
        notifyDataSetChanged()
    }

    fun setData(list: List<Vacancy>) {
        this.list = list as ArrayList<Vacancy>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (!showAllVacancies && list.size > 3) 3
        else list.size
    }

    interface OnClickListeners {
        fun onClick(vacancy: Vacancy)
    }
}