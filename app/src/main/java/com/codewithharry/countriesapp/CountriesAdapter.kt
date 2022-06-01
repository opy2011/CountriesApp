package com.codewithharry.countriesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codewithharry.countriesapp.api.Country
import com.codewithharry.countriesapp.pojos.Flags
import com.codewithharry.countriesapp.roomhelpers.CountryEntity


class CountriesAdapter(private val mContext: Context):
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>(){

    private var countriesList: List<Country>? = null
    private var countryEntityList: List<CountryEntity>? = null
    private var ONLINE_TOKEN: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false)
        return CountriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        if(ONLINE_TOKEN == 1) onBindOnline(holder,position)
        else onBindOffline(holder,position)
    }

    override fun getItemCount(): Int {
        if(ONLINE_TOKEN == 1) {
            if(countriesList != null) return countriesList!!.size
            return 0
        }
        else {
            if(countryEntityList != null) return countryEntityList!!.size
            return 0
        }
    }

    fun setCountryList(countries: List<Country>?) {
        ONLINE_TOKEN = 1
        countriesList = countries
        notifyDataSetChanged()
    }

    fun setCountryEntityList(countries: List<CountryEntity>) {
        ONLINE_TOKEN = 0
        countryEntityList = countries
        notifyDataSetChanged()
    }

    fun onBindOnline(holder: CountriesViewHolder, position: Int) {
        val country: Country? = countriesList?.get(position)

        val name: String? = country?.name?.common
        val capital: ArrayList<String>? = country?.capital
        val flag: Flags? = country?.flags
        val region: String? = country?.region
        val subregion: String? = country?.subregion
        val population: Int? = country?.population
        val borders: List<String?>? = country?.borders
        val languages: Map<String,String>? = country?.languages

//        val languages: Map<String,String>? = country?.languages

        holder.nameTextView.text = name
        holder.capitalTextView.text = capital?.get(0) ?: ""
        holder.regionTextView.text = region
        holder.subregionTextView.text = subregion
        holder.populationTextView.text = population.toString()
        val borderLinearLayout = holder.bordersLinearLayout
        val languageLinearLayout = holder.languagesLinearLayout

        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if(borders != null && borderLinearLayout.childCount == 0) {
            for(items in borders) {
                val view: TextView = inflater.inflate(R.layout.borders_list_item,null) as TextView
                view.text = items
                borderLinearLayout.addView(view,borderLinearLayout.childCount-1)
            }
        }

        if(languages !=null && languageLinearLayout.childCount == 0) {
            for(items in languages) {
                val view: TextView = inflater.inflate(R.layout.borders_list_item,null) as TextView
                view.text = items.value
                languageLinearLayout.addView(view,languageLinearLayout.childCount-1)
            }
        }

        Glide.with(mContext).load(flag?.png).into(holder.flagImageView)
    }

    fun onBindOffline(holder: CountriesViewHolder, position: Int) {
        val country: CountryEntity? = countryEntityList?.get(position)

        val name: String? = country?.name
        val capital: String? = country?.capital
        val flag: String? = country?.flags
        val region: String? = country?.region
        val subregion: String? = country?.subregion
        val population: Int? = country?.population

        holder.nameTextView.text = name
        holder.capitalTextView.text = capital
        holder.regionTextView.text = region
        holder.subregionTextView.text = subregion
        holder.populationTextView.text = population.toString()

        Glide.with(mContext).load(flag).into(holder.flagImageView)
    }


    public inner class CountriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        var capitalTextView: TextView = itemView.findViewById(R.id.capital_text_view)
        var flagImageView: ImageView = itemView.findViewById(R.id.flag_image_view)
        var regionTextView: TextView = itemView.findViewById(R.id.region_text_view)
        var subregionTextView: TextView = itemView.findViewById(R.id.subregion_text_view)
        var populationTextView: TextView = itemView.findViewById(R.id.population_text_view)
        var bordersLinearLayout: LinearLayout = itemView.findViewById(R.id.borders_linear_layout)
        var languagesLinearLayout: LinearLayout = itemView.findViewById(R.id.languages_linear_layout)

    }
}