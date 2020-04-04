package com.example.keviniswara.bookinglapang.user.home.view

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentHomeBinding
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mBinding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
                com.example.keviniswara.bookinglapang.R.layout.fragment_home, container, false)

        val view: View = mBinding.root

        var parentHeight = 0
        var buttonHeight = 0
        var parentWidth = 0
        var buttonWidth = 0

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                parentHeight = view.height
                buttonHeight = mBinding.buttonCariLapangan.height
                parentWidth = view.width
                buttonWidth = mBinding.buttonCariLapangan.width

                val buttonCariLapanganY: Float = ((parentHeight - buttonHeight * 13 / 8) / 2).toFloat()

                val buttonHomeY: Float = (buttonCariLapanganY + buttonHeight * 3 / 4)

                val buttonCariLapanganX: Float = ((parentWidth / 2 - buttonWidth * 5 / 6)).toFloat()

                val buttonHomeX: Float = (buttonCariLapanganX + buttonWidth * 2 / 3)

                mBinding.buttonCariLapangan.y = buttonCariLapanganY

                mBinding.buttonHelp.y = buttonHomeY

                mBinding.buttonCariLapangan.x = buttonCariLapanganX

                mBinding.buttonHelp.x = buttonHomeX

                if (parentHeight > 0 && buttonHeight > 0) {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })

        mBinding.buttonCariLapangan.setOnClickListener({
            val arguments = Bundle()
            val fragment = FieldDetailFragment()
            arguments.putString("field_name", "PASAGA")

            fragment.arguments = arguments
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(com.example.keviniswara.bookinglapang.R.id.content, fragment).addToBackStack(fragment
                    .javaClass.simpleName)
            ft.commit()
        })
        mBinding.buttonHelp.setOnClickListener {
            AlertDialog.Builder(this.context!!)
                    .setTitle("Cara Pemesanan")
                    .setMessage("1. Isi data lapangan yang ingin di cari ( Nama lapangan, Jenis Olahraga, Tanggal main, Jam main dan jam Selesai)\n" +
                            "\n" +
                            "2. Tunggu notifikasi pemesanan pada halaman Status Pemesanan. \n" +
                            "- Jika status \" Belum Verifikasi \", penjaga lapangan belum menjawab pesanan\n" +
                            "- Jika status \" lapangan tersedia \", lapangan yg dipesan tersedia dan user diharapkan segera melakukan pembayaran\n" +
                            "-Jika status \" tidak tersedia \", lapangan yg dipesan tidak tersedia, silahkan mengajukan pesanan lain\n" +
                            "\n" +
                            "3. Jika lapangan tersedia pilih menu bayar. Pilih ingin melakukan pembayaran transfer ke bank apa, dan lakukan transfer ke no rekening bank yang tertera sesuai dengan biaya serta KODE UNIK-nya (3 angka paling belakang)\n" +
                            "\n" +
                            "4. Tunggu konfirmasi finalisasi. apabila sudah sudah dikonfirmasi untuk pemesanan dan pembayaran, KorbanBooking hanya tinggal datang ke lapangan pada waktu dan tempat yang telah dipesan. Selamat bermain :)")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    .show()
        }
        return view
    }
}
