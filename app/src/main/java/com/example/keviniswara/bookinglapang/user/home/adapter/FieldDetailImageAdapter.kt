package com.example.keviniswara.bookinglapang.user.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FieldDetailSportListBinding
import com.example.keviniswara.bookinglapang.databinding.ItemFieldImagesBinding
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.view.FieldDetailFragment
import com.example.keviniswara.bookinglapang.user.home.view.JoinGameFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class FieldDetailImageAdapter(private val image_list: MutableList<String?>?, fragment: FieldDetailFragment) : RecyclerView.Adapter<FieldDetailImageAdapter.FieldDetailSportHolder>() {
    private lateinit var mBinding: ItemFieldImagesBinding
    private var mFragment: FieldDetailFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        if (image_list != null) {
            return image_list.size
        } else return 0
    }

    override fun onBindViewHolder(holder: FieldDetailImageAdapter.FieldDetailSportHolder, position: Int) {
        val itemOrder = image_list?.get(position)
        holder.bind(itemOrder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldDetailImageAdapter.FieldDetailSportHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_field_images, parent, false)
        return FieldDetailSportHolder(mBinding, mFragment)
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            image_list?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    class FieldDetailSportHolder(mBinding: ItemFieldImagesBinding, fragment: FieldDetailFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: ItemFieldImagesBinding
        private var find : FindEnemy? = null
        private var mFragment: FieldDetailFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment

        }

        fun bind(image_list: String){
//            this.find = order

            var url:String ="gs://booking-lapang.appspot.com/" + image_list;
            val mStorageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url)
            mStorageRef.downloadUrl.addOnSuccessListener {
                Picasso.get()
                        .load(it)
                        .into(mBinding.imageItemRestaurantImages)
            }

        }
    }
}